package DFA;

import static org.junit.Assert.*;

import org.junit.Test;

import REGEX.ASTOR;
import REGEX.ASTTerminal;

public class DFATests {

	
	NFA a;
	
	
	@Test
	public void test1() {
		ASTOR orNode=new ASTOR(27);
		ASTTerminal terminal1=new ASTTerminal(28);
		terminal1.isTrueTerminal=true;
		terminal1.idString="a";
		ASTTerminal terminal2=new ASTTerminal(28);
		terminal2.isTrueTerminal=true;
		terminal2.idString="a";
		
		orNode.jjtAddChild(terminal1, 0);
		orNode.jjtAddChild(terminal2, 1);
		
		a=new NFA(orNode);
		
		a.printTable();
		AutomataState s=a.getStart();
		
		AutomataState[] startClosure = s.closure();
		
		for (AutomataState q : startClosure){
			System.out.println("State: " + q.id);
		}
		
	}

	@Test
	public void test2(){
		
		AutomataState s = a.getStart();
		
		AutomataState[] startClosure = s.closure();
		
		for (AutomataState q : startClosure){
			System.out.println("State: " + q.id);
		}
		
		
		
		
		
	}
	
	
	
	
}
