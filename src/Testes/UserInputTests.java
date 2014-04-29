package Testes;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import mainPackage.UserInteractor;

import org.junit.Test;

import REGEX.ASTCONCAT;
import REGEX.ASTRegex;

public class UserInputTests {

	@Test
	public void validFileTest() {
		
		
		String str = "/Users/mppl/Dev/COMP-WS/CFLOW/src/Testes/UserInputTests.java\n";
		InputStream stream = new ByteArrayInputStream(str.getBytes());
		
		UserInteractor interactor=new UserInteractor();
		
		interactor.gatherFileName(stream);
		
		assertEquals(interactor.fileName, "/Users/mppl/Dev/COMP-WS/CFLOW/src/Testes/UserInputTests.java");
		
		
	}
	
	@Test
	public void validRegexTest(){
		
		String str= "(\"a\"|\"b\")\"b\"\n";
		InputStream stream = new ByteArrayInputStream(str.getBytes());
		UserInteractor interactor=new UserInteractor();
		interactor.gatherRegex(stream);
		assertEquals(ASTRegex.class, interactor.regexTree.getClass());
		ASTCONCAT concatNode=(ASTCONCAT) interactor.regexTree.jjtGetChild(0);
		assertEquals(ASTCONCAT.class,concatNode.getClass());
		
		
	}

}
