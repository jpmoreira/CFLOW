package Testes;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

import REGEX.EG2;
import REGEX.ParseException;
import REGEX.SimpleNode;

public class ASTTests {

	@Test
	public void test1() {
		
    	SimpleNode s;
		try {
			
			String str = "\"a\"\"b\"\"c\"\n";
			InputStream stream = new ByteArrayInputStream(str.getBytes());
			new EG2(stream);
			s = EG2.Regex();
			
			assertEquals("", "");
		} catch (ParseException e) {
			fail("exception thrown but shouldnt");
		}
	}

}
