package com.github.jenya705.mcapi.module.block;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.block.*;
import com.github.jenya705.mcapi.block.data.RedstoneWire;
import com.github.jenya705.mcapi.block.data.Slab;
import com.github.jenya705.mcapi.block.data.Stairs;
import com.github.jenya705.mcapi.error.FieldSetterNotExistException;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.rest.block.RestConnection;

import java.util.HashMap;
import java.util.Locale;
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
                .field("facing", Directional.class, BlockFace.class, Directional::setDirection)
                .field("half", Bisected.class, Half.class, Bisected::setHalf)
                .field("slabType", Slab.class, Slab.SlabType.class, Slab::setType)
                .field("stairsShape", Stairs.class, Shape.class, Stairs::setShape)
                .field("power", Powerable.class, boolean.class, Powerable::setPowered)
                .field("powerLevel", LevelPowerable.class, int.class, LevelPowerable::setPower)
                .field("connection", RedstoneWire.class, RestConnection.class, (wire, connection) ->
                        wire.setConnection(
                                BlockFace.valueOf(connection.getDirection().toUpperCase(Locale.ROOT)),
                                RedstoneWire.Connection.valueOf(connection.getConnection().toUpperCase(Locale.ROOT))
                        )
                )
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
