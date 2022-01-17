package com.github.jenya705.mcapi.rest.block;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String command;
    private String direction;

    public static RestCommandBlock from(CommandBlock commandBlock) {
        return new RestCommandBlock(
                commandBlock.getCommand(),
                commandBlock.getDirection().name()
        );
    }

}
