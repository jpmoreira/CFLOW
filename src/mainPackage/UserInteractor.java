package mainPackage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import REGEX.EG2;
import REGEX.ParseException;
import REGEX.SimpleNode;

public class UserInteractor {
	
	public String fileName;
	public SimpleNode regexTree;
	
	
	/**
	 * 
	 * A method that gathers the file name from the user and stores it in {@link UserInteractor#fileName} property
	 * @param stream
	 */
	public void gatherFileName(InputStream stream){
		
		
		if(stream==null)stream=System.in;//if no input stream provided then use System.in
		
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		
		
		while(!assertFileNameIsGood()){//while we don't have a file with a good name
			
			try {
				System.out.println("Please input the source file where the annotations are:");
				fileName=br.readLine();
			} catch (Exception e) {
				System.out.println("Invalid input");
				fileName=null;
			}
			
			
		}
	}
		
	private boolean assertFileNameIsGood(){
		
		if(fileName==null)return false;
		File f = new File(fileName);
		if(!f.exists() || f.isDirectory()){
		
			System.out.println("Non existing file");
			return false;
		}
		return true;

		
	}
	
	public boolean gatherRegex(InputStream stream){
		
		if(stream==null)stream=System.in;
		
		BufferedReader br= new BufferedReader(new InputStreamReader(stream));
		new EG2(br);
		
		while(true){
			
			try {
				SimpleNode root=EG2.Regex();
				regexTree=root;
				
				
			} catch (ParseException e) {
				System.out.println("Invalid REGEX expression. Please insert a valid one");
				continue;
			}
			
			return true;
			
		}
		
		
		
		
	}



}
