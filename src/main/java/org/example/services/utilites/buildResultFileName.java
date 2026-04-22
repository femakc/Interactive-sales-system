package org.example.services.utilites;

import org.example.AppConfig;

public class buildResultFileName {

    public static String filePath(String originalPath) {

        String prefix = AppConfig.get("result.file.prefix");

        int dotIndex = originalPath.lastIndexOf(".");

        if (dotIndex == -1) {
            return originalPath + prefix;
        }

        String name = originalPath.substring(0, dotIndex);
        String extension = originalPath.substring(dotIndex);

        return name + prefix + extension;
    }
}
