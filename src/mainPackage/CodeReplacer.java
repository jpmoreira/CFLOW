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

import AnnotationGrammar.ASTCflow;


public class CodeReplacer {
	
	private final String callStart="Automaton.consume(\"";
	private final String callEnd="\");\n";
	
	
	
	public CodeReplacer() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void replaceCflowTags(String inputFile,String outputFile) throws IOException{
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
		
		String input="";
		String temp="";
		int readChar;
		char c;
		

		readChar= reader.read();
		while(readChar!=-1){
			
			c=(char)readChar;
			if(c=='@'){
				input+=attemptReplacement(reader);
			}
			else input+=c;
			
			readChar=reader.read();
			
		}
	
	
		
		
		File file = new File(outputFile);  
		FileWriter writer = new FileWriter(file, true);  
		writer.write(input);
		writer.close();
		
		
		
		
		
		
		
		
	}
	
	
	public String attemptReplacement(BufferedReader reader) throws IOException{
		
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
			potentialCflowTag=callStart+potentialCflowTag+callEnd;
		}
		
		
		
		
		
		return potentialCflowTag;
		
		
		
	}
	
	

}
