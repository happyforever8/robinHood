First part was previously asked, but I didnt get much time to get into the the follow up question. Suggestions/approach?

Suppose we have an unsorted log file of accesses to web resources.
Each log entry consists of an access time, the ID of the user making the access, and the resource ID.
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

Write a function that takes the logs and returns the resource with the highest number of
accesses in any 5 minute window, together with how many accesses it saw.

Expected Output:
most_requested_resource(logs1) # => ('resource_3', 3)
  
  
Follow-up:
Write a function that takes the logs and returns the resource with the highest number of accesses in any 5 minute window, together with how many accesses it saw.
Example:
('resource_3', 3)





public class WebLog {

    public static void userMinMaxTimestamp(String [][] logs){
        Map<String, List<Integer>> map = new HashMap<>();

        for(String [] log : logs){
            Integer time = Integer.parseInt(log[0]);
            String userId = log[1];
            if(map.containsKey(userId)){
                map.get(userId).add(time);
            } else {
                List<Integer> timestampList = new ArrayList<>();
                timestampList.add(time);
                map.put(userId,timestampList);
            }
        }

        for(Map.Entry<String, List<Integer>> entry : map.entrySet()){
           String  userId = entry.getKey();
           List<Integer> timestampList = entry.getValue();
            timestampList.sort(Comparator.naturalOrder());
           int min = timestampList.get(0);
           int max = timestampList.get(timestampList.size()-1);

            System.out.println(userId +": [ "+min+","+max+" ]");
        }

    }

    public static void maxHitPageAndUser(String [][] logs){
        Map<String, List<Integer>> map = new HashMap<>();

        for(String [] log : logs){
            Integer time = Integer.parseInt(log[0]);
            String resourceId = log[2];
            if(map.containsKey(resourceId)){
                map.get(resourceId).add(time);
            } else {
                List<Integer> timestampList = new ArrayList<>();
                timestampList.add(time);
                map.put(resourceId,timestampList);
            }
        }

        Integer max = Integer.MIN_VALUE;
        String resource = null;
        for(Map.Entry<String, List<Integer>> entry : map.entrySet()){
            String  resourceId = entry.getKey();
            List<Integer> timestampList = entry.getValue();
            int localMax = getMaxHitInLast5MinWindow(resourceId,timestampList,5);
            if(max <= localMax){
                max = localMax;
                resource = resourceId;
            }
        }

        System.out.println(resource+" : "+max);
    }

    public static int getMaxHitInLast5MinWindow(String resourceId,List<Integer> timestampList, int windowInMinutes){
        int max = 1;
        int windowInSeconds = 60 * windowInMinutes;
        timestampList.sort(Comparator.naturalOrder());
        if(timestampList.size()>1){
            int start = 0;
            int end = 1;

            while(start< end && end< timestampList.size()){
                if(timestampList.get(end)-timestampList.get(start)<=windowInSeconds){
                    max++;
                    end++;
                } else {
                    start++;
                    end++;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
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

        userMinMaxTimestamp(logs);
        System.out.println("=========================");
        maxHitPageAndUser(logs);
    }
}






