package pipeline;

public class BREAKHandler {

	private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    
    private String instrnName;
        
    private InstructionCategory category = InstructionCategory.Break;
   	private InstructionType type = InstructionType.BREAK; 
       
    public BREAKHandler(String binIns, int addr) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;         
        instrnName = "BREAK";        
        constructDecodedInstrn();
}
/**
 * Identify the individual fields and construct the instruction in assembly format 
 */
public void constructDecodedInstrn()
{

	decodedInstrn = instrnName;
                 
}
public String getInstrn()
{
	return instrnName;
	
}
public String getDecodedInstrn()
{
	return decodedInstrn;
}

public InstructionCategory getCategory() {
	return category;
}

public InstructionType getType() {
	return type;
}
}
