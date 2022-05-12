package com.github.jenya705.mcapi.server.ss.model;

import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(ProxyModelMapperImpl.class)
public interface ProxyModelMapper {

    void addModel(String model, Class<?> modelClass);

    Class<?> getModelClass(String model);

}
