class BitManipulation {
	/**
	 * Question 5.1
	 * Replace bit positions 'i' through 'j' of n with m.
	 */
	int insertion(int m, int n, int i, int j) {
		int mask = (0xffffffff << j+1) | (0xffffffff >>> 32-i);
		return (m & mask) | n << i;
	}

	/**
	 * Convert a floating point number between 0 and 1 to a string 
	 * representation of its' binary value.  If the number can not be 
	 * accurately represented in 32 characters, return null.
	 */
	String binaryToString(double d) {
		String s = ".";

		while(d > 0 && s.length() < 32) {
			double g = d * 2;
			int i = (int) g;

			d = g - i;
			s += (char)(i + '0');
		}

		if (d==0) return s;
		else return null;
	}

	/**
	 * Determine the maximum number of sequential 1s that could be created by 
	 * flipping one bit to 1.
	 */
	int flipBitToWin(int x) {
		int count = 0;
		int leftOneBits = 0;
		int rightOneBits = 0;
		int maxbits = 0;
	
		while ((x & 0x00000001) == 1) {
			rightOneBits++;
			x = x >>> 1;
			count++;
		}
		while (count < 32) {
			x = x >> 1;	// zero bit
			count++;
			while ((x & 0x00000001) == 1 ) {
				leftOneBits++;
				x = x >> 1;
				count++;
			}
			maxbits = Math.max(maxbits, leftOneBits + rightOneBits + 1);
			leftOneBits = rightOneBits;
			rightOneBits = 0;
		}
		return maxbits;
	}

	/**
	 * Given a positive integer, return the next smallest and next largest number that has the same number
	 * of one bits in its' binary representation.
	 * The next largest number is computed by converting numbers of the form xx...x011..100..0 to xx..x100..001..1, 
	 * where x represents either a 0 or 1.
	 * Mathematically, this is accomplished by
	 *	1. x += 2**zeros - 1  (sets trailing zeros to ones)
	 *  2. x += 1 (flips first set of zeros and ones to zeros and puts a 1 at the bit in the next greatest position)
	 *  3. x += 2**(ones-1) -1 (sets trailing bits ones-1 zeros to ones)
	 *  where, zeros is the number of trailing zeros and ones is the size of the preceeding block of ones.
	 * Likewise, the next smallest number is computed by converting numbers of the form xx...x100..011..1 to
	 * xx..x011..1000..0
	 * This is accomplished in a similar fashion to finding the next largest number.
	 */
	int[] nextNumber(int x) {
		int vals[] = new int[2];
		int zeros = 0;
		int ones = 0;
		int tmp = x;

		// find trailing zeros
		while((tmp & 0x00000001) == 0) {
			zeros++;
			tmp = tmp >> 1;
		}

		// find size of preceding block of ones
		while((tmp & 0x00000001) == 1) {
			ones++;
			tmp = tmp >> 1;
		}

		vals[1] = x + (1 << zeros) + (1 << (ones-1)) - 1;	// calculate next larger

		zeros = 0;
		ones = 0;
		tmp = x;

		// find trailing ones
		while((tmp & 0x00000001) == 1) {
			ones++;
			tmp = tmp >> 1;
		}

		// find size of preceding block of ones
		while((tmp & 0x00000001) == 0) {
			zeros++;
			tmp = tmp >> 1;
		}

		vals[0] = x - (1 << ones) - (1 << (zeros-1)) + 1; // calculate next smaller

		return vals;
	}

	public static void main(String[] args) {
		BitManipulation test = new BitManipulation();

		int m = test.insertion(0x10000000, 0x10011, 2, 6);
		System.out.format("insertion(0x100000000, 0x10011, 2, 6) = 0x%x\n", m);

		String s = test.binaryToString(0.5);
		System.out.format("binaryToString(0.5) = %s\n", s);

		int maxbits = test.flipBitToWin(0x6ef);
		System.out.format("flipBitToWin(0x6ef) = %d\n", maxbits);

		int[] numbers = test.nextNumber(0x83);
		System.out.format("nextNumber(0x83) = 0x%x 0x%x\n", numbers[0], numbers[1]);
	}
} 