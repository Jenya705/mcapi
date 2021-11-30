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
public class RestConnection {

    private String direction;
    private String connection;

    public static RestConnection from(BlockFace face, RedstoneWire.Connection connection) {
        return new RestConnection(
                face.name(),
                connection.name()
        );
    }

}
