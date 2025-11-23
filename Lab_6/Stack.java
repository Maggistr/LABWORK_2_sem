package Lab_6;
public class Stack<T> {
    private T[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public Stack() {
        data = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public Stack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Емкость должна быть положительной");
        }
        data = (T[]) new Object[capacity];
        size = 0;
    }

    public void push(T element) {
        if (size == data.length) {
            resize(data.length * 2);
        }
        data[size++] = element;
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Стек пуст");
        }
        T element = data[--size];
        data[size] = null; // Помогаем сборщику мусора

        // Уменьшаем массив, если он слишком пустой
        if (size > 0 && size == data.length / 4) {
            resize(data.length / 2);
        }

        return element;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Стек пуст");
        }
        return data[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newData = (T[]) new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stack[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Демонстрация использования
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>(5);

        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println("Стек: " + stack);
        System.out.println("Верхний элемент: " + stack.peek());
        System.out.println("Извлечен: " + stack.pop());
        System.out.println("Извлечен: " + stack.pop());

        stack.push(4);
        stack.push(5);

        System.out.println("Стек после добавления: " + stack);
        System.out.println("Размер стека: " + stack.size());

        // Тестирование со строками
        Stack<String> stringStack = new Stack<>();
        stringStack.push("Hello");
        stringStack.push("World");
        System.out.println("Строковый стек: " + stringStack);
    }
}