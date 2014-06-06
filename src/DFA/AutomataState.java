package DFA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AutomataState {
	
	
	static int currentID=0;
	ArrayList<AutomataState> outs=new ArrayList<AutomataState>();
	static HashMap<Integer,AutomataState> allStates=new HashMap<Integer, AutomataState>();
	ArrayList<String> transitions=new ArrayList<String>();
	boolean isFinal=false;
	boolean isInitial=false;
	int id;
	
	public AutomataState(boolean isFinal,boolean isInitial) {
		this.isFinal=isFinal;
		this.isInitial=isInitial;
		this.id=currentID++;
		allStates.put(new Integer(this.id), this);
	}

	public int getId() {
		return id;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	public boolean isInitial(){
		return this.isInitial;
	}
	
	public void setInitial(boolean isInitial){
		this.isInitial=isInitial;
	}
	
	
	public void addTransition(String symbol, AutomataState state){
		
		outs.add(state);
		transitions.add(symbol);
	}
	
	public Closure closure(){
		
		HashSet<AutomataState> closureStates = new HashSet<AutomataState>();		
		ArrayList<AutomataState> visitedStates =new ArrayList<AutomataState>();
		
		closureStates.add(this);
		AutomataState visitingState=this;
		
		
		while (visitingState!=null) {
			visitedStates.add(visitingState);
			
			for(int i=0;i<visitingState.transitions.size();i++){
				if(visitingState.transitions.get(i)!=null)continue;//escape non epsilon transitions
				closureStates.add(visitingState.outs.get(i));//add state to closure
				
			}
			
			visitingState=null;//set to null
			for(AutomataState candidateState: closureStates){//try to get a new state to visit
				if(!visitedStates.contains(candidateState)){
					visitingState=candidateState;
					break;
				}
				
			}
			
			
		}
		
		
		return Closure.getClosureWithStates(closureStates);
	}


	private ArrayList<String> getTransitions() {
		return this.transitions;
	}

	private ArrayList<AutomataState> getOuts() {
		return this.outs;
	}
	
	

	public AutomataState deepCloneImpl(Map<AutomataState, AutomataState> copies) {
	    AutomataState copy = copies.get(this);
	    if (copy == null) {//if this was not copied yet
	      copy = new AutomataState(this.isFinal,this.isInitial);
	      //copy.transitions=this.transitions;
	      // Map the new AutomataState _before_ copying children.
	      copies.put(this, copy);
	      for(int i=0;i<this.outs.size();i++){
	    	  copy.addTransition(this.transitions.get(i), this.outs.get(i).deepCloneImpl(copies));
	      }
	      	
	    }
	    return copy;
	  }

	public AutomataState deepClone() {
	    return deepCloneImpl(new HashMap<AutomataState, AutomataState>());
	  }

	public static void resetStates(){
		
		allStates.clear();
		currentID=0;
	}
	
	public static HashMap<Integer, AutomataState> getAllStates(){
		
		return AutomataState.allStates;
	}
}
