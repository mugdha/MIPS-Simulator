package pipeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class IssueUnit {
 
	static String rd;
	static String rt;
	static String rs;
	static String instruction;
	static InstructionCategory categ;
	static InstructionType type;
	
	public static void instrnIssue() {
		
		int rdNum = 0;
		int rsNum = 0;
		int rtNum = 0;
		
		int rsRegVal = 0;
		int rtRegVal = 0;
		int rdRegVal = 0;
		
		PreALUQEntry prealuentry;
	
		ArrayList<String> tempdestRegINPreIssueQTobeRemoved=new ArrayList<>();
		ArrayList<String> tempsourceRegINPreIsseQTobeRemoved=new ArrayList<>();
		
		ArrayList<String> tempdestRegINPreALUQTobeAdded=new ArrayList<>();
		ArrayList<String> tempsourceRegINPreALUQTobeAdded=new ArrayList<>();
		

		for (int i = 0; i < 2; i++) {

			if (!QueueUtil.isprealuqFull()) {

				if (!QueueUtil.ispreiqEmpty()) {

					int index = fetchElligibleInstruction();
					if (index == -1) {
						break;
					}
					IQEntry instrn1 = QueueUtil.fetchFrompreiqByIndex(index);

					if (instrn1.category == InstructionCategory.Store) {

						rd = instrn1.getRd(); // contains the offset value
						rs = instrn1.getRs(); // base register
						rt=	instrn1.getRt();
						
						rdNum=instrn1.getRdNum();
						rtNum=instrn1.getRtNum();
						rdNum=instrn1.getRdNum();
						
						instruction = instrn1.getInstrn();
						categ = instrn1.getCategory();
						type = instrn1.getInstrnType();
						 prealuentry = new PreALUQEntry(instruction, categ, type, rs, rt, rd,rsNum,rtNum,rdNum);
						prealuentry.setPC(instrn1.getPC());
						QueueUtil.addToprealuq(prealuentry);
						 
						Scheduler.sourceReg.add(rs);
						//Scheduler.sourceReg.put(key, value)
						
						break;
					}
					if (instrn1.category == InstructionCategory.Load) {

						rd = instrn1.getRd(); // contains the offset value
						rs = instrn1.getRs(); // base register
						rt = instrn1.getRt();
						rdNum=instrn1.getRdNum();
						rtNum=instrn1.getRtNum();
						rdNum=instrn1.getRdNum();
						instruction = instrn1.getInstrn();
						categ = instrn1.getCategory();
						type = instrn1.getInstrnType();
						
						//r3.setBusy(rt);
						Scheduler.newr1.setBusy(rt);
						
						prealuentry = new PreALUQEntry(
								instruction, categ, type, rs, rt, rd,rsNum,rtNum,rdNum);
						prealuentry.setPC(instrn1.getPC());
						QueueUtil.addToprealuq(prealuentry);
						
						//remove from pre issue and keep in pre alu srrc and destination
						
						tempdestRegINPreALUQTobeAdded.add((prealuentry.getRt()));
						tempsourceRegINPreALUQTobeAdded.add(prealuentry.getRs());
						
						tempdestRegINPreIssueQTobeRemoved.add(prealuentry.getRt());
						tempsourceRegINPreIsseQTobeRemoved.add(prealuentry.getRs());
					/*	
						Scheduler.destRegINPreALUQ.add(prealuentry.getRt());
						Scheduler.sourceRegINPreALUQ.add(prealuentry.getRs());
						
						Scheduler.destRegINPreIssueQ.remove(prealuentry.getRt());
						Scheduler.sourceRegINPreIsseQ.remove(prealuentry.getRs());*/
						
						
						Scheduler.destinationReg.add(rt);
						Scheduler.sourceReg.add(rs);
						break;
					}
					switch (instrn1.category) {

					case ArithmeticImm:
						rt = instrn1.getRt();// destination reg
						rs = instrn1.getRs(); // one of the source reg
						
						rd=instrn1.getRd();
						rdNum=instrn1.getRdNum();
						rtNum=instrn1.getRtNum();
						rdNum=instrn1.getRdNum();
												
						instruction = instrn1.getInstrn();
						categ = instrn1.getCategory();
						type = instrn1.getInstrnType();
						/*r3.setBusy(rt); // for WAW
						r3.setBusy(rs);
					 
						newr3.setBusy(rs);*/
						Scheduler.newr1.setBusy(rt);
						prealuentry = new PreALUQEntry(
								instruction, categ, type, rs, rt, rd,rsNum,rtNum,rdNum);
						prealuentry.setPC(instrn1.getPC());
						QueueUtil.addToprealuq(prealuentry);
						
						
						tempdestRegINPreALUQTobeAdded.add((prealuentry.getRt()));
						tempsourceRegINPreALUQTobeAdded.add(prealuentry.getRs());
						
						tempdestRegINPreIssueQTobeRemoved.add(prealuentry.getRt());
						tempsourceRegINPreIsseQTobeRemoved.add(prealuentry.getRs());
						
						
						/*Scheduler.destRegINPreIssueQ.remove(prealuentry.getRt());
						Scheduler.sourceRegINPreIsseQ.remove(prealuentry.getRs());
						Scheduler.destRegINPreALUQ.add(prealuentry.getRt());
						Scheduler.sourceRegINPreALUQ.add(prealuentry.getRs());*/
												
						Scheduler.destinationReg.add(rt);
						Scheduler.sourceReg.add(rs);
						
						break;

					case ArithmeticReg:
						rd = instrn1.getRd(); // dest reg
						rs = instrn1.getRs(); // src1
						rt = instrn1.getRt(); // src2
						rdNum=instrn1.getRdNum();
						rtNum=instrn1.getRtNum();
						rdNum=instrn1.getRdNum();
						
						instruction = instrn1.getInstrn();
						categ = instrn1.getCategory();
						type = instrn1.getInstrnType();

						/*newr3.setBusy(rs);
						newr3.setBusy(rt);*/
						Scheduler.newr1.setBusy(rd);
							 

						prealuentry = new PreALUQEntry(
								instruction, categ, type, rs, rt, rd,rsNum,rtNum,rdNum);
						prealuentry.setPC(instrn1.getPC());
						QueueUtil.addToprealuq(prealuentry);
						
						
						tempdestRegINPreALUQTobeAdded.add((prealuentry.getRd()));
						tempsourceRegINPreALUQTobeAdded.add(prealuentry.getRs());
						tempsourceRegINPreALUQTobeAdded.add(prealuentry.getRt());
						
						tempdestRegINPreIssueQTobeRemoved.add(prealuentry.getRd());
						tempsourceRegINPreIsseQTobeRemoved.add(prealuentry.getRs());
						tempsourceRegINPreIsseQTobeRemoved.add(prealuentry.getRt());
						
						/*
						Scheduler.destRegINPreIssueQ.remove(prealuentry1.getRd());
						Scheduler.sourceRegINPreIsseQ.remove(prealuentry1.getRs());
						Scheduler.sourceRegINPreIsseQ.remove(prealuentry1.getRt());
						
						Scheduler.destRegINPreALUQ.add(prealuentry1.getRd());
						Scheduler.sourceRegINPreALUQ.add(prealuentry1.getRs());
						Scheduler.sourceRegINPreALUQ.add(prealuentry1.getRt());*/
						
						Scheduler.destinationReg.add(rd);
						Scheduler.sourceReg.add(rs);
						Scheduler.sourceReg.add(rt);
						break;
					default:
						break;

					}

				}
			}
		}
		
		Scheduler.destRegINPreALUQ.addAll(tempdestRegINPreALUQTobeAdded);
		Scheduler.sourceRegINPreALUQ.addAll(tempsourceRegINPreALUQTobeAdded);
		
		Scheduler.destRegINPreIssueQ.removeAll(tempdestRegINPreIssueQTobeRemoved);
		Scheduler.sourceRegINPreIsseQ.removeAll(tempsourceRegINPreIsseQTobeRemoved);

	}

	public static StringBuffer print() {
		StringBuffer decodedInstrn = new StringBuffer("Pre-ALU Queue:" + "\n");
		ArrayList<String> instrn = QueueUtil.printPreAlu();
		int size = instrn.size();
		if (size == 2) {
			int counter = 0;
			for (Iterator<String> iterator = instrn.iterator(); iterator
					.hasNext();) {
				String string = (String) iterator.next();
				decodedInstrn.append("\t" + "Entry " + counter + ":[" + string
						+ "]" + "\n");
				counter++;
			}
		} else if (size == 1) {
			for (Iterator<String> iterator = instrn.iterator(); iterator
					.hasNext();) {
				String string = (String) iterator.next();
				decodedInstrn.append("\t" + "Entry 0:" + "[" + string + "]"
						+ "\n");
			}
			decodedInstrn.append("\t" + "Entry 1::"+"\n");
			
			
		} else {
			decodedInstrn.append("\t" + "Entry 0 :" + "\n");
			decodedInstrn.append("\t" + "Entry 1 :" + "\n");
		}
		return decodedInstrn;

	}

	public boolean checkfor(IQEntry instr) {
		return false;

	}

	private static int fetchElligibleInstruction() {
		
		String src1;
		String src2;
		String des;

		Queue<IQEntry> queue = QueueUtil.getPreiq();

		int elligibleIndex = -1;
		int counter = 0;
		for (IQEntry iqEntry : queue) {

			if (iqEntry.category == InstructionCategory.Store) {
				elligibleIndex = counter;
				break;
			}
			if (iqEntry.category == InstructionCategory.Load) {
				
				des=iqEntry.getRt();
				src1=iqEntry.getRs();
				
				InstructionCategory cat1=null;
				InstructionCategory cat2=null;
				
				Queue<PreALUQEntry> q1= QueueUtil.readFromprealu();
				
				for (PreALUQEntry prealuEntry : q1) {
							  
					cat1=prealuEntry.getCategory();
				}
				 
				Queue<PreMEMEntry> q2=QueueUtil.readFrompremem();
				for (PreMEMEntry prememEntry : q2) {
					  
					cat2=prememEntry.getCategory();
				}
				
				if (!(cat1== InstructionCategory.Store)
						&& (!(cat2== InstructionCategory.Store))) {
					if(!(Scheduler.sourceRegINPreALUQ.contains(src1))){			
					
						if(!(Scheduler.destinationReg.contains(des))&&(!(Scheduler.sourceReg.contains(des)))) ///check for WAW and WAR
						{ 
							elligibleIndex = counter;
							break;
						}
					}

				}
			}
			switch (iqEntry.category) {
			
			case ArithmeticImm:
				
				des=iqEntry.getRt();
				src1=iqEntry.getRs();
				
				if(!(Scheduler.sourceRegINPreALUQ.contains(src1))){
				
				if(!(Scheduler.destinationReg.contains(des))&&(!(Scheduler.sourceReg.contains(des)))) ///check for WAW and WAR
				{ 
					elligibleIndex = counter;
					break;
				}
				}
				
			
			case ArithmeticReg:
				des=iqEntry.getRd();
				src1=iqEntry.getRs();
				src2=iqEntry.getRt();
				
				if(!(Scheduler.sourceRegINPreALUQ.contains(src1))&&(!(Scheduler.sourceRegINPreALUQ.contains(src2)))){
				
				if(!(Scheduler.destinationReg.contains(des))&&(!(Scheduler.sourceReg.contains(des)))) ///check for WAW and WAR
				{ 
					elligibleIndex = counter;
					break;
				}
				}
				
			default:
				break;

			}
		}
		counter++;
		return elligibleIndex;

	}
}
