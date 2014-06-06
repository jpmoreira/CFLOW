package automaton;

import java.util.HashMap;

public interface State {
	
	HashMap<String,Integer[]> validStates();
	
	Boolean isFinal();
	
	Integer[] nextState(String input);
}
