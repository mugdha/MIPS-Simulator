package pipeline;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;


public class Scheduler implements Serializable{	
	
		public static int PC = 128;
		public static int initAddress = 128;
	    private static String inputFileName;
	    private static String outputFileName;	    
	    private static File outputFile;
	   // private static BufferedWriter  outputWriter;
	    private static PrintWriter outputWriter;
	    private static int codeSize;
	    private static int instrnCount;
	    private static int instrnLen = 32;  
	    public static String[] binaryInstrn;
	    static boolean isBreakReached = false;
		static boolean isBreakCommitted = false;
	    private static int cycle;	
	    public static TreeMap<Integer, String> addrInstrnMap;
		public static TreeMap<Integer, String> addrDataMap;
		
		public static int startingDataAddress; 
		
		public static RegisterFile r1;
		public static DataFile d1;
		
		public static RegisterFile newr1;
		public static DataFile newd1;
		
		//store 1 in integer field to indicate that it is present in source and destination
		
		public static ArrayList<String> destinationReg;
		public static ArrayList<String> sourceReg; 
		
		public static ArrayList<String> destRegINPreIssueQ;
		public static ArrayList<String> sourceRegINPreIsseQ; 
			
		public static ArrayList<String> destRegINPreALUQ;
		public static ArrayList<String> sourceRegINPreALUQ; 
		
		public static ArrayList<String> destRegINPostALU;
		public static ArrayList<String> sourceRegINPostALU; 
			
		public static ArrayList<String> destRegINPreMEM;
		public static ArrayList<String> sourceRegPreMEM;
		
		public static ArrayList<String> destRegINPostMEM;
		public static ArrayList<String> sourceRegINPostMEM;
		
			
	    public static void initSimTrial(String input) throws IOException
	    {
	    	    String output="simulation.txt";
	            inputFileName = input;
	            outputFileName = output;	         
	            File inputFile = new File("."+File.separator+inputFileName);
	            BufferedReader inputStream = new BufferedReader(new FileReader(inputFile)); 
	            codeSize = (int) inputFile.length(); 
 
	            outputFile = new File(outputFileName);
	            
	            
	            cycle = 1;
	            
	            loadInstrnDataMaps();
	            
	            while (true) {
	                    if (outputFile.createNewFile() != true) {
	                            outputFile.delete();
	                            continue;
	                    } else {
	                            break;
	                    }
	                    } 
	            outputWriter = new PrintWriter(new FileWriter(outputFile));

	            // Start scheduling
	    	 
	    		init();
	            start();
	    		    		
	    		outputWriter.close();
	    		inputStream.close();
	            
	    }
	    
	    

		private static void start() {
			
			startingDataAddress=d1.memoryDataMap.firstKey();
			 
			while (!isBreakCommitted) 
			{
				System.out.println("Val of PC : "+PC);
				
				if((PC < startingDataAddress)&&(cycle<42))				 
				{			 
						WBUnit.executeCommit();
						MEMUnit.instrnMemory();
						ALUnit.instrnExecute();						
						IssueUnit.instrnIssue();	
						FetchUnit.instrnFetch();	
						update();
						display();
						cycle++;					
					
				}
				else {
					break;
				}
					
			}
			
		}

		private static void update() {

			r1 = newr1.clone();
			d1 = newd1.clone();
			
		}



		public static void display() {
			
			StringBuffer initial=new StringBuffer("--------------------");
			
			System.out.println(initial);
			outputWriter.println(initial);
			System.out.println("Cycle "+cycle+ ":"+"\n");
			outputWriter.println("Cycle "+cycle+":"+"\n");		

			StringBuffer fetchinstruction= FetchUnit.print();
			System.out.print(fetchinstruction);
			outputWriter.print(fetchinstruction);
			
			StringBuffer issueInstruction= IssueUnit.print();
			System.out.print(issueInstruction);
			outputWriter.print(issueInstruction);
			
			StringBuffer aluInstruction= ALUnit.print_premem();
			System.out.println(aluInstruction);
			outputWriter.println(aluInstruction);
			
			StringBuffer memInstruction= MEMUnit.print_postMEM();
			System.out.println(memInstruction);
			outputWriter.println(memInstruction);
			
			StringBuffer postALUInstruction= ALUnit.print_postALu();
			System.out.println(postALUInstruction);
			outputWriter.println(postALUInstruction);	 
			 
			String regStr = r1.print();	
			System.out.print(regStr);
			outputWriter.println(regStr);	 
			 
			String dataStr = d1.print();
			System.out.println(dataStr);
			outputWriter.println(dataStr);
	 
		}



		/**
		 * initialize the data structures and misc stuff
		 */
		private static void init() {
			 
			r1=new RegisterFile();
			d1=new DataFile(addrDataMap);
			
			newr1=new RegisterFile();
			newd1=new DataFile(addrDataMap);
			
			destinationReg = new ArrayList<String>();
			sourceReg = new ArrayList<String>(); 
			
			destRegINPreALUQ = new ArrayList<String>();
			sourceRegINPreALUQ = new ArrayList<String>(); 
			
			destRegINPreIssueQ = new ArrayList<String>();
			sourceRegINPreIsseQ = new ArrayList<String>(); 			
			
			destRegINPostALU= new ArrayList<String>(); 
			sourceRegINPostALU= new ArrayList<String>(); 
				
			destRegINPreMEM= new ArrayList<String>(); 
			sourceRegPreMEM= new ArrayList<String>(); 
			
			destRegINPostMEM= new ArrayList<String>(); 
			sourceRegINPostMEM= new ArrayList<String>(); 
				
			
			
			
			 
		}
	    
		 public static String addLeadingZeros(long s) {
		        String str=Long.toBinaryString((long)s);
		    	String temp;
		    	temp=str;
		    	while(temp.length()<32)
		    	{
		    		temp="0"+temp;
		    	}
		    	return temp;
		    }
		 
		 public static int getDataFromBinary(String bin) {
				int num = -1;
				if (bin.substring(0, 1).equals("0")) {
					num = Integer.parseInt(bin, 2);
				}
				// Handling negative numbers
				if (bin.substring(0, 1).equals("1")) {
					try {
						// Apply 2's complement
						bin = bin.replace('1', 'x');
						bin = bin.replace('0', '1');
						bin = bin.replace('x', '0');
						num = (Integer.parseInt(bin, 2) + 1) * -1;
						// num = (65536 - num) * (-1);
					} catch (NumberFormatException ex) {
						System.out.println("Error : " + ex.getMessage());
					}
				}
				return num;
			}
		 
	private static void loadInstrnDataMaps() {
		try {
			File inputFile = new File(inputFileName);
			DataInputStream inputStream = new DataInputStream(
					new BufferedInputStream(new FileInputStream(inputFile)));

			// Size of binary code, is the length of the binary file
			int codeSize = (int) inputFile.length();
			// initialize byte array to hold the binary instructions
			int instrnCount = codeSize / instrnLen;
			String[] binaryInstrn = new String[instrnCount];

			// initialize address-instruction handler map
			addrInstrnMap = new TreeMap<Integer, String>();
			addrDataMap = new TreeMap<Integer, String>();

			// initialize opcodes map
			new Opcodes();

			// boolean isBreakEncountered = false;
			boolean isDataBegin = false;
			int address = initAddress;

			for (int i = 0; i < instrnCount; i++) {
				
				String data = inputStream.readLine(); 
				//int data = inputStream.readInt();
				
				if(data==null)
                	break;
                long xbin = Long.parseLong(data, 2);                
                binaryInstrn[i] = addLeadingZeros(xbin);  
				
			/*	String xbin = Integer.toBinaryString(data);
				binaryInstrn[i] = Disassembler.addLeadingZeros(xbin);
				*/
				// If break instruction is not yet reached, keep decoding the
				// instructions
				if (!isDataBegin) {
					InstructionType type = SimUtils.getInstrnType(binaryInstrn[i]);
					addrInstrnMap.put(address, binaryInstrn[i]);
					
					if(type==InstructionType.BREAK)
					{
						isDataBegin=true;
					}
				}
				// If address , put remaining in data map
				else {
					addrDataMap.put(address, binaryInstrn[i]);
				}
				address += 4; 
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
