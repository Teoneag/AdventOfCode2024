import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
    private static List<String> read() {
        final List<String> input = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/input6.txt"))) {
            String line;
            while ((line = br.readLine()) != null) input.add(line);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return input;
    }

    private static void partOne(List<String> input) {
        int i, j = 0;

        int iDir = 0, jDir = 0;
        for (i = 0; i < input.size(); i++) {
            if (input.get(i).contains("^")) {
                j = input.get(i).indexOf("^");
                iDir = -1;
                break;
            }

            if (input.get(i).contains("v")) {
                j = input.get(i).indexOf("v");
                iDir = 1;
                break;
            }

            if (input.get(i).contains("<")) {
                j = input.get(i).indexOf("<");
                jDir = -1;
                break;
            }

            if (input.get(i).contains(">")) {
                j = input.get(i).indexOf(">");
                jDir = 1;
                break;
            }
        }

        int nr = 0;

        while (i < input.size() && i >= 0 && j < input.get(i).length() && j >= 0) {
            if (input.get(i).charAt(j) == '#') {
                // correct going one too far
                i -= iDir;
                j -= jDir;

                // rotate right
                if (iDir == 0) {
                    int temp = iDir;
                    iDir = jDir;
                    jDir = -temp;
                } else {
                    int temp = jDir;
                    jDir = -iDir;
                    iDir = temp;
                }
            }
            if (input.get(i).charAt(j) != 'X') nr++;
            input.set(i, input.get(i).substring(0, j) + "X" + input.get(i).substring(j + 1));

            i += iDir;
            j += jDir;
        }

        System.out.println(nr);
    }

    private static boolean isLoop(List<String> input) {
        int i, j = 0;
        int iDir = 0, jDir = 0;

        for (i = 0; i < input.size(); i++) {
            if ((j = input.get(i).indexOf("^")) != -1) {
                iDir = -1;
                break;
            } else if ((j = input.get(i).indexOf("v")) != -1) {
                iDir = 1;
                break;
            } else if ((j = input.get(i).indexOf("<")) != -1) {
                jDir = -1;
                break;
            } else if ((j = input.get(i).indexOf(">")) != -1) {
                jDir = 1;
                break;
            }
        }

        while (i < input.size() && i >= 0 && j < input.get(i).length() && j >= 0) {
//            print(input);
            int direction;
            if (iDir == 0) direction = 0;
            else direction = 1;
            char current = input.get(i).charAt(j);
            // 3 bits
            if (input.get(i).charAt(j) == ("" + direction).charAt(0)) {

                return true;
            }

            char oldChar = input.get(i).charAt(j);
            input.set(i, input.get(i).substring(0, j) + direction + input.get(i).substring(j + 1));
            if (oldChar == '#') {
                // correct going one too far
                i -= iDir;
                j -= jDir;

                // rotate right
                if (iDir == 0) {
                    int temp = iDir;
                    iDir = jDir;
                    jDir = -temp;
                } else {
                    int temp = jDir;
                    jDir = -iDir;
                    iDir = temp;
                }
            }
            i += iDir;
            j += jDir;
        }

        return false;
    }

    private static void partTwo(List<String> input) {
        int nr = 0;

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
//                if (input.get(i).charAt(j) == '.') {
                    final List<String> input2 = new ArrayList<>(input);
                    input2.set(i, input2.get(i).substring(0, j) + "#" + input2.get(i).substring(j + 1));
                    if (isLoop(input2)) {
                        nr++;
                        System.out.println(i + " " + j);
                    }
//                }
            }
        }

        System.out.println(nr);
    }

    private static void print(List<String> input) {
        for (String s : input) System.out.println(s);
        System.out.println();
    }

    public static void main(String[] args) {
        final List<String> input = read();
//        partOne(input);

        // 345 - too low
        // 346 - too low
        partTwo(input);
    }
}

/*


add an obstruction on each point, check if it runs in a loop?

follow the path point by point
8:03
*/