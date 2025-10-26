package Lab_3;

import java.util.Objects;

public class Student {
    private String firstName;
    private String lastName;
    private int age;
    private double gpa; // средний балл

    public Student(String firstName, String lastName, int age, double gpa) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gpa = gpa;
    }

    // геттеры/сеттеры (по необходимости можно добавить)
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public double getGpa() { return gpa; }

    @Override
    public String toString() {
        return String.format("%s %s, age=%d, GPA=%.2f",
                firstName, lastName, age, gpa);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return age == s.age
                && Double.compare(s.gpa, gpa) == 0
                && Objects.equals(firstName, s.firstName)
                && Objects.equals(lastName, s.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, gpa);
    }
}