package Testes;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import mainPackage.Cflow;

import org.junit.Test;

public class FileOperationTests {

	@Test
	public void testCopyDir() {
		
		String originalDir;
		String destinationDir;
		Scanner in = new Scanner(System.in);
		 
	      System.out.println("Enter source folder");
	      originalDir = in.nextLine();
	 
	      System.out.println("Enter backup folder");
	      destinationDir = in.nextLine();
	      
	      File source=new File(originalDir);
	      File dest=new File(destinationDir);
	      
	      try {
			Cflow.backupSourceCodeDir(source, dest);
		} catch (IOException e) {
			fail("error");
		}
	      
	      assertTrue(true);
	     
		
	}
	@Test
	public void testMoveJarFile(){
		
		try {
			Cflow.moveJarToSourceDir(new File("/Users/mppl/Desktop/ola"));
		} catch (IOException e) {
			fail("Exception Thrown");
		}
		
	}

}
