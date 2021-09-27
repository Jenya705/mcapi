package com.github.jenya705.mcapi.module.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypedMessageImpl implements TypedMessage {

    private String type;
    private Message message;
}
