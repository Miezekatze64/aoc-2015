import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static java.lang.Math.max;

public class Main {
    public HashMap<String, Person> persons = new HashMap<>();
    public static void main(String[] args) throws FileNotFoundException, IOException {
        new Main();
    }

    public Main() throws FileNotFoundException, IOException {
        var reader = new BufferedReader(new FileReader("./input.txt"));
        String in = "";
        while ((in = reader.readLine()) != null) {
            parse(in);
        }
        reader.close();
        System.out.println("Part 1: " + solve());
        persons.put("Me", new Person());
        System.out.println("Part 2: " + solve());
    }

    public int solve() {
        ArrayList<String> list = new ArrayList<>(persons.keySet());
        List<List<String>> perms = perm(list);
        int maxCount = Integer.MIN_VALUE;
        for (int i = 0; i < perms.size(); i++) {
            int count = 0;
            for (int j = 0; j < perms.get(i).size(); j++) {
                var person1 = perms.get(i).get(j);
                var person2 = perms.get(i).get((j+1) % perms.get(i).size());
                count += persons.get(person1).get(person2) + persons.get(person2).get(person1);
            }
            maxCount = max(maxCount, count);
        }
        return maxCount;
    }

    public List<List<String>> perm(List<String> original) {
        if (original.isEmpty()) {
            List<List<String>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }

        String firstElement = original.remove(0);
        List<List<String>> returnValue = new ArrayList<>();
        List<List<String>> permutations = perm(original);

        for (List<String> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                List<String> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }


    private void parse(String str) {
        String[] split = str.split("happiness units by sitting next to");
        String[] left = split[0].split("would");
        left[0] = left[0].trim();
        left[1] = left[1].trim();
        String right = split[1].replace(".", "").trim();

        int amount = 0;
        if (left[1].startsWith("gain")) {
            amount = Integer.parseInt(left[1].substring("gain".length()).trim());
        } else if (left[1].startsWith("lose")) {
            amount = -Integer.parseInt(left[1].substring("lose".length()).trim());
        } else {
            throw new RuntimeException("Unknown token " + left[1].split(" ")[0]);
        }

        if (persons.containsKey(left[0])) {
            persons.get(left[0]).add(right, amount);
        } else {
            Person p = new Person();
            p.add(right, amount);
            persons.put(left[0], p);
        }
    }

    public class Person {
        private HashMap<String, Integer> happiness = new HashMap<>();
        public void add(String person, int happy) {
            happiness.put(person, happy);
        }
        public int get(String person) {
            Integer ha = happiness.get(person);
            if (ha == null) return 0;
            return ha;
        }

        public String toString() {
            return happiness.toString();
        }
    }
}
