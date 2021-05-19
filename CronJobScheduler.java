Robinhood面试流程挺独特的，VO前有一轮当读的System Design电面。
我的题目是设计一个cron job scheduler，能handle millions of job submition. 关注点是如果job runners 全部挂了了怎么restart。


关注点是如果job runners 全部挂了了怎么restart 最直接的办法 kafka做persistent 已经run的job logging 然后等系统恢复做用logging的数据做re-trigger
