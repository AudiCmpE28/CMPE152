package lexer;

public class Word extends Token {

   public String lexeme = "";

   public Word(String s, int tag) {
      super(tag);
      lexeme = s;
   }

   public String toString() {
      return lexeme;
   }

   public String labelRetriever() {
      return "";
   }

   public static final Word and = new Word("AND", Tag.AND), //
         or = new Word("OR", Tag.OR), //
         eq = new Word("EQ", Tag.EQ), //
         ne = new Word("NE", Tag.NE), //
         le = new Word("LE", Tag.LE), //
         ge = new Word("GE", Tag.GE), //
         minus = new Word("minus", Tag.MINUS), //
         True = new Word("TRUE", Tag.TRUE), //
         False = new Word("FALSE", Tag.FALSE), //
         eof = new Word("`", Tag.EOF), //
         temp = new Word("t", Tag.TEMP); //
}
