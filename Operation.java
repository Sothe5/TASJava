import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class Operation implements Element {

	public List<Element> components;
	public OperationType type;
	private boolean isNotNegated;

	public Operation(OperationType type, boolean isNotNegated) {
		this.type = type;
		this.components = new ArrayList<>();
		this.isNotNegated = isNotNegated;
	}
	public Operation(OperationType type) {
		this(type, true);
	}

	public void addComponent(Element component) {
		this.components.add(component);
	}

	public boolean isNotNegated() {
		return this.isNotNegated;
	}

	public void negate() {
		if(isNotNegated()){
			switch(this.type) {
				case AND:
				this.type = OperationType.OR;
				this.negateAllComponents();
				break;
				case OR:
				this.type = OperationType.AND;
				this.negateAllComponents();
				break;
				case THEN:			
				this.type = OperationType.OR;
				this.components.get(0).negate();
				break;
				default:
				throw new RuntimeException("Operation not implemented");
			}
		} else {
			this.isNotNegated = true;
			if(this.type == OperationType.THEN) {
				this.type = OperationType.AND;
				this.components.get(1).negate();
			}
		}
	}

	public void negateAllComponents() {
		for (Element component : this.components) {
			component.negate();
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append((this.isNotNegated()? "":"¬") + "(");
		int lenComponents = this.components.size();
		for (int i = 0; i < lenComponents; i++) {
			sb.append(this.components.get(i));
			if (i < lenComponents - 1) {
				sb.append(" " + type + " ");
			}
		}
		sb.append(")");
		return sb.toString();
	}
}
