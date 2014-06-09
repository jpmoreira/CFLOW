package DFA;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class nfa2dfaTests {

	@Test
	public void testLine1() {
		
		AutomataState s1=new AutomataState(false, true);
		AutomataState s2=new AutomataState(false, true);
		AutomataState s3=new AutomataState(false, true);
		AutomataState s4=new AutomataState(false, true);
		
		s1.addTransition("a", s4);
		s1.addTransition(null, s2);
		s2.addTransition("a", s3);
		
		NFA a =new NFA(s1);
		
		HashSet<AutomataState> s1ClosureStates=new HashSet<AutomataState>();
		s1ClosureStates.add(s1);
		s1ClosureStates.add(s2);
		
		Closure c=Closure.getClosureWithStates(s1ClosureStates);
		HashMap<String,Closure>result= a.dfa_lineForClosure(c);
		
		Closure resultForA=result.get("a");
		
		AutomataState [] states=new AutomataState[resultForA.states.size()];
		resultForA.states.toArray(states);
		assertEquals(resultForA.states.size(), 2);
		
		boolean is4Present=false;
		boolean is3Present=false;
		
		for (AutomataState s : states) {
			if(s==s4)is4Present=true;
			if(s==s3)is3Present=true;
		}
		
		assertTrue(is4Present && is3Present);
		assertEquals(result.size(), 1);
		
		
		
	}
	
	
	@Test
	public void testLine2() {
		
		AutomataState s1=new AutomataState(false, true);
		AutomataState s2=new AutomataState(false, true);
		AutomataState s3=new AutomataState(false, true);
		AutomataState s4=new AutomataState(false, true);
		AutomataState s5=new AutomataState(false, true);
		
		s1.addTransition("a", s4);
		s1.addTransition(null, s2);
		s2.addTransition(null, s3);
		s3.addTransition("b", s4);
		s3.addTransition("a", s5);
		
		NFA a =new NFA(s1);
		
		HashSet<AutomataState> s1StateClosure=new HashSet<AutomataState>();
		s1StateClosure.add(s1);
		s1StateClosure.add(s2);
		s1StateClosure.add(s3);
		
		Closure c=Closure.getClosureWithStates(s1StateClosure);
		HashMap<String,Closure>result= a.dfa_lineForClosure(c);
		
		Closure resultForA=result.get("a");
		Closure resultForB=result.get("b");
		
		AutomataState [] states=new AutomataState[resultForA.states.size()];
		resultForA.states.toArray(states);
		assertEquals(resultForA.states.size(), 2);
		assertEquals(resultForB.states.size(),1);
		
		boolean is5Present=false;
		boolean is4Present=false;
		
		for (AutomataState s : states) {
			if(s==s5)is5Present=true;
			if(s==s4)is4Present=true;
		}
		
		assertTrue(is5Present && is4Present);
		
		
		
	}
	
	
	@Test
	public void testConvertion1() {
		
		
		
		AutomataState s0=new AutomataState(false, true);
		AutomataState s1=new AutomataState(false, true);
		AutomataState s2=new AutomataState(false, true);
		AutomataState s3=new AutomataState(false, true);
		AutomataState s4=new AutomataState(false, true);
		AutomataState s5=new AutomataState(false, true);
		AutomataState s6=new AutomataState(false, true);
		AutomataState s7=new AutomataState(false, true);
		AutomataState s8=new AutomataState(false, true);
		AutomataState s9=new AutomataState(false, true);
		AutomataState s10=new AutomataState(false, true);
		
		s0.addTransition(null, s1);
		s0.addTransition(null, s7);
		
		s1.addTransition(null, s2);
		s1.addTransition(null, s4);
		
		s2.addTransition("a", s3);
		
		s3.addTransition(null, s6);
		
		s4.addTransition(null, s5);
		
		s5.addTransition(null, s6);
		
		s6.addTransition(null, s7);
		s6.addTransition(null, s1);
		
		s7.addTransition("a", s8);
		
		s8.addTransition("b", s9);
		
		s9.addTransition("b", s10);
		
		NFA theNFA=new NFA(s0);
		
		HashMap<Closure , HashMap<String,Closure> > table=theNFA.dfa_table();
		
		Set<Closure> closures=table.keySet();
		Closure.resetClosures();
		theNFA.printDFATable();
		assertEquals(closures.size(), 5);
		
		assertTrue(true);
		
		
		
		
		
		
	}

}
