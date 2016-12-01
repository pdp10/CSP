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

import heuristic.*;
import csp.*;


/**
 * This class represents a generic search algorithm to find solutions of a CSP.
 *  
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public abstract class SearchAlgorithm<T extends Comparable<T>> {

	/**
	 * It is used to stop the search
	 */
	protected boolean stopSearch = false;
	
	/**
	 * It is the number of solutions to search.
	 */
	protected int numberOfSolutions = 1;
	
	/**
	 * It is the current number of solutions found.
	 */
	protected int counterSolutions = 0;
	
	/**
	 * The CSP of reference
	 */
	protected CSP<T> csp = null;
	
	/**
	 * If it is true, solutions are printed during the search, otherwise not.
	 */
	private boolean printSolution = true;
	
	/**
	 * An heuristic used during the search phase. By default, the heuristic is
	 * NaturalVariableHeuristic, so the original ordering of variables is preserved. (It
	 * runs as without heuristic).
	 */
	protected NextVariableHeuristic<T> nextVariableHeuristic = null;
	
	/**
	 * An heuristic used during the search phase. By default, the heuristic is
	 * NaturalValueHeuristic, so the original ordering of domain is preserved. (It
	 * runs as without heuristic).
	 */
	protected NextValueHeuristic<T> nextValueHeuristic = null;
	
	//STATISTICS
	/**
	 * The running time of the search phase.
	 */
	protected long nodesVisited = 0;
	
	/**
	 * It initializes the algorithm.
	 */
	public SearchAlgorithm() {
		this.nextVariableHeuristic = new NaturalVariableHeuristic<T>();
		this.nextValueHeuristic = new NaturalValueHeuristic<T>();
	}
	
	/**
	 * It initializes the algorithm with a csp.
	 * @param csp A CSP.
	 */
	public SearchAlgorithm(CSP<T> csp) {
		this.csp = csp;
		if(csp != null) {
			this.nextVariableHeuristic = new NaturalVariableHeuristic<T>(csp.getVariables());
			this.nextValueHeuristic = new NaturalValueHeuristic<T>(csp.getVariables());
		}
	}
	
	/**
	 * It searches the first solution if it exists.
	 */
	public abstract void search();

	/**
	 * It searches the first numberSolutions solutions if they exist.
	 * @param numberSolutions The number of solutions to find
	 */
	public abstract void search(int numberSolutions);
	
	/**
	 * It searches all solutions if they exist.
	 */
	public abstract void searchAll();
	
	/**
	 * It returns the CSP of reference.
	 * @return the csp
	 */
	public CSP<T> getCsp() {
		return csp;
	}

	/**
	 * It returns the number of solutions to search.
	 * @return the number of solutions
	 */
	public int getNumberOfSolutions() {
		return numberOfSolutions;
	}

	/**
	 * It returns the heuristic for the next variable used.
	 * 
	 * @return The heuristic used.
	 */
	public NextVariableHeuristic<T> getNextVariableHeuristic() {
		return nextVariableHeuristic;
	}

	/**
	 * It returns the heuristic for the next value used.
	 * 
	 * @return The heuristic used.
	 */
	public NextValueHeuristic<T> getNextValueHeuristic() {
		return nextValueHeuristic;
	}
	
	/**
	 * It returns the number of nodes visited during the search phase.
	 * 
	 * @return the number of nodes visited.
	 */
	public long getNodesVisited() {
		return nodesVisited;
	}
	
	/**
	 * It sets the CSP
	 * @param csp the csp to set
	 */
	public void setCsp(CSP<T> csp) {
		this.csp = csp;
		if(csp != null) {
			this.nextVariableHeuristic = new NaturalVariableHeuristic<T>(csp.getVariables());
			this.nextValueHeuristic = new NaturalValueHeuristic<T>(csp.getVariables());
		}
	}

	/**
	 * It sets the number of solutions to find
	 * @param numberOfSolutions the number of solutions to set
	 */
	public void setNumberOfSolutions(int numberOfSolutions) {
		this.numberOfSolutions = numberOfSolutions;
	}
	
	/**
	 * It sets a heuristic for the next variable used to improve the search phase.
	 * 
	 * @param nextVariableHeuristic
	 *            A heuristic to improve the search phase.
	 */
	public void setNextVariableHeuristic(NextVariableHeuristic<T> nextVariableHeuristic) {
		this.nextVariableHeuristic = nextVariableHeuristic;
	}
	
	/**
	 * It sets a heuristic for the next value used to improve the search phase.
	 * 
	 * @param nextValueHeuristic
	 *            A heuristic to improve the search phase.
	 */
	public void setNextValueHeuristic(NextValueHeuristic<T> nextValueHeuristic) {
		this.nextValueHeuristic = nextValueHeuristic;
	}
	
	/**
	 * It sets the last solution found.
	 */
	protected void setLastSolution() {
		if(csp != null) {
			Variable<T> var = null;
			for (int i = 0; i < csp.getVariables().size(); i++) {
				var = csp.getVariables().get(i);
				var.setSolution(var.getAssignment());
			}
			if(isPrintSolution()) {
				csp.printLastSolution();
			}
		}
	}
	
	/**
	 * It returns true if solutions are printed during the research, false otherwise.
	 * @return True if solutions are printed during the research, false otherwise.
	 */
	public boolean isPrintSolution() {
		return printSolution;
	}

	/**
	 * It sets the possibility to print a solution during the search phase or not.
	 * @param printSolution True if solutions are printed, false otherwise.
	 */
	public void setPrintSolution(boolean printSolution) {
		this.printSolution = printSolution;
	}
	
	/**
	 * It resets to restart a new search.
	 */
	protected void reset() {
		nodesVisited = 0;
		stopSearch = false;
		counterSolutions = 0;
	}
	
    /**
     * It returns the name of the algorithm.
     * @return The name of the algorithm.
     */
    public String toString() {
    	return "Generic Search Algorithm with \n" + 
    	nextVariableHeuristic.toString() + " variable-heuristic and \n" +
    	nextValueHeuristic.toString() + " value-heuristic";
    }
	
    // PRINT METHODS
    /**
     * It prints the name of the algorithm.
     */
    public void printAlgorithm() {
    	System.out.println("Search Algorithm:");
    	System.out.println("\tGeneric Search Algorithm with\n\t - " + 
    	nextVariableHeuristic.toString() + "\n\t - " +
    	nextValueHeuristic.toString());
    }
    
	/**
	 * It prints the statistics of the search phase.
	 */
	public void printStatistics() {
		System.out.println("Statistics:");
		System.out.println("\tNodes Visited: " + nodesVisited);
	}
	
}
