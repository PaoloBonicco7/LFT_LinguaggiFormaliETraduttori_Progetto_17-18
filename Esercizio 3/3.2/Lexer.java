import java.io.* ;
import java.util.* ;
/*
IDENTIFICATORI CON UNDERSCORE NUMERI E LETTERE:
	Esempio:
	aaa_12__12__bb , ACCETTATA l'identificaore termina quando incontra uno spazio

*/
public class Lexer {

	public static int line = 1; //serve per trovare riga d'errore
	public char peek = ' ';
	String ide = ""; //Per costruire identificatori e costanti numeriche

	private void readch(BufferedReader br) {
		try {
			peek = (char) br.read();
		} catch (IOException exc) {
			peek = (char) -1; // ERROR
		}
	}

	//METODO PER COSTRUZIONE IDENTIFICATORE
	public Word identificatore(BufferedReader br){
		boolean ok=false;
		while (Character.isDigit(peek) || Character.isLetter(peek) || peek == '_'){
			if (peek != '_' && !ok){
				ok = true;
			}
			ide = ide + peek;
			readch(br);
		}
		if (ok){
			return new Word(Tag.ID, ide);
		} else {
			System.err.println("Erroneous character:" + "STRINGA COMPOSTA DA SOLI '_' NON AMMESSA");
			return null;
		}
	}

	//METODO PER COSTRUZIONE IDENTIFICATORI NUMERI
	public NumberTok identificatoreNum(BufferedReader br){
		while(Character.isDigit(peek)){
			ide = ide + peek;
			readch(br);
		}
		return new NumberTok(Tag.NUM, ide);
	}

	public Token lexical_scan(BufferedReader br) {
		while (peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r') {
			if (peek == '\n') line++;
			readch(br);
		}

		switch (peek) {
			case '!':
				peek = ' ';
				return Token.not;
			case '(':
				peek = ' ';
				return Token.lpt;
			case ')':
				peek = ' ';
				return Token.rpt;
			case '+':
				peek = ' ';
				return Token.plus;
			case '-':
				peek = ' ';
				return Token.minus;
			case '*':
				peek = ' ';
				return Token.mult;
			case '/':
				readch(br);
		//GESTIONE COMMENTI
				if (peek == '/'){
					do{
						readch(br);
					} while (peek != (char)-1 && peek != '\n');
					peek = ' ';
					return lexical_scan(br);
				} else if (peek == '*'){
					boolean ok = false;
					while (peek != (char)-1 && !ok){
						readch(br);
						if (peek == '\n') line++;
						if (peek == '*'){
							readch(br);
							if (peek == '/'){
								ok = true;
							}
						}
					}
					if (!ok){
						System.err.println("Erroneous character:\n" + "\tcomment open");
						return null;
					}
					peek = ' ';
					return lexical_scan(br);
				} else
					return Token.div;
			case ';':
				peek = ' ';
				return Token.semicolon;

		// ... gestire i casi di (, ), +, -, * , /, ; ... //

			case '&': //gestione &&
				readch(br);
				if (peek == '&') {
					peek = ' ';
					return Word.and;
				} else {
					System.err.println("Erroneous character"
						+ " after & : " + peek );
					return null;
				}
			case '|':
				readch(br);
				if (peek == '|') {
					peek = ' ';
					return Word.or;
				} else {
					System.err.println("Erroneous character"
						+ " after | : " + peek );
					return null;
				}
			case '<':
				readch(br);
				if (peek == '=') {
					peek = ' ';
					return Word.le;
				} else if (peek == '>') {
					peek = ' ';
					return Word.ne;
				} else {
					return Word.lt;
				}
			case '>':
				readch(br);
				if (peek == '=')	{
					peek = ' ';
					return Word.ge;
				} else {
					return Word.gt;
				}
			case '=':
				readch(br);
				if (peek == '='){
					peek = ' ';
					return Word.eq;
				} else {
					return Token.assign;
				}
			// ... gestire i casi di ||, <, >, <=, >=, ==, <>, = ... //

			case (char)-1:
				return new Token(Tag.EOF);

			default: //PROBLEMA CON GESTIONE IDENTIFICATORI
				ide = "";
				if (Character.isLetter(peek) || peek == '_') { /* Bisogna presuppore che la stringa in input sia un identificatore, poi alla fine della lettura
													e bisogna salvare la stringa ceh viene letta, poi dopo che bisogna controllare se la sequenza è una parola chiave
													allora se lo è bisogna restituire il token della parola chiave, altrimetni un identificatore che corrisponde alla sequenza
													quindi un token con il campo lexeme uguale alle stringhe*/
			// ... gestire il caso degli identificatori e delle parole chiave //

					Word parola = identificatore (br);
					if (ide.equals("if")){
						return Word.iftok;
					} else if (ide.equals("then")){
						return Word.then;
					} else if (ide.equals("else")){
						return Word.elsetok;
					} else if (ide.equals("for")){
						return Word.fortok;
					} else if (ide.equals("do")){
						return Word.dotok;
					} else if (ide.equals("print")){
						return Word.print;
					} else if (ide.equals("read")){
						return Word.read;
					} else if (ide.equals("begin")){
						return Word.begin;
					} else if (ide.equals("end")){
						return Word.end;
					} else {
						return parola;
					}
				} else if (Character.isDigit(peek))	{
			// ... Gestione dei numeri

					return identificatoreNum (br);
				} else {
					System.err.println("Erroneous character: "
						+ peek );
					return null;
				}
			}
	}

	public static void main(String[] args) {
		Lexer lex = new Lexer();
		String path = "TestFile.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			Token tok;
			do {
				tok = lex.lexical_scan(br);
				System.out.println("Scan: " + tok);
			} while (tok.tag != Tag.EOF);
			br.close();
		} catch (IOException e) {e.printStackTrace();}
	}
}
