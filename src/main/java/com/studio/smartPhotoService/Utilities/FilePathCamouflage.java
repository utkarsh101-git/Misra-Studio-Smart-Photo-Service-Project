package com.studio.smartPhotoService.Utilities;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FilePathCamouflage {

    private static final String[] ADJECTIVES = {
            "Funky", "Crazy", "Weird", "Wild", "Sneaky", "Curious", "Dizzy", "Electric", "Zippy"
    };

    private static final String[] NOUNS = {
            "Penguin", "Unicorn", "Dragon", "Falcon", "Cheetah", "Robot", "Zebra", "Phoenix", "Owl"
    };

    // Method to generate random funky name
    private static String generateFunkyName() {
        Random random = new Random();
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[random.nextInt(NOUNS.length)];
        int number = random.nextInt(1000); // Adding a random number for uniqueness
        return adjective + noun + number;
    }

    // Method to camouflage file paths (without loading files)
    public static List<String> camouflageFilePaths(List<String> filePaths) {
        return filePaths.stream().map(FilePathCamouflage::camouflageFilePath).collect(Collectors.toList());
    }

    private static String camouflageFilePath(String filePath) {
        String fileName = filePath;

        // Extract the file extension if present
        String extension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            extension = fileName.substring(dotIndex); // e.g., ".jpg"
            fileName = fileName.substring(0, dotIndex); // Remove extension for name generation
        }

        // Generate funky name and re-append the extension
        String newFileName = generateFunkyName() + extension;

        // Replace the original file name with the funky one while maintaining directory structure
        int lastSlashIndex = filePath.lastIndexOf('/');
        if (lastSlashIndex != -1) {
            String path = filePath.substring(0, lastSlashIndex + 1);
            return path + newFileName;
        } else {
            return newFileName; // No directory structure
        }
    }

    public static String generateRandomFileName(Long weddingMemberId, String weddingMemberName) {
        Random random = new Random();
        int randomNumber = random.nextInt(100000); // Random 5-digit number
        return weddingMemberId.toString() + "_" + weddingMemberName + System.currentTimeMillis() + "_" + randomNumber;
    }

    public static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }


}
