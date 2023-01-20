package libraryCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

import libraryMain.ArithmeticExpressionEvaluation;
import libraryMain.ArithmeticOperatorsPrint;
import libraryMain.Node;

public class ArithmeticExpressionTree {

	static String exp = "Add (Const -72.45) (Mult (Const 4) (Add (Sqrt (Const 8)) (Exp (Const 10) (Const 4))))";
	static HashMap<String, Double> variables = new HashMap<String, Double>();
	static ArrayList<String> validTokens = new ArrayList<String>();
	static Stack<Character> stack = new Stack<Character>();

	public static ArrayList<String> parse(String expression) throws Exception {
		ArrayList<String> tokens = new ArrayList<String>();
		int i = 0;
		while (i < expression.length()) {
			char ch = expression.charAt(i);
			if (ch == ' ') {
				i++;
				continue;
			}
			if (ch == '(' || ch == ')') {
				if (ch == '(') {
					stack.push(ch);
					tokens.add(Character.toString(ch));
				} else if (ch == ')') {
					if (stack.size() > 0) {
						if (stack.pop() != '(')
							throw new Exception("Brackets are not correctly added");
					} else if (stack.size() == 0) {
						throw new Exception("Brackets are not correctly added, found wrong bracket at position "
								+ expression.indexOf(ch));
					}
					tokens.add(Character.toString(ch));
				}

			} else {
				int j = i;
				while (j < expression.length() && expression.charAt(j) != ' ' && expression.charAt(j) != '('
						&& expression.charAt(j) != ')') {
					j++;
				}
				String token = expression.substring(i, j);

				String isValidToken = validateToken(token, i);

				if (isValidToken != "Valid token") {
					if (token.length() > 1) {
						throw new Exception("Invalid token " + token);
					}
					throw new Exception("Found an invalid Character at position " + i);
				}

				tokens.add(token);
				i = j - 1;
			}
			i++;

		}
		return tokens;
	}

	public static String validateToken(String token, int i) throws Exception {
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
		if (token.length() > 1) {
			if (Pattern.matches("[a-zA-Z]+", token)) {
				if (validTokens.contains(token)) {
					if (!Character.isUpperCase(token.charAt(0))) {
						return "Invalid token " + token.charAt(0) + " at position " + i;
					}
				} else {
					return "Invalid token" + token;
				} // "[0-9a-zA-Z]+"
			} else if (Pattern.matches("-?\\d+(\\.\\d+)?", token)) {
				if (!pattern.matcher(token).matches()) {
					return "Invalid token " + token + " at position " + i;
				}
			} else {
				return "Invalid token " + token;
			}
		} else {
			if (Character.isUpperCase(token.charAt(0)) || !Character.isDigit(token.charAt(0))) {
				return "Invalid token " + token.charAt(0) + " at position " + i;
			}
		}

		return "Valid token";
	}

	public static Node createAST(ArrayList<String> tokens) throws Exception {
		Node currentNode = new Node();
		Node ast = currentNode;
		Stack<Node> stack = new Stack<Node>();
		int i = 0;
		while (i < tokens.size()) {
			String token = tokens.get(i);

			if (token.equals("(")) {
				Node newNode = new Node();
				currentNode.children.add(newNode);
				stack.push(currentNode);
				currentNode = newNode;
			} else if (token.equals(")")) {
				currentNode = stack.pop();
			} else {
				if (!currentNode.token.isEmpty()) {
					Node childNode = new Node();
					childNode.token = token;
					currentNode.children.add(childNode);
				} else {
					currentNode.token = token;
				}
			}
			i++;

		}
		return ast;
	}

	public static double evaluateAST(Node ast) throws Exception {
		Node leftNode = ast.children.get(0);
		Node rightNode = ast.children.size() > 1 ? ast.children.get(1) : null;

		switch (ast.token) {
		case "Add":
			return ArithmeticExpressionEvaluation.evaluateAdd(leftNode, rightNode);
		case "Mult":
			return ArithmeticExpressionEvaluation.evaluateMul(leftNode, rightNode);
		case "Sqrt":
			return ArithmeticExpressionEvaluation.evaluateSqrt(leftNode);
		case "Exp":
			return ArithmeticExpressionEvaluation.evaluateExp(leftNode, rightNode);
		case "Const":
			return Double.parseDouble(leftNode.token);
		case "Var":
			return variables.get(leftNode.token);
		case "Neg":
			return Double.parseDouble(leftNode.token) * -1;
		default:
			throw new Exception(ast.token);
		}
	}

	public static String printAST(Node ast) throws Exception {
		Node leftNode = ast.children.get(0);
		Node rightNode = ast.children.size() > 1 ? ast.children.get(1) : null;

		switch (ast.token) {
		case "Add":
			return ArithmeticOperatorsPrint.printAdd(leftNode, rightNode);
		case "Mult":
			return ArithmeticOperatorsPrint.printMult(leftNode, rightNode);
		case "Sqrt":
			return ArithmeticOperatorsPrint.printSqrt(leftNode);
		case "Exp":
			return ArithmeticOperatorsPrint.printExp(leftNode, rightNode);
		case "Const":
			return leftNode.token;
		case "Var":
			return leftNode.token;
		case "Neg":
			return "-" + leftNode.token;
		default:
			throw new Exception(ast.token);
		}
	}

	public static void createValidTokens() {
		validTokens.add("Add");
		validTokens.add("Mult");
		validTokens.add("Sqrt");
		validTokens.add("Exp");
		validTokens.add("Const");
		validTokens.add("Var");
		validTokens.add("Neg");
		validTokens.add("Log");
	}

	public static void main(String[] args) throws Exception {

		createValidTokens();

		Scanner reader = new Scanner(System.in);
		// System.out.println("Enter an expression: ");
		// String expression = reader.nextLine();

		variables.put("x", 6.0);
		variables.put("y", 32.0);

		ArrayList<String> tokens = parse(exp);
		System.out.println(tokens);
		Node ast = createAST(tokens);
		System.out.println(evaluateAST(ast));
		System.out.println(printAST(ast));
	}
}
