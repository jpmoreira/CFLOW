package automaton;

import java.util.HashMap;
import java.util.Vector;

public class AutomataState implements State{

	public HashMap<String, Integer[]> States;
	
	public Boolean isFinal;
	
	public AutomataState(HashMap<String, Integer[]> NextStates, Boolean isFinal){
		this.States = NextStates;
		this.isFinal = isFinal;
	}

	@Override
	public HashMap<String, Integer[]> validStates() {
		return States;
	}

	@Override
	public Boolean isFinal() {
		return isFinal;
	}

	@Override
	public Integer[] nextState(String input) {
		return States.get(input);
		
	}
	
	
	
}
