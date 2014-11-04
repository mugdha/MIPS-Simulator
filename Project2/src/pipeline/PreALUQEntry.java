package pipeline;

import java.io.Serializable;

public class PreALUQEntry implements Serializable{
 
	String instrn; // String representation of the instruction
	InstructionCategory category; // To determine if ALU/Load/Store/Branch
	InstructionType instrnType; // Specific instruction opcode

	// Source and destination operands
	String rs;
	String rt;
	String rd;	
	
	int rtNum;
	int rdNum;
	int rsNum;
 	 	
	public int getRtNum() {
		return rtNum;
	}

	public int getRdNum() {
		return rdNum;
	}

	public int getRsNum() {
		return rsNum;
	}
 	int PC;

	public PreALUQEntry(String instruction, InstructionCategory categ,InstructionType type,String rs, String rt, String rd,int rsNum, int rtNum, int rdNum) {
		
		super();
		this.instrn = instruction;
		this.category = categ;
		this.instrnType = type;
		this.rs = rs;
		this.rt = rt;
		this.rd = rd;	 
		this.rsNum=rsNum;
		this.rtNum=rtNum;
		this.rdNum=rdNum;
		
	}

	public InstructionCategory getCategory() {
		return category;
	}

	public InstructionType getInstrnType() {
		return instrnType;
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
	  String getInstrn() {
		// TODO Auto-generated method stub
		return instrn;
	}

	public void setPC(int pc2) {
		 this.PC=pc2;
		
	}

 
}
