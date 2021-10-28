package main;

import inter.Node;
import inter.Prog;
import lexer.Lexer;
import parser.Parser;

import java.io.IOException;
// { int r; int dd; int a; int d; r = a; dd = d; while( dd <= r ) dd = 2*dd;}
// { bool a; {float a; a = 1.6;} {a = 1.6;}}
public class Main {
	public static void main(String[] args) throws IOException {
		System.out.print("Enter the input: ");
		Lexer lex = new Lexer();
		Parser parse = new Parser(lex);
		Prog tree = parse.program();
		
		System.out.printf("\nSyntax tree:\n");
		String treeStr = printTree(tree);
		System.out.printf(treeStr);
	}

	public static String printTree(Node root) {
		int indent = 0;
		StringBuilder sb = new StringBuilder();
		printTree(root, indent, sb);
		return sb.toString();
	}

	private static void printTree(Node root, int indent, StringBuilder sb) {
		sb.append(getIndentString(indent));
		sb.append("+--");
		sb.append(root.getNodeStr());
		sb.append("\n");
		for (Node n : root.getChildren()) {
			printTree(n, indent + 1, sb);
		}
	}

	private static String getIndentString(int indent) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append("|  ");
		}
		return sb.toString();
	}
}
