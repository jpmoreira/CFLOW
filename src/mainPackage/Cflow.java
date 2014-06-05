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

import automaton.Automaton;
import automaton.State;
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
	static private final String executionDirectoryName="_cflow_bin";
	static private final String[] automataClassFiles={Automaton.class.getResource("Automaton.class").getPath(),State.class.getResource("State.class").getPath()};
	
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
		
		java.net.URL location = Cflow.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.getFile());
		SimpleNode tree;
		
		if(args.length<3){
			System.out.println("Invalid Usage");
			printUsage();
			return;
			
		}
		String regex=args[0]+"\n";
		
		File sourceDir=new File(getSourceDirName(args));
		if(!sourceDir.exists() || !sourceDir.isDirectory()){
			System.out.println("Invalid directory choosen.");
			printUsage();
			return;
		}
		
		File backupDir=createDirectoryWithAbsolutePath(sourceDir.getAbsolutePath()+"/"+tempDirectoryName);
		
		
		if(shouldRestore(args)){
			restore(sourceDir, backupDir);
			System.out.println("Restore Sucessfull.");
			return;
			
		}
		
		try {
			backupSourceCodeDir(sourceDir, backupDir);
		} catch (Exception e) {
			System.out.println("Impossible to backup source code. Execution will be aborted");
			return;
		}
		
		
	
		
		String compilerFlags=getCompilerFlags(args);
		String executeParams=getExecuteParams(args);
		
		
		InputStream regexStream = new ByteArrayInputStream(regex.getBytes());
		new EG2(regexStream);
		try {
			tree = EG2.Regex();
		} catch (ParseException e) {
			System.out.println("Invalid Regex Expression passed: "+regex);
		}
		
		
		
		if(!copyAutomataAndRelatedClassesToTempFolder())return;
		
		try {
			compile(sourceFiles);
		} catch (IOException e) {
			System.out.println("Unable To Compile");
		} catch (InterruptedException e) {
			System.out.println("Unable to compile");
		}
		
		//removeTempDirAndContent();

	}

	public static void restore(File sourceDir,File backupDir){
		
		
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
		
		if(operationItem.isFile() && operationItem.isFile() && operationItem.getName().equals(backupItem)){
			
			copyFile(backupItem, operationItem);
		}
		if(operationItem.isDirectory() && backupItem.isDirectory() && operationItem.getName().equals(backupItem.getName())){
			
			for (File backup : backupItem.listFiles()) {
				
				for (File  originalFile : operationItem.listFiles()) {
					
					if(backup.getName().equals(originalFile.getName())){
						
						if((backup.isFile() && originalFile.isFile()) || backup.isDirectory() && originalFile.isDirectory()){
							
							restoreFiles(originalFile,backup);
						}
						
					}
				}
				
			}
			
		}
		
	}
	
	public static String getSourceDirName(String[] args){
		
		for(int i=0;i<args.length-1;i++){
			
			if(args[i]=="-dir"){
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
	
	
	private static void printUsage(){
		
		System.out.println("USAGE:");
		System.out.println("	CFLOW -restore <source directory>");
		System.out.println("	CFLOW \"<REGEX>\"");
		System.out.println("	CFLOW \"<REGEX>\" -dir <source directory>");
		System.out.println("	CFLOW \"<REGEX>\" -dir <source directory> -compiler \"<parameters to compiler>\"");
		System.out.println("	CFLOW \"<REGEX>\" -dir <source directory> -execute \"<execution parameters>\" ");
		System.out.println("	CFLOW \"<REGEX>\" -dir <source directory> -compiler \"<parameters to compiler>\" -execute \"<execution parameters>\" ");
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
	
	private static void removeTempDirAndContent(){
		purgeDirectory(tempDirectory);
		tempDirectory.deleteOnExit();
		
	}
	
	private static boolean copyAutomataAndRelatedClassesToTempFolder(){
		
		
		for (String classFile : automataClassFiles) {
			File inputFile=new File(classFile);
			String[] classFileNames=classFile.split("/");
			String classFileName=classFileNames[classFileNames.length-1];
			
			File output=fileInCurrentDirectoryWithName(tempDirectoryName+"/"+classFileName);
			try {
				copyFile(inputFile, output);
			} catch (IOException e) {
				System.out.println("Unable To move automata class file");
				return false;
			}
			
		
			
		}
		return true;
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
		
	private static void compile(String[] sources) throws IOException, InterruptedException{
		
		ArrayList<String> l=new ArrayList<String>();
		l.add("javac");
		//l.add("-classpath "+"\""+tempDirectory.getAbsolutePath()+"\"");
		//l.add("-d "+tempDirectory.getAbsolutePath());
		for (String sourceFile : sources) {
			l.add(sourceFile);
		}
		
		
		
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(l.toArray(new String[l.size()]));
		//output both stdout and stderr data from proc to stdout of this process
		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream());
		StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());
		errorGobbler.start();
		outputGobbler.start();
		proc.waitFor();
		
		
		
		
		
		
	}
	static void copy(InputStream in, OutputStream out) throws IOException {
	    while (true) {
	      int c = in.read();
	      if (c == -1) break;
	      out.write((char)c);
	    }
	  }
}
