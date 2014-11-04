package pipeline;

import java.io.Serializable;

public class PreMEMEntry implements Serializable{
 
	String instrn;
	String destreg;
	int calculatedddress;
	InstructionType type;
	InstructionCategory categ;
	
	String rs;
	String rt;
	String rd;	
	int rtNum;
	int rdNum;
	int rsNum;
	
	public PreMEMEntry(String instruction, InstructionType typee,InstructionCategory categg,int operationResult, String destReg,String rs, String rt, String rd,int rsNum, int rtNum, int rdNum) {
		
		this.instrn=instruction;
		this.calculatedddress=operationResult;
		this.destreg=destReg;	
		this.type=typee;	
		this.categ=categg;
		this.rd=rd;
		this.rs=rs;
		this.rt=rt;
		this.rsNum=rsNum;
		this.rtNum=rtNum;
		this.rdNum=rdNum;
		
		
	}

	public String getInstrn() {
		return instrn;
	}
	

	public InstructionCategory getCategory() {
		return categ;
	}
	public String getdestreg() {
		return destreg;
	}
	public InstructionType getInstructionType() {
		return type;
	}
 
	public int getCalculatedValue() {
		return calculatedddress;
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
 	
	public int getRtNum() {
		return rtNum;
	}

	public int getRdNum() {
		return rdNum;
	}

	public int getRsNum() {
		return rsNum;
	}
}
