package lexer;

import symbols.Type;

import java.io.IOException;
import java.util.HashMap;

public class Lexer {
   public static int line = 1;
   char peek = ' ';
   HashMap<String, Word> words = new HashMap<>();

   void reserve(Word w) {
      words.put(w.lexeme, w);
   }

   public Lexer() {
      reserve(new Word("if", Tag.IF));
      reserve(new Word("else", Tag.ELSE));
      reserve(new Word("while", Tag.WHILE));
      reserve(new Word("do", Tag.DO));
      reserve(new Word("break", Tag.BREAK));

      reserve(Word.True);
      reserve(Word.False);

      reserve(Type.Int);
      reserve(Type.Char);
      reserve(Type.Bool);
      reserve(Type.Float);
   }

   void readch() throws IOException {
      peek = (char) System.in.read();
   }

   boolean readch(char c) throws IOException {
      readch();
      if (peek != c)
         return false;
      peek = ' ';
      return true;
   }

   public Token getNextToken() throws IOException {
      for (;; readch()) { // for readch is true
         if (peek == ' ' || peek == '\t') {
            continue;
         } else if (peek == '\n') {
            line = line + 1;
         } else {
            break;
         }
      }

      Token current_token = scan(); // will contain current token
      // System.out.println("String:: " + current_token.toString());

      if (current_token.tag == 13) { // end of line token num is 13 for \r\n
         System.out.println("Tag:: " + current_token.tag);
         return null;
      }
<<<<<<< HEAD

      Word temp = new Word(currentT.toString(), currentT.tag);

      if (words.get(currentT.toString()) != null) {
         // System.out.println("HashTable: " + words.get(temp.toString()));
         System.out.println("HashTable: ");
      } else if (temp.toString() == currentT.toString()) {
         // System.out.println("Word Label: " + temp.toString());
         System.out.println("Word Label: ");
      } else {
         reserve(temp); // adds stuff like { } ; [ ]
         // System.out.println("HashTable [Else]: " + words.get(currentT.toString()));
         System.out.println("HashTable [Else]: ");
      }
      return currentT;
=======
      
      return current_token;

>>>>>>> ffb1ed9d35738d1a3111fbca2d2f4125dc073dd7
   }

   public Token scan() throws IOException {
      for (;; readch()) { // while readch is true
         if (peek == ' ' || peek == '\t')
            continue;
         else if (peek == '\n')
            line = line + 1;
         else
            break;
      }
      switch (peek) {
         case '&':
            if (readch('&'))
               return Word.and;
            else
               return new Token('&');
         case '|':
            if (readch('|'))
               return Word.or;
            else
               return new Token('|');
         case '=':
            if (readch('='))
               return Word.eq;
            else
               return new Token('=');
         case '!':
            if (readch('='))
               return Word.ne;
            else
               return new Token('!');
         case '<':
            if (readch('='))
               return Word.le;
            else
               return new Token('<');
         case '>':
            if (readch('='))
               return Word.ge;
            else
               return new Token('>');
      }

      if (Character.isDigit(peek)) {
         int v = 0;
         do {
            v = 10 * v + Character.digit(peek, 10);
            readch();
         } while (Character.isDigit(peek));
         if (peek != '.')
            return new Num(v);
         float x = v;
         float d = 10;
         for (;;) {
            readch();
            if (!Character.isDigit(peek))
               break;
            x = x + Character.digit(peek, 10) / d;
            d = d * 10;
         }
         return new Real(x);
      }

      if (Character.isLetter(peek)) {
         StringBuffer b = new StringBuffer();
         do {
            b.append(peek);
            readch();
         } while (Character.isLetterOrDigit(peek));
         String s = b.toString();
         Word w = (Word) words.get(s);
         if (w != null)
            return w;
         w = new Word(s, Tag.ID);
         words.put(s, w);
         return w;
      }

      Token tok = new Token(peek);
      peek = ' ';
      return tok;
   }
}
