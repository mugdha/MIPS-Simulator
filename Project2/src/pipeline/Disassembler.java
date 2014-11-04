package pipeline;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class Disassembler {

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
    public InstructionInfo f1;
    
    public static LinkedHashMap<Long,InstructionInfo> InstructionMap = new LinkedHashMap<Long,InstructionInfo>();
    
    public Disassembler(String input)  throws FileNotFoundException, IOException {
           f1=new InstructionInfo();
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
                                   // String decodedInstrn = SimUtils.getDecodedInstrn(binaryInstrn[i], type, address,1);
                                    String decodedInstrn = SimUtils.getDecodedInstrn(binaryInstrn[i], type, address); //removing cycle time being
					f1.setAddress(address);
					f1.setInstruction(decodedInstrn);
					f1.setStage("ISSUE");

					InstructionMap.put((long) address, f1);
                                    
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
    
    
    
    public static String addLeadingZeros(String s) {
		String temp = "";
		int length = 32 - s.length();
		for (int i = 0; i < length; i++) {
			temp += "0";
		}
		temp += s;
		return temp;
	}
}
