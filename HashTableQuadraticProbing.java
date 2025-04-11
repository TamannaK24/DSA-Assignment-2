public class HashTableQuadraticProbing<K, V> {
    private static final int DEFAULT_CAPACITY = 17;
    private static final double LOAD_FACTOR = 0.5;

    // number of key value pairs in the table
    private int size = 0;
    // number of total slots the table has
    private int capacity;
    // threshold to trigger a resize of the table
    private double maxLoadFactor;
    // creates a table of Entry elements (key-value pairs)
    private Entry<K, V>[] table;

    // class to store key-value pairs
    public static class Entry<K, V> {
        int hash;
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.hash = key.hashCode();
        }

        public boolean equals(Entry<K, V> other) {
            return key.equals(other.key);
        }

        @Override
        public String toString() {
            return key + " => " + value;
        }
    }

    @SuppressWarnings("unchecked")
    public HashTableQuadraticProbing() {
        this.capacity = DEFAULT_CAPACITY;
        this.maxLoadFactor = LOAD_FACTOR;
        this.table = new Entry[capacity];
    }

    private int normalizeIndex(int hash) {
        return Math.abs(hash) % capacity;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        table = new Entry[capacity];
        size = 0;
    }

    public boolean containsKey(K key) {
        return getEntry(key) != null;
    }

    public V get(K key) {
        Entry<K, V> entry = getEntry(key);
        return (entry == null) ? null : entry.value;
    }

    public V put(K key, V value) {
        Entry<K, V> newEntry = new Entry<>(key, value);
        int hash = normalizeIndex(newEntry.hash);

        int index = hash;
        int i = 1;

        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                V oldValue = table[index].value;
                table[index].value = value;
                return oldValue;
            }
            index = normalizeIndex(hash + i * i); // quadratic probing
            i++;
        }

        table[index] = newEntry;
        size++;

        if ((double) size / capacity > maxLoadFactor) {
            resizeTable();
        }

        return null;
    }

    public V remove(K key) {
        int hash = normalizeIndex(key.hashCode());
        int index = hash;
        int i = 1;

        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                V value = table[index].value;
                table[index] = null;
                size--;
                rehashAfterRemove(index); // shift to preserve probing path
                return value;
            }
            index = normalizeIndex(hash + i * i);
            i++;
        }

        return null;
    }

    private Entry<K, V> getEntry(K key) {
        int hash = normalizeIndex(key.hashCode());
        int index = hash;
        int i = 1;

        while (table[index] != null) {
            if (table[index].key.equals(key)) {
                return table[index];
            }
            index = normalizeIndex(hash + i * i);
            i++;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private void resizeTable() {
        capacity *= 2;
        Entry<K, V>[] oldTable = table;
        table = new Entry[capacity];
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            if (entry != null) {
                put(entry.key, entry.value);
            }
        }
    }

    // rehash entries after a removal to maintain clustering
    private void rehashAfterRemove(int removedIndex) {
        int i = 1;
        int index = normalizeIndex(removedIndex + i * i);

        while (table[index] != null) {
            Entry<K, V> entryToRehash = table[index];
            table[index] = null;
            size--; // temporarily decrement so put() increments correctly
            put(entryToRehash.key, entryToRehash.value);
            index = normalizeIndex(index + i * i);
            i++;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                sb.append(entry).append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
