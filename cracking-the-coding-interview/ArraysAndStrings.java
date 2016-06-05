import java.util.Arrays; 

class ArraysAndStrings {
	/**
	 * Determine if a string has all unique characters.
	 */
	Boolean uniqueString(String str) {
		for (int i=0; i < str.length(); i++) 
			if (str.lastIndexOf(str.charAt(i)) != i) return false;
		return true;
	}

	/**
	 * Check if one string is a permutation of another.
	 */
	Boolean permutation(String a, String b) {
		char[] aArray = a.toCharArray();
		char[] bArray = b.toCharArray();
		Arrays.sort(aArray);
		Arrays.sort(bArray);
		return Arrays.equals(aArray, bArray);
	}

	/**
	 * urlify a string by converting spaces to "%20".
	 * Use a char array for Java, so conversion can be done
	 * in place.  The input string is padded with the correct
	 * number of spaces, to insure that the conversion will fit.
	 * 'num' is the number of chars without trailing blanks.
	 */
	char[] urlify(char[] chars, int num) {
		char c;
		int len = chars.length;
		int indx = len-1;

		for (int i=0; i<num; i++) {
			c = chars[num-(i+1)];
			if (c == ' ') {
				chars[indx--] = '0';
				chars[indx--] = '2';
				chars[indx--] = '%';
			} else
				chars[indx--] = c;
		}
		return chars;
	}

	/**
	 * Check if string is a permutation of a palindrome, lowercasing and removing
	 * spaces from the input string.
	 */
	Boolean palindromePermutation(String str) {
		char[] chars = str.toLowerCase().replaceAll("\\s","").toCharArray();
		Arrays.sort(chars);
		int odds = 0;
		int i = 0;
		int cnt;
		char c;

		while (i < chars.length) {
			c = chars[i];
			cnt = 0;
			do {
				cnt++;
				i++;
			} while (i < chars.length && chars[i] == c);
			odds += (cnt % 2);
			if (odds > 1) return false;
		}
		return true;
	}

	/**
	 * Check if two strings are a maximum of 1 edit distance apart.
	 * That is, the strings are equal or are different by one character.
	 */
	Boolean oneAway(String s, String t) {
		// Check for 0 edit distance
		if (s==t) return true;

		StringBuffer sb = new StringBuffer(s);
		char c;

		for (int index = 0; index < s.length(); index++) {
			c = sb.charAt(index);

			// check for one character deletion difference.
			sb.deleteCharAt(index);
			if (sb.toString().equals(t)) return true;
			sb.insert(index, c);

			for (int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				// Check for one character insertion difference.
				sb.insert(index, (char)charCode);
				if (sb.toString().equals(t)) return true;
				sb.deleteCharAt(index);

				// Check for one character substition difference.
				sb.setCharAt(index, (char)charCode);
				if (sb.toString().equals(t)) return true;
				sb.setCharAt(index, c);
			}
		}

		return false;
	}

	/**
	 * Compress a string, replacing duplicate consective characters with counts.
	 * For example, "aabcccccaaa" becomes a2b1c5a3". If the compressed string is
	 * not smaller than the original string, the original string is returned.
	 */
	String stringCompression(String str) {
		char[] chars = new char[str.length()];	// compressed string
		char c;
		int dups;
		int i = 0;  														// index for original string
		int j = 0;															// index for compressed string

		do {
			dups = 0;
			c = str.charAt(i);
			do {
				dups++;
				i++;
			} while (i < str.length() && c == str.charAt(i));
			chars[j] = c;
			chars[j+1] = Character.forDigit(dups, 10);	// assumes dups <= 9
			j += 2;
		} while (i < str.length() && j < str.length());

		if (j >= str.length()) 
			return str;
		else 
			return String.valueOf(chars);
	}

	/**
	 * Rotate an NxN array, 90 degrees, in place.  Return true for valid input matrix.
	 * Otherwise, return false.
	 */
	Boolean rotateArray(int[][] arr) {
		// Check for invalid input array.
		if (arr.length == 0) return false;
		for (int[] row: arr)
			if (row.length != arr.length) return false;
		
		// Rotate starting with outer-most rows and columns, then continue working towards
		// the center with the remaining outermost rows & columns, until there are no more
		// entries or just a single one.
		int n = arr.length; 
		for (int layer = 0; layer < n/2; layer++) {
			int first = layer;
			int last = n-1-layer;
			for(int i = first; i < last; i++) {
				int offset = i - layer;
				int top = arr[first][i];
				arr[first][i] = arr[last-offset][first];					// left => top
				arr[last-offset][first] = arr[last][last-offset];	// bottom => left
				arr[last][last-offset] = arr[i][last];						// right => bottom
				arr[i][last] = top;																// top => right
			}
		}
		return true;
	}

	/**
	 * Search array for entries that are zero.  When found, set all elements in the row
	 * and column of the zero element to zero.
	 */
	void zeroArray(int [][] arr) {

		// Check for zeros in the 0th column and 0th row
		//
		Boolean zeroInCol0 = false;
		Boolean zeroInRow0 = false;
		
		for (int i=0; i<arr.length; i++)	// check all rows
			if (arr[i][0] == 0) zeroInCol0 = true;
		for (int j=0; j<arr.length; j++) 	// check all cols
			if (arr[0][j] == 0) zeroInRow0 = true;
		
		// Check for zeros in remaining rows and columns, storing zero in 0th row or
		// column to indicate that the corresponding row or column needs to be zeroed.
		for (int i=1; i<arr.length; i++) {
			for (int j=1; j<arr.length; j++) {
				if(arr[i][j] == 0) {
					arr[0][j] = 0;
					arr[i][0] = 0;
				}
			}
		}

		// Using indicators set above, zero rows and columns.
		//
		for (int i = 0; i < arr.length; i++)	// check 0th column for zeros
			if (arr[i][0] == 0) zeroRow(arr, i);

		for (int j = 0; j < arr.length; j++)	// check 0th row for zeros
			if (arr[0][j] == 0) zeroCol(arr, j);

		if(zeroInCol0) zeroCol(arr, 0);

		if(zeroInRow0) zeroRow(arr, 0);
	}

	void zeroCol(int[][] arr, int col) {
		for (int i=0; i<arr.length; i++) arr[i][col] = 0;
	}

	void zeroRow(int[][] arr, int row) {
		for (int j=0; j<arr.length; j++) arr[row][j] = 0;
	}

	public static void main(String[] args) {
		ArraysAndStrings test = new ArraysAndStrings();
		Boolean val;
		char[] chars;
		String str;
		int[][] arr = {{0,1,2,3},{4,5,6,7},{8,9,0,11},{12,13,14,15}};

		val = test.uniqueString("abcedfbg");
		System.out.println("uniqueString(abcdefbg) = " + val);
		val = test.uniqueString("abcdefgh");
		System.out.println("uniqueString(abcdefgh) = " + val);

		val = test.permutation("abcdef", "bcdafe");
		System.out.println("permutation(abcdef, bcdafe) = " + val);
		val = test.permutation("abcdef", "acdafe");
		System.out.println("permutation(abcdef, acdafe) = " + val);

		chars = "Mr John Smith    ".toCharArray();
		chars = test.urlify(chars, 13);
		System.out.println("urlify(Mr John Smith    ) = " + String.valueOf(chars));

		val = test.palindromePermutation("Tact coa");
		System.out.println("palindromePermutation(Tact coa) = " + val);

		val = test.oneAway("pale", "ple");
		System.out.println("oneAway(pale, ple) = " + val);
		val = test.oneAway("pales", "pale");
		System.out.println("oneAway(pales, pale) = " + val);
		val = test.oneAway("pale", "bale");
		System.out.println("oneAway(pale, bale) = " + val);
		val = test.oneAway("pale", "bake");
		System.out.println("oneAway(pale, bake) = " + val);

		str = test.stringCompression("aabcccccaaa");
		System.out.println("stringCompression(aabcccccaaa) = " + str);
		str = test.stringCompression("abccccca");
		System.out.println("stringCompression(abccccca) = " + str);

		val = test.rotateArray(arr);
		System.out.println("rotateArray({{0,1,2,3},{4,5,6,7},{8,9,0,11},{12,13,14,15}}) = " +
			val);
		for (int[] row: arr)
			System.out.println(Arrays.toString(row));

		test.zeroArray(arr);
		System.out.println("zeroArray({{12,8,4,0},{13,9,5,1},{14,0,6,2},{15,11,7,3}}) = " +
			val);
		for (int[] row: arr)
			System.out.println(Arrays.toString(row));
	}
}