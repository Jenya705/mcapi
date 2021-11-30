package com.github.jenya705.mcapi.block;

/**
 * @author Jenya705
 */
public interface Powerable extends BlockData {

    boolean isPowered();

    void setPowered(boolean powered);

}
