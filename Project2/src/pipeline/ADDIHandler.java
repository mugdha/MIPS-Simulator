package pipeline;

public class ADDIHandler {
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
	private InstructionType type = InstructionType.ADDI; 

     
	public ADDIHandler(String binIns, int addr) {
		binaryInstrn = binIns;
		decodedInstrn = "";
		address = addr;
		rsNum = -1;
		rtNum = -1;
		immValue = -1;

		instrnName = "ADDI";
		rs = "R";
		rt = "R";
		immStr = "#";

		decodedInstrn = "";

		constructDecodedInstrn();
	}
    
        public void constructDecodedInstrn()
    {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
            
        immValue = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16, 32));
            
            rs = rs + rsNum;
            rt = rt +  rtNum;
            immStr = immStr + immValue;
            
            decodedInstrn = instrnName + " " + rt + ", " + rs + ", " + immStr;   
             
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

    	public String getBinaryInstrn() {
    		return binaryInstrn;
    	}
 

    	public int getAddress() {
    		return address;
    	}
 

    	public String getInstrnName() {
    		return instrnName;
    	}
    
    	public int getImmValue() {
    		return immValue;
    	}

    	public String getDecodedInstrn()
    	{
    		return decodedInstrn;
    	}
    	
    	public String getInstrn()
    	{
    		String instrn = instrnName + " " + rt + ", " + rs + ", " + immStr;;
    		return instrn;
    	}

		public InstructionCategory getCategory() {
			 
			return category;
		}

		public InstructionType getType() {
			// TODO Auto-generated method stub
			return type;
		}
    
 
	 
}
