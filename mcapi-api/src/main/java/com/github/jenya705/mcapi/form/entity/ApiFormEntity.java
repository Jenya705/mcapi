package com.github.jenya705.mcapi.form.entity;

import com.github.jenya705.mcapi.form.ApiForm;
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
public class ApiFormEntity implements ApiForm {

    private Map<String, Object>[] components;
}
