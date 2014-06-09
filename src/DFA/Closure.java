package DFA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Closure {
	
	HashSet<AutomataState> states=new HashSet<AutomataState>();
	int id;
	
	static ArrayList<Closure> usedClosures=new ArrayList<Closure>();
	
	static int closureID=0;
	
	
	static Closure getClosureWithStates(HashSet<AutomataState> theStates){
		
		for ( Closure closure : usedClosures) {
			if(isIdenticalHashSet(theStates, closure.states)){
				return closure;
			}
		}
		
		return new Closure(theStates);
		
	}

	private Closure(HashSet<AutomataState> theStates) {
		
		states=theStates;
		id=closureID++;
		usedClosures.add(this);
		
		
	}
	
	public HashSet<AutomataState> getStates(){
		return this.states;
	}
	
	public void addState(AutomataState s){
		
		this.states.add(s);
	}
	
	private static boolean isIdenticalHashSet (HashSet<AutomataState> h1, HashSet<AutomataState> h2) {
	    if ( h1.size() != h2.size() ) {
	        return false;
	    }
	    HashSet<AutomataState> clone = new HashSet<AutomataState>(h2); // just use h2 if you don't need to save the original h2
	    
	    Iterator<AutomataState> it = h1.iterator();
	    while (it.hasNext() ){
	        AutomataState state= it.next();
	        if (clone.contains(state)){ // replace clone with h2 if not concerned with saving data from h2
	            clone.remove(state);
	        } else {
	            return false;
	        }
	    }
	    return true; // will only return true if sets are equal
	}


	@Override
	public String toString() {
		String string="{";
		for (AutomataState s : states) {
			string+=s.id+" ";
			
		}
		string+="}";
		
		return string;
	}
	
	public int getID(){
		
		return id;
	}
	
	public static void resetClosures(){
		
		usedClosures.clear();
		closureID=0;
	}
	
	private boolean isFinal(){
		for (AutomataState state : states) {//see if at least one state is final
			if(state.isFinal)return true;
		}
		return false;
	}
	
	private boolean isInitial(){
		for (AutomataState state : states) {//see if at least one state is final
			if(state.isInitial)return true;
		}
		return false;
	}
	
	static public Integer[] finalClosures(){
		
		ArrayList<Integer> finalStates=new ArrayList<Integer>();
		
		for (Closure c : usedClosures) {
			if(c.isFinal())finalStates.add(c.id);//if is final add its id
		}
		
		Integer[] array=new Integer[finalStates.size()];
		finalStates.toArray(array);
		return array;
	}
	
	static public Integer initialClosure(){
		
		for (Closure c : usedClosures) {
			if(c.isInitial())return c.id;//if is initial return. There is only one
		}
		
		return -1;
	}
}
