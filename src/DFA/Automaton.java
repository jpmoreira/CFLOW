package DFA;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Automaton implements Serializable{

	private static Automaton automaton = null;

	private HashMap<Integer, HashMap<String,Integer>> transition;

	private Integer currentState;

	private Integer lastValidState;

	private String invalidInput;

	private String lastValidInput = "";
	
	private Integer[] finalStates;


	private Automaton(HashMap<Integer, HashMap<String,Integer>> transitionsTable) {

		this.transition = transitionsTable;

		currentState =Closure.initialClosure(); 

		lastValidState = currentState;
		this.finalStates=Closure.finalClosures();

		invalidInput = "";

	}



	public static Automaton getAutomaton(HashMap<Integer, HashMap<String,Integer>> transitionsTable) throws IOException, ClassNotFoundException {

		if(transitionsTable != null){

			automaton = new Automaton(transitionsTable);
			saveFlow();
			return automaton;

		}
		else{
			
			loadFlow();
			return automaton;
		}
			


	}


	public static void consume(String input){

		
		try {
			loadFlow();
		} catch (IOException e) {
			System.out.println("Unable to Load");
			return;
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to Load");
			return;
		}
		
		if (automaton.currentState == null ) return;




		automaton.lastValidState=automaton.currentState;
		automaton.currentState=automaton.transition.get(automaton.currentState).get(input);
		
		if(automaton.currentState!=null)automaton.lastValidInput+=input;
		else automaton.invalidInput+=input;
		
			

		
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


		if (automaton.currentState == null) {

			System.out.println("Invalid flow:");
			System.out.println("Accepted Substring: " + automaton.lastValidInput);
			printExpectedInput(automaton.lastValidState);
			System.out.println("Rejected Input: "+automaton.invalidInput);

			return;

		}

		
		for (Integer integer : automaton.finalStates) {
			if(integer==automaton.currentState){
				System.out.println("Program ran as expected.");
				return;
			}
		}


		System.out.println("Incomplete flow.");
		System.out.println("Program stopped working accordingly at: \"" + automaton.lastValidInput + "\"");
		printExpectedInput(automaton.lastValidState);



		return;

	}

	private static void printExpectedInput(Integer lastValidState) {

		Set<String> validInputs = new HashSet<String>();

		System.out.println("Expected inputs:");

		
		Set<String> tempValidInputs = automaton.transition.get(lastValidState).keySet();

		for (String s : tempValidInputs){
			validInputs.add(s);
		}

	

		for (String s : validInputs){
			System.out.println("\"" + s + "\"");
		}

	}




	public static void resetAutomaton() {
		automaton = null;

	}
}
