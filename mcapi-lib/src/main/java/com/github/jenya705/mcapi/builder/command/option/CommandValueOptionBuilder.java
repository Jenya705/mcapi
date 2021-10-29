package com.github.jenya705.mcapi.builder.command.option;

import com.github.jenya705.mcapi.builder.Buildable;
import com.github.jenya705.mcapi.builder.command.option.value.BooleanOptionBuilder;
import com.github.jenya705.mcapi.builder.command.option.value.IntegerOptionBuilder;
import com.github.jenya705.mcapi.builder.command.option.value.PlayerOptionBuilder;
import com.github.jenya705.mcapi.builder.command.option.value.StringOptionBuilder;
import com.github.jenya705.mcapi.command.CommandValueOption;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author Jenya705
 */
@Getter(AccessLevel.PROTECTED)
public abstract class CommandValueOptionBuilder
        <E extends CommandValueOptionBuilder<E, T>, T extends CommandValueOption> implements Buildable<T> {

    public static StringOptionBuilder string() {
        return new StringOptionBuilder();
    }

    public static BooleanOptionBuilder bool() {
        return new BooleanOptionBuilder();
    }

    public static IntegerOptionBuilder integer() {
        return new IntegerOptionBuilder();
    }

    public static PlayerOptionBuilder player() {
        return new PlayerOptionBuilder();
    }

    private String name;
    private boolean required;
    private boolean onlyFromTab;
    private Object tab;

    @SuppressWarnings("unchecked")
    public E name(String name) {
        this.name = name;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E required() {
        this.required = true;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E onlyFromTab() {
        this.onlyFromTab = true;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E suggestion(String suggestion) {
        if (tab instanceof String[]) {
            String[] suggestions = (String[]) tab;
            tab = Arrays.copyOf(suggestions, suggestions.length + 1);
        }
        else {
            tab = new String[1];
        }
        String[] suggestions = (String[]) tab;
        suggestions[suggestions.length - 1] = suggestion;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E suggestions(String... suggestions) {
        tab = suggestions;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E tabFunction(String function) {
        tab = function;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E validate() {
        if (name == null) {
            throw new IllegalStateException("Name is not set");
        }
        return (E) this;
    }

}
