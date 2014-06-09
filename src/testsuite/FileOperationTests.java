package testsuite;

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
	public void testRestoreFiles(){
		
	
		File source=new File("/Users/vmineiro/Desktop/original");
		File backUp=new File("/Users/vmineiro/Desktop/original/_cflow_tmp/");
		File restore=new File("/Users/vmineiro/Desktop/original/_cflow_tmp/original");
		
		try {
			Cflow.backupSourceCodeDir(source,backUp);
		} catch (IOException e) {
			fail("error");
		}
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			fail("error");
		}
		
		try {
			Cflow.restoreFiles(source, restore);
		} catch (IOException e) {
			fail("error");
		}
		
		
		
	}


}
