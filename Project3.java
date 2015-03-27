/*
Jeff Weeks
CSC 172: The Science of Data Structures
University of Rochester
Assignment: Project 3 (Word Search)
3/31/14 
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Project3 {
	
    public static void main( String[] args ) throws IOException {
    	
        BufferedReader dictInputStream = null;
        BufferedReader puzzleInputStream = null;
        BufferedWriter outputStream = null;
        
        int entries = countEntries();
        HashTable table = new HashTable( nextPrime( entries ) );
        int puzzleWidth = getPuzWidth();
        int puzzleHeight = getPuzHeight();
        char[][] puzzle = new char[puzzleHeight][puzzleWidth];

        try {
        	
        	dictInputStream = new BufferedReader( new FileReader( "dictionary.txt" ) );
        	puzzleInputStream = new BufferedReader( new FileReader( "infile.txt" ) );
	        
        	// Scans dictionary.txt line by line inserting into hash table
        	Scanner dictScan = new Scanner( dictInputStream );
	        while ( dictScan.hasNext() )
	        	table.insert( dictScan.next() );
	        dictScan.close();
        	table.analyzeTable();

	        // Scans infile.txt / puzzle line by line
	        Scanner puzzleScan = new Scanner( puzzleInputStream );
	        int h = 0;
	        while ( puzzleScan.hasNext() ){
	        	String ln = puzzleScan.next();
	        	char[] lnArray = ln.toCharArray();
        		for( int j = 0; j < puzzleWidth; j++ )
        			puzzle[h][j] = lnArray[j];
        		h++;
	        }
	        puzzleScan.close();
	        writePuzzle( puzzle );
	        searchPuzzle( puzzle, table, entries, puzzleWidth, puzzleHeight );
            
        } finally {
            System.out.println( "Dictionary entries: \t" + entries );
            System.out.println( "Table size: \t\t" + nextPrime( entries ) );
            System.out.println( "Puzzle dimensions: \t" + puzzleWidth + " x " + 
            		puzzleHeight + "\n" );
    		System.out.println( "See puzzle.txt for imported version of the puzzle.\n" );
		System.out.println( "See outfile.txt for a list of dictionary entries found " + 
                                     "in the puzzle.\n" );
        	if ( dictInputStream != null )  dictInputStream.close();
        	if ( puzzleInputStream != null ) puzzleInputStream.close();
        	if ( outputStream != null ) outputStream.close();    
        }
    }
    
    // scans dictionary.txt to count entries
    public static int countEntries() throws IOException {
    	BufferedReader dictInputStream = null;
    	int count = 1; // <-- This was the off by one error.
    	
    	 try {
         	dictInputStream = new BufferedReader( new FileReader( "dictionary.txt" ) );
         	
        	Scanner dictScan = new Scanner( dictInputStream );
			while ( dictScan.hasNext() ){
	        	dictScan.next(); 
	        	count++;
			}
   		 	dictScan.close();
    	 } finally {
    		 if ( dictInputStream != null )  dictInputStream.close();
			}
		return count;
    }
    
    // finds next prime number higher than number of entries
    public static int nextPrime( int entries ){
    	for ( int i = entries + 1; ; i++ )
    		if ( isPrime( i ) ) return i;
    }
    
	// returns whether the integer is prime
	public static boolean isPrime( int n ){
		if ( n == 1 ) return false; // if 1, not prime
		for( int i = 2 ; i < n ; i++ ){ // checks numbers from 2 to (n-1) for divisibility
			if ( n % i == 0 ) return false;
		}
		return true;
	}

	// gets puzzle width
	public static int getPuzWidth() throws IOException {
	
        BufferedReader puzzleInputStream = null;
        String ln1 = "";
        int width = 0;

        try {
        	puzzleInputStream = new BufferedReader( new FileReader( "infile.txt" ) );
        	Scanner puzzleScan = new Scanner( puzzleInputStream );
       		ln1 = puzzleScan.next();
       		width = ln1.length();
       		puzzleScan.close();
 	       } finally {
 	        	if ( puzzleInputStream != null ) puzzleInputStream.close();
 	       }
        return width;
	}
	
	// gets puzzle height
	public static int getPuzHeight() throws IOException {
		BufferedReader puzzleInputStream = null;
		int height = 0;

		try {
			puzzleInputStream = new BufferedReader( new FileReader( "infile.txt" ) );
			Scanner puzzleScan = new Scanner( puzzleInputStream );
			while( puzzleScan.hasNext() ){
				puzzleScan.next();
				height += 1;
	        }
			puzzleScan.close();
		} finally {
			if ( puzzleInputStream != null ) puzzleInputStream.close();
		}
		return height;
	}
	
	// Writes puzzle to puzzle.txt to test if puzzle is imported properly
	public static void writePuzzle( char[][] puzzle ) throws IOException {
        BufferedWriter outputStream = null;
        
        try {
        	outputStream = new BufferedWriter( new FileWriter( "puzzle.txt" ) );
	         for( int i = 0; i < puzzle.length; i++ ){
	        	 for( int j = 0; j < puzzle[i].length; j++ )
	        		 outputStream.write( puzzle[i][j]+ " " );
	        	 outputStream.write( "\n" );
	         }
        } finally {
        	if ( outputStream != null ) outputStream.close();    
        }
	}
	
	// Primarily searches puzzle and writes found entries into outfile.txt
	// Secondarily writes program statistics to outfile.txt
	public static void searchPuzzle( char[][] puzzle, HashTable table, int entries, 
			int puzzleHeight, int puzzleWidth ) throws IOException {
        BufferedWriter outputStream = null;
        
        try {
        	outputStream = new BufferedWriter( new FileWriter( "outfile.txt" ) );
        	outputStream.write("Entries in hashtable (dictionary size):\t" + entries + "\n");
        	outputStream.write("Hash table size:\t\t\t\t\t\t" + nextPrime( entries ) + "\n" );
        	outputStream.write("Hash table occupancy:\t\t\t\t\t" + table.occupancy + "%" + "\n" );
        	outputStream.write("Max chain length:\t\t\t\t\t\t" + table.maxChainLength + "\n" );
        	outputStream.write("Average chain length:\t\t\t\t\t" + table.aveChainLength + "\n" );
        	outputStream.write( "Puzzle dimensions: \t\t\t\t\t\t" + puzzleWidth + " x " + 
             		puzzleHeight + "\n\n" );
        	outputStream.write( "*See puzzle.txt for imported version of the puzzle.\n" );
        	outputStream.write( "*See tableOccupancy.txt for a bit string representation \n\t" +
				"of vacant and occupied hash table indexes.\n\n" );
        	outputStream.write( "======== Found Entries ========\n\n" );


        	String searchString = "";
	         for( int i = 0; i < puzzle.length; i++ ){
	        	 for( int j = 0; j < puzzle[i].length; j++ ){
	        		 // search for 1 letter word (base case)
	        		 searchString = String.valueOf( puzzle[i][j] );
	        		 if( table.search( searchString ) ) outputStream.write( searchString + "\n" );
	        		 
	        		 // search "northeast" 
	        		 int x = j + 1; int y = i - 1;
	        		 searchString = String.valueOf( puzzle[i][j] );
	        		 while ( x < puzzle[0].length && y >= 0 ){
	        			 searchString += puzzle[y][x];
		        		 if( table.search( searchString ) ) 
		        			 outputStream.write( searchString + "\n" );
		        		 x++; y--;
	        		 }
	        		 	        		 
	        		 // search "east" 
	        		 x = j + 1; y = i;
	        		 searchString = String.valueOf( puzzle[i][j] );
	        		 while ( x < puzzle[0].length ){
	        			 searchString += puzzle[y][x];
		        		 if( table.search( searchString ) ) 
		        			 outputStream.write( searchString + "\n" );
		        		 x++;
	        		 }
	        		 
	        		 // search "southeast" 
	        		 x = j + 1; y = i + 1;
	        		 searchString = String.valueOf( puzzle[i][j] );
	        		 while ( x < puzzle[0].length && y < puzzle.length ){
	        			 searchString += puzzle[y][x];
		        		 if( table.search( searchString ) ) 
		        			 outputStream.write( searchString + "\n" );
		        		 x++; y++;
	        		 }
	        		 
	        		 // search "south" 
	        		 x = j; y = i + 1;
	        		 searchString = String.valueOf( puzzle[i][j] );
	        		 while ( y < puzzle.length ){
	        			 searchString += puzzle[y][x];
		        		 if( table.search(searchString ) ) 
		        			 outputStream.write( searchString + "\n" );
		        		 y++;
	        		 }
	        		 
	        		 // search "southwest" 
	        		 x = j - 1; y = i + 1;
	        		 searchString = String.valueOf( puzzle[i][j] );
	        		 while ( x >= 0 && y < puzzle.length ){
	        			 searchString += puzzle[y][x];
		        		 if( table.search( searchString ) ) 
		        			 outputStream.write( searchString + "\n" );
		        		 x--; y++;
	        		 }
	        		 
	        		 // search "west" 
	        		 x = j - 1; y = i;
	        		 searchString = String.valueOf( puzzle[i][j] );
	        		 while ( x >= 0 ){
	        			 searchString += puzzle[y][x];
		        		 if( table.search( searchString ) ) 
		        			 outputStream.write( searchString + "\n" );
		        		 x--;
	        		 }
	        		 
	        		 // search "northwest" 
	        		 x = j - 1; y = i - 1;
	        		 searchString = String.valueOf( puzzle[i][j] );
	        		 while ( x >= 0 && y >= 0 ){
	        			 searchString += puzzle[y][x];
		        		 if(table.search( searchString ) ) outputStream.write( searchString + "\n" );
		        		 x--; y--;
	        		 }
	        		 
	        		 // search "north" 
	        		 x = j; y = i - 1;
	        		 searchString = String.valueOf( puzzle[i][j] );
	        		 while ( y >= 0 ){
	        			 searchString += puzzle[y][x];
		        		 if( table.search( searchString ) ) outputStream.write( searchString + "\n" );
		        		 y--;
	        		 }
	        		 
	        	 }
	         }
        } finally {
        	if ( outputStream != null ) outputStream.close();    
        }
	}
}
