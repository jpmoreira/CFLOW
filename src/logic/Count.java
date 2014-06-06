package logic;

import java.util.List;
import java.util.Map;

class Count extends Regex {
Regex r;

public Count(Regex r) {
 this.r = r; 
}

public Nfa mkNfa(Nfa.NameSource names) {
 Nfa nfa1 = r.mkNfa(names);
 Integer s0s = names.next();
 Nfa nfa0 = new Nfa(s0s, s0s);
 for (Map.Entry<Integer,List<Nfa.Transition>> entry : nfa1.getTrans().entrySet())
   nfa0.addTrans(entry);
 
 
 nfa0.addTrans(s0s, null, nfa1.getStart());
 nfa0.addTrans(nfa1.getExit(), null, s0s);
 return nfa0;
}
}