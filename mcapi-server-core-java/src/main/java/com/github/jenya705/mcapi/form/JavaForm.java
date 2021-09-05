package com.github.jenya705.mcapi.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class JavaForm implements Form {

    private final Component message;
}
