package dev.mcapi;

import org.slf4j.Logger;

import java.nio.file.Path;

public interface ApplicationBoostrap {

    /**
     * Returns path where all mcapi files should be located (data directory)
     *
     * @return Path
     */
    Path getPath();

    /**
     * Returns logger which will be used by mcapi modules
     *
     * @return Logger
     */
    Logger getLogger();

}
