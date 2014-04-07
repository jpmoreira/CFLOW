import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInteractor {
	
	String fileName;
	String regex;
	
	public void gatherUserInput(){
		
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
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
		
		File f = new File(fileName);
		if(!f.exists() || f.isDirectory())return false;
		
		return true;

		
	}

}
