import java.util.Random;

public class Double {
    public static void main(String[] args) {
        int rowsA = 2000;
        int colsA = 1200;
        int rowsB = 1200;
        int colsB = 2400;

        // Generate random matrices with doubles
        double[][] matrixA = generateRandomMatrix(rowsA, colsA, 100);
        double[][] matrixB = generateRandomMatrix(colsA, colsB, 100);

        // Record start time
        long startTime = System.nanoTime();

        // Perform matrix multiplication
        double[][] result = multiplyMatrices(matrixA, matrixB);

        // Record end time
        long endTime = System.nanoTime();

        // Display the result matrix;
        System.out.println("Result Matrix (Double):");
        displayMatrix(result);

        // Calculate and display the elapsed time in seconds
        double elapsedTimeInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Double Program execution time: " + elapsedTimeInSeconds + " seconds");
    }

    // Function to generate a random matrix with doubles
    private static double[][] generateRandomMatrix(int rows, int cols, int maxRandomValue) {
        double[][] matrix = new double[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextDouble() * 10.0; 
                // Random doubles between 0.0 and 10.0
            }
        }
        return matrix;
    }

    // Function to multiply two matrices
    private static double[][] multiplyMatrices(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int rowsB = matrixB.length;
        int colsB = matrixB[0].length;

        // Check if matrices can be multiplied
        if (colsA != rowsB) {
            System.out.println("Cannot multiply matrices. Incompatible dimensions.");
            return null;
        }

        double[][] result = new double[rowsA][colsB];

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
    private static void displayMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }
}
