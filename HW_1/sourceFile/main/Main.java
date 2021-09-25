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
				// System.out.println("ERROR: Invalid Operation");
				System.out.println("EOF");
				break;
				// } else if (parsedToken.toString() == "EOF") {
				// System.out.println("EOF");
				// break;
			} else { // prints
				System.out.println(mylexer.returnLabel(parsedToken.tag) + "   " + parsedToken.toString());
				// + " " + parsedToken.tag

			}
		}
	}

}

/* java main/Main.java */
/* java main.Main < test_file */