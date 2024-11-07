import java.io.*;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileSearchTool {

    private static final String FILE_EXTENSION = ".txt"; // Only search .txt files

    // Method to search for a keyword in a single file
    public static void searchKeywordInFile(Path filePath, String keyword) {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println("Found '" + keyword + "' in " + filePath + " at line " + lineNumber);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Could not read file: " + filePath + " due to " + e.getMessage());
        }
    }

    // Method to search all .txt files in a given directory
    public static void searchDirectory(Path directory, String keyword) {
        try {
            Files.walk(directory)
                .filter(Files::isRegularFile)
                .filter(file -> file.toString().endsWith(FILE_EXTENSION))
                .forEach(file -> searchKeywordInFile(file, keyword));
        } catch (IOException e) {
            System.out.println("Error while accessing directory: " + directory + " - " + e.getMessage());
        }
    }

    // Method to initialize multithreaded search for each subdirectory
    public static void threadedSearch(Path mainDirectory, String keyword) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            Files.walk(mainDirectory, 1)
                .filter(Files::isDirectory)
                .filter(dir -> !dir.equals(mainDirectory)) // Exclude main directory itself
                .forEach(subDir -> executorService.submit(() -> searchDirectory(subDir, keyword)));
        } catch (IOException e) {
            System.out.println("Error while setting up threads for directories: " + e.getMessage());
        } finally {
            executorService.shutdown();
        }
    }

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter the directory path to search in: ");
            String dirPath = reader.readLine();
            Path mainDirectory = Paths.get(dirPath);

            System.out.print("Enter the keyword to search for: ");
            String keyword = reader.readLine();

            if (Files.exists(mainDirectory) && Files.isDirectory(mainDirectory)) {
                System.out.println("Searching for '" + keyword + "' in directory: " + mainDirectory);
                threadedSearch(mainDirectory, keyword);
            } else {
                System.out.println("Invalid directory path: " + mainDirectory);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
