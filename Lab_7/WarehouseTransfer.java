package Lab_7;
import java.util.*;
import java.util.concurrent.locks.*;

public class WarehouseTransfer {

    static class Product {
        private final String name;
        private final int weight;

        public Product(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return name + "(" + weight + "кг)";
        }
    }

    static class Warehouse {
        private final List<Product> products;
        private final Lock lock;

        public Warehouse() {
            this.products = new ArrayList<>();
            this.lock = new ReentrantLock();
        }

        public void addProduct(Product product) {
            lock.lock();
            try {
                products.add(product);
            } finally {
                lock.unlock();
            }
        }

        public Product removeProduct() {
            lock.lock();
            try {
                if (products.isEmpty()) {
                    return null;
                }
                return products.remove(0);
            } finally {
                lock.unlock();
            }
        }

        public boolean isEmpty() {
            lock.lock();
            try {
                return products.isEmpty();
            } finally {
                lock.unlock();
            }
        }

        public int getProductCount() {
            lock.lock();
            try {
                return products.size();
            } finally {
                lock.unlock();
            }
        }
    }

    static class Loader extends Thread {
        private final String name;
        private final Warehouse sourceWarehouse;
        private final Warehouse destinationWarehouse;
        private int totalWeightCarried;
        private static final int MAX_WEIGHT_PER_TRIP = 150;

        public Loader(String name, Warehouse sourceWarehouse, Warehouse destinationWarehouse) {
            this.name = name;
            this.sourceWarehouse = sourceWarehouse;
            this.destinationWarehouse = destinationWarehouse;
            this.totalWeightCarried = 0;
        }

        @Override
        public void run() {
            while (!sourceWarehouse.isEmpty()) {
                int currentTripWeight = 0;
                List<Product> currentLoad = new ArrayList<>();

                // Сбор товаров для текущей итерации
                while (currentTripWeight < MAX_WEIGHT_PER_TRIP && !sourceWarehouse.isEmpty()) {
                    Product product = sourceWarehouse.removeProduct();
                    if (product != null && currentTripWeight + product.getWeight() <= MAX_WEIGHT_PER_TRIP) {
                        currentLoad.add(product);
                        currentTripWeight += product.getWeight();
                    } else if (product != null) {
                        // Если товар не помещается, возвращаем его на склад
                        sourceWarehouse.addProduct(product);
                        break;
                    }
                }

                if (!currentLoad.isEmpty()) {
                    // Перенос на другой склад
                    System.out.println(name + " переносит " + currentLoad.size() +
                            " товаров общим весом " + currentTripWeight + "кг");

                    try {
                        // Имитация времени переноса
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }

                    // Разгрузка на другом складе
                    for (Product product : currentLoad) {
                        destinationWarehouse.addProduct(product);
                    }

                    totalWeightCarried += currentTripWeight;

                    System.out.println(name + " завершил перенос. Всего перенесено: " +
                            totalWeightCarried + "кг");
                }
            }

            System.out.println(name + " завершил работу. Итого перенесено: " +
                    totalWeightCarried + "кг");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Warehouse sourceWarehouse = new Warehouse();
        Warehouse destinationWarehouse = new Warehouse();

        // Заполняем склад товарами
        Random random = new Random();
        String[] productNames = {"Холодильник", "Телевизор", "Стиральная машина",
                "Микроволновка", "Пылесос", "Утюг", "Чайник"};

        for (int i = 0; i < 20; i++) {
            String name = productNames[random.nextInt(productNames.length)] + "_" + (i + 1);
            int weight = random.nextInt(50) + 10; // Вес от 10 до 60 кг
            sourceWarehouse.addProduct(new Product(name, weight));
        }

        System.out.println("Исходный склад содержит " + sourceWarehouse.getProductCount() + " товаров");

        // Создаем грузчиков
        Loader[] loaders = {
                new Loader("Грузчик 1", sourceWarehouse, destinationWarehouse),
                new Loader("Грузчик 2", sourceWarehouse, destinationWarehouse),
                new Loader("Грузчик 3", sourceWarehouse, destinationWarehouse)
        };

        // Запускаем грузчиков
        for (Loader loader : loaders) {
            loader.start();
        }

        // Ждем завершения всех грузчиков
        for (Loader loader : loaders) {
            loader.join();
        }

        System.out.println("\nВсе товары перенесены!");
        System.out.println("Конечный склад содержит " + destinationWarehouse.getProductCount() + " товаров");
    }
}
