package pipeline;

import java.io.Serializable;

public class WBUnit implements Serializable{
 
	static String destinationReg;
	
	public static void executeCommit()
	{
		
		if(!QueueUtil.ispostaluqEmpty())
		{
			PostALUEntry postaluentry=QueueUtil.fetchFrompostaluq();
			int regNo=postaluentry.getRdNum();
			destinationReg=postaluentry.getdestReg();
			
			Scheduler.r1.updateReg(regNo, postaluentry.getCalculatedValue());
			Scheduler.r1.setReady(regNo);	
			
			Scheduler.destinationReg.remove(destinationReg);
			Scheduler.sourceReg.remove(postaluentry.getRs());
			Scheduler.sourceReg.remove(postaluentry.getRt());
			
			Scheduler.destRegINPostALU.remove(destinationReg);
			Scheduler.sourceRegINPostALU.remove(postaluentry.getRs());
			Scheduler.sourceRegINPostALU.remove(postaluentry.getRt());
			
			
			
						
		}
		if(!QueueUtil.ispostmqEmpty())
		{
			PostMEMEntry postmementry=QueueUtil.fetchFrompostmq();
			int regNo=postmementry.getRtNum();
			Scheduler.r1.updateReg(regNo,postmementry.getRegtobeUpdatedVal());
			Scheduler.r1.setReady(regNo); 
			Scheduler.destinationReg.remove(postmementry.getReg());
			Scheduler.sourceReg.remove(postmementry.getRs());
			
			Scheduler.destRegINPostMEM.remove(postmementry.getReg());
			Scheduler.sourceRegINPostMEM.add(postmementry.getRs());
			
		}
		
	}

	 
}
