public class NumberTok extends Token {
	
	public String lexeme = "";
	
	public NumberTok (int tag, String s) { super(tag); lexeme=s; }  //COSTRUTTORE
		// Salva il tag e la stringa
	
	public String toString() { return "<" + tag + ", " + lexeme + ">"; }
	/*
	public static final NumberTok
		zero = new NumberTok (Tag.NUM, 0),
		one = new NumberTok (Tag.NUM, 1),
		two = new NumberTok (Tag.NUM, 2),
		three = new NumberTok (Tag.NUM, 3),
		four = new NumberTok (Tag.NUM, 4),
		five = new NumberTok (Tag.NUM, 5),
		six = new NumberTok (Tag.NUM, 6),
		seven = new NumberTok (Tag.NUM, 7),
		eigth = new NumberTok (Tag.NUM, 8),
		nine = new NumberTok (Tag.NUM, 9);
	*/	
}
