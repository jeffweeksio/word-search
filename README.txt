# word-search
Jeff Weeks
CSC 172: The Science of Data Structures
University of Rochester
Assignment: Project 3 (Word Search)
4/6/14 

v==== Program Description ====v

Program starts by scanning dictionary.txt to import all words into a hashtable for quick access.
Then scans infile.txt to import the puzzle.  It then proceeds to check in all eight directions
at every point in the puzzle matrix for words found in dictionary.txt.  Program searches for words 
in a clockwise direction, first scanning in the 1:30 o'clock direction, followed by 3 o'clock etc. 
all the way to 12 o'clock.

My hash function was chosen through educated trial and error.  I had working code and an analyzeTable
method that helped me to tinker.  My final solution is (using the word "apple"):

	([("a" * ascii * (position index + 1)^3) + ("p" * ascii * (position index + 1)^3)] *
	[("p" * ascii * (position index + 1)^3) + ("l" * ascii * (position index + 1)^3)] *
	[("e" * ascii * (position index + 1)^3)]) % table size
	
I found that using the position index of each letter improved my hash function's occupancy by about 20%.
and decreased my longest chain by 2/3. Summing 1, 3, or 4 characters at a time resulted in a significant 
occupancy reduction as opposed to 2 characters at a time.  Cubing the index position provided marginal, 
yet worthy performance improvement, as did adding 1 to that index (to avoid multiplication by zero on 
the first letter).

I sized my hash table by finding out the size of the dictionary and using the next higher prime.

Conflicts were handled through separate chaining.  Upon construction of my hash table I filled each index
with an empty linked list.  When a word was inserted it was simply added at the head of the index's linked
list.

v==== Extras ====v

*analyzeTable() method: Provides comprehensive analysis of Hash Table efficiency.
*Statistics included:
	Indexes occupied
	Longest chain length
	Average chain length
	Dictionary entries
	Table size
	Puzzle dimensions
*Created puzzle.txt to see imported version of the puzzle and check import accuracy.
*Created tableOccupancy.txt to see visual representation of the hash table's occupancy
	in the form of a bit string and check for large gaps in distribution and sufficiently
	dispersed distribution of hashed values.
*Allowed for puzzles with rectangular, or I believe even irregular, dimensions.  I haven't
	proven this, but I believe this will work on any left justified puzzle shape.

v==== Files Included ====v

dictionary.txt			dictionary file
infile.txt			input file
outfile.txt			output file	 	 
puzzle.txt			contains puzzle as imported from infile
tableOccupancy.txt		contains bit string representation of my hash table's occupancy
HashChain.java			linked list class for separate chaining in the hash table
HashNode.java			node class for linked lists
HashTable.java			hash table class
MyLinkedList.java		linked list interface
Project3.java			main

v==== Running instructions ====v

How to run:
	>javac Project3.java
	>java Project3 <no parameters>

v==== Puzzles Tested ====v
1.)

this
wats
oahg
fgdt

2.) Citation: http://tertuliadofado.com/printable-hard-word-searches/

esproxybiidgtwtzohbr
jincdgytlaneplxbkowq
mmfwaefviahhgoekyynt
vbasmdebtutreqwnrtre
daerpsjidrqiurrouiyb
lcaigicuiexepoiisrfi
edratiogsnsuramtauvr
iynpfvnictsrkwcaecdt
yteeiycforeclosurezt
tjdrggptualdlihltssl
xnnetlrtnpblbalancet
twecijiatraatslvebme
sodmdbmgntvtnuoccaog
ariweiejetikmkcotscd
ccvdrtrraneuzmrofinu
esihcnarfacevalueiib
redrhltbyceesltnplqo
optionelaurccauditqk
fmjskfkshkqjxoezafcd
zdskzihnnljrxkjlhzjy

v==== Console output sample ====v

See tableOccupancy.txt for a bit string representation of vacant and occupied hash table indexes.

Indexes occupied: 	53.320797%
Longest chain length: 	21
Average chain length: 	1.875321
Dictionary entries: 	109530
Table size: 		109537
Puzzle dimensions: 	20 x 20

See puzzle.txt for imported version of the puzzle.
