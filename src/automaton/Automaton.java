package automaton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Set;

public class Automaton implements Serializable{

	private static Automaton automaton = null;

	private State[] transition;

	private int emptyStateIndex = -1;

	private int currentState;
	
	private int lastValidState = 0;
	
	private String invalidInput;
	
	private String lastValidInput;

	
	private Automaton(State[] transitions) {

		this.transition = transitions;
		
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

		if (automaton.currentState == automaton.emptyStateIndex) return;
		
		int tempState = automaton.currentState;

		automaton.currentState = automaton.transition[automaton.currentState].nextState(input);

		if (automaton.currentState == automaton.emptyStateIndex){
			
			automaton.lastValidState = tempState;
			automaton.invalidInput = input;
			
		} else {
			
			automaton.lastValidInput = input;
			
		}
		
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
		
		if (automaton.currentState == automaton.emptyStateIndex) {
			
			System.out.println("Invalid flow at flow control point: ");
			System.out.println("Error ocurred in flow control point: " + automaton.lastValidInput);
			System.out.println("Invalid flow control point: " + automaton.invalidInput);
			
			expectedInput(automaton.lastValidState);
			
			return;
			
		}
		
		if (automaton.transition[automaton.currentState].isFinal()) {			
			System.out.println("Program ran as expected.");
			return;
		}
		
		System.out.println("Incomplete flow.");
		System.out.println("Program stopped at: " + automaton.lastValidInput);
		
		expectedInput(automaton.currentState);
		
		
		return;
		
	}

	private static void expectedInput(int lastValidState2) {
		
		Set<String> validInputs = automaton.transition[lastValidState2].validStates().keySet();
		
		System.out.println("Expected inputs:");
		
		for (String s : validInputs){
			System.out.println(s);
		}
		
	}

	public static void resetAutomaton() {
		automaton = null;
		
	}
}
