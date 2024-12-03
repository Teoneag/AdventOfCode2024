import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.function.Function;

public class day2 {

    public static void main(String[] args) {
        solve(day2::isSafePartOne);
        solve(day2::isSafePartTwo);
    }

    private static void solve(Function<int[], Boolean> isSafe) {
        int nrSafeLevels = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("data/input2.txt"))) {
            System.out.println(reader.lines()
                    .map(line -> Arrays.stream(line.split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray())
                    .filter(isSafe::apply)
                    .count());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isSafePartOne(int[] reports) {
        int comparison = Integer.compare(reports[0], reports[1]);
        if (comparison == 0) return false;

        for (int i = 0; i < reports.length - 1; i++) {
            final int diff = Math.abs(reports[i] - reports[i + 1]);
            if (diff < 1 || diff > 3 || comparison != Integer.compare(reports[i], reports[i + 1])) {
                return false;
            }
        }

        return true;
    }

    public static boolean isSafePartTwo(int[] reports) {
        for (int i = 0; i < reports.length; i++) {
            int[] newReports = new int[reports.length - 1];
            System.arraycopy(reports, 0, newReports, 0, i);
            System.arraycopy(reports, i + 1, newReports, i, reports.length - i - 1);
            if (isSafePartOne(newReports)) {
                return true;
            }
        }
        return false;
    }
}

/*
2 min
1: go over each report, keep in mind if we're increasing/decreasing (compare 1st 2 nr to determine this)
2: if we get an unsafe part, remove the thing, then check if it's safe again
*/