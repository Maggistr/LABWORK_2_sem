package Lab_8;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DataManager<T> {
    private List<T> data;
    private final List<Object> processors;
    private final ExecutorService executorService;

    public DataManager() {
        this.processors = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    // Регистрация обработчиков
    public void registerDataProcessor(Object processor) {
        boolean hasAnnotation = Arrays.stream(processor.getClass().getMethods())
                .anyMatch(method -> method.isAnnotationPresent(DataProcessor.class));

        if (hasAnnotation) {
            processors.add(processor);
            System.out.println("Зарегистрирован обработчик: " + processor.getClass().getSimpleName());
        }
    }

    // Загрузка данных (имитация)
    @SuppressWarnings("unchecked")
    public void loadData(String source) {
        System.out.println("Загрузка данных из: " + source);

        // Имитация загрузки данных - в реальности здесь может быть чтение из файла, БД и т.д.
        this.data = (List<T>) createSampleData();
        System.out.println("Загружено " + data.size() + " записей");
    }

    // Многопоточная обработка данных
    @SuppressWarnings("unchecked")
    public void processData() {
        if (data == null || data.isEmpty()) {
            System.out.println("Нет данных для обработки");
            return;
        }

        System.out.println("\nНачало многопоточной обработки...");

        // Собираем все методы с аннотацией @DataProcessor
        List<Method> processorMethods = processors.stream()
                .flatMap(processor -> Arrays.stream(processor.getClass().getMethods())
                        .filter(method -> method.isAnnotationPresent(DataProcessor.class))
                        .sorted(Comparator.comparingInt(m -> m.getAnnotation(DataProcessor.class).order())))
                .collect(Collectors.toList());

        // Применяем обработчики в многопоточном режиме
        List<CompletableFuture<Void>> futures = processorMethods.stream()
                .map(method -> CompletableFuture.runAsync(() -> {
                    try {
                        DataProcessor annotation = method.getAnnotation(DataProcessor.class);
                        System.out.println("Применение обработчика: " + annotation.name() +
                                " (порядок: " + annotation.order() + ")");

                        Object processor = processors.stream()
                                .filter(p -> Arrays.asList(p.getClass().getMethods()).contains(method))
                                .findFirst()
                                .orElse(null);

                        if (processor != null) {
                            method.invoke(processor, data);
                        }
                    } catch (Exception e) {
                        System.err.println("Ошибка в обработчике " + method.getName() + ": " + e.getMessage());
                    }
                }, executorService))
                .collect(Collectors.toList());

        // Ожидаем завершения всех задач
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println("Обработка завершена");
    }

    // Сохранение данных
    public void saveData(String destination) {
        System.out.println("Сохранение данных в: " + destination);
        if (data != null) {
            System.out.println("Сохранено " + data.size() + " записей");
            // В реальном приложении здесь была бы логика сохранения в файл, БД и т.д.
            data.forEach(System.out::println);
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }

    // Вспомогательный метод для создания тестовых данных
    private List<?> createSampleData() {
        return Arrays.asList(
                new Person("Иван", "Иванов", 25, 45000),
                new Person("Петр", "Петров", 30, 55000),
                new Person("Мария", "Сидорова", 28, 60000),
                new Person("Анна", "Кузнецова", 35, 75000),
                new Person("Сергей", "Смирнов", 22, 40000),
                new Person("Ольга", "Попова", 40, 80000),
                new Person("Дмитрий", "Васильев", 33, 65000),
                new Person("Елена", "Новикова", 29, 58000),
                new Person("Алексей", "Морозов", 45, 90000),
                new Person("Наталья", "Волкова", 27, 52000)
        );
    }

    public List<T> getData() {
        return data;
    }
}