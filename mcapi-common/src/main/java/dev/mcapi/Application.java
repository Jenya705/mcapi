package dev.mcapi;

import com.google.inject.*;
import dev.mcapi.config.ConfigType;
import lombok.Getter;
import org.slf4j.Logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Path;

@Getter
public class Application implements AutoCloseable {

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @BindingAnnotation
    public @interface DataPath {
    }

    private final ApplicationBootstrap bootstrap;
    private final Injector injector;

    public Application(ApplicationBootstrap bootstrap) {
        this.bootstrap = bootstrap;
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Logger.class).toInstance(bootstrap.getLogger());
                bind(ClassLoader.class).toInstance(bootstrap.getClassLoader());
                bind(Path.class).annotatedWith(DataPath.class).toInstance(bootstrap.getDataPath());
                bind(ApplicationBootstrap.class).toInstance(bootstrap);
                bind(ConfigType.class).toInstance(bootstrap.getConfigType());
                bind(Application.class).toInstance(Application.this);
            }
        });
    }

    @Override
    public void close() {
        for (Binding<?> binding: injector.getAllBindings().values()) {
            try {
                Object obj = binding.getProvider().get();
                if (obj != this && obj instanceof AutoCloseable) {
                    ((AutoCloseable) obj).close();
                }
            } catch (Exception e) {
                bootstrap.getLogger().error("Failed to close a binding:", e);
            }
        }
    }
}
