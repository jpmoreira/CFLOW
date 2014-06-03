package automaton;

import java.util.HashMap;

public interface State {
	
	HashMap<String,Integer> validStates();
	
	Boolean isFinal();
	
	int nextState(String input);
}
