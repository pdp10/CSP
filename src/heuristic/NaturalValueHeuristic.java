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
 * 			v.1.1 (20/06/2007): Documentation of the class.
 * 			v.1.0 (19/06/2007): Codify of the class.
 */
package heuristic;

import java.util.*;
import csp.*;

/**
 * This heuristic preserves the original ordering of domains.
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public class NaturalValueHeuristic<T extends Comparable<T>> extends NextValueHeuristic<T> {

	/**
	 * It constructs an empty map.
	 */
	public NaturalValueHeuristic() { 
		super();
	}
	
	/**
	 * It initializes a map with the same cardinality of the list of variables. Then it initializes
	 * the structure with the same ordering of domains.
	 * @param variables A list of variables.
	 */
	public NaturalValueHeuristic(LinkedList<Variable<T>> variables) {
		super(variables);
		if(variables != null)
			initialize(variables);
	}
	
	/**
	 * It sets a map with the same cardinality of the list of variables. Then it initializes
	 * the structure with the same ordering of domains.
	 * @param variables A list of variables.
	 */
	public void setVariables(LinkedList<Variable<T>> variables) {
		super.setVariables(variables);
		if(variables != null)
			initialize(variables);
	}
	
	/**
	 * It initializes the map preserving the original ordering of domains.
	 * @param variables A list of variables.
	 */
	private void initialize(LinkedList<Variable<T>> variables) {
		Variable<T> var = null;
		for(int i = 0; i < variables.size(); i++) {
			var = variables.get(i);
			map.put(var, var.getDomain());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Natural Value Heuristic";
	}
	
}