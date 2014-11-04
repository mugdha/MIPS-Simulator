package pipeline;

import java.io.Serializable;

public class FetchBranchQEntry implements Serializable{

	String instrn; // String representation of the instruction
	InstructionCategory category; // To determine if ALU/Load/Store/Branch
	InstructionType instrnType; // Specific instruction opcode

	// Source and destination operands
	String rs;
	String rt;
	String rd;
	

    int rsNum;
    int rtNum;
    int rdNum;
	
	int PC;
	

	public FetchBranchQEntry(String instrn, InstructionCategory category,
			InstructionType instrnType, String rs, String rt, String rd,int rsNum,int rtNum,int rdNum) {
		super();
		this.instrn = instrn;
		this.category = category;
		this.instrnType = instrnType;
		this.rs = rs;
		this.rt = rt;
		this.rd = rd;
		this.rsNum=rsNum;
		this.rtNum=rtNum;
		this.rdNum=rdNum;
		
		
	}

	public FetchBranchQEntry(String instrn,InstructionType instrnType, String rs, String rt, String rd) {
		super();
		this.instrn = instrn;
		this.instrnType = instrnType;
		this.rs = rs;
		this.rt = rt;
		this.rd = rd;
		
	}

	public int getPC() {
		return PC;
	}

	public void setPC(int pc) {
		PC = pc;
	}

	public String getInstrn() {
		return instrn;
	}

	public void setInstrn(String instrn) {
		this.instrn = instrn;
	}

	public InstructionCategory getCategory() {
		return category;
	}

	public void setCategory(InstructionCategory category) {
		this.category = category;
	}

	public InstructionType getInstrnType() {
		return instrnType;
	}

	public void setInstrnType(InstructionType instrnType) {
		this.instrnType = instrnType;
	}

	public String getRs() {
		return rs;
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

	public int getRtNum() {
		return rtNum;
	}

	public int getRdNum() {
		return rdNum;
	}
 
	

}
