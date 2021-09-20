package main;

import java.io.IOException;

import lexer.Lexer;
import lexer.Token;

public class Main {
	public static void main(String[] args) throws IOException {
		Lexer mylexer = new Lexer();
		while (true) {
			Token parsedToken = mylexer.getNextToken();

			if (parsedToken == null) {
				System.out.println("End of Line");
				break;
			} else { // prints
				if (parsedToken.tag >= 256 && parsedToken.tag <= 275) {
					System.out.println("Token Number: " + parsedToken.tag);
					System.out.println("Tokens: " + parsedToken.toString() + " " + parsedToken.toString());
					System.out.write('\n');
				} else {
					System.out.println("Token Number: " + parsedToken.tag);
					System.out.println("Tokens: " + parsedToken.toString() + " " + parsedToken.toString());
					System.out.write('\n');
				}

				// continue;
			}
		}
	}

}
