package dev.mcapi;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.Builder;
import lombok.Getter;

@Singleton
public class Application implements MCApi {

    @Getter
    @Builder(buildMethodName = "buildArguments")
    private static class Arguments {

        private final ApplicationBoostrap boostrap;

        private static class ArgumentsBuilder {
            public Application build() {
                return new Application(buildArguments());
            }
        }
    }

    public static Arguments.ArgumentsBuilder builder() {
        return Arguments.builder();
    }

    @Getter
    private final ApplicationBoostrap boostrap;

    private final Injector injector;

    private Application(Arguments arguments) {
        boostrap = arguments.getBoostrap();
        injector = Guice.createInjector(new ApplicationBinder(this));
    }

}
