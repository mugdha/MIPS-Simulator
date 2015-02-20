/* On my honor, I have neither given nor received unauthorised aid on this assignment */

 	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.OutputStreamWriter;
	import java.util.HashMap;
	import java.util.Iterator;
	import java.util.LinkedList;
	import java.util.Queue;
	import java.util.Set;


	public class MIPSsim {
		
		public static Queue<String> INM = new LinkedList<String>();
		public static Queue<String> INB = new LinkedList<String>();
		public static Queue<String> AIB = new LinkedList<String>();
		public static Queue<String> LIB = new LinkedList<String>();
		public static Queue<String> ADB = new LinkedList<String>();
		public static Queue<String> REB = new LinkedList<String>();
		public static int stepCount=0;
		public static BufferedWriter  outputWriter ;
		
		
		
		public static void main(String args[]) throws IOException
		{
		// loaded the file value in the register Hashmap 
			File inputFile = new File("."+File.separator+"registers.txt");
			FileInputStream fis = new FileInputStream(inputFile);		 
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		 
			String line = null;
			while ((line = br.readLine()) != null) {
				// initialize th register value
				String parts[]=line.split(",");
				int regNo=Integer.parseInt(parts[0].substring(2));
				int regVal=Integer.parseInt(parts[1].substring(0, parts[1].length()-1));
				RegisterFile.updateReg(regNo, regVal);		
				
			}
		 
			br.close();
			
		// load data memory in the hashmap
			
			File f2 = new File("."+File.separator+"datamemory.txt");
			FileInputStream fis2 = new FileInputStream(f2);		 
			BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2));
		 
			 line = null;
			while ((line = br2.readLine()) != null) {
				String parts[]=line.split(",");
				int address=Integer.parseInt(parts[0].substring(1));
				int val=Integer.parseInt(parts[1].substring(0,parts[1].length()-1));
				DataMemory.updateDataMem(address, val);		
				
			}	 
			br2.close();
			
			// create a file for writing content in it
			 File output = new File("."+File.separator+"simulation.txt");
		        
		        while (true) {
		            if (output.createNewFile() != true) {
		            	output.delete();
		                    continue;
		            } else {
		                    break;
		            }
		            } 
		        
		         outputWriter = new BufferedWriter(new FileWriter("simulation.txt")); 
			
			
			// now we will read intruction one by one and produce the output
			simutor();	
			outputWriter.close();
			
			 
			
		}

		private static void simutor() throws IOException {

			File f2 = new File("."+File.separator+"instructions.txt");
			FileInputStream fis2 = new FileInputStream(f2);		 
			BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2));	 
			String line = null;		 
			while ((line = br2.readLine()) != null) {
				INM.add(line);			
			}	 
			br2.close();	
			
			printvalue();// print value for othe index
			
			do{
				write();
				load();
				addressCalculation();
				Add_SubtractUnit();
				Issue();
				readAndDecode();
				stepCount++;	
				printvalue();
				if(INM.isEmpty() && REB.isEmpty())
					break;
			
			}while(true);
			
			
			
			
		}
		private static void printvalue() throws IOException {
			 String outputVal="";
			 outputVal="STEP"+" "+stepCount+":"+"\n";
			 
			 outputVal=outputVal+"INM"+":";
			 for(Object object : INM) {
				    String element = (String) object;
				    outputVal=outputVal+element+","; 			    
				}
			 if(!INM.isEmpty())
			 {
			 outputVal=outputVal.substring(0, outputVal.length()-1);
			 }
			 outputVal=outputVal+"\n";
			 
			 outputVal=outputVal+"INB"+":";
			 for(Object object : INB) {
				    String element = (String) object;
				    outputVal=outputVal+element+","; 			    
				}
			 if(!INB.isEmpty())
			 {
			 outputVal=outputVal.substring(0, outputVal.length()-1);
			 }
			 outputVal=outputVal+"\n";;
			 
			 outputVal=outputVal+"AIB"+":";
			 for(Object object : AIB) {
				    String element = (String) object;
				    outputVal=outputVal+element+","; 			    
				}
			 if(!AIB.isEmpty())
			 outputVal=outputVal.substring(0, outputVal.length()-1);
			 outputVal=outputVal+"\n";
			 
			 outputVal=outputVal+"LIB:";
			 for(Object object : LIB) {
				    String element = (String) object;
				    outputVal=outputVal+element+","; 			    
				}
			 if(!LIB.isEmpty())
			 outputVal=outputVal.substring(0, outputVal.length()-1);
			 outputVal=outputVal+"\n";
			 
			 outputVal=outputVal+"ADB:";
			 for(Object object : ADB) {
				    String element = (String) object;
				    outputVal=outputVal+element+","; 			    
				}
			 if(!ADB.isEmpty())
			 outputVal=outputVal.substring(0, outputVal.length()-1);
			 outputVal=outputVal+"\n";
			 
			 outputVal=outputVal+"REB:";
			 for(Object object : REB) {
				    String element = (String) object;
				    outputVal=outputVal+element+","; 			    
				}
			 if(!REB.isEmpty())
			 outputVal=outputVal.substring(0, outputVal.length()-1);
			 outputVal=outputVal+"\n";
			 
			 outputVal=outputVal+"RGF:";
			 outputVal=RegisterFile.appendData(outputVal);
			 outputVal=outputVal+"\n";
			 outputVal=outputVal+"DAM:";
			 outputVal=DataMemory.appendData(outputVal);
			 outputVal=outputVal+"\n";
			 outputVal=outputVal+"\n";		  
			 outputWriter.write(outputVal);
			
		}
	 
		public static void Issue()
		{
			String intr=INB.peek();
			if(intr!=null)
			{
			if(intr.contains("LD"))
			{
				LIB.add(intr);
				INB.poll();
			}
			else
			{
			AIB.add(intr);
			INB.poll();
			}
			}
			
		}
		public static void addressCalculation()
		{
			String intr=LIB.poll();
			if(intr!=null)
			{
			String parts[]=intr.split(",");
			String reg=parts[1];
			int operand1=Integer.valueOf(parts[2]);
			int operand2=Integer.valueOf(parts[3].substring(0, parts[3].length()-1));
			int result=operand1+operand2;
			StringBuffer temp=new StringBuffer();
			temp.append("<");
			temp.append(reg);
			temp.append(",");
			temp.append(result);
			temp.append(">");
			ADB.add(temp.toString());
			}
			
			
		}
		public static void load()
		{
			String adb_instru=ADB.poll();
			if(adb_instru!=null)
			{
			String parts[]=adb_instru.split(",");
			int address=Integer.valueOf(parts[1].substring(0, 1));
			int regVal=DataMemory.getDataMe(address);
			parts[1]=String.valueOf(regVal);
			
			StringBuilder builder = new StringBuilder();
			builder.append(parts[0]);
			builder.append(",");
			builder.append(parts[1]);
			builder.append(">");
			REB.add(builder.toString());
			}
		}
		
		public static void write()
		{
			String finalIntr=REB.poll();
			if(finalIntr!=null)
			{
			String temp=finalIntr.substring(1,finalIntr.length()-1);
			String parts[]=temp.split(",");
			int regNo=Integer.valueOf(parts[0].substring(1));
			RegisterFile.updateReg(regNo, Integer.parseInt(parts[1]));
			}
		}
		 
		public static void Add_SubtractUnit()
		{
			String intruct=AIB.poll();
			if(intruct!=null)
			{
			String parts[]=intruct.split(",");
			String operation=parts[0].substring(1);
			int destinationReg=Integer.parseInt(parts[1].substring(1));
			int operand1=Integer.parseInt(parts[2]);
			int operand2=Integer.parseInt(parts[3].substring(0, 1));
			int result=0;
			
			if(operation.equalsIgnoreCase("ADD"))
				result=operand1+operand2;
			else
				result=operand1-operand2;
			StringBuffer s=new StringBuffer();
			s.append("<R");
			s.append(destinationReg);
			s.append(",");
			s.append(result);
			s.append(">");
			REB.add(s.toString());	
			 
			}
		}
		
		
		public static void readAndDecode()
		{
			String intruction=INM.peek();
			if(intruction!=null)
			{
			String[] parts=intruction.split(",");
			
			if(parts[0].contains("LD"))  // need to check only one operand
			{
				int regNo=Integer.valueOf(parts[2].substring(1));
				int response=RegisterFile.getRegVal(regNo);
				if(response!=-1) // source operand is available
				{
					parts[2]=String.valueOf(response);
					StringBuilder builder = new StringBuilder();
					builder.append(parts[0]);
					builder.append(",");
					builder.append(parts[1]);
					builder.append(",");
					builder.append(parts[2]);
					builder.append(",");
					builder.append(parts[3]);				 
					//builder.append(">");
					INM.poll();
					INB.add(builder.toString());
				}
					
			}
			else   // assuming it is sum or sub as there are three instruction only
			{
				int regNo1=Integer.valueOf(parts[2].substring(1));
				int regNo2=Integer.valueOf(parts[3].substring(1,parts[3].length()-1));
				int regresponse1=RegisterFile.getRegVal(regNo1);
				int regresponse2=RegisterFile.getRegVal(regNo2);
				if(regresponse1!=-1 && regresponse2 !=-1) // both source operands are available
				{
					parts[2]=String.valueOf(regresponse1);
					parts[3]=String.valueOf(regresponse2);
					StringBuilder builder = new StringBuilder();
					builder.append(parts[0]);
					builder.append(",");
					builder.append(parts[1]);
					builder.append(",");
					builder.append(parts[2]);
					builder.append(",");
					builder.append(parts[3]);				 
					builder.append(">");
					INM.poll();
					INB.add(builder.toString());
				}
				
			}
		}
	}


	}

	class DataMemory {
	static HashMap<Integer, Integer> dataMemoryMap = new HashMap();	
		
		public DataMemory()
		{
			/*
			 * Initiate register with the value given in the register.txt
			 */
			for(int i=0;i<7;i++)
			{
				dataMemoryMap.put(i, -1);
				 			 
			}	
		}
		 
		public static void updateDataMem(int address, int value)
		{
			dataMemoryMap.put(address, value);
		}
		
		public static int getDataMe(int address)
		{
			return dataMemoryMap.get(address);
		}

		public static String appendData(String s)
		{	 
			
			Set<Integer> keySet=dataMemoryMap.keySet();
			Iterator it = keySet.iterator();
			while(it.hasNext())
			{
				Integer addressNo=(Integer) it.next();
				Integer addressVal=dataMemoryMap.get(addressNo);
				if(addressVal!=-1)
				{
					 s=s+"<"+addressNo+","+addressVal+">,";
				}			
			}
			s=s.substring(0, s.length()-1);
			
			return s;
		}
	}
	class RegisterFile {
	static HashMap<Integer, Integer> regDataMap = new HashMap();	
		
		public RegisterFile()
		{
			/*
			 * Initiate register with the value given in the register.txt
			 */
			for(int i=0;i<7;i++)
			{
				regDataMap.put(i, -1);
				 			 
			}	
		}
		 
		public static void updateReg(int regNo, int value)
		{
			regDataMap.put(regNo, value);
		}
		
		public static int getRegVal(int regNo)
		{
			return regDataMap.get(regNo);
		}
		
		public static String appendData(String s)
		{	 
			
			Set<Integer> keySet=regDataMap.keySet();
			Iterator it = keySet.iterator();
			while(it.hasNext())
			{
				Integer regNo=(Integer) it.next();
				Integer regVal=regDataMap.get(regNo);
				if(regVal!=-1)
				{
					 s=s+"<"+"R"+regNo+","+regVal+">,";
				}			
			}
			s=s.substring(0, s.length()-1);
			
			return s;
		}
	}
 
