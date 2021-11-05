package lexer;

import symbols.Type;

import java.io.IOException;
import java.util.HashMap;

public class Lexer {
   public static int line = 1;
   char peek = ' ';
   int i = 0;
   HashMap<String, Word> words = new HashMap<>();

   // Hashmap to return token names or add them
   HashMap<Integer, String> tokName = new HashMap<>();

   void reserve(Word w) {
      words.put(w.lexeme, w);
   }

   void tokenNamesInit() {
      tokName.put(256, "AND");
      tokName.put(257, "BASE_TYPE");
      tokName.put(258, "BREAK");
      tokName.put(259, "DO");
      tokName.put(260, "ELSE");
      tokName.put(261, "EQ");
      tokName.put(262, "FALSE");
      tokName.put(276, "EOF");
      tokName.put(277, "FOR");
      tokName.put(263, "GE");
      tokName.put(264, "ID");
      tokName.put(265, "IF");
      tokName.put(267, "LE");
      tokName.put(269, "NE");
      tokName.put(270, "NUM");
      tokName.put(271, "OR");
      tokName.put(272, "REAL");
      tokName.put(274, "TRUE");
      tokName.put(275, "WHILE");

      tokName.put(266, "INDEX");
      tokName.put(268, "MINUS");
   }

   public Lexer() {
      reserve(new Word("if", Tag.IF));
      reserve(new Word("else", Tag.ELSE));
      reserve(new Word("while", Tag.WHILE));
      reserve(new Word("do", Tag.DO));
      reserve(new Word("break", Tag.BREAK));
      reserve(new Word("for", Tag.FOR));

      reserve(Word.True);
      reserve(Word.False);
      reserve(Type.Int);
      reserve(Type.Char);
      reserve(Type.Bool);
      reserve(Type.Float);

      tokenNamesInit(); // calls function to anlso initialize token names hashmap
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

   // returns token name form hash map
   public String returnLabel(int tokenTag) throws IOException {
      String temp = tokName.get(tokenTag);
      if (temp == null) {
         return null;
      }
      return temp;
   }

   public Token getNextToken() throws IOException {
      Token currentTok = scan(); // will contain current token

      while (currentTok.tag == 13) { // checks for carriage return (CR) "\r"
         readch();
         currentTok = scan();
      }

      if (currentTok.tag == 0) {
         return null;
      } else if (tokName.get(currentTok.tag) == null) {
         tokName.put(currentTok.tag, currentTok.toString());
      }

      return currentTok;
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

      // if (peek == '\uFFFF') { // Check if EOF
      // return new Token('\uFFFF');
      // }

      Token tok = new Token(peek);
      peek = ' ';
      return tok;
   }
}
