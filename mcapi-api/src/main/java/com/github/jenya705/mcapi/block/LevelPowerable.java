package com.github.jenya705.mcapi.block;

/**
 * @author Jenya705
 */
public interface LevelPowerable extends BlockData {

    int getPower();

    void setPower(int power);

    int getMaxPower();

}
