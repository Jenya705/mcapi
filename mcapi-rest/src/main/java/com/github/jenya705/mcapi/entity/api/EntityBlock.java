package com.github.jenya705.mcapi.entity.api;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.entity.RestBlock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityBlock implements Block {

    private Location location;
    private Material material;
    private BlockData blockData;

    public RestBlock rest() {
        return RestBlock.from(this);
    }

}
