package com.sqe.framework.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CustomFileUtils {

    public static String readFileAsString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static List<String> readFileAsLines(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }

    public static void writeToFile(String filePath, String content) {
        try {
            Files.write(Paths.get(filePath),
                    content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(String sourcePath, String destinationPath) {
        try {
            FileUtils.copyFile(
                    new File(sourcePath),
                    new File(destinationPath)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    public static void deleteFile(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}