package com.github.jenya705.mcapi.server.application;

import com.github.jenya705.mcapi.server.ServerCore;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.event.application.ApplicationClassActionEvent;
import com.github.jenya705.mcapi.server.event.application.ApplicationDisableEvent;
import com.github.jenya705.mcapi.server.log.TimerTask;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.web.tunnel.EventTunnel;
import com.github.jenya705.mcapi.server.util.Pair;
import com.github.jenya705.mcapi.server.worker.Worker;
import com.google.inject.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Slf4j
@Getter
@Singleton
public class ServerApplication {

    private boolean initialized;
    private boolean enabled = true;

    private final Injector injector;
    private final AtomicBoolean debug = new AtomicBoolean(true);
    private final ServerCore core;
    private final Worker worker;
    private final EventLoop eventLoop;
    private final Provider<EventTunnel> eventTunnelProvider;

    @Inject
    public ServerApplication(ServerCore serverCore, Injector injector,
                             Worker worker, EventLoop eventLoop, Provider<EventTunnel> eventTunnelProvider) {
        log.info("Plugin is under heavy development! All api is subject to change!");
        log.info("If you find a bug, consider to issue it on https://github.com/Jenya705/mcapi/issues");
        log.info("Plugin wiki: https://github.com/Jenya705/mcapi/wiki");
        core = serverCore;
        this.injector = injector;
        this.worker = worker;
        this.eventLoop = eventLoop;
        this.eventTunnelProvider = eventTunnelProvider;
    }

    public void start() {
        TimerTask startTask = TimerTask.start(log, "Starting application...");
        Map<Integer, Set<Pair<Object, Method>>> initializingMethods = new HashMap<>();
        Map<Integer, Set<Pair<Object, Method>>> startupMethods = new HashMap<>();
        initialized = true;
        for (Object obj : getBeans(it -> true)) {
            onStartMethods(initializingMethods, startupMethods, obj);
        }
        setDebug(getBean(ConfigModule.class)
                .getConfig()
                .getBoolean("debug")
                .orElse(false)
        );
        runMethods(
                initializingMethods,
                "initialize",
                ApplicationClassActionEvent.Action.INIT,
                true
        );
        if (!enabled) return;
        runMethods(
                startupMethods,
                "startup",
                ApplicationClassActionEvent.Action.START,
                true
        );
        if (isDebug()) {
            log.info("Debug mode enabled");
        }
        startTask.complete();
    }

    protected void onStartMethods(Map<Integer, Set<Pair<Object, Method>>> initializingMethods, Map<Integer, Set<Pair<Object, Method>>> startupMethods, Object obj) {
        Class<?> currentClass = obj.getClass();
        while (currentClass != null) {
            for (Method method : currentClass.getMethods()) {
                OnInitializing onInitializing = method.getAnnotation(OnInitializing.class);
                if (onInitializing != null) {
                    addToMap(initializingMethods, onInitializing.priority(), new Pair<>(obj, method));
                }
                OnStartup onStartup = method.getAnnotation(OnStartup.class);
                if (onStartup != null) {
                    addToMap(startupMethods, onStartup.priority(), new Pair<>(obj, method));
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }

    protected <E, T> void addToMap(Map<E, Set<T>> map, E key, T value) {
        if (!map.containsKey(key)) map.put(key, new HashSet<>());
        map.get(key).add(value);
    }

    protected void runMethods(Map<Integer, Set<Pair<Object, Method>>> methods, String procedureName, ApplicationClassActionEvent.Action action, boolean onFailedDisable) {
        List<Set<Pair<Object, Method>>> sortedMethods =
                methods
                        .entrySet()
                        .stream()
                        .sorted(Comparator.comparingInt(Map.Entry::getKey))
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList());
        for (Set<Pair<Object, Method>> methodsPriority : sortedMethods) {
            for (Pair<Object, Method> methodPair : methodsPriority) {
                try {
                    ApplicationClassActionEvent event = new ApplicationClassActionEvent(
                            methodPair.getLeft(), action
                    );
                    eventLoop.invoke(event);
                    if (event.isCancelled()) continue;
                    methodPair.getRight().setAccessible(true);
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
        if (!initialized || !enabled) {
            if (isDebug()) {
                log.warn(
                        "Someone tried to stop application when it is not enabled",
                        new RuntimeException()
                );
            }
            return;
        }
        Map<Integer, Set<Pair<Object, Method>>> endMethods = new HashMap<>();
        for (Object obj : getBeans(it -> true)) {
            Class<?> currentClass = obj.getClass();
            while (currentClass != null) {
                for (Method method : currentClass.getMethods()) {
                    OnDisable onDisable = method.getAnnotation(OnDisable.class);
                    if (onDisable != null) {
                        addToMap(endMethods, onDisable.priority(), new Pair<>(obj, method));
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
        }
        runMethods(
                endMethods,
                "stop",
                ApplicationClassActionEvent.Action.STOP,
                false
        );
        initialized = false;
        enabled = false;
    }

    public <T> T getBean(Class<? extends T> clazz) {
        if (!initialized) {
            throw new IllegalStateException("Application is not initialized");
        }
        if (injector == null) {
            if (isDebug()) {
                log.warn("Someone trying to get bean while injector is null", new RuntimeException());
            }
            return null;
        }
        Binding<? extends T> binding = injector.getExistingBinding(Key.get(clazz));
        if (binding == null) {
            if (isDebug()) {
                log.warn("Someone trying to get non-exist bean", new RuntimeException());
            }
            return null;
        }
        return binding.getProvider().get();
    }

    public void setDebug(boolean debug) {
        this.debug.set(debug);
    }

    public boolean isDebug() {
        return debug.get();
    }

    public EventTunnel getEventTunnel() {
        return eventTunnelProvider.get();
    }

    private Collection<Object> getBeans(Predicate<Binding<?>> predicate) {
        if (injector == null) {
            return Collections.emptyList();
        }
        Set<Class<?>> exists = new HashSet<>();
        return injector
                .getBindings()
                .values()
                .stream()
                .filter(it -> {
                    Class<?> clazz = it.getKey().getTypeLiteral().getRawType();
                    if (exists.contains(clazz)) return false;
                    exists.add(clazz);
                    return predicate.test(it);
                })
                .map(binding -> binding.getProvider().get())
                .collect(Collectors.toList());
    }

    private void disable() {
        eventLoop.invoke(new ApplicationDisableEvent());
        enabled = false;
        core.disable();
    }
}
