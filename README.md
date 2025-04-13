# DSA-Assignment-2
AVL Tree, Splay Tree, Hashtables

Tamanna Khurana
Txk220058
CS 3345.005 
Data Structures and Algorithms - Omar Hamdy 8:30 am 

FILES:
AVLTree.java
- code for inserting, deleting, searching, balancing, rotations in an AVL Tree
Entry.java
- class made for entry objects (key, value) pairs for both HashTables
HashTableChaining.java
- Uses entry class and has Linked Lists in each bucket to handle collisions 
LinkedList.java 
- Based off last assignment, Used by HashTableChaining.java
Main.java
- Prints out the tables 
Memory.java
- Functions that measure memory for insertion, deletion, search for all data structures
Time.java
- Functions that measure time for insertion, deletion, search for all data structures
SplayTree.java
- code for inserting, deleting, searching, balancing, splaying, and roations in a splay tree
PerformanceReport.csv
- all tables and graphs that pair with them



PERFORMANCE REPORT:

Please enter an input data size: 10000
Please enter an input data size: 100000

Insertion Performance Comparison (Time in milliseconds): 
--------------------------------------------------------------------------------------------
Data Structure          1000 Elements           10000 Elements          100000 Elements
AVL Tree                5ms                     15ms                    105ms
Splay Tree              3ms                     19ms                    133ms
Hash Chaining           8ms                     17ms                    79ms
Hash Probing            2ms                     7ms                     64ms

- HashTable Quadratic Probing Performed the best for insertion because it stores element in the array and if it is occupied it just searches for the next open spot
- HashTable Chaining using linked lists used more time because with more elements, more collision happens, longer linkedlists to cycle through when searching
- AVL tree is slower because it checks if tree is balanced at every insert and performs necessary rotations
- Splay tree is similar but self balances as it searches


Deletion Performance Comparison (Time in milliseconds): 
--------------------------------------------------------------------------------------------
Data Structure          1000 Elements           10000 Elements          100000 Elements
AVL Tree                0ms                     1ms                     13ms
Splay Tree              0ms                     1ms                     14ms
Hash Chaining           0ms                     1ms                     12ms
Hash Probing            0ms                     0ms                     3ms

- HashTable Quadrating Probing performed fastest because it is just a spot being deleted, table is not restructured every time 
- HashTable Chaiing performed a little bit slower because of searching in linkedlist to delete and then adjusting
- AVL tree performed next best but has to search, delete, and then rebalance each time making it slower
- Splay tree is slowest but similar to AVL, deleting the root to splay it makes it take longer

Search (Lookup) Operation Performance Comparison (Time in milliseconds): 
--------------------------------------------------------------------------------------------
Data Structure          1000 Elements           10000 Elements          100000 Elements
AVL Tree                0ms                     0ms                     8ms
Splay Tree              0ms                     2ms                     15ms
Hash Chaining           0ms                     1ms                     7ms
Hash Probing            0ms                     1ms                     7ms

- HashTable Quadratic Probing is fastest because it is a simple array and you are able to probe until you find the element
- HashTable chaining is is second fast but still takes a little long because of linked lists
- AVL Search si O(logn) but is time consuming at large numbero f elements
- Splay tree is similar but slower because it splays with every search, also O(log n)

Insertion Performance Comparison (Memory in Kilobytes):
--------------------------------------------------------------------------------------------
Data Structure          1000 Elements           10000 Elements          100000 Elements
AVL Tree                1268KB                  1268KB                  6344KB
Splay Tree              1268KB                  1268KB                  3806KB
Hash Chaining           1268KB                  2537KB                  15226KB
Hash Probing            1268KB                  1268KB                  8701KB

- AVL trees and splay trees do not use as much memory
- HashTable Chaining uses alot of memory because of linked lists pointers in each bucket and having objects in each node
- HashTable Probing is slightly more efficient because it is an array and does not contain linkedlists in every bucket

Deletion Performance Comparison (Memory in Kilobytes):
--------------------------------------------------------------------------------------------
Data Structure          1000 Elements           10000 Elements          100000 Elements
AVL Tree                1268KB                  1268KB                  3806KB
Splay Tree              1268KB                  1268KB                  3806KB
Hash Chaining           1268KB                  2537KB                  15226KB
Hash Probing            1268KB                  1268KB                  8701KB

- identical to insertion because when an element is deleted in java, the allocated memory for the node slot is not immediately released and java uses automatic garbage collection 
- also in hashtables often the slot is still there in the table but the object is not there, not completely deleting the slot so it is still structurally the same

Search (Lookup) Operation Performance Comparison (Memory in Kilobytes):
--------------------------------------------------------------------------------------------
Data Structure          1000 Elements           10000 Elements          100000 Elements
AVL Tree                1268KB                  1268KB                  3806KB
Splay Tree              1268KB                  1268KB                  3806KB
Hash Chaining           1268KB                  2537KB                  15226KB
Hash Probing            1268KB                  1268KB                  8701KB

- read only operations and don't modify the structure or allocate new memory to adding any data 
