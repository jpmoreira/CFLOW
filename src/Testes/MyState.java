package Testes;

import java.io.Serializable;
import java.util.HashMap;

import automaton.State;

public class MyState implements State, Serializable{

	HashMap<String, Integer[]> transitions;

	boolean finalState;

	public MyState(HashMap<String, Integer[]> transitions, boolean finalState) {
		this.transitions = transitions;
		this.finalState = finalState;
	}

	@Override
	public HashMap<String, Integer[]> validStates() {
		return transitions;
	}

	@Override
	public Boolean isFinal() {
		return finalState;
	}

	@Override
	public Integer[] nextState(String input) {
		
		if (transitions.get(input) == null) {
			Integer[] temp = new Integer[] {-1};
			return temp;
		}
		return transitions.get(input);
	}


}
