public class Russian {
	public static int multiply (int m, int n) {
		int toReturn = 0;
		
		while (n > 0) {
			//System.out.println(n%2 == 1);
			if (n%2 == 1) {
				toReturn += m;
                System.out.println("1");
            }
			
			m = m*2; 
			n = n/2;
            System.out.println("n = " + n);
            System.out.println("2");
        }
        System.out.println("3");

        return toReturn;
	}
}
