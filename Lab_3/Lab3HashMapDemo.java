package Lab_3;

import java.util.HashMap;
import java.util.Map;

public class Lab3HashMapDemo {
    public static void main(String[] args) {
        // Используем номер зачетной книжки (String) в качестве ключа
        Map<String, Student> students = new HashMap<>();

        // Вставка студентов (put)
        students.put("2023-001", new Student("Иван", "Иванов", 20, 4.50));
        students.put("2023-002", new Student("Мария", "Петрова", 21, 4.75));
        students.put("2023-003", new Student("Алексей", "Сидоров", 19, 3.90));

        System.out.println("=== Все студенты (после вставки) ===");
        students.forEach((k,v) -> System.out.println(k + " -> " + v));

        // Поиск студента по номеру зачетки (get)
        String searchKey = "2023-002";
        Student found = students.get(searchKey);
        System.out.println("\nПоиск по номеру " + searchKey + ":");
        System.out.println(found != null ? found : "Студент не найден.");

        // Удаление студента (remove)
        String removeKey = "2023-001";
        Student removed = students.remove(removeKey);
        System.out.println("\nУдаление номера " + removeKey + ": " + (removed != null ? "удален: " + removed : "не найден"));

        System.out.println("\n=== Результат после удаления ===");
        students.forEach((k,v) -> System.out.println(k + " -> " + v));

        // Демонстрация использования собственной HashTable (optional)
        System.out.println("\n=== Демонстрация собственной HashTable ===");
        HashTable<String, Student> myTable = new HashTable<>();
        myTable.put("2023-002", new Student("Мария", "Петрова", 21, 4.75));
        myTable.put("2023-003", new Student("Алексей", "Сидоров", 19, 3.90));

        System.out.println("myTable.get(2023-002) -> " + myTable.get("2023-002"));
        System.out.println("myTable.size() -> " + myTable.size());
        myTable.remove("2023-003");
        System.out.println("after remove, myTable.size() -> " + myTable.size());
    }
}