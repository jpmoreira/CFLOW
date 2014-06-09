package testsuite;

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
			
			String str = "(\"a\"){3}\n";
			InputStream stream = new ByteArrayInputStream(str.getBytes());
			EG2 myEG=new EG2(stream);
			s = myEG.Regex();
			
			assertEquals("", "");
		} catch (ParseException e) {
			fail("exception thrown but shouldnt");
		}
	}

}
