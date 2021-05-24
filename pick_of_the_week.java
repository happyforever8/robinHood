Your function will be called with a list/array of strings of the format "userId,stockSymbol" - for example, ["123,AAPL", "456629,GOOG"].

It should calculate a leaderboard of stock picks - that is, the stocks that users picked most, with the number of users that picked them - and return this leaderboard as an ordered list/array of strings in the form "stockSymbol,numberOfPicks" - for example, ["AAPL,54", "GOOG,31", "MMM,4", ...].

Implementation notes:

The output should be in descending order, based on the number of picks.
If a user has more than one pick, you should select only their first pick (the one with the lowest index), and ignore later picks (don't raise an error).
We don't mind too much about having perfect runtime efficiency - it's OK to have a non-optimal solution as long as it's not exponential time.
CodeSignal will call your function with a fixed input array/list each time and check the output against our tests.

You can run all tests with the "Run Tests" button at the bottom right, or a single test by clicking the "play" button on that test in the bottom pane.

Extra Bits (Tests 5 - 6)

Extend your solution to handle two more requirements:

Test 5: Have your program ignore any extra whitespace around either the user ID or the stock symbol (e.g. interpret 24231, TWLO as the user ID 24231 and the stock TWLO)

Test 6: Only output the top 5 picks in the leaderboard, not all of them.

[execution time limit] 3 seconds (java)

[input] array.string picks

An array of strings in the format "user_id,stock_ticker" (e.g. "98924,GOOG")

[output] array.string

String[] pick_of_the_week(String[] picks) {
    if(picks == null || picks.length == 0) {
        return new String[0];
    }
    Map<String, Integer> stockPicks = new HashMap<>();
    Set<String> userIds = new HashSet<>();
    TreeMap<Integer, String> rs = new TreeMap<>();
    for(String pick : picks) {
        String[] data = pick.split(",");
        String stockLabel = data[1].trim();
        String userId = data[0].trim();
        if(userIds.contains(userId)) {
            continue;
        }
        if(!stockPicks.containsKey(stockLabel)) {
            stockPicks.put(stockLabel, 0);
        }
        stockPicks.put(stockLabel, stockPicks.get(stockLabel) + 1);
        userIds.add(userId);
    }
    for(Map.Entry<String, Integer> stock : stockPicks.entrySet()) {
        rs.put(stock.getValue(), stock.getKey());
        if(rs.size() >= 6) {
            rs.pollFirstEntry();
        }
    }
    String[] result = new String[rs.size()];
    int index = 0;
    for(Map.Entry<Integer, String> stock : rs.descendingMap().entrySet()) {
        result[index] = stock.getValue() + "," + stock.getKey();
        index++;
    }
    return result;
}
