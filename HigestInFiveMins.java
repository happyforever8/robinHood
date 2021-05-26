First part was previously asked, but I didnt get much time to get into the the follow up question. Suggestions/approach?

Suppose we have an unsorted log file of accesses to web resources. Each log entry consists of an access time, 
the ID of the user making the access, and the resource ID.
The access time is represented as seconds since 00:00:00, and all times are assumed to be in the same day.

For example:
logs1 = [
["58523", "user_1", "resource_1"],
["62314", "user_2", "resource_2"],
["54001", "user_1", "resource_3"],
["200", "user_6", "resource_5"],
["215", "user_6", "resource_4"],
["54060", "user_2", "resource_3"],
["53760", "user_3", "resource_3"],
["58522", "user_22", "resource_1"],
["53651", "user_5", "resource_3"],
["2", "user_6", "resource_1"],
["100", "user_6", "resource_6"],
["400", "user_7", "resource_2"],
["100", "user_8", "resource_6"],
["54359", "user_1", "resource_3"],
]

Example 2:
logs2 = [
["300", "user_1", "resource_3"],
["599", "user_1", "resource_3"],
["900", "user_1", "resource_3"],
["1199", "user_1", "resource_3"],
["1200", "user_1", "resource_3"],
["1201", "user_1", "resource_3"],
["1202", "user_1", "resource_3"]
]

Write a function that takes the logs and returns the resource with the highest
number of accesses in any 5 minute window, together with how many accesses it saw.

Expected Output:
most_requested_resource(logs1) # => ('resource_3', 3)

Follow Up
Write a function that takes the logs as input, builds the transition graph and 
returns it as an adjacency list with probabilities. Add START and END states.

Specifically, for each resource, we want to compute a list of every possible 
next step taken by any user, together with the corresponding probabilities.
  The list of resources should include START but not END, since by definition END is a terminal state.

Expected output for logs1:
transition_graph(logs1) # =>
{
'START': {'resource_1': 0.25, 'resource_2': 0.125, 'resource_3': 0.5, 'resource_6': 0.125},
'resource_1': {'resource_6': 0.333, 'END': 0.667},
'resource_2': {'END': 1.0},
'resource_3': {'END': 0.4, 'resource_1': 0.2, 'resource_2': 0.2, 'resource_3': 0.2},
'resource_4': {'END': 1.0},
'resource_5': {'resource_4': 1.0},
'resource_6': {'END': 0.5, 'resource_5': 0.5}
}

For example, of 8 total users, 4 users have resource_3 as a first
visit (user_1, user_2, user_3, user_5), 2 users have resource_1 as a first visit (user_6, user_22),
1 user has resource_2 as a first visit (user_7), and 1 user has resource_6 (user_8) so the possible
 next steps for START are resource_3 with probability 4/8, resource_1 with probability 2/8, 
and resource_2 and resource_6 with probability 1/8.

These are the resource paths per user for the first logs example, ordered by access time:
{
'user_1': ['resource_3', 'resource_3', 'resource_1'],
'user_2': ['resource_3', 'resource_2'],
'user_3': ['resource_3'],
'user_5': ['resource_3'],
'user_6': ['resource_1', 'resource_6', 'resource_5', 'resource_4'],
'user_7': ['resource_2'],
'user_8': ['resource_6'],
'user_22': ['resource_1'],
}

Expected output for logs2:
transition_graph(logs2) # =>
{
'START': {'resource_3': 1.0},
'resource_3': {'resource_3: 0.857, 'END': 0.143}
}

===============================================================================
  class Solution {
  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("Hello, World!");
    strings.add("Welcome to CoderPad.");
    strings.add("This pad is running Java " + Runtime.version().feature());

    for (String string : strings) {
      System.out.println(string);
    }
    
     String [][]  logs = {
                {"58523", "user_1", "resource_1"},
                {"62314", "user_2", "resource_2"},
                {"54001", "user_1", "resource_3"},
                {"200", "user_6", "resource_5"},
                {"215", "user_6", "resource_4"},
                {"54060", "user_2", "resource_3"},
                {"53760", "user_3", "resource_3"},
                {"58522", "user_4", "resource_1"},
                {"53651", "user_5", "resource_3"},
                {"2", "user_6", "resource_1"},
                {"100", "user_6", "resource_6"},
                {"400", "user_7", "resource_2"},
                {"100", "user_8", "resource_2"},
                {"54359", "user_1", "resource_3"},
        };

        //userMinMaxTimestamp(logs);
        System.out.println("=========================");
        maxHitPageAndUser(logs);
    
  }
  
  public static void maxHitPageAndUser(String [][]  logs){
    Map<String, List<Integer>> map = new HashMap<>();
    
    
    for (String[] log : logs){
      int time = Integer.parseInt(log[0]);
      
      String resourceId = log[2];
      
      map.putIfAbsent(resourceId, new ArrayList<>());
      
      map.get(resourceId).add(time);
    }
    
    int max = Integer.MIN_VALUE;
    String result = "";
    
    
    for (Map.Entry<String,List<Integer>> entry : map.entrySet()){
      String resourceId = entry.getKey();
      
      List<Integer> timeList = entry.getValue();
      
      int currMax = helper(resourceId, timeList);
      
      if (max <= currMax){
        max = currMax;
        result = resourceId;
      }
    }
    
    System.out.println(result + " : " + max);
    
  }
  
  public static int helper(String resourceId, List<Integer> timeList){
    int maxValue = 1;
    
    Collections.sort(timeList);
    
    int windows = 300;
    
    if (timeList.size() > 1){
      int start = 0;
      int end = 1;
      
      while (start < end && end < timeList.size()){
        if (timeList.get(end) - timeList.get(start) <= windows){
          maxValue++;
          end++;
        } else {
          start++;
          end++;
      }
    }
   
  }
     return maxValue;
}
}
=======================================================================================================

Follow up
  
  第二问大概思路如下：
1. Build resource array (sorted by time) for each user
2. Traverse arrays above to count transition state from r1 -> r2 (r1 and r2 are resoruce tags, can be different or same)
3. Compute probability based on transition state count above.


	public Map<String, Map<String, Double>> getTransitionGraph(String[][] logs) {
		//UtilityHelper.printMatrix(logs);
		Arrays.sort(logs, new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				return Integer.valueOf(o1[0]) - Integer.valueOf(o2[0]);
			}
		});
		//UtilityHelper.printMatrix(logs);
		Map<String, List<String>> userToResourceMap = new HashMap<>();
		for (String[] log : logs) {
			String userId = log[1];
			String resourceName = log[2];
			if (!userToResourceMap.containsKey(userId)) {
				userToResourceMap.put(userId, new ArrayList<>());
			}
			userToResourceMap.get(userId).add(resourceName);
		}
		//System.out.println(userToResourceMap);
		Map<String, Map<String, Double>> adjMap = new HashMap<>();
		for (String userId : userToResourceMap.keySet()) {
			List<String> resourceList = userToResourceMap.get(userId);
			if (resourceList != null && !resourceList.isEmpty()) {
				String firstResource = resourceList.get(0);
				if (!adjMap.containsKey("START")) {
					adjMap.put("START", new HashMap<>());
				}
				Map<String, Double> tempStart = adjMap.get("START");
				tempStart.put(firstResource, tempStart.getOrDefault(firstResource, 0.0) + 1.0);
				for (int i = 1; i < resourceList.size(); i++) {
					String prevResource = resourceList.get(i - 1);
					String currResource = resourceList.get(i);
					if (!adjMap.containsKey(prevResource)) {
						adjMap.put(prevResource, new HashMap<>());
					}
					Map<String, Double> temp = adjMap.get(prevResource);
					temp.put(currResource, temp.getOrDefault(currResource, 0.0) + 1.0);
				}
				String lastResource = resourceList.get(resourceList.size() - 1);
				if (!adjMap.containsKey(lastResource)) {
					adjMap.put(lastResource, new HashMap<>());
				}
				Map<String, Double> tempEnd = adjMap.get(lastResource);
				tempEnd.put("END", tempEnd.getOrDefault("END", 0.0) + 1.0);
			}
		}
		//System.out.println(adjMap);
		for (String key : adjMap.keySet()) {
			Double total = 0.0;
			Map<String, Double> map = adjMap.get(key);
			for (String tempKey : map.keySet()) {
				total += map.get(tempKey);
			}
			for (String tempKey : map.keySet()) {
				map.put(tempKey, roundDoubleTo3Decimals(map.get(tempKey) / total));
			}
		}
		//System.out.println(adjMap);
		return adjMap;
	}

	private Double roundDoubleTo3Decimals(Double value) {
		return (double) Math.round(value * 1000d) / 1000d;
	}


