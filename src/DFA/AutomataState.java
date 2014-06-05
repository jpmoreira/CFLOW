package DFA;

import java.util.ArrayList;

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
	



}
