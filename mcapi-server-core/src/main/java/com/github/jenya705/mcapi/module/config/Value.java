package com.github.jenya705.mcapi.module.config;

/**
 * @author Jenya705
 */
public @interface Value {

    boolean required() default true;

    String key() default "";

}
