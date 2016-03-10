import scala.math

object Level1Code {
  // Returns "Hello World!"
  def helloWorld(): String = "Hello World!"

  // Return the sum of two numbers
  def sum(a:Int, b:Int): Int = a + b

  // Return the product of two numbers
  def product(a:Int, b:Int): Int = a * b

  // Return the square of a number
  def square(a:Int): Int = math.pow(a,2).toInt

  // return the cube of a number
  def cube(a:Int):Int = math.pow(a,3).toInt

  // return the Int quotient of a fraction
  def quotient(numerator:Int, denominator:Int):Int = numerator / denominator

  // return the Int remainder of a fraction
  def remainder(numerator:Int, denominator:Int):Int = numerator % denominator

  // return the square root of perfect squares only.
  def squareRootOfPerfectSquare(a:Int):Option[Int] = {
    val b = math.sqrt(a).toInt
    if (math.pow(b,2).toInt == a) Some(b) else None
  }

  // return an array containing the square of each number
  // in the source array in the same order
  def squareAll(as:Array[Int]): Array[Int] = as.map(x => square(x))

  // return an array containing the cube of each number
  // in the source array in the same order
  def cubeAll(as:Array[Int]): Array[Int] = as.map(x => cube(x))

  // return an array containing the product of an 'a' in array 'as' with
  // its respective 'b' in array 'bs'
  def productAll(as:Array[Int], bs:Array[Int]) : Array[Int] = {
    for ((a,b) <- (as zip bs)) yield a * b
  }

  // sum all of the elements in the array and return the result
  def sumAll(as:Array[Int]): Int = as.sum

  // reverse the array
  def reverse(as:Array[Int]): Array[Int] = as.reverse

}