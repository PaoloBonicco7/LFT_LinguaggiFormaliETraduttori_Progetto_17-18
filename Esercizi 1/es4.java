public class es4 { // Automa che riconosce turno matricola con spazi (T2 e T3)

	public static boolean scan(String s)
	{
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);
			switch (state) {

				case 0:
					if(ch >= '0' && ch <= '9' && (int)ch % 2 == 0)
						state = 2; // NUMERI PARI
					else if (ch >= '0' && ch <= '9' && (int)ch % 2 != 0)
						state = 1; //NUMERI DISPARI
					else if (ch == ' ')
						state = 0;
					else
						state = -1;
					break;

				case 1: // numeri dispari
					if(ch >= '0' && ch <= '9' && (int)ch % 2 == 0)
						state = 2; // NUMERI PARI
					else if (ch == ' ')
						state = 4;
					else if(ch >= '0' && ch <= '9' && (int)ch % 2 != 0)
						state = 1;
					else if(ch >= 'L' && ch <= 'Z')
						state = 5; //ok nome l_z e disp
					else
						state = -1;
					break;

				case 2: // numeri pari
					if(ch >= '0' && ch <= '9' && (int)ch % 2 == 0)
						state = 2; // NUMERI PARI
					else if (ch == ' ')
						state = 3;
					else if(ch >= '0' && ch <= '9' && (int)ch % 2 != 0)
						state = 1;
					else if(ch >= 'A' && ch <= 'K')
						state = 5; //ok nome a_k e pari
					else
						state = -1;
					break;

				case 3:
					if(ch >= 'A' && ch <= 'K')
						state = 5;
					else if (ch == ' ')
						state = 3;
					else
						state = -1;
					break;

				case 4:
					if(ch >= 'L' && ch <= 'Z')
						state = 5;
					else if (ch == ' ')
						state = 4;
					else
						state = -1;
					break;

				case 5:
					if(ch >= 'a' && ch <= 'z')
						state = 5;
					else if (ch == ' ')
						state = 6;
					else
						state = -1;
					break;

				case 6:
					if(ch == 'A' && ch == 'Z')
						state = 5;
					else if (ch == ' ')
						state = 6;
					else
						state = -1;
					break;
			}
		}
		return (state == 5 || state == 6);
		}
		public static void main(String[] args)
		{
			System.out.println(scan(args[0]) ? "OK" : "NOPE");
		}
}
