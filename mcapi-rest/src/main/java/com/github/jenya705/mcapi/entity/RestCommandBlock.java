package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.block.CommandBlock;
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

    private String command;

    public static RestCommandBlock from(CommandBlock commandBlock) {
        return new RestCommandBlock(
                commandBlock.getCommand()
        );
    }

}
