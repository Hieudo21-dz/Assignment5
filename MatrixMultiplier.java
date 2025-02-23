import java.io.*;
import java.util.*;

public class MatrixMultiplier {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (args.length == 1) {
            try {
                int n = Integer.parseInt(args[0]);
                processIntegerInput(n);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Provide either an integer or two valid file names.");
            }
        } else if (args.length == 2) {
            File file1 = new File(args[0]);
            File file2 = new File(args[1]);
            if (file1.exists() && file2.exists()) {
                processFileInput(args[0], args[1]);
            } else {
                System.out.println("One or both files do not exist. Please check the file names.");
            }
        } else {
            System.out.println("Enter an integer (for random matrix generation) or two file names separated by space:");
            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+");
            if (parts.length == 1) {
                try {
                    int n = Integer.parseInt(parts[0]);
                    processIntegerInput(n);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Expected an integer.");
                }
            } else if (parts.length == 2) {
                File file1 = new File(parts[0]);
                File file2 = new File(parts[1]);
                if (file1.exists() && file2.exists()) {
                    processFileInput(parts[0], parts[1]);
                } else {
                    System.out.println("One or both files do not exist.");
                }
            } else {
                System.out.println("Invalid input format.");
            }
        }
        scanner.close();
    }

    public static void processFileInput(String file1, String file2) {
        try {
            int[][] matrixA = readMatrixFromFile(file1);
            int[][] matrixB = readMatrixFromFile(file2);
            if (matrixA[0].length != matrixB.length) {
                System.out.println("Matrix dimensions are not compatible for multiplication.");
                return;
            }
            int[][] product = multiplyMatrices(matrixA, matrixB);
            writeMatrixToFile(product, "matrix3.txt");
            System.out.println("Matrix multiplication complete. Result saved in matrix3.txt");
        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
        }
    }

    public static void processIntegerInput(int n) {
        int[][] matrixA = generateRandomMatrix(n, n);
        int[][] matrixB = generateRandomMatrix(n, n);
        try {
            writeMatrixToFile(matrixA, "matrix1.txt");
            writeMatrixToFile(matrixB, "matrix2.txt");
            System.out.println("Random matrices generated and saved in matrix1.txt and matrix2.txt");
        } catch (IOException e) {
            System.out.println("Error writing matrices to files: " + e.getMessage());
        }
        int[][] product = multiplyMatrices(matrixA, matrixB);
        try {
            writeMatrixToFile(product, "matrix3.txt");
            System.out.println("Matrix multiplication complete. Result saved in matrix3.txt");
        } catch (IOException e) {
            System.out.println("Error writing result matrix to file: " + e.getMessage());
        }
    }

    public static int[][] readMatrixFromFile(String filename) throws IOException {
        List<int[]> rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.trim().split("\\s+");
            int[] row = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                row[i] = Integer.parseInt(tokens[i]);
            }
            rows.add(row);
        }
        br.close();
        int[][] matrix = new int[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            matrix[i] = rows.get(i);
        }
        return matrix;
    }

    public static void writeMatrixToFile(int[][] matrix, String filename) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        for (int[] row : matrix) {
            for (int num : row) {
                bw.write(num + " ");
            }
            bw.newLine();
        }
        bw.close();
    }

    public static int[][] multiplyMatrices(int[][] A, int[][] B) {
        int rowsA = A.length;
        int colsA = A[0].length; // same as rowsB
        int colsB = B[0].length;
        int[][] product = new int[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                int sum = 0;
                for (int k = 0; k < colsA; k++) {
                    sum += A[i][k] * B[k][j];
                }
                product[i][j] = sum;
            }
        }
        return product;
    }

    public static int[][] generateRandomMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextInt(10);
            }
        }
        return matrix;
    }
}