package com.github.jenya705.mcapi.entity.command;

import com.github.jenya705.mcapi.command.ApiCommandOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCommandOption {

    private String name;
    private String type;

    public static RestCommandOption from(ApiCommandOption option) {
        return new RestCommandOption(
                option.getName(),
                option.getType()
        );
    }
}
