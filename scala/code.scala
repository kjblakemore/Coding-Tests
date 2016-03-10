object Code {
    // Returns "Hello World!"
    def helloWorld() : String = "Hello World!"

    // Take a single-spaced <sentence>, and capitalize every <n> word starting with <offset>.
    def capitalizeEveryNthWord(sentence:String, offset:Integer, n:Integer) : String = {
        val words = sentence.split(" ").zipWithIndex
        words.map {
            case (word,i) if i < offset     => word
            case (word,i) if i % n == 0     => word.capitalize
            case (word,i)                   => word
        }.mkString(" ")
    }
    
    // Determine if a number is prime
    def isPrime(n:Integer) : Boolean = {
        if (n <= 1) return false
        
        if (n <= 3) return true
        
        if (n % 2 == 0 || n % 3 == 0) return false
        
        // Can reduce number of divisors to test by noting that:
        // 1. Only need to check up to the sqrt(n)
        // 2. All numbers can be represented as 6 * k + i for i = -1,0,...,4
        // 3. Since we have already checked for divisibility by 2 & 3, need only consider
        //    divisors of the from 6 * k +/- 1 for k >= 1
        
        val nums = Stream.from(5).takeWhile(i => i*i <= n)
        val divisor = nums.find(i => n % i == 0 || n % (i+2) == 0)
        return divisor == None
    }
    
    // Calculate the golden ratio.
    // Given two numbers a and b with a > b > 0, the ratio is b / a.
    // Let c = a + b, then the ratio c / b is closer to the golden ratio.
    // Let d = b + c, then the ratio d / c is closer to the golden ratio. 
    // Let e = c + d, then the ratio e / d is closer to the golden ratio.
    // If you continue this process, the result will trend towards the golden ratio.
    def goldenRatio(a:Double, b:Double) : Double = {
        // Create a stream representing the sequence:
        // b/a, (a+b)/b, (a+(a+b))/(a+b), ((a+b)+(a+(a+b)))/(a+(a+b))...
        // ratios are represented as (denominator, numerator) pairs
        def ratios(r: (Double,Double)): Stream[(Double,Double)] = 
            (r._1,r._2) #:: ratios((r._2,r._1+r._2))

        throw new Exception("Not Implemented")
    }

    // Give the nth Fibonacci number
    // Starting with 0, 1, 1, 2, ... a Fibonacci number is the sum of the previous two.
    def fibonacci(n:Integer) : Integer = {
        def fibFrom(a: Integer, b: Integer): Stream[Int] = a #:: fibFrom(b, a + b)

        fibFrom(0,1).take(n+1).toList.last
    }
    
    // Give the square root of a number
    // Using a binary search algorithm, search for the square root of a given number.
    // Do not use the built-in square root function.
    def squareRoot(n:Double) : Double = {
        throw new Exception("Not Implemented");
    }
}
