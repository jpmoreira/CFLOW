package testsuite;

import static org.junit.Assert.*;

import org.junit.Test;

import DFA.AutomataState;
import DFA.NFA;
import REGEX.ASTCONCAT;
import REGEX.ASTOR;
import REGEX.ASTTerminal;

public class DFATests {
	@Test
	public void test0() {
		ASTTerminal terminal1=new ASTTerminal(28);
		terminal1.isTrueTerminal=true;
		terminal1.idString="a";
		
		terminal1.upperBound=3;
		terminal1.lowerBound=1;
		
	
		
		NFA a=new NFA(terminal1);
		
		a.printTable();
		System.out.println("OUT");
		AutomataState s=a.getStart();
	}

	@Test
	public void test1() {
		ASTOR orNode=new ASTOR(27);
		ASTTerminal terminal1=new ASTTerminal(28);
		terminal1.isTrueTerminal=true;
		terminal1.idString="a";
		ASTTerminal terminal2=new ASTTerminal(28);
		terminal2.isTrueTerminal=true;
		terminal2.idString="b";
		
		orNode.jjtAddChild(terminal1, 0);
		orNode.jjtAddChild(terminal2, 1);
		
		NFA a=new NFA(orNode);
		
		a.printTable();
	}
	
	
	@Test
	public void test3() {
		
		System.out.println("CONCAT AND OR:");
		
		ASTOR orNode=new ASTOR(10);
		ASTTerminal terminal0=new ASTTerminal(11);
		terminal0.isTrueTerminal=true;
		terminal0.idString="b";
		ASTCONCAT concatNode=new ASTCONCAT(27);
		ASTTerminal terminal1=new ASTTerminal(29);
		terminal1.isTrueTerminal=true;
		terminal1.idString="a";
		ASTTerminal terminal2=new ASTTerminal(28);
		terminal2.isTrueTerminal=true;
		terminal2.idString="a";
		
		orNode.jjtAddChild(terminal0, 0);
		orNode.jjtAddChild(concatNode, 1);
		
		concatNode.jjtAddChild(terminal1, 0);
		concatNode.jjtAddChild(terminal2, 1);
		
		NFA a=new NFA(orNode);
		
		a.printTable();
		AutomataState s=a.getStart();
	}
	
	
	
	@Test
	public void testPlusSign() {
		System.out.println("Plus sign");
		ASTTerminal terminal1=new ASTTerminal(28);
		terminal1.isTrueTerminal=true;
		terminal1.idString="a";
		
		terminal1.upperBound=Integer.MAX_VALUE;
		terminal1.lowerBound=1;
		
	
		
		NFA a=new NFA(terminal1);
		
		a.printTable();
		System.out.println("OUT");
		AutomataState s=a.getStart();
	}
	
	@Test
	public void testKleeneStar() {
		System.out.println("KleeneStar");
		ASTTerminal terminal1=new ASTTerminal(28);
		terminal1.isTrueTerminal=true;
		terminal1.idString="a";
		
		terminal1.upperBound=Integer.MAX_VALUE;
		terminal1.lowerBound=0;
		
	
		
		NFA a=new NFA(terminal1);
		
		a.printTable();
		System.out.println("OUT");
		AutomataState s=a.getStart();
	}
	
	
	
	
	
	
	
	
}
