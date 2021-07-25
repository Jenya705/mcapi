package com.github.jenya705.mcapi.command;

import java.util.Map;

/**
 * @since 1.0
 * @author Jenya705
 */
public interface ApiServerCommandFactory {

    ApiServerSubCommandContainer createSubContainer(Map<String, ApiServerCommandExecutor> subs);

    ApiServerCommandExecutor createCommandInstance(Class<? extends ApiServerCommandExecutor> clazz);

}
