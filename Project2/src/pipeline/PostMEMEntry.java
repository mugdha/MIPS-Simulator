package pipeline;

import java.io.Serializable;

public class PostMEMEntry implements Serializable{
	
	String instruction;
	String reg;
	int regtobeUpdatedVal;
	
	String rs;
	String rt;
	String rd;	
	int rtNum;
	int rdNum;
	int rsNum;
	

	public PostMEMEntry(String instrn, String destreg, int desrRegValue,String rs, String rt, String rd,int rsNum, int rtNum, int rdNum) {
		
		this.instruction=instrn;
		this.reg=destreg;
		this.regtobeUpdatedVal=desrRegValue;	
		this.rd=rd;
		this.rs=rs;
		this.rt=rt;
		this.rsNum=rsNum;
		this.rtNum=rtNum;
		this.rdNum=rdNum;
	
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
 	public String getInstrn() {
		 
		return instruction;
	}

	public String getInstruction() {
		return instruction;
	}

	public String getReg() {
		return reg;
	}
	
 	public int getRegtobeUpdatedVal() {
		return regtobeUpdatedVal;
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
