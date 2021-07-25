package com.github.jenya705.mcapi.command;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @since 1.0
 * @author Jenya705
 */
public class JavaServerCommandFactory implements ApiServerCommandFactory {

    private static final Map<Class<? extends ApiServerCommandExecutor>, Supplier<ApiServerCommandExecutor>> commandInitializers = Map.of(
            ApiServerCreateTokenCommand.class, JavaServerCreateTokenCommand::new
    );

    @Override
    public ApiServerSubCommandContainer createSubContainer(Map<String, ApiServerCommandExecutor> subs) {
        return new JavaServerSubCommandContainer(subs);
    }

    @Override
    public ApiServerCommandExecutor createCommandInstance(Class<? extends ApiServerCommandExecutor> clazz) {
        return commandInitializers.get(clazz).get();
    }
}
