package libraryMain;

import libraryCore.ArithmeticExpressionTree;

public class ArithmeticExpressionEvaluation {
	public static double evaluateAdd(Node a, Node b) throws Exception {
		return ArithmeticExpressionTree.evaluateAST(a) + ArithmeticExpressionTree.evaluateAST(b);
	}
	
	public static double evaluateMul(Node a, Node b) throws Exception {
		return ArithmeticExpressionTree.evaluateAST(a) * ArithmeticExpressionTree.evaluateAST(b);
	}
	
	public static double evaluateExp(Node a, Node b) throws Exception {
		return Math.pow(ArithmeticExpressionTree.evaluateAST(a), ArithmeticExpressionTree.evaluateAST(b));
	}
	
	public static double evaluateSqrt(Node a) throws Exception {
		return Math.sqrt(ArithmeticExpressionTree.evaluateAST(a));
	}
}
