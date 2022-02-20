import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.max;

public class Main {
    public ArrayList<Ingredient> ingredients = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new Main();
    }

    public Main() throws FileNotFoundException, IOException {
        var reader = new BufferedReader(new FileReader("./input.txt"));
        String str = "";
        while ((str = reader.readLine()) != null) {
            parse(str);
        }
        reader.close();

        int max = Integer.MIN_VALUE;
        for (int[] arr : getAll()) {
            int[] res = get(ingredients, arr);
            max = max(max, res[0] * res[1] * res[2] * res[3]);
        }
        System.out.println("Part 1: " + max);

        max = Integer.MIN_VALUE;
        for (int[] arr : getAll()) {
            int[] res = get(ingredients, arr);
            if (res[4] == 500)
                max = max(max, res[0] * res[1] * res[2] * res[3]);
        }
        System.out.println("Part 2: " + max);

    }

    ArrayList<int[]> getAll() {
        ArrayList<int[]> result = new ArrayList<>();
        for (int j = 0; j < 100; j++) 
            for (int k = 0; k <= 100-j; k++) 
                for (int l = 0; l <= 100-j-k; l++) 
                    for (int m = 0; m <= 100-j-k-l; m++) 
                        result.add(new int[]{j, k, l, m});
        return result;
    }

    private int[] get(ArrayList<Ingredient> ingredients, int[] nums) {
        int[] result = new int[5];
        assert nums.length == ingredients.size();
        int index = 0;
        for (Ingredient in : ingredients) {
            for (int i = 0; i < 5; i++)
                result[i] += nums[index]*in.get(i);
            index++;
        }
        
        for (int i = 0; i < result.length; i++) {
            if (result[i] < 0) result[i] = 0;
        }
        
        return result;
    }

    public void parse(String line) {
        String[] split = line.split(":");
        String name = split[0];
        String rest = split[1];

        String[] split2 = rest.split(", ");

        int capacity = 0, durability = 0, flavor = 0, texture = 0, calories = 0;

        for (String s : split2) {
            String[] split3 = s.trim().split(" ");
            String propertyName = split3[0].trim();
            String property = split3[1].trim();

            switch(propertyName) {
            case "capacity":
                capacity = Integer.parseInt(property);
                break;
            case "durability":
                durability = Integer.parseInt(property);
                break;
            case "flavor":
                flavor = Integer.parseInt(property);
                break;
            case "texture":
                texture = Integer.parseInt(property);
                break;
            case "calories":
                calories = Integer.parseInt(property);
                break;
            default:
                throw new RuntimeException("Unknown property " + propertyName);
            }
        }

        ingredients.add(new Ingredient(name, capacity, durability, flavor, texture, calories));
    }

    public record Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
    public int get(int index) {
        switch (index) {
        case 0:
            return capacity;
        case 1:
            return durability;
        case 2:
            return flavor;
        case 3:
            return texture;
        case 4:
            return calories;
        default:
            return -1;
        }
    }
    }
}
