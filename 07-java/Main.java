import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    private HashMap<String, Integer> values = new HashMap<>();
    private HashMap<String, Op> ops = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new Main();
    }

    public Main() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        
        String str = "";
        while ((str = reader.readLine()) != null) {
            parse(str);
        }
        
        reader.close();
        int part1 = solve("a");
        System.out.println("Part 1: " + part1);
        ops.replace("b", new Value(part1));
        values = new HashMap<>();
        System.out.println("Part 2: " + solve("a"));
    }

    private void parse(String str) {
        String[] parts = str.split(" ");
        Op op = null;
        LogicGate gate = null;
        Factor f1 = null, f2 = null;
        boolean arrow = false;
        String to = "";

        for (String s : parts) {
            if (s.equals("->")) {
                arrow = true;
                continue;
            }
            if (arrow) {
                to = s;
                break;
            }
            try {
                int val = Integer.parseInt(s);
                if (gate == null) {
                    f1 = new Value(val);
                } else {
                    f2 = new Value(val);
                }
            } catch (NumberFormatException e) {
                switch (s) {
                case "AND":
                    gate = LogicGate.AND;
                    break;
                case "OR":
                    gate = LogicGate.OR;
                    break;
                case "LSHIFT":
                    gate = LogicGate.LSHIFT;
                    break;
                case "RSHIFT":
                    gate = LogicGate.RSHIFT;
                    break;
                case "NOT":
                    gate = LogicGate.NOT;
                    break;
                default:
                    if (gate == null)
                        f1 = new Ref(s);
                    else
                        f2 = new Ref(s);
                    break;
                }
            }
        }

        if (gate == null) {
            op = (Op)f1;
        } else if (gate == LogicGate.RSHIFT || gate == LogicGate.LSHIFT) {
            op = new Shift(gate, f1, (int)((Value)f2).value);
        } else {
            op = new Operation(gate, f1, f2);
        }
        ops.put(to, op);
    }

    private int solve(Factor f) {
        if (f instanceof Value) {
            return ((Value)f).value();
        } else {
            return solve(((Ref)f).key());
        }
    }

    private int solve(String key) {
        if (values.containsKey(key)) return values.get(key);
        int res = 0;
        if (key == null || key.length() == 0) {
            System.exit(-1);
        }

        Op ope = ops.get(key);
        if (ope instanceof Operation) {
            Operation op = (Operation)ope;
            switch (op.gate()) {
            case AND:
                res = ( solve(op.first()) & solve(op.second()) + 65536) % 65536;
                break;
            case NOT:
                res = ( ~solve(op.second()) + 65536) % 65536;
                break;
            case OR:
                res = ( solve(op.first()) | solve(op.second()) + 65536) % 65536;
                break;
            default:
                throw new RuntimeException("Unknown gate " + op.gate());
            }
        } else if (ope instanceof Shift) {
            Shift sh = (Shift)ope;
            switch (sh.gate()) {
            case LSHIFT:
                res = ( ( solve(sh.first) << sh.digits() ) + 65536) % 65536;
                break;
            case RSHIFT:
                res = ( ( solve(sh.first()) >> sh.digits() ) + 65536) % 65536;
                break;
            default:
                throw new RuntimeException("Unknown gate " + sh.gate());
           }
        } else if (ope instanceof Value) {
            res = ((Value)ope).value;
        } else if (ope instanceof Ref) {
            res = solve(((Ref)ope).key());
        } else if (ope == null) {
            System.err.println("NULL: " + key);
            res = 0;
        } else {
            throw new RuntimeException("unreachable");
        }
        
        values.put(key, res);
        return res;
    }

    private interface Op{}

    private interface Factor {}

    private record Shift(LogicGate gate, Factor first, int digits) implements Op{}

    private record Operation(LogicGate gate, Factor first, Factor second) implements Op{}

    private record Value(int value) implements Op, Factor{}

    private record Ref(String key) implements Op, Factor{}

    private enum LogicGate {
        AND,
        OR,
        LSHIFT,
        RSHIFT,
        NOT
    }
}
