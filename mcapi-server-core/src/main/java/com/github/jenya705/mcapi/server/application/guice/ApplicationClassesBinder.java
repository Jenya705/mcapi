package com.github.jenya705.mcapi.server.application.guice;

import com.github.jenya705.mcapi.server.application.EventBroadcaster;
import com.github.jenya705.mcapi.server.event.DefaultEventLoop;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.form.ComponentFormProvider;
import com.github.jenya705.mcapi.server.form.component.ComponentMapParser;
import com.github.jenya705.mcapi.server.ignore.IgnoreManager;
import com.github.jenya705.mcapi.server.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.server.module.block.BlockDataModule;
import com.github.jenya705.mcapi.server.module.bot.BotManagement;
import com.github.jenya705.mcapi.server.module.command.CommandModule;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.enchantment.EnchantmentStorage;
import com.github.jenya705.mcapi.server.module.entity.capture.EntityCaptureModule;
import com.github.jenya705.mcapi.server.module.inject.field.FieldInjectionModule;
import com.github.jenya705.mcapi.server.module.link.LinkingModule;
import com.github.jenya705.mcapi.server.module.localization.LocalizationModule;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.module.material.MaterialStorage;
import com.github.jenya705.mcapi.server.module.menu.MenuModule;
import com.github.jenya705.mcapi.server.module.message.MessageDeserializer;
import com.github.jenya705.mcapi.server.module.rest.RestModule;
import com.github.jenya705.mcapi.server.module.rest.route.SendMessageRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.block.*;
import com.github.jenya705.mcapi.server.module.rest.route.bot.GetBotLinkedPlayersRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.bot.GetBotPermissionRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.bot.GetBotTargetPermissionRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.bot.LinkRequestRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.command.CreateCommandRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.command.DeleteCommandRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.entity.CaptureEntityRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.entity.GetEntityRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.inventory.CloseInventoryRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.inventory.OpenInventoryRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.offline.BanOfflinePlayerRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.offline.GetOfflinePlayerRouteHandler;
import com.github.jenya705.mcapi.server.module.rest.route.player.*;
import com.github.jenya705.mcapi.server.module.rest.route.world.GetWorldRouteHandler;
import com.github.jenya705.mcapi.server.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import com.github.jenya705.mcapi.server.module.web.WebServer;
import com.github.jenya705.mcapi.server.worker.Worker;
import com.google.inject.AbstractModule;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ApplicationClassesBinder extends AbstractModule {

    public static final List<Class<?>> modules = Arrays.asList(
            ConfigModule.class,
            DatabaseModule.class,
            LocalizationModule.class,
            Mapper.class,
            ComponentFormProvider.class,
            StorageModule.class,
            AuthorizationModule.class,
            BlockDataModule.class,
            BotManagement.class,
            CommandModule.class,
            EnchantmentStorage.class,
            EntityCaptureModule.class,
            FieldInjectionModule.class,
            LinkingModule.class,
            MaterialStorage.class,
            MenuModule.class,
            MessageDeserializer.class,
            RestModule.class,
            SelectorProvider.class,
            WebServer.class,
            DefaultEventLoop.class,
            IgnoreManager.class,
            ComponentMapParser.class,
            EventBroadcaster.class,
            EventLoop.class,
            Worker.class
    );

    public static final List<Class<?>> routes = Arrays.asList(
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
    );

    public static ApplicationClassesBinder ofModules() {
        return new ApplicationClassesBinder(modules);
    }

    public static ApplicationClassesBinder ofRoutes() {
        return new ApplicationClassesBinder(routes);
    }

    public static ApplicationClassesBinder of(Class<?>... classes) {
        return new ApplicationClassesBinder(classes);
    }

    private final Collection<Class<?>> classes;

    public ApplicationClassesBinder(Collection<Class<?>> classes) {
        this.classes = classes;
    }

    public ApplicationClassesBinder(Class<?>... classes) {
        this(Arrays.asList(classes));
    }

    @Override
    protected void configure() {
        classes.forEach(this::bind);
    }

    public ApplicationBuilderLayer asLayer() {
        return new ApplicationBuilderLayer().module(this);
    }

}
