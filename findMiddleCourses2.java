/*
有多种track，返回所有path里的中间那门课
Students may decide to take different "tracks" or sequences of courses
in the Computer Science curriculum. There may be more than one track 
that includes the same course, but each student follows a single linear 
track from a "root" node to a "leaf" node. In the graph below, their path always moves left to right.

Write a function that takes a list of (source, destination) pairs,
and returns the name of all of the courses that the students could be taking when they are halfway through their track of courses.


作者：关辰晓
链接：https://www.jianshu.com/p/fdbcba5fe5bc
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。


Sample input 1:
all_courses_1 = [
    ["Logic", "COBOL"],
    ["Data Structures", "Algorithms"],
    ["Creative Writing", "Data Structures"],
    ["Algorithms", "COBOL"],
    ["Intro to Computer Science", "Data Structures"],
    ["Logic", "Compilers"],
    ["Data Structures", "Logic"],
    ["Graphics", "Networking"],
    ["Networking", "Algorithms"],
    ["Creative Writing", "System Administration"],
    ["Databases", "System Administration"],
    ["Creative Writing", "Databases"],
    ["Intro to Computer Science", "Graphics"],
]

Sample output 1 (in any order):
          ["Data Structures", "Networking", "Creative Writing", "Databases"]

All paths through the curriculum (midpoint *highlighted*):

Intro to C.S. -> Graphics -> *Networking* -> Algorithms -> Cobol
Intro to C.S. -> *Data Structures* -> Algorithms -> COBOL
Intro to C.S. -> *Data Structures* -> Logic -> COBOL
Intro to C.S. -> *Data Structures* -> Logic -> Compiler
Creative Writing -> *Databases* -> System Administration
*Creative Writing* -> System Administration
Creative Writing -> *Data Structures* -> Algorithms -> COBOL
Creative Writing -> *Data Structures* -> Logic -> COBOL
Creative Writing -> *Data Structures* -> Logic -> Compilers

Visual representation:

                    ____________    ______________
                    |          |    |            |
                    | Graphics |    | Networking |
               ---->|__________|--->|____________|
               |                       |      
               |                       |      
               |                       |  ______________
____________   |                       |  |            |
|          |   |    ______________     >->| Algorithms |--\     _____________
| Intro to |   |    |            |    /   |____________|   \    |           |
| C.S.     |---+    | Data       |   /                      >-->| COBOL     |
|__________|    \   | Structures |--+     ______________   /    |___________|
                 >->|____________|   \    |            |  /
____________    /                     \-->| Logic      |-+      _____________
|          |   /    ______________        |____________|  \     |           |
| Creative |  /     |            |                         \--->| Compilers |
| Writing  |-+----->| Databases  |                              |___________|
|__________|  \     |____________|-\     _________________________
               \                    \    |                       |
                \--------------------+-->| System Administration |
                                         |_______________________|

Sample input 2:
all_courses_2 = [
    ["Course_3", "Course_7"],
    ["Course_0", "Course_1"],
    ["Course_1", "Course_2"],
    ["Course_2", "Course_3"],
    ["Course_3", "Course_4"],
    ["Course_4", "Course_5"],
    ["Course_5", "Course_6"],
]
Sample output 2 (in any order):
["Course_2", "Course_3"]

Complexity analysis variables:

n: number of pairs in the input

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
    
    
    String[][] courses = {{"Logic", "COBOL"},
    {"Data Structures", "Algorithms"},
    {"Creative Writing", "Data Structures"},
    {"Algorithms", "COBOL"},
    {"Intro to Computer Science", "Data Structures"},
    {"Logic", "Compilers"},
    {"Data Structures", "Logic"},
    {"Graphics", "Networking"},
    {"Networking", "Algorithms"},
    {"Creative Writing", "System Administration"},
    {"Databases", "System Administration"},
    {"Creative Writing", "Databases"},
    {"Intro to Computer Science", "Graphics"}};
    
    
      Map<String, Set<String>> graph = buildGraph(courses);
      Map<String, Integer> degree = new HashMap<>();

      List<String> zeroDegree = findZeroDegree(graph, degree);


      List<List<String>> courseGraph = new ArrayList<>();

      for (String next : zeroDegree){
        List<String> path = new ArrayList<>();
        path.add(next);
        
        backTrack(courseGraph, path, graph, zeroDegree, next, new HashSet<>());
      } 


      List<String> result = new ArrayList<>();

      for (List<String> list : courseGraph){
        String[] arr = list.toArray(new String[list.size()]);

        for (String s : arr){
          System.out.print("****" + s + "***");
          
 
        }
        
         System.out.println("===========");
        if (arr.length % 2 == 0){

          result.add(arr[arr.length / 2 - 1]);
        } else {
          result.add(arr[arr.length / 2]);
        }


      }          
       for (String next : result){
          System.out.println(next);
       }
  
  }
  
  
  public static void backTrack(List<List<String>> courseGraph, List<String> list, Map<String, Set<String>> graph,
                        List<String>  zeroDegree, String str, Set<String> set){
     
      if (graph.get(str).size() == 0){
        
         courseGraph.add(new ArrayList<>(list));
        return;
      }
      set.add(str);
      for (String next : graph.get(str)){ 
        if (set.add(next)){
          list.add(next);
          backTrack(courseGraph, list, graph, zeroDegree, next, set);
          list.remove(list.size() - 1);
        }
      }
      set.remove(str);
  }
  
    public static Map<String, Set<String>> buildGraph(String[][] courses){
      Map<String, Set<String>> graph = new HashMap<>();

       for (int i = 0; i < courses.length; i++){
        for (int j = 0; j < courses[i].length; j++){
          graph.putIfAbsent(courses[i][j], new HashSet<>());
        }
      }

      for (int i = 0; i < courses.length; i++){
        for (int j = 1; j < courses[i].length; j++){
          graph.get(courses[i][0]).add(courses[i][j]);
        }
      }
    
      return graph;

    }

    public static List<String> findZeroDegree(Map<String, Set<String>> graph, Map<String, Integer> degree){


      List<String> list = new ArrayList<>();

      for (String key : graph.keySet()){
        degree.put(key, 0);
      }

      for (String key: graph.keySet()){
        for (String next : graph.get(key)){
          degree.put(next, degree.getOrDefault(next, 0) + 1);
        }
      }

      for (String key : graph.keySet()){
        if (degree.get(key) == 0){
          list.add(key);
        }
      }
      return list;
    }
}

