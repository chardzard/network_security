package q4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DigraphicCipher {

	static char[][] keyMatrix;

	public static void main(String[] args) {
		keyMatrix = readKeyMatrixFile();
		// for(int row = 0; row < keyMatrix.length; row++) {
		// for(int col = 0; col < keyMatrix[0].length; col++) {
		// System.out.print(keyMatrix[row][col]);
		// }
		// System.out.println();
		// }
		ArrayList<Pair> plainText = readPlainText();
		ArrayList<Pair> cipherText = encryptOrDecrypt(plainText, true);
		try {
			FileWriter outFile = new FileWriter(new File("cipher.txt"));
			for (Pair p : cipherText) {
				outFile.append(p.getFirst());
				outFile.append(p.getSecond());
			}
			outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Pair> decrypt = encryptOrDecrypt(cipherText, false);
		try {
			FileWriter outFile = new FileWriter(new File("decrypt.txt"));
			for (Pair p : decrypt) {
				if (p.getFirst() != 'X' && p.getFirst() != 'Z') {
					outFile.append(p.getFirst());
				}
				if (p.getSecond() != 'X' && p.getSecond() != 'Z') {
					outFile.append(p.getSecond());
				}
			}
			outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static char[][] readKeyMatrixFile() {
		char[][] toReturn = new char[5][5];

		try {
			Scanner inFile = new Scanner(new File("key.txt"));
			int row = 0;
			while (inFile.hasNextLine()) {
				String line = inFile.nextLine();
				String[] split = line.split("\t");
				for (int col = 0; col < split.length; col++) {
					toReturn[row][col] = split[col].charAt(0);
				}
				row++;
			}
			inFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return toReturn;
	}

	public static ArrayList<Pair> readPlainText() {
		ArrayList<Pair> toReturn = new ArrayList<Pair>();
		String text = "";

		try {
			Scanner inFile = new Scanner(new File("plain.txt"));

			while (inFile.hasNextLine()) {
				text += inFile.nextLine();
			}

			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		text = text.replaceAll(" ", "");
		text = text.toUpperCase();

		StringBuffer textBuffer = new StringBuffer(text);

		for (int i = 0; i < textBuffer.length() - 1; i++) {
			if (textBuffer.charAt(i) == textBuffer.charAt(i + 1)) {
				textBuffer.insert(i + 1, 'X');
			}
		}

		text = textBuffer.toString();

		if (text.length() % 2 != 0) {
			text += "Z";
		}

		System.out.println(text);

		for (int i = 0; i < text.length() - 1; i += 2) {
			toReturn.add(new Pair(text.charAt(i), text.charAt(i + 1)));
		}

		return toReturn;
	}

	public static ArrayList<Pair> encryptOrDecrypt(ArrayList<Pair> pairs, boolean encrypt) {
		ArrayList<Pair> toReturn = new ArrayList<Pair>();

		for (Pair p : pairs) {
			Pair encryptedPair = checkColumn(p, encrypt);
			if (encryptedPair.isEmpty()) {
				encryptedPair = checkRow(p, encrypt);
				if (encryptedPair.isEmpty()) {
					encryptedPair = checkOther(p);
				}
			}
			toReturn.add(encryptedPair);
		}

		return toReturn;
	}

	public static Pair checkColumn(Pair p, boolean encrypt) {
		boolean empty = true;
		for (int col = 0; col < keyMatrix.length; col++) {
			int first = -1, second = -1;
			for (int row = 0; row < keyMatrix[0].length; row++) {
				if (keyMatrix[row][col] == p.getFirst()) {
					first = row;
				}
				if (keyMatrix[row][col] == p.getSecond()) {
					second = row;
				}
			}
			if (first > -1 && second > -1) {
				empty = false;
				if (encrypt) {
					first = (first + 1) % 5;
					second = (second + 1) % 5;
				} else {
					first -= 1;
					second -= 1;
					if (first < 0) {
						first = 4;
					}
					if (second < 0) {
						second = 4;
					}
				}
				p.setFirst(keyMatrix[first][col]);
				p.setSecond(keyMatrix[second][col]);
			}
		}
		p.setEmpty(empty);
		return p;
	}

	public static Pair checkRow(Pair p, boolean encrypt) {
		boolean empty = true;
		for (int row = 0; row < keyMatrix.length; row++) {
			int first = -1, second = -1;
			for (int col = 0; col < keyMatrix[0].length; col++) {
				if (keyMatrix[row][col] == p.getFirst()) {
					first = col;
				}
				if (keyMatrix[row][col] == p.getSecond()) {
					second = col;
				}
			}
			if (first > -1 && second > -1) {
				empty = false;
				if (encrypt) {
					first = (first + 1) % 5;
					second = (second + 1) % 5;
				} else {
					first -= 1;
					second -= 1;
					if (first < 0) {
						first = 4;
					}
					if (second < 0) {
						second = 4;
					}
				}
				p.setFirst(keyMatrix[row][first]);
				p.setSecond(keyMatrix[row][second]);
			}
		}
		p.setEmpty(empty);
		return p;
	}

	public static Pair checkOther(Pair p) {
		int firstRow = -1, firstCol = -1, secondRow = -1, secondCol = -1;
		for (int row = 0; row < keyMatrix.length; row++) {
			for (int col = 0; col < keyMatrix[0].length; col++) {
				if (keyMatrix[row][col] == p.getFirst()) {
					firstRow = row;
					firstCol = col;
				}
				if (keyMatrix[row][col] == p.getSecond()) {
					secondRow = row;
					secondCol = col;
				}
			}
		}
		p.setFirst(keyMatrix[firstRow][secondCol]);
		p.setSecond(keyMatrix[secondRow][firstCol]);
		return p;
	}
}
