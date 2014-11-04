package pipeline;

public class NORHandler {

    private String binaryInstrn;
    private String decodedInstrn;

    private int address;
    
    private String instrnName;
    private String rs;
    private String rt;
    private String rd;
   
    public int getRsNum() {
		return rsNum;
	}

	public int getRtNum() {
		return rtNum;
	}

	public int getRdNum() {
		return rdNum;
	}

	public void setRsNum(int rsNum) {
		this.rsNum = rsNum;
	}

	public void setRtNum(int rtNum) {
		this.rtNum = rtNum;
	}

	public void setRdNum(int rdNum) {
		this.rdNum = rdNum;
	}

	private int rsNum;
    private int rtNum;
    private int rdNum;

 
    
    private InstructionCategory category = InstructionCategory.ArithmeticReg;
   	private InstructionType type = InstructionType.NOR; 
    
    public NORHandler(String binIns, int addr) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        rdNum = -1;
      
        
        instrnName = "NOR";
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
    
    public InstructionCategory getCategory() {
		return category;
    }

    public InstructionType getType() {
		return type;
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

	public String getInstrn() {
		return (instrnName + " " + rd + ", " + rs + ", " + rt);
	}

}
