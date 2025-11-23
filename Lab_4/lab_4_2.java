package Lab_4;
import java.io.*;
import java.nio.file.*;

public class lab_4_2 {
    public static void main(String[] args) {
        System.out.println("\n=== Задание 2: Копирование файлов ===");

        copyFileWithHandling("existing_file.txt", "copy1.txt");
        copyFileWithHandling("nonexistent_file.txt", "copy2.txt");
        copyFileWithHandling("existing_file.txt", "/invalid/path/copy3.txt");
    }

    public static void copyFileWithHandling(String sourcePath, String destPath) {
        System.out.println("\nКопирование из '" + sourcePath + "' в '" + destPath + "':");

        try {
            File sourceFile = new File(sourcePath);
            if (!sourceFile.exists()) {
                throw new FileNotFoundException("Исходный файл не найден: " + sourcePath);
            }

            File destFile = new File(destPath);
            File parentDir = destFile.getParentFile();
            if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                throw new IOException("Не удалось создать директорию для целевого файла: " + parentDir.getPath());
            }

            Files.copy(Paths.get(sourcePath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✓ Файл успешно скопирован");

        } catch (FileNotFoundException e) {
            System.out.println("✗ Ошибка открытия файла: " + e.getMessage());
        } catch (AccessDeniedException e) {
            System.out.println("✗ Ошибка доступа: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("✗ Ошибка ввода-вывода: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("✗ Ошибка безопасности: " + e.getMessage());
        } finally {
            System.out.println("Завершение операции копирования");
        }
    }
}