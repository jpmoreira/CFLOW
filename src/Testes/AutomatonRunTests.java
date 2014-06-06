package Testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DFA.Automaton;
import DFA.Closure;
import DFA.NFA;
import REGEX.EG2;
import REGEX.ParseException;
import REGEX.SimpleNode;

public class AutomatonRunTests {
	
	@Test
	public void test0(){
		SimpleNode s;
		try {
			
			String str = "(\"a\"){3}\n";
			InputStream stream = new ByteArrayInputStream(str.getBytes());
			EG2 myEG=new EG2(stream);
			s = myEG.Regex();
			
			NFA a=new NFA((SimpleNode)s.jjtGetChild(0));
			
			try {
				Automaton.getAutomaton(a.simplified_dfa_table());
			} catch (IOException e) {
				fail("Exception Thrown");
			} catch (ClassNotFoundException e) {
				fail("Exception Thrown");
			}
			
			Automaton.consume("a");
			Automaton.consume("a");
			Automaton.consume("a");
			
			Automaton.validateResult();
			
		} catch (ParseException e) {
			fail("exception thrown but shouldnt");
		}
		
	}
	
	
	@Test
	public void test1(){
		
		Closure.resetClosures();
		SimpleNode s;
		try {
			
			String str = "(\"a\"){3}\n";
			InputStream stream = new ByteArrayInputStream(str.getBytes());
			EG2 myEG=new EG2(stream);
			s = myEG.Regex();
			
			NFA a=new NFA((SimpleNode)s.jjtGetChild(0));
			
			try {
				Automaton.getAutomaton(a.simplified_dfa_table());
			} catch (IOException e) {
				fail("Exception Thrown");
			} catch (ClassNotFoundException e) {
				fail("Exception Thrown");
			}
			
			Automaton.consume("a");
			Automaton.consume("a");
			//Automaton.consume("a");
			
			Automaton.validateResult();
			
		} catch (ParseException e) {
			fail("exception thrown but shouldnt");
		}
		
	}
}
