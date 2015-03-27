/*
Jeff Weeks
CSC 172
Lab Section: M,W 6:15-7:30pm
Lab TA: Ciaran Downey
Assignment: Project 3 (Word Search)
3/31/14 
*/

public class HashChain implements MyLinkedList{

	private HashNode first;
		
		@Override
		public void insert( Object x ) { // Runtime: O(1)
			HashNode newNode = new HashNode();
			newNode.data = x;
			newNode.next = first;
			first = newNode;
		}

		@Override
		public boolean lookup( Object x ) { // Runtime: O(n)
			if ( isEmpty() ) return false;
			HashNode current = first;
			while ( current != null ){
				if ( current.data.equals( x ) )
					return true;
				current = current.next;
			}
			return false;
		}

		@Override
		public boolean isEmpty() { // Runtime: O(1)
			return first == null;
		}

		@Override
		public void printList() { // Runtime: O(n)
			if ( isEmpty() ) return;
			HashNode temp = first;
			while ( temp != null ){
				System.out.println( temp.data.toString() );
				temp = temp.next;
			}
		}
		
		public int countList() { // Runtime: O(n)
			int count = 0;
			if ( isEmpty() ) return 0;
			HashNode temp = first;
			while ( temp != null ){
				count++;
				temp = temp.next;
			}
			return count;
		}

		@Override
		public void delete( Object x ) {
			// TODO Auto-generated method stub
		}
}
