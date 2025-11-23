package Lab_8;
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Лабораторная работа №8: Аннотации и Stream API ===\n");

        // Создаем менеджер данных
        DataManager<Person> dataManager = new DataManager<>();

        try {
            // Регистрируем обработчики
            dataManager.registerDataProcessor(new FilterProcessor());
            dataManager.registerDataProcessor(new TransformProcessor());
            dataManager.registerDataProcessor(new AggregateProcessor());

            // Загружаем данные
            dataManager.loadData("memory://sample-data");

            // Обрабатываем данные (многопоточная обработка)
            dataManager.processData();

            // Сохраняем результаты
            dataManager.saveData("memory://processed-data");

        } finally {
            dataManager.shutdown();
        }

        System.out.println("\n=== Обработка завершена ===");
    }
}