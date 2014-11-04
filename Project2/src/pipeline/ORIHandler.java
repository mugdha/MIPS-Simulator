package pipeline;
public class ORIHandler {

	public void setRsNum(int rsNum) {
		this.rsNum = rsNum;
	}

	public void setRtNum(int rtNum) {
		this.rtNum = rtNum;
	}

	public void setImmValue(int immValue) {
		this.immValue = immValue;
	}

	private String binaryInstrn;
	private String decodedInstrn;
	private int address;
	
	private String instrnName;
	private String rs;
	private String rt;
	private String immStr;
	 

	private int rsNum;
	private int rtNum;
	private int immValue;
 
	private InstructionCategory category = InstructionCategory.ArithmeticImm;
	private InstructionType type = InstructionType.ORI;

	public ORIHandler(String binIns, int addr) {
		binaryInstrn = binIns;
		decodedInstrn = "";
		address = addr;
		rsNum = -1;
		rtNum = -1;
		immValue = -1;

		instrnName = "ORI";
		rs = "R";
		rt = "R";
		immStr = "#";

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

		immValue = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16,
				32));

		rs = rs + rsNum;
		rt = rt + rtNum;
		immStr = immStr + immValue;

		decodedInstrn = instrnName + " " + rt + ", " + rs + ", " + immStr;

	}

	public String getDecodedInstrn() {
		return decodedInstrn;
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
	public String getImmStr() {
		return immStr;
	}

	public int getRsNum() {
		return rsNum;
	} 
	public int getRtNum() {
		return rtNum;
	}

 

	public int getImmValue() {
		return immValue;
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
