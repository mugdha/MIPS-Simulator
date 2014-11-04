package pipeline;

import java.util.ArrayList;
import java.util.Iterator;

public class ALUnit {

	public static void instrnExecute() {

		int operationResult = 0;
		InstructionType type;
		InstructionCategory categ;
		int src1 = 0;
		int src2 = 0;
		String instruction;
		String destReg;
		int offset = 0;
		int baseRegValue = 0;
		int desAddress;
		
		int rdNum = 0;
		int rsNum = 0;
		int rtNum = 0;
		
		String rd;
		String rs;
		String rt;
		
		

		if (!QueueUtil.isprealuqEmpty()) {
			PreALUQEntry prealuentry = QueueUtil.fetchFromprealuq();	 

			if ((prealuentry.getCategory() == InstructionCategory.Load)
					|| (prealuentry.getCategory() == InstructionCategory.Store)) {
				type = prealuentry.getInstrnType();
							
				rdNum=prealuentry.getRdNum(); //offsetvalue
				rsNum=prealuentry.getRsNum();
				rdNum=prealuentry.getRdNum();
				
				rs=prealuentry.getRs();
				rt=prealuentry.getRt();
				rd=prealuentry.getRd();
				categ=prealuentry.getCategory();
				
				baseRegValue = Scheduler.r1.getRegVal(rsNum);
				desAddress = baseRegValue + rdNum;
				destReg = prealuentry.getRt();
				
				instruction = prealuentry.getInstrn();
				PreMEMEntry premementry = new PreMEMEntry(instruction, type,categ,desAddress, destReg, rs,rt,rd,rsNum,rtNum,rdNum);
				QueueUtil.addTopremq(premementry);
				
				Scheduler.destRegINPreMEM.add(premementry.getRt());
				Scheduler.sourceRegPreMEM.add(premementry.getRs());
				
				Scheduler.destRegINPreALUQ.remove(premementry.getRt());
				Scheduler.sourceRegINPreALUQ.remove(premementry.getRs());	 
				

			} else {
				type = prealuentry.getInstrnType();
				categ=prealuentry.getCategory();
				if(categ==InstructionCategory.ArithmeticImm)
				{
					 
					rd=prealuentry.getRd();
					rt=prealuentry.getRt();
					rs=prealuentry.getRs();
					
					rtNum=prealuentry.getRtNum();
					rsNum=prealuentry.getRsNum();
					rdNum=prealuentry.getRdNum();
					
					
					src1 = Scheduler.r1.getRegVal(rsNum);
					
					src2 = rdNum;
					
					instruction = prealuentry.getInstrn();
					destReg = prealuentry.getRt();
					operationResult = SimUtils.getOperationResult(type, src1, src2);
					PostALUEntry postaluentry = new PostALUEntry(instruction,operationResult, destReg,rs,rt, rd, rsNum,  rtNum,rdNum);
					QueueUtil.addTopostaluq(postaluentry);
					
					
					Scheduler.destRegINPostALU.add(postaluentry.getRt());
					Scheduler.sourceRegINPostALU.add(postaluentry.getRs());
					
					Scheduler.destRegINPreALUQ.remove(postaluentry.getRt());
					Scheduler.sourceRegINPreALUQ.remove(postaluentry.getRs());
					
					
					
				}
				else if(categ==InstructionCategory.ArithmeticReg)
				{
					rd=prealuentry.getRd();
					rt=prealuentry.getRt();
					rs=prealuentry.getRs();
					
					rtNum=prealuentry.getRtNum();
					rsNum=prealuentry.getRsNum();
					rdNum=prealuentry.getRdNum();				
					
					src1 = Scheduler.r1.getRegVal(rsNum);
					src2 = Scheduler.r1.getRegVal(rtNum);
					instruction = prealuentry.getInstrn();
					destReg = prealuentry.getRd();
					operationResult = SimUtils.getOperationResult(type, src1, src2);
					PostALUEntry postaluentry = new PostALUEntry(instruction,operationResult, destReg,rs,rt,rd,rsNum,rtNum,rdNum);
					QueueUtil.addTopostaluq(postaluentry);
					
					Scheduler.destRegINPreALUQ.remove(postaluentry.getRd());
					Scheduler.sourceRegINPreALUQ.remove(postaluentry.getRs());
					Scheduler.sourceRegINPreALUQ.remove(postaluentry.getRt());
					
					Scheduler.destRegINPostALU.add(postaluentry.getRd());
					Scheduler.sourceRegINPostALU.add(postaluentry.getRs());
					Scheduler.sourceRegINPostALU.add(postaluentry.getRt());
				}	 

			}

		}

	}

	public static StringBuffer print_premem() {
		StringBuffer decodedInstrn = new StringBuffer("Pre-MEM Queue:");

		ArrayList<String> instrn = QueueUtil.printPreMEM();
		for (Iterator<String> iterator = instrn.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			decodedInstrn.append("[" + string + "]" + "\n");

		}
		return decodedInstrn;
	}

	public static StringBuffer print_postALu() {
		StringBuffer decodedInstrn = new StringBuffer("Post-ALU Queue:");

		ArrayList<String> instrn = QueueUtil.printPostAlu();

		for (Iterator<String> iterator = instrn.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			decodedInstrn.append("[" + string + "]" + "\n");

		}
		return decodedInstrn;
	}

}
