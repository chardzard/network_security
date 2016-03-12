package q2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.net.util.SubnetUtils;

/**
 * 
 * @author Richard
 *
 */
public class Main {

	static ACL list = new ACL();
	static Scanner kb = new Scanner(System.in);
	static ArrayList<Packet> packets = new ArrayList<Packet>();

	public static void main(String[] args) {
		readACLFile();
		readPackets();
		kb.close();
		testPackets();
	}

	public static void readACLFile() {		
		System.out.println("Please enter the name of the ACL file: ");
		String fileName = kb.nextLine();		
		try {			
			Scanner inFile = new Scanner(new File(fileName));
			while(inFile.hasNextLine()) {
				String[] line = inFile.nextLine().split(" ");
				boolean allow = true;
				int lineNum = Integer.parseInt(line[1]);
				if(line[0].equals("access-list")) {
					if(line[2].equals("deny")) {
						allow = false;
					}
					if(lineNum < 100) {						
						ListEntry lineInfo = new ListEntry(lineNum, allow, line[3], line[4]);
						list.getRules().add(lineInfo);
					}
					else {
						ListEntry lineInfo = new ListEntry
								(lineNum, allow, line[4], line[5], line[6], line[7]);
						list.getRules().add(lineInfo);
					}
				}
			}			
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}

	public static void readPackets() {
		System.out.println("Please enter the name of the packet file: ");
		String fileName = kb.nextLine();
		try {
			Scanner inFile = new Scanner(new File(fileName));
			while (inFile.hasNextLine()) {
				String[] line = inFile.nextLine().split(" ");
				Packet p = new Packet(line[0], line[1]);
				packets.add(p);
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void testPackets() {
		try {
			FileWriter outFile = new FileWriter(new File("out.txt"));
			for(Packet p: packets) {
				boolean found = false;
				for(int i = 0; i < list.getRules().size() && !found; i++) {
					ListEntry l = list.getRules().get(i);
					if(l.isExtended()) {
						if(isAddressInNetwork(p.getSourceIP(), l.getSourceIP(), l.getSourceMask()) && 
								isAddressInNetwork(p.getDestIP(), l.getDestIP(), l.getDestMask())) {
							found = true;
							outFile.append(p.toString() + " " + l.isAllow() + "\n");
						}
					}
					else {
						if(isAddressInNetwork(p.getSourceIP(), l.getSourceIP(), l.getSourceMask())) {
							found = true;
							outFile.append(p.toString() + " " + l.isAllow() + "\n");
						}
					}
				}
				if(!found) {
					outFile.append(p.toString() + " " + !found + "\n");
				}
			}
			outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isAddressInNetwork(String address, String network, String mask) {
		SubnetUtils rule = new SubnetUtils(network, mask);
		return rule.getInfo().isInRange(address);
	}
}
