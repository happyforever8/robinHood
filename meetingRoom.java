class Solution {
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        PriorityQueue<Interval> pq = new PriorityQueue<>((a, b) -> a.start - b.start);
        
        schedule.forEach(e -> pq.addAll(e));
        List<Interval> result = new ArrayList<>();
        
        Interval prev = pq.poll();
        
        while (!pq.isEmpty()){
            Interval curr = pq.poll();
            
            if (prev.end < curr.start){
                result.add(new Interval(prev.end, curr.start));
                prev = curr;
            } else {
                prev = prev.end < curr.end ? curr : prev;
            }
        }
        return result;
    }
}
===============================================================================================================
  Input: int[][] meetings, int start, int end
(e.g 13:00 => 1300, 9:30 => 930)

Output: boolean, whether the new time could be scheduled as new meetings

Sample:

{[1300,1500],[930,1200],[830,845]},new meeting [820,830], return true, [1450,1500] return false
  
  
public static boolean meetingRooms1(int[][] meetings, int start, int end){
    for(int[] meeting : meetings){
        if((meeting[0] <= start && meeting[1] > start) || (meeting[0] < end && meeting[1] >= end)) return false;
    }
    return true;
}
===============================================================================================================
.2 Similar to Merge Intervals(LeetCode 56), but the output is different, 
now you are required to output idle time after time intervals merged, notice also output 0 - first start time.

Java Solution: O(nlogn) time dominated by .sort, O(n) space. 
  Thank god I finally cut the bs and get it done, linkedlist is a good friend.
    
    public static List<List<Integer>> meetingRooms2(int[][] intervals){
    List<List<Integer>> ans = new ArrayList<>();
    Arrays.sort(intervals, (a,b) -> Integer.compare(a[0], b[0]));
    LinkedList<int[]> cur = new LinkedList<>();
    for(int[] pair : intervals){
        if(cur.isEmpty() || cur.getLast()[1] < pair[0]) cur.add(pair);
        else{
            cur.getLast()[1] = Math.max(cur.getLast()[1], pair[1]);
        }
    }
    List<Integer> Start_interval = new ArrayList<>();
    Start_interval.add(0);
    Start_interval.add(cur.get(0)[0]);
    ans.add(Start_interval);
    for(int i = 0; i < cur.size() - 1; i++){
        List<Integer> cur_interval = new ArrayList<>();
        cur_interval.add(cur.get(i)[1]);
        cur_interval.add(cur.get(i + 1)[0]);
        ans.add(cur_interval);
    }
    return ans;
}
    
    
    
    
    

