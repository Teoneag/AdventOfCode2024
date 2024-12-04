import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
    public static void main(String[] args) {
        final List<String> lines = read();
        System.out.println(solvePartOne(lines));
        System.out.println(solvePartTwo(lines));
    }

    private static int solvePartOne(List<String> lines) {
        int count = 0;

        count += lines.stream().mapToInt(Day4::countXmasInString).sum();
        count += transpose(lines).stream().mapToInt(Day4::countXmasInString).sum();
        count += countDiagonals(lines, true) + countDiagonals(lines, false);

        return count;
    }

    private static int solvePartTwo(List<String> lines) {
        int count = 0;

        for (int i = 0; i < lines.size() - 2; i++) {
            for (int j = 0; j < lines.getFirst().length() - 2; j++) {
                if (isValidMatrix(lines, i, j)) count++;
            }
        }

        return count;
    }

    /**
     * Checks if the matrix is one of these
     * M.S    S.M    S.S    M.M
     * .A.    .A.    .A.    .A.
     * M.S    S.M    M.M    S.S
     */
    private static boolean isValidMatrix(List<String> matrix, int i, int j) {
        if (matrix.get(i + 1).charAt(j + 1) != 'A') return false;

        char tl = matrix.get(i).charAt(j), tr = matrix.get(i).charAt(j + 2);
        char bl = matrix.get(i + 2).charAt(j), br = matrix.get(i + 2).charAt(j + 2);

        return tl != br && tr != bl && Stream.of(tl, tr, bl, br).allMatch(c -> c == 'M' || c == 'S');
    }

    private static int countDiagonals(List<String> matrix, boolean isPrimary) {
        int count = 0;
        int rows = matrix.size(), cols = matrix.getFirst().length();

        for (int start = 0; start < rows + cols - 1; start++) {
            int row = isPrimary ? Math.max(0, start - cols + 1) : Math.max(0, start - rows + 1);
            int col = isPrimary ? Math.max(0, cols - 1 - start) : Math.min(cols - 1, start);

            StringBuilder sb = new StringBuilder();

            while (row < rows && col < cols && row >= 0 && col >= 0) {
                sb.append(matrix.get(row).charAt(col));
                row++;
                col += isPrimary ? 1 : -1;
            }

            count += countXmasInString(sb.toString());
        }

        return count;
    }

    private static List<String> transpose(List<String> matrix) {
        List<String> transposed = new ArrayList<>();

        for (int j = 0; j < matrix.getFirst().length(); j++) {
            StringBuilder sb = new StringBuilder();
            for (String row : matrix) sb.append(row.charAt(j));
            transposed.add(sb.toString());
        }

        return transposed;
    }

    private static int countXmasInString(String input) {
        return countStringInString(input) + countStringInString(reverseString(input));
    }

    private static int countStringInString(String input) {
        return (input.split("XMAS", -1).length - 1);
    }

    private static String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    private static List<String> read() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/input4.txt"))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

/*
Part 1: 1h

Part 2: 20m
go over all 3x3 matrices, and check if they are valid
*/