import java.io.*;
import java.util.ArrayList;
import static java.lang.Math.max;

public class Main {

    private boolean[][] lights = new boolean[1000][1000];
    private long[][] lights2 = new long[1000][1000];
    private ArrayList<Instruction> instructions = new ArrayList<Instruction>();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new Main();
    }

    public Main() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String str = "";
        while ((str = reader.readLine()) != null) {
            parse(str);
        }
        reader.close();

        for (Instruction i : instructions) {
            Point start = i.start();
            Point end = i.end();
            for (int x = start.x(); x <= end.x(); x++) {
                for (int y = start.y(); y <= end.y(); y++) {
                    switch (i.action()) {
                    case ON:
                        lights[x][y] = true;
                        lights2[x][y]++;
                        break;
                    case OFF:
                        lights[x][y] = false;
                        lights2[x][y] = max(lights2[x][y]-1, 0);
                        break;
                    case TOGGLE:
                        lights[x][y] = !lights[x][y];
                        lights2[x][y] += 2;
                        break;
                    }
                }
            }
        }

        int count = 0;
        for (boolean[] arr : lights) {
            for (boolean light : arr) {
                if (light) count++;
            }
        }
        
        long brightness = 0;
        for (long[] arr : lights2) {
            for (long light : arr) {
                brightness += light;
            }
        }

        System.out.println("Part 1: " + count);
        System.out.println("Part 2: " + brightness);
    }

    private void parse(String str) {
        Action action;
        String rest = "";
        if (str.startsWith("toggle ")) {
            action = Action.TOGGLE;
            rest = str.substring("toggle ".length());
        } else if (str.startsWith("turn on ")) {
            action = Action.ON;
            rest = str.substring("turn on ".length());
        } else if (str.startsWith("turn off ")) {
            action = Action.OFF;
            rest = str.substring("turn off ".length());
        } else {
            throw new RuntimeException("Unkown action");
        }
        String[] points = rest.split(" through ");
        String[] firstStr = points[0].split(",");
        String[] secondStr = points[1].split(",");
        Point first = new Point(toInt(firstStr[0]), toInt(firstStr[1]));
        Point second = new Point(toInt(secondStr[0]), toInt(secondStr[1]));
        instructions.add(new Instruction(action, first, second));
    }

    private record Instruction(Action action, Point start, Point end) {
/*        public String toString() {
            return action + 
        }*/
    }

    private int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    private record Point(int x, int y) {
        
    }

    private enum Action {
        TOGGLE,
        ON,
        OFF
    }
}
