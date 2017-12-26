package Tas;

import java.util.Set;

import prLogicalElements.*;

public class Tas {

	public static void runTAS(Operation root) {
		System.out.println(root);
		Signar.signar(root);
		System.out.println(root);
		// TAS EXECUTION
		getPureLiterals(root);
		removePureLiterals(root, getPureLiterals(root));
		System.out.println(root);

		/*
		 * Restringir Red Completa Puro Sub Quine
		 * 
		 * A continuacion idea basica que en parte no me convenze por el hecho de que al
		 * comprobar restringir por ejemplo lo hace desde la root y no solo de las
		 * componentes modificadas
		 */

		// while(!isSolution(root)) {
		//
		// restringe(root);
		// if(!isModified) { // opto porque esto sea una booleana en realidad que se
		// pone a true desde cada metodo(res,red,pur,etc)
		// // si hace cambios(asi no hay que comprobar nada)
		// RedCompleta(root);
		// if(!isModified){
		// LiteralPuro(root);
		// if(!isModified){
		// SubReduccion(root);
		// if(!isModified){
		// Quine(root);
		// }
		// }
		// }
		// }
		//
		// }
	}

	private static Set<Literal> getPureLiterals(Operation operation) {
		// This method is not finished
		Set<Literal> literals = operation.getLiterals();
		System.out.print("The following literals are pure literals: ");
		for (Literal literal : literals) {
			if (!literals.contains(opositLiteralOf(literal))) {
				System.out.print(literal + " ");
			}
		}
		System.out.println();
		return literals;
	}

	private static void removePureLiterals(Operation operation, Set<Literal> pureLiterals) {
		switch (operation.type) {
		case OR:
			for (Literal literal : pureLiterals) {
				if(operation.components.contains(literal)) {
					operation.type = OperationType.NIL;
					break;
				}
			}
			break;
		case AND:
			for (Literal literal : pureLiterals) {
				operation.components.remove(literal);			
			}
			removePureLiterals(operation, pureLiterals);
			break;
		default: 
			throw new RuntimeException("Operation seems not to be in negative normal form: " + operation);
		}
	}

	private static Literal opositLiteralOf(Literal literal) {
		return new Literal(literal.symbol, !literal.isPositive());
	}

}
