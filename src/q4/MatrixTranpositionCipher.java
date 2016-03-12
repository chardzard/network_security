package q4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MatrixTranpositionCipher {
	
	public static void main(String[] args) {
		ArrayList<Integer> key = readKeyFile();
		String text = readTextFile("plainMatrix.txt");
		String encrypted = encryptText(text, key);
		try {
			FileWriter outFile = new FileWriter(new File("outMatrix.txt"));
			outFile.append(encrypted);
			outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String readEncrypted = readTextFile("outMatrix.txt");
		String decrypted = decryptText(readEncrypted, key);
		try {
			FileWriter outFile = new FileWriter(new File("decryptMatrix.txt"));
			outFile.append(decrypted);
			outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String readTextFile(String fileName) {
		String toReturn = "";
		
		try {
			Scanner inFile = new Scanner(new File(fileName));
			while(inFile.hasNextLine()) {
				toReturn += inFile.nextLine();
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return toReturn;
	}

	public static ArrayList<Integer> readKeyFile() {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		
		try {
			Scanner inFile = new Scanner(new File("keyMatrix.txt"));
			while(inFile.hasNextLine()) {
				String line = inFile.nextLine();
				String[] split = line.split("\t");
				for(String s: split) {
					toReturn.add(Integer.parseInt(s));
				}
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return toReturn;
	}
	
	public static String encryptText(String text, ArrayList<Integer> key) {
		double rowDouble = key.size();
		char[][] cipherMatrix = new char[key.size()][(int) Math.ceil(text.length()/rowDouble)];
		
		int character = 0;
		for(int row = 0; row < cipherMatrix.length; row++) {
			for(int col = 0; col < cipherMatrix[0].length; col++) {
				if(character < text.length()) {
					cipherMatrix[row][col] = text.charAt(character);
				}
				else {
					cipherMatrix[row][col] = '%';
				}
				character++;
			}
		}
		String encrypted = "";
		for(Integer i: key) {
			for(int row = 0; row < cipherMatrix.length; row++) {
				encrypted += cipherMatrix[row][i - 1];
			}
		}
		return encrypted;
	}
	
	public static String decryptText(String text, ArrayList<Integer> key) {
		double rowDouble = key.size();
		char[][] plainMatrix = new char[key.size()][(int) Math.ceil(text.length()/rowDouble)];
		
		int character = 0;
		for(Integer i: key) {
			for(int row = 0; row < plainMatrix[0].length; row++) {
				plainMatrix[row][i - 1] = text.charAt(character);
				character++;
			}
		}
		
		String plain = "";
		for(int row = 0; row < plainMatrix.length; row++) {
			for(int col = 0; col < plainMatrix[0].length; col++) {
				plain += plainMatrix[row][col];
			}
		}
		return plain;
	}
}
