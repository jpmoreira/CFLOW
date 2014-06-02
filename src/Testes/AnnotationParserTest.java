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


		return AnnotationParserTest.class.getResourceAsStream("/Testes/"+fileName);


	}

	@Test
	public void test1() {
		try {
			
			InputStream stream = AnnotationParserTest.reteriveByteArrayInputStream("input1");
			
			AnnotationParser obj=new AnnotationParser(stream,"UTF-8");
			SimpleNode s=obj.Annotations();
			s.dump("");
			
			
			assertEquals("", "");
		} catch (ParseException e) {
			fail("exception thrown but shouldnt");
			
		}
		
	}
	@Test
	public void test2() {
		try {

			InputStream stream = AnnotationParserTest.reteriveByteArrayInputStream("input2");

			AnnotationParser obj=new AnnotationParser(stream);
			SimpleNode s=obj.Annotations();
			s.dump("");


			assertEquals("", "");
		} catch (ParseException e) {
			fail("exception thrown but shouldnt");

		}

	}

	
}
