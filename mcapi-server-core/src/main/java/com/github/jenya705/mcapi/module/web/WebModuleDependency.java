package com.github.jenya705.mcapi.module.web;

import com.github.jenya705.mcapi.ServerModule;
import com.github.jenya705.mcapi.module.ServerModuleDependency;

/**
 * @author Jenya705
 */
@ServerModuleDependency("webserver")
public interface WebModuleDependency extends ServerModule {

    default void addClass(Class<?> clazz) {
        getApplication().getWebModule().getClasses().add(clazz);
    }

    default WebModule web() {
        return getApplication().getWebModule();
    }


}
