package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.Form;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestForm {

    private Map<String, Object>[] components;

    public static RestForm from(Form form) {
        return new RestForm(
                form.getComponents()
        );
    }
}
