package com.github.jenya705.mcapi.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
@Data
@AllArgsConstructor
public class ComponentForm implements Form {

    private final Component message;
}
