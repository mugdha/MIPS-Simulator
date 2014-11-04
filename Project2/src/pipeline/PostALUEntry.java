package pipeline;

import java.io.Serializable;

public class PostALUEntry implements Serializable{

	String instrn;
	String destReg;
	int calculatedValue;
	// Source and destination operands
	String rs;
	String rt;
	String rd;	
	
	int rtNum;
	int rdNum;
	int rsNum;
	
	public PostALUEntry(String instruction, int operationResult, String destReg,String rs, String rt, String rd,int rsNum, int rtNum, int rdNum) {
		
		this.instrn=instruction;
		this.calculatedValue=operationResult;
		this.destReg=destReg;
		this.rs = rs;
		this.rt = rt;
		this.rd = rd;
		this.rsNum=rsNum;
		this.rtNum=rtNum;
		this.rdNum=rdNum;
		
	}

	public String getInstrn() {
		return instrn;
	}

	public String getdestReg() {
		return destReg;
	}
	public int getCalculatedValue() {
		return calculatedValue;
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
