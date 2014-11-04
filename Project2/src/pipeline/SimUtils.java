package pipeline;

import java.io.Serializable;
import java.util.Set;

public class SimUtils implements Serializable{

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
	public static String getDecodedInstrn(String binaryInstrn,
			InstructionType type, int address) {
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
			BGTZHandler bgtzHandle = new BGTZHandler(binaryInstrn, address);
			decodedInstrn = bgtzHandle.getDecodedInstrn();
			break;
		case BREAK:
			BREAKHandler breakHandler = new BREAKHandler(binaryInstrn, address);
			decodedInstrn = breakHandler.getDecodedInstrn();
			break;
		case LW:
			LWHandler lwHandler = new LWHandler(binaryInstrn, address);
			decodedInstrn = lwHandler.getDecodedInstrn();
			break;
		case SW:
			SWHandler swHandler = new SWHandler(binaryInstrn, address);
			decodedInstrn = swHandler.getDecodedInstrn();
			break;

		// category 2 instruction
		case ADD:
			ADDHandler addHandler = new ADDHandler(binaryInstrn, address);
			decodedInstrn = addHandler.getDecodedInstrn();
			break;
		case SUB:
			SUBHandler subHandler = new SUBHandler(binaryInstrn, address);
			decodedInstrn = subHandler.getDecodedInstrn();
			break;
		case MUL:
			MULHandler mulHandler = new MULHandler(binaryInstrn, address);
			decodedInstrn = mulHandler.getDecodedInstrn();
			break;
		case AND:
			ANDHandler andHandler = new ANDHandler(binaryInstrn, address);
			decodedInstrn = andHandler.getDecodedInstrn();
			break;
		case OR:
			ORHandler orHandler = new ORHandler(binaryInstrn, address);
			decodedInstrn = orHandler.getDecodedInstrn();
			break;
		case XOR:
			XORHandler xorHandler = new XORHandler(binaryInstrn, address);
			decodedInstrn = xorHandler.getDecodedInstrn();
			break;
		case NOR:
			NORHandler norHandler = new NORHandler(binaryInstrn, address);
			decodedInstrn = norHandler.getDecodedInstrn();
			break;
		// category 3 instruction
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

	/*
	 * public static String PrintCycle(String binaryInstruction, InstructionType
	 * type, int startAddress, int cycleno) {
	 * 
	 * StringBuffer decodedInstrn =new
	 * StringBuffer("--------------------"+"\n"); int rdReg=0; int rsReg=0; int
	 * rtReg=0; int rsRegVal=0; int rtRegVal=0; int rdRegVal=0; int
	 * immdetiateVal=0; int offset=0; int addr=0; currentAddr=startAddress;
	 * 
	 * switch (type) { case J: JHandler jHandler=new JHandler(binaryInstruction,
	 * startAddress,cycleno); nextAddr=jHandler.getNextjumLevel();
	 * decodedInstrn.append(jHandler.getDecodedInstrn()); break; case BEQ:
	 * BEQHandler beqHandler=new BEQHandler(binaryInstruction, startAddress,
	 * cycleno); rsReg=beqHandler.getRS(); rsRegVal=r1.getRegVal(rsReg);
	 * rtReg=beqHandler.getRT(); rtRegVal=r1.getRegVal(rtReg);
	 * offset=beqHandler.getOffsetVal(); if(rsRegVal==rtRegVal) { nextAddr=
	 * currentAddr+offset+4; } else { nextAddr=currentAddr+4; }
	 * decodedInstrn.append(beqHandler.getDecodedInstrn()); break; case BGTZ:
	 * BGTZHandler bgtzHandler=new BGTZHandler(binaryInstruction, startAddress,
	 * cycleno); rsReg=bgtzHandler.getRS(); rsRegVal=r1.getRegVal(rsReg);
	 * offset=bgtzHandler.getOffsetVal(); if(rsRegVal>0) {
	 * nextAddr=currentAddr+offset+4; } else { nextAddr=currentAddr+4; }
	 * decodedInstrn.append(bgtzHandler.getDecodedInstrn()); break; case BREAK:
	 * BREAKHandler breakHandler=new BREAKHandler(binaryInstruction,
	 * startAddress, cycleno);
	 * decodedInstrn.append(breakHandler.getDecodedInstrn()); nextAddr=000;//Any
	 * number for which loop will break and end
	 * 
	 * break; case SW: SWHandler swHandler=new SWHandler(binaryInstruction,
	 * startAddress,cycleno); rsReg=swHandler.getBasereg(); //base register
	 * rsRegVal=r1.getRegVal(rsReg); rtReg=swHandler.getRTreg();
	 * rtRegVal=r1.getRegVal(rtReg); offset=swHandler.getOffsetVal();
	 * addr=offset+rsRegVal; d2.memoryDataMap.put(addr, rtRegVal);
	 * decodedInstrn.append(swHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break; case LW: LWHandler lwHandler=new
	 * LWHandler(binaryInstruction, startAddress,cycleno);
	 * rsReg=lwHandler.getBasereg(); //base register
	 * rsRegVal=r1.getRegVal(rsReg); rtReg=lwHandler.getRTreg();
	 * offset=lwHandler.getOffsetVal(); addr=offset+rsRegVal;
	 * rtRegVal=d2.memoryDataMap.get(addr); r1.updateReg(rtReg, rtRegVal);
	 * decodedInstrn.append(lwHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break;
	 * 
	 * 
	 * case ADD: ADDHandler addHandler=new ADDHandler(binaryInstruction,
	 * startAddress, cycleno); rdReg=addHandler.getRDNum();
	 * rsReg=addHandler.getRSNum(); rtReg=addHandler.getRTNum();
	 * rsRegVal=r1.getRegVal(rsReg); rtRegVal=r1.getRegVal(rtReg);
	 * rdRegVal=rsRegVal+rtRegVal; r1.updateReg(rdReg, rdRegVal);
	 * decodedInstrn.append(addHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break; case SUB: SUBHandler subHandler=new
	 * SUBHandler(binaryInstruction, startAddress, cycleno);
	 * rdReg=subHandler.getRDNum(); rsReg=subHandler.getRSNum();
	 * rtReg=subHandler.getRTNum(); rsRegVal=r1.getRegVal(rsReg);
	 * rtRegVal=r1.getRegVal(rtReg); rdRegVal=rsRegVal-rtRegVal;
	 * r1.updateReg(rdReg, rdRegVal);
	 * decodedInstrn.append(subHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break; case MUL: MULHandler mulHandler=new
	 * MULHandler(binaryInstruction, startAddress, cycleno);
	 * rdReg=mulHandler.getRDNum(); rsReg=mulHandler.getRSNum();
	 * rtReg=mulHandler.getRTNum(); rsRegVal=r1.getRegVal(rsReg);
	 * rtRegVal=r1.getRegVal(rtReg); rdRegVal=rsRegVal*rtRegVal;
	 * r1.updateReg(rdReg, rdRegVal);
	 * decodedInstrn.append(mulHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break; case AND: ANDHandler andHandler=new
	 * ANDHandler(binaryInstruction, startAddress, cycleno);
	 * rdReg=andHandler.getRDNum(); rsReg=andHandler.getRSNum();
	 * rtReg=andHandler.getRTNum(); rsRegVal=r1.getRegVal(rsReg);
	 * rtRegVal=r1.getRegVal(rtReg); rdRegVal=rsRegVal & rtRegVal;
	 * r1.updateReg(rdReg, rdRegVal);
	 * decodedInstrn.append(andHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break; case OR: ORHandler orHandler=new
	 * ORHandler(binaryInstruction, startAddress, cycleno);
	 * rdReg=orHandler.getRDNum(); rsReg=orHandler.getRSNum();
	 * rtReg=orHandler.getRTNum(); rsRegVal=r1.getRegVal(rsReg);
	 * rtRegVal=r1.getRegVal(rtReg); rdRegVal=rsRegVal | rtRegVal;
	 * r1.updateReg(rdReg, rdRegVal);
	 * decodedInstrn.append(orHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break; case XOR: XORHandler xorHandler=new
	 * XORHandler(binaryInstruction, startAddress, cycleno);
	 * rdReg=xorHandler.getRDNum(); rsReg=xorHandler.getRSNum();
	 * rtReg=xorHandler.getRTNum(); rsRegVal=r1.getRegVal(rsReg);
	 * rtRegVal=r1.getRegVal(rtReg); rdRegVal=rsRegVal ^ rtRegVal;
	 * r1.updateReg(rdReg, rdRegVal);
	 * decodedInstrn.append(xorHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break; case NOR: NORHandler norHandler=new
	 * NORHandler(binaryInstruction, startAddress, cycleno);
	 * rdReg=norHandler.getRDNum(); rsReg=norHandler.getRSNum();
	 * rtReg=norHandler.getRTNum(); rsRegVal=r1.getRegVal(rsReg);
	 * rtRegVal=r1.getRegVal(rtReg); int templ=rsRegVal | rtRegVal;
	 * rdRegVal=swapBits(templ); r1.updateReg(rdReg, rdRegVal);
	 * decodedInstrn.append(norHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break;
	 * 
	 * case ADDI: ADDIHandler addiHandler=new ADDIHandler(binaryInstruction,
	 * startAddress, cycleno); rsReg=addiHandler.getRSValue();
	 * rtReg=addiHandler.getRTValue(); rsRegVal=r1.getRegVal(rsReg);
	 * immdetiateVal=addiHandler.getImmediateVal();
	 * rtRegVal=rsRegVal+immdetiateVal; r1.updateReg(rtReg, rtRegVal);
	 * decodedInstrn.append(addiHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break; case ANDI: ANDIHandler andiHandler=new
	 * ANDIHandler(binaryInstruction, startAddress, cycleno);
	 * rsReg=andiHandler.getRSValue(); rtReg=andiHandler.getRTValue();
	 * rsRegVal=r1.getRegVal(rsReg);
	 * immdetiateVal=andiHandler.getImmediateVal(); rtRegVal=rsRegVal &
	 * immdetiateVal; r1.updateReg(rtReg, rtRegVal);
	 * decodedInstrn.append(andiHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break; case ORI: ORIHandler oriHandler=new
	 * ORIHandler(binaryInstruction, startAddress, cycleno);
	 * rsReg=oriHandler.getRSValue(); rtReg=oriHandler.getRTValue();
	 * rsRegVal=r1.getRegVal(rsReg); immdetiateVal=oriHandler.getImmediateVal();
	 * rtRegVal=rsRegVal | immdetiateVal; r1.updateReg(rtReg, rtRegVal);
	 * decodedInstrn.append(oriHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break; case XORI: XORIHandler xoriHandler=new
	 * XORIHandler(binaryInstruction, startAddress, cycleno);
	 * rsReg=xoriHandler.getRSValue(); rtReg=xoriHandler.getRTValue();
	 * rsRegVal=r1.getRegVal(rsReg);
	 * immdetiateVal=xoriHandler.getImmediateVal(); rtRegVal=rsRegVal ^
	 * immdetiateVal; r1.updateReg(rtReg, rtRegVal);
	 * decodedInstrn.append(xoriHandler.getDecodedInstrn());
	 * nextAddr=currentAddr+4; break;
	 * 
	 * default: break; }
	 * 
	 * decodedInstrn.append(r1.print()+"\n"+d2.Print()+"\n"); return
	 * decodedInstrn.toString();
	 * 
	 * }
	 */
	public static int swapBits(int bit) {
		String value = Integer.toBinaryString(bit);
		value.replace('0', 'x');
		value.replace('1', '0');
		value.replace('x', '1');
		int rdVal = Integer.parseInt(value, 2);
		return rdVal;
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

	public static int getImmValue(String str) {
		return Integer.parseInt(str.replace("#", ""));
	}

	public static IQEntry getIQEntry(String binaryInstrn, int address) {

		InstructionType type = SimUtils.getInstrnType(binaryInstrn);
		IQEntry entry = null;
		String instrn = "";
		InstructionCategory category = null;
		InstructionType instrnType = null;
		String rs = "";
		String rt = "";
		String rd = ""; // set rd as imm value for instrns with imm operand
		int rtNum;
		int rdNum;
		int rsNum;
		
		
		switch (type) {
		case ANDI:
			ANDIHandler andiHandler = new ANDIHandler(binaryInstrn, address);
			instrn = andiHandler.getInstrn();
			category = andiHandler.getCategory();
			instrnType = andiHandler.getType();
			rs = andiHandler.getRs();
			rt = andiHandler.getRt();
			rd = andiHandler.getImmStr(); // set rd as imm value for instrns
			// with imm operand
			
			rtNum=andiHandler.getRtNum();
			rsNum=andiHandler.getRsNum();
			rdNum=andiHandler.getImmValue();
			
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;

		case ORI:
			ORIHandler oriHandler = new ORIHandler(binaryInstrn, address);
			instrn = oriHandler.getInstrn();
			category = oriHandler.getCategory();
			instrnType = oriHandler.getType();
			rs = oriHandler.getRs();
			rt = oriHandler.getRt();
			rd = oriHandler.getImmStr(); // set rd as imm value for instrns
			// with imm operand
		 		
			
			rtNum=oriHandler.getRtNum();
			rsNum=oriHandler.getRsNum();
			rdNum=oriHandler.getImmValue();
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;

		case XORI:
			XORIHandler xoriHandler = new XORIHandler(binaryInstrn, address);
			instrn = xoriHandler.getInstrn();
			category = xoriHandler.getCategory();
			instrnType = xoriHandler.getType();
			rs = xoriHandler.getRs();
			rt = xoriHandler.getRt();
			rd = xoriHandler.getImmStr(); // set rd as imm value for instrns
			// with imm operand
			

			rtNum=xoriHandler.getRtNum();
			rsNum=xoriHandler.getRsNum();
			rdNum=xoriHandler.getImmValue();
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;

		case ADDI:
			ADDIHandler addiHandler = new ADDIHandler(binaryInstrn, address);
			instrn = addiHandler.getInstrn();
			category = addiHandler.getCategory();
			instrnType = addiHandler.getType();
			rs = addiHandler.getRs();
			rt = addiHandler.getRt();
			rd = addiHandler.getImmStr();
			// with imm operand
			
			rtNum=addiHandler.getRtNum();
			rsNum=addiHandler.getRsNum();
			rdNum=addiHandler.getImmValue();
			 // set rd as imm value for instrns
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;
		case SW:
			SWHandler swHandler = new SWHandler(binaryInstrn, address);
			instrn = swHandler.getInstrn();
			category = swHandler.getCategory();
			instrnType = swHandler.getType();
			rs = swHandler.getBase(); // rs = base in store
			rsNum=swHandler.getBaseNum();
			
			rd=String.valueOf(swHandler.getOffsetVal());
			
			rt = swHandler.getRt();
			rtNum=swHandler.getRtNum();			
			rdNum=swHandler.getOffsetVal();	 
		 
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;
		case LW:
			LWHandler lwHandler = new LWHandler(binaryInstrn, address);
			instrn = lwHandler.getInstrn();
			category = lwHandler.getCategory();
			instrnType = lwHandler.getType();
			rs = lwHandler.getBase(); // rs = base in store
			rsNum=lwHandler.getBaseNum();
			rd=String.valueOf(lwHandler.getOffsetVal());
			
			rt = lwHandler.getRt();
			rtNum=lwHandler.getRtNum();
			
			rdNum =lwHandler.getOffsetVal(); // set rd
			// as
			// offset
			// for
			// load
			// instrns
			entry = new IQEntry(instrn, category, instrnType, rs, rt,rd,rsNum,rtNum,rdNum);
			break;
		case BEQ:
			BEQHandler beqHandler = new BEQHandler(binaryInstrn, address);
			instrn = beqHandler.getInstrn();
			category = beqHandler.getCategory();
			instrnType = beqHandler.getType();
			rs = beqHandler.getRs();
			rsNum=beqHandler.getRsNum();
			
			rt = beqHandler.getRt();
			rtNum=beqHandler.getRtNum();
			rd=beqHandler.getOffsetStr();
			rdNum=beqHandler.getOffsetVal(); // set rd
			// as
			// offset
			// for
			// branch
			// instrns
			entry = new IQEntry(instrn, category, instrnType, rs, rt,rd,rsNum,rtNum,rdNum);
			break;
		case J:
			JHandler jHandler = new JHandler(binaryInstrn, address);
			instrn = jHandler.getInstrn();
			category = jHandler.getCategory();
			instrnType = jHandler.getType();
			rs = jHandler.getInstrIndexStr(); // rs = addr of instrn to jump to
			rsNum=jHandler.getNextjumLevel();
			entry = new IQEntry(instrn,category,instrnType,rs,null,null,rsNum,0,0);
			break;

		case BGTZ:
			BGTZHandler bgtzHandler = new BGTZHandler(binaryInstrn, address);
			instrn = bgtzHandler.getInstrn();
			category = bgtzHandler.getCategory();
			instrnType = bgtzHandler.getType();
			rs = bgtzHandler.getRs();
			rsNum=bgtzHandler.getRsNum();
			rd=bgtzHandler.getOffsetStr();
			rdNum=bgtzHandler.getOffsetVal();// Set rd as offset for branch instrns
			 
			entry = new IQEntry(instrn, category, instrnType, rs, null, rd,rsNum,0,rdNum);
			break;

		case ADD:
			ADDHandler addHandler = new ADDHandler(binaryInstrn, address);
			instrn = addHandler.getInstrn();
			category = addHandler.getCategory();
			instrnType = addHandler.getType();
			rs = addHandler.getRs();
			rt = addHandler.getRt();
			rd = addHandler.getRd();
			
			rsNum=addHandler.getRsNum();
			rtNum=addHandler.getRtNum();
			rdNum=addHandler.getRdNum();
			
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;

		case SUB:
			SUBHandler subHandler = new SUBHandler(binaryInstrn, address);
			instrn = subHandler.getInstrn();
			category = subHandler.getCategory();
			instrnType = subHandler.getType();
			rs = subHandler.getRs();
			rt = subHandler.getRt();
			rd = subHandler.getRd();
			rsNum=subHandler.getRsNum();
			rtNum=subHandler.getRtNum();
			rdNum=subHandler.getRdNum();
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;

		case MUL:
			MULHandler mulHandler = new MULHandler(binaryInstrn, address);
			instrn = mulHandler.getInstrn();
			category = mulHandler.getCategory();
			instrnType = mulHandler.getType();
			rs = mulHandler.getRs();
			rt = mulHandler.getRt();
			rd = mulHandler.getRd();
			rsNum=mulHandler.getRsNum();
			rtNum=mulHandler.getRtNum();
			rdNum=mulHandler.getRdNum();
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;

		case AND:
			ANDHandler andHandler = new ANDHandler(binaryInstrn, address);
			instrn = andHandler.getInstrn();
			category = andHandler.getCategory();
			instrnType = andHandler.getType();
			rs = andHandler.getRs();
			rt = andHandler.getRt();
			rd = andHandler.getRd();
			
			rsNum=andHandler.getRsNum();
			rtNum=andHandler.getRtNum();
			rdNum=andHandler.getRdNum();
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;
		case OR:
			ORHandler orHandler = new ORHandler(binaryInstrn, address);
			instrn = orHandler.getInstrn();
			category = orHandler.getCategory();
			instrnType = orHandler.getType();
			rs = orHandler.getRs();
			rt = orHandler.getRt();
			rd = orHandler.getRd();
			
			rsNum=orHandler.getRsNum();
			rtNum=orHandler.getRtNum();
			rdNum=orHandler.getRdNum();
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;
		case XOR:
			XORHandler xorHandler = new XORHandler(binaryInstrn, address);
			instrn = xorHandler.getInstrn();
			category = xorHandler.getCategory();
			instrnType = xorHandler.getType();
			rs = xorHandler.getRs();
			rt = xorHandler.getRt();
			rd = xorHandler.getRd();
			
			rsNum=xorHandler.getRsNum();
			rtNum=xorHandler.getRtNum();
			rdNum=xorHandler.getRdNum();
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;
		case NOR:
			NORHandler norHandler = new NORHandler(binaryInstrn, address);
			instrn = norHandler.getInstrn();
			category = norHandler.getCategory();
			instrnType = norHandler.getType();
			rs = norHandler.getRs();
			rt = norHandler.getRt();
			rd = norHandler.getRd();
			rsNum=norHandler.getRsNum();
			rtNum=norHandler.getRtNum();
			rdNum=norHandler.getRdNum();
			entry = new IQEntry(instrn, category, instrnType, rs, rt, rd,rsNum,rtNum,rdNum);
			break;

		case BREAK:
			BREAKHandler breakHandler = new BREAKHandler(binaryInstrn, address);
			instrn = breakHandler.getInstrn();
			category = breakHandler.getCategory();
			instrnType = breakHandler.getType();
			entry = new IQEntry(instrn, category, instrnType,null,null,null,0,0,0);
			break;

		default:
		}
		return entry;
	}

	public static int getOperationResult(InstructionType op, int src1, int src2) {
		int ans = 0;
		switch (op) {
		case ADDI:
			ans = src1 + src2;
			break;

		case ADD:
			ans = src1 + src2;
			break;
		case SUB:
			ans = src1 - src2;
			break;

		case MUL:
			ans = src1 * src2;
			break;

		case AND:
			ans = src1 & src2;
			break;
		case ANDI:
			ans = src1 & src2;
			break;
		case OR:
			ans = src1 | src2;
			break;

		case ORI:
			ans = src1 | src2;
			break;

		case XOR:
			ans = src1 ^ src2;
			break;

		case XORI:
			ans = src1 ^ src2;
			break;
		case NOR:
			ans = ~(src1 | src2);
			break;
		}
		return ans;
	}

}
