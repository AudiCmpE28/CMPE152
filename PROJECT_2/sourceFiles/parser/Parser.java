package parser;

import java.io.*;
import lexer.*;
import symbols.*;
import inter.*;

public class Parser {

   private Lexer lex; // lexical analyzer for this parser
   private Token look; // lookahead token
   private Token lookBehind; // keep track of previous token
   private boolean prevTok;

   Env top = null; // current or top symbol table
   int used = 0; // storage used for declarations

   public Parser(Lexer l) throws IOException {
      lex = l;
      move();
   }

   void move() throws IOException {
      // look = lex.scan();
      look = lex.getNextToken();

   }

   void error(String s) {
      throw new Error("near line " + lex.line + ": " + s);
   }

   void match(int t) throws IOException {
      if (look.tag == t)
         move();
      else
         error("syntax error");
   }

   public Prog program() throws IOException { // program -> block
      Block s = block();
      return new Prog(s);
   }

   Block block() throws IOException { // block -> { decls stmts }
      match('{');
      Env savedEnv = top;
      top = new Env(top);
      decls();
      Stmt s = stmts();
      match('}');
      top = savedEnv;
      return new Block(s);
   }

   void decls() throws IOException {
      prevTok = false;
      while (look.tag == Tag.BASIC) { // D -> type ID ;
         Type p = type();
         Token tok = look;
         // match(Tag.ID);
         if (look.tag == Tag.ID) {
            lookBehind = look;
            match(Tag.ID);
            System.out.println("Grabbed variable:");
            System.out.println(lookBehind.toString());

         }

         if (look.tag == ';') { // match(;)
            match(';');
            Id id = new Id((Word) tok, p, used);
            top.put(tok, id);
            used = used + p.width;

         } else if (look.tag == '=') {
            // match('=');
            Token temp = new Token(';');
            System.out.println("Here for num");
            Id id = new Id((Word) tok, p, used);
            top.put(tok, id);
            used = used + p.width;

            prevTok = true; // assign();
            break;
         }
         // match(';'); // ; or = int a; a = 1;
         // Id id = new Id((Word) tok, p, used);
         // top.put(tok, id);
         // used = used + p.width;
      }
   }

   Type type() throws IOException {
      Type p = (Type) look; // expect look.tag == Tag.BASIC
      match(Tag.BASIC);
      return p; // T -> basic
   }

   Stmt stmts() throws IOException {
      if (look.tag == '}')
         return Stmt.Null;
      else
         return new Seq(stmt(), stmts());
   }

   Stmt stmt() throws IOException {
      Expr x;
      Stmt s, s1, s2;
      Stmt savedStmt; // save enclosing loop for breaks

      switch (look.tag) {
      // more case statements int i = 0; =
      case ';':
         move();
         return Stmt.Null;

      case Tag.IF:
         match(Tag.IF);
         match('(');
         x = allexpr();
         match(')');
         s1 = stmt();
         if (look.tag != Tag.ELSE)
            return new If(x, s1);
         match(Tag.ELSE);
         s2 = stmt();
         return new Else(x, s1, s2);

      case Tag.WHILE:
         While whilenode = new While();
         savedStmt = Stmt.Enclosing;
         Stmt.Enclosing = whilenode;
         match(Tag.WHILE);
         match('(');
         x = allexpr();
         match(')');
         s1 = stmt();
         whilenode.init(x, s1);
         Stmt.Enclosing = savedStmt; // reset Stmt.Enclosing
         return whilenode;

      case Tag.DO:
         Do donode = new Do();
         savedStmt = Stmt.Enclosing;
         Stmt.Enclosing = donode;
         match(Tag.DO);
         s1 = stmt();
         match(Tag.WHILE);
         match('(');
         x = allexpr();
         match(')');
         match(';');
         donode.init(s1, x);
         Stmt.Enclosing = savedStmt; // reset Stmt.Enclosing
         return donode;

      case Tag.BREAK:
         match(Tag.BREAK);
         match(';');
         return new Break();

      case '{':
         return block();

      default:
         return assign();
      }
   }

   Stmt assign() throws IOException {
      Stmt stmt;
      Token t = look;

      if (prevTok == true) {
         System.out.println("PrevTok true");
         // match(lookBehind.tag);
         Id id = top.get(lookBehind);
         if (id == null)
            error(lookBehind.toString() + " undeclared");
         move();
         stmt = new Set(id, allexpr()); // S -> id = E ;
         match(';');

         prevTok = false;
         return stmt;

      } else {
         match(Tag.ID);
         Id id = top.get(t);
         if (id == null)
            error(t.toString() + " undeclared");
         move();
         stmt = new Set(id, allexpr()); // S -> id = E ;
         match(';');
         return stmt;
      }
   }

   Expr allexpr() throws IOException {
      Expr x = andexpr();
      while (look.tag == Tag.OR) {
         Token tok = look;
         move();
         x = new Or(tok, x, andexpr());
      }
      return x;
   }

   Expr andexpr() throws IOException {
      Expr x = equality();
      while (look.tag == Tag.AND) {
         Token tok = look;
         move();
         x = new And(tok, x, equality());
      }
      return x;
   }

   Expr equality() throws IOException {
      Expr x = rel();
      while (look.tag == Tag.EQ || look.tag == Tag.NE) {
         Token tok = look;
         move();
         x = new Rel(tok, x, rel());
      }
      return x;
   }

   Expr rel() throws IOException {
      Expr x = expr();
      switch (look.tag) {
      case '<':
      case Tag.LE:
      case Tag.GE:
      case '>':
         Token tok = look;
         move();
         return new Rel(tok, x, expr());
      default:
         return x;
      }
   }

   Expr expr() throws IOException {
      Expr x = term();
      while (look.tag == '+' || look.tag == '-') {
         Token tok = look;
         move();
         x = new Arith(tok, x, term());
      }
      return x;
   }

   Expr term() throws IOException {
      Expr x = factor();
      while (look.tag == '*' || look.tag == '/') {
         Token tok = look;
         move();
         x = new Arith(tok, x, factor());
      }
      return x;
   }

   Expr factor() throws IOException {
      Expr x = null;
      switch (look.tag) {
      case '(':
         move();
         x = allexpr();
         match(')');
         return x;
      case Tag.NUM:
         x = new Constant(look, Type.Int);
         move();
         return x;
      case Tag.REAL:
         x = new Constant(look, Type.Float);
         move();
         return x;
      case Tag.TRUE:
         x = Constant.True;
         move();
         return x;
      case Tag.FALSE:
         x = Constant.False;
         move();
         return x;
      case Tag.ID:
         String s = look.toString();
         Id id = top.get(look);
         if (id == null)
            error(look.toString() + " undeclared");
         move();
         return id;
      default:
         error("syntax error");
         return x;
      }
   }
}
