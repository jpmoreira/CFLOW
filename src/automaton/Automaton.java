package automaton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Automaton implements Serializable{

	private static Automaton automaton = null;

	private static int[][] transition;

	private static int emptyStateIndex;

	private static HashMap<String, Integer> alphabetIndex;

	private static int currentState;

	private static int[] stateAction;

	private static ArrayList<String> flowInput;
	private static ArrayList<Integer> flowResult;

	// codes for actions
	final static int NOT_ACCEPT=0;
	final static int ACCEPT=1;

	
	private Automaton() {

		
	}
	
	public static void initializeAutomaton(int[][] transitions, int[] statesType, String[] alphabet){

		Automaton.transition = transitions;
		Automaton.stateAction = statesType;
		Automaton.emptyStateIndex = alphabet.length-1;

		Automaton.flowInput = new ArrayList<String>();
		Automaton.flowResult = new ArrayList<Integer>();

		Automaton.alphabetIndex = new HashMap<String, Integer>();

		for (int i = 0 ; i < alphabet.length; i++){

			alphabetIndex.put(alphabet[i], i);

		}
		
	}


	public static Automaton getAutomaton() {
		
		if(automaton == null){

			automaton = new Automaton();
			return automaton;
			
		}
		else
			return automaton;
		

	}


	public void consume(String input){

		int inputIndex = mapInputToCol(input);

		Automaton.currentState = transition[currentState][inputIndex];

		Automaton.flowInput.add(input);
		Automaton.flowResult.add(stateAction[currentState]);


	}


	private int mapInputToCol(String idToken){

		return alphabetIndex.get(idToken);

	}
	
	public boolean validateResult(){
		
		if (stateAction[currentState] == 1) return true;
		
		
		return false;
		
	}
}
