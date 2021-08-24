package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.module.authorization.AuthorizationModuleImpl;
import com.github.jenya705.mcapi.module.config.ConfigModuleImpl;
import com.github.jenya705.mcapi.module.database.DatabaseModuleImpl;
import com.github.jenya705.mcapi.module.storage.StorageModuleImpl;
import com.github.jenya705.mcapi.rest.PlayerGetterRest;
import com.github.jenya705.mcapi.rest.PlayerListRest;
import com.github.jenya705.mcapi.rest.PlayerPunishmentRest;
import com.github.jenya705.mcapi.rest.PlayerSendMessageRest;
import com.github.jenya705.mcapi.util.Pair;
import jakarta.ws.rs.core.UriBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;

/**
 * @author Jenya705
 */
@Slf4j
public class ServerApplication {

    private final List<Pair<Class<?>, Boolean>> classes = new ArrayList<>();
    private final List<Object> beans = new ArrayList<>();
    private final Map<Class<?>, Object> cachedBeans = new HashMap<>();
    private boolean initialized = false;

    @Getter
    private ServerCore core;

    private ServerApplication() {
        addClasses(
                PlayerSendMessageRest.class,
                PlayerGetterRest.class,
                PlayerListRest.class,
                PlayerPunishmentRest.class,
                JacksonProvider.class,
                ServerExceptionMapper.class,
                ConfigModuleImpl.class,
                DatabaseModuleImpl.class,
                StorageModuleImpl.class,
                AuthorizationModuleImpl.class
        );
    }

    @Getter
    private static final ServerApplication application = new ServerApplication();

    public void start() {
        List<Class<?>> jerseyClasses = new ArrayList<>();
        List<Set<Pair<Object, Method>>> initializingMethods = new ArrayList<>(5);
        List<Set<Pair<Object, Method>>> startupMethods = new ArrayList<>(5);
        for (int i = 0; i < 5; ++i) {
            initializingMethods.add(new HashSet<>());
            startupMethods.add(new HashSet<>());
        }
        for (Pair<Class<?>, Boolean> pair : classes) {
            Class<?> clazz = pair.getLeft();
            if (pair.getRight() || clazz.getAnnotation(JerseyClass.class) != null) {
                jerseyClasses.add(clazz);
                continue;
            }
            try {
                Constructor<?> clazzConstructor = clazz.getConstructor();
                Object thisObject = clazzConstructor.newInstance();
                for (Method method : clazz.getMethods()) {
                    OnInitializing onInitializing = method.getAnnotation(OnInitializing.class);
                    if (onInitializing != null) {
                        initializingMethods.get(onInitializing.priority()).add(new Pair<>(thisObject, method));
                    }
                    OnStartup onStartup = method.getAnnotation(OnStartup.class);
                    if (onStartup != null) {
                        startupMethods.get(onStartup.priority()).add(new Pair<>(thisObject, method));
                    }
                }
                beans.add(thisObject);
            } catch (Exception e) {
                log.error(String.format("Can not initialize bean %s, disabling:", clazz.getCanonicalName()), e);
                return;
            }
        }
        initialized = true;
        core = getBean(ServerCore.class);
        runMethods(initializingMethods, "initialize");
        runMethods(startupMethods, "startup");
        runJerseyServer(jerseyClasses);
    }

    protected void runJerseyServer(List<Class<?>> jerseyClasses) {
        URI baseUri = UriBuilder.fromUri("http://localhost").port(8080).build();
        ResourceConfig configuration = new ResourceConfig(jerseyClasses.toArray(new Class<?>[0]));
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, configuration);
        try {
            httpServer.start();
        } catch (IOException e) {
            log.error("Can not start http server:", e);
        }
    }

    protected void runMethods(List<Set<Pair<Object, Method>>> methods, String procedureName) {
        for (Set<Pair<Object, Method>> methodsPriority : methods) {
            for (Pair<Object, Method> methodPair : methodsPriority) {
                try {
                    methodPair.getRight().invoke(methodPair.getLeft());
                } catch (Exception e) {
                    log.error(String.format(
                            "Can not %s bean object %s:",
                            procedureName,
                            methodPair.getLeft().getClass().getCanonicalName()
                    ), e);
                }
            }
        }
    }

    public void stop() {
        if (!initialized) return;
        List<Set<Pair<Object, Method>>> endMethods = new ArrayList<>(5);
        for (int i = 0; i < 5; ++i) {
            endMethods.add(new HashSet<>());
        }
        for (Object obj : beans) {
            for (Method method : obj.getClass().getMethods()) {
                OnDisable onDisable = method.getAnnotation(OnDisable.class);
                if (onDisable != null) {
                    endMethods.get(onDisable.priority()).add(new Pair<>(obj, method));
                }
            }
        }
        runMethods(endMethods, "stop");
        initialized = false;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<? extends T> clazz) {
        if (!initialized) {
            throw new IllegalStateException("Application is not initialized");
        }
        if (cachedBeans.containsKey(clazz)) {
            return (T) cachedBeans.get(clazz);
        }
        for (Object obj : beans) {
            if (clazz.isAssignableFrom(obj.getClass())) {
                cachedBeans.put(clazz, obj);
                return clazz.cast(obj);
            }
        }
        return null;
    }

    public void addClass(Class<?> clazz) {
        classes.add(new Pair<>(clazz, false));
    }

    public void addClasses(Class<?>... classes) {
        for (Class<?> clazz: classes) {
            this.classes.add(new Pair<>(clazz, false));
        }
    }

    public void addJerseyClass(Class<?> clazz) {
        classes.add(new Pair<>(clazz, true));
    }

    public void addBean(Object obj) {
        beans.add(obj);
    }
}
