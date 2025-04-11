public class HashTableChaining<K, V> {
    private static final int DEFAULT_CAPACITY = 17; 
    private static final double LOAD_FACTOR = 1.5; 

    //number of key value pairs in the table
    private int size = 0;
    //number of total buckets/slots the table has 
    private int capacity; 
    //threshole to trigger a resize of the table (chang capacity)
    private double maxLoadFactor;
    //creates a tabl with data type of linked list of entry elements
    private LinkedList<Entry<K, V>>[] table; 

    @SuppressWarnings("unchecked")
    public HashTableChaining() {
        this.capacity = DEFAULT_CAPACITY;
        this.maxLoadFactor = LOAD_FACTOR; 
        //creates a table which is an array but of data type LinkedList (array of linkedlists)
        //data type LinkedList has K,V pairs inside of each node 
        this.table = new LinkedList[capacity]; 
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>(); 
        }
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

    //intializes a new hashtable to clear it 
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>(); 
        }
        size = 0; 
    }

    //if the key exists in the table and getEntry(key) returns the entry, then returns true
    public boolean containsKey(K key) {
        return getEntry(key) != null; 
    }

    //returns the corresponding value of the key if it exists in the table, otherwise returns null
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
        //creates new key value pair object
        Entry<K, V> newEntry = new Entry<>(key, value); 
        //creates index for the new entry using hashcode
        int index = normalizeIndex(newEntry.hash);
        //accesses and creates a bucket at the desired index to insert the element
        LinkedList<Entry<K, V>> bucket = table[index]; 
        //cycles through each entry in the bucket
        for (Entry<K, V> entry : bucket) {
            //if the keys are equal, value is reassigned and returned
            if (entry.equals(newEntry)) {
                V oldValue = entry.value;
                entry.value = value; 
                return oldValue; 
            }
        }
        //if the key is not found, the entry is added to the bucket and size is increased
        bucket.add(newEntry);
        size++; 
        //calculate the load factor and if it exceeds the max load factor, resize
        if ((double) size / capacity > maxLoadFactor) {
            resizeTable(); 
        }
        //returns null if new key value pair is added
        return null; 
    }

    public V remove(K key) {
        //find index and access the bucket of that index in the table
        int index = normalizeIndex(key.hashCode()); 
        LinkedList<Entry<K, V>> bucket = table[index]; 
        //cycle through each entry in the bucket and see if that entry is equal to a current entry
        //if it is, create a new value for that key and remove the entry
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                V value = entry.value; 
                bucket.remove(entry); 
                size--;
                return value; 
            }
        }
        //if no key found to remove, return null
        return null; 
    }

    private Entry<K, V> getEntry(K key) {
        //find index and access the bucket of that index in the table
        int index = normalizeIndex(key.hashCode()); 
        LinkedList<Entry<K,V>> bucket = table[index]; 
        //cycle through each entry in the bucket and see if that entry is equal to a current entry
        //if it is, return that entry
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry; 
            }
        }
        //return null if that entry was not found in the table 
        return null; 
    }

    @SuppressWarnings("unchecked")
    private void resizeTable() {
        //double capacity of the table
        capacity *= 2;
        //assign table to old table to store as temp variables
        LinkedList<Entry<K, V>>[] oldTable = table;
        //create a new empty table with new capacity a
        table = new LinkedList[capacity]; 
        size = 0; 
        //create new linked lists for each bucket in the table (2x as big bc capacity)
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>(); 
        }
        //cycle through each bucket in the old table 
        for (LinkedList<Entry<K, V>> bucket : oldTable) {
            //cycle through each entry in the bucket and rehash it to the new table
            for (Entry<K, V> entry : bucket) {
                put(entry.key, entry.value); 
            }
        }
    }

    @Override 
    public String toString() {
        //initialize string builder to create a string representation of the table
        StringBuilder sb = new StringBuilder(); 
        //cycle through each bucket in the table and append the entries to the string builder
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    sb.append(entry).append(", ");
                }
            }
        }
        sb.append("}");
        return sb.toString(); 
    }

}
