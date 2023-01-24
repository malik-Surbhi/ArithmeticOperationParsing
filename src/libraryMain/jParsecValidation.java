package libraryMain;

import org.jparsec.OperatorTable;
import org.jparsec.Parser;
import org.jparsec.Parsers;
import org.jparsec.Scanners;
import org.jparsec.Terminals;

public class jParsecValidation {
	
	 static final Parser<Double> NUMBER =
		      Terminals.DecimalLiteral.PARSER.map(Double::valueOf);
		     
		  private static final Terminals OPERATORS =
		      Terminals.operators("Add", "Mul", "Sqrt", "Sub", "(", ")", "Neg", "Div", "Pow");
		  
		  static final Parser<Void> IGNORED = Parsers.or(
		      Scanners.JAVA_LINE_COMMENT,
		      Scanners.JAVA_BLOCK_COMMENT,
		      Scanners.WHITESPACES).skipMany();
		      
		  static final Parser<?> TOKENIZER =
		      Parsers.or(Terminals.DecimalLiteral.TOKENIZER, OPERATORS.tokenizer());
		  
		  static Parser<?> term(String... names) {
		    return OPERATORS.token(names);
		  }
		  
		  static final Parser<?> WHITESPACE_MUL = term("Add", "Sub", "Mul", "Sqrt", "Neg", "Div", "Pow").not();
		  
		  static <T> Parser<T> op(String name, T value) {
		    return term(name).retn(value);
		  }
		  
		  static Parser<Double> calculator(Parser<Double> atom) {
		    Parser.Reference<Double> ref = Parser.newReference();
		    Parser<Double> unit = ref.lazy().between(term("("), term(")")).or(atom);
		    Parser<Double> parser = new OperatorTable<Double>()
		        .infixr(op("Add", (l, r) -> l + r), 10)
		        .infixl(op("Sub", (l, r) -> l - r), 10)
		        .infixl(Parsers.or(term("Mul"), WHITESPACE_MUL).retn((l, r) -> l * r), 20)
		        .infixl(op("Div", (l, r) -> l / r), 20)
		        .infixl(op("Pow", (l, r) -> Math.pow(l, r)),0)
		        .prefix(op("Sqrt", v -> Math.sqrt(v)), 0)
		        .prefix(op("Neg", v -> -v), 30)
		        .build(unit);
		    ref.set(parser);
		    return parser;
		  }
		  
		  public static final Parser<Double> CALCULATOR =
		      calculator(NUMBER).from(TOKENIZER, IGNORED);
		  
		  public static void main (String args[]) {
			  System.out.println(CALCULATOR.parse("4Add(Sqrt(2Pow(24Sub10)))")); 
		  }
		 
	
}
