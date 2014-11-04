package pipeline;

public class InstructionInfo {
	 private long Address;
	 private String Instruction;
	 private String Stage;
	 private InstructionType type;
	 private InstructionCategory category;
	 private String InstructionBinary;
	 	 
	 
	 public String getInstructionBinary() {
		return InstructionBinary;
	}
	public void setInstructionBinary(String instructionBinary) {
		InstructionBinary = instructionBinary;
	}
	public InstructionType getType() {
		return type;
	}
	public void setType(InstructionType type2) {
		this.type = type2;
	}
	public InstructionCategory getCategory() {
		return category;
	}
	public void setCategory(InstructionCategory category) {
		this.category = category;
	}
	public void setAddress(long Address){
		 this.Address = Address;
	 }
	 public void setInstruction(String Instruction){
		 this.Instruction = Instruction;
		 
	 }
	 public String getInstruction(){
		 return this.Instruction;
	 }
	 public void setStage(String Stage){
		this.Stage = Stage; 
	 }
	 
	 public String getStage(){
		 return this.Stage;
	 }

}
