package logic;

import java.io.IOException;

class Example {
public static void main(String[] args) 
 throws IOException {
 Regex a = new Sym("A");
 Regex b = new Sym("B");
 
 Regex r = new Seq(new Seq(a,a), new Seq(a, b));
 Regex o =  new Alt(r, r);
 Regex m  =  new Star(o);
 

 // The regular expression (a|b)*ab
 buildAndShow("dfa1.dot",m);
 // The regular expression ((a|b)*ab)*
}

public static void buildAndShow(String filename, Regex r) 
 throws IOException {
 Nfa nfa = r.mkNfa(new Nfa.NameSource());
 System.out.println(nfa);
 Dfa dfa = nfa.toDfa();
 System.out.println(dfa);
 System.out.println("Writing DFA graph to file " + filename);
 dfa.writeDot(filename);
 System.out.println();
}
}