package pipeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class FetchUnit {

  
	public static int nextAddr = 0;
	public static int currentAddr = 0;

	public static void instrnFetch() {
		
		//denoted register number
		int rdNum = 0;
		int rsNum = 0;
		int rtNum = 0;

		String rd;
		String rs;
		String rt;

		int rsRegVal = 0;
		int rtRegVal = 0;
		int rdRegVal = 0;
		String instr = null;
		currentAddr = Scheduler.PC;	 
		
		for (int i = 0; i < 2; i++) {

			InstructionType newInstfetchedType;  
			
			if(QueueUtil.isfetchExecutedQueueFull())
			{
				QueueUtil.fetchExecutedQueue();
			}

			if (QueueUtil.isfetchWaitingQueueFull()) {

				FetchBranchQEntry fetchbranchqe = QueueUtil.fetchFromfetchWaitingQueue();
				
				InstructionType type = fetchbranchqe.getInstrnType();

				if (type.equals("BEQ")) {

					rtNum = fetchbranchqe.getRtNum();
					rsNum = fetchbranchqe.getRsNum();
					rdNum=fetchbranchqe.getRdNum();
					
					rt=fetchbranchqe.getRt();
					rs=fetchbranchqe.getRs();				 
					
					rdRegVal=fetchbranchqe.getRdNum();
					
										 
					instr = fetchbranchqe.getInstrn();
					
					if((!(Scheduler.destinationReg.contains(rt)))&&(!(Scheduler.destinationReg.contains(rs)))){ 
						if((!(Scheduler.destRegINPostALU.contains(rt)))&&(!(Scheduler.destRegINPostMEM.contains(rt))))
						{
							if((!(Scheduler.destRegINPostALU.contains(rs)))&&(!(Scheduler.destRegINPostMEM.contains(rs))))
							{
						rsRegVal = Scheduler.r1.getRegVal(rsNum);
						rtRegVal = Scheduler.r1.getRegVal(rtNum);
						rdRegVal = rdNum;

						if (rsRegVal == rtRegVal) {
							nextAddr = currentAddr + rdRegVal + 4;
						} else {
							nextAddr = currentAddr + 4;
						}				 

							fetchbranchqe.setPC(Scheduler.PC);
							QueueUtil.addTofetchExecutedQueue(fetchbranchqe);
							break;

						} 
					}
					}
						else
						{					 
							QueueUtil.addTofetchWaitingQueue(fetchbranchqe);

						}					 
					 break;
				}
				else if (type.equals("BGTZ")) {

					// rtReg= bgtzHandler.get();
					rsNum = fetchbranchqe.getRsNum();
					rdNum = fetchbranchqe.getRdNum();
					
					rs=fetchbranchqe.getRs();
					 
				 	instr = fetchbranchqe.getInstrn();
				 	
				 	if(!(Scheduler.destinationReg.contains((rs))))
				 	{	
				 		if((!(Scheduler.destinationReg.contains(rs)))&&(!(Scheduler.destinationReg.contains(rs))))
				 		{
				 		rsRegVal = Scheduler.r1.getRegVal(rsNum);
						rdRegVal = rdNum;

						if (rsRegVal > 0) {
							nextAddr = currentAddr + rdRegVal + 4;
						} else {
							nextAddr = currentAddr + 4;
						}
						 
						QueueUtil.addTofetchExecutedQueue(fetchbranchqe);
						

					} 
				 	}	
					else 
					{
						QueueUtil.addTofetchWaitingQueue(fetchbranchqe);
					}
					
					break;// exit loop as only one branch instruction need to be
							// executed
				}
			} 
			else {
				String instruction = Scheduler.addrInstrnMap.get(Scheduler.PC);
				newInstfetchedType = SimUtils.getInstrnType(instruction);
				// will check it its branch instruction or non branch
				// instruction
				if ((newInstfetchedType == InstructionType.BEQ)
						|| (newInstfetchedType == InstructionType.J)
						|| (newInstfetchedType == InstructionType.BGTZ)
						|| (newInstfetchedType == InstructionType.BREAK)) {
					if (newInstfetchedType == InstructionType.BEQ) {
						BEQHandler beqHandler = new BEQHandler(instruction,
								Scheduler.PC);
				 					 
						instr = beqHandler.getInstrn();

						rs = beqHandler.getRs();
						rt = beqHandler.getRt();
						rd = beqHandler.getOffsetStr();
						
						rsNum=beqHandler.getRsNum();
						rtNum=beqHandler.getRtNum();
						rdNum=beqHandler.getOffsetVal();

						FetchBranchQEntry fetchbrnchemtryp = new FetchBranchQEntry(instr,InstructionCategory.Branch, newInstfetchedType, rs, rt, rd,rsNum,rtNum,rdNum);

						//Checking for RAW
						if((!(Scheduler.destinationReg.contains(rt)))&&(!(Scheduler.destinationReg.contains(rs))))
						{ 
							if((!(Scheduler.destRegINPostALU.contains(rt)))&&(!(Scheduler.destRegINPostMEM.contains(rt))))
							{
								if((!(Scheduler.destRegINPostALU.contains(rs)))&&(!(Scheduler.destRegINPostMEM.contains(rs))))
								{
							
							
							rsRegVal = Scheduler.r1.getRegVal(rsNum);
							rtRegVal = Scheduler.r1.getRegVal(rtNum);
							rdRegVal = rdNum;

							if (rsRegVal == rtRegVal) {
								nextAddr = currentAddr + rdRegVal + 4;
								Scheduler.PC=nextAddr;
							} else {
								nextAddr = currentAddr + 4;
								Scheduler.PC=nextAddr;
							}
						 		fetchbrnchemtryp.setPC(Scheduler.PC);
								QueueUtil.addTofetchExecutedQueue(fetchbrnchemtryp);

							} 
							}
						}
							else
							{
								fetchbrnchemtryp.setPC(Scheduler.PC);
								QueueUtil.addTofetchWaitingQueue(fetchbrnchemtryp);

							}
		  
						break;
					}

					if (newInstfetchedType == InstructionType.BGTZ) {

						BGTZHandler bgtzhandler = new BGTZHandler(instruction,
								Scheduler.PC);
						// rtReg= bgtzHandler.get();
						rsNum = bgtzhandler.getRsNum();
						rdNum = bgtzhandler.getOffsetVal();
						
						instr = bgtzhandler.getInstrn();
						rs = bgtzhandler.getRs();
						rd = bgtzhandler.getOffsetStr();
						FetchBranchQEntry fetchbrnchqentry = new FetchBranchQEntry(
								instr, InstructionCategory.Branch,newInstfetchedType, rs, null, rd,rsNum,0,rdNum);

						
						if(!(Scheduler.destinationReg.contains(rs)))
						{
							if((!(Scheduler.destinationReg.contains(rs)))&&(!(Scheduler.destinationReg.contains(rs))))
					 		{
						rsRegVal = Scheduler.r1.getRegVal(rsNum);
						rdRegVal = rdNum;

							if (rsRegVal > 0) {
								nextAddr = currentAddr + rdRegVal + 4;
								Scheduler.PC=nextAddr;
							} else {
								nextAddr = currentAddr + 4;
								Scheduler.PC=nextAddr;
							}					 

							QueueUtil.addTofetchExecutedQueue(fetchbrnchqentry);
							
						} 
						}
						else 
						{
						
						QueueUtil.addTofetchWaitingQueue(fetchbrnchqentry);
						}
					break;	
					}
					if (newInstfetchedType == InstructionType.BREAK) {

						BREAKHandler breakHandler = new BREAKHandler(
								instruction, Scheduler.PC);
						instr = breakHandler.getInstrn();
						FetchBranchQEntry fetchbrnchqentry = new FetchBranchQEntry(
								instr, newInstfetchedType, null, null, null);
						QueueUtil.addTofetchExecutedQueue(fetchbrnchqentry);
						Scheduler.isBreakCommitted=true;
						break;

					}
					if (newInstfetchedType == InstructionType.J) {
						JHandler jhandler = new JHandler(instruction,Scheduler.PC);
						instr=jhandler.getInstrn();
						rs = jhandler.getInstrIndexStr();
						rsNum=jhandler.getNextjumLevel();
						FetchBranchQEntry fetchnewbrnentry = new FetchBranchQEntry(instr,InstructionCategory.Jump, newInstfetchedType, rs, null, null,rsNum,0,0);
						QueueUtil.addTofetchExecutedQueue(fetchnewbrnentry);
						//nextAddr = jhandler.getNextjumLevel();
						Scheduler.PC=jhandler.getNextjumLevel();
						break;

					}
				} 
				else 
				{
					if (!QueueUtil.ispreiqFull()) {
						IQEntry entry = SimUtils.getIQEntry(instruction,
								Scheduler.PC);

						entry.setPC(Scheduler.PC);
						InstructionCategory c1=entry.getCategory();
						
						if(c1==InstructionCategory.ArithmeticImm)
						{
							
							QueueUtil.addTopreiq(entry);
							Scheduler.destRegINPreIssueQ.add(entry.getRt());
							Scheduler.sourceRegINPreIsseQ.add(entry.getRs());
							
						}
						if(c1==InstructionCategory.ArithmeticReg)
						{
							QueueUtil.addTopreiq(entry);
							Scheduler.destRegINPreIssueQ.add(entry.getRd());
							Scheduler.sourceRegINPreIsseQ.add(entry.getRs());
							Scheduler.sourceRegINPreIsseQ.add(entry.getRt());
							
						}
						if(c1==InstructionCategory.Load)
						{
							QueueUtil.addTopreiq(entry);
							Scheduler.destRegINPreIssueQ.add(entry.getRt());
							Scheduler.sourceRegINPreIsseQ.add(entry.getRs());
							//Scheduler.sourceRegINPreIsseQ.add(entry.getRt());
							
						}
						if(c1==InstructionCategory.Store)
						{
							QueueUtil.addTopreiq(entry);
						}
						
					 					
						//nextAddr = currentAddr + 4;
						Scheduler.PC+=4;
					}
				}
				

			}
			//Scheduler.PC = nextAddr;
		}
	}

	public static StringBuffer print() {
		
		// print the content of fetchWaitingQueue
		StringBuffer decodedInstrn = new StringBuffer("IF Unit:" + "\n");
		
		if(QueueUtil.isfetchWaitingQueueEmpty())
		{
			decodedInstrn.append("\t" + "Waiting Instruction:"+ "\n");
		}
		else{		
		decodedInstrn.append("\t" + "Waiting Instruction:["
				+ QueueUtil.printfetchWaitingQueue() + "]" + "\n");}
		
		
		if(QueueUtil.isfetchExecutedQueueEmpty())
		{
			decodedInstrn.append("\t" + "Executed Instruction:"+ "\n");
		}
		else{		
		decodedInstrn.append("\t" + "Executed Instruction:["
				+ QueueUtil.printfetchExecutedQueue() + "]" + "\n");
		}
 		// print the content of Pre-Issue Queue:

		decodedInstrn.append("Pre-Issue Queue:" + "\n");

		// String[] instrn = new String[4];
		ArrayList<String> instrn = QueueUtil.printPreiq();
		int size = instrn.size();

		if (size == 4) {
			int counter = 0;
			for (Iterator<String> iterator = instrn.iterator(); iterator
					.hasNext();) {
				String string = (String) iterator.next();
				decodedInstrn.append("\t" + "Entry " + counter + ":[" + string
						+ "]" + "\n");
				counter++;
			}
		} else if (size == 3) {
			int counter = 0;
			for (Iterator<String> iterator = instrn.iterator(); iterator
					.hasNext();) {
				String string = (String) iterator.next();
				decodedInstrn.append("\t" + "Entry " + counter + ":[" + string
						+ "]" + "\n");
				counter++;
			}
			decodedInstrn.append("\t" + "Entry 3:" + "\n");
		} else if (size == 2) {
			int counter = 0;
			for (Iterator<String> iterator = instrn.iterator(); iterator
					.hasNext();) {
				String string = (String) iterator.next();
				decodedInstrn.append("\t" + "Entry " + counter + ":[" + string
						+ "]" + "\n");
				counter++;
			}
			decodedInstrn.append("\t" + "Entry 2:" + "\n");
			decodedInstrn.append("\t" + "Entry 3:" + "\n");
		} else if (size == 1) {
			int counter = 0;
			for (Iterator<String> iterator = instrn.iterator(); iterator
					.hasNext();) {
				String string = (String) iterator.next();
				decodedInstrn.append("\t" + "Entry " + counter + ":[" + string
						+ "]" + "\n");

			}
			decodedInstrn.append("\t" + "Entry 1:" + "\n");
			decodedInstrn.append("\t" + "Entry 2:" + "\n");
			decodedInstrn.append("\t" + "Entry 3:" + "\n");
		} else {
			decodedInstrn.append("\t" + "Entry 0:" + "\n");
			decodedInstrn.append("\t" + "Entry 1:" + "\n");
			decodedInstrn.append("\t" + "Entry 2:" + "\n");
			decodedInstrn.append("\t" + "Entry 3:" + "\n");
		}
		return decodedInstrn;

	}
}
