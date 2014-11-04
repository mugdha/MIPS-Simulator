package pipeline;

public class LWHandler {
    private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    private String instrnName;
 
    private String rt;
    private int rtNum;

    private String base;// rs
    private int baseNum;

    private int offsetVal; //rd
    
    private InstructionCategory category = InstructionCategory.Load;
   	private InstructionType type = InstructionType.LW; 
   
    
    public LWHandler(String binIns, int addr) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        instrnName = "LW";
        address = addr;
        rtNum = -1;
        baseNum = -1;

        rt = "R";
        base = "R";
        
        constructDecodedInstrn();
}

    public void constructDecodedInstrn() {
            baseNum = Integer.parseInt(binaryInstrn.substring(6, 11), 2);
            rtNum = Integer.parseInt(binaryInstrn.substring(11, 16), 2);
            offsetVal = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16,
                            32));

            base = base + baseNum;
            rt = rt + rtNum;

            decodedInstrn = instrnName + " " + rt + ", " + offsetVal + "(" + base + ")";
            
    } 

	public String getRt() {
		return rt;
	}

	public int getRtNum() {
		return rtNum;
	}

	public String getBase() {
		return base;
	}

	public int getBaseNum() {
		return baseNum;
	}

	public int getOffsetVal() {
		return offsetVal;
	}

	
	public InstructionCategory getCategory() {
		return category;
	}

	public InstructionType getType() {
		return type;
	}
	
	
	public String getDecodedInstrn() {
		return decodedInstrn;
	}
	public String getInstrn()
	{
		String instrn = instrnName + " " + rt + ", " + offsetVal + "(" + base
										+ ")";
		return instrn;
	}
}
