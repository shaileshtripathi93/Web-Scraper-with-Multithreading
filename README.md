# 📂 File Search Tool with Multithreading in Java

This **File Search Tool** is a Java-based command-line application that leverages **multithreading** to perform efficient keyword searches within `.txt` files in a specified directory and its subdirectories. Designed for speed and ease of use, the tool splits directory processing across multiple threads, allowing faster search results—ideal for larger directories.

## 🚀 Features

- **Multithreaded Search**: Utilizes Java’s `ExecutorService` to manage multiple threads, enhancing search performance by processing subdirectories in parallel.
- **Precise Keyword Matching**: Searches for user-defined keywords within `.txt` files and provides results with file paths and line numbers.
- **Flexible and Scalable**: Easily handles large directories with many subfolders, thanks to concurrent processing.

## 🛠️ Requirements

- **Java 8** or higher

## 📥 Installation

1. **Clone the Repository**: Download or clone the repository to your local machine.
2. **Verify Java Installation**: Ensure Java is installed and configured. You can check this by running:
   ```bash
   java -version
