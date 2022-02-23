import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Main {
    ArrayList<Long> packages = new ArrayList<>();
    public static void main(String[] args) throws FileNotFoundException, IOException {
        new Main();
    }
    
    public Main() throws FileNotFoundException, IOException{
        var reader = new BufferedReader(new FileReader("./input.txt"));
        String str = "";
        while ((str = reader.readLine()) != null) {
            packages.add((long)Integer.parseInt(str));
        }
        reader.close();

        var list = new ArrayList<List<Long>>();
        final long MAX3 = sum(packages)/3;
        int minSize = Integer.MAX_VALUE;

        for (int i = 0; i < packages.size(); i++) {
            if (i > minSize) continue;
            var combinations = findCombinations(packages, i);
            for (var x : combinations) {
                if (sum(x) == MAX3) {
                    list.add(x);
                    if (minSize > x.size()) minSize = x.size();
                }
            }
        }

        long minProd = Long.MAX_VALUE;
        for (var l : list) {
            if (prod(l) < minProd) minProd = prod(l);
        }

        System.out.println("Part 1: " + minProd);
 
        list = new ArrayList<List<Long>>();
        final long MAX4 = sum(packages)/4;
        minSize = Integer.MAX_VALUE;

        for (int i = 0; i < packages.size(); i++) {
            if (i > minSize) continue;
            var combinations = findCombinations(packages, i);
            for (var x : combinations) {
                if (sum(x) == MAX4) {
                    list.add(x);
                    if (minSize > x.size()) minSize = x.size();
                }
            }
        }

        minProd = Long.MAX_VALUE;
        for (var l : list) {
            if (prod(l) < minProd) minProd = prod(l);
        }

        System.out.println("Part 2: " + minProd);
       
    }

    public static Set<List<Long>> findCombinations(List<Long> A, int k) {
        Set<List<Long>> subarrays = new HashSet<>();
        findCombinations(A, 0, k, subarrays, new ArrayList<>());
        return subarrays;
    }

    public static void findCombinations(List<Long> A, int i, int k, Set<List<Long>> subarrays, List<Long> out) {
        if (A.size() == 0 || k > A.size()) {
            return;
        }
 
        if (k == 0) {
            subarrays.add(new ArrayList<>(out));
            return;
        }
 
        for (int j = i; j < A.size(); j++)
        {
            out.add(A.get(j));
            findCombinations(A, j + 1, k - 1, subarrays, out);
            out.remove(out.size() - 1);        // backtrack
        }
    }

    private long sum(List<Long> list) {
        long sum = 0;
        for (long i : list) {
            sum += i;
        }
        return sum;
    }

    private long prod(List<Long> list) {
        long prod = 1;
        for (long i : list) {
            prod *= i;
        }
        return prod;
   }
}
