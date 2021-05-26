more than 3 times within one hour

给 list of [name, time], time is string format: '1300' //下午一点
return: list of names and the times where their swipe badges within one hour. 
if there are multiple intervals that satisfy the condition, return any one of them.
name1: time1, time2, time3...
name2: time1, time2, time3, time4, time5...
example:
input: [['James', '1300'],
        ['Martha', '1600'], 
        ['Martha', '1620'],
        ['Martha', '1530']] 
output: {
'Martha': ['1600', '1620', '1530']
}


class Solution {
  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("Hello, World!");
    strings.add("Welcome to CoderPad.");
    strings.add("This pad is running Java " + Runtime.version().feature());

    for (String string : strings) {
      System.out.println(string);
    }
    
    
    String[][] records = {{"Jame", "1300"}, {"Martha", "1600"}, {"Martha", "1620"}, {"Martha", "1530"}}; 
    
    
    String[][] records1 = {
{"Paul", "1355"},
{"Jennifer", "1910"},
{"John", "830"},
{"Paul", "1315"},
{"John", "1615"},
{"John", "1640"},
{"John", "835"},
{"Paul", "1405"},
{"John", "855"},
{"John", "930"},
{"John", "915"},
{"John", "730"},
{"Jennifer", "1335"},
{"Jennifer", "730"},
{"John", "1630"},
};
    frequentAccess(records1);
    
  }
  
  public static Map<String, List<String>> frequentAccess(String[][] records){
    Map<String, List<String>> result = new HashMap<>();
    
    if (records == null || records.length == 0){
      return result;
    }
    
    Map<String, List<String>> map = new HashMap<>();
    
    for (String[] record : records){
        
        map.putIfAbsent(record[0], new ArrayList<>());
        map.get(record[0]).add(record[1]);
    }
    
    for (Map.Entry<String,List<String>> entry : map.entrySet()){
      List<String> timeList = entry.getValue();
      
      Collections.sort(timeList);
      
      int start = 0;
      
      
      List<String> curr = new ArrayList<>();
      
      curr.add(timeList.get(start));
      
      for (int end = 1; end < timeList.size(); end++){
        if (timeDiff(timeList.get(start), timeList.get(end))){
          
            curr.add(timeList.get(end));
 
        } else {
           curr.clear();
           curr.add(timeList.get(end));
           start = end;
        }
      }
      
      if (curr.size() >= 3){
        result.put(entry.getKey(), curr);
        System.out.print(entry.getKey() + ":");
        
        for (String str : curr){
         System.out.print(str + " ");
        }
      }
    }
    
    return result;
  }
  
  public static boolean timeDiff(String time1, String time2){
    int t1 = Integer.parseInt(time1);
    int t2 = Integer.parseInt(time2);
    
    return Math.abs(t1 - t2) <= 100;
  }
}
============================================================================================================================================================================

第二题是 largest group (大家找一下以前的帖子看具体题，我这里就贴一下code）注意 输入不是sorted 的


//    {"Curtis",    "745", "enter"}
    //    {"Jennifer",  "800", "enter"}, 
    //    {"Curtis",    "810", "exit"},
    //    {"John",      "815", "enter"},
    //    {"Paul",      "830", "enter"}, 
    //    {"Jennifer",  "900", "exit"},
    //    {"Paul",      "903", "exit"},
    //    {"John",      "908", "exit"},
    //    {"Curtis",   "1100", "enter"},
    //    {"Paul",     "1214", "enter"},
    //    {"Jennifer", "1217", "enter"},
    //    {"John",     "1230", "enter"},
    //    {"Paul",     "1235", "exit"},
    //    {"John",     "1235", "exit"},
    //    {"Jennifer", "1240", "exit"},
    //    {"Curtis",   "1330", "exit"},
// }
 
    // [Jennifer, John, Paul],[830, 900],[1230, 1235]
    static List<List<String>> getLargestGroup(String[][] records) {
        List<String[]> recordList = new ArrayList<>();
        for (String[] record : records) {
            recordList.add(record);
        }
        recordList.sort(new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                String t1 = o1[1];
                String t2 = o2[1];
                if (t1.length() != t2.length()) {
                    return t1.length()-t2.length();
                }
                return t1.compareTo(t2);
            }
        });
 
        List<Set<String>> groups = new ArrayList<>();
        List<String> times = new ArrayList<>();
        Set<String> currGroup = new HashSet<>();
        for (int i = 0; i < recordList.size();) {
            String[] record = recordList.get(i);
            String time = record[1];
            String action = record[2];
 
            int j = i;
            while (action.equals("enter") && j < recordList.size() && recordList.get(j)[1].equals(time)
                    && recordList.get(j)[2].equals(action)) {
                currGroup.add(recordList.get(j)[0]);
                j++;
            }
 
            while (action.equals("exit") && j < recordList.size() && recordList.get(j)[1].equals(time)
                    && recordList.get(j)[2].equals(action)) {
                currGroup.remove(recordList.get(j)[0]);
                j++;
            }
            i = j;
 
            groups.add(new HashSet<>(currGroup));
            times.add(time);
        }
 
        int size = 0;
        Set<String> retGroup = new HashSet<>();
        List<List<String>> retTime = new ArrayList<>();
        for (int i = 0; i < groups.size()-2; i++) {
            for (int j = i+1; j < groups.size()-1; j++) {
                Set<String> tmp = new HashSet<>(groups.get(i));
                tmp.retainAll(groups.get(j));
                if (tmp.size() > size) {
                    retGroup = tmp;
                    size = tmp.size();
                    retTime.clear();
                    retTime.add(new ArrayList<>(Arrays.asList(times.get(i), times.get(i+1))));
                    retTime.add(new ArrayList<>(Arrays.asList(times.get(j), times.get(j+1))));
                }
            }
        }
 
        List<List<String>> ret = new ArrayList<>();
        ret.add(new ArrayList<>(retGroup));
        ret.add(new ArrayList<>(retTime.get(0)));
        ret.add(new ArrayList<>(retTime.get(1)));
 
        return ret;
    }
