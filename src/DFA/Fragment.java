package DFA;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
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
			ASTTerminal t=(ASTTerminal)s;
			if(t.isTrueTerminal){
				return terminalFragment((ASTTerminal)s);
			}
			else if(t.lowerBound==0 && t.upperBound==Integer.MAX_VALUE) {
				return applyKleeneStartToFragment(stack.pop());
			}
			else if(t.lowerBound==1 && t.upperBound==Integer.MAX_VALUE){
				return applyPlusToFragment(stack.pop());
			}
			else if(t.upperBound==1 && t.lowerBound==1)return null;
			else{
				return applyRepetitionTo(stack.pop(), t.lowerBound, t.upperBound);
			}
		}
		
		return null;
	}
	
	
	static Fragment concatFragment(ASTCONCAT s,Stack<Fragment> stack){
		
		
		Fragment obj1=stack.pop();
		AutomataState end=obj1.exit;//on the stack work backwards... last in concatenation pop first
		end.setFinal(true);
		Fragment obj2;
		for(int i=0;i<s.jjtGetNumChildren()-1;i++){
			obj2=stack.pop();
			obj2.concatWith(obj1);
			obj1=obj2;
		}
		AutomataState start=obj1.start;
		obj1.start.setInitial(true);
		
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
		
		AutomataState start=new AutomataState(false, true);
		AutomataState end=new AutomataState(true, false);
		start.addTransition(s.idString, end);
		Fragment f=new Fragment(start, end);
		
		if(s.upperBound==Integer.MAX_VALUE && s.lowerBound==0)return applyKleeneStartToFragment(f);
		else if(s.upperBound==Integer.MAX_VALUE && s.lowerBound==1)return applyPlusToFragment(f);
		
		return applyRepetitionTo(f, s.lowerBound, s.upperBound);
		
	}

	static Fragment fakeTerminalFragment(ASTTerminal s,Stack<Fragment> stack){
		
		Fragment fragToRepeat=stack.pop();
		
		if(s.lowerBound==0 && s.upperBound==Integer.MAX_VALUE)return applyKleeneStartToFragment(fragToRepeat);
		else if(s.lowerBound==1 && s.upperBound==Integer.MAX_VALUE)return applyPlusToFragment(fragToRepeat);
		
		return applyRepetitionTo(fragToRepeat, s.lowerBound, s.upperBound);
		
	}
	
	
	static Fragment applyKleeneStartToFragment(Fragment f){
		
		Fragment newFragment= applyPlusToFragment(f);
		
		newFragment.start.addTransition(null, newFragment.exit);
		return newFragment;
		
	}
	static Fragment applyPlusToFragment(Fragment f){
		
		AutomataState start=new AutomataState(false, true);
		AutomataState end=new AutomataState(true,false);
	
		Fragment frag=applyRepetitionTo(f, 1, 1);
		
		frag.exit.addTransition(null, frag.start);
		start.addTransition(null, frag.start);
		frag.exit.addTransition(null, end);
		frag.exit.setFinal(false);
		frag.start.setInitial(false);
		
		
		return new Fragment(start, end);
	}
	static Fragment applyRepetitionTo(Fragment f,int min,int max){
		
	//create new end and start nodes	
	AutomataState start=new AutomataState(false, true);
	AutomataState end=new AutomataState(true,false);
	f.start.setFinal(false);
	f.start.setInitial(false);
	f.exit.setFinal(false);
	f.start.setFinal(false);
	
	Fragment fragSequence[]=new Fragment[max];//create an array to hold all the fragments

	fragSequence[0]=f.hardCopy();
	for(int i=1;i<fragSequence.length;i++){
		
		//create the fragment sequence for this position
		fragSequence[i]=f.hardCopy();
		fragSequence[i].start.isInitial=false;
		fragSequence[i].exit.isFinal=false;
		
		//connect with the previous
		fragSequence[i-1].exit.addTransition(null, fragSequence[i].start);
		
		//if its beyond the minimum then should connect to the end 
		if(i>=min ){
			fragSequence[i].start.addTransition(null, end);
		}
	}
	
	//connect start and end to the rest of the automata
	
	start.addTransition(null, fragSequence[0].start);
	fragSequence[fragSequence.length-1].exit.addTransition(null, end);
	
	return new Fragment(start, end);
	
		
		
		
		
	}
	
	void concatWith(Fragment next){
		
		exit.setFinal(false);
		next.start.setInitial(false);
		next.exit.setFinal(true);
		exit.addTransition(null, next.start);//NOT THE OTHER WAY AROUND
		
	}

	Fragment hardCopy(){
		
		HashMap<AutomataState, AutomataState> cloneMap=new HashMap<AutomataState, AutomataState>();
		AutomataState newStart=this.start.deepCloneImpl(cloneMap);
		AutomataState newEnd=cloneMap.get(this.exit);
		
		return new Fragment(newStart, newEnd);
	}
	
	
}
