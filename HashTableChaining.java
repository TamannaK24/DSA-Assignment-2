import java.util.Iterator;
import java.util.LinkedList;

public class HashTableChaining<T> implements Iterable<T> {

    private LinkedList<T>[] table;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public HashTableChaining(int capacity) {
        this.capacity = capacity;
        this.table = new LinkedList[capacity];
        this.size = 0;
    }

    public int hash(T value) {
        return (value.hashCode() & 0x7FFFFFFF) % capacity;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(T value) {
        if (value == null) {
            return false;
        }
        int index = hash(value);
        LinkedList<T> bucket = table[index];
        if (bucket == null) {
            return false;
        }
        return bucket.contains(value);
    }

    public boolean insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot insert null value");
        }
        int index = hash(value);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        if (!table[index].contains(value)) {
            table[index].add(value);
            size++;
            return true;
        }
        return false;
    }

    public boolean remove(T value) {
        if (value == null) {
            return false;
        }
        int index = hash(value);
        LinkedList<T> bucket = table[index];
        if (bucket != null && bucket.remove(value)) {
            size--;
            return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int bucketIndex = 0;
            private Iterator<T> listIterator = null;

            {
                advanceToNextNonEmptyBucket();
            }

            private void advanceToNextNonEmptyBucket() {
                while ((listIterator == null || !listIterator.hasNext()) && bucketIndex < capacity) {
                    if (table[bucketIndex] != null) {
                        listIterator = table[bucketIndex].iterator();
                    }
                    bucketIndex++;
                }
            }

            @Override
            public boolean hasNext() {
                advanceToNextNonEmptyBucket();
                return listIterator != null && listIterator.hasNext();
            }

            @Override
            public T next() {
                return listIterator.next();
            }
        };
    }
}
