package inter;

import symbols.Type;

public class For extends Stmt {

   Expr expr1, expr2, expr3; Stmt stmt;

   public For() { expr1 = expr2 = expr3 = null; stmt = null; } 

   public void init(Expr arg1, Expr arg2, Expr arg3, Stmt s) {
      expr1 = arg1; expr2 = arg2; expr3 = arg3;  stmt = s;
      if( expr2.type != Type.Bool ) expr2.error("boolean required in For loop");

      children.add(expr1);
      children.add(expr2);
      children.add(expr3);
      children.add(stmt);
   }

   public String getNodeStr() {
      return "FOR";
   }
}

