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

	private static State[] transition;

	private static int emptyStateIndex = -1;

	private static int currentState;
	
	private static int lastValidState = 0;
	
	private static String invalidInput;
	
	private static String lastValidInput;

	
	private Automaton() {

		
	}
	
	public static void initializeAutomaton(State[] transitions){

		Automaton.transition = transitions;
		
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

		if (currentState == emptyStateIndex) return;
		
		int tempState = currentState;

		currentState = transition[currentState].nextState(input);

		if (currentState == emptyStateIndex){
			
			lastValidState = tempState;
			invalidInput = input;
			
		} else {
			
			lastValidInput = input;
			
		}
		
		try {
			saveFlow();
		} catch (IOException i){
			System.out.println("Error saving automaton.");
			
		}

	}

	
	private void saveFlow() throws IOException {
		
		FileOutputStream fileOut = new FileOutputStream("./automaton");
		ObjectOutputStream os = new ObjectOutputStream(fileOut);

		/* Write the game in a file */
		os.writeObject(this);

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
		
		if (currentState == emptyStateIndex) {
			
			System.out.println("Invalid flow at flow control point: ");
			System.out.println("Error ocurred in flow control point: " + lastValidInput);
			System.out.println("Invalid flow control point: " + invalidInput);
			
			expectedInput(lastValidState);
			
			return;
			
		}
		
		if (transition[currentState].isFinal()) {			
			System.out.println("Program ran as expected.");
			return;
		}
		
		System.out.println("Incomplete flow.");
		System.out.println("Program stopped at: " + lastValidInput);
		
		expectedInput(lastValidState);
		
		
		return;
		
	}

	private static void expectedInput(int lastValidState2) {
		
		Set<String> validInputs = transition[lastValidState2].validStates().keySet();
		
		System.out.println("Expected inputs:");
		
		for (String s : validInputs){
			System.out.println(s);
		}
		
	}
}
