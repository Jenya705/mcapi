package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface JavaBaseCommon extends BaseCommon {

    JavaServerApplication app();

    @Override
    default JavaServerCore core() {
        return (JavaServerCore) app().getCore();
    }
}
