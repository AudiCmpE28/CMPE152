package lexer;

public class Word extends Token {

   public String lexeme = "";
   public Word(String s, int tag) //default constructor
   { 
      super(tag);//creating a parent class of this tag, which is the token
      lexeme = s; 
   }
   public String toString() { return lexeme; }

   public static final Word

      and = new Word( "&&", Tag.AND ),  or = new Word( "||", Tag.OR ),
      eq  = new Word( "==", Tag.EQ  ),  ne = new Word( "!=", Tag.NE ),
      le  = new Word( "<=", Tag.LE  ),  ge = new Word( ">=", Tag.GE ),

      minus  = new Word( "minus", Tag.MINUS ),
      True   = new Word( "true",  Tag.TRUE  ),
      False  = new Word( "false", Tag.FALSE ),
      eof = new Word("`", Tag.EOF), 
      temp   = new Word( "t",     Tag.TEMP  );
}
