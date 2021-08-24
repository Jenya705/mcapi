package com.github.jenya705.mcapi.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jenya705
 */
@UtilityClass
public class FileUtils {

    public byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        return bytes;
    }

}
