import java.io.*;

public class Parser {
   private Lexer lex;
   private BufferedReader pbr;
   private Token look; //corrisponde a cc nella teoria

   public Parser(Lexer l, BufferedReader br) {
      lex = l;
      pbr = br;
      move();
   }

   void move() {
 		look = lex.lexical_scan(pbr);
 		System.out.println("token = " + look);
 	}

 	void error(String s) {
 		throw new Error("near line " + lex.line + ": " + s);
 	}

 	void match(int t) {
 		if (look.tag == t) {
 			if (look.tag != Tag.EOF) move();
 		} else error("syntax error");
 	}

 	public void prog() {
 		switch(look.tag){
 			case Tag.ID:
 			case Tag.PRINT:
 			case Tag.READ:
 			case Tag.IF:
 			case Tag.FOR:
 			case Tag.BEGIN:
 				statlist();
 				match(Tag.EOF);
 				break;
 			default:
 				error("syntax error prog()");
 		}
 	}

 	private void stat() {
 		switch(look.tag){
 			case Tag.ID:
 				match(Tag.ID);
 				match('=');
 				expr();
 				break;
 			case Tag.PRINT:
 				match(Tag.PRINT);
 				match('(');
 				expr();
 				match(')');
 				break;
 			case Tag.READ:
 				match(Tag.READ);
 				match('(');
 				match(Tag.ID);
 				match(')');
 				break;
 			case Tag.IF:
 				match(Tag.IF);
 				bexpr();
 				match(Tag.THEN);
 				stat();
 				if(look.tag==Tag.ELSE){
 					match(Tag.ELSE);
 					stat();
 					break;
 				}
 				break;
 			case Tag.FOR:
 				match(Tag.FOR);
 				match('(');
 				match(Tag.ID);
 				match('=');
 				expr();
 				match(';');
 				bexpr();
 				match(')');
 				match(Tag.DO);
 				stat();
 				break;
 			case Tag.BEGIN:
 				match(Tag.BEGIN);
 				statlist();
 				match(Tag.END);
 				break;
 			default:
 				error("syntax error stat()");
 		}
 	}

 	private void statlist() {
 		switch(look.tag){
 			case Tag.ID:
 			case Tag.PRINT:
 			case Tag.READ:
 			case Tag.IF:
 			case Tag.FOR:
 			case Tag.BEGIN:
 				stat();
 				statlistp();
 				break;
 			default:
 				error("syntax error statlist()");
 		}
 	}

 	private void statlistp() {
 		switch(look.tag){
 			case ';':
 				match(';');
 				stat();
 				statlistp();
 				break;
 			case Tag.END:
 			case Tag.EOF:
 				break;
 			default:
 				error("syntax error statlistp()");
 		}
 	}

 	private void bexpr() {
 		switch(look.tag){
 			case '(':
 			case Tag.NUM:
 			case Tag.ID:
 				expr();
 				match(Tag.RELOP);
 				expr();
 				break;
 			default:
 				error("syntax error bexpr()");
 		}
 	}

 	private void expr() {
 		switch(look.tag){
 			case '(':
 			case Tag.NUM:
 			case Tag.ID:
 				term();
 				exprp();
 				break;
 			default:
 				error("syntax error expr()");
 		}
 	}

 	private void exprp() {
 		switch (look.tag) {
 			case '+':
 				match('+');
 				term();
 				exprp();
 				break;
 			case '-':
 				match('-');
 				term();
 				exprp();
 				break;
 			case ')':
 			case ';':
 			case Tag.ELSE:
 			case Tag.END:
 			case Tag.RELOP:
 			case Tag.EOF:
 			case Tag.THEN:
 				break;
 			default:
 				error("syntax error exprp()");
 		}
 	}

 	private void term() {
 		switch(look.tag){
 			case '(':
 			case Tag.NUM:
 			case Tag.ID:
 				fact();
 				termp();
 				break;
 			default:
 				error("syntax error term()");
 		}
 	}

 	private void termp() {
 		switch(look.tag){
 			case '*':
 				match('*');
 				fact();
 				termp();
 				break;
 			case '/':
 				match('/');
 				fact();
 				termp();
 				break;
 			case '+':
 			case '-':
 			case ')':
 			case '(':
 			case ';':
 			case Tag.ELSE:
 			case Tag.END:
 			case Tag.RELOP:
 			case Tag.NUM:
 			case Tag.ID:
 			case Tag.EOF:
 			case Tag.THEN:
 				break;
 			default:
 				error("syntax error termp()");
 		}
 	}

 	private void fact() {
 		switch(look.tag){
 			case '(':
 				match('(');
 				expr();
 				match(')');
 				break;
 			case Tag.NUM:
 				match(Tag.NUM);
 				break;
 			case Tag.ID:
 				match(Tag.ID);
 				break;
 			default:
 				error("syntax error fact()");
 		}
   }

   	public static void main(String[] args) {
   		Lexer lex = new Lexer();
   		String path = "TestFile.txt"; // il percorso del file da leggere
   		try {
   			BufferedReader br = new BufferedReader(new FileReader(path));
   			Parser parser = new Parser(lex, br);
   			parser.prog();
   			System.out.println("Input OK");
   			br.close();
   		} catch (IOException e) {e.printStackTrace();}
   	}
}
