public class Code {

    // Returns "Hello World!"
    public static String helloWorld() {
        return "Hello World!";
    }

    // Take a single-spaced <sentence>, and capitalize every <n> word starting with <offset>.
    public static String capitalizeEveryNthWord(String sentence, Integer offset, Integer n) {
        String newSentence = new String();
        String[] words = sentence.split(" ");

        if(sentence==null)
            throw new IllegalArgumentException("null sentence string");
        if(offset < 0 || offset > sentence.length())
            throw new IllegalArgumentException("invalid offset");
        if(n < 0 || n > words.length)
            throw new IllegalArgumentException("invalid count");

        for(int i=0; i<offset; i++) {
            newSentence += words[i] + " ";
        }

        for(int i=offset; i<words.length; i++) {
            if((i % n) == 0) {
                newSentence += words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
            } else {
                newSentence += words[i];
            }
            if(i != words.length-1) {
                newSentence += " ";
            }
        }

        return newSentence;
    }

    // Determine if a number is prime
    public static Boolean isPrime(Integer n) {
        if(n <= 1) return false;

        if(n <= 3) return true;
        
        if(n % 2 == 0 || n % 3 == 0) return false;

        // Can reduce number of divisors to test by noting that:
        // 1. Only need to check up to the sqrt(n)
        // 2. All numbers can be represented as 6 * k + i for i = -1,0,...,4
        // 3. Since we have already checked for divisibility by 2 & 3, need only consider
        //    divisors of the from 6 * k +/- 1 for k >= 1
        for(int i=5; i*i <= n; i += 6)
            if(n % i == 0 || n % (i+2) == 0) return false;
        
        return true;
    }


    // Calculate the golden ratio.
    // Given two numbers a and b with a > b > 0, the ratio is b / a.
    // Let c = a + b, then the ratio c / b is closer to the golden ratio.
    // Let d = b + c, then the ratio d / c is closer to the golden ratio.
    // Let e = c + d, then the ratio e / d is closer to the golden ratio.
    // If you continue this process, the result will trend towards the golden ratio.
    public static Double goldenRatio(Double a, Double b) {
        if(a <= 0 || b <= 0) throw new IllegalArgumentException("negative arguments");

        Double EPSILON = 1e-5;
        Double goldenRatio1 = b/a;
        Double tmp = b;
        b = a + b;
        a = tmp;
        Double goldenRatio2 = b/a;

        while(Math.abs(goldenRatio1 - goldenRatio2) > EPSILON) {
            goldenRatio1 = goldenRatio2;
            tmp = b;
            b = a + b;
            a = tmp;
            goldenRatio2 = b/a;

        }
        return goldenRatio2;
    }

    // Give the nth Fibionacci number
    // Starting with 1 and 1, a Fibionacci number is the sum of the previous two.
    public static Integer fibionacci(Integer n) {
        if(n < 0) throw new IllegalArgumentException("negative argument");

        if(n == 0) return 0;

        int a = 1, b = 1;
        for (int i = 3; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }           
        return b;
    }

    // Give the square root of a number
    // Using a binary search algorithm, search for the square root of a given number.
    // Do not use the built-in square root function.
    public static Double squareRoot(Double n) {
        if(n < 0) throw new IllegalArgumentException("negative argument");

        Double EPSILON = 1e-4;
        Double start = 0.0;
        Double end = n;
        Double mid;

        while (end - start > EPSILON) {
            mid = (start + end) / 2;
            if (mid * mid <= n) start = mid;
            else end = mid;
        }
        return Math.floor(end * 10000)/10000;   // truncate to 4 digits right of decimal pt.
    }
}
