package com.github.jenya705.mcapi.server.stringful;

import com.github.jenya705.mcapi.server.module.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public class StringfulListParser implements StringfulParser<List<Object>> {

    private final List<StringfulDataValueFunction<List<Object>>> dataValues;
    private final int requiredStart;

    public StringfulListParser(Mapper mapper, int requiredStart, Class<?>... types) {
        dataValues = new ArrayList<>();
        for (Class<?> type : types) {
            dataValues.add(generateFunction(
                    it -> mapper.fromRaw(it, type)
            ));
        }
        this.requiredStart = requiredStart;
    }

    public StringfulListParser(int requiredStart, List<StringfulDataValueFunction<List<Object>>> dataValues) {
        this.dataValues = dataValues;
        this.requiredStart = requiredStart;
    }

    @Override
    public StringfulParseResult<List<Object>> create(StringfulIterator stringfulIterator) {
        return StringfulParserImpl.apply(
                stringfulIterator,
                new ArrayList<>(),
                dataValues,
                requiredStart,
                null
        );
    }

    private StringfulDataValueFunction<List<Object>> generateFunction(Function<String, Object> function) {
        return (data, value) -> data.add(function.apply(value));
    }
}
