package com.github.jenya705.mcapi.block;

/**
 * @author Jenya705
 */
public interface Openable extends BlockData {

    boolean isOpen();

    void setOpen(boolean opened);

}
