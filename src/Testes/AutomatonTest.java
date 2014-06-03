package Testes;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import automaton.Automaton;
import automaton.State;

public class AutomatonTest {

	State[]  transitionTable ;
	
	Automaton automaton;



	@Before
	public void setUp() throws Exception {

		State st0 = new State() {

			@Override
			public HashMap<String, Integer> validStates() {
				HashMap<String, Integer> temp = new HashMap<String, Integer>();
				temp.put("ab", 1);
				temp.put("ef", 3);

				return temp;
			}

			@Override
			public int nextState(String input) {
				HashMap<String, Integer> temp = new HashMap<String, Integer>();
				temp.put("ab", 1);
				temp.put("ef", 3);
				
				if (temp.get(input) == null) return -1;
				return temp.get(input);
			}

			@Override
			public Boolean isFinal() {
				return false;
			}
		};
		
		
		

		State st1 = new State() {

			@Override
			public HashMap<String, Integer> validStates() {
				HashMap<String, Integer> temp = new HashMap<String, Integer>();
				temp.put("ab", 1);
				temp.put("cd", 2);

				return temp;
			}

			@Override
			public int nextState(String input) {
				HashMap<String, Integer> temp = new HashMap<String, Integer>();
				temp.put("ab", 1);
				temp.put("cd", 2);
				
				if (temp.get(input)	== null) return -1;
				return temp.get(input);
			}

			@Override
			public Boolean isFinal() {
				return false;
			}
		};
		
		
		
		
		State st2 = new State() {

			@Override
			public HashMap<String, Integer> validStates() {
				HashMap<String, Integer> temp = new HashMap<String, Integer>();
				temp.put("ef", 3);

				return temp;
			}

			@Override
			public int nextState(String input) {
				HashMap<String, Integer> temp = new HashMap<String, Integer>();
				temp.put("ef", 3);
				
				if (temp.get(input)	== null) return -1;
				return temp.get(input);
			}

			@Override
			public Boolean isFinal() {
				return true;
			}
		};
		
		State st3 = new State() {

			@Override
			public HashMap<String, Integer> validStates() {
				HashMap<String, Integer> temp = new HashMap<String, Integer>();
				temp.put("g", 4);

				return temp;
			}

			@Override
			public int nextState(String input) {
				HashMap<String, Integer> temp = new HashMap<String, Integer>();
				temp.put("g", 4);

				if (temp.get(input)	== null) return -1;
				return temp.get(input);
			}

			@Override
			public Boolean isFinal() {
				return false;
			}
		};
		
		State st4 = new State() {

			@Override
			public HashMap<String, Integer> validStates() {
				HashMap<String, Integer> temp = new HashMap<String, Integer>();

				return temp;
			}

			@Override
			public int nextState(String input) {
				HashMap<String, Integer> temp = new HashMap<String, Integer>();
				
				return -1;
			}

			@Override
			public Boolean isFinal() {
				return true;
			}
		};


		transitionTable = new State[] {st0,st1,st2,st3,st4};
		
		automaton = Automaton.getAutomaton();

		Automaton.initializeAutomaton(transitionTable);


	}
	
	@After
	public void tearDown(){
		
		automaton = null;
		
	}



	@Test
	public void transitionTest_01(){

		String[] flow = {"ab","cd","ef","g"};

		for (String flowPoint: flow) {

			automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();

	}
	
	@Test
	public void transitionTest_02(){

		

		String[] flow = {"ef", "cd","ab","g"};

		for (String flowPoint: flow) {

			automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();

	}

}
