public class Main {
    public static final int ROW = 3010;
    public static final int COL = 3019;

    public static void main(String[] args) {
        int x = 1;
        int y = 1;
        long val = 20151125L;

        while (x != COL || y != ROW) {
            val = next(val);
            if (y == 1) {
                y = x+1;
                x = 1;
            } else {
                y--;
                x++;
            }
        }
        System.out.println("Part 1: " + val);
    }

    private static long next(long prev) {
        return (prev*252533L)%33554393L;
    }
}
