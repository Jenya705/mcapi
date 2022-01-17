package com.github.jenya705.mcapi.server.application;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which says that this method need to execute on initializing
 *
 * @author Jenya705
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnInitializing {

    int priority() default 2;
}
