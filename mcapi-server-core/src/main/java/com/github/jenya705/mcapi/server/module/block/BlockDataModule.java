package com.github.jenya705.mcapi.server.module.block;

import com.github.jenya705.mcapi.block.BlockData;

import java.util.function.BiConsumer;

/**
 * @author Jenya705
 */
public interface BlockDataModule {

    void setField(BlockData data, String field, String json);

    <T, E> void addFieldSetter(String field, Class<? extends T> dataClass, Class<? extends E> valueClass, BiConsumer<T, E> setter);

    default <T, E> BlockDataModule field(String field, Class<? extends T> dataClass, Class<? extends E> valueClass, BiConsumer<T, E> setter) {
        addFieldSetter(field, dataClass, valueClass, setter);
        return this;
    }

}
