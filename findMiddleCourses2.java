/*
有多种track，返回所有path里的中间那门课
Students may decide to take different "tracks" or sequences of courses
in the Computer Science curriculum. There may be more than one track 
that includes the same course, but each student follows a single linear 
track from a "root" node to a "leaf" node. In the graph below, their path always moves left to right.

Write a function that takes a list of (source, destination) pairs,
and returns the name of all of the courses that the students could be taking when they are halfway through their track of courses.


作者：关辰晓
链接：https://www.jianshu.com/p/fdbcba5fe5bc
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。


Sample input 1:
all_courses_1 = [
    ["Logic", "COBOL"],
    ["Data Structures", "Algorithms"],
    ["Creative Writing", "Data Structures"],
    ["Algorithms", "COBOL"],
    ["Intro to Computer Science", "Data Structures"],
    ["Logic", "Compilers"],
    ["Data Structures", "Logic"],
    ["Graphics", "Networking"],
    ["Networking", "Algorithms"],
    ["Creative Writing", "System Administration"],
    ["Databases", "System Administration"],
    ["Creative Writing", "Databases"],
    ["Intro to Computer Science", "Graphics"],
]

Sample output 1 (in any order):
          ["Data Structures", "Networking", "Creative Writing", "Databases"]

All paths through the curriculum (midpoint *highlighted*):

Intro to C.S. -> Graphics -> *Networking* -> Algorithms -> Cobol
Intro to C.S. -> *Data Structures* -> Algorithms -> COBOL
Intro to C.S. -> *Data Structures* -> Logic -> COBOL
Intro to C.S. -> *Data Structures* -> Logic -> Compiler
Creative Writing -> *Databases* -> System Administration
*Creative Writing* -> System Administration
Creative Writing -> *Data Structures* -> Algorithms -> COBOL
Creative Writing -> *Data Structures* -> Logic -> COBOL
Creative Writing -> *Data Structures* -> Logic -> Compilers

Visual representation:

                    ____________    ______________
                    |          |    |            |
                    | Graphics |    | Networking |
               ---->|__________|--->|____________|
               |                       |      
               |                       |      
               |                       |  ______________
____________   |                       |  |            |
|          |   |    ______________     >->| Algorithms |--\     _____________
| Intro to |   |    |            |    /   |____________|   \    |           |
| C.S.     |---+    | Data       |   /                      >-->| COBOL     |
|__________|    \   | Structures |--+     ______________   /    |___________|
                 >->|____________|   \    |            |  /
____________    /                     \-->| Logic      |-+      _____________
|          |   /    ______________        |____________|  \     |           |
| Creative |  /     |            |                         \--->| Compilers |
| Writing  |-+----->| Databases  |                              |___________|
|__________|  \     |____________|-\     _________________________
               \                    \    |                       |
                \--------------------+-->| System Administration |
                                         |_______________________|

Sample input 2:
all_courses_2 = [
    ["Course_3", "Course_7"],
    ["Course_0", "Course_1"],
    ["Course_1", "Course_2"],
    ["Course_2", "Course_3"],
    ["Course_3", "Course_4"],
    ["Course_4", "Course_5"],
    ["Course_5", "Course_6"],
]
Sample output 2 (in any order):
["Course_2", "Course_3"]

Complexity analysis variables:

n: number of pairs in the input

*/
