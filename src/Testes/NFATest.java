package Testes;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import automaton.Automaton;
import automaton.State;

public class NFATest {

	
	State[]  transitionTable ;


	@Before
	public void setUp() throws Exception {

		
		
		HashMap<String, Integer[]> tempHash = new HashMap<String, Integer[]>();
		tempHash.put("ab", new Integer[] {1,3});
		tempHash.put("g", new Integer[] {4});
		
		State st0 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer[]>();
		tempHash.put("cd", new Integer[] {2});
		tempHash.put("ef", new Integer[] {3});

		State st1 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer[]>();
		tempHash.put("g", new Integer[] {4});

		State st2 = new MyState(tempHash, true);
			
		
		tempHash = new HashMap<String, Integer[]>();
		tempHash.put("ef", new Integer[] {2});
		tempHash.put("g", new Integer[] {4});
		
		State st3 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer[]>();
		
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

		System.out.println("Teste 1");
		
		String[] flow = {"ab","cd"};

		for (String flowPoint: flow) {

			Automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();
		System.out.println();

	}
	
	@Test
	public void transitionTest_02(){

		System.out.println("Teste 2");

		String[] flow = {"ef", "cd","ab","g"};

		for (String flowPoint: flow) {

			Automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();
		System.out.println();

	}
	
	@Test
	public void transitionTest_03(){

		System.out.println("Teste 3");

		String[] flow = {"ab"};

		for (String flowPoint: flow) {

			Automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();
		System.out.println();

	}

	
}
