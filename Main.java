import java.util.Random;
import java.util.Scanner; 

public class Main {

    public static void main(String[] args) {
        int inputData[] = new int[3]; 
        collectDataSize(inputData); 
        System.out.println(); 
    
        String operations[] = {"Insertion", "Deletion", "Search (Lookup) Operation"}; 
        for (String operation : operations) {
            printTableTitle(operation); 
            printTableHeader(inputData);

            if (operation.equals("Insertion")) {
                AVLInsertion(inputData); 
                SplayInsertion(inputData);
                ChainingInsertion(inputData);
                ProbingInsertion(inputData);
            }
            if (operation.equals("Deletion")) {
                AVLDeletion(inputData);
                SplayDeletion(inputData);
                ChainingDeletion(inputData);
                ProbingDeletion(inputData);
            }
            if (operation.equals("Search (Lookup) Operation")) {
                AVLSearch(inputData); 
                SplaySearch(inputData);
                ChainingSearch(inputData);
                ProbingSearch(inputData);
            }
            System.out.println();
        }
    }

    public static void collectDataSize(int[] dataSizes) {
        Scanner scanner = new Scanner(System.in); 
        for (int i = 0; i < 3; i++) {
            System.out.print("Please enter an input data size: "); 
            dataSizes[i] = scanner.nextInt();
        }
        scanner.close();
    }

    public static void printTableHeader(int[] dataSizes) {
        System.out.println("Data Structure\t\t" + dataSizes[0] + " Elements\t\t" + 
                          dataSizes[1] + " Elements\t\t" + dataSizes[2] + " Elements");
    }

    public static void printTableTitle(String operation) {
        System.out.println(operation +"Performance Comparison (Time in milliseconds): "); 
        System.out.println("--------------------------------------------------------------------------------------------");
    }

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

    public static void AVLInsertion(int[] dataSizes) {
        System.out.print("AVL Tree\t\t");
        for (int size : dataSizes) {
            Integer[]data = generateRandomArray(size); 
            AVLTree<Integer> avl = new AVLTree<>(); 
            long start = System.nanoTime(); 
            for (int val : data) {
                avl.insert(val);
            }
            long end = System.nanoTime(); 
            long time = (end - start) / 1_000_000; 
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println(); 
    }
    
    public static void AVLDeletion(int[] dataSizes) {
        System.out.print("AVL Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
    
            AVLTree<Integer> avl = new AVLTree<>();
            for (int val : data) {
                avl.insert(val); // pre-fill the tree
            }
    
            long start = System.nanoTime();
            for (int val : toDelete) {
                avl.remove(val);
            }
            long end = System.nanoTime();
    
            long time = (end - start) / 1_000_000;
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println();
    }
    

    public static void AVLSearch(int[] dataSizes) {
        System.out.print("AVL Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
    
            AVLTree<Integer> avl = new AVLTree<>();
            for (int val : data) {
                avl.insert(val); 
            }
    
            long start = System.nanoTime();
            for (int val : toSearch) {
                avl.contains(val);
            }
            long end = System.nanoTime();
    
            long time = (end - start) / 1_000_000;
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println();
    }

    public static void SplayInsertion(int[] dataSizes) {
        System.out.print("Splay Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            SplayTree<Integer> splay = new SplayTree<>();
            long start = System.nanoTime();
            for (int val : data) {
                splay.insert(val);
            }
            long end = System.nanoTime();
            long time = (end - start) / 1_000_000;
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println();
    }

    public static void SplayDeletion(int[] dataSizes) {
        System.out.print("Splay Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
    
            SplayTree<Integer> splay = new SplayTree<>();
            for (int val : data) {
                splay.insert(val);
            }
    
            long start = System.nanoTime();
            for (int val : toDelete) {
                splay.remove(val);
            }
            long end = System.nanoTime();
    
            long time = (end - start) / 1_000_000;
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println();
    }

    public static void SplaySearch(int[] dataSizes) {
        System.out.print("Splay Tree\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
    
            SplayTree<Integer> splay = new SplayTree<>();
            for (int val : data) {
                splay.insert(val);
            }
    
            long start = System.nanoTime();
            for (int val : toSearch) {
                splay.contains(val);
            }
            long end = System.nanoTime();
    
            long time = (end - start) / 1_000_000;
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println();
    }    
    
    public static void ChainingInsertion(int[] dataSizes) {
        System.out.print("Hash Chaining\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            HashTableChaining<Integer, Integer> table = new HashTableChaining<>();
            long start = System.nanoTime();
            for (int val : data) {
                table.put(val, val);
            }
            long end = System.nanoTime();
            long time = (end - start) / 1_000_000;
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println();
    }
    
    public static void ChainingDeletion(int[] dataSizes) {
        System.out.print("Hash Chaining\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
            HashTableChaining<Integer, Integer> table = new HashTableChaining<>();
            for (int val : data) {
                table.put(val, val);
            }
            long start = System.nanoTime();
            for (int val : toDelete) {
                table.remove(val);
            }
            long end = System.nanoTime();
            long time = (end - start) / 1_000_000;
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println();
    }

    public static void ChainingSearch(int[] dataSizes) {
        System.out.print("Hash Chaining\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
            HashTableChaining<Integer, Integer> table = new HashTableChaining<>();
            for (int val : data) {
                table.put(val, val);
            }
            long start = System.nanoTime();
            for (int val : toSearch) {
                table.get(val);
            }
            long end = System.nanoTime();
            long time = (end - start) / 1_000_000;
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println();
    }

    public static void ProbingInsertion(int[] dataSizes) {
        System.out.print("Hash Probing\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            HashTableQuadraticProbing<Integer, Integer> table = new HashTableQuadraticProbing<>();
            long start = System.nanoTime();
            for (int val : data) {
                table.put(val, val);
            }
            long end = System.nanoTime();
            long time = (end - start) / 1_000_000;
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println();
    }
    
    public static void ProbingDeletion(int[] dataSizes) {
        System.out.print("Hash Probing\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toDelete = getSubset(data, size / 10);
            HashTableQuadraticProbing<Integer, Integer> table = new HashTableQuadraticProbing<>();
            for (int val : data) {
                table.put(val, val);
            }
            long start = System.nanoTime();
            for (int val : toDelete) {
                table.remove(val);
            }
            long end = System.nanoTime();
            long time = (end - start) / 1_000_000;
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println();
    }
    
    public static void ProbingSearch(int[] dataSizes) {
        System.out.print("Hash Probing\t\t");
        for (int size : dataSizes) {
            Integer[] data = generateRandomArray(size);
            Integer[] toSearch = getSubset(data, size / 10);
            HashTableQuadraticProbing<Integer, Integer> table = new HashTableQuadraticProbing<>();
            for (int val : data) {
                table.put(val, val);
            }
            long start = System.nanoTime();
            for (int val : toSearch) {
                table.get(val);
            }
            long end = System.nanoTime();
            long time = (end - start) / 1_000_000;
            System.out.print(time + "ms\t\t\t");
        }
        System.out.println();
    }    
    
}