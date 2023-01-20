package libraryMain;

import libraryCore.ArithmeticExpressionTree;

public class ArithmeticOperatorsPrint {

	public static String printAdd(Node a, Node b) throws Exception {
		return "(" + ArithmeticExpressionTree.printAST(a) + " + " + ArithmeticExpressionTree.printAST(b) + ")";
	}
	
	public static String printMult(Node a, Node b) throws Exception {
		return "(" + ArithmeticExpressionTree.printAST(a) + " * " + ArithmeticExpressionTree.printAST(b) + ")";
	}
	
	public static String printExp(Node a, Node b) throws Exception {
		return "(" + ArithmeticExpressionTree.printAST(a) + " ^ " + ArithmeticExpressionTree.printAST(b) + ")";
	}
	
	public static String printSqrt(Node a) throws Exception {
		return "√" + ArithmeticExpressionTree.printAST(a);
	}
}
