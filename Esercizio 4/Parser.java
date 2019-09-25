import java.io.*;
/*
* ANALIZZATORE SINTATTICO O PARSIFICATORE:
* Esempio: 3 + 4 * 5
expr
term exprp
3 termp exprp
3 exprp
3 + term exprp
3 + fact termp exprp
3 + 4 termp exprp
3 + 4 * fact termp exprp
3 + 4 * 5 termp exprp
3 + 4 * 5 exprp

Prendiamo un esempio:
1 + 23 EOF
-------
start
expr EOF
term exprp EOF
fact termp exprp EOF
//fact diventa un numero:
1 termp exprp EOF
//termp corrisponde ad epsilon, quindi sparisce
1 exprp EOF
1 + term exprp EOF
1 + fact termp exprp EOF
1 + 23 termp exprp EOF
1 + 23 exprp EOF
//corrisponde ad epsilon quindi sparisce
1 + 23 EOF

Altro esempio:
dove vengono rilevati errori?
5+)  --> in term
1*+2 --> in fact
2+(  --> expr
1+2( --> Term
)2 --> Start
1+2) --> syntax error (match)
() --> errore
)2 --> Start
2+( --> expr
5+) --> Termp
1+2( --> termp
1*+2 --> fact
1+2) --> syntax error (confronto con EOF)
*/

public class Parser {
   private Lexer lex;
   private BufferedReader pbr;
   private Token look; //corrisponde a cc nella teoria

   public Parser(Lexer l, BufferedReader br) {
      lex = l;
      pbr = br;
      move();
   }

   void move() { //leggi il carattere seguente
      look = lex.lexical_scan(pbr);
      System.out.println("token = " + look);
   }

   void error(String s) { //metodo per gestire errori,
      //si può passare una stringa per approfonidire l'errore
      throw new Error("near line " + lex.line + ": " + s);
   }

   void match(int t) { //La variabile t corrisponde ad un tag
      if (look.tag == t) {
         if (look.tag != Tag.EOF) move(); //move() se non siamo a EOF
      } else error("syntax error in match");
   }

   public void start() {
 		if(look.tag=='('||look.tag==Tag.NUM){
 			expr();
 			match(Tag.EOF);
 		}else {
 			error("syntax error start");
 		}
 	}

 	private void expr() {
 		if((look.tag=='(')||(look.tag==Tag.NUM)){
 			term();
 			exprp();
 		}else {
 			error("syntax error expr");
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
 			break;
 		case Tag.EOF:
 			break;
 		default:
 			error("syntax error exprp");
 		}
 	}

 	private void term() {
 		if(look.tag=='('||look.tag==Tag.NUM){
 			fact();
 			termp();
 		}else {
 			error("syntax error term");
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
 			break;
 		case '-':
 			break;
 		case ')':
 			break;
 		case Tag.EOF:
 			break;
 		default:
 			error("syntax error termp");
 		}
 	}

 	private void fact() {
 		if(look.tag=='('||look.tag==Tag.NUM){
 			if(look.tag=='('){
 				match('(');
 				expr();
 				match(')');
 			}else match(Tag.NUM);
 		}else {
 			error("syntax error fact");
 		}
   }

   public static void main(String[] args) {
      Lexer lex = new Lexer();
      String path = "TestFile.txt"; // il percorso del file da leggere
      try {
         BufferedReader br = new BufferedReader(new FileReader(path));
         Parser parser = new Parser(lex, br);
         parser.start();
         System.out.println("Input OK");
         br.close();
      } catch (IOException e) {e.printStackTrace();}
   }
}
