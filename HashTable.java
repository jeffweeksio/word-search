/*
Jeff Weeks
CSC 172: The Science of Data Structures
University of Rochester
Assignment: Project 3 (Word Search)
3/31/14 
*/

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HashTable {
	
	int size;
	HashChain[] table;
	float occupancy = 0;
	int maxChainLength = 0;
	float aveChainLength = 0;
	
	public HashTable( int size ){
		this.size = size;
		this.table = new HashChain[size];
		for( int i = 0; i < table.length; i++ ){
			table[i] = new HashChain();
		}
	}
	
	public int hash( String key ){
		int sum;
		int hashVal = 0;
		
		// loops through 2 char at a time adding their ascii sum * (index + 1)^3
		for( int i = 0; i < key.length(); i++ ){
			if( key.length() - i >= 2 ){
				sum = 0;
				for ( int j = 0; j < 2; j++ )
					// occupancy decreased by 4% when I tried groups of 3 letters
					// occupancy increased by 20% when I added multiplication by index
					sum += Math.abs(Character.getNumericValue( key.charAt( i++ ) ) * 
							( i + 1 ) * ( i + 1 ) * ( i + 1 ) );
				// multiply sum by previous pair's sum
				if ( hashVal == 0) hashVal = sum;
				else hashVal *= sum;
			}
			else {
				// handles case when only 1 character remains at the end of the word
				sum = 0;
				for ( ; i < key.length(); i++ )
					sum += Math.abs( Character.getNumericValue( key.charAt( i ) ) * 
							( i + 1 ) * ( i + 1 ) * ( i + 1 ) );
				if ( hashVal == 0 ) hashVal = sum;
				else hashVal *= sum;
			}
		}
		hashVal = Math.abs( hashVal );
		return hashVal % size;
	}
	
	// inserts string into linked list at the hashed index
	public void insert( String key ){
		int index = hash( key );
		this.table[index].insert( key );
	}
	
	// searches the hash table for a string
	public boolean search( String key ){
		int value = hash( key );
		return this.table[value].lookup( key );
	}
	
	// analyzes the hashtable; prints table statistics to console
	public void analyzeTable() throws IOException {
		int fullCount1 = 0; // Counts occupied indexes for assigning list lengths of occupied
                            //      indexes into the counts array as they are counted  
		int fullCount2 = 0; // Counts occupied indexes for creating the array and calculating the
                            //      average list length      
		
		for( int k = 0; k < table.length; k++ ) {
			if ( !table[k].isEmpty() )
				fullCount2++;
		}
		
		int[] counts = new int[fullCount2];
		for( int i = 0; i < table.length; i++ ){
			if ( !table[i].isEmpty() ){
				counts[fullCount1] = table[i].countList();
				fullCount1++;
			}
		}
		occupancy = (float)((float)fullCount1/table.length)*100;
		System.out.println( "\nSee tableOccupancy.txt for a bit string representation \n\t" +
				"of vacant and occupied hash table indexes.\n" );
		System.out.print( "Indexes occupied: \t" + occupancy + 
				"%\n" );
		int j = 0;
		maxChainLength = counts[0];
		while ( j < counts.length ) {
			if ( counts[j] > maxChainLength ) maxChainLength = counts[j];
			aveChainLength += ( counts[j] );
			j++;
		}
		System.out.println( "Longest chain length: \t" + maxChainLength );
		aveChainLength = aveChainLength / counts.length;
		System.out.println( "Average chain length: \t" + aveChainLength );
		tableOccupancy();
	}
	
	// writes a bit string representation of the hash table's occupancy to tableOccupancy.txt
	public void tableOccupancy() throws IOException {
        BufferedWriter outputStream = null;
        
        try {
        	outputStream = new BufferedWriter( new FileWriter( "tableOccupancy.txt" ) );
    		for( int i = 0; i < table.length; i++ ){
    			if ( i > 0 && i % 100 == 0 ) outputStream.write( "\n" );
    			if ( !table[i].isEmpty() ){
    				outputStream.write( "1" );
    			}
    			else outputStream.write( "0" );
    		}
        } finally {
        	if ( outputStream != null ) outputStream.close();    
        }
	}
}
