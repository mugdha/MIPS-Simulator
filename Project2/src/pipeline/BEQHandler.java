package pipeline;

public class BEQHandler {

	private String binaryInstrn;
	private String decodedInstrn;
	private int address;
	private String instrnName;
	public void setRsNum(int rsNum) {
		this.rsNum = rsNum;
	}

	public void setRtNum(int rtNum) {
		this.rtNum = rtNum;
	}

	public void setOffsetVal(int offsetVal) {
		this.offsetVal = offsetVal;
	}

	private String rs;
	private String rt;
	private String offsetStr;

	public String getOffsetStr() {
		return offsetStr;
	}

	public void setOffsetStr(String offsetStr) {
		this.offsetStr = offsetStr;
	}

	private int rsNum;
	private int rtNum;
	private int offsetVal;

	private InstructionCategory category = InstructionCategory.Branch;
	private InstructionType type = InstructionType.BEQ;

	public InstructionCategory getCategory() {
		return category;
	}

	public InstructionType getType() {
		return type;
	}

	public String getRt() {
		return rt;
	}

	public int getRtNum() {
		return rtNum;
	}

	public String getRs() {
		return rs;
	}

	public int getRsNum() {
		return rsNum;
	}

	public int getOffsetVal() {
		return offsetVal;
	}

	public BEQHandler(String binIns, int addr) {
		binaryInstrn = binIns;
		decodedInstrn = "";
		address = addr;
		rsNum = -1;
		rtNum = -1;
		offsetVal = -1;

		instrnName = "BEQ";
		rs = "R";
		rt = "R";
		offsetStr = "#";

		constructDecodedInstrn();
	}

	/**
	 * Identify the individual fields and construct the instruction in assembly
	 * format
	 */
	public void constructDecodedInstrn() {
		rsNum = Integer.parseInt(binaryInstrn.substring(6, 11), 2);
		rtNum = Integer.parseInt(binaryInstrn.substring(11, 16), 2);

		// offset is 18 bits i.e. 16bit offset is shifted left 2 bits.
		String offsetBin = binaryInstrn.substring(16, 32) + "00";
		offsetVal = Integer.parseInt(offsetBin, 2);
		rs = rs + rsNum;
		rt = rt + rtNum;
		offsetStr = offsetStr + offsetVal;

		decodedInstrn = instrnName + " " + rs + ", " + rt + ", " + offsetStr;
	}

	public String getDecodedInstrn() {
		return decodedInstrn;
	}

	public String getInstrn() {
		String instrn = instrnName + " " + rs + ", " + rt + ", " + offsetStr;
		return instrn;
	}
}
