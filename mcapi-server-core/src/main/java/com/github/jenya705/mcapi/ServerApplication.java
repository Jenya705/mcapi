package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.event.DefaultEventLoop;
import com.github.jenya705.mcapi.event.EventLoop;
import com.github.jenya705.mcapi.form.ComponentFormProvider;
import com.github.jenya705.mcapi.form.component.ComponentMapParserImpl;
import com.github.jenya705.mcapi.ignore.IgnoreManager;
import com.github.jenya705.mcapi.log.TimerTask;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModuleImpl;
import com.github.jenya705.mcapi.module.block.BlockDataModuleImpl;
import com.github.jenya705.mcapi.module.bot.BotManagementImpl;
import com.github.jenya705.mcapi.module.command.CommandModuleImpl;
import com.github.jenya705.mcapi.module.config.ConfigModuleImpl;
import com.github.jenya705.mcapi.module.database.DatabaseModuleImpl;
import com.github.jenya705.mcapi.module.enchantment.EnchantmentStorageImpl;
import com.github.jenya705.mcapi.module.entity.capture.EntityCapturableModuleImpl;
import com.github.jenya705.mcapi.module.link.LinkingModuleImpl;
import com.github.jenya705.mcapi.module.localization.LocalizationModuleImpl;
import com.github.jenya705.mcapi.module.mapper.MapperImpl;
import com.github.jenya705.mcapi.module.material.MaterialStorageImpl;
import com.github.jenya705.mcapi.module.menu.MenuModuleImpl;
import com.github.jenya705.mcapi.module.message.MessageDeserializerImpl;
import com.github.jenya705.mcapi.module.rest.RestModule;
import com.github.jenya705.mcapi.module.rest.route.SendMessageRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.block.*;
import com.github.jenya705.mcapi.module.rest.route.bot.GetBotLinkedPlayersRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.bot.GetBotPermissionRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.bot.GetBotTargetPermissionRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.bot.LinkRequestRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.command.CreateCommandRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.command.DeleteCommandRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.entity.CaptureEntityRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.entity.GetEntityRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.inventory.CloseInventoryRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.inventory.OpenInventoryRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.offline.BanOfflinePlayerRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.offline.GetOfflinePlayerRouteHandler;
import com.github.jenya705.mcapi.module.rest.route.player.*;
import com.github.jenya705.mcapi.module.rest.route.world.GetWorldRouteHandler;
import com.github.jenya705.mcapi.module.selector.ServerSelectorProvider;
import com.github.jenya705.mcapi.module.storage.StorageModuleImpl;
import com.github.jenya705.mcapi.module.web.reactor.ReactorServer;
import com.github.jenya705.mcapi.module.web.tunnel.DefaultEventTunnel;
import com.github.jenya705.mcapi.module.web.tunnel.EventTunnel;
import com.github.jenya705.mcapi.util.Pair;
import com.github.jenya705.mcapi.worker.ExecutorServiceWorker;
import com.github.jenya705.mcapi.worker.Worker;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Slf4j
public class ServerApplication {

    @Getter
    private final List<Class<?>> classes = new ArrayList<>();

    private final List<Object> beans = new ArrayList<>();
    private final Map<Class<?>, Object> cachedBeans = new HashMap<>();

    @Getter
    private boolean initialized = false;
    @Getter
    private boolean enabled = true;

    @Getter
    @Setter
    private boolean debug = false;

    @Getter
    @Bean
    private ServerCore core;
    @Getter
    @Bean
    private EventTunnel eventTunnel;
    @Getter
    @Bean
    private Worker worker;
    @Getter
    @Bean
    private EventLoop eventLoop;

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
                DefaultEventTunnel.class,
                EventBroadcaster.class,
                MessageDeserializerImpl.class,
                ComponentMapParserImpl.class,
                ComponentFormProvider.class,
                ExecutorServiceWorker.class,
                DefaultEventLoop.class,
                BotManagementImpl.class,
                BlockDataModuleImpl.class,
                IgnoreManager.class,
                MaterialStorageImpl.class,
                EnchantmentStorageImpl.class,
                EntityCapturableModuleImpl.class,
                MenuModuleImpl.class,
                // Routes
                GetPlayerLocationRouteHandler.class,
                GetPlayerRouteHandler.class,
                GetPlayerListRouteHandler.class,
                BanPlayerRouteHandler.class,
                KickPlayerRouteHandler.class,
                PlayerPermissionRouteHandler.class,
                SendMessageRouteHandler.class,
                KillPlayerRouteHandler.class,
                GetBotLinkedPlayersRouteHandler.class,
                GetBotPermissionRouteHandler.class,
                GetBotTargetPermissionRouteHandler.class,
                CreateCommandRouteHandler.class,
                DeleteCommandRouteHandler.class,
                LinkRequestRouteHandler.class,
                GetOfflinePlayerRouteHandler.class,
                BanOfflinePlayerRouteHandler.class,
                GetBlockRouteHandler.class,
                GetWorldRouteHandler.class,
                GetBlockDataRouteHandler.class,
                GetBlockInventoryRouteHandler.class,
                GetBlockInventoryItemRouteHandler.class,
                GetPlayerInventoryRouteHandler.class,
                GetPlayerInventoryItemRouteHandler.class,
                GetPlayerEnderChestRouteHandler.class,
                GetPlayerEnderChestItemRouteHandler.class,
                SetBlockDataFieldRouteHandler.class,
                GetEntityRouteHandler.class,
                CaptureEntityRouteHandler.class,
                OpenInventoryRouteHandler.class,
                CloseInventoryRouteHandler.class
                // End Routes
        );
    }

    public void start() {
        log.info("Plugin is under heavy development! All api is subject to change!");
        log.info("If you find a bug, consider to issue it on https://github.com/Jenya705/mcapi/issues");
        log.info("Plugin wiki: https://github.com/Jenya705/mcapi/wiki");
        TimerTask startTask = TimerTask.start(log, "Starting application...");
        addBean(this);
        Map<Integer, Set<Pair<Object, Method>>> initializingMethods = new HashMap<>();
        Map<Integer, Set<Pair<Object, Method>>> startupMethods = new HashMap<>();
        for (Object obj : beans) {
            onStartMethods(initializingMethods, startupMethods, obj);
        }
        for (Class<?> clazz : classes) {
            try {
                Constructor<?> clazzConstructor = clazz.getConstructor();
                Object thisObject = clazzConstructor.newInstance();
                onStartMethods(initializingMethods, startupMethods, thisObject);
                beans.add(thisObject);
            } catch (Exception e) {
                log.error(String.format("Can not initialize bean %s, disabling:", clazz.getCanonicalName()), e);
                disable();
                return;
            }
        }
        initialized = true;
        injectBeans();
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
        if (debug) {
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
        if (!initialized || !enabled) return;
        Map<Integer, Set<Pair<Object, Method>>> endMethods = new HashMap<>();
        for (Object obj : beans) {
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
        if (isDebug()) {
            log.warn("Trying to get not exist bean:", new RuntimeException());
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

    public void swapBeans(Class<?> from, Class<?> to) {
        if (initialized) {
            throw new IllegalStateException("You can not change bean classes when application already initialized");
        }
        int i = 0;
        for (Class<?> clazz: classes) {
            if (clazz.isAssignableFrom(from)) {
                classes.remove(i);
                classes.add(to);
                return;
            }
            ++i;
        }
        if (isDebug()) {
            log.warn(String.format(
                    "Tried to swap not exist bean. From: %s, To: %s",
                    from, to
            ), new RuntimeException());
        }
    }

    private void disable() {
        eventLoop.invoke(new ApplicationDisableEvent());
        enabled = false;
        core.disable();
    }
}
