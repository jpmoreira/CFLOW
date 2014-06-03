package mainPackage;

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

import javax.print.DocFlavor.URL;
import javax.xml.crypto.KeySelector.Purpose;

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
	static private final String tempFilePrefix="_cflow_";
	static private final String automataClassName="Automat";
	
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
		
		String[] sourceFiles=getSourceFiles(args);
		
		if(sourceFiles.length==0){
			System.out.println("No source files provided");
			printUsage();
			return;
		}
		
		if(!verifyFiles(sourceFiles))return;
		
		String compilerFlags=getCompilerFlags(args);
		String executeParams=getExecuteParams(args);
		
		
		InputStream regexStream = new ByteArrayInputStream(regex.getBytes());
		new EG2(regexStream);
		try {
			tree = EG2.Regex();
		} catch (ParseException e) {
			System.out.println("Invalid Regex Expression passed: "+regex);
		}
		
		
		try{
			copyFilesToTmpDir(sourceFiles);
		}catch(IOException e){
			System.out.println("Error creating copy of files");
			return;
		}
		
		
		removeTempDirAndContent();

	}
	
	
	private static String[] getSourceFiles(String[] args){
		
		boolean isSourceFile=false;
		
		ArrayList<String> sources=new ArrayList<String>();
		for(int i=0;i<args.length;i++){
			
			if(isSourceFile){
				if(args[i].matches("-.+"))break;
				sources.add(args[i]);
			}
			
			else if(args[i].equals("-source"))isSourceFile=true;
			
		}
		
		return sources.toArray(new String[sources.size()]);
		
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
			File f=fileWithName(fileName);
			if(f==null || !f.exists() || !f.isFile()){
				System.out.println("File : "+fileName+" does not exist.");
				return false;
			}
		}
		return true;
	} 
	
	private static void copyFilesToTmpDir(String[] sources) throws IOException{
		
		
		tempDirectory=createDirectoryWithName(tempDirectoryName);
		 
		 for (String sourceName : sources) {
			 String tmpFileName=tempDirectoryName+"/"+tempFilePrefix+sourceName;
			 InputStream is = null;
			 OutputStream os = null;
			 File outputFile=fileWithName(tmpFileName);
			 outputFile.createNewFile();
			 try {
				 
				 is = new FileInputStream(fileWithName(sourceName));
				 os = new FileOutputStream(outputFile);
				 byte[] buffer = new byte[1024];
				 int length;
				 while ((length = is.read(buffer)) > 0) {
					 os.write(buffer, 0, length);
				 }
			 } finally {
				 is.close();
				 os.close();
			 }
			 
			
		}
		
	}
	private static void printUsage(){
		
		System.out.println("USAGE:");
		System.out.println("	CFLOW \"<REGEX>\" -source <file1.java> <file2.java> <...> <filen.java>");
		System.out.println("	CFLOW \"<REGEX>\" -source <file1.java> <file2.java> <...> <filen.java> -compiler \"<parameters to compiler>\"");
		System.out.println("	CFLOW \"<REGEX>\" -source <file1.java> <file2.java> <...> <filen.java> -execute \"<execution parameters>\" ");
		System.out.println("	CFLOW \"<REGEX>\" -source <file1.java> <file2.java> <...> <filen.java> -compiler \"<parameters to compiler>\" -execute \"<execution parameters>\" ");
	}
	
	private static File fileWithName(String name){
		
		
		String path=System.getProperty("user.dir");
		path=path.concat("/"+name);
		
		File f=null;
		f= new File(path);
		try {
			if(!f.exists())f.createNewFile();
		} catch (IOException e) {}
		
		
		return f;
	}

	private static File createDirectoryWithName(String name){
		
		String path=System.getProperty("user.dir");
		path=path.concat("/"+name);
		File f=new File(path);
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
}
