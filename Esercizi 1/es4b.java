public class es4b { // automa che riconosce turno matricola aìcon spazi
	public static boolean scan(String s)
	{
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);
			switch (state) {
				case 0:
					if(ch == ' ')
						state=0;
					else if(ch >= '0' && ch <= '9' && (int)ch % 2 == 0) //CONDIZ PARI
						state = 2;
					else if(ch >= '0' && ch <= '9' && (int)ch % 2 != 0) //CONDIZ DISPARI
						state=1;
					else
						state=-1;
				case 1: //disp
					if(ch >= '0' && ch <= '9' && (int)ch % 2 != 0)
						state=1;
					else if(ch >= '0' && ch <= '9' && (int)ch % 2 == 0)
						state=2;
					else if(ch == ' ' || ch >= 'L' && ch <= 'Z')
						state=3;
					else 
						state=-1;
				case 2:	//pari
					if(ch >= '0' && ch <= '9' && (int)ch % 2 != 0)
						state=1;
					else if(ch >= '0' && ch <= '9' && (int)ch % 2 == 0)
						state=2;
					else if(ch == ' ' || ch >= 'A' && ch <= 'K')
						state=4;
					else 
						state=-1;
				case 3: //disp
					if(ch == ' ')
						state=3;
					else if(ch >= 'L' && ch <= 'Z') //LETTERA MAIUSCOLA A/K
						state=5;
					else
						state=-1;	
				case 4: //pari
					if(ch == ' ')
						state=4;
					else if(ch >= 'A' && ch <= 'K')
						state=5;
					else 
						state=-1;
				case 5:
					if(ch >= 'a' && ch <= 'z')
						state=5;
					else if(ch == ' ')
						state=6;
					else
						state=-1;
				case 6:
					if(ch == ' ')
						state=6;
					else if(ch >= 'A' && ch <= 'Z')
						state=7;
					else
						state=-1;
				case 7:
					if(ch >= 'a' && ch <= 'z')
						state=7;
					else if(ch == ' ')
						state=8;
					else 
						state=-1;
				case 8:
					if(ch == ' ')
						state=8;
					else
						state=-1;
			}
		}
		return state == 5 || state == 6 || state == 7 || state == 8;
		}
		public static void main(String[] args)
		{
			System.out.println(scan(args[0]) ? "OK" : "NOPE");
		}
}