package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public abstract class AbstractJavaApplicationModule implements JavaBaseCommon {

    @Bean
    private JavaServerApplication application;

    public AbstractJavaApplicationModule() { }

    public AbstractJavaApplicationModule(JavaServerApplication application) {
        this.application = application;
    }

    @Override
    public JavaServerApplication app() {
        return application;
    }
}
