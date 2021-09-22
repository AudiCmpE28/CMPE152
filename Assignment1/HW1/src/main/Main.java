package main;

import java.io.IOException;

import lexer.Lexer;
import lexer.Token;

public class Main {
	public static void main(String[] args) throws IOException {
		Lexer mylexer = new Lexer();
		System.out.print("Enter anything: ");
		while (true) {
			Token parsedToken = mylexer.getNextToken();
			if (parsedToken == null) {
				System.out.println("End of Line");
				break;
			}else if(parsedToken.tag >=256 && parsedToken.tag <=275){
				switch(parsedToken.tag){
					case 256:System.out.println("AND\t" + parsedToken.toString());continue;
					case 257:System.out.println("BASE_TYPE\t" + parsedToken.toString());continue;
					case 258:System.out.println("BREAK\t" + parsedToken.toString());continue;
					case 259:System.out.println("DO\t" + parsedToken.toString());continue;
					case 260:System.out.println("ELSE\t" + parsedToken.toString());continue;
					case 261:continue;
					case 262:continue;
					case 263:continue;
					case 264:System.out.println("ID\t" + parsedToken.toString());continue;
					case 265:continue;
					case 266:continue;
					case 267:continue;
					case 268:continue;
					case 269:continue;
					case 270:System.out.println("NUM\t " + parsedToken.toString());
					case 271:continue;
					case 272:continue;
					case 273:continue;
					case 274:continue;
					case 275:System.out.println("WHILE\t" + parsedToken.toString());continue;
				}//end of switch
			}else System.out.println(parsedToken.toString() + "\t" + parsedToken.toString());
			} //end of while
		}//end of main
	}//end of class
