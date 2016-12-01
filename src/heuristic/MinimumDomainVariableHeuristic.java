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
 * Created on: 19/06/2007
 * Modifies:
 * 			v.1.0 (19/06/2007): Documentation and codify of the class.
 */
package heuristic;

import java.util.*;

import csp.Variable;

/**
 * This class implements the minimum domain heuristic. 
 * On details, this heuristic sorts the list of variables by cardinality of domain increasing.
 * So the first variable will be the variable with minimum domain.
 * 
 * @author Piero Dalle Pezze
 * @version 1.0
 * @param <T> the type of objects that this object may be compared to
 */
public class MinimumDomainVariableHeuristic<T extends Comparable<T>> extends NextVariableHeuristic<T> {
		
	/**
	 * It constructs an empty linked list of variables.
	 */
	public MinimumDomainVariableHeuristic() {
		super();
	}
	
	/**
	 * It initializes the list of variables with the list received as parameter.
	 * In this heuristic, the variables are sorted by cardinality of domain increasing.
	 * @param variables A list of variables.
	 */
	public MinimumDomainVariableHeuristic(LinkedList<Variable<T>> variables) {
		super();
		if(variables != null)
			initialize(variables);
	}
	
	/**
	 * It sets the list of variables. In this heuristic, the variables are sorted 
	 * by cardinality of domain increasing.
	 * @param variables A list of variables.
	 */
	public void setVaribles(LinkedList<Variable<T>> variables) {
		if(variables != null)
			initialize(variables);
	}
		
	/**
	 * It initializes the list of variables, sorting them by cardinality of domain increasing.
	 * @param variables A list of variables.
	 */
	private void initialize(LinkedList<Variable<T>> variables) {
		// it initializes an empty list.
		this.variables = new LinkedList<Variable<T>>();
		Variable<T> var = null;
		boolean added = false;
		// for each variable in variables
		for(int i = 0; i < variables.size(); i++) {
			var = variables.get(i);
			added = false;
			// it adds var in the correct order in this.variables .
			for(int j = 0; j < this.variables.size() && !added; j++) {
				if(this.variables.get(j).getDomain().size() > var.getDomain().size()) {
					this.variables.add(j,var);
					added = true;
				}
			}
			if(!added) {
				this.variables.addLast(var);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Minimum Domain Variable Heuristic";
	}

}