public class Entry<K, V> {
    int hash;
    K key;
    V value;

    //hashcode function assigned the hash for the value
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.hash = key.hashCode();
    }

    public boolean equals(Entry<K, V> other) {
        //checks if the hash codes of both key value pairs is equal, if not then they're not equal
        if (hash != other.hash) {
            return false; 
        }
        //if hashcodes are equal, checks if keys are equal in case of collision and if they are, then pairs are equal
        return key.equals(other.key); 
    }

    @Override
    public String toString() {
        return key + " => " + value;
    }
}
