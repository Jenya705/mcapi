package com.github.jenya705.mcapi.stringful;

import com.github.jenya705.mcapi.module.mapper.Mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public class StringfulParserImpl<T> implements StringfulParser<T> {

    private final Class<? extends T> dataClass;
    private final Mapper mapper;
    private final List<StringfulDataValueFunction<T>> dataValues = new ArrayList<>();
    private Constructor<? extends T> dataConstructor;
    private BiConsumer<Object, String[]> othersFunction;
    private int requiredStart = -1;

    public StringfulParserImpl(Class<? extends T> dataClass, Mapper mapper) throws Exception {
        this.dataClass = dataClass;
        this.mapper = mapper;
        regenerate();
    }

    protected void regenerate() throws Exception {
        dataConstructor = dataClass.getConstructor();
        dataValues.clear();
        for (Field field : dataClass.getDeclaredFields()) {
            generateForReflectionObject(new StringfulReflectionField(field));
        }
        for (Method method : dataClass.getMethods()) {
            generateForReflectionObject(new StringfulReflectionMethod(method));
        }
    }

    protected void generateForReflectionObject(StringfulReflectionObject reflectionObject) {
        Others othersAnnotation = reflectionObject.getAnnotation(Others.class);
        if (othersAnnotation != null) {
            generateForOthersObject(reflectionObject);
            return;
        }
        Argument argumentAnnotation = reflectionObject.getAnnotation(Argument.class);
        Index indexAnnotation = reflectionObject.getAnnotation(Index.class);
        if (indexAnnotation == null) return;
        boolean required = argumentAnnotation == null || argumentAnnotation.required();
        if (!required) {
            requiredStart = requiredStart == -1 ?
                    indexAnnotation.value() :
                    Math.min(indexAnnotation.value(), requiredStart);
        }
        setDataValue(indexAnnotation.value(), generateReflectionObjectFunction(reflectionObject));
    }

    protected void generateForOthersObject(StringfulReflectionObject reflectionObject) {
        if (reflectionObject.getType() == String[].class) {
            othersFunction = reflectionObject::invoke;
        }
        else if (Collection.class.isAssignableFrom(reflectionObject.getType())) {
            othersFunction = (data, array) -> reflectionObject.invoke(data, Arrays.asList(array));
        }
        else {
            throw new IllegalArgumentException(
                    "This reflection object's type need to be string[] or collection of strings");
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
                    requiredStart,
                    othersFunction
            );
        } catch (Exception e) {
            return new StringfulParseResultImpl<>(
                    null, new StringfulParseErrorImpl(e, 0)
            );
        }
    }

    public static <T> StringfulParseResult<T> apply(StringfulIterator stringfulIterator,
                                                    T generatedData,
                                                    List<StringfulDataValueFunction<T>> dataValues,
                                                    int requiredStart,
                                                    BiConsumer<Object, String[]> othersFunction) {
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
            if (othersFunction != null) {
                othersFunction.accept(generatedData, stringfulIterator.allNext());
            }
            return new StringfulParseResultImpl<>(generatedData, null);
        } catch (Exception e) {
            return new StringfulParseResultImpl<>(
                    null, new StringfulParseErrorImpl(e, currentArgument)
            );
        }
    }

    protected StringfulDataValueFunction<T> generateReflectionObjectFunction(StringfulReflectionObject reflectionObject) {
        return (data, value) ->
                reflectionObject.invoke(data, mapper.fromRaw(value, reflectionObject.getType()));
    }

    protected StringfulDataValueFunction<T> generateFieldFunction(Field field) {
        return (data, value) ->
                field.set(data, mapper.fromRaw(value, field.getType()));
    }

    protected StringfulDataValueFunction<T> generateMethodFunction(Method method) {
        return (data, value) ->
                method.invoke(data, mapper.fromRaw(value, method.getParameterTypes()[0]));
    }
}
