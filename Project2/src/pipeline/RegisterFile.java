package pipeline;

import java.io.Serializable;
import java.util.TreeMap;

public class RegisterFile implements Serializable{

	TreeMap<Integer, Integer> regDataMap = new TreeMap<Integer, Integer>();
	private int maxReg = 32;
	
	boolean[] ready = new boolean[maxReg];
	
	public RegisterFile() {
		/*
		 * Initiate register with zero
		 */
		for (int i = 0; i < 32; i++) {
			regDataMap.put(i, 0);
			this.ready[i]=true;

		}
	}
	
	public RegisterFile clone()
	{
		RegisterFile regFileCopy = new RegisterFile();
		regFileCopy.regDataMap = (TreeMap<Integer, Integer>) this.regDataMap.clone();
		regFileCopy.ready = this.ready.clone();	 		
		return regFileCopy;		
	}
	
	

	public void updateReg(int regNo, int value) {
		regDataMap.put(regNo, value);
	}

	public int getRegVal(int regNo) {
		return regDataMap.get(regNo);
	}
	
	public void setReady(int regNo)
	{
		ready[regNo] = true;
	}
	
	public void setBusy(String reg)
	{
		String r = reg.replace("R", "");
		int regNo = Integer.parseInt(r);		
		ready[regNo] = false;
	}
	 
	public boolean isValueReady(String reg)
	{
		String r = reg.replace("R", "");
		int regNo = Integer.parseInt(r);
		return ready[regNo];
		
	}
	 
	

	/**
	 * Print contents of register file
	 */
	public String print() {
		StringBuffer s1 = new StringBuffer("R00:");
		StringBuffer s2 = new StringBuffer("R08:");
		StringBuffer s3 = new StringBuffer("R16:");
		StringBuffer s4 = new StringBuffer("R24:");

		for (int i = 0; i < 8; i++) {
			s1.append("\t" + regDataMap.get(i));
		}
		for (int i = 8; i < 16; i++) {
			s2.append("\t" + regDataMap.get(i));
		}
		for (int i = 16; i < 24; i++) {
			s3.append("\t" + regDataMap.get(i));
		}
		for (int i = 24; i < 32; i++) {
			s4.append("\t" + regDataMap.get(i));
		}

		StringBuffer str = new StringBuffer("\n" + "Registers" + "\n");
		str.append(s1 + "\n" + s2 + "\n" + s3 + "\n" + s4 + "\n");
		return str.toString();
	}
	/*public RegisterFile clone()
	{
		RegisterFile regFileCopy = new RegisterFile();
		regFileCopy.regDataMap = (TreeMap<Integer, Integer>) this.regDataMap.clone();
		regFileCopy.ready = this.ready.clone();
		regFileCopy.robEntry = this.robEntry.clone();
		return regFileCopy;		
	}*/

}
