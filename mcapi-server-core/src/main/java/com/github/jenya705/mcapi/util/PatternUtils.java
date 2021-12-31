package com.github.jenya705.mcapi.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jenya705
 */
@UtilityClass
public class PatternUtils {

    public boolean validateAllString(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() && matcher.end() == str.length() && matcher.start() == 0;
    }

}
