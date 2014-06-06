package DFA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
	
	NFA(AutomataState state){
		start=state;
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


	
	HashMap<HashSet<AutomataState> , HashMap<String,HashSet<AutomataState>> > dfa_table(){
		
		
		HashMap<HashSet<AutomataState> , HashMap<String,HashSet<AutomataState>> > table= new HashMap<HashSet<AutomataState>, HashMap<String,HashSet<AutomataState>>>();
		
		HashSet<AutomataState> firstStateClosure=this.start.closure();
		ArrayList<HashSet<AutomataState>> closuresToProcess=new ArrayList<HashSet<AutomataState>>();//will save the closures not yet processed
		closuresToProcess.add(firstStateClosure);
		
		while(closuresToProcess.size()>0){//while we have closures to process
		
			//get last closure to process and delete it (aka pop)
			HashSet<AutomataState> processingClosure=closuresToProcess.get(closuresToProcess.size()-1);
			closuresToProcess.remove(processingClosure);
			
			HashMap<String,HashSet<AutomataState>> tableLine=this.dfa_lineForClosure(processingClosure);
			
			Set<String> allTransitions=tableLine.keySet();
			
			
			for (String transition : allTransitions) {
				
				HashSet<AutomataState> closure=tableLine.get(transition);
				
				if(table.get(closure)==null)table.put(closure, tableLine);
				else closuresToProcess.add(closure);
				
				
			}
			
		}
		
		return table;
		
	}
	
	
	HashMap<String,HashSet<AutomataState>> dfa_lineForClosure(HashSet<AutomataState> groupOfStates){
	
		HashMap<String, HashSet<AutomataState>> tableLine =new HashMap<String, HashSet<AutomataState>>();
		
		AutomataState[] statesArray=new AutomataState[groupOfStates.size()];
		
		groupOfStates.toArray(statesArray);
		for (AutomataState state : statesArray) {//for each state in the closure
			ArrayList<AutomataState> destStates=state.outs;
			ArrayList<String> transitions=state.transitions;
			
			for(int i=0; i<destStates.size();i++){//for each of the transitions
				if(transitions.get(i)==null)continue;//ignore epsilon transitions
				HashSet<AutomataState>closure=tableLine.get(transitions.get(i));
				if(closure==null){//if no closure is there already
					closure=new HashSet<AutomataState>();
					tableLine.put(transitions.get(i), closure);//put the current closure there for this transition
				}
				closure.add(destStates.get(i));//put the curent transition states in the closure
			}
			
		}
		
		return tableLine;
	}

	private static boolean isIdenticalHashSet (HashSet<AutomataState> h1, HashSet<AutomataState> h2) {
	    if ( h1.size() != h2.size() ) {
	        return false;
	    }
	    HashSet<AutomataState> clone = new HashSet<AutomataState>(h2); // just use h2 if you don't need to save the original h2
	    
	    Iterator<AutomataState> it = h1.iterator();
	    while (it.hasNext() ){
	        AutomataState state= it.next();
	        if (clone.contains(state)){ // replace clone with h2 if not concerned with saving data from h2
	            clone.remove(state);
	        } else {
	            return false;
	        }
	    }
	    return true; // will only return true if sets are equal
	}
}
