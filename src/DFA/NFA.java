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
	
	public AutomataState start;
	
	
	public NFA(SimpleNode tree){
		
		composeNFA(tree);
		
		start=workingStack.peek().start;
		
	}
	
	public NFA(AutomataState state){
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
			if(frag==null)return;
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
	
	
	public void printTable(){
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


	public HashMap<Integer, HashMap<String,Integer>> simplified_dfa_table(){
		
		HashMap<Integer, HashMap<String,Integer>> simplifiedTable=new HashMap<Integer, HashMap<String,Integer>>();
		
		HashMap<Closure , HashMap<String,Closure> > normalTable=this.dfa_table();
		
		Set<Closure> keyLines=normalTable.keySet();
		
		for (Closure closure : keyLines) {
			
			HashMap<String,Closure> normalLine=normalTable.get(closure);
			HashMap<String,Integer> simplifiedLine=new HashMap<String, Integer>();
			
			Set<String> lineKeys=normalLine.keySet();
			for (String string : lineKeys) {
				simplifiedLine.put(string, normalLine.get(string).getID());//place a similar element but with the id
			}
			simplifiedTable.put(closure.getID(), simplifiedLine);
			
			
		}
		
		return simplifiedTable;
		
	}
	
	HashMap<Closure , HashMap<String,Closure> > dfa_table(){
		
		
		HashMap<Closure , HashMap<String,Closure> > table= new HashMap<Closure, HashMap<String,Closure>>();
		
		Closure firstStateClosure=this.start.closure();
		ArrayList<Closure> closuresToProcess=new ArrayList<Closure>();//will save the closures not yet processed
		closuresToProcess.add(firstStateClosure);
		
		while(closuresToProcess.size()>0){//while we have closures to process
		
			//get last closure to process and delete it (aka pop)
			Closure processingClosure=closuresToProcess.get(closuresToProcess.size()-1);
			closuresToProcess.remove(processingClosure);
			
			//System.out.print("Closure "+processingClosure.toString()+" "+processingClosure.getID()+" with Transitions: ");
			
			HashMap<String,Closure> tableLine=this.dfa_lineForClosure(processingClosure);
			
			table.put(processingClosure, tableLine);//place line in the table
			
			Set<String> allTransitions=tableLine.keySet();
			
			
			for (String transition : allTransitions) {
				
				
				
				Closure closure=tableLine.get(transition);
				//System.out.print(transition+"->"+closure.toString()+" "+closure.getID());
				
				if(table.get(closure)==null)closuresToProcess.add(closure);
				
				
				
			}
			
			System.out.println("");
			
		}
		
		return table;
		
	}
	
	
	HashMap<String,Closure> dfa_lineForClosure(Closure groupOfStates){
	
		
		HashMap<String, Closure> tableLineToReturn =new HashMap<String, Closure>();
		
		HashMap<String, HashSet<AutomataState>> tableLine =new HashMap<String, HashSet<AutomataState>>();
		
		
		
		for (AutomataState state : groupOfStates.getStates()) {//for each state in the closure
			
			ArrayList<AutomataState> destStates=state.outs;
			ArrayList<String> transitions=state.transitions;
			
			for(int i=0; i<destStates.size();i++){//for each of the transitions
				if(transitions.get(i)==null)continue;//ignore epsilon transitions
				HashSet<AutomataState>closure=tableLine.get(transitions.get(i));
				
				if(closure==null){//if no closure is there already
					closure=new HashSet<AutomataState>();
					tableLine.put(transitions.get(i), closure);//put the current closure there for this transition
				}
				
				Closure destenyStateClosure=destStates.get(i).closure();//get the closure of the destination
				for (AutomataState automataState : destenyStateClosure.states) {
					closure.add(automataState);
				}
			}
			
		}
		
		Set<String> keys=tableLine.keySet();
		
		for (String string : keys) {
			
			tableLineToReturn.put(string, Closure.getClosureWithStates(tableLine.get(string)));
			
		}
		
		return tableLineToReturn;
	}

	
	public void printDFATable(){
		
		HashMap<Closure , HashMap<String,Closure> > table=this.dfa_table();
		
		Set<Closure> closures=table.keySet();
		
		for (Closure closure : closures) {
			
			System.out.print(""+closure.getID()+": ");
			
			Set<String> strings=table.get(closure).keySet();
			for (String string : strings) {
				System.out.print(string+"->"+table.get(closure).get(string).getID()+"  ");
			}
			
			System.out.println("");
		}
		
	}

}
