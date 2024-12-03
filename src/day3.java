import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class day3 {
    public static void main(String[] args) {
        String input = read();
        System.out.println(solvePartOne(input));
        System.out.println(solvePartTwo(input));
    }

    public static String read() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/input3.txt"))) {
            return br.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static long solvePartOne(String input) {
        final Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

        return pattern.matcher(input).results().mapToLong(matchResult -> {
            long nr1 = Long.parseLong(matchResult.group(1));
            long nr2 = Long.parseLong(matchResult.group(2));
            return nr1 * nr2;
        }).sum();
    }

    public static long solvePartTwo(String input) {
        boolean enabled = true;

        // find all do(), don't(), mul(nr1,nr2), and get them as a string / 2 nr
        // for do enable, for don't disable, for mul multiply

        final Pattern pattern = Pattern.compile("(do\\(\\)|don't\\(\\)|mul\\((\\d+),(\\d+)\\))");
        final Matcher matcher = pattern.matcher(input);
        long result = 0;



        while (matcher.find()) {
            String match = matcher.group(0);
            if (match.equals("do()")) {
                enabled = true;
            } else if (match.equals("don't()")) {
                enabled = false;
            } else {
                if (enabled) {
                    long nr1 = Long.parseLong(matcher.group(2));
                    long nr2 = Long.parseLong(matcher.group(3));
                    result += nr1 * nr2;
                }
            }
        }

        return result;
    }
}

// 178538786
// 7535446 too low
// 17353406 too low
// 102467299

/*
2 min
regex: find all expressions like mul(nr1,nr2) and add all nr1*nr2
6 min

1 min
regex: find all expressions like do() then random shit (that doesn't contain any don't()) mul(nr1,nr2)
*/