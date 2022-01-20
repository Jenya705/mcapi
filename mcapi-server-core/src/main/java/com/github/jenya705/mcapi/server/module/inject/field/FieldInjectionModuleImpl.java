package com.github.jenya705.mcapi.server.module.inject.field;

import com.github.jenya705.mcapi.rest.*;
import com.github.jenya705.mcapi.rest.block.*;
import com.github.jenya705.mcapi.rest.command.RestCommand;
import com.github.jenya705.mcapi.rest.command.RestCommandExecutableOption;
import com.github.jenya705.mcapi.rest.command.RestCommandInteractionEvent;
import com.github.jenya705.mcapi.rest.command.RestCommandInteractionValue;
import com.github.jenya705.mcapi.rest.enchantment.RestEnchantment;
import com.github.jenya705.mcapi.rest.enchantment.RestItemEnchantment;
import com.github.jenya705.mcapi.rest.entity.RestCapturedEntityClickEvent;
import com.github.jenya705.mcapi.rest.entity.RestEntity;
import com.github.jenya705.mcapi.rest.event.*;
import com.github.jenya705.mcapi.rest.inventory.*;
import com.github.jenya705.mcapi.rest.menu.RestMenuItem;
import com.github.jenya705.mcapi.rest.player.RestPlayer;
import com.github.jenya705.mcapi.rest.player.RestPlayerAbilities;
import com.github.jenya705.mcapi.rest.player.RestPlayerList;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.OnDisable;
import com.github.jenya705.mcapi.server.application.OnStartup;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.log.TimerTask;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.util.CacheClassMap;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class FieldInjectionModuleImpl extends AbstractApplicationModule implements FieldInjectionModule {

    private static final String fileName = "field-ignores";

    @Getter
    @RequiredArgsConstructor
    private static class InjectedClassInformation<T> {

        private final Set<String> ignoredFields;
        private final Map<String, Function<Object, Object>> addedFields;

        public InjectedClassInformation() {
            this(new CopyOnWriteArraySet<>(), new ConcurrentHashMap<>());
        }

    }

    private final Mapper mapper;

    private final Map<Class<?>, InjectedClassInformation<?>> injectableClasses = CacheClassMap.concurrent();

    @Inject
    @SuppressWarnings("unchecked")
    public FieldInjectionModuleImpl(ServerApplication application, Mapper mapper) throws IOException {
        super(application);
        this.mapper = mapper;
        TimerTask task = TimerTask.start(log, "Loading field ignores");
        Map<String, Object> loadedIgnorableFields = Objects.requireNonNullElse(
                core().loadConfig(fileName), Collections.emptyMap());
        for (Map.Entry<String, Object> entry : loadedIgnorableFields.entrySet()) {
            String className = entry.getKey();
            try {
                Map<String, Boolean> fieldsToggle = (Map<String, Boolean>) entry.getValue();
                Class<?> clazz = Class.forName(className);
                InjectedClassInformation<?> injectedClassInformation =
                        new InjectedClassInformation<>(
                                new CopyOnWriteArraySet<>(
                                        fieldsToggle
                                                .entrySet()
                                                .stream()
                                                .filter(it -> !it.getValue())
                                                .map(Map.Entry::getKey)
                                                .collect(Collectors.toSet())
                                ),
                                new ConcurrentHashMap<>()
                        );
                injectableClasses.put(
                        clazz, injectedClassInformation
                );
                registerClassSerializer(clazz);
            } catch (Throwable e) {
                log.warn(String.format("Failed to load field ignores of %s", className), e);
            }
        }
        task.complete();
        //<editor-fold desc="Registering Classes" defaultstate="collapsed">
        registerClasses(
                RestBarrel.class,
                RestBlock.class,
                RestBrewingStand.class,
                RestCampfire.class,
                RestChest.class,
                RestCommandBlock.class,
                RestDoor.class,
                RestEnderChest.class,
                RestFurnace.class,
                RestPiston.class,
                RestRedstoneWire.class,
                RestRedstoneWireConnection.class,
                RestShulkerBox.class,
                RestStairs.class,
                RestCommand.class,
                RestCommandExecutableOption.class,
                RestCommandInteractionEvent.class,
                RestCommandInteractionValue.class,
                RestEnchantment.class,
                RestItemEnchantment.class,
                RestCapturedEntityClickEvent.class,
                RestEntity.class,
                RestJoinEvent.class,
                RestLinkEvent.class,
                RestMenuClickEvent.class,
                RestMessageEvent.class,
                RestPlayerBlockBreakEvent.class,
                RestPlayerBlockPlaceEvent.class,
                RestQuitEvent.class,
                RestSubscribeEvent.class,
                RestUnlinkEvent.class,
                RestIdentifiedInventoryItemStack.class,
                RestInventory.class,
                RestInventoryItemStack.class,
                RestInventoryView.class,
                RestItemStack.class,
                RestMenuItem.class,
                RestPlayer.class,
                RestPlayerAbilities.class,
                RestPlayerList.class,
                RestBoundingBox.class,
                RestCommandSender.class,
                RestError.class,
                RestEventTunnelAuthorizationRequest.class,
                RestForm.class,
                RestLinkRequest.class,
                RestLocation.class,
                RestOfflinePlayer.class,
                RestPermission.class,
                RestShortBoundingBox.class,
                RestSlab.class,
                RestSubscribeRequest.class,
                RestWorld.class
        );
        //</editor-fold>
    }

    @OnDisable(priority = 5)
    @OnStartup(priority = 5)
    public void save() throws IOException {
        TimerTask task = TimerTask.start(log, "Saving field ignores");
        Map<String, Object> ignorableFieldsMap = new HashMap<>();
        injectableClasses.forEach((clazz, ignores) -> {
            Map<String, Boolean> thisClassResult = new HashMap<>();
            for (Field field : clazz.getDeclaredFields()) {
                thisClassResult.put(field.getName(), true);
            }
            ignores.getAddedFields().forEach((name, func) -> thisClassResult.put(name, true));
            ignores.getIgnoredFields().forEach(it -> thisClassResult.put(it, false));
            ignorableFieldsMap.put(clazz.getCanonicalName(), thisClassResult);
        });
        core().saveConfig(fileName, ignorableFieldsMap);
        task.complete();
    }

    @Override
    public Set<String> getIgnorableFields(Class<?> clazz) {
        if (!injectableClasses.containsKey(clazz)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(injectableClasses.get(clazz).getIgnoredFields());
    }

    @Override
    public void registerClass(Class<?> clazz) {
        if (!injectableClasses.containsKey(clazz)) {
            injectableClasses.put(clazz, new InjectedClassInformation<>());
            registerClassSerializer(clazz);
        }
    }

    private void registerClassSerializer(Class<?> clazz) {
        mapper.jsonSerializer(clazz, (value, generator, serializers) -> {
            InjectedClassInformation<?> information = injectableClasses.get(clazz);
            try {
                generator.writeStartObject();
                for (Field field : clazz.getDeclaredFields()) {
                    if (information.getIgnoredFields().contains(field.getName())) {
                        continue;
                    }
                    boolean wasAccessible;
                    if (Modifier.isStatic(field.getModifiers())) {
                        wasAccessible = field.canAccess(null);
                    }
                    else {
                        wasAccessible = field.canAccess(value);
                    }
                    if (!wasAccessible) field.setAccessible(true);
                    Object currentValue = field.get(value);
                    mapper.getDefaultValueGetter().writeIfNotDefault(generator, currentValue, field, false);
                    if (!wasAccessible) field.setAccessible(false);
                }
                generator.writeEndObject();
            } catch (IllegalAccessException e) {
                throw new IOException(e);
            }
            for (Map.Entry<String, Function<Object, Object>> entry : information.getAddedFields().entrySet()) {
                if (information.getIgnoredFields().contains(entry.getKey())) {
                    continue;
                }
                generator.writeObjectField(entry.getKey(), entry.getValue().apply(value));
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void registerField(Class<T> clazz, String field, Function<T, Object> getter) {
        if (!injectableClasses.containsKey(clazz)) {
            return;
        }
        InjectedClassInformation<T> injectableClass = (InjectedClassInformation<T>) injectableClasses.get(clazz);
        injectableClass.getAddedFields().put(field, it -> getter.apply((T) it));
    }
}
