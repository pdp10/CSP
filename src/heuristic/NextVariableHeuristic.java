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

import csp.Variable;

/**
 * This class defines a generic heuristic to improve the search phase during the resolution
 * of the CSP.
 * 
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public abstract class NextVariableHeuristic<T extends Comparable<T>> {
	
	/**
	 * A list of variables sorted by the heuristic.
	 */
	protected LinkedList<Variable<T>> variables;
	
	/**
	 * It constructs an empty linked list of variables.
	 */
	public NextVariableHeuristic() { 
		variables = new LinkedList<Variable<T>>();
	}
	
	/**
	 * It initializes the list of variables with the list received as parameter.
	 * @param variables A list of variables.
	 */
	public NextVariableHeuristic(LinkedList<Variable<T>> variables) {
		if(variables != null) {
			this.variables = variables;
		} else {
			variables = new LinkedList<Variable<T>>();
		}
	}
	
	/**
	 * It sets the list of variables.
	 * @param variables A list of variables.
	 */
	public void setVaribles(LinkedList<Variable<T>> variables) {
		if(variables != null) {
			this.variables = variables;
		} else {
			variables = new LinkedList<Variable<T>>();
		}
	}
	
	/**
	 * It returns the list of variables, sorted by the heuristic.
	 * @return A list of variables.
	 */
	public LinkedList<Variable<T>> getVariables() {
		return variables;
	}
	
	/**
	 * It returns the name of the heuristic.
	 * @return The name of the heuristic.
	 */
	public String toString() {
		return "Generic Next Variable Heuristic";
	}

}
