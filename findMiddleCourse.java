// 一堆课，找出上课顺序里,中间的那门课，只有一种正确的顺序，并且每个课只会对应另外一个prereq
// 比如

//     ["Data Structures", "Algorithms"],
//     ["COBOL", "Networking"],
//     ["Algorithms", "COBOL"],

// "Data Structures"->"Algorithms" -> "COBOL"-> "Networking"

// 返回 “Algorithms"


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