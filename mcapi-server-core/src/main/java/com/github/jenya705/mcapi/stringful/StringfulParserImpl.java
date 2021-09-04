package com.github.jenya705.mcapi.stringful;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public class StringfulParserImpl<T> implements StringfulParser<T> {

    public static final Map<Class<?>, Function<String, Object>> typeParsers = new HashMap<>();

    static {
        typeParsers.put(String.class, value -> value);
        // Integers
        typeParsers.put(byte.class, Byte::parseByte);
        typeParsers.put(short.class, Short::parseShort);
        typeParsers.put(int.class, Integer::parseInt);
        typeParsers.put(long.class, Long::parseLong);
        // Floats
        typeParsers.put(float.class, Float::parseFloat);
        typeParsers.put(double.class, Double::parseDouble);
        // Boolean
        typeParsers.put(boolean.class, Boolean::parseBoolean);
    }

    private final Class<? extends T> dataClass;

    private final List<StringfulDataValueFunction<T>> dataValues = new ArrayList<>();
    private Constructor<? extends T> dataConstructor;
    private int requiredStart = -1;

    public StringfulParserImpl(Class<? extends T> dataClass) throws Exception {
        this.dataClass = dataClass;
        regenerate();
    }

    protected void regenerate() throws Exception {
        dataConstructor = dataClass.getConstructor();
        dataValues.clear();
        for (Field field : dataClass.getDeclaredFields()) {
            Argument argumentAnnotation = field.getAnnotation(Argument.class);
            Index indexAnnotation = field.getAnnotation(Index.class);
            if (indexAnnotation == null) continue;
            boolean required = argumentAnnotation == null || argumentAnnotation.required();
            field.setAccessible(true);
            if (!required) {
                requiredStart = requiredStart == -1 ?
                        indexAnnotation.value() :
                        Math.min(indexAnnotation.value(), requiredStart);
            }
            setDataValue(indexAnnotation.value(), generateFieldFunction(field));
        }
        for (Method method : dataClass.getMethods()) {
            Argument argumentAnnotation = method.getAnnotation(Argument.class);
            Index indexAnnotation = method.getAnnotation(Index.class);
            if (indexAnnotation == null || method.getParameterTypes().length != 1) continue;
            boolean required = argumentAnnotation == null || argumentAnnotation.required();
            if (!required) {
                requiredStart = requiredStart == -1 ?
                        indexAnnotation.value() :
                        Math.min(indexAnnotation.value(), requiredStart);
            }
            setDataValue(indexAnnotation.value(), generateMethodFunction(method));
        }
    }

    protected void setDataValue(int index, StringfulDataValueFunction<T> function) {
        for (int i = dataValues.size(); i <= index; ++i) {
            dataValues.add(null);
        }
        dataValues.set(index, function);
    }

    @Override
    public StringfulParseResult<T> create(StringfulIterator stringfulIterator) {
        try {
            return apply(
                    stringfulIterator,
                    dataConstructor.newInstance(),
                    dataValues,
                    requiredStart
            );
        } catch (Exception e) {
            return new StringfulParseResultImpl<>(
                    null, new StringfulParseErrorImpl(e, 0)
            );
        }
    }

    public static <T> StringfulParseResult<T> apply(StringfulIterator stringfulIterator, T generatedData, List<StringfulDataValueFunction<T>> dataValues, int requiredStart) {
        int currentArgument = 0;
        try {
            if (!stringfulIterator.hasNext(requiredStart)) {
                return new StringfulParseResultImpl<>(
                        null, new StringfulParseErrorImpl(null, -1)
                );
            }
            for (int i = 0; i < requiredStart; ++i) {
                currentArgument++;
                dataValues.get(i).accept(generatedData, stringfulIterator.next());
            }
            for (int i = requiredStart; i < dataValues.size(); ++i) {
                if (!stringfulIterator.hasNext()) break;
                currentArgument++;
                dataValues.get(i).accept(generatedData, stringfulIterator.next());
            }
            return new StringfulParseResultImpl<>(generatedData, null);
        } catch (Exception e) {
            return new StringfulParseResultImpl<>(
                    null, new StringfulParseErrorImpl(e, currentArgument)
            );
        }
    }

    protected StringfulDataValueFunction<T> generateFieldFunction(Field field) {
        return (data, value) ->
                field.set(data, typeParsers.get(field.getType()).apply(value));
    }

    protected StringfulDataValueFunction<T> generateMethodFunction(Method method) {
        return (data, value) ->
                method.invoke(typeParsers.get(method.getParameterTypes()[0]).apply(value));
    }
}
