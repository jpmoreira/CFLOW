package Testes;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import DFA.AutomataState;
import DFA.Closure;
import DFA.NFA;
import REGEX.EG2;
import REGEX.ParseException;
import REGEX.SimpleNode;

public class RegexToDFATests {

	@Test
	public void test3Times() {
		
		SimpleNode s;
		try {
			
			String str = "(\"a\"){3}\n";
			InputStream stream = new ByteArrayInputStream(str.getBytes());
			EG2 myEG=new EG2(stream);
			s = myEG.Regex();
			
			NFA a=new NFA((SimpleNode)s.jjtGetChild(0));
			
			AutomataState state=a.start;
			System.out.println(state.closure().toString());
			
			System.out.println("DFA Table");
			a.printDFATable();
			System.out.println("NFA Table");
			a.printTable();
			
			HashMap<Integer,AutomataState> allStates=AutomataState.getAllStates();
			Set<Integer> keys=allStates.keySet();
			for (Integer integer : keys) {
				AutomataState myState=allStates.get(integer);
				System.out.println(myState.getId()+" e final "+myState.isFinal());
				System.out.println(myState.getId()+" e inicial "+myState.isInitial());
			}
			
			
			
		} catch (ParseException e) {
			fail("exception thrown but shouldnt");
		}
		
	}
	
	@Test
	public void testAStar() {
		
		Closure.resetClosures();
		SimpleNode s;
		try {
			
			String str = "\"a\"*\n";
			InputStream stream = new ByteArrayInputStream(str.getBytes());
			EG2 myEG=new EG2(stream);
			s = myEG.Regex();
			
			NFA a=new NFA((SimpleNode)s.jjtGetChild(0));
			
			AutomataState state=a.start;
			System.out.println(state.closure().toString());
			
			System.out.println("DFA Table");
			a.printDFATable();
			System.out.println("NFA Table");
			a.printTable();
			
			
			HashMap<Integer,AutomataState> allStates=AutomataState.getAllStates();
			Set<Integer> keys=allStates.keySet();
			for (Integer integer : keys) {
				AutomataState myState=allStates.get(integer);
				System.out.println(myState.getId()+" e final "+myState.isFinal());
				System.out.println(myState.getId()+" e inicial "+myState.isInitial());
			}
			
			
		} catch (ParseException e) {
			fail("exception thrown but shouldnt");
		}
		
	}
	
	
	@Test
	public void testAPlus() {
		
		Closure.resetClosures();
		SimpleNode s;
		try {
			
			String str = "\"a\"+\n";
			InputStream stream = new ByteArrayInputStream(str.getBytes());
			EG2 myEG=new EG2(stream);
			s = myEG.Regex();
			
			NFA a=new NFA((SimpleNode)s.jjtGetChild(0));
			
			AutomataState state=a.start;
			System.out.println(state.closure().toString());
			
			System.out.println("DFA Table");
			a.printDFATable();
			System.out.println("NFA Table");
			a.printTable();
			
			
			HashMap<Integer,AutomataState> allStates=AutomataState.getAllStates();
			Set<Integer> keys=allStates.keySet();
			for (Integer integer : keys) {
				AutomataState myState=allStates.get(integer);
				System.out.println(myState.getId()+" e final "+myState.isFinal());
				System.out.println(myState.getId()+" e inicial "+myState.isInitial());
			}
			
			
		} catch (ParseException e) {
			fail("exception thrown but shouldnt");
		}
		
	}
	
	@Test
	public void testComplex() {
		
		Closure.resetClosures();
		SimpleNode s;
		try {
			
			String str = "(\"a\"|\"b\")+\n";
			InputStream stream = new ByteArrayInputStream(str.getBytes());
			EG2 myEG=new EG2(stream);
			s = myEG.Regex();
			
			NFA a=new NFA((SimpleNode)s.jjtGetChild(0));
			
			AutomataState state=a.start;
			System.out.println(state.closure().toString());
			
			System.out.println("DFA Table");
			a.printDFATable();
			System.out.println("NFA Table");
			a.printTable();
			
			
			HashMap<Integer,AutomataState> allStates=AutomataState.getAllStates();
			Set<Integer> keys=allStates.keySet();
			for (Integer integer : keys) {
				AutomataState myState=allStates.get(integer);
				System.out.println(myState.getId()+" e final "+myState.isFinal());
				System.out.println(myState.getId()+" e inicial "+myState.isInitial());
			}
			
			
		} catch (ParseException e) {
			fail("exception thrown but shouldnt");
		}
		
	}


}
