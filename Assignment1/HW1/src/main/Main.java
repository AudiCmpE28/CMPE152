package main;

import java.io.IOException;

import lexer.Lexer;
import lexer.Token;

public class Main {
	public static void main(String[] args) throws IOException {
		Lexer mylexer = new Lexer();
		System.out.print("Enter the string to be parsed: ");
		while (true) {
			Token parsedToken = mylexer.getNextToken();
			if (parsedToken == null) {
				System.out.println("End of Line");
				break;
			}else if(parsedToken.tag >=256 && parsedToken.tag <=275){
				switch(parsedToken.tag){
					case 257:System.out.println("BASE_T\t" + parsedToken.toString());continue;
					case 264:System.out.println("ID\t" + parsedToken.toString());continue;
					case 270:System.out.println("NUM\t" + parsedToken.toString());continue;
					case 272:System.out.println("REAL\t" + parsedToken.toString());continue;
				}//end of switch
			}else System.out.println(parsedToken.toString() + "\t" + parsedToken.toString());
			} //end of while
		}//end of main
	}//end of class