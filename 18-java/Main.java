import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int SIZE = 100;
    boolean[][] cells = new boolean[SIZE][SIZE];
    public static void main(String[] args) throws IOException, FileNotFoundException {
        new Main();
    }

    public Main() throws IOException, FileNotFoundException {
        var reader = new BufferedReader(new FileReader("./input.txt"));
        String in = "";
        int count = 0;
        while ((in = reader.readLine()) != null) {
            parse(in, count++);
        }
        reader.close();

        boolean[][] origin = clone(cells);
        show();
        for (int i = 0; i < 100; i++) {
            step(false);
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {}
            show();
        }
        System.out.println("Part 1: " + count());

        System.out.print("Enter to continue to part 2: ");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        sc.close();
    
        cells = clone(origin);
        cells[0][0] = true;
        cells[0][cells.length-1] = true;
        cells[cells.length-1][0] = true;
        cells[cells.length-1][cells.length-1] = true;

        show();
        for (int i = 0; i < 100; i++) {
            step(true);
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {}
            show();
        }
        System.out.println("Part 2: " + count());

    }

    private int count() {
        int count = 0;
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++)
                if (cells[i][j]) count++;
        return count;
    }

    private int getNeighbours(int x, int y, boolean[][] arr) {
        int count = 0;
        for (int i = -1; i <= 1; i++)
           for (int j = -1; j <= 1; j++)
               if (!(j == 0 && i == 0))
                   if (i+x > -1 && i+x < arr.length && j+y > -1 && j+y < arr[i+x].length)
                       if (arr[i+x][j+y]) count++;
        return count;
    }

    public boolean[][] clone(boolean[][] arr) {
        boolean[][] result = new boolean[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr[i].length; j++)
                result[i][j] = arr[i][j];
        return result;
    }
 
    public void step(boolean part2) {
        boolean[][] tmp = clone(cells);
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (part2) {
                    if ((i == 0 && j == 0) || (i == 0 && j == cells.length-1) || (i == cells.length-1 && j == 0) || (i == cells.length-1 && j == cells.length-1)) continue;
                }
               int count = getNeighbours(i, j, tmp);
               if (tmp[i][j]) 
                    cells[i][j] = ( count == 2 || count == 3 );
                else
                    cells[i][j] = ( count == 3 );
            }
        }
    }

    public void clear() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            try {
                Runtime.getRuntime().exec("cls");
            } catch (Exception e) {}
        } else {
            System.out.print("\033[H");
        }
    }

    public void show() {
        clear();
        for (boolean[] line : cells) {
            for (boolean cell : line) {
                System.out.print(cell?'\u25A0':' ');
            }
            System.out.print("\r\n\r");
        }
    }

    public void parse(String line, int ln) {
        int index = 0;
        for (char c : line.toCharArray()) {
            cells[ln][index++] = (c == '#');
        }
    }
}
