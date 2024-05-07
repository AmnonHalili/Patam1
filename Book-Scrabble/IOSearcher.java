package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOSearcher {

    public static boolean search(String word, String... fileNames) {
        for (String fileName : fileNames) {
            if (loadWordsFromFile(fileName).contains(word)) {
                return true;
            } 
        }
        return false;
    }

    public static List<String> loadWordsFromFile(String fileName) {
        List<String> words = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.trim().split("\\s+"); // Split the line into words
                for (String word : lineWords) {
                    words.add(word);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName + ". " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error closing file: " + fileName + ". " + e.getMessage());
                }
            }
        }
        return words;
    }
}