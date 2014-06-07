package Testes;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import mainPackage.CodeReplacer;

import org.junit.Test;

import AnnotationGrammar.ASTCflow;
import AnnotationGrammar.AnnotationParser;
import AnnotationGrammar.ParseException;
import AnnotationGrammar.SimpleNode;

public class CodeReplacerTests {

	@Test
	public void test1() throws IOException {

		String inputAbsolutePath = CodeReplacerTests.class.getResource(
				"/Testes/input1").getPath();

		String outputAbsolutePath =  CodeReplacerTests.class.getResource(
				"CodeReplacerTests.class").getPath();
		outputAbsolutePath=outputAbsolutePath.replaceAll("CodeReplacerTests.class", "inputReplaced1");
		CodeReplacer replacer = new CodeReplacer();

		replacer.replaceCflowTags(inputAbsolutePath, outputAbsolutePath);

	}
	@Test
	public void test2() throws IOException {

		

		CodeReplacer replacer = new CodeReplacer();

		String inputAbsolutePath = CodeReplacerTests.class.getResource(
				"/Testes/input2").getPath();
		String outputAbsolutePath =  CodeReplacerTests.class.getResource(
				"CodeReplacerTests.class").getPath();
		outputAbsolutePath=outputAbsolutePath.replaceAll("CodeReplacerTests.class", "inputReplaced2");
		System.out.println(outputAbsolutePath);

		replacer.replaceCflowTags(inputAbsolutePath, outputAbsolutePath);

	}
}
