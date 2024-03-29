package mainPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AnnotationGrammar.ASTCflow;


public class CodeReplacer {
	
	private final String callStart="Automaton.consume(\"";
	private final String callEnd="\");\n";
	private final String importStatement="\nimport DFA.Automaton;\n";
	
	private int lineNumber=1;
	
	
	
	public CodeReplacer() {
		
	}
	
	
	public void replaceCflowTags(String inputFile,String outputFile) throws IOException{
		
		lineNumber=1;
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
		
		String input = "";
		int readChar;
		char c;
		boolean placedImport=false;
		

		readChar= reader.read();
		while(readChar!=-1){
			
			c=(char)readChar;
			if (!placedImport){
				
				Pattern p = Pattern.compile(".*package.*;",Pattern.DOTALL);
				Matcher m = p.matcher(input);
				if(m.matches()){
					input+=importStatement;
					placedImport=true;
				}
			}
			if(c=='\n')lineNumber++;
			if(c=='@'){
				input+=attemptReplacement(reader,outputFile);
			}
			else input+=c;
			
			readChar=reader.read();
			
		}

		reader.close();
		
		
		if(!placedImport){//if no package declaration
			input=importStatement+input;//place import at the start of the file anyway
		}
	
		
		
		File file = new File(outputFile);  
		PrintWriter writer = new PrintWriter(file);  
		writer.print(input);
		writer.close();
		
		
		
		
		
		
		
		
	}
	
	
	public String attemptReplacement(BufferedReader reader,String fileName) throws IOException{
		
		String potentialCflowTag="@";
		char c = 0;
		int output=0;
		while(c!='\n' ){
			output=reader.read();
			if(output==-1)return potentialCflowTag;
			c=(char)output;
			potentialCflowTag+=c;
		}
		
		
		if(potentialCflowTag.matches("@CFLOW\\s*[a-zA-Z]\\w*\\s*\n")){
			
			potentialCflowTag=potentialCflowTag.replace("@CFLOW","");
			potentialCflowTag=potentialCflowTag.replaceAll("\\s+", "");
			potentialCflowTag=potentialCflowTag.replace("\n","");
			potentialCflowTag=callStart+potentialCflowTag+"\","+lineNumber+",\""+fileName+callEnd;
		}
		
		if(c=='\n')lineNumber++;
		
		
		
		
		
		return potentialCflowTag;
		
		
		
	}
	
	

}
