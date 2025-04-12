public class HashTableQuadraticProbing<K, V> {
    private static final int DEFAULT_CAPACITY = 17;
    private static final double LOAD_FACTOR = 0.5;

    //number of key value pairs in the table
    private int size = 0; 
    //number of total buckets/slots the table has
    private int capacity; 
    //threshold to trigger a resize of the table 
    private double maxLoadFactor; 
    //creates a table with data type of linked list of entry elements
    private Entry<K, V>[] table;
    //array used to track which slots have been used in the hashtable 
    //(to check if the index has ever been part of the probing sequence)
    //even if slot was once used and now deleted, used is true
    private boolean[] used; 

    @SuppressWarnings("uAnchecked") 
    public HashTableQuadraticProbing() {
        this.capacity = DEFAULT_CAPACITY;
        this.maxLoadFactor = LOAD_FACTOR;
        //table has 17 slots with one key value pair in each slot
        this.table = new Entry[capacity]; 
        //used is an array of booleans with the same size as the table
        this.used = new boolean[capacity]; 
    }

    //returns index of the bucket where key value pair goes
    private int normalizeIndex(int hash) {
        return Math.abs(hash) % capacity; 
    }

    public int size() {
        return size; 
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //clears table by creating a new table and setting size to 0
    public void clear() {
        table = new Entry[capacity]; 
        used = new boolean[capacity]; 
        size = 0; 
    }

    //returns the entry of the key if it exists in the table, otherwise returns null
    public boolean containsKey(K key) {
        return getEntry(key) != null; 
    }
    
    //returns corresponding value of the key if it exists in the table, otherwise returns null
    public V get(K key) {
        Entry<K, V> entry = getEntry(key); 
        if (entry == null) {
            return null;
        }
        else {
            return entry.value;
        }
    }

    public V put(K key, V value) {
        //if table is full, resize it
        if ((double) size / capacity >= maxLoadFactor) {
            resizeTable();
        }
        //create new entry to put the given key value pair in 
        Entry<K, V> newEntry = new Entry<>(key, value);
        //find index for new entry
        int hash = normalizeIndex(newEntry.hash); 
        int x = 1; 
        int index = hash; 
        //keep probing as long as current slot is occupied, stop once finding an empty slot
        while (table[index] != null) {
            //if the key already exists, update the old value paired with it to be the new value and return the old value indicating its been replaced
            if (table[index].key.equals(key)) {
                V oldValue = table[index].value;
                table[index].value = value;
                return oldValue; 
            }
            //else if the slot is occupied, use quadratic probing 
            index = normalizeIndex(hash + x*x); 
            //increase x to probe the next slot in the sequence
            x++; 
        }
        //once arrived at an open slot, insert the new entry
        //slot is now used, increase size
        table[index] = newEntry;
        used[index] = true;
        size++;
        return null; 
    }

    public V remove(K key) {
        //find index of the key to be removed
        int hash = normalizeIndex(key.hashCode()); 
        int x = 1;
        int index = hash;
        //keep probing as long as the slot is occupied and the key is not found
        while (used[index]) {
            //if the table index has the key and isn't empty
            if (table[index] != null && table[index].key.equals(key)) {
                //remove the entry by making the slot null 
                //return the value of the removed entry
                V value = table[index].value; 
                table[index] = null; 
                size--; 
                return value; 
            }
            //if the key isnt found, continue probing 
            index = normalizeIndex(hash + x*x); 
            x++; 
        }
        return null; 
    }

    private Entry<K, V> getEntry(K key) {
        //get hash code of key to be found and find its index
        int hash = normalizeIndex(key.hashCode()); 
        int x = 1; 
        int index = hash; 
        //keep probing as long as the slot is occupied and the key is not found
        while (used[index]) {
            //if the table index has the key and isn't empty, return the entry
            if (table[index] != null && table[index].key.equals(key)) {
                return table[index]; 
            }
            //if the key isnt found, continue probing quadratically 
            index = normalizeIndex(hash + x*x); 
            x++;   
        }
        //if the key is not found, return null
        return null; 
    }

    @SuppressWarnings("unchecked") 
    private void resizeTable() {
        //double the size of the table and create a new table
        capacity *= 2;
        //store the old table in a temporary variable
        Entry<K, V>[] oldTable = table; 
        //store number of used in a temporary variable
        boolean[] oldUsed = used; 
        //create a new table and used array with the new capacity
        table = new Entry[capacity]; 
        //create a new used array with the new capacity
        used = new boolean[capacity]; 
        size = 0;
        //cycle through old table and if the slot is occupied, rehash the entry and put it in the new table
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null) {
                put(oldTable[i].key, oldTable[i].value); 
            }
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



