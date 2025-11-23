package Lab_6;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TopWords {
    public static void main(String[] args) {
        String filePath = "text.txt"; // Укажите путь к вашему файлу

        Map<String, Integer> wordCount = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter("[^a-zA-Zа-яА-Я0-9]+");

            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                if (!word.isEmpty()) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
            return;
        }

        // Создаем список из элементов Map
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCount.entrySet());

        // Сортируем список по убыванию количества повторений
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Выводим топ-10 слов
        System.out.println("Топ-10 самых часто встречающихся слов:");
        int count = Math.min(10, list.size());
        for (int i = 0; i < count; i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            System.out.printf("%d. '%s' - %d раз(а)%n", i + 1, entry.getKey(), entry.getValue());
        }
    }
}