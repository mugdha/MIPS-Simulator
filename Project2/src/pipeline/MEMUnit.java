package pipeline;

import java.util.ArrayList;
import java.util.Iterator;

public class MEMUnit {
 
 	
	public static void instrnMemory() {
		InstructionType type;
		String instrn;
		String destreg;
		int destregNum;
		int desrRegValue;
		int calculatedddress;
		
		
		if (!QueueUtil.ispremqEmpty()) {
			
			PreMEMEntry prememtry = QueueUtil.fetchFrompremq();
			type=prememtry.getInstructionType();
			instrn=prememtry.getInstrn();
			if(type==InstructionType.SW)
			{
				calculatedddress=prememtry.calculatedddress;
				destreg=prememtry.getdestreg();				
				desrRegValue=Scheduler.r1.getRegVal(prememtry.getRtNum());				
				Scheduler.d1.memoryDataMap.put(calculatedddress, desrRegValue);
				
			}
			else if(type==InstructionType.LW)
			{
				calculatedddress=prememtry.calculatedddress;
				desrRegValue=Scheduler.d1.memoryDataMap.get(calculatedddress);
				destreg=prememtry.getdestreg();
				PostMEMEntry postmementry=new PostMEMEntry(instrn,destreg,desrRegValue,prememtry.getRs(),prememtry.getRt(),prememtry.getRd(),prememtry.getRsNum(),prememtry.getRtNum(),prememtry.getRdNum());
				QueueUtil.addTopostmq(postmementry);
				
				Scheduler.destRegINPostMEM.add(destreg);
				Scheduler.sourceRegINPostMEM.add(postmementry.getRs());
				
				Scheduler.destRegINPreMEM.remove(postmementry.getRt());
				Scheduler.sourceRegPreMEM.remove(postmementry.getRs());
			}   		
	}

	}

	public static StringBuffer print_postMEM()
	{
		StringBuffer decodedInstrn=new StringBuffer("Post-MEM Queue:");
		ArrayList<String> instrn=QueueUtil.printPostMEM();
		
			for (Iterator<String> iterator = instrn.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();				
				decodedInstrn.append("["+string+"]"+"\n");
			 		
			}
			return decodedInstrn;
		}
}
