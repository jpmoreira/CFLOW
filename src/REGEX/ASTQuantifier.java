/* Generated By:JJTree: Do not edit this line. ASTQuantifier.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package REGEX;

public
class ASTQuantifier extends SimpleNode {
	
	public int lowerBound;
	public int upperBound;
	public SimpleNode quantified;
  public ASTQuantifier(int id) {
    super(id);
  }

  public ASTQuantifier(EG2 p, int id) {
    super(p, id);
  }

}
/* JavaCC - OriginalChecksum=5592254d0e37304e2ba3729447431839 (do not edit this line) */
