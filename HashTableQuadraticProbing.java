import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashTableQuadraticProbing<T> implements Iterable<T> {

    private T[] table;
    private boolean[] deleted;
    private int size;
    private int capacity;
    private final double loadFactor = 0.5;
    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    public HashTableQuadraticProbing(int capacity, Class<T> clazz) {
        this.capacity = capacity;
        this.clazz = clazz;
        this.table = (T[]) java.lang.reflect.Array.newInstance(clazz, capacity);
        this.deleted = new boolean[capacity];
        this.size = 0;
    }

    private int hash(T value) {
        return (value.hashCode() & 0x7FFFFFFF) % capacity;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(T value) {
        return findSlot(value) != -1;
    }

    public boolean insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot insert null value");
        }

        if ((double) size / capacity >= loadFactor) {
            resize();
        }

        int index = hash(value);
        int i = 0;

        while (i < capacity) {
            int probe = (index + i * i) % capacity;
            if (table[probe] == null || deleted[probe]) {
                table[probe] = value;
                deleted[probe] = false;
                size++;
                return true;
            } else if (table[probe].equals(value)) {
                return false;
            }
            i++;
        }

        return false;
    }

    public boolean remove(T value) {
        int slot = findSlot(value);
        if (slot == -1) {
            return false;
        }
        table[slot] = null;
        deleted[slot] = true;
        size--;
        return true;
    }

    private int findSlot(T value) {
        if (value == null) {
            return -1;
        }
        int index = hash(value);
        int i = 0;

        while (i < capacity) {
            int probe = (index + i * i) % capacity;
            if (table[probe] == null && !deleted[probe]) {
                return -1;
            }
            if (table[probe] != null && table[probe].equals(value)) {
                return probe;
            }
            i++;
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        T[] oldTable = table;
        boolean[] oldDeleted = deleted;

        table = (T[]) java.lang.reflect.Array.newInstance(clazz, newCapacity);
        deleted = new boolean[newCapacity];
        size = 0;
        capacity = newCapacity;

        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null && !oldDeleted[i]) {
                insert(oldTable[i]);
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                while (index < capacity) {
                    if (table[index] != null && !deleted[index]) {
                        return true;
                    }
                    index++;
                }
                return false;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++];
            }
        };
    }
}
