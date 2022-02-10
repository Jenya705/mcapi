package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.data.CommandBlock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCommandBlock {

    private RestBlock block;
    private String command;
    private String direction;

    public static RestCommandBlock from(CommandBlock commandBlock) {
        return new RestCommandBlock(
                RestBlock.from(commandBlock.getBlock()),
                commandBlock.getCommand(),
                commandBlock.getDirection().name()
        );
    }

}
