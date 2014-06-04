package automaton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Automaton implements Serializable{

	private static Automaton automaton = null;

	private State[] transition;

	private int emptyStateIndex = -1;

	private Set<Integer> currentStates;

	private ArrayList<Integer> lastValidStates;

	private ArrayList<String> invalidInput;

	private String lastValidInput = "first input";


	private Automaton(State[] transitions) {

		this.transition = transitions;
		
		currentStates = new HashSet<Integer>();
		currentStates.add(0);
		
		lastValidStates = new ArrayList<Integer>();
		lastValidStates.add(0);
		
		invalidInput = new ArrayList<String>();

	}



	public static Automaton getAutomaton(State[] transitions) {

		if(transitions != null){

			automaton = new Automaton(transitions);
			return automaton;

		}
		else
			return automaton;


	}


	public static void consume(String input){

		if (automaton.currentStates.size() == 0 ) return;
		
		Set<Integer> tempCurrentStates = new HashSet<Integer>();
		ArrayList<Integer> tempLastValidStates = new ArrayList<Integer>();


		for (int currentState : automaton.currentStates){


			Integer[] stateAux = automaton.transition[currentState].nextState(input);

			for (int st : stateAux) {
				
				if (st == automaton.emptyStateIndex) {

					tempLastValidStates.add(currentState);
					automaton.invalidInput.add(input);

				} else {

					tempLastValidStates.add(currentState);
					tempCurrentStates.add(st);
					automaton.lastValidInput = input;

				}
				
			}
			
		}
		
		automaton.currentStates = tempCurrentStates;
		automaton.lastValidStates = tempLastValidStates;

		try {
			saveFlow();
		} catch (IOException i){
			System.out.println("Error saving automaton.");
			i.printStackTrace();
		}

	}


	private static void saveFlow() throws IOException {

		FileOutputStream fileOut = new FileOutputStream("./automaton");
		ObjectOutputStream os = new ObjectOutputStream(fileOut);

		/* Write the automaton in a file */
		os.writeObject(automaton);

		fileOut.close();
		os.close();	

	}

	private static void loadFlow() throws IOException, ClassNotFoundException  {

		FileInputStream fileIn = new FileInputStream("./automaton");
		ObjectInputStream is = new ObjectInputStream(fileIn);

		/* load the saved game in the file to the object tempGame */
		automaton = (Automaton) is.readObject();

		is.close();
		fileIn.close();

	}

	public static void validateResult(){

		try {
			loadFlow();
		} catch (IOException e) {
			System.out.println("Automaton does not exist.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading automaton.");
			e.printStackTrace();
		}

		
		if (automaton.currentStates.size() == 0) {
			
			System.out.println("Invalid flow at:");
			
			
			for (int i = 0; i < automaton.lastValidStates.size(); i++) {
				
				System.out.println("- flow control point \"" + automaton.lastValidInput + "\" receive a input: \"" + automaton.invalidInput.get(i) +"\"");

				expectedInput(automaton.lastValidStates.get(i));
				
			}
			
		}

		for (int currentState : automaton.currentStates){

			if (automaton.transition[currentState].isFinal()) {			
				System.out.println("Program ran as expected.");
				break;
			}

			System.out.println("Incomplete flow.");
			System.out.println("Program stopped at: \"" + automaton.lastValidInput + "\"");

			expectedInput(currentState);

		}



		return;

	}

	private static void expectedInput(int lastValidState) {

		Set<String> validInputs = automaton.transition[lastValidState].validStates().keySet();

		System.out.println("Expected inputs:");

		for (String s : validInputs){
			System.out.println("\"" + s + "\"");
		}

	}

	public static void resetAutomaton() {
		automaton = null;

	}
}
