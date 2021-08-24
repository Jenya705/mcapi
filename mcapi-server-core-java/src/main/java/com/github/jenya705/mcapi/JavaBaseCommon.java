package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface JavaBaseCommon extends BaseCommon {

    default JavaServerApplication java() {
        return JavaServerApplication.getApplication();
    }

    @Override
    default <T> T bean(Class<? extends T> clazz) {
        return java().getBean(clazz);
    }

    @Override
    default JavaServerCore core() {
        return (JavaServerCore) java().getCore();
    }
}
