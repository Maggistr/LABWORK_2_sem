package Lab_8;
import java.util.List;
import java.util.stream.Collectors;

// Обработчик для фильтрации
class FilterProcessor {

    @DataProcessor(name = "Фильтрация по возрасту", order = 1)
    public void filterByAge(List<Person> data) {
        System.out.println("  - Фильтрация: возраст > 30 лет");
        List<Person> filtered = data.stream()
                .filter(person -> person.getAge() > 30)
                .collect(Collectors.toList());
        data.clear();
        data.addAll(filtered);
    }

    @DataProcessor(name = "Фильтрация по зарплате", order = 2)
    public void filterBySalary(List<Person> data) {
        System.out.println("  - Фильтрация: зарплата > 50000");
        List<Person> filtered = data.stream()
                .filter(person -> person.getSalary() > 50000)
                .collect(Collectors.toList());
        data.clear();
        data.addAll(filtered);
    }
}

// Обработчик для трансформации
class TransformProcessor {

    @DataProcessor(name = "Преобразование имен", order = 3)
    public void transformNames(List<Person> data) {
        System.out.println("  - Преобразование: имена в верхний регистр");
        data.forEach(person -> {
            person.setFirstName(person.getFirstName().toUpperCase());
            person.setLastName(person.getLastName().toUpperCase());
        });
    }

    @DataProcessor(name = "Увеличение зарплаты", order = 4)
    public void increaseSalary(List<Person> data) {
        System.out.println("  - Преобразование: увеличение зарплаты на 10%");
        data.forEach(person ->
                person.setSalary((int)(person.getSalary() * 1.1))
        );
    }
}

// Обработчик для агрегации
class AggregateProcessor {

    @DataProcessor(name = "Сортировка по зарплате", order = 5)
    public void sortBySalary(List<Person> data) {
        System.out.println("  - Агрегация: сортировка по убыванию зарплаты");
        List<Person> sorted = data.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getSalary(), p1.getSalary()))
                .collect(Collectors.toList());
        data.clear();
        data.addAll(sorted);
    }

    @DataProcessor(name = "Статистика", order = 6)
    public void calculateStatistics(List<Person> data) {
        System.out.println("  - Агрегация: расчет статистики");
        if (!data.isEmpty()) {
            double avgAge = data.stream()
                    .mapToInt(Person::getAge)
                    .average()
                    .orElse(0);

            double avgSalary = data.stream()
                    .mapToInt(Person::getSalary)
                    .average()
                    .orElse(0);

            int maxSalary = data.stream()
                    .mapToInt(Person::getSalary)
                    .max()
                    .orElse(0);

            System.out.println("    Средний возраст: " + String.format("%.2f", avgAge));
            System.out.println("    Средняя зарплата: " + String.format("%.2f", avgSalary));
            System.out.println("    Максимальная зарплата: " + maxSalary);
        }
    }
}