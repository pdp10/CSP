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
 * Created on: 17/06/2007
 * Modifies:
 * 			v.1.1 (18/06/2007): Documentation of the class.
 * 			v.1.0 (17/06/2007): Codify of the class.
 */
package heuristic;

import java.util.*;

import csp.Constraint;
import csp.Variable;

/**
 * This class implements the most constrained variable heuristic. 
 * On details, this heuristic sorts the list of variables by number of constraints decreasing.
 * So the first variable will be the most constrained variable.
 * 
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public class MostConstrainedVariableHeuristic<T extends Comparable<T>> extends NextVariableHeuristic<T> {
	
	/**
	 * It defines a pair <variable, number of constraints>.
	 * 
	 * @author Piero Dalle Pezze
	 * @version 1.1
	 */
	protected class VarNumConstraint {
		/**
		 * The variable of the pair.
		 */
		protected Variable<T> var = null;
		/**
		 * The number of constraints of the variable.
		 */
		protected int num = 0;
		/**
		 * It builds a pair <variable, number of constraints>
		 * @param var The variable
		 * @param numConstraint The number of constraints.
		 */
		public VarNumConstraint(Variable<T> var, int numConstraint) {
			this.var = var;
			this.num = numConstraint;
		}
		/**
		 * It returns the variable of the couple.
		 * @return The variable of the couple.
		 */
		public Variable<T> getVariable() {
			return var;
		}
		/**
		 * It returns the number of constraints of the variable.
		 * @return The number of constraints of the variable.
		 */
		public int getNumConstraints() {
			return num;
		}
	} // end inner class VarNumConstraint
	
	/**
	 * It constructs an empty linked list of variables.
	 */
	public MostConstrainedVariableHeuristic() {
		super();
	}
	
	/**
	 * It initializes the list of variables with the list received as parameter.
	 * In this heuristic, the variables are sorted by number of constraints decreasing.
	 * @param variables A list of variables.
	 * @param constraints A list of constraints between variables.
	 */
	public MostConstrainedVariableHeuristic(LinkedList<Variable<T>> variables, LinkedList<Constraint<T>> constraints) {
		super();
		if(variables != null && constraints != null)
			initialize(variables, constraints);
	}
	
	/**
	 * It sets the list of variables. In this heuristic, the variables are sorted by 
	 * number of constraints decreasing.
	 * @param variables A list of variables.
	 * @param constraints A list of constraints between variables.
	 */
	public void setVaribles(LinkedList<Variable<T>> variables, LinkedList<Constraint<T>> constraints) {
		if(variables != null && constraints != null)
			initialize(variables, constraints);
	}
	
	/**
	 * It initializes the list of variables, sorting them by number of constraints decreasing.
	 * @param variables A list of variables.
	 * @param constraints A list of constraints between variables.
	 */
	private void initialize(LinkedList<Variable<T>> variables, LinkedList<Constraint<T>> constraints) {
		// it initializes an empty list.
		this.variables = new LinkedList<Variable<T>>();
		// A list of VarNumConstraint. 
		// This list is sorted by number of constraints decreasing.
		LinkedList<VarNumConstraint> structure = new LinkedList<VarNumConstraint>();
		VarNumConstraint vnc = null;
		boolean added = false;
		int numOfConstraints = 0;
		Constraint<T> hc = null;
		Variable<T> var = null;
		// For each variable 
		for(int i = 0; i < variables.size(); i++) {
			// It counts the number of constraints (numOfConstraints)
			numOfConstraints = 0;
			var = variables.get(i);
			for(int j = 0; j < constraints.size(); j++) {
				hc = constraints.get(j);
				if(hc.contains(var)) {
					numOfConstraints++;
				}
			}
			// It adds to the structure the pair <var, numOfConstraints> by holding
			// the list sorted by number of constraints decreasing.
			added = false;
			for(int j = 0; j < structure.size() && !added; j++) {
				vnc = structure.get(j);
				if(vnc.getNumConstraints() < numOfConstraints) {
					structure.add(j, new VarNumConstraint(var, numOfConstraints));
					added = true;
				}
			}
			if(!added) {
				structure.addLast(new VarNumConstraint(var, numOfConstraints));
			}
		}
		// All variables are added to the structure.
		// For each node of the structure, it extracts the i-th variables and adds it to
		// this.variables .
		for(int i = 0; i < structure.size(); i++) {
			this.variables.add(structure.get(i).getVariable());
		}

	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Most Constrained Variable Heuristic";
	}

}