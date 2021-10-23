// 一堆课，找出上课顺序里,中间的那门课，只有一种正确的顺序，并且每个课只会对应另外一个prereq
// 比如

//     ["Data Structures", "Algorithms"],
//     ["COBOL", "Networking"],
//     ["Algorithms", "COBOL"],

// "Data Structures"->"Algorithms" -> "COBOL"-> "Networking"

// 返回 “Algorithms". 
// time Complexity O(E + V). V is the number of course, E is number of depency,is the course length
// space complexity is the same

class Solution {
  public static void main(String[] args) {

    // String[][] courses =   {{"Data Structures", "Algorithms"},
    //                         {"COBOL", "Networking"},
    //                         {"Algorithms", "COBOL"}};
    
    String[][] courses =   {{"Data Structures", "A"}, {"A", "B"}};
    System.out.println(middleClass(courses));
  }
  

  
  public static String middleClass(String[][] courses){
    
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
    
    
    
    String[] coursesList =  topological(graph);
    
    if (coursesList.length % 2 == 0){
       return coursesList[coursesList.length / 2 - 1];
    
    } else {
      return coursesList[coursesList.length / 2];
    }
    
  }
  
  public static String[] topological(Map<String, Set<String>> graph){

    Map<String, Integer> degree = new HashMap<>();
    
    List<String> list = new ArrayList<>();
    
    
    for (String key : graph.keySet()){
       degree.put(key, 0);
    }
    
    for (String key : graph.keySet()){
      for (String next : graph.get(key)){
        degree.put(next, degree.get(next) + 1);
      }
    }
    
    Queue<String> queue = new LinkedList<>();
    
    for (String key : graph.keySet()){

      if ((int)degree.get(key) == 0){
        queue.offer(key);
      }
    }
    
    while (!queue.isEmpty()){
        String curr = queue.poll();
      
        list.add(curr);
      
        for (String next : graph.get(curr)){
          degree.put(next, degree.get(next) - 1);
          
          if (degree.get(next) == 0){
            queue.offer(next);
          }
        }
    }
    return list.toArray(new String[list.size()]);
      
    
  }
}


*********************

You are a developer for a university. Your current project is to develop a system for students to find courses they share with friends. The university has a system for querying courses students are enrolled in, returned as a list of (ID, course) pairs.

Write a function that takes in a list of (student ID number, course name) pairs and returns, for every pair of students, a list of all courses they share.

Sample Input:

student_course_pairs_1 = [
["58", "Linear Algebra"],
["94", "Art History"],
["94", "Operating Systems"],
["17", "Software Design"],
["58", "Mechanics"],
["58", "Economics"],
["17", "Linear Algebra"],
["17", "Political Science"],
["94", "Economics"],
["25", "Economics"],
["58", "Software Design"],
]

Sample Output (pseudocode, in any order):

find_pairs(student_course_pairs_1) =>
{
[58, 17]: ["Software Design", "Linear Algebra"]
[58, 94]: ["Economics"]
[58, 25]: ["Economics"]
[94, 25]: ["Economics"]
[17, 94]: []
[17, 25]: []
}



import java.util.*;
public class HelloWorld{

     public static void main(String []args){
        System.out.println("Hello World");
        
        String[][] student_course_pairs_1 = {{"58", "Linear Algebra"},
{"94", "Art History"},
{"94", "Operating Systems"},
{"17", "Software Design"},
{"58", "Mechanics"},
{"58", "Economics"},
{"17", "Linear Algebra"},
{"17", "Political Science"},
{"94", "Economics"},
{"25", "Economics"},
{"58", "Software Design"}};


// [58, 17]: ["Software Design", "Linear Algebra"]
// [58, 94]: ["Economics"]
// [58, 25]: ["Economics"]
// [94, 25]: ["Economics"]
// [17, 94]: []
// [17, 25]: []
// }



     Map<int[], List<String>> result = getPossiblePairs(student_course_pairs_1);
     
     for (Map.Entry<int[], List<String>> entry : result.entrySet()){
         for (int str : entry.getKey()){
             System.out.println(str);
         }
         for (String s : entry.getValue()){
             System.out.println(s);
         }
         System.out.println("=========");
     }
     }
  public static Map<int[], List<String>> getPossiblePairs(String[][] input){
     
       Map<int[], List<String>> result = new HashMap();
       
       // id to name
        Map<Integer, Set<String>> adjList = new HashMap();
        // students id
        List<Integer> studentIds = new ArrayList();
        for(String[] course : input){
            int studentId = Integer.parseInt(course[0]);
            if(!adjList.containsKey(studentId)) studentIds.add(studentId);
            adjList.putIfAbsent(studentId, new HashSet());
            adjList.get(studentId).add(course[1]);
        }
        for(int i = 0; i < studentIds.size(); i++){
            int curr = studentIds.get(i);
            for(int j = i + 1; j < studentIds.size(); j++){
                List<String> commonCourses = findCommon(studentIds.get(j), curr, adjList);
                result.put(new int[]{curr, studentIds.get(j)}, commonCourses);
            }
        }
        return result;
    }

    public static List<String> findCommon(int id1, int id2, Map<Integer, Set<String>> adjMap){
        Set<String> student1 = adjMap.get(id1);
        Set<String> student2 = adjMap.get(id2);
        List<String> common = new ArrayList();
        for(String course : student1){
            if(student2.contains(course )){
                common.add(course);
            }
        }
        return common;
    }

}
