package pipeline;
import java.util.HashMap;
import java.util.Map;


public class Opcodes {

    public static Map<InstructionType, String> category1Map = new HashMap<InstructionType, String>();
    public static Map<InstructionType, String> category2Map = new HashMap<InstructionType, String>();
    public static Map<InstructionType, String> category3Map = new HashMap<InstructionType, String>();
    public Opcodes()
 {
		category1Map.put(InstructionType.J, "000");
		category1Map.put(InstructionType.BEQ, "010");
		category1Map.put(InstructionType.BGTZ, "100");
		category1Map.put(InstructionType.BREAK, "101");
		category1Map.put(InstructionType.SW, "110");
		category1Map.put(InstructionType.LW, "111");

		category2Map.put(InstructionType.ADD, "000");
		category2Map.put(InstructionType.SUB, "001");
		category2Map.put(InstructionType.MUL, "010");
		category2Map.put(InstructionType.AND, "011");
		category2Map.put(InstructionType.OR, "100");
		category2Map.put(InstructionType.XOR, "101");
		category2Map.put(InstructionType.NOR, "110");

		category3Map.put(InstructionType.ADDI, "000");
		category3Map.put(InstructionType.ANDI, "001");
		category3Map.put(InstructionType.ORI, "010");
		category3Map.put(InstructionType.XORI, "011");

	}
}
