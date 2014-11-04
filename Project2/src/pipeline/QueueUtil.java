package pipeline;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class QueueUtil {

	private static  Queue<IQEntry> preiq = new ArrayBlockingQueue<>(Constants.PRE_ISSUE_SIZE);
	private static  Queue<PreALUQEntry> prealuq = new ArrayBlockingQueue<>(Constants.PRE_ALU_SIZE);
	private static  Queue<PostALUEntry> postaluq = new ArrayBlockingQueue<>(Constants.POST_ALU_SIZE);
	private static  Queue<PreMEMEntry> premq = new ArrayBlockingQueue<>(Constants.PRE_MEM_SIZE);
	private static  Queue<PostMEMEntry> postmq= new ArrayBlockingQueue<>(Constants.POST_MEM_SIZE);
	
	private static  Queue<FetchBranchQEntry> fetchWaitingQueue= new ArrayBlockingQueue<>(Constants.FETCH_WAITING_INST);
	private static  Queue<FetchBranchQEntry> fetchExecutedQueue= new ArrayBlockingQueue<>(Constants.FETCH_EXECUTED_INST);
	

	
	public static boolean isfetchExecutedQueueEmpty() {
		preiq.peek();
		return fetchExecutedQueue.isEmpty();
	}

	public static void addTofetchExecutedQueue(FetchBranchQEntry strValue) {
		fetchExecutedQueue.add(strValue);
	}

	public static boolean isfetchExecutedQueueFull() {
		return (fetchExecutedQueue.size() == Constants.FETCH_EXECUTED_INST);
	}

	public static FetchBranchQEntry fetchExecutedQueue() {
		return fetchExecutedQueue.poll();
	}	
	
	public static String printfetchExecutedQueue() {
		
		String instruction=null;
		for (FetchBranchQEntry fbq : fetchExecutedQueue) {
			//System.out.println(fbq.getInstrn());
			instruction=fbq.getInstrn();
			}
		return instruction;	
		
	}
	
	
	
	public static boolean isfetchWaitingQueueEmpty() {
		return fetchWaitingQueue.isEmpty();
	}

	public static void addTofetchWaitingQueue(FetchBranchQEntry strValue) {
		fetchWaitingQueue.add(strValue);
	}

	public static boolean isfetchWaitingQueueFull() {
		return (fetchWaitingQueue.size() == Constants.FETCH_WAITING_INST);
	}

	public static FetchBranchQEntry fetchFromfetchWaitingQueue() {
		return fetchWaitingQueue.poll();
	}
	
	public static String printfetchWaitingQueue() {
		
		String instruction=null;
		for (FetchBranchQEntry fbq : fetchWaitingQueue) {
			
			//System.out.println(fbq.getInstrn());
			instruction=fbq.getInstrn();
			}
		return instruction;
	}
	
	public static boolean ispreiqEmpty() {
		return preiq.isEmpty();
	}

	public static void addTopreiq(IQEntry strValue) {
		preiq.add(strValue);
	}

	public static boolean ispreiqFull() {
		return (preiq.size() == Constants.PRE_ISSUE_SIZE);
	}

	public static IQEntry fetchFrompreiq() {
		return preiq.poll();
	}

	public static Queue<IQEntry> readOnlyFrompreissue() {
		return preiq;
	}
	public static Queue<PreALUQEntry> readFromprealu() {
		return prealuq;
	}
	public static Queue<PreMEMEntry> readFrompremem() {
		return premq;
	}
	public static Queue<PostALUEntry> readFrompostalu() {
		return postaluq;
	}
	public static Queue<PostMEMEntry> readFrompostmem() {
		return postmq;
	}
	
	
	
	public static Queue<IQEntry> getPreiq()
	{		
		return preiq;
	}
	
	public static ArrayList<String> printPreiq() {			
		 
		ArrayList<String> instrn = new ArrayList<>();
	 
		for (IQEntry iqe : preiq) {
			instrn.add(iqe.getInstrn());			 
			//System.out.println(iqe.getInstrn());
			}
		return instrn;
		
	}
	
	public static boolean ispostmqEmpty() {
		return postmq.isEmpty();
	}

	public static void addTopostmq(PostMEMEntry strValue) {
		postmq.add(strValue);
	}

	public static boolean ispostmqFull() {
		return (postmq.size() == Constants.POST_MEM_SIZE);
	}

	public static PostMEMEntry fetchFrompostmq() {
		return postmq.poll();
	}
	
	
	public static boolean ispremqEmpty() {
		return premq.isEmpty();
	}

	public static void addTopremq(PreMEMEntry premementry) {
		premq.add(premementry);
	}

	public static boolean ispremqFull() {
		return (premq.size() == Constants.PRE_MEM_SIZE);
	}

	public static PreMEMEntry fetchFrompremq() {
		return premq.poll();
	}
	
	
	
	public static boolean ispostaluqEmpty() {
		return postaluq.isEmpty();
	}

	public static void addTopostaluq(PostALUEntry postaluentry) {
		postaluq.add(postaluentry);
	}

	public static boolean ispostaluqFull() {
		return (postaluq.size() == Constants.POST_ALU_SIZE);
	}

	public static PostALUEntry fetchFrompostaluq() {
		return postaluq.poll();
	}
	
	public static ArrayList<String> printPostAlu() {			

		ArrayList<String> instrn = new ArrayList<>();
		 
		for (PostALUEntry palue : postaluq) 
		{		
			instrn.add(palue.getInstrn());			 
			//System.out.println(palue.getInstrn());
			}
		return instrn;
		
	}
	
	
	/*public static String fetchFrompreiqByIndex(int index) {
		Queue<String> newPreiq = new ArrayBlockingQueue<>(
				Constants.PRE_ISSUE_SIZE);
		IQEntry retval = null;
		for (int count = 0; !preiq.isEmpty(); count++) {
			if (count == index) {
				retval = preiq.poll();
			} else {
				newPreiq.offer(preiq.poll());
			}
		}
		preiq = newPreiq;
		return retval;
	}*/
	
	
	public static boolean isprealuqEmpty() {
		return prealuq.isEmpty();
	}

	public static void addToprealuq(PreALUQEntry instr) {
		prealuq.add(instr);
	}

	public static boolean isprealuqFull() {
		return (prealuq.size() == Constants.PRE_ALU_SIZE);
	}

	public static PreALUQEntry fetchFromprealuq() {
		return prealuq.poll();
	}	
	
	public static ArrayList<String> printPreAlu() {
		
		ArrayList<String> instrn = new ArrayList<>();
		for (PreALUQEntry paluqe : prealuq) {
			instrn.add(paluqe.getInstrn());			 
			//System.out.println(paluqe.getInstrn());
			}
		return instrn;
	
		
	}
		
	
	public static ArrayList<String> printPreMEM() {
		
		ArrayList<String> instrn = new ArrayList<>();
		for (PreMEMEntry preme : premq) {
			instrn.add(preme.getInstrn());			 
			//System.out.println(preme.getInstrn());
			}
		return instrn;
		
	}
	public static ArrayList<String> printPostMEM() {
		
		ArrayList<String> instrn = new ArrayList<>();
		for (PostMEMEntry postme : postmq) {
			instrn.add(postme.getInstrn());			 
			//System.out.println(postme.getInstrn());
			}
		return instrn;
	}
	
	
	public static IQEntry fetchFrompreiqByIndex(int index) {
		Queue<IQEntry> newPreiq = new ArrayBlockingQueue<>(Constants.PRE_ISSUE_SIZE);
		IQEntry retval = null;
		for (int count = 0; !preiq.isEmpty(); count++) {
		if (count == index) {
		retval = preiq.poll();
		} else {
		newPreiq.offer(preiq.poll());
		}
		}
		preiq = newPreiq;
		return retval;
		}




}
