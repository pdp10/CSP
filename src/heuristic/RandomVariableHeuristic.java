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
 * This class defines a random heuristic. In particular, the ordering of variables is
 * casual.
 * 
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public class RandomVariableHeuristic<T extends Comparable<T>> extends NextVariableHeuristic<T> {

	/**
	 * The random generator.
	 */
	protected Random random = new Random();
	
	/**
	 * It constructs an empty linked list of variables.
	 */
	public RandomVariableHeuristic() {
		super();
	}
	
	/**
	 * It initializes the list of variables with the list received as parameter.
	 * In this heuristic, the original ordering is changed with a random sorting.
	 * @param variables A list of variables.
	 */
	public RandomVariableHeuristic(LinkedList<Variable<T>> variables) {
		super();
		if(variables != null)
			initialize(variables);
	}
	
	/**
	 * It sets the list of variables. In this heuristic, the original ordering is changed 
	 * with a random sorting.
	 * @param variables A list of variables.
	 */
	public void setVaribles(LinkedList<Variable<T>> variables) {
		if(variables != null)
			initialize(variables);
	}
	
	/**
	 * It initializes the list of variables, by using a permutation of the original
	 * sorting.
	 * @param variables A list of variables.
	 */
	private void initialize(LinkedList<Variable<T>> variables) {
		// it initializes an empty linked list.
		this.variables = new LinkedList<Variable<T>>();
		Integer rnd = 0;
		// the list of indexes. it will be initialized so:
		// {0,1,2,...,variable.size()-1}
		LinkedList<Integer> temp = new LinkedList<Integer>();
		for(int i = 0; i < variables.size(); i++) {
			temp.add(new Integer(i));
		}
		// it adds variables to this.variables .
		for(int i = 0; i < variables.size(); i++) {
			// It extracts an random index from temp. 
			rnd = temp.remove(random.nextInt(temp.size()));
			// It adds in position i, the variable in index rnd
			this.variables.add(variables.get(rnd.intValue()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Random Variable Heuristic";
	}
	
}
