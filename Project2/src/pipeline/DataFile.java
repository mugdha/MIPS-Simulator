package pipeline;

import java.util.Map;
import java.util.TreeMap;


public class DataFile {


	//<address, data value>
	TreeMap<Integer, Integer> memoryDataMap = new TreeMap<Integer, Integer>();	
	
	public DataFile()
	{
		
	}
	public DataFile(TreeMap<Integer, String> addrDataMap)
	{
		for (Map.Entry<Integer, String> entry: addrDataMap.entrySet())
		{
			     int addr = entry.getKey();
			     String dataStr = entry.getValue();
			     int dataVal = SimUtils.getDataFromBinary(dataStr);
			     memoryDataMap.put(addr, dataVal);
		}
	}
	public DataFile clone()
	{
		DataFile DScopy = new DataFile();
		DScopy.memoryDataMap = (TreeMap<Integer, Integer>) this.memoryDataMap.clone();
		return DScopy;
	}
	
	 
	public void setValueAt(int address, int value)
	{
		memoryDataMap.put(address, value);
	}
	public int getValAt(int address)
	{
		int value=memoryDataMap.get(address);
		return value;
	}
	 
	public boolean containsKey(int address) 
	{
		boolean val=memoryDataMap.containsKey(address);
		return val;
	}
	
	public String print()
	{
		Integer firstAddress=memoryDataMap.firstKey();	 
		int size=memoryDataMap.size();
		int loopCount=(int) Math.ceil(size*1.0/8);
		int increment=32;		 
		StringBuffer str=new StringBuffer("\n"+"Data"+"\n");
		
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
