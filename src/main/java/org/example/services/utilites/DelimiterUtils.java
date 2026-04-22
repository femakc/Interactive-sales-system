package org.example.services.utilites;

public class DelimiterUtils {
    public static String getDelimiter(String line) {
        if (line.contains("|")) return "\\|";
        if (line.contains("#")) return "#";
        throw new IllegalArgumentException("Unknown delimiter in line: " + line);
    }
}
