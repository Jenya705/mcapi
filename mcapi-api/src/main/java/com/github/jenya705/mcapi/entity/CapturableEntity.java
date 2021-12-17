package com.github.jenya705.mcapi.entity;

/**
 * @author Jenya705
 */
public interface CapturableEntity extends Entity {

    void setOwner(int id);

    int getOwner();

}
