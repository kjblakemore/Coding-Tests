import java.lang.String;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

public class SortingAndSearching {

  /** Question 10.1
   * Given two sorted arrays, a and b, merge the two arrays in sorted order,
   * returning result in A which has been padded to hold B.
   */
  void sortedMerge(int[] a, int lengthA, int[] b, int lengthB) {
    int indexA = lengthA - 1;
    int indexB = lengthB - 1;
    int lengthResult = lengthA + lengthB;
    for(int i = lengthResult-1; i>=0; i--) {
      int aValue = indexA >= 0 ? a[indexA] : Integer.MIN_VALUE;
      int bValue = indexB >= 0 ? b[indexB] : Integer.MIN_VALUE;
      if(aValue > bValue) {
        a[i] = aValue;
        indexA--;
      } else {
        a[i] = bValue;
        indexB--;
      }
    }
  }

  /* Question 10.2
   * Sort an array of strings, so that all anagrams are close to each other.
   */
  void groupAnagrams(String[] arr) {
    HashMap<String, ArrayList<String>> stringMap = new HashMap<String, ArrayList<String>>();

    // Group strings by sorted values.
    for(String str: arr) {
      char chars[] = str.toCharArray();
      Arrays.sort(chars);
      String key = String.valueOf(chars);

      ArrayList<String> val;
      if(stringMap.containsKey(key))
        val = stringMap.get(key);
      else {
        val = new ArrayList<String>();
      }
      val.add(str);
      stringMap.put(key, val);
    }

    // Return array of strings grouped by sorted values.
    int i = 0;
    for(ArrayList<String> strs: stringMap.values()) {
      for(String str: strs)
        arr[i++] = str;
    }
  }

  public static void main(String args[]) {
    SortingAndSearching test = new SortingAndSearching();

    int a[] = {1,2,3,4,6,7,8,0,0,0,0};
    int b[] = {-1,5,9,11};
    test.sortedMerge(a, 7, b, 4);
    System.out.println("sortedMerge({1,2,3,4,6,7,8},{-1,5,9,11}) = ");
    for(int x: a)
      System.out.println(x);

    String[] strs = {"oodg", "hello", "yeb", "good", "bye", "doog"};
    test.groupAnagrams(strs);
    System.out.println("groupAnagrams(oodg, hello, yeb, good, bye, doog) = ");
    for(String str: strs)
      System.out.println(str);
  }
}