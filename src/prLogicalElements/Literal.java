package src.prLogicalElements;

public class Literal implements Element {

	private boolean isPositive;
	private char symbol;
	public Literal(char symbol, boolean isPositive){
		this.symbol = symbol;
		this.isPositive = isPositive;
	}
	public Literal(char symbol) {
		this(symbol, true);
	}

	public boolean isPositive() {
		return this.isPositive;
	}

	public void negate() {
		this.isPositive = !this.isPositive;
	}

	public String toString() {
		return (this.isPositive ? "":"¬") + this.symbol;
	}

	public void signar() {
		
	}
	
	public boolean equals(Object o) {
		boolean isEquals = false;
		if(o instanceof Literal) {
			Literal literal = (Literal)o;
			isEquals = literal.symbol == this.symbol;
		}
		return isEquals;
	}
}