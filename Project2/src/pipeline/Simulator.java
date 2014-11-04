package pipeline;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.TreeMap;

public class Simulator implements Serializable{
	 
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
           	//String decodedInstrn= SimUtils.PrintCycle(instrn, type, address,cycleCounter);
           	// outputWriter.write(decodedInstrn);
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
           
            //address=SimUtils.nextAddr; nextAddr is a value in simUtil
            
            }while(addrInstn.containsKey(address));            	 
           	 
            }
			  outputWriter.close();
		 }
		 catch (IOException e) {
            e.printStackTrace();
    }
	}  
}

