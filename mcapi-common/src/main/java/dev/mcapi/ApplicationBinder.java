package dev.mcapi;

import com.google.inject.AbstractModule;
import dev.mcapi.modules.config.ConfigModule;
import dev.mcapi.modules.event.EventLoop;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
class ApplicationBinder extends AbstractModule {

    private static final Collection<Class<?>> FIRST_STAGE = List.of(
            ConfigModule.class,
            EventLoop.class
    );

    private static final Collection<Class<?>> SECOND_STAGE = List.of(

    );

    private final Application application;

    @Override
    protected void configure() {
        bind(Logger.class).toInstance(application.getBoostrap().getLogger());
        bind(java.util.logging.Logger.class).toInstance(
                java.util.logging.Logger.getLogger(application.getBoostrap().getLogger().getName())
        );
        bind(ApplicationBoostrap.class).toInstance(application.getBoostrap());
        bind(Application.class).toInstance(application);
        bind(MCApi.class).toInstance(application);
        FIRST_STAGE.forEach(this::bind);
        SECOND_STAGE.forEach(this::bind);
    }
}
