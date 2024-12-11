import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day5 {
    private static boolean isOrdered(List<Integer> xs, HashMap<Integer, HashSet<Integer>> ordering) {
        HashSet<Integer> alreadyAdded = new HashSet<>();

        for (int x : xs) {
            alreadyAdded.add(x);
            if (!ordering.containsKey(x)) continue;

            for (int y : ordering.get(x)) {
                if (alreadyAdded.contains(y)) return false;
            }
        }

        return true;
    }

    private static int middleUnordered(List<Integer> xs, HashMap<Integer, HashSet<Integer>> ordering) {
        final HashSet<Integer> set = new HashSet<>(xs);
        final HashMap<Integer, Integer> scores = new HashMap<>();

        for (int x : xs) {
            if (!ordering.containsKey(x)) continue;

            for (int y : ordering.get(x)) {
                if (set.contains(y)) {
                    scores.compute(x, (_, v) -> v == null ? 1 : v + 1);
                    scores.compute(y, (_, v) -> v == null ? -1 : v - 1);
                }
            }
        }

        final List<Map.Entry<Integer, Integer>> sortedEntries = new ArrayList<>(scores.entrySet());
        // could make more efficient by using quick select, but Java doesn't have a built-in implementation
        sortedEntries.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        return sortedEntries.get(sortedEntries.size() / 2).getKey();
    }

    public static void main(String[] args) {
        HashMap<Integer, HashSet<Integer>> ordering = new HashMap<>();
        int sumOrdered = 0;
        int sumUnordered = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("data/input5.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) break;

                String[] parts = line.split("\\|");
                int before = Integer.parseInt(parts[0]);
                int after = Integer.parseInt(parts[1]);
                if (!ordering.containsKey(before)) ordering.put(before, new HashSet<>());
                ordering.get(before).add(after);
            }

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                List<Integer> numbers = new ArrayList<>();
                for (String part : parts) numbers.add(Integer.parseInt(part));

                if (isOrdered(numbers, ordering)) {
                    sumOrdered += numbers.get(numbers.size() / 2);
                } else {
                    sumUnordered += middleUnordered(numbers, ordering);
                }
            }

            System.out.println(sumOrdered);
            System.out.println(sumUnordered);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

/*
Part 1: 30 min
Part 2 code: 10 min

hashmap with ordering (before -> after)
hashmap with already added

find nrs out of order and see where to move them back. Do this recursively? (would it even stop?)
get all relevant pairs (before, after) where both before and after are in the list
    order them by nr of times they need to be before & after (before: +1, after: -1)
*/