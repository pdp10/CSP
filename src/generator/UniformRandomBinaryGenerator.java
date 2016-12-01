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
 * 			v.1.1 (19/06/2007): Documentation of the class.
 * 			v.1.0 (18/06/2007): Codify of the class.
 */
package generator;

import java.util.*;
import csp.*;

/**
 * This is an uniform random binary problem generator. It generates CSP with uniform probability
 * distribution. For further details, you can see 
 * <a href="http://www.ics.uci.edu/%7Edfrost/csp/generator.html">
 * http://www.ics.uci.edu/%7Edfrost/csp/generator.html</a>
 * 
 * @author Piero Dalle Pezze
 * @version 1.1
 * @see ProblemGenerator
 */
public class UniformRandomBinaryGenerator extends ProblemGenerator {

	/**
	 * It constructs an uniform random binary problem generator.
	 */
	public UniformRandomBinaryGenerator() {
		super();
	}

	/**
	 * It constructs an uniform random binary problem generator by defining the four parameters.
	 * @param N The number of variables
	 * @param D The capacity of the domain.
	 * @param d The density of the problem.
	 * @param T The tightness of the problem.
	 */
	public UniformRandomBinaryGenerator(int N, int D, float d, float T) {
		super(N, D, d, T);
		generate();
	}

	/**
	 * It generates N variables, each one with domain capacity D.
	 * @return A list of integer variables.
	 */
	protected LinkedList<Variable<Integer>> generateVariables() {
		LinkedList<Variable<Integer>> variables = new LinkedList<Variable<Integer>>();
		LinkedList<Integer> domain = null;
		// it generates variables
		for (int i = 0; i < N; i++) {
			domain = new LinkedList<Integer>();
			// it generates domains
			for (int j = 0; j < D; j++) {
				domain.add(new Integer(j));
			}
			variables.add(new Variable<Integer>("var_" + String.valueOf(i), domain));
		}
		return variables;
	}

	/**
	 * It generates a list of constraints defined on integer variables. In general, at start it
	 * generates all possible constraints, then it extracts a number of constraints defined by
	 * the d parameter using an uniform random variable. Afterwhich, for each constraint, it adds 
	 * the value pairs disallowed choosing using again an uniform random variable. The number of 
	 * these value pairs is decided by the T parameter.
	 * @param variables A list of variables.
	 * @return A list of constraints defined on integer variables.
	 */
	protected LinkedList<Constraint<Integer>> generateConstraints(
			LinkedList<Variable<Integer>> variables) {
		LinkedList<Constraint<Integer>> constraints = new LinkedList<Constraint<Integer>>();
		
		// This is the total number of possible constraints.
		int possibleCTs = (N * (N - 1)) / 2;
		// This is the total number of value pairs disallowed.
		int possibleNGs = D * D;
		// It computes the number of constraints in the problem to generate.
		int numberOfConstraints = Math.round((((float) (N * (N - 1))) / 2) * d);
		// It computes the number of value pairs disallowed in the problem to generate.
		int pairsDisallowed = Math.round((float) (T * possibleNGs));
		// It builds an array with all possible constraints.
		ArrayList<GeneratedConstraint<Integer>> CTArray = new ArrayList<GeneratedConstraint<Integer>>(possibleCTs);
		// It builds an array with all value pairs disallowed.
		int[] NGArray = new int[possibleNGs];
		// Temporary data
		GeneratedConstraint<Integer> tempConstraint = null;
		int value = 0;

		int k = 0;
		
		// Initialize the CTarray. It contains all possible constraints.
		for (int i = 0; i < variables.size() - 1; i++) {
			for (int j = i + 1; j < variables.size(); j++) {
				CTArray.add(k, new GeneratedConstraint<Integer>(variables.get(i), variables.get(j)));
				k++;
			}
		}

		// it selects from CTArray numberOfConstraints constraints.
		for (int i = 0; i < numberOfConstraints; i++) {
			
			// 1. CONSTRAINT SELECTION	
			// Choose a random number in [i, PossibleCTs - 1].
			k = i + (int) (random.nextDouble() * (possibleCTs - i));
			// Swap elements [i] and [k].
			tempConstraint = CTArray.get(k);
			CTArray.set(k, CTArray.get(i));
			CTArray.set(i, tempConstraint);
			// It adds the constraint to constraints
			constraints.add(tempConstraint);
		
			// 2. ILLEGAL VALUE PAIRS SELECTION
			// Now, for each constraint, it selects T illegal value pairs. 
			// Initialize the NGarray.
			for(int j = 0; j < possibleNGs; j++) {
				NGArray[j] = j;
			}
			// Select T incompatible pairs.
			for(int t = 0; t < pairsDisallowed; t++) {
				// Choose a random number in [t, possibleNGs-1] 
				k = t + (int) (random.nextDouble() * (possibleNGs - t));
				// Swap elements [t] and [k].
				value = NGArray[k]; 
				NGArray[k] = NGArray[t]; 
				NGArray[t] = value;
				// It adds disallowed pairs to the constraint
				// NOTE: (value / D) <= D because value <= D * D.
				CTArray.get(i).addDisallowedPair(
					new Integer((int) (value / D)), 
					new Integer((int) (value % D)));
			}
		}
		
		return constraints;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return new String("Uniform Random Binary Generator");
	}

}
