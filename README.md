Word Search Solver

This program reads and hashes a dictionary file (dictionary.txt), reads a left anchored matrix of characters from infile.txt (the puzzle) and searches the puzzle for words contained in dictionary.txt.

Outputs include:
outfile.txt 		The list of words from dictionary.txt found in infile.txt as well as some program statistics.
 			Statistics include: dictionary size, hash table size (next higher prime), hash table 					occupancy (percentage), maximum chain length (separate chaining), average chain length, 				and puzzle dimensions.
tableOccupancy.txt	A bit string representation of the hash table occupancy. Used for analysis of the custom hash 			function.
puzzle.txt		The puzzle as imported from infile.txt.

