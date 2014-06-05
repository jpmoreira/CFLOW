package DFA;

import static org.junit.Assert.*;

import org.junit.Test;

import REGEX.ASTOR;
import REGEX.ASTTerminal;

public class DFATests {

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
		
		NFA a=new NFA(orNode);
		
		a.printTable();
		AutomataState s=a.getStart();
	}

}
