import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.max;

public class Main {
    public ArrayList<Reindeer> reindeers = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new Main();
    }

    public Main() throws FileNotFoundException, IOException {
        var reader = new BufferedReader(new FileReader("./input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            parse(line);
        }
        reader.close();
        
        for (int i = 0; i < 2503; i++)
            for (Reindeer r : reindeers) {
                r.step();
            }
        int max = Integer.MIN_VALUE;
        for (Reindeer r : reindeers) {
            max = max(r.getPosition(), max);
            r.reset();
        }

        System.out.println("Part 1: " + max);

        int[] points = new int[reindeers.size()];
        for (int i = 0; i < 2503; i++) {
            for (Reindeer r : reindeers) {
                r.step();
            }
            max = Integer.MIN_VALUE;
            for (Reindeer r : reindeers) {
                max = max(r.getPosition(), max);
            }
            for (int ii = 0; ii < reindeers.size(); ii++) {
                if (reindeers.get(ii).getPosition() == max) {
                    points[ii]++;
                }
            }
        }

        max = Integer.MIN_VALUE;
        for (int i : points) {
            max = max(max, i);
        }

        System.out.println("Part 2: " + max);
   }

    public void parse(String str) {
        String[] split = str.split(", but then must rest for ");
        String restString = split[1].split("seconds")[0].trim();
        int rest = Integer.parseInt(restString);

        String left = split[0];
        String[] splitLeft = left.split(" can fly ");
        String name = splitLeft[0];

        String middle = splitLeft[1];
        String[] speedAndTime = middle.split(" for ");
        String speedString = speedAndTime[0].trim().split(" ")[0].trim();
        String timeString = speedAndTime[1].trim().split(" ")[0].trim();

        int speed = Integer.parseInt(speedString);
        int time = Integer.parseInt(timeString);
        
        reindeers.add(new Reindeer(name, speed, time, rest));
    }

    public class Reindeer {
        private String name;
        private int speed, flyTime, restTime;
        private int position = 0;
        private int time = 0;

        public Reindeer(String name, int speed, int flyTime, int restTime) {
            this.name = name;
            this.speed = speed;
            this.flyTime = flyTime;
            this.restTime = restTime;
        }

        public void step() {
            if (time % (flyTime + restTime) < flyTime) {
                position += speed;
            }
            time++;
        }

        public void reset() {
            this.position = 0;
            this.time = 0;
        }

        public int getPosition() {
            return position;
        }
    }
}
