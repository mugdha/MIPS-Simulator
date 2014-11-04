package pipeline;

public class JHandler {

	private String binaryInstrn;
	private String decodedInstrn;
	private int address;

	private String instrIndexStr;
	private int instrIndexVal;

	private String instrnName;

	private InstructionCategory category = InstructionCategory.Jump;
	private InstructionType type = InstructionType.J;

	public JHandler(String binIns, int addr) {
		address = addr;
		binaryInstrn = binIns;
		decodedInstrn = "";

		instrnName = "J";

		instrIndexVal = -1;
		instrIndexStr = "#";

		constructDecodedInstrn();
	}

	/**
	 * Identify the individual fields and construct the instruction in assembly
	 * format
	 */
	public void constructDecodedInstrn() {
		String insIndexBin = binaryInstrn.substring(6, 32) + "00";
		instrIndexVal = Integer.parseInt(insIndexBin, 2);
		instrIndexStr = instrIndexStr + instrIndexVal;
		decodedInstrn = instrnName + " " + instrIndexStr;

		decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn
				+ "\n";

	}

	public String getDecodedInstrn() {
		return decodedInstrn;
	}

	public String getInstrn() {
		String instrn = instrnName + " " + instrIndexStr;
		return instrn;
	}

	public int getNextjumLevel() {
		return instrIndexVal;
	}

	public InstructionCategory getCategory() {
		return category;
	}

	public InstructionType getType() {
		return type;
	}

	public String getInstrIndexStr() {
		// TODO Auto-generated method stub
		return instrIndexStr;
	}

}
