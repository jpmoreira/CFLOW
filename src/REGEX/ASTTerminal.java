/* Generated By:JJTree: Do not edit this line. ASTTerminal.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package REGEX;

public
class ASTTerminal extends SimpleNode {
	public int upperBound=1;
	public int lowerBound=1;
	public boolean isTrueTerminal;
	public String idString;
  public ASTTerminal(int id) {
    super(id);
  }

  public ASTTerminal(EG2 p, int id) {
    super(p, id);
  }

}
/* JavaCC - OriginalChecksum=3b4b316d3e647093adc3eff0cc2469ce (do not edit this line) */
