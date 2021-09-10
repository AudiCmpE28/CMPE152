package main;

import java.io.IOException;

import lexer.Lexer;
import lexer.Token;

public class Main {

	public static void main(String[] args) throws IOException {
		Lexer lex = new Lexer();
	    Token t = lex.scan();
	    System.out.printf("The first token's tag is: %d", t.tag);
	    // Rather than printing the first token's tag, you need to loop
		// until EOF and output each token's type name and its actual
		// string contents (lexeme).
		System.out.write('\n');
	}
}
