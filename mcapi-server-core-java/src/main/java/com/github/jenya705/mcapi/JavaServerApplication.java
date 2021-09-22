package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.form.JavaFormProvider;
import lombok.Getter;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class JavaServerApplication extends ServerApplication {

    public JavaServerApplication() {
        super();
        addClasses(
                JavaFormProvider.class
        );
        setPlatform(ServerPlatform.JAVA);
    }

}
