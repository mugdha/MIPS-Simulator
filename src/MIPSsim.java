/*
 * On my honor, I have neither given nor received unauthorized aid on this 
 *assignment
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.HashMap; 
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;



public class MIPSsim {	 
	
	 public static void main(String args[]) {
         if (args.length < 1) {
                 System.out.println("Please enter input file");
                 System.exit(0);
         }  
         try {               
                 String inputFile = args[0];
                 new Disassembler(inputFile);  
                 new Simulator (inputFile);
                 
         } catch (FileNotFoundException ex) {
                 System.out.println("Input File not found! Try again");
         } catch (IOException ex)
         {
        	 	System.out.println("I/O Exception" + ex.getMessage());
                 System.exit(0);
         }
         catch (Exception ex) {
                 System.out.println("Unknown Exception occured. " + ex.getMessage());
                 System.exit(0);
         }

 }
}	 
	 
	  



class LWHandler {
    private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    private String instrnName;
    private String cycle;
    private int cycleCount;

    private String rt;
    private int rtNum;

    private String base;
    private int baseNum;

    private int offsetVal;

    public LWHandler(String binIns, int addr) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            instrnName = "LW";
            address = addr;
            rtNum = -1;
            baseNum = -1;

            rt = "R";
            base = "R";

            constructDecodedInstrn();
    }
    public LWHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        instrnName = "LW";
        cycle="Cycle:";
        cycleCount=cycle_value;
        address = addr;
        rtNum = -1;
        baseNum = -1;

        rt = "R";
        base = "R";

        constructDecodedInstrn_simulator();
}

    public void constructDecodedInstrn() {
            baseNum = Integer.parseInt(binaryInstrn.substring(6, 11), 2);
            rtNum = Integer.parseInt(binaryInstrn.substring(11, 16), 2);
            offsetVal = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16,
                            32));

            base = base + baseNum;
            rt = rt + rtNum;

            decodedInstrn = instrnName + " " + rt + ", " + offsetVal + "(" + base + ")";
             
            decodedInstrn = binaryInstrn + "\t" + address  + "\t" + decodedInstrn+"\n";
             
    }
    public void constructDecodedInstrn_simulator() {
        baseNum = Integer.parseInt(binaryInstrn.substring(6, 11), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(11, 16), 2);
        offsetVal = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16,
                        32));

        base = base + baseNum;
        rt = rt + rtNum;

        decodedInstrn = instrnName + " " + rt + ", " + offsetVal + "(" + base + ")";
         
        decodedInstrn = cycle+cycleCount + "\t" + address  + "\t" + decodedInstrn;
         
}

    public String getDecodedInstrn() {
            return decodedInstrn;
    }
    public int getBasereg()
    {
    	return baseNum;
    }
    public int getRTreg()
    {
    	return rtNum;
    }
    public int getOffsetVal()
    {
    	return offsetVal;
    }

}


class ADDIHandler {
    private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    private String instrnName;
    private String rs;      
    private String rt;
    private String immStr;
    private String cycle;
    
    private int rsNum;
    private int rtNum;
    private int immValue;
    private int cycleCount;
    
    
    public ADDIHandler(String binIns, int addr) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            address = addr;
            rsNum = -1;
            rtNum = -1;
            immValue = -1;
            
            instrnName = "ADDI";
            rs = "R";
            rt = "R";
            immStr = "#";
            
            decodedInstrn = "";
            
            constructDecodedInstrn();
    }
    public ADDIHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        immValue = -1;
        cycleCount=cycle_value;
        
        instrnName = "ADDI";
        rs = "R";
        rt = "R";
        immStr = "#";
        cycle="Cycle:";
        
        decodedInstrn = "";
        
        constructDecodedInstrn_simulator();
}
    
    /**
     * Identify the individual fields and construct the instruction in assembly format 
     */
    public void constructDecodedInstrn()
    {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
            
        immValue = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16, 32));
            
            rs = rs + rsNum;
            rt = rt +  rtNum;
            immStr = immStr + immValue;
            
            decodedInstrn = instrnName + " " + rt + ", " + rs + ", " + immStr;                 
            decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";
             
    }
    public void constructDecodedInstrn_simulator()
    {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
            
        immValue = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16, 32));
            
            rs = rs + rsNum;
            rt = rt +  rtNum;
            immStr = immStr + immValue;
            
            decodedInstrn = instrnName + " " + rt + ", " + rs + ", " + immStr;                 
            decodedInstrn = cycle +cycleCount+ "\t" + address + "\t" + decodedInstrn;
             
    }
    public String getDecodedInstrn()
    {
            return decodedInstrn;
    }
    public int getRSValue()
    {
    	return rsNum;
    }
    public int getRTValue()
    {
    	return rtNum;
    }
    public int getImmediateVal()
    {
    	return immValue;
    }
}

class ANDHandler {
    private String binaryInstrn;
    private String decodedInstrn;

    private int address;
    
    private String instrnName;
    private String rs;
    private String rt;
    private String rd;
    private String cycle;

    private int rsNum;
    private int rtNum;
    private int rdNum;
    private int cycleCount;

    public ANDHandler(String binIns, int addr) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            address = addr;
            rsNum = -1;
            rtNum = -1;
            rdNum = -1;

            instrnName = "AND";
            rs = "R";
            rt = "R";
            rd = "R";

            decodedInstrn = "";

            constructDecodedInstrn();
    }
    public ANDHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        rdNum = -1;
        cycle="Cycle:";

        instrnName = "AND";
        rs = "R";
        rt = "R";
        rd = "R";
        cycleCount=cycle_value;

        decodedInstrn = "";

        constructDecodedInstrn_simulator();
}

    /**
     * Identify the individual fields and construct the instruction in assembly
     * format
     */
    public void constructDecodedInstrn() {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;                
            decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";
             
    }
    public void constructDecodedInstrn_simulator() {
    	
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;                
            decodedInstrn = cycle+cycleCount + "\t" + address + "\t" + decodedInstrn;
             
    }

    public String getDecodedInstrn() {
            return decodedInstrn;
    }
    public int getRSNum()
    {
    	return rsNum;
    }
    public int getRTNum()
    {
    	return rtNum;
    }
    public int getRDNum()
    {
    	return rdNum;
    }
     
}
class ANDIHandler {
	  private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    private String instrnName;
    private String rs;      
    private String rt;
    private String immStr;
    private String cycle;
    
    private int rsNum;
    private int rtNum;
    private int immValue;
    private int cycleCount;
    
    public ANDIHandler(String binIns, int addr) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        immValue = -1;
        
        instrnName = "ANDI";
        rs = "R";
        rt = "R";
        immStr = "#";
        
        decodedInstrn = "";
        
        constructDecodedInstrn();
}
    public ANDIHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        immValue = -1;
        cycle="Cycle:";
        
        instrnName = "ANDI";
        rs = "R";
        rt = "R";
        immStr = "#";
        cycleCount=cycle_value;
        
        decodedInstrn = "";
        
        constructDecodedInstrn_simulator();
}

/**
 * Identify the individual fields and construct the instruction in assembly format 
 */
public void constructDecodedInstrn()
{
	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
    rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        
    immValue = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16, 32));
        
        rs = rs + rsNum;
        rt = rt +  rtNum;
        immStr = immStr + immValue;
        
        decodedInstrn = instrnName + " " + rt + ", " + rs + ", " + immStr;                 
        decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn;
         
}
public void constructDecodedInstrn_simulator()
{
	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
    rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        
    immValue = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16, 32));
        
        rs = rs + rsNum;
        rt = rt +  rtNum;
        immStr = immStr + immValue;
        
        decodedInstrn = instrnName + " " + rt + ", " + rs + ", " + immStr;                 
        decodedInstrn = cycle+cycleCount + "\t" + address + "\t" + decodedInstrn+"\n";
         
}
public String getDecodedInstrn()
{
        return decodedInstrn;
}
public int getRSValue()
{
	return rsNum;
}
public int getRTValue()
{
	return rtNum;
}
public int getImmediateVal()
{
	return immValue;
}
}
class BEQHandler {
    private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    private String instrnName;
    private String rs;      
    private String rt;
    private String offsetStr;
    private String cycle;
    
    private int rsNum;
    private int rtNum;
    private int offsetVal;
    private int cycleCount;
    
    public BEQHandler(String binIns, int addr) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            address = addr;
            rsNum = -1;
            rtNum = -1;
            offsetVal = -1;
            
            instrnName = "BEQ";
            rs = "R";
            rt = "R";
            offsetStr = "#";

            constructDecodedInstrn();
    }
    public BEQHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        offsetVal = -1;
        cycle="Cycle:";
        
        instrnName = "BEQ";
        rs = "R";
        rt = "R";
        offsetStr = "#";
        cycleCount=cycle_value;

        constructDecodedInstrn_simulator();
}

    
    /**
     * Identify the individual fields and construct the instruction in assembly format 
     */
    public void constructDecodedInstrn()
    {
            rsNum = Integer.parseInt(binaryInstrn.substring(6, 11), 2);
            rtNum = Integer.parseInt(binaryInstrn.substring(11, 16), 2);
            
            //offset is 18 bits i.e. 16bit offset is shifted left 2 bits.
            String offsetBin = binaryInstrn.substring(16, 32) + "00";
            offsetVal = Integer.parseInt(offsetBin, 2);
             rs = rs + rsNum;
            rt = rt +  rtNum;
            offsetStr = offsetStr + offsetVal;
            
            decodedInstrn = instrnName + " " + rs + ", " + rt + ", " + offsetStr;
            decodedInstrn = binaryInstrn + "\t"  + address + "\t" + decodedInstrn+"\n";
             
    }
    public void constructDecodedInstrn_simulator()
    {
            rsNum = Integer.parseInt(binaryInstrn.substring(6, 11), 2);
            rtNum = Integer.parseInt(binaryInstrn.substring(11, 16), 2);
            
            //offset is 18 bits i.e. 16bit offset is shifted left 2 bits.
            String offsetBin = binaryInstrn.substring(16, 32) + "00";
            offsetVal = Integer.parseInt(offsetBin, 2);
             rs = rs + rsNum;
            rt = rt +  rtNum;
            offsetStr = offsetStr + offsetVal;
            
            decodedInstrn = instrnName + " " + rs + ", " + rt + ", " + offsetStr;
            decodedInstrn = cycle+cycleCount + "\t"  + address + "\t" + decodedInstrn;
             
    }
    public String getDecodedInstrn()
    {
            return decodedInstrn;
    }
    public int getRT()
    {
    	return rtNum;
    }
    public int getRS()
    {
    	return rsNum;
    }
    public int getOffsetVal()
    {
    	return offsetVal;
    }
}
class BGTZHandler {
	private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    private String instrnName;
    private String rs;     
    private String offsetStr;   
    private String cycle;
    
    private int rsNum;    
    private int offsetVal;
    private int cycleCount;
    
    public BGTZHandler(String binIns, int addr)
	{
		binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        offsetVal = -1;
        
        instrnName = "BGTZ";
        rs = "R";        
        offsetStr = "#";

        constructDecodedInstrn();
}

    public BGTZHandler(String binIns, int addr,int cycle_value)
	{
		binaryInstrn = binIns;
		cycle="Cycle:";
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        offsetVal = -1;
        
        instrnName = "BGTZ";
        rs = "R";        
        offsetStr = "#";
        cycleCount=cycle_value;

        constructDecodedInstrn_simulator();
}

    /**
	 * Identify the individual fields and construct the instruction in assembly
	 * format
	 */
	private void constructDecodedInstrn() {
		  rsNum = Integer.parseInt(binaryInstrn.substring(6, 11), 2);
		  String offsetBin = binaryInstrn.substring(16, 32) + "00";
		  offsetVal = Integer.parseInt(offsetBin, 2);
          rs = rs + rsNum;         
         offsetStr = offsetStr + offsetVal;
         decodedInstrn = instrnName + " " + rs + ", " + offsetStr;
         decodedInstrn = binaryInstrn + "\t"  + address + "\t" + decodedInstrn+"\n";
          
		 
	}
	private void constructDecodedInstrn_simulator() {
	   rsNum = Integer.parseInt(binaryInstrn.substring(6, 11), 2);
	   String offsetBin = binaryInstrn.substring(16, 32) + "00";
	   offsetVal = Integer.parseInt(offsetBin, 2);
       rs = rs + rsNum;         
       offsetStr = offsetStr + offsetVal;
       decodedInstrn = instrnName + " " + rs + ", " + offsetStr;
       decodedInstrn = cycle+cycleCount + "\t"  + address + "\t" + decodedInstrn;
        
		 
	}
	public String getDecodedInstrn() {
		return decodedInstrn;
	}
	public int getRS()
	{
		return rsNum;
	}
	public int getOffsetVal()
	{
		return offsetVal;
	}
}
class BREAKHandler {
	private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    private String instrnName;
    private String cycle;
    private int cycleCount;
       
    public BREAKHandler(String binIns, int addr) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;   
        instrnName = "BREAK";
        constructDecodedInstrn();
}
    public BREAKHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;   
        cycle="Cycle:";
        instrnName = "BREAK";
        cycleCount=cycle_value;
        constructDecodedInstrn_simulator();
}
/**
 * Identify the individual fields and construct the instruction in assembly format 
 */
public void constructDecodedInstrn()
{

	decodedInstrn = instrnName;
        decodedInstrn = binaryInstrn + "\t"  + address + "\t" + decodedInstrn+"\n";        
}
public void constructDecodedInstrn_simulator()
{

	decodedInstrn = instrnName;
        decodedInstrn = cycle+cycleCount + "\t"  + address + "\t" + decodedInstrn;        
}

public String getDecodedInstrn()
{
        return decodedInstrn;
}

}
class DataFile {
	
	TreeMap<Integer, Integer> memoryDataMap = new TreeMap<Integer, Integer>();	 
	 
	public void setDataTreeMap(int address, int value)
	{
		memoryDataMap.put(address, value);
	}
	public int getDataTreeMapVal(int address)
	{
		int value=memoryDataMap.get(address);
		return value;
	}
	 
	public boolean containsKey(int address) 
	{
		boolean val=memoryDataMap.containsKey(address);
		return val;
	}
	
	public String Print()
	{
		Integer firstAddress=memoryDataMap.firstKey();	 
		int size=memoryDataMap.size();
		int loopCount=(int) Math.ceil(size*1.0/8);
		int increment=32;		 
		StringBuffer str=new StringBuffer("Data"+"\n");
		
		for(int row=0;row<loopCount;row++)
		{	
			int labelAddress=firstAddress+row*increment;
			str.append(labelAddress+":");
			for(int column=0;column<8;column++)
			{				 
				int nextAddres=labelAddress + 4*column;				
				str.append("\t"+memoryDataMap.get(nextAddres));					  
			}
			str=str.append("\n");			
		}		
		return str.toString();

	}
	

}
class Disassembler {

    public static int initAddress = 128;
    private String inputFileName;
    private String outputFileName;
    private File inputFile;
    private BufferedReader inputStream;
    private File outputFile;
    private BufferedWriter  outputWriter;
    private int codeSize;
    private int instrnCount;
    private static int instrnLen = 32; // Each instruction is 32 bits
    // bytes
    private String[] binaryInstrn;
    public static DataFile d1=new DataFile();
    public static TreeMap<Integer,String> binInstnAddrs = new TreeMap<Integer,String>();

    public Disassembler(String input)
                    throws FileNotFoundException, IOException {
           
            String output="disassembly.txt";
            this.inputFileName = input;
            this.outputFileName = output;
            // Initialize input file
            File inputFile = new File("."+File.separator+inputFileName);
            inputStream = new BufferedReader(new FileReader(inputFile)); 
            codeSize = (int) inputFile.length();      // codeSize is the length of the binary file on whole(number of character +\n+\r        

            // Initialize output file
            outputFile = new File(outputFileName);
            while (true) {
                    if (outputFile.createNewFile() != true) {
                            outputFile.delete();
                            continue;
                    } else {
                            break;
                    }
                    } 
            
            outputWriter = new BufferedWriter(new FileWriter("disassembly.txt")); 
            
            // initialize byte array to hold the binary instructions
            instrnCount = codeSize / instrnLen;
            binaryInstrn = new String[instrnCount];

            // initialize opcodes map
            new Opcodes();

            // Start disassembly process
            startDisassembly();
    }

    /**
     * This method takes reads from the file and converts to a string array.
     * Each array element is of length 32 and corresponds of zeros and ones.
     */
    private void startDisassembly() {
            // Read 32 bit integers from file, convert to binary string and store in
            // binaryInstrns array
            boolean isBreakEncountered = false;
            int address = initAddress;
            try {
                    for (int i = 0; i < instrnCount; i++) {
                            String data = inputStream.readLine();   
                            if(data==null)
                            	break;
                            long xbin = Long.parseLong(data, 2);
                            
                            binaryInstrn[i] = addLeadingZeros(xbin);                               
                            
                            //If break instruction is not yet reached, keep decoding the instructions
                            if (!isBreakEncountered) {
                                    InstructionType type = SimUtils.getInstrnType(binaryInstrn[i]);
                                    String decodedInstrn = SimUtils.getDecodedInstrn(binaryInstrn[i], type, address);
                                    //store address and corresponding instructions
                                    binInstnAddrs.put(address,binaryInstrn[i]);
                                    outputWriter.write(decodedInstrn);
                                   // outputWriter.newLine();
                                    if (type == InstructionType.BREAK) {
                                            isBreakEncountered = true;
                                    }
                            }
                            // If  break was encountered before, just print out the remaining bytes.
                            else
                            { 		int val =getDataFromBinary(binaryInstrn[i]);
                            		d1.memoryDataMap.put(address, val);                                		                               		 
                            		String decodedInstrn = binaryInstrn[i] + "\t" + address + "\t" + val+"\n";
                                    outputWriter.write(decodedInstrn);
                                                                    		 
                            }
                            address += 4;                                
                    }
                     outputWriter.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
            
    } 
   

	/**
     * This method takes a string s and adds leading zeros When the binary file
     * is read, 32 bit integers are read. The Integer.toBinaryString truncates
     * the leading zeros. This method adds the leading zeros to make the
     * instruction length 32.
     * 
     * @param s
     * @return
     */
   
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
   
}

class MULHandler {
	private String binaryInstrn;
    private String decodedInstrn;

    private int address;
    
    private String instrnName;
    private String rs;
    private String rt;
    private String rd;
    private String cycle;

    private int rsNum;
    private int rtNum;
    private int rdNum;
    private int cycleCount;

    public MULHandler(String binIns, int addr) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            address = addr;
            rsNum = -1;
            rtNum = -1;
            rdNum = -1;

            instrnName = "MUL";
            rs = "R";
            rt = "R";
            rd = "R";

            decodedInstrn = "";

            constructDecodedInstrn();
    }
    public MULHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        cycle="Cycle:";
        rsNum = -1;
        rtNum = -1;
        rdNum = -1;
        cycleCount=cycle_value;

        instrnName = "MUL";
        rs = "R";
        rt = "R";
        rd = "R";

        decodedInstrn = "";

        constructDecodedInstrn_simulator();
}
    /**
     * Identify the individual fields and construct the instruction in assembly
     * format
     */
    public void constructDecodedInstrn() {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;                
            decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";
             
    }
    
    public void constructDecodedInstrn_simulator() {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;                
            decodedInstrn = cycle+cycleCount + "\t" + address + "\t" + decodedInstrn+"\n";
             
    }

    public String getDecodedInstrn() {
            return decodedInstrn;
    }
    public int getRSNum()
    {
    	return rsNum;
    }
    public int getRTNum()
    {
    	return rtNum;
    }
    public int getRDNum()
    {
    	return rdNum;
    }
     

}

class NORHandler {
    private String binaryInstrn;
    private String decodedInstrn;

    private int address;
    
    private String instrnName;
    private String rs;
    private String rt;
    private String rd;
    private String cycle;

    private int rsNum;
    private int rtNum;
    private int rdNum;
    private int cycleCount;

    public NORHandler(String binIns, int addr) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            address = addr;
            rsNum = -1;
            rtNum = -1;
            rdNum = -1;

            instrnName = "NOR";
            rs = "R";
            rt = "R";
            rd = "R";

            decodedInstrn = "";

            constructDecodedInstrn();
    }
    public NORHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        rdNum = -1;
        cycle="Cycle:";
        
        instrnName = "NOR";
        rs = "R";
        rt = "R";
        rd = "R";
        cycleCount=cycle_value;

        decodedInstrn = "";

        constructDecodedInstrn_simulator();
}

    /**
     * Identify the individual fields and construct the instruction in assembly
     * format
     */
    public void constructDecodedInstrn() {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;
            decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";
            
    }
    public void constructDecodedInstrn_simulator() {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;
            decodedInstrn = cycle+cycleCount + "\t" + address + "\t" + decodedInstrn;
            
    }

    public String getDecodedInstrn() {
            return decodedInstrn;
    }
    public int getRSNum()
    {
    	return rsNum;
    }
    public int getRTNum()
    {
    	return rtNum;
    }
    public int getRDNum()
    {
    	return rdNum;
    }
}


class Opcodes {
    
    public static Map<InstructionType, String> category1Map = new HashMap<InstructionType, String>();
    public static Map<InstructionType, String> category2Map = new HashMap<InstructionType, String>();
    public static Map<InstructionType, String> category3Map = new HashMap<InstructionType, String>();
    public Opcodes()
    {
    		category1Map.put(InstructionType.J,"000");
    		category1Map.put(InstructionType.BEQ,"010");
    		category1Map.put(InstructionType.BGTZ,"100");
    		category1Map.put(InstructionType.BREAK,"101");        	
    		category1Map.put(InstructionType.SW, "110");
    		category1Map.put(InstructionType.LW, "111");

            
    		category2Map.put(InstructionType.ADD, "000");
    		category2Map.put(InstructionType.SUB, "001");
    		category2Map.put(InstructionType.MUL, "010");
    		category2Map.put(InstructionType.AND, "011");
    		category2Map.put(InstructionType.OR, "100");
    		category2Map.put(InstructionType.XOR, "101");
    		category2Map.put(InstructionType.NOR, "110");

            category3Map.put(InstructionType.ADDI, "000");
            category3Map.put(InstructionType.ANDI, "001");
            category3Map.put(InstructionType.ORI, "010");
            category3Map.put(InstructionType.XORI, "011");
            
    }
}

class ORHandler {
    private String binaryInstrn;
    private String decodedInstrn;

    private int address;
    
    private String instrnName;
    private String rs;
    private String rt;
    private String rd;
    private String cycle;

    private int rsNum;
    private int rtNum;
    private int rdNum;
    private int cycleCount;

    public ORHandler(String binIns, int addr) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            address = addr;
            rsNum = -1;
            rtNum = -1;
            rdNum = -1;

            instrnName = "OR";
            rs = "R";
            rt = "R";
            rd = "R";

            decodedInstrn = "";

            constructDecodedInstrn();
    }
    public ORHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        rdNum = -1;
        cycle="Cycle:";
        cycleCount=cycle_value;

        instrnName = "OR";
        rs = "R";
        rt = "R";
        rd = "R";

        decodedInstrn = "";

        constructDecodedInstrn_simulator();
}

    /**
     * Identify the individual fields and construct the instruction in assembly
     * format
     */
    public void constructDecodedInstrn() {
    	
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;                
            decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";
            
    }

    public void constructDecodedInstrn_simulator() {
    	
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;                
            decodedInstrn = cycle+cycleCount + "\t" + address + "\t" + decodedInstrn;
            
    }

    public String getDecodedInstrn() {
            return decodedInstrn;
    }
    public int getRSNum()
    {
    	return rsNum;
    }
    public int getRTNum()
    {
    	return rtNum;
    }
    public int getRDNum()
    {
    	return rdNum;
    }
     
}

class ORIHandler {
	 private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    private String instrnName;
    private String rs;      
    private String rt;
    private String immStr;
    private String cycle;
    
    private int rsNum;
    private int rtNum;
    private int immValue;
    private int cycleCount;
    
    public ORIHandler(String binIns, int addr) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        immValue = -1;
        
        instrnName = "ORI";
        rs = "R";
        rt = "R";
        immStr = "#";
        
        decodedInstrn = "";
        
        constructDecodedInstrn();
}
    public ORIHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        cycle="Cycle:";
        rsNum = -1;
        rtNum = -1;
        immValue = -1;
        
        instrnName = "ORI";
        rs = "R";
        rt = "R";
        immStr = "#";
        cycleCount=cycle_value;
        
        decodedInstrn = "";
        
        constructDecodedInstrn_simulator();
}
/**
 * Identify the individual fields and construct the instruction in assembly format 
 */
public void constructDecodedInstrn()
{
	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
    rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        
    immValue = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16, 32));
        
        rs = rs + rsNum;
        rt = rt +  rtNum;
        immStr = immStr + immValue;
        
        decodedInstrn = instrnName + " " + rt + ", " + rs + ", " + immStr;                 
        decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";
         
}
public void constructDecodedInstrn_simulator()
{
	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
    rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        
    immValue = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16, 32));
        
        rs = rs + rsNum;
        rt = rt +  rtNum;
        immStr = immStr + immValue;
        
        decodedInstrn = instrnName + " " + rt + ", " + rs + ", " + immStr;                 
        decodedInstrn = cycle+cycleCount + "\t" + address + "\t" + decodedInstrn;
         
}

public String getDecodedInstrn()
{
        return decodedInstrn;
}
public int getRSValue()
{
	return rsNum;
}
public int getRTValue()
{
	return rtNum;
}
public int getImmediateVal()
{
	return immValue;
}
}

class RegisterFile {
	
	TreeMap<Integer, Integer> regDataMap = new TreeMap<Integer, Integer>();	
	
	public RegisterFile()
	{
		/*
		 * Initiate register with zero
		 */
		for(int i=0;i<32;i++)
		{
			regDataMap.put(i, 0);
			 			 
		}	
	}
	 
	public void updateReg(int regNo, int value)
	{
		regDataMap.put(regNo, value);
	}
	
	public int getRegVal(int regNo)
	{
		return regDataMap.get(regNo);
	}
	
	 
	/**
	 * Print contents of register file
	 */
	public String print()
	{
		StringBuffer s1 = new StringBuffer("R00:");
		StringBuffer s2=new StringBuffer("R08:");
		StringBuffer s3=new StringBuffer("R16:");
		StringBuffer s4=new StringBuffer("R24:");
		
		for(int i=0;i<8;i++)
		{
			s1.append("\t" + regDataMap.get(i));
		}
		for(int i=8;i<16;i++)
		{
			s2.append("\t" + regDataMap.get(i));
		}
		for(int i=16;i<24;i++)
		{
			s3.append("\t" + regDataMap.get(i));
		}
		for(int i=24;i<32;i++)
		{
			s4.append("\t" + regDataMap.get(i));
		}
		
		StringBuffer str=new StringBuffer("\n"+"Registers"+"\n");		 
		str.append(s1+"\n"+s2+"\n"+s3+"\n"+s4+"\n");
		return str.toString();
	}
	
}


class Simulator {
	 public static int initAddress = 128;	  
    private String inputFileName;
    private String outputFileName;
    private File inputFile;
    private BufferedReader inputStream; 
    private File outputFile;
    private BufferedWriter  outputWriter;
    //private int codeSize;
    public static TreeMap<Integer,String> addrInstn=Disassembler.binInstnAddrs;
    public static int cycleCounter;    

	public Simulator(String input)throws FileNotFoundException, IOException  {
		 String output="simulation.txt";
		 this.inputFileName = input;
        this.outputFileName = output;
        // Initialize input file
        File inputFile = new File("."+File.separator + inputFileName);
        inputStream = new BufferedReader(new FileReader(inputFile)); 
         
        outputFile = new File(outputFileName);
        while (true) {
                if (outputFile.createNewFile() != true) {
                        outputFile.delete();
                        continue;
                } else {
                        break;
                }

        }
        outputWriter = new BufferedWriter(new FileWriter("simulation.txt"));
       
        new Opcodes(); 
        startSimulator();
	}
	private void startSimulator() {
		 	 		  
		 boolean isDataBegin = false;
		 int address=initAddress;
		 try {  
			 if(addrInstn.containsKey(address))
			 {	 
            do{            	  
           	 if(!isDataBegin)
           	 {	 
           	 String instrn=addrInstn.get(address);
           	 InstructionType type = SimUtils.getInstrnType(instrn);
           	 cycleCounter++;
           	 String decodedInstrn= SimUtils.PrintCycle(instrn, type, address,cycleCounter);
           	 outputWriter.write(decodedInstrn);
               // outputWriter.newLine();
                if (type == InstructionType.BREAK) 
                	{
               	 isDataBegin = true;
                	}               
                
           	 }
           	 else
           	 {
           		 break;
           	 }
           
            address=SimUtils.nextAddr;
            
            }while(addrInstn.containsKey(address));            	 
           	 
            }
			  outputWriter.close();
		 }
		 catch (IOException e) {
            e.printStackTrace();
    }
	}  
}



class SimUtils {
	 
	public static RegisterFile r1=new RegisterFile();
	public static DataFile d2=Disassembler.d1;
	public static int nextAddr=0;
	public static int currentAddr=0;
	
        public static InstructionType getInstrnType(String instrn) {
                InstructionType type = InstructionType.NOT_SUPPORTED;
                String opcode = "";

                // Identify the opcode
                String first3Bits = instrn.substring(0, 3);

                if (first3Bits.equals("000")) {
                        // If first 3 bits is zero, then it is a category 1 instruction 
                        // last 6
                        // bits are the opcode
                        opcode = instrn.substring(3, 6);
                        Opcodes.category1Map.values();
                        // if the opcode is not in map, then return not supported
                        if (!Opcodes.category1Map.containsValue(opcode)) {
                                return InstructionType.NOT_SUPPORTED;
                        }
                        // iterate through the splOpcodesMap for instruction type
                        Set<InstructionType> keys = Opcodes.category1Map.keySet();
                        for (InstructionType tempType : keys) {
                                String tempOp = Opcodes.category1Map.get(tempType);
                                if (tempOp.equals(opcode)) {
                                        type = tempType;
                                }
                        }
                } else if (first3Bits.equals("110")) {
                        // If first 3 bits is 110 they are category 2  
                        // instructions
                        opcode = instrn.substring(13, 16);
                        Opcodes.category2Map.values();
                        // if the opcode is not in map, then return not supported
                        if (!Opcodes.category2Map.containsValue(opcode)) {
                                return InstructionType.NOT_SUPPORTED;
                        }
                        // iterate through the regImmOpcodesMap for instruction type
                        Set<InstructionType> keys = Opcodes.category2Map.keySet();
                        for (InstructionType tempType : keys) {
                                String tempOp = Opcodes.category2Map.get(tempType);
                                if (tempOp.equals(opcode)) {
                                        type = tempType;
                                }
                        }
                }  else if (first3Bits.equals("111")) {
                            // If first 3 bits is 111 they are register immediate
                            // instructions
                            opcode = instrn.substring(13, 16);
                            // if the opcode is not in map, then return not supported
                            if (!Opcodes.category3Map.containsValue(opcode)) {
                                    return InstructionType.NOT_SUPPORTED;
                            }
                            // iterate through the regImmOpcodesMap for instruction type
                            Set<InstructionType> keys = Opcodes.category3Map.keySet();
                            for (InstructionType tempType : keys) {
                                    String tempOp = Opcodes.category3Map.get(tempType);
                                    if (tempOp.equals(opcode)) {
                                            type = tempType;
                                    }
                            }       
                } else {                      
                         return InstructionType.NOT_SUPPORTED;
                         
                }                  
                return type;
        }

        /**
         * This method takes the 32 bit integer as input, finds the appropriate
         * handler for it. This handler is created and the decoded string is
         * returned back to disassemble method.
         * 
         * @param binaryInstrn
         * @param type
         * @param address
         * @return String(decoded instruction)
         */
        public static String getDecodedInstrn(String binaryInstrn,InstructionType type, int address) {
                String decodedInstrn = "";
                switch (type) {
               
                case J:
                        JHandler jHandler = new JHandler(binaryInstrn, address);
                        decodedInstrn = jHandler.getDecodedInstrn();
                        break;
                case BEQ:
	                    BEQHandler beqHandler = new BEQHandler(binaryInstrn, address);
	                    decodedInstrn = beqHandler.getDecodedInstrn();
	                    break;
                case BGTZ:
	                	BGTZHandler bgtzHandle=new BGTZHandler(binaryInstrn, address);
	                	decodedInstrn=bgtzHandle.getDecodedInstrn();
	                	break;
                case BREAK:
                		BREAKHandler breakHandler=new BREAKHandler(binaryInstrn, address);
                		decodedInstrn=breakHandler.getDecodedInstrn();
                		break;
                case LW:
                		LWHandler lwHandler=new LWHandler(binaryInstrn, address);
                		decodedInstrn=lwHandler.getDecodedInstrn();
                		break;
               case SW:
            	   		SWHandler swHandler=new SWHandler(binaryInstrn, address);
            	   		decodedInstrn=swHandler.getDecodedInstrn();
            	   		break;       

                // category 2 instruction
                case ADD:
                        ADDHandler addHandler = new ADDHandler(binaryInstrn, address);
                        decodedInstrn = addHandler.getDecodedInstrn();
                        break;
                case SUB:
                		SUBHandler subHandler=new SUBHandler(binaryInstrn, address);
                		decodedInstrn=subHandler.getDecodedInstrn();
                        break;
                case MUL:
                		MULHandler mulHandler=new MULHandler(binaryInstrn, address);
                		decodedInstrn=mulHandler.getDecodedInstrn();
                        break;
                case AND:
                		ANDHandler andHandler=new ANDHandler(binaryInstrn, address);
                		decodedInstrn=andHandler.getDecodedInstrn();
                        break;
                case OR:
                		ORHandler orHandler=new ORHandler(binaryInstrn, address);
                		decodedInstrn=orHandler.getDecodedInstrn();
                        break;
                case XOR:
                		XORHandler xorHandler=new XORHandler(binaryInstrn, address);
                		decodedInstrn=xorHandler.getDecodedInstrn();
                        break;
                case NOR:
                        NORHandler norHandler= new NORHandler(binaryInstrn, address);
                        decodedInstrn = norHandler.getDecodedInstrn();
                        break;
                        //category 3 instruction
                case ADDI:
                    ADDIHandler addiHandler = new ADDIHandler(binaryInstrn, address);
                    decodedInstrn = addiHandler.getDecodedInstrn();
                    break;
                case ANDI:
                    ANDIHandler andiHandler = new ANDIHandler(binaryInstrn, address);
                    decodedInstrn = andiHandler.getDecodedInstrn();
                    break;
                case ORI:
                    ORIHandler oriHandler = new ORIHandler(binaryInstrn, address);
                    decodedInstrn = oriHandler.getDecodedInstrn();
                    break;
                case XORI:
                    XORIHandler xoriHandler = new XORIHandler(binaryInstrn, address);
                    decodedInstrn = xoriHandler.getDecodedInstrn();
                    break;       

                default:
                        return "NOT_SUPPORTED";
                }
                return decodedInstrn;
        }

        /**
         * Takes a string of 0's and 1's as input and returns a number as output.
         * This method looks for -ve and +ve numbers and calculates the value
         * accordingly.
         * 
         * @param binaryString
         * @return integerValue
         */
        public static int getIntFromBinaryString(String bin) {
                int num = -1;
                if (bin.substring(0, 1).equals("0")) {
                        num = Integer.parseInt(bin, 2);
                }
                // Handling negative numbers
                if (bin.substring(0, 1).equals("1")) {
                        try {
                                num = Integer.parseInt(bin, 2);
                                num = (65536 - num) * (-1);
                        } catch (NumberFormatException ex) {
                                System.out.println("Error : " + ex.getMessage());
                        }
                }
                return num;
        }

		public static String PrintCycle(String binaryInstruction, InstructionType type,
				int startAddress, int cycleno) {
			 
			StringBuffer decodedInstrn =new StringBuffer("--------------------"+"\n");
			int rdReg=0;
			int rsReg=0;
			int rtReg=0;
			int rsRegVal=0;
			int rtRegVal=0;
			int rdRegVal=0;
			int immdetiateVal=0;
			int offset=0;
			int addr=0;
			currentAddr=startAddress;
			
			switch (type) {
			case J:
					JHandler jHandler=new JHandler(binaryInstruction, startAddress,cycleno);
					nextAddr=jHandler.getNextjumLevel();
					decodedInstrn.append(jHandler.getDecodedInstrn());		 
					break;
			case BEQ:
					BEQHandler beqHandler=new BEQHandler(binaryInstruction, startAddress, cycleno);
					rsReg=beqHandler.getRS();
					rsRegVal=r1.getRegVal(rsReg);
					rtReg=beqHandler.getRT();
					rtRegVal=r1.getRegVal(rtReg);
					offset=beqHandler.getOffsetVal();
					if(rsRegVal==rtRegVal)
					{							 
						nextAddr= currentAddr+offset+4;
					}	
					else
					{
						nextAddr=currentAddr+4;
					}
					decodedInstrn.append(beqHandler.getDecodedInstrn());
					break;
			case BGTZ:
					BGTZHandler bgtzHandler=new BGTZHandler(binaryInstruction, startAddress, cycleno);
					rsReg=bgtzHandler.getRS();
					rsRegVal=r1.getRegVal(rsReg);					 
					offset=bgtzHandler.getOffsetVal();
					if(rsRegVal>0)
					{						 
						nextAddr=currentAddr+offset+4;
					}
					else
					{
						nextAddr=currentAddr+4;
					}
					decodedInstrn.append(bgtzHandler.getDecodedInstrn());
				break;
			case BREAK:
					BREAKHandler breakHandler=new BREAKHandler(binaryInstruction, startAddress, cycleno);
					decodedInstrn.append(breakHandler.getDecodedInstrn());
					nextAddr=000;//Any number for which loop will break and end
				
				break;
			case SW:
					SWHandler swHandler=new SWHandler(binaryInstruction, startAddress,cycleno);
					rsReg=swHandler.getBasereg(); //base register
					rsRegVal=r1.getRegVal(rsReg);
					rtReg=swHandler.getRTreg();
					rtRegVal=r1.getRegVal(rtReg);
					offset=swHandler.getOffsetVal();
					addr=offset+rsRegVal;
					d2.memoryDataMap.put(addr, rtRegVal);
					decodedInstrn.append(swHandler.getDecodedInstrn());	
					nextAddr=currentAddr+4;					
					break;
			case LW:
					LWHandler lwHandler=new LWHandler(binaryInstruction, startAddress,cycleno);
					rsReg=lwHandler.getBasereg(); //base register
					rsRegVal=r1.getRegVal(rsReg);
					rtReg=lwHandler.getRTreg();				 
					offset=lwHandler.getOffsetVal();
					addr=offset+rsRegVal;
					rtRegVal=d2.memoryDataMap.get(addr);
					r1.updateReg(rtReg, rtRegVal);
					decodedInstrn.append(lwHandler.getDecodedInstrn());	
					nextAddr=currentAddr+4;
					break;

					
			case ADD:
					ADDHandler addHandler=new ADDHandler(binaryInstruction, startAddress, cycleno);
					rdReg=addHandler.getRDNum();
					rsReg=addHandler.getRSNum();
					rtReg=addHandler.getRTNum();
					rsRegVal=r1.getRegVal(rsReg);
					rtRegVal=r1.getRegVal(rtReg);
					rdRegVal=rsRegVal+rtRegVal;
					r1.updateReg(rdReg, rdRegVal);
					decodedInstrn.append(addHandler.getDecodedInstrn()); 				 
					nextAddr=currentAddr+4;			 
					break;
			case SUB:
					SUBHandler subHandler=new SUBHandler(binaryInstruction, startAddress, cycleno);
					rdReg=subHandler.getRDNum();
					rsReg=subHandler.getRSNum();
					rtReg=subHandler.getRTNum();
					rsRegVal=r1.getRegVal(rsReg);
					rtRegVal=r1.getRegVal(rtReg);
					rdRegVal=rsRegVal-rtRegVal;
					r1.updateReg(rdReg, rdRegVal);
					decodedInstrn.append(subHandler.getDecodedInstrn()); 	
					nextAddr=currentAddr+4;
					break;
			case MUL:
					MULHandler mulHandler=new MULHandler(binaryInstruction, startAddress, cycleno);
					rdReg=mulHandler.getRDNum();
					rsReg=mulHandler.getRSNum();
					rtReg=mulHandler.getRTNum();
					rsRegVal=r1.getRegVal(rsReg);
					rtRegVal=r1.getRegVal(rtReg);
					rdRegVal=rsRegVal*rtRegVal;
					r1.updateReg(rdReg, rdRegVal);
					decodedInstrn.append(mulHandler.getDecodedInstrn());
					nextAddr=currentAddr+4;
					break; 	
			case AND:
					ANDHandler andHandler=new ANDHandler(binaryInstruction, startAddress, cycleno);
					rdReg=andHandler.getRDNum();
					rsReg=andHandler.getRSNum();
					rtReg=andHandler.getRTNum();
					rsRegVal=r1.getRegVal(rsReg);
					rtRegVal=r1.getRegVal(rtReg);
					rdRegVal=rsRegVal & rtRegVal;
					r1.updateReg(rdReg, rdRegVal);
					decodedInstrn.append(andHandler.getDecodedInstrn());
					nextAddr=currentAddr+4;
					break;
			case OR:
					ORHandler orHandler=new ORHandler(binaryInstruction, startAddress, cycleno);
					rdReg=orHandler.getRDNum();
					rsReg=orHandler.getRSNum();
					rtReg=orHandler.getRTNum();
					rsRegVal=r1.getRegVal(rsReg);
					rtRegVal=r1.getRegVal(rtReg);
					rdRegVal=rsRegVal | rtRegVal;
					r1.updateReg(rdReg, rdRegVal);
					decodedInstrn.append(orHandler.getDecodedInstrn());
					nextAddr=currentAddr+4;
					break;
			case XOR:
					XORHandler xorHandler=new XORHandler(binaryInstruction, startAddress, cycleno);
					rdReg=xorHandler.getRDNum();
					rsReg=xorHandler.getRSNum();
					rtReg=xorHandler.getRTNum();
					rsRegVal=r1.getRegVal(rsReg);
					rtRegVal=r1.getRegVal(rtReg);
					rdRegVal=rsRegVal ^ rtRegVal;
					r1.updateReg(rdReg, rdRegVal);
					decodedInstrn.append(xorHandler.getDecodedInstrn());
					nextAddr=currentAddr+4;
					break;
			case NOR:
					NORHandler norHandler=new NORHandler(binaryInstruction, startAddress, cycleno);
					rdReg=norHandler.getRDNum();
					rsReg=norHandler.getRSNum();
					rtReg=norHandler.getRTNum();
					rsRegVal=r1.getRegVal(rsReg);
					rtRegVal=r1.getRegVal(rtReg);
					int templ=rsRegVal | rtRegVal;
					rdRegVal=swapBits(templ);
					r1.updateReg(rdReg, rdRegVal);
					decodedInstrn.append(norHandler.getDecodedInstrn()); 	
					nextAddr=currentAddr+4;
	 				break;
 			
			case ADDI:
					ADDIHandler addiHandler=new ADDIHandler(binaryInstruction, startAddress, cycleno);
					rsReg=addiHandler.getRSValue();
					rtReg=addiHandler.getRTValue();
					rsRegVal=r1.getRegVal(rsReg);
					immdetiateVal=addiHandler.getImmediateVal();
					rtRegVal=rsRegVal+immdetiateVal;
					r1.updateReg(rtReg, rtRegVal);
					decodedInstrn.append(addiHandler.getDecodedInstrn());		
					nextAddr=currentAddr+4;
	 				break;
			case ANDI:
					ANDIHandler andiHandler=new ANDIHandler(binaryInstruction, startAddress, cycleno);
					rsReg=andiHandler.getRSValue();
					rtReg=andiHandler.getRTValue();
					rsRegVal=r1.getRegVal(rsReg);
					immdetiateVal=andiHandler.getImmediateVal();
					rtRegVal=rsRegVal & immdetiateVal;
					r1.updateReg(rtReg, rtRegVal);
					decodedInstrn.append(andiHandler.getDecodedInstrn());
					nextAddr=currentAddr+4;
	 				break;
			case ORI:
					ORIHandler oriHandler=new ORIHandler(binaryInstruction, startAddress, cycleno);
					rsReg=oriHandler.getRSValue();
					rtReg=oriHandler.getRTValue();
					rsRegVal=r1.getRegVal(rsReg);
					immdetiateVal=oriHandler.getImmediateVal();
					rtRegVal=rsRegVal | immdetiateVal;
					r1.updateReg(rtReg, rtRegVal);
					decodedInstrn.append(oriHandler.getDecodedInstrn());	
					nextAddr=currentAddr+4;
 				break;
			case XORI:
					XORIHandler xoriHandler=new XORIHandler(binaryInstruction, startAddress, cycleno);
					rsReg=xoriHandler.getRSValue();
					rtReg=xoriHandler.getRTValue();
					rsRegVal=r1.getRegVal(rsReg);
					immdetiateVal=xoriHandler.getImmediateVal();
					rtRegVal=rsRegVal ^ immdetiateVal;
					r1.updateReg(rtReg, rtRegVal);
					decodedInstrn.append(xoriHandler.getDecodedInstrn());		
					nextAddr=currentAddr+4;
	 				break;

			default:
				break;
			}
		 
			decodedInstrn.append(r1.print()+"\n"+d2.Print()+"\n");				 
			return decodedInstrn.toString();		
			 
		}

		public static int swapBits(int bit)
		{
			String value=Integer.toBinaryString(bit);
			value.replace('0', 'x');
			value.replace('1', '0');
			value.replace('x', '1');			
			int rdVal=Integer.parseInt(value, 2);
			return rdVal;
		}
}


class SUBHandler {
    private String binaryInstrn;
    private String decodedInstrn;

    private int address;
    
    private String instrnName;
    private String rs;
    private String rt;
    private String rd;
    private String cycle;

    private int rsNum;
    private int rtNum;
    private int rdNum;
    private int cycleCount;

    public SUBHandler(String binIns, int addr) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            address = addr;
            rsNum = -1;
            rtNum = -1;
            rdNum = -1;

            instrnName = "SUB";
            rs = "R";
            rt = "R";
            rd = "R";

            decodedInstrn = "";

            constructDecodedInstrn();
    }
    public SUBHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        rdNum = -1;
        cycle="Cycle:";
        cycleCount=cycle_value;

        instrnName = "SUB";
        rs = "R";
        rt = "R";
        rd = "R";

        decodedInstrn = "";

        constructDecodedInstrn_simulator();
}

    /**
     * Identify the individual fields and construct the instruction in assembly
     * format
     */
    public void constructDecodedInstrn() {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;                
            decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";
            
    }
    public void constructDecodedInstrn_simulator() {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;                
            decodedInstrn = cycle+cycleCount + "\t" + address + "\t" + decodedInstrn;
            
    }
    public String getDecodedInstrn() {
            return decodedInstrn;
    }
    public int getRSNum()
    {
    	return rsNum;
    }
    public int getRTNum()
    {
    	return rtNum;
    }
    public int getRDNum()
    {
    	return rdNum;
    }
     
}


class SWHandler {

    private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    private String instrnName;
    private String cycle;

    private String rt;
    private int rtNum;
    private int cycleCount;

    private String base;
    private int baseNum;

    private int offsetVal;

    public SWHandler(String binIns, int addr) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            instrnName = "SW";
            address = addr;

            rtNum = -1;
            baseNum = -1;

            rt = "R";
            base = "R";

            constructDecodedInstrn();
    }
    public SWHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        instrnName = "SW";
        cycle="Cycle:";
        address = addr;

        rtNum = -1;
        baseNum = -1;

        rt = "R";
        base = "R";
        cycleCount=cycle_value;

        constructDecodedInstrn_simulator();
}

    public void constructDecodedInstrn() {
            baseNum = Integer.parseInt(binaryInstrn.substring(6, 11), 2);
            rtNum = Integer.parseInt(binaryInstrn.substring(11, 16), 2);
            offsetVal = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16,
                            32));

            base = base + baseNum;
            rt = rt + rtNum;

            decodedInstrn = instrnName + " " + rt + ", " + offsetVal + "(" + base
                            + ")";
           
            decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";
            
    }
    
    public void constructDecodedInstrn_simulator() {
        baseNum = Integer.parseInt(binaryInstrn.substring(6, 11), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(11, 16), 2);
        offsetVal = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16,
                        32));

        base = base + baseNum;
        rt = rt + rtNum;

        decodedInstrn = instrnName + " " + rt + ", " + offsetVal + "(" + base
                        + ")";
       
        decodedInstrn = cycle+cycleCount + "\t" + address + "\t" + decodedInstrn;
        
}

    public String getDecodedInstrn() {
            return decodedInstrn;
    }
    public int getBasereg()
    {
    	return baseNum;
    }
    public int getRTreg()
    {
    	return rtNum;
    }
    public int getOffsetVal()
    {
    	return offsetVal;
    }
}


class XORHandler {
    private String binaryInstrn;
    private String decodedInstrn;

    private int address;
    
    private String instrnName;
    private String rs;
    private String rt;
    private String rd;
    private String cycle;

    private int rsNum;
    private int rtNum;
    private int rdNum;
    private int cycleCount;

    public XORHandler(String binIns, int addr) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            address = addr;
            rsNum = -1;
            rtNum = -1;
            rdNum = -1;

            instrnName = "XOR";
            rs = "R";
            rt = "R";
            rd = "R";

            decodedInstrn = "";

            constructDecodedInstrn();
    }

    public XORHandler(String binIns, int addr,int cylcle_value) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            address = addr;
            rsNum = -1;
            rtNum = -1;
            rdNum = -1;
            cycle="Cycle:";

            instrnName = "XOR";
            rs = "R";
            rt = "R";
            rd = "R";
            cycleCount=cylcle_value;

            decodedInstrn = "";

            constructDecodedInstrn_simulator();
    }

    /**
     * Identify the individual fields and construct the instruction in assembly
     * format
     */
    public void constructDecodedInstrn() {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);


            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;                
            decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";
            
    }
    public void constructDecodedInstrn_simulator() {
    	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);


            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;                
            decodedInstrn = cycle+cycleCount + "\t" + address + "\t" + decodedInstrn;
            
    }

    public String getDecodedInstrn() {
            return decodedInstrn;
    }
    public int getRSNum()
    {
    	return rsNum;
    }
    public int getRTNum()
    {
    	return rtNum;
    }
    public int getRDNum()
    {
    	return rdNum;
    }
}


class XORIHandler {
	 private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    private String instrnName;
    private String rs;      
    private String rt;
    private String immStr;
    private String cycle;
    
    private int rsNum;
    private int rtNum;
    private int immValue;
    private int cycleCount;
    
    public XORIHandler(String binIns, int addr) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        immValue = -1;
        
        instrnName = "XORI";
        rs = "R";
        rt = "R";
        immStr = "#";
        
        decodedInstrn = "";
        
        constructDecodedInstrn();
}
    public XORIHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        immValue = -1;
        cycle="Cycle:";
        
        instrnName = "XORI";
        rs = "R";
        rt = "R";
        immStr = "#";
        cycleCount=cycle_value;
        
        decodedInstrn = "";
        
        constructDecodedInstrn_simulator();
}
/**
 * Identify the individual fields and construct the instruction in assembly format 
 */
public void constructDecodedInstrn()
{
	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
    rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        
    immValue = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16, 32));
        
        rs = rs + rsNum;
        rt = rt +  rtNum;
        immStr = immStr + immValue;
        
        decodedInstrn = instrnName + " " + rt + ", " + rs + ", " + immStr;                 
        decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";
        
}
public void constructDecodedInstrn_simulator()
{
	rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
    rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        
    immValue = SimUtils.getIntFromBinaryString(binaryInstrn.substring(16, 32));
        
        rs = rs + rsNum;
        rt = rt +  rtNum;
        immStr = immStr + immValue;
        
        decodedInstrn = instrnName + " " + rt + ", " + rs + ", " + immStr;                 
        decodedInstrn = cycle+cycleCount + "\t" + address + "\t" + decodedInstrn;
        
}
public String getDecodedInstrn()
{
        return decodedInstrn;
}
public int getRSValue()
{
	return rsNum;
}
public int getRTValue()
{
	return rtNum;
}
public int getImmediateVal()
{
	return immValue;
}

}

 
class JHandler {

    private String binaryInstrn;
    private String decodedInstrn;
    private int address;
    private String instrIndexStr;
    private int instrIndexVal;
    private String cycle;
    private int cycleCount;        
    private String instrnName;
    
    public JHandler(String binIns, int addr)
    {
            address = addr;
            binaryInstrn = binIns;
            decodedInstrn = "";
            
            instrnName = "J";
            
            instrIndexVal = -1;
            instrIndexStr = "#";
            
            constructDecodedInstrn();
    }
    public JHandler(String binIns, int addr,int cycle_value)
    {
            address = addr;
            binaryInstrn = binIns;
            cycle="Cycle:";
            cycleCount=cycle_value;
            decodedInstrn = "";
            
            instrnName = "J";
            
            instrIndexVal = -1;
            instrIndexStr = "#";
            
            constructDecodedInstrn_simulator();
    }
    
    /**
     * Identify the individual fields and construct the instruction in assembly format 
     */
    public void constructDecodedInstrn()
    {
            String insIndexBin = binaryInstrn.substring(6, 32) + "00";
            instrIndexVal = Integer.parseInt(insIndexBin, 2);
            instrIndexStr = instrIndexStr + instrIndexVal; 
            decodedInstrn = instrnName + " " + instrIndexStr;              
            
            decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";

    }
    public void constructDecodedInstrn_simulator()
    {
            String insIndexBin = binaryInstrn.substring(6, 32) + "00";
            instrIndexVal = Integer.parseInt(insIndexBin, 2);
            instrIndexStr = instrIndexStr + instrIndexVal; 
            decodedInstrn = instrnName + " " + instrIndexStr;              
            
            decodedInstrn = cycle+cycleCount + "\t" + address + "\t" + decodedInstrn;

    }
    public String getDecodedInstrn()
    {
            return decodedInstrn;
    }
    public int getNextjumLevel()
    {
    	return instrIndexVal;
    }

}

class ADDHandler {

    private String binaryInstrn;
    private String decodedInstrn;

    private int address;
    
    private String instrnName;
    private String rs;
    private String rt;
    private String rd;
    private String cycle;

    private int rsNum;
    private int rtNum;
    private int rdNum;
    private int cycleCount;

    public ADDHandler(String binIns, int addr) {
            binaryInstrn = binIns;
            decodedInstrn = "";
            address = addr;
            rsNum = -1;
            rtNum = -1;
            rdNum = -1;

            instrnName = "ADD";
            rs = "R";
            rt = "R";
            rd = "R";
            
            decodedInstrn = "";

            constructDecodedInstrn();
    }
    public ADDHandler(String binIns, int addr,int cycle_value) {
        binaryInstrn = binIns;
        decodedInstrn = "";
        address = addr;
        rsNum = -1;
        rtNum = -1;
        rdNum = -1;
        cycleCount=cycle_value;

        instrnName = "ADD";
        rs = "R";
        rt = "R";
        rd = "R";
        cycle="Cycle:";

        decodedInstrn = "";

        constructDecodedInstrn_simulator();
}

    /**
     * Identify the individual fields and construct the instruction in assembly
     * format
     */
    public void constructDecodedInstrn() {
            rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
            rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
            rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

            rs = rs + rsNum;
            rt = rt + rtNum;
            rd = rd + rdNum;

            decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;            
            decodedInstrn = binaryInstrn + "\t" + address + "\t" + decodedInstrn+"\n";                 
    }

    public void constructDecodedInstrn_simulator() {
        rsNum = Integer.parseInt(binaryInstrn.substring(3, 8), 2);
        rtNum = Integer.parseInt(binaryInstrn.substring(8, 13), 2);
        rdNum = Integer.parseInt(binaryInstrn.substring(16, 21), 2);

        rs = rs + rsNum;
        rt = rt + rtNum;
        rd = rd + rdNum;           
         
        decodedInstrn = instrnName + " " + rd + ", " + rs + ", " + rt;            
        decodedInstrn = cycle+cycleCount+ "\t" + address + "\t" + decodedInstrn+"\n";                 
}
    public String getDecodedInstrn() {
            return decodedInstrn;
    }
    public int getRSNum()
    {
    	return rsNum;
    }
    public int getRTNum()
    {
    	return rtNum;
    }
    public int getRDNum()
    {
    	return rdNum;
    }
     
}

enum InstructionType
{
J,BEQ,BGTZ, BREAK, SW, LW,
ADD, SUB, MUL, AND, OR, XOR, NOR,
ADDI, ANDI, ORI, XORI,
NOT_SUPPORTED;
}

