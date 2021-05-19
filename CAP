CAP theorem
https://cloudxlab.com/assessment/displayslide/345/nosql-cap-theorem#:~:text=NoSQL%20can%20not%20provide%20consistency%20and%20high%20availability%20together.&text=CAP%20theorem%20or%20Eric%20Brewers,Consistency%2C%20Availability%20and%20Partition%20Tolerance.


It is very important to understand the limitations of NoSQL database.
NoSQL can not provide consistency and high availability together.
This was first expressed by Eric Brewer in CAP Theorem.

CAP theorem or Eric Brewers theorem states that we can only achieve 
at most two out of three guarantees for a database: Consistency, Availability and Partition Tolerance.

(1) Here Consistency means that all nodes in the network see the same data at the same time.

(2) Availability is a guarantee that every request receives a response about whether
    it was successful or failed. However it does not guarantee that a read request returns 
    the most recent write.The more number of users a system can cater to better is the availability.

(3) Partition Tolerance is a guarantee that the system continues to operate 
    despite arbitrary message loss or failure of part of the system. 
    In other words, even if there is a network outage in the data center
    and some of the computers are unreachable, still the system continues to perform.

Out of these three guarantees, no system can provide more than 2 guarantees.
Since in the case of a distributed systems, the partitioning of the network is must, 
********the tradeoff is always between consistency and availability.****************



As depicted in the Venn diagram, RDBMS can provide only consistency but not partition tolerance.
While HBASE and Redis can provide Consistency and Partition tolerance.
And MongoDB, CouchDB, Cassandra and Dynamo guarantee only availability but no consistency. 
Such databases generally settle down for eventual consistency meaning that after a while the system is going to be ok.

Let us take a look at various scenarios or architectures of systems to better understand the CAP theorem.

The first one is RDBMs where Reading and writing of data happens on the same machine.
Such systems are consistent but not partition tolerant because if this machine goes down,
there is no backup. Also, if one user is modifying the record, others would have to wait thus compromising the high availability.

The second diagram is of a system which has two machines. Only one machine can accept
modifications while the reads can be done from all machines. In such systems, the modifications flow from that one machine to the rest.

Such systems are highly available as there are multiple machines to serve. 
Also, such systems are partition tolerant because if one machine goes down, 
there are other machines available to take up that responsibility.
Since it takes time for the data to reach other machines from the node A,
the other machine would be serving older data. This causes inconsistency.
Though the data is eventually going to reach all machine and after a while, 
things are going to okay. There we call such systems eventually consistent 
instead of strongly consistent. This kind of architecture is found in Zookeeper and MongoDB.

In the third design of any storage system, we have one machine similar 
to our first diagram along with its backup. Every new change or modification 
at A in the diagram is propagated to the backup machine B. There is only one 
machine which is interacting with the readers and writers.
So, It is consistent but not highly available. If A goes down, B can take A's place. Therefore this system is partition tolerant.

Examples of such system we are HDFS having secondary Namenode and even relational databases having a regular backup.
