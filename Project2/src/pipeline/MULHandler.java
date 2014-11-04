package pipeline;

public class MULHandler {

	private String binaryInstrn;
	private String decodedInstrn;

	private int address;

	private String instrnName;
	private String rs;
	private String rt;
	private String rd;

	private int rsNum;
	private int rtNum;
	private int rdNum;

	private InstructionCategory category = InstructionCategory.ArithmeticReg;
	private InstructionType type = InstructionType.MUL;

	public MULHandler(String binIns, int addr) {
		binaryInstrn = binIns;
		decodedInstrn = "";
		address = addr;
		rsNum = -1;
		rtNum = -1;
		rdNum = -1;
		instrnName = "MUL";
		rs = "R";
		rt = "R";
		rd = "R";

		decodedInstrn = "";

		constructDecodedInstrn();
	}

	/**
	 * Identify the individual fields and construct the instruction in assembly
	 * format
	 */
	public void constructDecodedInstrn() {
		rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
		rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
		rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

		rs = rs + rsNum;
		rt = rt + rtNum;
		rd = rd + rdNum;
		decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;

	}

	public String getDecodedInstrn() {
		return decodedInstrn;
	}

	public String getBinaryInstrn() {
		return binaryInstrn;
	}

	public String getInstrnName() {
		return instrnName;
	}

	public String getRs() {
		return rs;
	}

	public String getRt() {
		return rt;
	}

	public String getRd() {
		return rd;
	}
 

	public int getRsNum() {
		return rsNum;
	}
 
	public int getRtNum() {
		return rtNum;
	}
	public int getRdNum() {
		return rdNum;
	}

	public InstructionCategory getCategory() {
		return category;
	}

	public InstructionType getType() {
		return type;
	}

	public String getInstrn() {
		// TODO Auto-generated method stub
		return instrnName;
	}

}
