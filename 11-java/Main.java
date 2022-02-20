public class Main {
    public static String PASSWORD = "hxbxwxba";
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        String pass = PASSWORD;
        while (!isValid(pass)) {
            pass = step(pass);
        }
        System.out.println("Part 1: " + pass);
        pass = step(pass);
        while (!isValid(pass)) {
            pass = step(pass);
        }
        System.out.println("Part 2: " + pass);
    }

    protected boolean isValid(String s) {
        return hasThreeIncreasingLetters(s) && !hasBadLetters(s) && hasTwoPairs(s);
    }

    private boolean hasTwoPairs(String s) {
        boolean onePair = false;
        boolean wait = false;
        char lastChar = 0;
        
        for (char c : s.toCharArray()) {
            if (wait) {
                wait = false;
                lastChar = c;
                continue;
            }
            if (c == lastChar && onePair) return true;
            if (c == lastChar && !onePair) {
                onePair = true;
                wait = true;
            }
            lastChar = c;
        }
        return false;
    }

    private boolean hasBadLetters(String s) {
        return s.contains("i") || s.contains("o") || s.contains("l");
    }

    private boolean hasThreeIncreasingLetters(String s) {
        char lastChar = 0;
        boolean two = false;

        for (char c : s.toCharArray()) {
            if (c == lastChar+1 && two) {
                return true;
            }
            two = false;
            if (c == lastChar+1 && !two) {
                two = true;
            }
            lastChar = c;
        }

        return false;
    }

    protected String step(String pass) {
        String result = pass;
        boolean inc = true;
        int i = pass.length()-1;
        while (inc) {
            int tmpi = i;
            inc = false;
            char c = pass.charAt(i);
            char c2 = c;
            if (c < 'a') throw new RuntimeException("Invalid letter: " + c);
            if (c < 'z') c2++;
            if (c == 'z') {
                c2 = 'a';
                inc = true;
                i--;
            }
            if (c > 'z') throw new RuntimeException("Invalid letter: " + c);
            result = new StringBuilder(result.substring(0, tmpi)).append(c2).append(result.substring(tmpi+1)).toString();
        }
        return result;
    }
}
