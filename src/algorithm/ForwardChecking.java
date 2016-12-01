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
 * Created on: 18/06/2007
 * Modifies:
 * 			v.1.1 (19/06/2007): Class documentation.
 * 			v.1.0 (18/06/2007): Class codify.
 */
package algorithm;

import java.util.*;
import csp.*;

/**
 * This class represents the forward checking search algorithm to find solutions of a CSP.
 *  
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public class ForwardChecking<T extends Comparable<T>> extends SearchAlgorithm<T> {

	/**
	 * A bidimensional matrix used by the forward checking algorithm. The first
	 * index defines the variable, the second one the domain. So it is a
	 * non-rectangular bidimensional matrix.
	 * 
	 * It holds the following invariant: 
	 * - At starts, all assignments are possible, so (for each i, l) [i,l] <-- -1 
	 * - If the domain value m of the variable var_j cannot be used because it 
	 *   makes the constraint (var_i,var_j) not consistent for a previous variable 
	 *   var_i, then [j,m] <-- i.
	 * 
	 * See FC() method and the article "On The Forward Checking Algorithm" by
	 * Fahiem Bacchus and Adam Grove (BGCP95).
	 */
	private HashMap<Variable<T>, int[]> domain;
	
	/**
	 * It initializes the forward checking algorithm.
	 */
	public ForwardChecking() {
		super();
	}
	
	/**
	 * It initializes the forward checking algorithm with a csp.
	 * @param csp A CSP.
	 */
	public ForwardChecking(CSP<T> csp) {
		super(csp);
		if(csp != null)
			initialize();
	}
	
	/**
	 * It initializes the domain structure.
	 */
	private void initialize() {
		// It initializes the domain structure to -1
		domain = new HashMap<Variable<T>, int[]>(csp.getVariables().size());
		Variable<T> var;
		int[] dom;
		for (int i = 0; i < csp.getVariables().size(); i++) {
			var = csp.getVariables().get(i);
			int domainSize_i = var.getDomain().size();
			dom = new int[domainSize_i];
			domain.put(var, dom);
			for (int j = 0; j < domainSize_i; j++) {
				dom[j] = -1;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setCsp(CSP<T> csp) {
		super.setCsp(csp);
		if(csp != null)
			initialize();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void search() {
		numberOfSolutions = 1;
		reset();
		if(csp != null)
			FC(0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void search(int numberSolutions) {
		numberOfSolutions = numberSolutions;
		reset();
		if(csp != null && numberOfSolutions > 0)
			FC(0);
	}
	
	/**
	 * It searches all solutions.
	 */
	public void searchAll() {
		numberOfSolutions = -1;
		reset();
		if(csp != null)
			FC(0);
	}
	
	/**
	 * It returns true if the constraints between variables var_i and var_j are
	 * consistent with the assignment val_i and val_j respectively.
	 * 
	 * @param var_i
	 *            The i-th variable.
	 * @param var_j
	 *            The j-th variable.
	 * @param val_i
	 *            The value to assign to var_i.
	 * @param val_j
	 *            The value to assign to var_j.
	 * @return True if the constraints are consistent, false otherwise.
	 */
	protected boolean isConsistent(Variable<T> var_i, Variable<T> var_j,
			T val_i, T val_j) {
		Constraint<T> hc = null;
		boolean consistent = true;
		// For each constraint
		for (int i = 0; i < csp.getConstraints().size() && consistent; i++) {
			hc = csp.getConstraints().get(i);
			if (hc.contains(var_i, var_j)) {
				// The constraint contains var_i and var_j
				if (!hc.isConsistent(val_i, val_j)) {
					// The constraint isn't consistent with this assignment.
					consistent = false;
				}
			}
		}
		return consistent;
	}

	/**
	 * The forward checking algorithm. This algorithm is used during the search
	 * phase. To perform the search an heuristic can be used. See the article
	 * "On The Forward Checking Algorithm" by Fahiem Bacchus and Adam Grove
	 * (BGCP95) for more details. This algorithm used the matrix structure
	 * domain[][] defined before.
	 * 
	 * @param i
	 *            The index of the next variable to assign following the
	 *            heuristic ordering.
	 */
	protected void FC(int i) {
		// The i-th variable is the variable which will be expanded.
		// It gets the i-th variable from the list of variables of heuristic.
		// So it is possible to change the original ordering of variable
		// selection.
		// Moreover, it is independent from the particular heuristic used.
		Variable<T> var_i = nextVariableHeuristic.getVariables().get(i);
		// It obtains the domain of the variable var_i
		// It is independent from the particular heuristic used.
		LinkedList<T> domain_i = nextValueHeuristic.getDomain(var_i);
		// For each domain value of the variable var_i
		for (int l = 0; l < domain_i.size() && !stopSearch; l++) {
			// It assign the l-th domain value to var_i
			var_i.assign(domain_i.get(l));
			// It tests if the current assignment sounds good.
			// The assignment var_i <-- l is ammissible.
			if (domain.get(var_i)[l] == -1) {
				// It counts the number of nodes visited
				nodesVisited++;
				if (i == csp.getVariables().size() - 1) {
					// All variable are assigned.
					setLastSolution();
					counterSolutions++;
					if(counterSolutions == numberOfSolutions) {
						// it stops the search
						stopSearch = true;
					}
				} else {
					// Not all variable are assigned
					// It checks forward if constraints between var_i and
					// not assigned variables are consistent
					if (checkForward(i)) {
						// Recursive call. It expands the (i+1)-th variable
						FC(i + 1);
					}
					// It restore the previous state. It is used to go up the
					// tree.
					restore(i);
				}
			}
			// else try the next domain value
		}
	}

	/**
	 * It checks forward if constraints between var_i and variables not still
	 * assigned are consistent. It returns true if all constraints are still
	 * consistent, false otherwise.
	 * 
	 * @param i The i-th variable in the heuristic ordering.
	 * @return True if all constraints are still consitent, false otherwise.
	 */
	protected boolean checkForward(int i) {
		// It gets the i-th variable from the list of variables of heuristic.
		Variable<T> var_i = nextVariableHeuristic.getVariables().get(i);
		// dwo means domain wipe-out. If the constraint between var_i and var_j,
		// respectively with the assignment var_i.getAssignment() and m-th
		// domain value
		// of var_j is consistent, dwo is setted to false. Otherwise it remains
		// true
		// and the entry [j,m] in domain is setted to i (the index of the i-th
		// expanded
		// variable var_i).
		boolean dwo = true;
		Variable<T> var_j = null;
		LinkedList<T> domain_j = null;
		// for each variable var_j which is not still assigned
		for (int j = i + 1; j < csp.getVariables().size(); j++) {
			dwo = true;
			// it obtains the j-th variable
			var_j = nextVariableHeuristic.getVariables().get(j);
			// it obtains the domain of var_j
			domain_j = nextValueHeuristic.getDomain(var_j); 
			// for each domain value of the variable var_j
			for (int m = 0; m < domain_j.size(); m++) {
				// It tests if the m-th domain value of var_j sounds good
				// The assignment var_j <-- m is until now ammissible.
				if (domain.get(var_j)[m] == -1) {
					if (isConsistent(var_i, var_j, var_i.getAssignment(),
							domain_j.get(m))) {
						// The constraint is consistent with this assignment
						dwo = false;
					} else {
						// the constraint is not consistent
						// The assignment var_j <-- m is not ammissible.
						// i-th var forbids the assignment var_j <-- m
						domain.get(var_j)[m] = i;
					}
				}
			}
			// NOTE: if at least one constraint is consistent, dwo is false.
			if (dwo) {
				// The current domain of var_j is empty.
				return false;
			}
		}
		return true;
	}

	/**
	 * It restores the previous state.
	 * 
	 * @param i
	 *            The i-th variable in the heuristic ordering.
	 */
	protected void restore(int i) {
		Variable<T> var_j = null;
		LinkedList<T> domain_j = null;
		// for each variable var_j that follows var_i
		for (int j = i + 1; j < csp.getVariables().size(); j++) {
			// it obtains the j-th variable
			var_j = nextVariableHeuristic.getVariables().get(j);
			// it obtains the domain of var_j
			domain_j = nextValueHeuristic.getDomain(var_j);
			// for each domain value of the variable var_j
			for (int m = 0; m < domain_j.size(); m++) {
				// if the domain value is not consistent respect with a
				// constraint
				if (domain.get(var_j)[m] == i) {
					// i-th var forbids the assignment var_j <-- m
					// it resets the value to -1
					domain.get(var_j)[m] = -1;
				}
			}
		}
	}
	
    // PRINT METHODS
    /**
     * It returns the name of the algorithm.
     * @return The name of the algorithm.
     */
    public String toString() {
    	return "Forward Checking with \n\t" + 
    	nextVariableHeuristic.toString() + "\n\t" +
    	nextValueHeuristic.toString();
    }
	
    // PRINT METHODS
    /**
     * It prints the name of the algorithm.
     */
    public void printAlgorithm() {
    	System.out.println("Search Algorithm:");
    	System.out.println("\tForward Checking with \n\t - " + 
    	nextVariableHeuristic.toString() + "\n\t - " +
    	nextValueHeuristic.toString());
    }
	
}
