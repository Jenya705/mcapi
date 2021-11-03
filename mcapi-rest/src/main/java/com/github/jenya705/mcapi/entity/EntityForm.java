package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.Form;
import com.github.jenya705.mcapi.rest.RestForm;
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
public class EntityForm implements Form {

    private Map<String, Object>[] components;

    public RestForm rest() {
        return RestForm.from(this);
    }
}
