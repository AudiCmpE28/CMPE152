package inter;

import symbols.Type;

public class For extends Stmt {

   Expr expr; Stmt stmt_1, stmt_2, stmt_in_block;

   public For() {expr = null; stmt_1 = stmt_2 = stmt_in_block = null; } 

   public void init(Stmt arg1, Expr arg2, Stmt arg3, Stmt s) {

      stmt_1 = arg1;
      expr = arg2;
      stmt_2 = arg3;
      stmt_in_block = s;

      if( expr.type != Type.Bool ) expr.error("boolean required in For loop");

      children.add(stmt_1);
      children.add(expr);
      children.add(stmt_2);
      children.add(stmt_in_block);
   }

   public String getNodeStr() {
      return "FOR";
   }
}

