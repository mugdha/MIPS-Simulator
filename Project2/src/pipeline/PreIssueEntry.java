package pipeline;
import java.io.Serializable;

/**
 *  it shows what data/instruction will enter the pre issue queue
 * 
 */
public class PreIssueEntry implements Serializable{
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
	
	int PC;

	public PreIssueEntry(String instrn, InstructionCategory category,
			InstructionType instrnType, String rs, String rt, String rd,int rsNum, int rtNum, int rdNum) {
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
