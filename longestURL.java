Coding:
We have some clickstream data that we gathered on our client's website. Using cookies,
we collected snippets of users' anonymized URL histories while they browsed the site.
The histories are in chronological order, and no URL was visited more than once per person.

Write a function that takes two users' browsing histories as input and returns the longest contiguous sequence of URLs that appears in both.

Sample input:

user0 = ["/start", "/green", "/blue", "/pink", "/register", "/orange", "/one/two"]
user1 = ["/start", "/pink", "/register", "/orange", "/red", "a"]
user2 = ["a", "/one", "/two"]
user3 = ["/pink", "/orange", "/yellow", "/plum", "/blue", "/tan", "/red", "/amber", "/HotRodPink", "/CornflowerBlue", "/LightGoldenRodYellow", "/BritishRacingGreen"]
user4 = ["/pink", "/orange", "/amber", "/BritishRacingGreen", "/plum", "/blue", "/tan", "/red", "/lavender", "/HotRodPink", "/CornflowerBlue", "/LightGoldenRodYellow"]
user5 = ["a"]
user6 = ["/pink","/orange","/six","/plum","/seven","/tan","/red", "/amber"]

Sample output:

findContiguousHistory(user0, user1) => ["/pink", "/register", "/orange"]
findContiguousHistory(user0, user2) => [] (empty)
findContiguousHistory(user2, user1) => ["a"]
findContiguousHistory(user5, user2) => ["a"]
findContiguousHistory(user3, user4) => ["/plum", "/blue", "/tan", "/red"]
findContiguousHistory(user4, user3) => ["/plum", "/blue", "/tan", "/red"]
findContiguousHistory(user3, user6) => ["/tan", "/red", "/amber"]



/*
 * Click `Run` to execute the snippet below!
 */

import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("Hello, World!");
    strings.add("Welcome to CoderPad.");
    strings.add("This pad is running Java " + Runtime.version().feature());

    for (String string : strings) {
      System.out.println(string);
    }
    
    
    
    String[] user0 = {"/start", "/green", "/blue", "/pink", "/register", "/orange", "/one/two"};
String[] user1 = {"/start", "/pink", "/register", "/orange", "/red", "a"};
String[] user2 = {"a", "/one", "/two"};
String[] user3 = {"/pink", "/orange", "/yellow", "/plum", "/blue", "/tan", "/red", "/amber", "/HotRodPink", "/CornflowerBlue", "/LightGoldenRodYellow", "/BritishRacingGreen"};
String[] user4 = {"/pink", "/orange", "/amber", "/BritishRacingGreen", "/plum", "/blue", "/tan", "/red", "/lavender", "/HotRodPink", "/CornflowerBlue", "/LightGoldenRodYellow"};
String[] user5 = {"a"};
String[] user6 = {"/pink","/orange","/six","/plum","/seven","/tan","/red", "/amber"};
    
    
    LCS(user5, user2);
  }
  
  public static List<String> LCS(String[] str1, String[] str2){
    int m = str1.length;
    int n = str2.length;
    int LCStuff[][] = new int[m + 1][n + 1];
       
    List<String> list = new ArrayList<>();
    
        // To store length of the longest
        // common substring
        int result = 0;
    
        int row = 0;
        int col = 0;
    
        int len = 0;
 
        // Following steps build
        // LCSuff[m+1][n+1] in bottom up fashion
        for (int i = 0; i <= m; i++)
        {
            for (int j = 0; j <= n; j++)
            {
                if (i == 0 || j == 0){
                   LCStuff[i][j] = 0;
                } else if (str1[i - 1].equals(str2[j - 1])){
                    LCStuff[i][j]= LCStuff[i - 1][j - 1] + 1;
                  
                    if (len < LCStuff[i][j]){
                        len = LCStuff[i][j];
                        row = i;
                        col = j;
                    }
                }else {
                    LCStuff[i][j] = 0;
                }
 
            }
        }
       
    while ( LCStuff[row][col] != 0){
      list.add(str1[row - 1]);
      len--;
      row--;
      col--;
    }
    
    for (String str : list){
      
      System.out.println(str + "----");
    }
    return list;
    
    
  }
}

====================================================================================================
def findContiguousHistory(X, Y):
    maxLength = 0
    res = []
    counter = [ [0] * (len(Y) + 1) for _ in range(len(X)+1)]
    for i in range(len(Y)):
        for j in range(len(X)):
            if X[j] == Y[i]:
                newLength = counter[j][i] + 1
                if maxLength < newLength:
                    maxLength = newLength
                    res = X[j - newLength + 1:j + 1]
                counter[j + 1][i + 1] = newLength
    return res
