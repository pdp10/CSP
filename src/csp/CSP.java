/*
 * CSP - Backtracking search with forward checking and heuristics.
 * Copyright (C) 2007. Piero Dalle Pezze, -
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *
 * Created on: 16/06/2007
 * Modifies:
 * 			v.1.1 (18/06/2007): Simplified the structure of the class.
 * 			v.1.0 (16/06/2007): Documentation and codify of the class.
 */
package csp;

import java.util.*;

/**
 * This class represents a constraint satisfaction problem.
 * 
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public class CSP<T extends Comparable<T>> {

	// MAIN DATA
	/**
	 * A list of variables with domains.
	 */
	private LinkedList<Variable<T>> variables;

	/**
	 * A list of constraints defined between variables
	 */
	private LinkedList<Constraint<T>> constraints;

	/**
	 * It initializes the CSP with a set of variables and a set of constraints
	 * defined on these variables.
	 * 
	 * @param variables
	 *            A list of variables.
	 * @param constraints
	 *            A list of constraints.
	 */
	public CSP(LinkedList<Variable<T>> variables,
			LinkedList<Constraint<T>> constraints) {
		this.variables = variables;
		this.constraints = constraints;
	}
	
	/**
	 * It returns the list of variables.
	 * 
	 * @return The list of variables.
	 */
	public LinkedList<Variable<T>> getVariables() {
		return variables;
	}

	/**
	 * It returns the list of constraints.
	 * 
	 * @return The list of constraints.
	 */
	public LinkedList<Constraint<T>> getConstraints() {
		return constraints;
	}

	/**
	 * It prints the problem data and the heuristic name to improve the search
	 * phase.
	 */
	public void printProblem() {
		System.out.println("Constraint Satisfaction Problem (CSP):");
		System.out.println(" - Variables:");
		if(variables != null) {
			for (int i = 0; i < variables.size(); i++) {
				Variable<T> var = variables.get(i);
				System.out.println("\t" + var.toString() + ": "
						+ var.toStringDomain());
			}
		}
		System.out.println(" - Constraints:");
		if(constraints != null) {
			for (int i = 0; i < constraints.size(); i++) {
				Constraint<T> hc = constraints.get(i);
				System.out.println("\t" + hc.toString());
			}
		}
	}

	/**
	 * It prints the last found solution of the problem if it exists.
	 */
	public void printLastSolution() {
		String solution = new String();
		if(variables != null) {
			for (int i = 0; i < variables.size(); i++) {
				Variable<T> var = variables.get(i);
				solution = solution + var.toString() + "/" + var.toStringSolution();
				if (i + 1 < variables.size()) {
					solution = solution + ",";
				}
			}
		}
		System.out.println("Solution:");
		System.out.println("\t<" + solution + ">");
	}
	
}
