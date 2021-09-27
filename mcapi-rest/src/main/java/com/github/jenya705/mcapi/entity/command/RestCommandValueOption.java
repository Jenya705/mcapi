package com.github.jenya705.mcapi.entity.command;

import com.github.jenya705.mcapi.command.ApiCommandValueOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCommandValueOption {

    private String name;
    private boolean required;
    private boolean onlyFromTab;
    private String tabFunction = "";
    private String[] suggestions = {};

    public static RestCommandValueOption from(ApiCommandValueOption option) {
        return new RestCommandValueOption(
                option.getName(),
                option.isRequired(),
                option.isOnlyFromTab(),
                option.getTabFunction(),
                option.getSuggestions()
        );
    }
}
