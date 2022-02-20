import java.io.*;

public class Main {
    public static String input = "1113222113";

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        String val = input;
        int length = 50;
        for (int i = 0; i < length; i++) {
            val = step(val);
            System.out.println(i + ": " + val.length());
            if (i == 39) System.out.println("Part 1: " + val.length());
            if (i == 49) System.out.println("Part 2: " + val.length());

        }
    }

    private String step(String str) {
        StringBuilder result = new StringBuilder();
        int count = 0;
        char lastChar = 0;
        for (char c : str.toCharArray()) {
            if (lastChar == c) {
                count++;
            } else {
                if (count > 0) result.append(count).append(lastChar);
                count = 1;
                lastChar = c;
            }
        }
        if (count > 0) result.append(count).append(lastChar);
        return result.toString();
    }
}
