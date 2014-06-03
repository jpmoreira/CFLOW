package Testes;

import java.io.Serializable;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import automaton.Automaton;
import automaton.State;

public class AutomatonTest {
	
	
	State[]  transitionTable ;


	@Before
	public void setUp() throws Exception {

		
		
		HashMap<String, Integer> tempHash = new HashMap<String, Integer>();
		tempHash.put("ab", 1);
		tempHash.put("ef", 3);
		
		State st0 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer>();
		tempHash.put("ab", 1);
		tempHash.put("cd", 2);

		State st1 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer>();
		tempHash.put("ef", 3);

		State st2 = new MyState(tempHash, true);
			
		
		tempHash = new HashMap<String, Integer>();
		tempHash.put("g", 4);
		
		State st3 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer>();
		
		State st4 = new MyState(tempHash, true);
		
		
		transitionTable = new State[] {st0,st1,st2,st3,st4};
		
		Automaton.getAutomaton(transitionTable);

	}
	
	@After
	public void tearDown(){
		
		Automaton.resetAutomaton();
		
	}



	@Test
	public void transitionTest_01(){

		System.out.println();
		
		String[] flow = {"ab","cd","ef","g"};

		for (String flowPoint: flow) {

			Automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();

	}
	
	@Test
	public void transitionTest_02(){

		System.out.println();

		String[] flow = {"ef", "cd","ab","g"};

		for (String flowPoint: flow) {

			Automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();

	}
	
	@Test
	public void transitionTest_03(){

		System.out.println();

		String[] flow = {"ef"};

		for (String flowPoint: flow) {

			Automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();

	}

}
