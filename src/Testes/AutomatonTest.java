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

		
		
		HashMap<String, Integer[]> tempHash = new HashMap<String, Integer[]>();
		tempHash.put("ab", new Integer[] {1});
		tempHash.put("ef", new Integer[] {3});
		
		State st0 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer[]>();
		tempHash.put("ab", new Integer[] {1});
		tempHash.put("cd", new Integer[] {2});

		State st1 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer[]>();
		tempHash.put("ef", new Integer[] {3});

		State st2 = new MyState(tempHash, true);
			
		
		tempHash = new HashMap<String, Integer[]>();
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
		
		String[] flow = {"ab","cd","ef","g"};

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

		String[] flow = {"ef"};

		for (String flowPoint: flow) {

			Automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();
		System.out.println();

	}
	
	
	
	@Test
	public void transitionTest_04(){
		
		HashMap<String, Integer[]> tempHash = new HashMap<String, Integer[]>();
		tempHash.put("a", new Integer[] {0,1});
		
		State st0 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer[]>();
		tempHash.put("a", new Integer[] {1,2});
		tempHash.put("b", new Integer[] {2});

		State st1 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer[]>();
		tempHash.put("a", new Integer[] {0,2});

		State st2 = new MyState(tempHash, true);
		
		
		transitionTable = new State[] {st0,st1,st2};
		
		Automaton.getAutomaton(transitionTable);
		
		

		System.out.println("Teste 4");

		String[] flow = {"a","a","a","a"};

		for (String flowPoint: flow) {

			Automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();
		System.out.println();

	}
	
	
	@Test
	public void transitionTest_05(){
		
		HashMap<String, Integer[]> tempHash = new HashMap<String, Integer[]>();
		tempHash.put("a", new Integer[] {0,1});
		
		State st0 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer[]>();
		tempHash.put("a", new Integer[] {1,2});
		tempHash.put("b", new Integer[] {2});

		State st1 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer[]>();
		tempHash.put("a", new Integer[] {0,2});

		State st2 = new MyState(tempHash, true);
		
		
		transitionTable = new State[] {st0,st1,st2};
		
		Automaton.getAutomaton(transitionTable);
		
		

		System.out.println("Teste 5");

		String[] flow = {"a","b"};

		for (String flowPoint: flow) {

			Automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();
		System.out.println();

	}
	
	
	
	@Test
	public void transitionTest_06(){
		
		HashMap<String, Integer[]> tempHash = new HashMap<String, Integer[]>();
		tempHash.put("a", new Integer[] {0,1});
		
		State st0 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer[]>();
		tempHash.put("a", new Integer[] {1,2});
		tempHash.put("b", new Integer[] {2});

		State st1 = new MyState(tempHash, false);
		
		
		
		tempHash = new HashMap<String, Integer[]>();
		tempHash.put("a", new Integer[] {0,2});

		State st2 = new MyState(tempHash, true);
		
		
		transitionTable = new State[] {st0,st1,st2};
		
		Automaton.getAutomaton(transitionTable);
		
		

		System.out.println("Teste 3");

		String[] flow = {"b","b"};

		for (String flowPoint: flow) {

			Automaton.consume(flowPoint);

		}
		
		Automaton.validateResult();
		System.out.println();

	}

}
