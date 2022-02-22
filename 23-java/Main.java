import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private ArrayList<Instruction> instructions = new ArrayList<>();
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
        int[] registers = run(instructions, false);
        System.out.println("Part 1: " + registers[1]);

        registers = run(instructions, true);
        System.out.println("Part 2: " + registers[1]);

    }

    public int[] run(List<Instruction> list, boolean part2) {
        int ip = 0;
        int a = part2?1:0;
        int b = 0;

        while (true) {
            if (ip >= list.size()) break;
            Instruction inst = list.get(ip);
            switch (inst.type()) {
            case HLF:
                if (inst.register() == Register.A) {
                    a /= 2;
                } else {
                    b /= 2;
                }
                ip++;
                break;
            case INC:
                if (inst.register() == Register.A) {
                    a++;
                } else {
                    b++;
                }
                ip++;
                break;
            case JIE:
                if (inst.register() == Register.A) {
                    if (a % 2 == 0) ip += inst.offset(); else ip++;
                } else {
                    if (a % 2 == 0) ip += inst.offset(); else ip++;
                }
                break;
            case JIO:
                if (inst.register() == Register.A) {
                    if (a == 1) ip += inst.offset(); else ip++;
                } else {
                    if (a == 1) ip += inst.offset(); else ip++;
                }
               break;
            case JMP:
                ip += inst.offset();
                break;
            case TPL:
                 if (inst.register() == Register.A) {
                    a *= 3;
                } else {
                    b *= 3;
                }
                ip++;
                break;
            } 

        }
        
        return new int[]{a, b};
    }

    public void parse(String str) {
        str = str.replaceAll(",", "");
        String[] split = str.split(" ");
        String instruction = split[0];
        Register register = null;
        int offset = 0;
        
        InstructionType type = parseType(instruction);
        String fst = split[1];
        if (fst.startsWith("+") || fst.startsWith("-")) {
            offset = Integer.parseInt(fst);
        } else {
            register = (split[1].equals("a")) ? Register.A : Register.B;
        }
        if (split.length > 2) offset = Integer.parseInt(split[2]);

        instructions.add(new Instruction(type, register, offset));
    }

    public record Instruction(InstructionType type, Register register, int offset) {}

    public enum InstructionType {
        HLF,
        TPL,
        INC,
        JMP,
        JIE,
        JIO,
    }

    public enum Register {
        A,
        B,
    }


    public InstructionType parseType(String instruction) {
        return switch (instruction) {
            default -> null;
            case "hlf" -> InstructionType.HLF;
            case "tpl" -> InstructionType.TPL;
            case "inc" -> InstructionType.INC;
            case "jmp" -> InstructionType.JMP;
            case "jie" -> InstructionType.JIE;
            case "jio" -> InstructionType.JIO;
        };

    }
}
