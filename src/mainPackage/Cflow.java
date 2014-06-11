package mainPackage;

import java.awt.List;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.print.DocFlavor.URL;
import javax.xml.crypto.KeySelector.Purpose;

import DFA.NFA;
import DFA.Automaton;
import REGEX.EG2;
import REGEX.ParseException;
import REGEX.SimpleNode;



/**
 * 
 * Main class.
 *
 *
 *
 */
public class Cflow {

	
	static private final String tempDirectoryName="_cflow_tmp";
	
	static File tempDirectory=null;
	
	public static void main(String[] args)  {
		
		
		/***
		 * 
		 * 
		 * CLFOW -source 3 a.java b.java c.java -compiler " some parameters"
		 * 
		 * 
		 * javac "some parameters "
		 * 
		 * 
		 */
		
		/*
		 * 
		 * Get Source files
		 * Replace COde in source files
		 * Append Automata source file
		 * How to use java compiler http://stackoverflow.com/questions/2946338/how-do-i-programmatically-compile-and-instantiate-a-java-class
		 * Launch program in another process
		 * Verificar o termino
		 * Desserializar classe automato
		 * Verificar estado 
		 * Mostrar output
		 * 
		 * 
		 * 
		 * 
		 * */
		

		SimpleNode tree = null;
		
		if((args.length == 2 && args[0].equals("-restore")) ||  args.length != 9 ){
			System.out.println("Invalid Usage");
			printUsage();
			return;
			
		}
		
		String compilerFlags=getCompilerFlags(args);
		String executeParams=getExecuteParams(args);
		String automataPath=getAutomataJar(args);
		
		if (compilerFlags == null || executeParams == null || automataPath==null) {
			System.out.println("Invalid Usage");
			printUsage();
			return;
		}
		
		File automataFile=new File(automataPath);
	
		
		String regex=args[0]+"\n";
		
		File sourceDir=new File(getSourceDirName(args));
		
		System.out.println("Working on source directory: " + sourceDir.getAbsolutePath());
		
		if(!sourceDir.exists() || !sourceDir.isDirectory()){
			System.out.println("Invalid directory choosen.");
			printUsage();
			return;
		}
		
		File backupDir=createDirectoryWithAbsolutePath(sourceDir.getAbsolutePath()+"/"+tempDirectoryName);
		
		
		
		
		if(shouldRestore(args)){
			//restore(sourceDir, backupDir);
			System.out.println("Restore Sucessfull.");
			return;
		}
		
		try {
			backupSourceCodeDir(sourceDir, backupDir);
		} catch (Exception e) {
			System.out.println("Impossible to backup source code. Execution will be aborted");
			return;
		}
		
		System.out.println("Created backup directory: " + backupDir.getAbsolutePath());
		
		
		try {
			replaceTags(sourceDir,new CodeReplacer());
		} catch (IOException e2) {
			System.out.println("Unable to replace tags. Execution will be aborted");
			return;
		}
		
	
		try {
			if(!moveJarToSourceDir(automataFile,sourceDir)){
				System.out.println("Original jar does not exist at "+automataFile.getAbsolutePath()+". Execution will be aborted");
				return;
			}
		} catch (IOException e1) {
			System.out.println("Unable to move jar file to desired place.Execution will be aborted");
			return;
		}
		
		System.out.println("Moved '.jar' file to: " + sourceDir.getAbsolutePath());
		
		
		InputStream regexStream = new ByteArrayInputStream(regex.getBytes());
		EG2 eg = new EG2(regexStream);
		try {
			tree = eg.Regex();
		} catch (ParseException e) {
			System.out.println("Invalid Regex Expression passed: "+regex);
			return;
		}
		
		NFA nfa = new NFA((SimpleNode) tree.jjtGetChild(0));
		
		try {
			Automaton.getAutomaton(nfa.simplified_dfa_table());
		} catch (IOException e1) {
			System.out.println("Error loading states transition table. Execution will be aborted.");
			return;
		} catch (ClassNotFoundException e1) {
			System.out.println("Error loading states transition table. Execution will be aborted.");
			return;
		}
		
		
		compilerFlags = "-cp " + sourceDir + "/automata.jar " + compilerFlags;
		
		try {
			compile(compilerFlags);
		} catch (IOException e) {
			System.out.println("Unable To Compile");
			return;
		} catch (InterruptedException e) {
			System.out.println("Unable to compile");
			return;
		}

		
		executeParams = "java -cp " + sourceDir + "/automata.jar:" + sourceDir + " " + executeParams;
		System.out.println("Issuing command: "+executeParams);
		Runtime rt = Runtime.getRuntime();
		Process proc;
		try {
			proc = rt.exec(executeParams);
		} catch (IOException e) {
			System.out.println("Error running executable. Execution will be aborted.");
			return;
		}
		
		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream());
		StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());
		errorGobbler.start();
		outputGobbler.start();
		
		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			System.out.println("Could not wait for execution end. Execution will be aborted.");
			return;
		}
		
		Automaton.validateResult();
		
		try {
			restore(sourceDir, backupDir);
			//restoreFiles(sourceDir, new File(backupDir.getAbsolutePath().concat("/"+sourceDir.getName())));
		} catch (IOException e) {
			System.out.println("Unable to restore files");
		}
		
		
//		removeTempDirAndContent();

	}

	private static void replaceTags(File sourceDir, CodeReplacer c) throws IOException {
		
		
		for (File child: sourceDir.listFiles()){
			
			if (child.isDirectory()) 
				replaceTags(child,c);
			else if (child.isFile() && child.getName().matches(".*\\.java"))
				c.replaceCflowTags(child.getAbsolutePath(), child.getAbsolutePath());
			
		}
		
		
	}


	private static void restore(File source,File backup) throws IOException{
		
		File jarFile=new File(source.getAbsolutePath()+"/automata.jar");
		if(jarFile.exists())jarFile.delete();
		
		File rootBackup=new File(backup.getAbsolutePath().concat("/"+source.getName()));
		
		restoreFiles(source, rootBackup);
		
		deleteDir(backup);
		
		
		
		
	}
	
	private static boolean shouldRestore(String[] args){
		
		for (String string : args) {
			if(string.equals("-restore"))return true;
		}
		
		return false;
	}
	public static void backupSourceCodeDir(File operatingItem,File backupDir) throws IOException{
		

	
		if(operatingItem.exists() && operatingItem.isDirectory() && !operatingItem.getName().equals(tempDirectoryName)){
			File newBackupDir=null;
					
			for (File item : operatingItem.listFiles()) {
				if(newBackupDir==null)newBackupDir=createDirectoryWithAbsolutePath(backupDir.getAbsolutePath()+"/"+operatingItem.getName());
				backupSourceCodeDir(item,newBackupDir);
			}
		}
		else if (operatingItem.exists() && operatingItem.isFile()){
			
			String name=operatingItem.getName();
			if(name.matches(".*\\.java")){
				File copy=new File(backupDir.getAbsolutePath()+"/"+operatingItem.getName());
				copy.createNewFile();
				copyFile(operatingItem, copy);
			}
		}
		
	}
	
	
	public static void restoreFiles(File operationItem,File backupItem) throws IOException{
		
		if(operationItem.isDirectory() && backupItem.isDirectory() && operationItem.getName().equals(backupItem.getName())){
			
			for (File backup : backupItem.listFiles()) {
				
				for (File  originalFile : operationItem.listFiles()) {
					
					if(backup.getName().equals(originalFile.getName())){
						
						if(backup.isFile() && originalFile.isFile()){
							
							copyFile(backup, originalFile);
							
						}
						else if(backup.isDirectory() && originalFile.isDirectory()){
							restoreFiles(originalFile, backupItem);
						}
						
						
					}
				}
				
			}
			
		}
		
	}
	
	public static String getSourceDirName(String[] args){
		
		for(int i=0;i<args.length-1;i++){
			
			if(args[i].equals("-dir")){
				return args[i+1];
			}
		}
		return System.getProperty("user.dir");
	}
	
	private static String getCompilerFlags(String[] args){

		
		for(int i=0;i<args.length-1;i++){
			
			
			if(args[i].equals("-compiler"))return args[i+1];
			
		}
		
		return null;
		
	}
	
	private static String getExecuteParams(String[] args){

		
		for(int i=0;i<args.length-1;i++){
			
			
			if(args[i].equals("-execute"))return args[i+1];
			
		}
		
		return null;
		
	}
	
	private static boolean verifyFiles(String[] sourceFiles){
		
		for (String fileName : sourceFiles) {
			File f=fileInCurrentDirectoryWithName(fileName);
			if(f==null || !f.exists() || !f.isFile()){
				System.out.println("File : "+fileName+" does not exist.");
				return false;
			}
		}
		return true;
	} 
	
	
	private static String getAutomataJar(String[] args){
		
		for (int i = 0; i < args.length-1; i++) {
			if(args[i].equals("-automata")){
				return args[i+1];
			}
		}
		
		return null;
	}
	
	private static void printUsage(){
		
		System.out.println("USAGE:");
		System.out.println("	CFLOW -restore <source directory>");
		System.out.println("	CFLOW \"<REGEX>\" -dir <source directory> -compiler \"<parameters to compiler>\" -execute \"<execution parameters>\" -automata <automata file path>");
	}
	
	private static File fileInCurrentDirectoryWithName(String name){
		
		
		String path=System.getProperty("user.dir");
		path=path.concat("/"+name);
		
		File f=null;
		f= new File(path);
		try {
			if(!f.exists())f.createNewFile();
		} catch (IOException e) {}
		
		
		return f;
	}


	
	private static File createDirectoryWithAbsolutePath(String name){
		
	
		File f=new File(name);
		if(f.isDirectory())purgeDirectory(f);
		else f.mkdir();
		return f;
	}
	
	private  static void purgeDirectory(File dir) {
	    for (File file: dir.listFiles()) {
	        if (file.isDirectory()) purgeDirectory(file);
	        file.delete();
	    }
	}
	

	
	private static void copyFile(File source,File dest) throws IOException{
		FileInputStream in=null;
		FileOutputStream out=null;
		
		try {
			in = new FileInputStream(source);
			out = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			 int length;
			 while ((length = in.read(buffer)) > 0)out.write(buffer, 0, length);
			
		} finally{
			
			in.close();
			out.close();
		}
		 
		
	}
		
	private static void compile(String compilerFlags) throws IOException, InterruptedException{
		
		
		
		
		Runtime rt = Runtime.getRuntime();
		System.out.println("Issuing command: javac "+compilerFlags);
		Process proc = rt.exec("javac "+compilerFlags);//TODO not passing jar file 
		//output both stdout and stderr data from proc to stdout of this process
		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream());
		StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());
		
		System.out.println("JAVAC Output:");
		errorGobbler.start();
		outputGobbler.start();
		proc.waitFor();
		
		System.out.println("JAVAC Output end");	
		
	}
	static void copy(InputStream in, OutputStream out) throws IOException {
	    while (true) {
	      int c = in.read();
	      if (c == -1) break;
	      out.write((char)c);
	    }
	  }
	public static boolean moveJarToSourceDir(File originalJar,File directoryToPlaceJar) throws IOException{
		
	
		
		if(!originalJar.exists() || !originalJar.isFile())return false;
		File outputFile=new File(directoryToPlaceJar.getAbsolutePath()+"/automata.jar");
		outputFile.createNewFile();
		if(!outputFile.exists() || !outputFile.isFile())return false;
		copyFile(originalJar, outputFile);
		return true;
		
		
	}

	
	public static void deleteDir(File file)
	    	throws IOException{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	 
	    		   file.delete();
	 
	    		}else{
	 
	    		   //list all the directory contents
	        	   String files[] = file.list();
	 
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	 
	        	      //recursive delete
	        	     deleteDir(fileDelete);
	        	   }
	 
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	   }
	    		}
	 
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    	}
	    }
}
