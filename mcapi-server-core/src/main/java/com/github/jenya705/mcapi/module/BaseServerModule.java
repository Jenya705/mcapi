package com.github.jenya705.mcapi.module;

import com.github.jenya705.mcapi.BaseServerCommon;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.ServerModule;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
public abstract class BaseServerModule extends BaseServerCommon implements ServerModule {

    private final ServerApplication application;

    public BaseServerModule(ServerApplication application) {
        this.application = application;
    }

}
