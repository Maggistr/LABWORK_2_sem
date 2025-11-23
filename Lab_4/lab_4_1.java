package Lab_4;

public class lab_4_1 {
    public static void main(String[] args) {
        int[] validArray = {1, 2, 3, 4, 5};
        int[] emptyArray = {};
        int[] nullArray = null;

        System.out.println("=== Задание 1: Среднее арифметическое массива ===");

        System.out.println("\nТест 1: Корректный массив [1, 2, 3, 4, 5]");
        calculateAverage(validArray);

        System.out.println("\nТест 2: Пустой массив");
        calculateAverage(emptyArray);

        System.out.println("\nТест 3: Null массив");
        calculateAverage(nullArray);
    }

    public static void calculateAverage(int[] arr) {
        try {
            if (arr == null) {
                throw new NullPointerException("Массив не может быть null");
            }

            if (arr.length == 0) {
                throw new IllegalArgumentException("Массив не может быть пустым");
            }

            int sum = 0;
            for (int i = 0; i < arr.length; i++) {
                sum += arr[i];
            }

            double average = (double) sum / arr.length;
            System.out.println("Среднее арифметическое: " + average);

        } catch (NullPointerException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
}