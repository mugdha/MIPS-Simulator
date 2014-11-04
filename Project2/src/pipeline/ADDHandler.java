package pipeline;

public class ADDHandler {


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
	private InstructionType type = InstructionType.ADD; 

     
    public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getRt() {
		return rt;
	}

	public void setRt(String rt) {
		this.rt = rt;
	}

	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}

	public int getRsNum() {
		return rsNum;
	}

	public void setRsNum(int rsNum) {
		this.rsNum = rsNum;
	}

	public int getRtNum() {
		return rtNum;
	}

	public void setRtNum(int rtNum) {
		this.rtNum = rtNum;
	}

	public int getRdNum() {
		return rdNum;
	}

	public void setRdNum(int rdNum) {
		this.rdNum = rdNum;
	}

	public void setCategory(InstructionCategory category) {
		this.category = category;
	}

	public ADDHandler(String binIns, int addr) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        rdNum = -1;       

        instrnName = "ADD";
        rs = "R";
        rt = "R";
        rd = "R";
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
	public String getInstrn()
	{
		String instrn = instrnName + " " + rd + ", " + rs + ", " + rt;
		return instrn;
	}
     
}
