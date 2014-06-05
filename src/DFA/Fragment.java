package DFA;

import java.util.Stack;

import org.hamcrest.core.IsInstanceOf;

import REGEX.ASTCONCAT;
import REGEX.ASTExpression;
import REGEX.ASTOR;
import REGEX.ASTTerminal;
import REGEX.SimpleNode;

public class Fragment{
	
	
	AutomataState start;
	AutomataState exit;
	
	
	Fragment(AutomataState s,AutomataState e){
		
		this.start=s;
		this.exit=e;
	}
	
	static public Fragment fragmentForNode(SimpleNode s,Stack<Fragment> stack) {
		
		if(s instanceof ASTCONCAT){
			
			return concatFragment((ASTCONCAT)s, stack);
		}
		else if(s instanceof ASTOR){
			
			return orFragment((ASTOR)s, stack);
		}
		else if(s instanceof ASTTerminal){
			if(((ASTTerminal)s).isTrueTerminal){
				return terminalFragment((ASTTerminal)s);
			}
		}
		
		return null;
	}
	
	
	static Fragment concatFragment(ASTCONCAT s,Stack<Fragment> stack){
		
		
		Fragment obj1=stack.pop();
		AutomataState start=obj1.start;
		start.setInitial(true);
		Fragment obj2;
		for(int i=0;i<s.jjtGetNumChildren()-1;i++){
			obj2=stack.pop();
			obj1.concatWith(obj2);
			obj1=obj2;
		}
		AutomataState end=obj1.exit;
		obj1.exit.setFinal(true);
		
		return new Fragment(start,end);
		
	}
	
	

	static Fragment orFragment(ASTOR s,Stack<Fragment> stack){
		
		AutomataState start=new AutomataState(false, true);
		AutomataState end=new AutomataState(true, false);
		
		for(int i=0;i<s.jjtGetNumChildren();i++){
			
			Fragment f=stack.pop();
			start.addTransition(null, f.start);//add de epsilon transition
			f.start.setInitial(false);
			f.exit.setFinal(false);
			f.exit.addTransition(null, end);//add epsilon transition at end
		}
		
		return new Fragment(start, end);
		
	}
	
	static Fragment terminalFragment(ASTTerminal s){
		
		AutomataState start=new AutomataState(false,true);
		AutomataState end=new AutomataState(true, false);
		start.addTransition(s.idString, end);
		
		return new Fragment(start, end);
	}

	void concatWith(Fragment next){
		
		exit.setFinal(false);
		next.start.setInitial(false);
		next.exit.setFinal(true);
		exit.addTransition(null, next.start);//NOT THE OTHER WAY AROUND
		
	}

}
