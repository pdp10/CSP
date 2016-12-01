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
import csp.*;

/**
 * This is a generic heuristic to define which will be the next value of the domain to be chosen.
 * 
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public abstract class NextValueHeuristic<T extends Comparable<T>> {

	/**
	 * A map with entry <Variable, domain>. The domain is sorted according to the specific heuristic.
	 */
	protected HashMap<Variable<T>, LinkedList<T>> map;
	
	/**
	 * It constructs an empty map.
	 */
	public NextValueHeuristic() {
		map = new HashMap<Variable<T>, LinkedList<T>>();
	}
	
	/**
	 * It initializes a map with the same cardinality of the list of variables.
	 * @param variables A list of variables.
	 */
	public NextValueHeuristic(LinkedList<Variable<T>> variables) {
		if(variables != null) {
			map = new HashMap<Variable<T>, LinkedList<T>>(variables.size());
		} else {
			map = new HashMap<Variable<T>, LinkedList<T>>();
		}
	}

	/**
	 * It sets a map with the same cardinality of the list of variables.
	 * @param variables A list of variables.
	 */
	public void setVariables(LinkedList<Variable<T>> variables) {
		if(variables != null) {
			map = new HashMap<Variable<T>, LinkedList<T>>(variables.size());
		} else {
			map = new HashMap<Variable<T>, LinkedList<T>>();
		}
	}
	
	/**
	 * It returns the domain sorted according to the heuristic of the variable var.
	 * @param var A variable
	 * @return The domain of var.
	 */
	public LinkedList<T> getDomain(Variable<T> var) {
		return map.get(var);
	}
	
	/**
	 * It returns the name of the heuristic.
	 * @return The name of the heuristic.
	 */
	public String toString() {
		return "Generic Next Value Heuristic";
	}
	
}
