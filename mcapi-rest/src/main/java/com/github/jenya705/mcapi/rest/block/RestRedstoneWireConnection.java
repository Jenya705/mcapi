package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.BlockFace;
import com.github.jenya705.mcapi.block.data.RedstoneWire;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestRedstoneWireConnection {

    private String direction;
    private String connection;

    public static RestRedstoneWireConnection from(BlockFace face, RedstoneWire.Connection connection) {
        return new RestRedstoneWireConnection(
                face.name(),
                connection.name()
        );
    }

}
