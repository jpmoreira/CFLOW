package DFA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import REGEX.ASTTerminal;
import REGEX.SimpleNode;

public class NFA {
	
	private Stack<Fragment> workingStack=new Stack<Fragment>();
	
	private AutomataState start;
	
	
	NFA(SimpleNode tree){
		
		composeNFA(tree);
		
		start=workingStack.peek().start;
		
	}
	
	
	public AutomataState getStart() {
		return start;
	}


	void composeNFA(SimpleNode tree){
		
			for(int i=0;i<tree.jjtGetNumChildren();i++){
				SimpleNode child=(SimpleNode) tree.jjtGetChild(i);
				composeNFA(child);
			}
			Fragment frag=Fragment.fragmentForNode(tree,workingStack);
			workingStack.push(frag);
			
	}

	

	
	HashMap<Integer, HashMap<String,ArrayList<Integer> > > transitionTable(){
		
		HashMap<Integer, HashMap<String,ArrayList<Integer>> > table=new HashMap <Integer,HashMap<String,ArrayList<Integer>>>();
		
		Stack<AutomataState> statesStack=new Stack<AutomataState>();
		statesStack.push(this.start);
		while(statesStack.size()>0){//while the stack is not empty
			AutomataState s=statesStack.pop();
			
			HashMap<String, ArrayList<Integer>> transitionsForThisState=new HashMap<String, ArrayList<Integer>>();
			for(int i=0;i<s.outs.size();i++){
				
				AutomataState dest=s.outs.get(i);
				String theString=s.transitions.get(i);
				if(theString==null)theString="__epsilon";
				Integer stateID=s.outs.get(i).getId();
				ArrayList<Integer> destinations=transitionsForThisState.get(theString);
				if(destinations==null)destinations=new ArrayList<Integer>();
				destinations.add(stateID);
				transitionsForThisState.put(theString, destinations);
				
				if(table.get(new Integer(dest.getId()))==null){//if no line for this destiny state
					statesStack.push(dest);
				}
				
				
			}
			table.put(s.getId(), transitionsForThisState);
			
			
		}
		
		return table;
	}
	
	
	void printTable(){
		HashMap<Integer, HashMap<String,ArrayList<Integer>> > table=this.transitionTable();
		
		
		for (Integer integer : table.keySet()) {//for every line
			
			HashMap<String,ArrayList<Integer>> line=table.get(integer);
			
			System.out.print("For State "+integer+": ");
			
			for (String transition : line.keySet()) {//for every column
			
				ArrayList<Integer> states=line.get(transition);
				String statesString="";
				for (Integer stateCode : states) {
					statesString+=stateCode+",";
				}
				System.out.print(transition+"->"+statesString+"  ");
			}
			
			System.out.println("");
			
			
		}
		
	}
}
