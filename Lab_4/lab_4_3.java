package Lab_4;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class CustomEmailFormatException extends Exception {
    private String invalidEmail;

    public CustomEmailFormatException(String message) {
        super(message);
    }

    public CustomEmailFormatException(String message, String invalidEmail) {
        super(message);
        this.invalidEmail = invalidEmail;
    }

    public String getInvalidEmail() {
        return invalidEmail;
    }

    @Override
    public String toString() {
        if (invalidEmail != null) {
            return "CustomEmailFormatException: " + getMessage() + " [Invalid email: " + invalidEmail + "]";
        }
        return "CustomEmailFormatException: " + getMessage();
    }
}

class ExceptionLogger {
    private static final String LOG_FILE = "exceptions.log";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logException(Exception exception) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.println("[" + timestamp + "] " + exception.getClass().getSimpleName());
            writer.println("   Message: " + exception.getMessage());

            // Добавляем дополнительную информацию для CustomEmailFormatException
            if (exception instanceof CustomEmailFormatException) {
                String invalidEmail = ((CustomEmailFormatException) exception).getInvalidEmail();
                if (invalidEmail != null) {
                    writer.println("   Invalid Email: " + invalidEmail);
                }
            }

            writer.println("   Stack Trace:");
            for (StackTraceElement element : exception.getStackTrace()) {
                if (element.getClassName().contains("ExceptionDemo")) {
                    writer.println("      " + element.toString());
                }
            }
            writer.println("---");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог-файл: " + e.getMessage());
        }
    }
}

class EmailValidator {

    public static void validateEmail(String email) throws CustomEmailFormatException {
        if (email == null || email.trim().isEmpty()) {
            throw new CustomEmailFormatException("Email адрес не может быть пустым", email);
        }

        // Базовая проверка формата email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!email.matches(emailRegex)) {
            throw new CustomEmailFormatException(
                    "Неверный формат email адреса. Email должен содержать @ и домен",
                    email
            );
        }

        if (email.startsWith("@") || email.endsWith("@")) {
            throw new CustomEmailFormatException("Email не может начинаться или заканчиваться символом @", email);
        }

        if (email.contains("..")) {
            throw new CustomEmailFormatException("Email не может содержать две точки подряд", email);
        }

        String[] parts = email.split("@");
        if (parts.length != 2) {
            throw new CustomEmailFormatException("Email должен содержать ровно один символ @", email);
        }

        String domain = parts[1];
        if (!domain.contains(".")) {
            throw new CustomEmailFormatException("Домен email должен содержать точку", email);
        }

        if (domain.startsWith(".") || domain.endsWith(".")) {
            throw new CustomEmailFormatException("Домен не может начинаться или заканчиваться точкой", email);
        }
    }
}

public class lab_4_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Валидация Email адресов ===");
        System.out.println("Введите 'exit' для завершения программы");
        System.out.println("Введите 'demo' для запуска демонстрации");

        while (true) {
            System.out.print("\nВведите email адрес для проверки: ");
            String email = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(email)) {
                break;
            } else if ("demo".equalsIgnoreCase(email)) {
                demonstrateEmailValidation();
                continue;
            }

            try {
                EmailValidator.validateEmail(email);
                System.out.println("✓ Email адрес '" + email + "' корректен!");

            } catch (CustomEmailFormatException e) {
                System.out.println("✗ Ошибка: " + e.getMessage());
                ExceptionLogger.logException(e);
                System.out.println("   Информация об ошибке записана в лог-файл");
            }
        }

        scanner.close();
        System.out.println("\nПрограмма завершена. Проверьте файл exceptions.log для просмотра деталей ошибок.");
    }

    public static void demonstrateEmailValidation() {
        System.out.println("\n=== Демонстрация валидации email ===");

        String[] testEmails = {
                "user@example.com",           // валидный
                "invalid-email",              // невалидный
                "user@domain",                // невалидный (нет точки в домене)
                "@domain.com",                // невалидный (начинается с @)
                "user@.com",                  // невалидный (домен начинается с точки)
                "user@domain..com",           // невалидный (две точки подряд)
                "",                           // невалидный (пустая строка)
                "user.name@domain.co.uk",     // валидный
                "test.test@test.test.ru",               // валидный
                "hello@world"                 // невалидный
        };

        for (String email : testEmails) {
            try {
                EmailValidator.validateEmail(email);
                System.out.println("✓ " + email + " - ВАЛИДНЫЙ");
            } catch (CustomEmailFormatException e) {
                System.out.println("✗ " + email + " - НЕВАЛИДНЫЙ: " + e.getMessage());
                ExceptionLogger.logException(e);
            }
        }
    }
}