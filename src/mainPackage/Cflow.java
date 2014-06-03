package mainPackage;

import java.lang.reflect.Array;
import java.util.ArrayList;



/**
 * 
 * Main class.
 *
 *
 *
 */
public class Cflow {

	
	
	
	public static void main(String[] args) {
		
		
		

	}
	
	
	private static String[] getSourceFiles(String[] args){
		
		int nrSources=0;
		int firstSourceFileIndex=-1;
		ArrayList<String> sources=new ArrayList<String>();
		for(int i=0;i<args.length-2;i++){//-source should not be the last two arguments (number of sources should follow and then the sources itself)
			if(args[i]=="-source"){
				nrSources=Integer.parseInt(args[i+1]);
				firstSourceFileIndex=i;
				break;
			}
		}
		if(firstSourceFileIndex==-1)return null;//if not found return null
		
		for(int i=firstSourceFileIndex;i<args.length;i++){
			sources.add(args[i]);
		}
		
		return sources.toArray(new String[nrSources]);
		
	}

}
