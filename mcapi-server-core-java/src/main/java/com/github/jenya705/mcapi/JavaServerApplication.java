package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.form.JavaFormProvider;

/**
 * @author Jenya705
 */
public class JavaServerApplication extends ServerApplication {

    public JavaServerApplication() {
        super();
        addClasses(
                JavaFormProvider.class,
                JavaComponentMessageProvider.class,
                JavaRestModule.class
        );
        setPlatform(ServerPlatform.JAVA);
    }
}
