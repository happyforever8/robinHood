Robinhood面试流程挺独特的，VO前有一轮当读的System Design电面。
我的题目是设计一个cron job scheduler，能handle millions of job submition. 关注点是如果job runners 全部挂了了怎么restart。

关注点是如果job runners 全部挂了了怎么restart 最直接的办法 kafka做persistent 已经run的job logging 然后等系统恢复做用logging的数据做re-trigger

https://medium.com/walmartglobaltech/an-approach-to-designing-distributed-fault-tolerant-horizontally-scalable-event-scheduler-278c9c380637
=================================================================================================================================================



https://leetcode.com/discuss/general-discussion/1082786/system-design-designing-a-distributed-job-scheduler-many-interesting-concepts-to-lear




=================================================================================================================================================

电面直接就设计题。设计scheduler system。详细写出DB schema。

每个部分都讨论了容灾的方法。
中途讨论了如何避免两个sheduler job同时运行的问题，还有分布式锁的问题。


补充内容 (2021-04-07 08:31 +8:00):
刚刚收到消息挂了。。
反馈是没有主动计算机器数量，没有主动讨论CAP。。真的是太搞笑了，面试官上来问我说咱们先把大致的图画出来再说，然后一直在积极问问题。难道我答非所问强来一波算机器和解释CAP吗？

补充内容 (2021-04-07 08:36 +8:00):
面试官一直说this is great，全程交流都挺好的，所以挂得我很震惊。总之面试这玩意儿类似谈恋爱，要看对眼，没感觉的话拒绝对方的理由随便就给个。总之这次面试言表不一，差评。


discussion point主要是详细说明每个component具体如何运作的，比如果这个component要支持HTTP request吗？
component之间如何通讯？component做的事情是stateless的吗？
有一些component是stateful的，所以同一时刻只能一台机器做某一个工作
（比如说有台机器挂了，会留下没有做完的tasks，那pick up这些tasks的接盘机器只能有一台，不然的话有可能重复submit tasks）。
这个时候就聊到了分布式锁，聊到了zookeeper和Paxos协议。后来我也提到了对于race-condition不严重的系统其实不需要上分布式锁，直接用database锁就可以了。
还有一个地方提到了锁，集群需要discovery service，这个discovery service本身其实就是分布式锁。然后提到了zookeeper是比较常用的discovery service。



(1)Question :
  LZ 简单讨论一下哈，如果一个worker只能做一个task 那么只需要zookeeper或者db level的lock来知道那个worker做那个task 
  然后重新schedule给任何一个worker就行了吧 不需要特别加一个机器 只需要put back into schedule queue就行了
  LZ 能提供一下什么叫discovery service吗

Answer:
  恩恩，同意，我也是这么答的。两种方案：
  1. 一个是上DB锁，每次拿task的机器得先得到锁。如果中途挂了，那么未来一次锁竞争的时候会发现DB锁过期了。此时竞争到锁的worker就去接盘。
  2. 另一个方案是用distributed lock + discovery service。这两个feature都是zookeeper支持的。
  这个方案的话每次选出固定一个机器去schedule tasks，之后不会变。如果这台机器挂了，
  那么zookeeper会发现（因为没有心跳了，discovery service就是zookeeper监听所有worker心跳的这个功能的装X说法），
  zookeeper会broadcast所有其他的机器过来重新竞选scheduler tasks。

  对于task scheduler来说方案1就够了，因为schedule task不会很经常，DB锁的开销不会很大。

(2)Question:

  感谢讨论 你的第二个方案 并没有明显的优势 因为worker可以从kafka拿events 换句话说 不需要schedule 
  只要自己event完成了 就可以直接拿了 所以第二个有点over eng 第一个简单 stable和直接 已经加米 LZ 有兴趣一起讨论一下design吗
  
  Answer:

  感谢讨论 你的第二个方案 并没有明显的优势 因为worker可以从kafka拿events 换句话说 不需要schedule 只要自己event完成了 
  就可以直接拿了 所以第二个有点over eng 第一个简单 stable和直接 已经加米 LZ 有兴趣一起讨论一下design吗
  
(3) Question
    纯讨论

我感觉楼主似乎设计了一个不需要master的scheduler （每个worker在向db要task）
。这个设计不是不行，但这种设计intuitively应该是设计给超大型系统的。我感觉面试官的feedback是想你能够从一开始先研究清楚具体有多少task，再来从实际角度出发设计这个系统。

我举个简单例子，如果这只是一个startup里面的每天运行10个任务的平台，如果设计如此复杂的分布式锁机制来管理task，会让系统很容易出错，也难debug，死锁之后系统变慢，也就是所谓的cap。

如果有一个master来schedule这个task，每一个worker只是在被动接受任务，应该会简单很多。
如果希望master有一个backup，确实可以用到分布式锁。但老实说，这个设计还是要根据需求定，
一个master如果只是down了10分钟，然后重启之后整个系统都可以运转，对于cron job scheduler来说应该是可以忍受的。除非你设计的是amazon的云服务。again，一切都要根据需求来。

欢迎讨论

  Answer:
  我同意那个分布式锁的方案是给大型系统用的。
不过那个DB锁的方案实现还是挺简单。你觉得这个方案怎么样？
我当时也想了master follower模式，不过除了有follower挂了master去找人接盘以外，没发现master的额外用处。所以就没用这个方案。这个master具体做什么可以展开说一下吗？咱们可以讨论一下。












































