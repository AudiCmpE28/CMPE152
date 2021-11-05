package inter;

import lexer.Token;
import symbols.Type;

public class Logical extends Expr {

   public Expr expr1, expr2;

   Logical(Token tok, Expr x1, Expr x2) {
      super(tok, null); // null type to start
      expr1 = x1;
      expr2 = x2;
      type = check(expr1.type, expr2.type);
      if (type == null)
         error("type error");
      children.add(expr1);
      children.add(expr2);
   }

   public Type check(Type p1, Type p2) {
      if (p1 == Type.Bool && p2 == Type.Bool)
         return Type.Bool;
      else if (p1 == Type.Int && p2 == Type.Int)
         return Type.Int;
      else if (p1 == Type.Char && p2 == Type.Char)
         return Type.Char;
      else if (p1 == Type.Float && p2 == Type.Float)
         return Type.Float;
      else
         return null;
   }
}
