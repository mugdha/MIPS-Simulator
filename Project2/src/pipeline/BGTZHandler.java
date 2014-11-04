package pipeline;

public class BGTZHandler {

	private String binaryInstrn;
    private String decodedInstrn;
    
    private int address;
    
    private String instrnName;
    private String rs;     
    private String offsetStr;   
    
    private int rsNum;  
    
    public int getRsNum() {
		return rsNum;
	}

	public void setRsNum(int rsNum) {
		this.rsNum = rsNum;
	}

	public void setOffsetVal(int offsetVal) {
		this.offsetVal = offsetVal;
	}

	private int offsetVal;

    private InstructionCategory category = InstructionCategory.Branch;
   	private InstructionType type = InstructionType.BGTZ; 
   	

	public String getBinaryInstrn() {
		return binaryInstrn;
	}

	public int getAddress() {
		return address;
	}

	public String getInstrnName() {
		return instrnName;
	}

	public String getRs() {
		return rs;
	}
 
	public String getOffsetStr() {
		return offsetStr;
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

   	
   	public BGTZHandler(String binIns, int adresscurrent)
	{
		binaryInstrn = binIns;
 
        decodedInstrn = "";
        address = adresscurrent;
        rsNum = -1;
        offsetVal = -1;
        
        instrnName = "BGTZ";
        rs = "R";        
        offsetStr = "#";        

        constructDecodedInstrn();
}

    /**
	 * Identify the individual fields and construct the instruction in assembly
	 * format
	 */
	private void constructDecodedInstrn() {
		
		  rsNum = Integer.parseInt(binaryInstrn.substring(6, 11), 2);
		  String offsetBin = binaryInstrn.substring(16, 32) + "00";
		  offsetVal = Integer.parseInt(offsetBin, 2);
          rs = rs + rsNum;         
         offsetStr = offsetStr + offsetVal;
         decodedInstrn = instrnName + " " + rs + ", " + offsetStr;         
		 
	}
	public String getDecodedInstrn() {
		return decodedInstrn;
	}
	
	public String getInstrn()
	{
		String instrn = instrnName + " " + rs + ", " + offsetStr;
		return instrn;
	}
	 
	 
	}
