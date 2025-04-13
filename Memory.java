import java.util.Random;

public class Memory {
    public static Integer[] generateRandomArray(int size) {
        Integer[] array = new Integer[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(size * 10);
        }
        return array;
    }

    public static Integer[] getSubset(Integer[] array, int size) {
        Random rand = new Random();
        Integer[] subset = new Integer[size];
        for (int i = 0; i < size; i++) {
            subset[i] = array[rand.nextInt(array.length)];
        }
        return subset;
    }

    public static void measureMemory(Runnable operation) {
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        long before = runtime.totalMemory() - runtime.freeMemory();

        operation.run();

        long after = runtime.totalMemory() - runtime.freeMemory();
        long usedMemoryKB = (after - before) / 1024;
        System.out.print(usedMemoryKB + "KB\t\t\t");
    }

    public static void AVLInsertion(int[] dataSizes) {
        System.out.print("AVL Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            measureMemory(() -> {
                AVLTree<Integer> avl = new AVLTree<>();
                for (int val : data) avl.insert(val);
            });
        }
        System.out.println();
    }

    public static void AVLDeletion(int[] dataSizes) {
        System.out.print("AVL Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
            measureMemory(() -> {
                AVLTree<Integer> avl = new AVLTree<>();
                for (int val : data) avl.insert(val);
                for (int val : toDelete) avl.remove(val);
            });
        }
        System.out.println();
    }

    public static void AVLSearch(int[] dataSizes) {
        System.out.print("AVL Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
            measureMemory(() -> {
                AVLTree<Integer> avl = new AVLTree<>();
                for (int val : data) avl.insert(val);
                for (int val : toSearch) avl.contains(val);
            });
        }
        System.out.println();
    }

    public static void SplayInsertion(int[] dataSizes) {
        System.out.print("Splay Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            measureMemory(() -> {
                SplayTree<Integer> splay = new SplayTree<>();
                for (int val : data) splay.insert(val);
            });
        }
        System.out.println();
    }

    public static void SplayDeletion(int[] dataSizes) {
        System.out.print("Splay Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
            measureMemory(() -> {
                SplayTree<Integer> splay = new SplayTree<>();
                for (int val : data) splay.insert(val);
                for (int val : toDelete) splay.remove(val);
            });
        }
        System.out.println();
    }

    public static void SplaySearch(int[] dataSizes) {
        System.out.print("Splay Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
            measureMemory(() -> {
                SplayTree<Integer> splay = new SplayTree<>();
                for (int val : data) splay.insert(val);
                for (int val : toSearch) splay.contains(val);
            });
        }
        System.out.println();
    }

    public static void ChainingInsertion(int[] dataSizes) {
        System.out.print("Hash Chaining\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            measureMemory(() -> {
                HashTableChaining<Integer, Integer> table = new HashTableChaining<>();
                for (int val : data) table.put(val, val);
            });
        }
        System.out.println();
    }

    public static void ChainingDeletion(int[] dataSizes) {
        System.out.print("Hash Chaining\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
            measureMemory(() -> {
                HashTableChaining<Integer, Integer> table = new HashTableChaining<>();
                for (int val : data) table.put(val, val);
                for (int val : toDelete) table.remove(val);
            });
        }
        System.out.println();
    }

    public static void ChainingSearch(int[] dataSizes) {
        System.out.print("Hash Chaining\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
            measureMemory(() -> {
                HashTableChaining<Integer, Integer> table = new HashTableChaining<>();
                for (int val : data) table.put(val, val);
                for (int val : toSearch) table.get(val);
            });
        }
        System.out.println();
    }

    public static void ProbingInsertion(int[] dataSizes) {
        System.out.print("Hash Probing\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            measureMemory(() -> {
                HashTableQuadraticProbing<Integer, Integer> table = new HashTableQuadraticProbing<>();
                for (int val : data) table.put(val, val);
            });
        }
        System.out.println();
    }

    public static void ProbingDeletion(int[] dataSizes) {
        System.out.print("Hash Probing\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
            measureMemory(() -> {
                HashTableQuadraticProbing<Integer, Integer> table = new HashTableQuadraticProbing<>();
                for (int val : data) table.put(val, val);
                for (int val : toDelete) table.remove(val);
            });
        }
        System.out.println();
    }

    public static void ProbingSearch(int[] dataSizes) {
        System.out.print("Hash Probing\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
            measureMemory(() -> {
                HashTableQuadraticProbing<Integer, Integer> table = new HashTableQuadraticProbing<>();
                for (int val : data) table.put(val, val);
                for (int val : toSearch) table.get(val);
            });
        }
        System.out.println();
    }
}
