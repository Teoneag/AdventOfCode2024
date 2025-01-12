import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    public static void main(String[] args) {
        final char[][] input = read();

        final Point startPoint = Point.fromSymbol(input);

        partOne(deepCopy2DArray(input), startPoint);

        // 345 - too low
        // 346 - too low
        partTwo(input, startPoint);
    }

    record Point(int i, int j, Direction direction) {
        public static Point fromSymbol(char[][] input) {
            for (int i = 0, inputLength = input.length; i < inputLength; i++) {
                for (int j = 0, charsLength = input[i].length; j < charsLength; j++) {
                    for (Direction direction : Direction.values()) {
                        if (direction.symbol == input[i][j]) return new Point(i, j, direction);
                    }
                }
            }
            throw new RuntimeException("No starting point found");
        }
    }

    enum Direction {
        UP('^', -1, 0, 1),
        DOWN('v', 1, 0, 2),
        LEFT('<', 0, -1, 4),
        RIGHT('>', 0, 1, 8);

        public final char symbol;
        public final int iDir, jDir, code;

        Direction(char symbol, int iDir, int jDir, int code) {
            this.symbol = symbol;
            this.iDir = iDir;
            this.jDir = jDir;
            this.code = code;
        }

        public Direction rotateRight() {
            return switch (this) {
                case UP -> RIGHT;
                case DOWN -> LEFT;
                case LEFT -> UP;
                case RIGHT -> DOWN;
            };
        }

        public char addCode(char code) {
            return (char) (this.code | code);
        }

        public boolean containsCode(char code) {
            if (code > 16) return false;
            return (this.code & code) == this.code;
        }
    }

    private static void partOne(char[][] input, Point startPoint) {
        int i = startPoint.i, j = startPoint.j;
        Direction direction = startPoint.direction;
        int nr = 0;

        while (i < input.length && i >= 0 && j < input[i].length && j >= 0) {
            if (input[i][j] == '#') {
                // correct going one too far
                i -= direction.iDir;
                j -= direction.jDir;

                direction = direction.rotateRight();
            }
            if (input[i][j] != 'X') nr++;
            input[i][j] = 'X';

            i += direction.iDir;
            j += direction.jDir;
        }

        System.out.println(nr);
    }

    private static boolean isLoop(char[][] input, Point startPoint) {
        int i = startPoint.i, j = startPoint.j;
        Direction direction = startPoint.direction;

        while (i < input.length && i >= 0 && j < input[i].length && j >= 0) {
            if (direction.containsCode(input[i][j])) {
//                print(input);
//                System.out.println(i + " " + j);
                return true;
            }

            char oldChar = input[i][j];
            input[i][j] = direction.addCode(oldChar > 16 ? 0 : oldChar);
            if (oldChar == '#') {
                // correct going one too far
                i -= direction.iDir;
                j -= direction.jDir;

                direction = direction.rotateRight();
            }

            i += direction.iDir;
            j += direction.jDir;
        }

        return false;
    }

    private static void partTwo(char[][] input, Point startPoint) {
        int nr = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == '.') {
                    final char[][] input2 = deepCopy2DArray(input);
                    input2[i][j] = '#';
                    if (isLoop(input2, startPoint)) {
                        nr++;
//                        System.out.println(i + " " + j);
                    }
                }
            }
        }

        System.out.println(nr);
    }

    private static void print(char[][] input) {
        for (char[] chars : input) {
            for (char aChar : chars) {
                if ((int) aChar > 16) System.out.print(aChar);
                else System.out.print((int) aChar);
            }
            System.out.println();
        }
    }

    private static char[][] read() {
        final List<String> input = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/input6.txt"))) {
            String line;
            while ((line = br.readLine()) != null) input.add(line);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return input.stream().map(String::toCharArray).toArray(char[][]::new);
    }

    public static char[][] deepCopy2DArray(char[][] original) {
        char[][] copy = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new char[original[i].length];
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }
}

/*


add an obstruction on each point, check if it runs in a loop?

follow the path point by point
8:03
*/