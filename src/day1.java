import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class day1 {
    record ListPair(List<Integer> a, List<Integer> b) {}

    private static ListPair readInput() {
        final ListPair input = new ListPair(new ArrayList<>(), new ArrayList<>());

        try (BufferedReader reader = new BufferedReader(new FileReader("data/input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                input.a().add(Integer.parseInt(parts[0]));
                input.b().add(Integer.parseInt(parts[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return input;
    }

    private static int solvePartOne(ListPair input) {
        Collections.sort(input.a());
        Collections.sort(input.b());

        int sum = 0;

        for (int i = 0; i < input.a().size(); i++) {
            sum += Math.abs(input.a().get(i) - input.b().get(i));
        }

        return sum;
    }

    private static int solvePartTwo(ListPair input) {
        int similarityScore = 0;
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();

        for (int b : input.b()) {
            frequencyMap.put(b, frequencyMap.getOrDefault(b, 0) + 1);
        }

        for (int a : input.a()) {
            similarityScore += a * frequencyMap.getOrDefault(a, 0);
        }

        return similarityScore;
    }

    public static void main(String[] args) {
        final ListPair input = readInput();
        System.out.println(solvePartOne(input));
        System.out.println(solvePartTwo(input));
    }
}

/*
Ideas
    - sort each list + go line by line: O(n log n)
    - more efficient would be O(n), which is not possible?
*/