/*
l'automa accetta le stringhe che contengono dei commenti:
	esempi:	 “aaa/ **** /aa”, “aa/ * a * a * /”, “aaaa”, “/ **** /”, “/ * aa * /”, “ * /a”, “a/ ** / *** a”,
			“a/ ** / *** /a” e “a/ ** /aa/ *** /a”, ma non “aaa/ * /aa” oppure “aa/ * aa”.
*/

public class es8 {

	public static boolean scan(String s) {

		int i=0,state=0;
		while(state >= 0 && i < s.length()) {
			final char c=s.charAt(i++);
			switch (state) {

  			case 0:
          if (c== '/')
  				  state = 1;
  				else if (c=='a' || c== '*')
  					state = 0;
  				else
  					state = -1;
  				break;

  			case 1:
          if (c=='*')
  					state = 2;
  				else if (c == 'a')
            state = 0;
          else
  					state = -1;
  				break;

  			case 2:
          if (c=='*')
  					state = 3;
  				else if(c=='a')
  					state = 2;
          else if(c == '/')
            state = 1;
  				else
  					state=-1;
  				break;

  			case 3:
          if (c=='a')
  					state =2;
  				else if (c=='*')
  					state =3;
          else if (c == '/')
            state = 0;
  				else
  					state=-1;
  				break;

	    }
    }
		return state == 0 || state == 1;
	}

  public static void main(String[] args)
  {
    System.out.println(scan(args[0]) ? "OK" : "NOPE");
  }

}
