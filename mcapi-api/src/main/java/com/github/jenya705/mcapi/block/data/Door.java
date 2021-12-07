package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.block.*;

/**
 * @author Jenya705
 */
public interface Door extends BlockData, Openable, Powerable, Bisected, Directional {

    enum Hinge {
        LEFT,
        RIGHT
    }

    Hinge getHinge();

    void setHinge(Hinge hinge);

}
