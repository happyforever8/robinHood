新鲜罗兵侠店面，新题，用topological sort算一个service mesh的loadfactor

给一堆service call relations和一个entrypoint，比如 [a:b,c, b:c] a意思就是a会call b和c，b还会call c，
然后assume entrypoint a有一个request，这样a的load就是1，b也是1，c是2。就是每个service都会completely fanout它收到的requests，
比如如果c要有downstream的话，c给他每个downstream的load就是2.

用topological sort做，要注意除了entrypoint之外还有别的service可能没有indegree，这些service 的load要是0，不然漏掉的话graph traverse不完

还要注意有些service的downstream是不存在的，ignore就好


public Map<String, Integer> findLoadFactor(List<String> relations, String entryPoint) {
    Map<String, List<String>> map = new HashMap<>();
 
    processRelations(relations, map);
 
    Map<String, Integer> res = new HashMap<>();
 
    dfs(map, res, entryPoint);
 
    return res;
 
}
 
private void processRelations(List<String> relations, Map<String, List<String>> map) {
    for (String relation : relations) {
        int index = relation.indexOf(":");
        String caller = relation.substring(0, index);
        String[] callee = relation.substring(index + 1).split(",");
        map.putIfAbsent(caller, new ArrayList<>());
        map.get(caller).addAll(new ArrayList<>(Arrays.asList(callee)));
    }
}
 
private void dfs(Map<String, List<String>> relations, Map<String, Integer> res, String entryPoint) {
    res.put(entryPoint, res.getOrDefault(entryPoint, 0) + 1);
    if (relations.containsKey(entryPoint)) {
        for (String next: relations.get(entryPoint)) {
            dfs(relations, res, next);
        }
    }
}
