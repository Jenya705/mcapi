package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.rest.PlayerSendComponentRest;
import lombok.Getter;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class JavaServerApplication {

    @Delegate
    protected ServerApplication getDefaultApplication() {
        return ServerApplication.getApplication();
    }

    private JavaServerApplication() {
        addClasses(
                PlayerSendComponentRest.class
        );
    }

    @Getter
    private final static JavaServerApplication application = new JavaServerApplication();

}
