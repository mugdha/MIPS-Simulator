package pipeline;
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
               // new Disassembler(inputFile);  
                // new Simulator (inputFile);
                 Scheduler.initSimTrial(inputFile);
                 
                 
         } catch (Exception ex) {
        	 System.out.println("Unknown Exception occured. " + ex.getMessage());
                 System.exit(0);
         }

 }
}	 
	 
	  
