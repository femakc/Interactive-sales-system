package org.example.services.utilites;

import java.util.Objects;

public class DelimiterUtils {
    public static String getDelimiter(String excitation) {
        if (Objects.equals(excitation, "txt")) {
            return "\\|";
        }
        if (excitation.isEmpty()) {
            return "#";
        }
        throw new IllegalArgumentException("Unknown excitation of file: " + excitation);
    }
}
