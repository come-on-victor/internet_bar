package team.InternetBar;

import java.util.Scanner;

public class Input {
	
	static Scanner sc;
	
	static {
		sc = new Scanner(System.in);
	}
	public static Scanner getInput() {
		return sc;
	}
	
	public static void releaseInput() {
		if(sc != null) {
			sc.close();
			sc = null;
		}
	}
}
