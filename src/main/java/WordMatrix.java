import java.util.*;

public class WordMatrix {

    private int[][] solution;
    private int path = 1;

    public WordMatrix(int N) {
        solution = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                solution[i][j] = 0;
            }
        }
    }

    public boolean searchWord(char[][] matrix, String word) {
        int N = matrix.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (search(matrix, word, i, j, 0, N)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean search(char[][] matrix, String word, int row, int col, int index, int N) {
        // check if current cell not already used
        if (solution[row][col] != 0 || word.charAt(index) != matrix[row][col]) {
            return false;
        }
        if (index == word.length() - 1) {
            // word is found, return true
            solution[row][col] = path++;
            return true;
        }
        // mark the current cell
        solution[row][col] = path++;
        // down
        if (row + 1 < N && search(matrix, word, row + 1, col, index + 1, N)) {
            return true;
        }
        // up
        if (row - 1 >= 0 && search(matrix, word, row - 1, col, index + 1, N)) {
            return true;
        }
        // right
        if (col + 1 < N && search(matrix, word, row, col + 1, index + 1, N)) {
            return true;
        }
        // left
        if (col - 1 >= 0 && search(matrix, word, row, col - 1, index + 1, N)) {
            return true;
        }
        // if none of the option works out, BACKTRACK and return false
        solution[row][col] = 0;
        path--;
        return false;
    }

    public void print() {
        Map<Integer, String> output = new TreeMap<>();
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution.length; j++) {

                System.out.print(solution[i][j] + " ");
                if (solution[i][j] > 0) {
                    output.put(solution[i][j], "[" + i + "," + j + "]");
                }
            }
            System.out.println();
        }
        System.out.println("Solution: " + output.values());
    }

    public static char[][] getMatrix(String matrixString, int dimension) {
        char[][] matrix = new char[dimension][dimension];
        String[] rows = matrixString.split("(?<=\\G.{" + dimension + "})");
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = rows[i].toCharArray();
        }
        return matrix;
    }

    public static void main(String[] args) {
        //check input:
        if (args.length != 2) {
            System.out.println("Bad args.");
            return;
        }
        String matrixString = args[0].toUpperCase();
        String word = args[1].toUpperCase();

        if (Math.sqrt(matrixString.length()) % 1 != 0 || matrixString.isEmpty() || word.isEmpty()) {
            System.out.println("Bad input.");
            return;
        }

        char[][] matrix = getMatrix(matrixString, (int) Math.sqrt(matrixString.length()));

        WordMatrix w = new WordMatrix(matrix.length);
        if (w.searchWord(matrix, word)) {
            w.print();
        } else {
            System.out.println("No path found.");
        }
    }

}