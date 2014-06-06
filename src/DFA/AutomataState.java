package DFA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AutomataState {
	
	
	static int currentID=0;
	ArrayList<AutomataState> outs=new ArrayList<AutomataState>();
	
	ArrayList<String> transitions=new ArrayList<String>();
	boolean isFinal=false;
	boolean isInitial=false;
	int id;
	
	public AutomataState(boolean isFinal,boolean isInitial) {
		this.isFinal=isFinal;
		this.isInitial=isInitial;
		this.id=currentID++;
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
	

	public AutomataState deepCloneImpl(Map<AutomataState, AutomataState> copies) {
	    AutomataState copy = copies.get(this);
	    if (copy == null) {//if this was not copied yet
	      copy = new AutomataState(this.isFinal,this.isInitial);
	      copy.transitions=this.transitions;
	      // Map the new AutomataState _before_ copying children.
	      copies.put(this, copy);
	      for (AutomataState child : this.outs)
	        copy.outs.add(child.deepCloneImpl(copies));//add a deep clone copy of the original graph
	    }
	    return copy;
	  }

	  public AutomataState deepClone() {
	    return deepCloneImpl(new HashMap<AutomataState, AutomataState>());
	  }

}
