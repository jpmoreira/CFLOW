package Testes;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import AnnotationGrammar.AnnotationParser;
import AnnotationGrammar.ParseException;
import AnnotationGrammar.SimpleNode;

public class AnnotationParserTest {
	
	public static InputStream reteriveByteArrayInputStream(String fileName) {

		
		return AnnotationParserTest.class.getResourceAsStream("/Testes/input1");
		

	}

	@Test
	public void test() {
		try {
			
			InputStream stream = AnnotationParserTest.reteriveByteArrayInputStream("input1");
			
			AnnotationParser obj=new AnnotationParser(stream);
	    	SimpleNode s=obj.Annotations();
	    	s.dump("");
	    	
			
			assertEquals("", "");
		} catch (ParseException e) {
			fail("exception thrown but shouldnt");
		
	}

}
}
