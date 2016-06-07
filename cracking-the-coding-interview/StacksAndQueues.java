import java.util.Arrays;
import java.util.EmptyStackException;

class StacksAndQueues {

	/**
	 * The MultiStack class implements multiple stacks (substacks) with a 
	 * single array. The array is partitioned into equal parts, one for each 
	 * substack.  Stacks grow from min to max offset.  An offset of -1 indicates 
	 * an empty stack.
	 */
	static class MultiStack {
		int[] stacks;			// stack values
		int[] bases;			// index of base of substack
		int[] offsets;		// offset of top of substack
		int stack_count;	// number of stacks
		int stack_size;		// size of each stack
		
		public MultiStack(int count, int size) {
			stack_count = count;
			stack_size = size;
			stacks = new int[count*size];
			bases = new int[count];
			for (int i=0; i<count; i++) bases[i] = i * size;
			offsets = new int[count];
			Arrays.fill(offsets, -1);
		}

		/**
		 * Return true if stack is empty, else false.
		 */
		public Boolean isEmpty(int num) {
			if (offsets[num] == -1) return true;
			else return false;
		}

		/**
		 * Push 'val' on stack number 'num', where 'num' is zero based.
		 * Return true if push was successful, otherwise false.
		 */
		public Boolean push(int num, int val) {
			if(offsets[num]== stack_size-1)	return false;	// stack is full
			offsets[num]++;
			stacks[bases[num]+offsets[num]] = val;
			return true;
		}

		/**
		 * Pop top entry from stack and return value.  If stack is empty,
		 * throw an exception.
		 */
		public int pop(int num) throws EmptyStackException {
			if(isEmpty(num)) throw new EmptyStackException();
			int val = stacks[bases[num]+offsets[num]];
			offsets[num]--;
			return val;
		}

		/**
		 * Peek at top entry of stack, returning value, but keeping on stack.
		 */
		public int peek(int num) throws EmptyStackException {
			if(isEmpty(num)) throw new EmptyStackException();
			return stacks[bases[num]+offsets[num]];
		}
	}

	public static void main(String[] args) {
		Boolean success;
		int val=-1;
		MultiStack stacks = new MultiStack(3,4);

		success = stacks.push(0,0);
		System.out.println("push(0,0) = " + val);
		success = stacks.push(0,1);
		System.out.println("push(0,0) = " + val);
		success = stacks.push(2,20);
		System.out.println("push(0,0) = " + val);
		success = stacks.push(2,21);
		System.out.println("push(0,0) = " + val);

		try {
			val = stacks.pop(2);
		} catch (EmptyStackException e) {
			System.out.println("pop(2) Exception");
		} 
		System.out.println("pop(2) = " + val);

		try {
			val = stacks.peek(0);
		} catch (EmptyStackException e) {
			System.out.println("peek(0) Exception");
		} 
		System.out.println("peek(0) = " + val);
	}
}