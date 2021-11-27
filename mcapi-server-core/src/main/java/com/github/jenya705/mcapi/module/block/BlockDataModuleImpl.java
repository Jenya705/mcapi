package com.github.jenya705.mcapi.module.block;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.block.Liter;
import com.github.jenya705.mcapi.block.Waterlogged;
import com.github.jenya705.mcapi.error.FieldSetterNotExistException;
import com.github.jenya705.mcapi.module.mapper.Mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * @author Jenya705
 */
public class BlockDataModuleImpl extends AbstractApplicationModule implements BlockDataModule {

    private final Map<String, BiConsumer<BlockData, String>> fieldSetters = new HashMap<>();

    @Bean
    private Mapper mapper;

    public BlockDataModuleImpl() {
        this
                .field("waterlogged", Waterlogged.class, boolean.class, Waterlogged::setWaterlogged)
                .field("lit", Liter.class, boolean.class, Liter::setLit)
        ;
    }

    @Override
    public void setField(BlockData data, String field, String json) {
        Optional.ofNullable(fieldSetters.get(field))
                .orElseThrow(() -> FieldSetterNotExistException.create(field))
                .accept(data, json);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, E> void addFieldSetter(String field, Class<? extends T> dataClass, Class<? extends E> valueClass, BiConsumer<T, E> setter) {
        fieldSetters.put(field, (blockData, strValue) ->
                setter.accept((T) blockData, mapper.fromJson(strValue, valueClass))
        );
    }
}
