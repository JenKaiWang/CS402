import java.util.Random;

public class Integer {
    public static void main(String[] args) {
        int rowsA = 2000;
        int colsA = 1200;
        int rowsB = 1200;
        int colsB = 2400;

        // Generate random matrices with integers
        int[][] matrixA = generateRandomMatrix(rowsA, colsA, 100);
        int[][] matrixB = generateRandomMatrix(colsA, colsB, 100);

        // Record start time
        long startTime = System.nanoTime();

        // Perform matrix multiplication
        int[][] result = multiplyMatrices(matrixA, matrixB);

        // Record end time
        long endTime = System.nanoTime();

        // Display the result matrix
        System.out.println("Result Matrix (Integer):");
        displayMatrix(result);

        // Calculate and display the elapsed time
        double elapsedTime = (endTime - startTime) /1_000_000_000.0;
        System.out.println("Integer Program execution time: " + elapsedTime + " seconds");
    }

    // Function to generate a random matrix with integers
    private static int[][] generateRandomMatrix(int rows, int cols, int maxRandomValue) {
        int[][] matrix = new int[rows][cols];
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(10); // Random integers between 0 and 9
            }
        }
        return matrix;
    }

    // Function to multiply two matrices
    private static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int rowsB = matrixB.length;
        int colsB = matrixB[0].length;

        // Check if matrices can be multiplied
        if (colsA != rowsB) {
            System.out.println("Cannot multiply matrices. Incompatible dimensions.");
            return null;
        }

        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return result;
    }

    // Function to display a matrix
    private static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }
}
