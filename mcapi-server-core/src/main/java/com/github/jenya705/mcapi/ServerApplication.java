package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.form.component.ComponentMapParserImpl;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModuleImpl;
import com.github.jenya705.mcapi.module.command.CommandModuleImpl;
import com.github.jenya705.mcapi.module.config.ConfigModuleImpl;
import com.github.jenya705.mcapi.module.database.DatabaseModuleImpl;
import com.github.jenya705.mcapi.module.link.LinkingModuleImpl;
import com.github.jenya705.mcapi.module.localization.LocalizationModuleImpl;
import com.github.jenya705.mcapi.module.mapper.MapperImpl;
import com.github.jenya705.mcapi.module.message.MessageDeserializerImpl;
import com.github.jenya705.mcapi.module.rest.RestModule;
import com.github.jenya705.mcapi.module.rest.route.*;
import com.github.jenya705.mcapi.module.selector.ServerSelectorProvider;
import com.github.jenya705.mcapi.module.storage.StorageModuleImpl;
import com.github.jenya705.mcapi.module.web.gateway.DefaultGateway;
import com.github.jenya705.mcapi.module.web.gateway.Gateway;
import com.github.jenya705.mcapi.module.web.reactor.ReactorServer;
import com.github.jenya705.mcapi.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    private boolean enabled = true;

    @Getter
    @Setter
    private ServerPlatform platform;

    @Getter
    @Bean
    private ServerCore core;
    @Getter
    @Bean
    private Gateway gateway;
    @Getter
    @Bean
    private ServerGateway serverGateway;

    public ServerApplication() {
        addClasses(
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
                GetPlayerLocationRouteHandler.class,
                GetPlayerRouteHandler.class,
                GetPlayerListRouteHandler.class,
                BanPlayerRouteHandler.class,
                SendMessageRouteHandler.class,
                DefaultGateway.class,
                ServerGatewayImpl.class,
                MessageDeserializerImpl.class,
                ComponentMapParserImpl.class
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
                Class<?> currentClass = clazz;
                while (currentClass != null) {
                    for (Method method : currentClass.getMethods()) {
                        OnInitializing onInitializing = method.getAnnotation(OnInitializing.class);
                        if (onInitializing != null) {
                            initializingMethods.get(onInitializing.priority()).add(new Pair<>(thisObject, method));
                        }
                        OnStartup onStartup = method.getAnnotation(OnStartup.class);
                        if (onStartup != null) {
                            startupMethods.get(onStartup.priority()).add(new Pair<>(thisObject, method));
                        }
                    }
                    currentClass = currentClass.getSuperclass();
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
        injectBeansInObject(this);
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
            Class<?> currentClass = obj.getClass();
            while (currentClass != null) {
                for (Method method : currentClass.getMethods()) {
                    OnDisable onDisable = method.getAnnotation(OnDisable.class);
                    if (onDisable != null) {
                        endMethods.get(onDisable.priority()).add(new Pair<>(obj, method));
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
        }
        runMethods(endMethods, "stop", false);
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
