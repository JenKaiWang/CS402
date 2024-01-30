import java.util.Random;

public class DoubleSecond {
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

        // Display the result matrix
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
                matrix[i][j] = random.nextDouble() * maxRandomValue;
                // Random doubles between 0.0 and maxRandomValue
            }
        }
        return matrix;
    }

    // Function to multiply two matrices using Strassen algorithm
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

        // Check if dimensions are power of 2
        int maxSize = Math.max(Math.max(rowsA, colsA), Math.max(rowsB, colsB));
        int n = 1;
        while (n < maxSize) {
            n *= 2;
        }

        // Pad matrices to make dimensions power of 2
        double[][] paddedA = padMatrix(matrixA, n);
        double[][] paddedB = padMatrix(matrixB, n);

        // Perform Strassen multiplication
        double[][] result = strassenMultiply(paddedA, paddedB);

        // Remove padding from result
        return extractSubmatrix(result, rowsA, colsB);
    }

    // Function to pad a matrix to make dimensions power of 2
    private static double[][] padMatrix(double[][] matrix, int size) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] paddedMatrix = new double[size][size];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, paddedMatrix[i], 0, cols);
        }

        return paddedMatrix;
    }

    // Function to extract a submatrix from a padded matrix
    private static double[][] extractSubmatrix(double[][] matrix, int rows, int cols) {
        double[][] submatrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, submatrix[i], 0, cols);
        }
        return submatrix;
    }

    // Function to perform Strassen multiplication
    private static double[][] strassenMultiply(double[][] A, double[][] B) {
        int n = A.length;

        if (n <= 64) {
            // Base case: Use standard matrix multiplication for small matrices
            return standardMultiply(A, B);
        }

        // Split matrices into quadrants
        double[][] A11 = new double[n / 2][n / 2];
        double[][] A12 = new double[n / 2][n / 2];
        double[][] A21 = new double[n / 2][n / 2];
        double[][] A22 = new double[n / 2][n / 2];
        double[][] B11 = new double[n / 2][n / 2];
        double[][] B12 = new double[n / 2][n / 2];
        double[][] B21 = new double[n / 2][n / 2];
        double[][] B22 = new double[n / 2][n / 2];

        splitMatrix(A, A11, A12, A21, A22);
        splitMatrix(B, B11, B12, B21, B22);

        // Compute products of submatrices
        double[][] P1 = strassenMultiply(add(A11, A22), add(B11, B22));
        double[][] P2 = strassenMultiply(add(A21, A22), B11);
        double[][] P3 = strassenMultiply(A11, subtract(B12, B22));
        double[][] P4 = strassenMultiply(A22, subtract(B21, B11));
        double[][] P5 = strassenMultiply(add(A11, A12), B22);
        double[][] P6 = strassenMultiply(subtract(A21, A11), add(B11, B12));
        double[][] P7 = strassenMultiply(subtract(A12, A22), add(B21, B22));

        // Compute result submatrices
        double[][] C11 = add(subtract(add(P1, P4), P5), P7);
        double[][] C12 = add(P3, P5);
        double[][] C21 = add(P2, P4);
        double[][] C22 = add(subtract(add(P1, P3), P2), P6);

        // Combine result submatrices
        double[][] result = new double[n][n];
        joinMatrix(C11, C12, C21, C22, result);

        return result;
    }

    // Function to perform standard matrix multiplication
    private static double[][] standardMultiply(double[][] A, double[][] B) {
        int n = A.length;
        int m = A[0].length;
        int p = B[0].length;

        double[][] result = new double[n][p];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < m; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return result;
    }

    // Function to split a matrix into quadrants
    private static void splitMatrix(double[][] matrix, double[][] topLeft, double[][] topRight, double[][] bottomLeft, double[][] bottomRight) {
        int size = matrix.length / 2;

        for (int i = 0; i < size; i++) {
            System.arraycopy(matrix[i], 0, topLeft[i], 0, size);
            System.arraycopy(matrix[i], size, topRight[i], 0, size);
            System.arraycopy(matrix[i + size], 0, bottomLeft[i], 0, size);
            System.arraycopy(matrix[i + size], size, bottomRight[i], 0, size);
        }
    }

    // Function to combine four submatrices into a matrix
    private static void joinMatrix(double[][] topLeft, double[][] topRight, double[][] bottomLeft, double[][] bottomRight, double[][] result) {
        int size = topLeft.length;

        for (int i = 0; i < size; i++) {
            System.arraycopy(topLeft[i], 0, result[i], 0, size);
            System.arraycopy(topRight[i], 0, result[i], size, size);
            System.arraycopy(bottomLeft[i], 0, result[i + size], 0, size);
            System.arraycopy(bottomRight[i], 0, result[i + size], size, size);
        }
    }

    // Function to add two matrices
    private static double[][] add(double[][] A, double[][] B) {
        int n = A.length;
        int m = A[0].length;

        double[][] result = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = A[i][j] + B[i][j];
            }
        }

        return result;
    }

    // Function to subtract one matrix from another
    private static double[][] subtract(double[][] A, double[][] B) {
        int n = A.length;
        int m = A[0].length;

        double[][] result = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = A[i][j] - B[i][j];
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
