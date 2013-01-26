package com.samphippen.games.ggj2013;

import java.io.FileInputStream;
import java.util.Scanner;

public class ConstantsReader {

	public void readConstants(FileInputStream fileStream){
		Scanner scanner = new Scanner(fileStream);
		scanner.next();
		Constants.SIZE = Double.parseDouble(scanner.next()); 
		scanner.next();
		Constants.SPEED = Double.parseDouble(scanner.next());
	}
}
