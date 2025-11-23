package Lab_6;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Класс для представления товара
class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f руб.", name, price);
    }
}

// Класс для учета продаж
class SalesManager {
    private ArrayList<Product> soldProducts; // Список проданных товаров
    private Map<String, Integer> productSalesCount; // Количество продаж по каждому товару

    public SalesManager() {
        soldProducts = new ArrayList<>();
        productSalesCount = new HashMap<>();
    }

    // Метод для добавления проданного товара
    public void addSoldProduct(Product product) {
        soldProducts.add(product);

        // Обновляем счетчик продаж для данного товара
        String productName = product.getName();
        productSalesCount.put(productName,
                productSalesCount.getOrDefault(productName, 0) + 1);

        System.out.println("Товар добавлен: " + product.getName());
    }

    // Метод для вывода списка всех проданных товаров
    public void displaySoldProducts() {
        if (soldProducts.isEmpty()) {
            System.out.println("Список проданных товаров пуст.");
            return;
        }

        System.out.println("\n=== СПИСОК ПРОДАННЫХ ТОВАРОВ ===");
        for (int i = 0; i < soldProducts.size(); i++) {
            System.out.println((i + 1) + ". " + soldProducts.get(i));
        }
    }

    // Метод для расчета общей суммы продаж
    public double calculateTotalSales() {
        double total = 0;
        for (Product product : soldProducts) {
            total += product.getPrice();
        }
        return total;
    }

    // Метод для поиска наиболее популярного товара
    public void findMostPopularProduct() {
        if (productSalesCount.isEmpty()) {
            System.out.println("Нет данных о продажах.");
            return;
        }

        String mostPopular = null;
        int maxSales = 0;

        for (Map.Entry<String, Integer> entry : productSalesCount.entrySet()) {
            if (entry.getValue() > maxSales) {
                maxSales = entry.getValue();
                mostPopular = entry.getKey();
            }
        }

        System.out.println("\n=== САМЫЙ ПОПУЛЯРНЫЙ ТОВАР ===");
        System.out.println("Товар: " + mostPopular);
        System.out.println("Количество продаж: " + maxSales);
    }

    // Метод для вывода статистики по продажам
    public void displaySalesStatistics() {
        System.out.println("\n=== СТАТИСТИКА ПРОДАЖ ===");
        System.out.println("Общее количество проданных товаров: " + soldProducts.size());
        System.out.printf("Общая сумма продаж: %.2f руб.\n", calculateTotalSales());

        if (!productSalesCount.isEmpty()) {
            System.out.println("\nКоличество продаж по товарам:");
            for (Map.Entry<String, Integer> entry : productSalesCount.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " шт.");
            }
        }
    }
}

// Главный класс программы
public class StoreSales {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SalesManager salesManager = new SalesManager();

        // Предопределенные товары для демонстрации
        Product[] availableProducts = {
                new Product("Хлеб", 50.0),
                new Product("Молоко", 80.0),
                new Product("Яблоки", 120.0),
                new Product("Кофе", 350.0),
                new Product("Чай", 200.0)
        };

        System.out.println("=== ПРОГРАММА УЧЕТА ПРОДАЖ В МАГАЗИНЕ ===");

        boolean running = true;
        while (running) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Добавить проданный товар");
            System.out.println("2. Показать все проданные товары");
            System.out.println("3. Показать общую сумму продаж");
            System.out.println("4. Найти самый популярный товар");
            System.out.println("5. Показать статистику продаж");
            System.out.println("6. Добавить несколько тестовых продаж");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число!");
                continue;
            }

            switch (choice) {
                case 1:
                    // Добавление проданного товара
                    System.out.println("\nДоступные товары:");
                    for (int i = 0; i < availableProducts.length; i++) {
                        System.out.println((i + 1) + ". " + availableProducts[i]);
                    }

                    System.out.print("Выберите товар (1-" + availableProducts.length + "): ");
                    try {
                        int productChoice = Integer.parseInt(scanner.nextLine()) - 1;
                        if (productChoice >= 0 && productChoice < availableProducts.length) {
                            salesManager.addSoldProduct(availableProducts[productChoice]);
                        } else {
                            System.out.println("Неверный выбор товара!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: введите число!");
                    }
                    break;

                case 2:
                    // Показать все проданные товары
                    salesManager.displaySoldProducts();
                    break;

                case 3:
                    // Показать общую сумму продаж
                    double total = salesManager.calculateTotalSales();
                    System.out.printf("\nОбщая сумма продаж: %.2f руб.\n", total);
                    break;

                case 4:
                    // Найти самый популярный товар
                    salesManager.findMostPopularProduct();
                    break;

                case 5:
                    // Показать статистику продаж
                    salesManager.displaySalesStatistics();
                    break;

                case 6:
                    // Добавить тестовые данные
                    System.out.println("\nДобавление тестовых продаж...");
                    salesManager.addSoldProduct(availableProducts[0]); // Хлеб
                    salesManager.addSoldProduct(availableProducts[1]); // Молоко
                    salesManager.addSoldProduct(availableProducts[0]); // Хлеб
                    salesManager.addSoldProduct(availableProducts[2]); // Яблоки
                    salesManager.addSoldProduct(availableProducts[1]); // Молоко
                    salesManager.addSoldProduct(availableProducts[0]); // Хлеб
                    salesManager.addSoldProduct(availableProducts[3]); // Кофе
                    System.out.println("Тестовые продажи добавлены!");
                    break;

                case 0:
                    running = false;
                    System.out.println("Программа завершена. До свидания!");
                    break;

                default:
                    System.out.println("Неверный выбор! Попробуйте снова.");
            }
        }

        scanner.close();
    }
}