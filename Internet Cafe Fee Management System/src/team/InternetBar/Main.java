package team.InternetBar;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
		int temp;
		MySystem ms = new MySystem();
		Menu menu = new Menu();
		menu.systemMenu();
		Scanner sc = new Scanner(System.in);
		temp = sc.nextInt();
		while(temp != 0) {
			switch(temp) {
				case 1:
					ms.signIn();
					break;
				default:
					System.out.println("无此选项");
					break;
			}
			menu.systemMenu();
			temp = sc.nextInt();
		}
		sc.close();
		Tools.releaseConn();
	}
}
