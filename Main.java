import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        //collects data sizes from user and stores in array
        int inputData[] = new int[3];
        collectDataSize(inputData);
        System.out.println();

        //prints out table for time performance of each data structure for each operation
        String operations[] = {"Insertion", "Deletion", "Search (Lookup) Operation"};
        for (String operation : operations) {
            printTableTitleTime(operation);
            printTableHeader(inputData);

            if (operation.equals("Insertion")) {
                Time.AVLInsertion(inputData);
                Time.SplayInsertion(inputData);
                Time.ChainingInsertion(inputData);
                Time.ProbingInsertion(inputData);
            }
            if (operation.equals("Deletion")) {
                Time.AVLDeletion(inputData);
                Time.SplayDeletion(inputData);
                Time.ChainingDeletion(inputData);
                Time.ProbingDeletion(inputData);
            }
            if (operation.equals("Search (Lookup) Operation")) {
                Time.AVLSearch(inputData);
                Time.SplaySearch(inputData);
                Time.ChainingSearch(inputData);
                Time.ProbingSearch(inputData);
            }

            System.out.println();

        }

        //prints out the table for memory performance of each data structure for each operation
        for (String operation : operations) {
            printTableTitleMemory(operation);
            printTableHeader(inputData);

            if (operation.equals("Insertion")) {
                Memory.AVLInsertion(inputData);
                Memory.SplayInsertion(inputData);
                Memory.ChainingInsertion(inputData);
                Memory.ProbingInsertion(inputData);
            }
            if (operation.equals("Deletion")) {
                Memory.AVLDeletion(inputData);
                Memory.SplayDeletion(inputData);
                Memory.ChainingDeletion(inputData);
                Memory.ProbingDeletion(inputData);
            }
            if (operation.equals("Search (Lookup) Operation")) {
                Memory.AVLSearch(inputData);
                Memory.SplaySearch(inputData);
                Memory.ChainingSearch(inputData);
                Memory.ProbingSearch(inputData);
            }

            System.out.println();
        }
    }

    //scanner function to collect data size from user
    public static void collectDataSize(int[] dataSizes) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            System.out.print("Please enter an input data size: ");
            dataSizes[i] = scanner.nextInt();
        }
        scanner.close();
    }
    //general function to print the table header for each data structure
    public static void printTableHeader(int[] dataSizes) {
        System.out.println("Data Structure\t\t" + dataSizes[0] + " Elements\t\t" +
                           dataSizes[1] + " Elements\t\t" + dataSizes[2] + " Elements");
    }

    //time performance table header for each data structure
    public static void printTableTitleTime(String operation) {
        System.out.println(operation + " Performance Comparison (Time in milliseconds): ");
        System.out.println("--------------------------------------------------------------------------------------------");
    }

    //memory performance table header for each data structure
    public static void printTableTitleMemory(String operation) {
        System.out.println(operation + " Performance Comparison (Memory in Kilobytes): ");
        System.out.println("--------------------------------------------------------------------------------------------");
    }
}
