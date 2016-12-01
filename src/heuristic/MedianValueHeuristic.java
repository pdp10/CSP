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
 * This heuristic sorts domains by median value. So the next value will be the median value.
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public class MedianValueHeuristic<T extends Comparable<T>> extends NextValueHeuristic<T> {

	/**
	 * It constructs an empty map.
	 */
	public MedianValueHeuristic() { 
		super();
	}
	
	/**
	 * It initializes a map with the same cardinality of the list of variables. Then it initializes
	 * the structure sorting domains by median value.
	 * @param variables A list of variables.
	 */
	public MedianValueHeuristic(LinkedList<Variable<T>> variables) {
		super(variables);
		if(variables != null)
			initialize(variables);
	}
	
	/**
	 * It sets a map with the same cardinality of the list of variables. Then it initializes
	 * the structure sorting domains by median value.
	 * @param variables A list of variables.
	 */
	public void setVariables(LinkedList<Variable<T>> variables) {
		super.setVariables(variables);
		if(variables != null)
			initialize(variables);
	}
	
	/**
	 * It initializes the map sorting domains by median value.
	 * @param variables A list of variables.
	 */
	private void initialize(LinkedList<Variable<T>> variables) {
		Variable<T> var = null;
		LinkedList<T> domainVar = null; 
		T temp = null;
		LinkedList<T> sortedDomain = null;
		boolean added = false;
		int medial = 0;
		// for each variable
		for(int i = 0; i < variables.size(); i++) {
			sortedDomain = new LinkedList<T>();
			var = variables.get(i);
			domainVar = var.getDomain();
			// it gets its domain
			for(int j = 0; j < domainVar.size(); j++) {
				temp = domainVar.get(j);
				added = false;
				// add elements of the domain preserving in increasing order
				for(int k = 0; k < sortedDomain.size() && !added; k++) {
					if(temp.compareTo(sortedDomain.get(k)) < 0) {
						sortedDomain.add(k, temp);
						added = true;
					}
				}
				if(!added) {
					sortedDomain.addLast(temp);
				}
			}
			// it sorts elements by median value
			for(int j = 0; j < sortedDomain.size(); j++) {
				medial = (sortedDomain.size() - j - 1) / 2;
				temp = sortedDomain.remove(medial + j);
				sortedDomain.add(j, temp);
			}
			map.put(var, sortedDomain);	
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Median Value Heuristic";
	}
	
}
