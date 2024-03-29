public class es5 {// riconosce cognomeMatricola (es. Bonicco123456)

	public static boolean scan(String s)
	{
		int state = 0;
		int i = 0;
		while (state >= 0 && i < s.length()) {
			final char ch = s.charAt(i++);
			switch (state) {

				case 0:
					if (ch >= 'L' && ch <= 'Z' || ch >= 'l' && ch <= 'z')
						state = 1;
					else if (ch >= 'A' && ch <= 'K' || ch >= 'a' && ch <= 'k')
						state = 2;
					else
						state = -1;
					break;

				case 1: // L/Z
					if (ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' )
						state = 1;
					else if (ch >= '0' && ch <= '9' && ch % 2 == 0)
						state = 1;
					else if (ch >= '0' && ch <= '9' && ch % 2 != 0)
						state = 4;
					else
						state = -1;
					break;

				case 2: // A/L
					if (ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' )
						state = 2;
					else if (ch >= '0' && ch <= '9' && ch % 2 == 0)
						state = 3;
					else if (ch >= '0' && ch <= '9' && ch % 2 != 0)
						state = 2;
					else
						state = -1;
					break;

				case 3:
					if (ch >= '0' && ch <= '9' && ch % 2 == 0)
						state = 3;
					else if (ch >= '0' && ch <= '9')
						state = 2;
					else
						state = -1;
					break;

				case 4:
					if (ch >= '0' && ch <= '9' && ch % 2 != 0)
						state = 4;
					else if (ch >= '0' && ch <= '9')
						state = 1;
					else
						state = -1;
					break;
			}
		}
			return state == 3 || state == 4;
		}
		public static void main(String[] args)
		{
			System.out.println(scan(args[0]) ? "OK" : "NOPE");
		}
}
