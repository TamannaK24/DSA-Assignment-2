import java.util.Random;

public class Memory {
    
    //generate random array of integers
    public static Integer[] generateRandomArray(int size) {
        Integer[] array = new Integer[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(size * 10);
        }
        return array;
    }

    //generate random array of integers with a specific range
    //used for deleting and searching 
    public static Integer[] getSubset(Integer[] array, int size) {
        Random rand = new Random();
        Integer[] subset = new Integer[size];
        for (int i = 0; i < size; i++) {
            subset[i] = array[rand.nextInt(array.length)];
        }
        return subset;
    }

    //measure memory usage of a given operation
    public static void measureMemory(Runnable operation) {
        //accessing JVM memory
        Runtime runtime = Runtime.getRuntime();
        //java garbage collector to clean up unused memory before measuring
        System.gc();
        //total memory - free memory = used memory
        long before = runtime.totalMemory() - runtime.freeMemory();
        //run the operation to measure memory usage
        operation.run();

        //measure memory again after the operation
        long after = runtime.totalMemory() - runtime.freeMemory();
        //calculate the difference in memory usage in KB
        long usedMemoryKB = (after - before) / 1024;
        System.out.print(usedMemoryKB + "KB\t\t\t");
    }

    //AVL Tree
    public static void AVLInsertion(int[] dataSizes) {
        System.out.print("AVL Tree\t\t");
        //loop through the data sizes to measure memory usage for each size
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            //uses measureMemory method to measure the memory usage of the operation
            //using java's runnable interface
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    //creates empty avl tree and inserts
                    AVLTree<Integer> avl = new AVLTree<>();
                    for (int i = 0; i < data.length; i++) {
                        int val = data[i];
                        avl.insert(val);
                    }
                }
            });
        }
        System.out.println();
    }
    
    public static void AVLDeletion(int[] dataSizes) {
        System.out.print("AVL Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    AVLTree<Integer> avl = new AVLTree<>();
                    for (int i = 0; i < data.length; i++) {
                        avl.insert(data[i]);
                    }
                    for (int i = 0; i < toDelete.length; i++) {
                        avl.remove(toDelete[i]);
                    }
                }
            });
        }
        System.out.println();
    }
    
    public static void AVLSearch(int[] dataSizes) {
        System.out.print("AVL Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    AVLTree<Integer> avl = new AVLTree<>();
                    for (int i = 0; i < data.length; i++) {
                        avl.insert(data[i]);
                    }
                    for (int i = 0; i < toSearch.length; i++) {
                        avl.contains(toSearch[i]);
                    }
                }
            });
        }
        System.out.println();
    }
    
    //Splay Tree
    
    public static void SplayInsertion(int[] dataSizes) {
        System.out.print("Splay Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    SplayTree<Integer> splay = new SplayTree<>();
                    for (int i = 0; i < data.length; i++) {
                        splay.insert(data[i]);
                    }
                }
            });
        }
        System.out.println();
    }
    
    public static void SplayDeletion(int[] dataSizes) {
        System.out.print("Splay Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    SplayTree<Integer> splay = new SplayTree<>();
                    for (int i = 0; i < data.length; i++) {
                        splay.insert(data[i]);
                    }
                    for (int i = 0; i < toDelete.length; i++) {
                        splay.remove(toDelete[i]);
                    }
                }
            });
        }
        System.out.println();
    }
    
    public static void SplaySearch(int[] dataSizes) {
        System.out.print("Splay Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    SplayTree<Integer> splay = new SplayTree<>();
                    for (int i = 0; i < data.length; i++) {
                        splay.insert(data[i]);
                    }
                    for (int i = 0; i < toSearch.length; i++) {
                        splay.contains(toSearch[i]);
                    }
                }
            });
        }
        System.out.println();
    }
    
    //Hash Table Chaining
    
    public static void ChainingInsertion(int[] dataSizes) {
        System.out.print("Hash Chaining\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    HashTableChaining<Integer, Integer> table = new HashTableChaining<>();
                    for (int i = 0; i < data.length; i++) {
                        table.put(data[i], data[i]);
                    }
                }
            });
        }
        System.out.println();
    }
    
    public static void ChainingDeletion(int[] dataSizes) {
        System.out.print("Hash Chaining\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    HashTableChaining<Integer, Integer> table = new HashTableChaining<>();
                    for (int i = 0; i < data.length; i++) {
                        table.put(data[i], data[i]);
                    }
                    for (int i = 0; i < toDelete.length; i++) {
                        table.remove(toDelete[i]);
                    }
                }
            });
        }
        System.out.println();
    }
    
    public static void ChainingSearch(int[] dataSizes) {
        System.out.print("Hash Chaining\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    HashTableChaining<Integer, Integer> table = new HashTableChaining<>();
                    for (int i = 0; i < data.length; i++) {
                        table.put(data[i], data[i]);
                    }
                    for (int i = 0; i < toSearch.length; i++) {
                        table.get(toSearch[i]);
                    }
                }
            });
        }
        System.out.println();
    }
    
    //Hash Table Probing 
    
    public static void ProbingInsertion(int[] dataSizes) {
        System.out.print("Hash Probing\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    HashTableQuadraticProbing<Integer, Integer> table = new HashTableQuadraticProbing<>();
                    for (int i = 0; i < data.length; i++) {
                        table.put(data[i], data[i]);
                    }
                }
            });
        }
        System.out.println();
    }
    
    public static void ProbingDeletion(int[] dataSizes) {
        System.out.print("Hash Probing\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    HashTableQuadraticProbing<Integer, Integer> table = new HashTableQuadraticProbing<>();
                    for (int i = 0; i < data.length; i++) {
                        table.put(data[i], data[i]);
                    }
                    for (int i = 0; i < toDelete.length; i++) {
                        table.remove(toDelete[i]);
                    }
                }
            });
        }
        System.out.println();
    }
    
    public static void ProbingSearch(int[] dataSizes) {
        System.out.print("Hash Probing\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
            measureMemory(new Runnable() {
                @Override
                public void run() {
                    HashTableQuadraticProbing<Integer, Integer> table = new HashTableQuadraticProbing<>();
                    for (int i = 0; i < data.length; i++) {
                        table.put(data[i], data[i]);
                    }
                    for (int i = 0; i < toSearch.length; i++) {
                        table.get(toSearch[i]);
                    }
                }
            });
        }
        System.out.println();
    }
}   