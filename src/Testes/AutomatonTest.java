package Testes;

import org.junit.Before;
import org.junit.Test;

import automaton.Automaton;

public class AutomatonTest {

	int[][] transitionTable ;
	
	int[] action;
	
	String[] input;

	@Before
	public void setUp() throws Exception {

		int[][] transitionTable = new int[][] {
				{1,5,3,5},
				{1,2,5,5},
				{5,5,3,5},
				{5,5,5,4},
				{5,5,5,5},
				{5,5,5,5}
		};
		
		action = new int[] {0,0,1,0,1,0};

		input = new String[] {"ab","cd","ef","g"};
		
	}



	@Test
	private void transitionTest_01(){

		Automaton automaton = Automaton.getAutomaton();
		
		automaton.initializeAutomaton(transitionTable, action, input);
		
		String[] flow = {"ab","cd","ef","g"};
		
		for (String flowPoint: flow) {
			
			automaton.consume(flowPoint);
			
		}
		
		





	}

}
