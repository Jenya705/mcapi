package com.github.jenya705.mcapi.module.block;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.block.*;
import com.github.jenya705.mcapi.block.data.*;
import com.github.jenya705.mcapi.error.FieldSetterNotExistException;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.inventory.InventoryItemStack;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.rest.block.RestRedstoneWireConnection;

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
                .field("open", Openable.class, boolean.class, Openable::setOpen)
                .field("hinge", Door.class, Door.Hinge.class, Door::setHinge)
                .field("extended", Piston.class, boolean.class, Piston::setExtended)
                .field("connection", RedstoneWire.class, RestRedstoneWireConnection.class, (wire, connection) ->
                        wire.setConnection(
                                BlockFace.valueOf(connection.getDirection().toUpperCase(Locale.ROOT)),
                                RedstoneWire.Connection.valueOf(connection.getConnection().toUpperCase(Locale.ROOT))
                        )
                )
                .field("item", InventoryHolder.class, InventoryItemStack.class, (inventoryHolder, itemStack) ->
                        inventoryHolder
                                .getInventory()
                                .setItem(itemStack.getIndex(), itemStack)
                )
        ;
    }

    @Override
    public void setField(BlockData data, String field, String json) {
        Optional.ofNullable(fieldSetters.get(field.toLowerCase(Locale.ROOT)))
                .orElseThrow(() -> FieldSetterNotExistException.create(field))
                .accept(data, json);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, E> void addFieldSetter(String field, Class<? extends T> dataClass, Class<? extends E> valueClass, BiConsumer<T, E> setter) {
        fieldSetters.put(field.toLowerCase(Locale.ROOT), (blockData, strValue) ->
                setter.accept((T) blockData, mapper.fromJson(strValue, valueClass))
        );
    }
}
