package prLogicalElements;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Operation implements Element {

	public List<Element> components;
	public OperationType type;
	public List<Element> literalsThatMustBeMoved;
	public List<Element> literalsThatMustBeMovedJustOne;
	private boolean isPositive;
	
	
	public Operation(OperationType type, boolean isPositive) {
		this.type = type;
		this.components = new ArrayList<>();
		this.isPositive = isPositive;
		this.literalsThatMustBeMoved = new ArrayList<>();
		this.literalsThatMustBeMovedJustOne = new ArrayList<>();
	}

	public Operation(OperationType type) {
		this(type, true);
	}

	public void addComponents(Element ... elements) {
		for(Element element : elements) {
			this.components.add(element);
		}
	}
	
	public void addComponents(Collection<Element> elements) {
		this.components.addAll(elements);
	}

	public boolean isPositive() {
		return this.isPositive;
	}

	public void negate() {
		this.isPositive = !this.isPositive;
	}	

	public void negateAllComponents() {
		for (Element component : this.components) {
			component.negate();
		}
	}
	
	public Set<Literal> getLiterals() {
		Set<Literal> literals = new HashSet<>();
		for(Element element: this.components) {
			if (element instanceof Literal) {
				literals.add((Literal) element);
			} else {
				literals.addAll(((Operation)element).getLiterals());
			}
		}
		return literals;
	}
	
	public List<Operation> getOperations() {
		List<Operation> operations = new ArrayList<>();
		for(Element element: this.components) {
			if (element instanceof Operation) {
				operations.add((Operation) element);
			}
		}
		return operations;
	}
	
	public Set<Literal> getInmediateLiterals() {

		Set<Literal> literals = new HashSet<>();
		for (Element elements : this.components) {
			if (elements instanceof Literal) {
				literals.add((Literal) elements);
			}
		}
		
		return literals;
	}
	
	public List<Operation> getInmediateOperations() {

		List<Operation> operations = new ArrayList<>();
		for (Element elements : this.components) {
			if (elements instanceof Operation) {
				operations.add((Operation) elements);
			}
		}
		
		return operations;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append((this.isPositive() ? "" : "�") + "(");
		int lenComponents = this.components.size();
		if(lenComponents == 0) {
			sb.append(type);
		}
		for (int i = 0; i < lenComponents; i++) {
			sb.append(this.components.get(i));
			if (i < lenComponents - 1) {
				sb.append(" " + type + " ");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	public Element clone() {
		Operation clone = new Operation(this.type);
		for (Element component: this.components) {
			clone.addComponents(component.clone());
		}
		return clone;
	}
}
