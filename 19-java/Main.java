import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    private LinkedList<Replacement> replacements = new LinkedList<>();
    private String start = "";

    public static void main(String[] args) throws IOException, FileNotFoundException {
        new Main();
    }

    public Main() throws IOException, FileNotFoundException {
        var reader = new BufferedReader(new FileReader("./input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            parse(line);
        }
        reader.close();

        String[] arr = split(start);
        System.out.println("Part 1: " + generate(arr).size());

        System.out.println("Part 2: " + part2(start));
    }
    
    protected int count(String[] search, String str) {
        int count = 0;
        for (String s : search) if (str.equals(s)) count++;
        return count;
    }

    protected int part2(String med) {
        var molecule = split(med);
        return molecule.length //length
            - 2*count(molecule, "Rn")   // Rn and Ar are grouping elements and are never on the left side
            - 2*count(molecule, "Y")    // seperator between two grouped elements
            - 1;                        // electron
    }

    private HashSet<String> generate(String[] arr) {
        HashSet<String> list = new HashSet<>();
        for (int index = 0; index < arr.length; index++) {
            for (String s : getReplacements(replacements, arr[index])) {
                String tmp = "";
                for (int i = 0; i < index; i++) {
                    tmp += arr[i];
                }
                tmp += s;
                for (int i = index+1; i < arr.length; i++) {
                    tmp += arr[i];
                }
                list.add(tmp);
            }
        }
        return list;
    }

    private String[] getReplacements(LinkedList<Replacement> replacements, String key) {
        LinkedList<String> list = new LinkedList<String>();
        replacements.forEach((Replacement s) -> {
            if (s.key().equals(key)) {
                list.add(s.value());
            }
        });
        return list.toArray(new String[0]);
    }

    private String[] split(String s) {
        LinkedList<String> result = new LinkedList<>();
        String tmp = "";

        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) {
                tmp += c;
            } else {
                if (tmp.length() > 0) result.add(tmp);
                tmp = c+"";
            }
        }

        result.add(tmp);

        return result.toArray(new String[0]);
    }

    protected void parse(String str) {
        if (str.contains("=>")) {
            String[] split = str.trim().split("=>");
            String element = split[0].trim();
            String molecule = split[1].trim();

            replacements.add(new Replacement(element, molecule));
        } else if (str.length() > 0) {
            this.start = str.trim();
        }
    }

    public record Replacement(String key, String value) {}
}
