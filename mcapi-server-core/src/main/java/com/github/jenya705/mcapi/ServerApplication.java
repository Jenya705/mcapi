package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.module.web.gateway.Gateway;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModuleImpl;
import com.github.jenya705.mcapi.module.command.CommandModuleImpl;
import com.github.jenya705.mcapi.module.config.ConfigModuleImpl;
import com.github.jenya705.mcapi.module.database.DatabaseModuleImpl;
import com.github.jenya705.mcapi.module.link.LinkingModuleImpl;
import com.github.jenya705.mcapi.module.localization.LocalizationModuleImpl;
import com.github.jenya705.mcapi.module.mapper.MapperImpl;
import com.github.jenya705.mcapi.module.rest.GetPlayerRouteHandler;
import com.github.jenya705.mcapi.module.rest.RestModule;
import com.github.jenya705.mcapi.module.rest.SendMessageRouteHandler;
import com.github.jenya705.mcapi.module.selector.ServerSelectorProvider;
import com.github.jenya705.mcapi.module.storage.StorageModuleImpl;
import com.github.jenya705.mcapi.module.web.reactor.ReactorServer;
import com.github.jenya705.mcapi.util.Pair;
import jakarta.ws.rs.core.UriBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;

/**
 * @author Jenya705
 */
@Slf4j
public class ServerApplication {

    private final List<Class<?>> classes = new ArrayList<>();
    private final List<Object> beans = new ArrayList<>();
    private final Map<Class<?>, Object> cachedBeans = new HashMap<>();

    private boolean initialized = false;
    private HttpServer httpServer;
    private boolean enabled = true;

    @Getter
    @Setter
    private ServerPlatform platform;

    @Getter
    private ServerCore core;
    @Getter
    private Gateway gateway;
    @Getter
    private final ServerGateway serverGateway = new ServerGatewayImpl();

    public ServerApplication() {
        addClasses(
                JacksonProvider.class,
                ConfigModuleImpl.class,
                DatabaseModuleImpl.class,
                StorageModuleImpl.class,
                AuthorizationModuleImpl.class,
                CommandModuleImpl.class,
                LinkingModuleImpl.class,
                LocalizationModuleImpl.class,
                MapperImpl.class,
                ReactorServer.class,
                RestModule.class,
                ServerSelectorProvider.class,
                GetPlayerRouteHandler.class,
                SendMessageRouteHandler.class
        );
    }

    public void start() {
        List<Set<Pair<Object, Method>>> initializingMethods = new ArrayList<>(5);
        List<Set<Pair<Object, Method>>> startupMethods = new ArrayList<>(5);
        for (int i = 0; i < 5; ++i) {
            initializingMethods.add(new HashSet<>());
            startupMethods.add(new HashSet<>());
        }
        for (Class<?> clazz : classes) {
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
                disable();
                return;
            }
        }
        addBean(this);
        initialized = true;
        injectBeans();
        core = getBean(ServerCore.class);
        gateway = getBean(Gateway.class);
        runMethods(initializingMethods, "initialize", true);
        if (!enabled) return;
        runMethods(startupMethods, "startup", true);
    }

    protected void injectBeans() {
        for (Object obj : beans) {
            injectBeansInObject(obj);
        }
    }

    public void injectBeansInObject(Object obj) {
        Class<?> currentObjClass = obj.getClass();
        while (currentObjClass != null) {
            injectBeans(obj, currentObjClass);
            currentObjClass = currentObjClass.getSuperclass();
        }
    }

    protected void injectBeans(Object obj, Class<?> objClass) {
        for (Field field : objClass.getDeclaredFields()) {
            if (field.getAnnotation(Bean.class) == null) continue;
            Object toInject = getBean(field.getType());
            if (toInject == null) {
                log.error(String.format(
                        "Failed to inject %s bean to %s class in %s field",
                        field.getType().getCanonicalName(),
                        obj.getClass().getCanonicalName(),
                        field.getName()
                ));
                continue;
            }
            try {
                field.setAccessible(true);
                field.set(obj, toInject);
            } catch (Exception e) {
                log.error(String.format(
                        "Failed to inject %s bean to %s class in %s field because of exception:",
                        field.getType().getCanonicalName(),
                        obj.getClass().getCanonicalName(),
                        field.getName()
                ), e);
            }
        }
    }

    protected void runGrizzlyHttpServer(List<Class<?>> jerseyClasses) {
        URI baseUri = UriBuilder.fromUri("http://localhost").port(8080).build();
        ResourceConfig configuration = new ResourceConfig(jerseyClasses.toArray(new Class<?>[0]));
        httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, configuration, false);
        try {
            httpServer.start();
        } catch (IOException e) {
            log.error("Can not start http server:", e);
        }
    }

    protected void runMethods(List<Set<Pair<Object, Method>>> methods, String procedureName, boolean onFailedDisable) {
        for (Set<Pair<Object, Method>> methodsPriority : methods) {
            for (Pair<Object, Method> methodPair : methodsPriority) {
                try {
                    methodPair.getRight().invoke(methodPair.getLeft());
                } catch (Throwable e) {
                    log.error(String.format(
                            "Can not %s bean object %s:",
                            procedureName,
                            methodPair.getLeft().getClass().getCanonicalName()
                    ), e);
                    if (onFailedDisable) {
                        log.error("Because this object throw Exception, so disabling application as said");
                        disable();
                        return;
                    }
                }
            }
        }
    }

    public void stop() {
        if (!initialized || !enabled) return;
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
        runMethods(endMethods, "stop", false);
        try {
            httpServer.shutdown().get();
        } catch (Exception e) {
            log.error("Can not disable http server:", e);
        }
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
                return (T) obj;
            }
        }
        return null;
    }

    public void addClass(Class<?> clazz) {
        classes.add(clazz);
    }

    public void addClasses(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            addClass(clazz);
        }
    }

    public void addBean(Object obj) {
        beans.add(obj);
    }

    private void disable() {
        enabled = false;
        core.disable();
    }
}
