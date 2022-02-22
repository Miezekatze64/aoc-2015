import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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
    
    protected int countReplacements(String str) {
        return getReplacements(replacements, str).length;
    }

    protected String[] replace(String[] list, int index, String[] value) {
        String[] res = new String[list.length+value.length-1];
        for (int i = 0; i < index; i++) {
            res[i] = list[i];
        }
        for (int i = index; i < index+value.length; i++) {
            res[i] = value[i-index];
        }
        for (int i = index+value.length; i < value.length + list.length -1; i++) {
            res[i] = list[i-value.length];
        }
        return res;
    }

    protected int part2(String med) {
        String[] molecule = new String[]{"e"};
        HashSet<String[]> molecules = new HashSet<>();
        molecules.add(molecule);
        int count = 0;
        while (!contains(molecules, split(med))) {
            final int size = molecules.size();
            final var mol2 = new HashSet<String[]>(molecules);
            final var list = mol2.stream().toList();
            for (int i = 0; i < size; i++) {
                step(list.get(i), molecules);
                molecules.remove(list.get(i));
            }

            System.out.println(list.get(0).length);

            count++;
        }
        return count;
    }

    protected boolean contains(HashSet<String[]> set, String[] arr) {
        var list = set.stream().toList();
        for (int i = 0; i < set.size(); i++) {
            String[] fst = list.get(i);
            if (Arrays.equals(fst, arr)) return true;
        }
        return false;
    }

    protected void step(String[] arr, HashSet<String[]> set) {
        for (int i = 0; i < arr.length; i++) {
            for (String s : getReplacements(replacements, arr[i])) {
                String[] arr2 = replace(arr, i, split(s));
                set.add(arr2);
            }
        }
    }

    protected HashSet<String> generate(String[] arr) {
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

    protected String[] getReplacements(LinkedList<Replacement> replacements, String key) {
        LinkedList<String> list = new LinkedList<String>();
        replacements.forEach((Replacement s) -> {
            if (s.key().equals(key)) {
                list.add(s.value());
            }
        });
        return list.toArray(new String[0]);
    }

    protected String[] split(String s) {
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
