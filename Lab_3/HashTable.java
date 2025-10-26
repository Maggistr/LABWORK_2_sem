package Lab_3;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Iterator;

public class HashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private LinkedList<Entry<K,V>>[] table;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        int cap = Math.max(1, capacity);
        table = (LinkedList<Entry<K,V>>[]) new LinkedList[cap];
    }

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    private int indexFor(Object key) {
        int h = (key == null) ? 0 : Objects.hashCode(key);
        // ensure non-negative
        return Math.abs(h) % table.length;
    }

    public void put(K key, V value) {
        int idx = indexFor(key);
        if (table[idx] == null) table[idx] = new LinkedList<>();
        for (Entry<K,V> e : table[idx]) {
            if (Objects.equals(e.key, key)) {
                e.value = value; // replace
                return;
            }
        }
        table[idx].add(new Entry<>(key, value));
        size++;
    }

    public V get(K key) {
        int idx = indexFor(key);
        if (table[idx] == null) return null;
        for (Entry<K,V> e : table[idx]) {
            if (Objects.equals(e.key, key)) return e.value;
        }
        return null;
    }

    public boolean remove(K key) {
        int idx = indexFor(key);
        if (table[idx] == null) return false;
        Iterator<Entry<K,V>> it = table[idx].iterator();
        while (it.hasNext()) {
            Entry<K,V> e = it.next();
            if (Objects.equals(e.key, key)) {
                it.remove();
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Простой внутренний класс записи
    private static class Entry<K,V> {
        K key;
        V value;
        Entry(K k, V v) { key = k; value = v; }
    }
}