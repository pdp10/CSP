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
 * This is a CSP generator. It receives four parameters:
 * <ul>
 * <li>N : The number of variables.</li>
 * <li>D : The capacity of the domain.</li>
 * <li>d : The density which defines the number of constraints in percentage.</li>
 * <li>T : The tightness which defines the number of value pairs disallowed in percentage.</li>
 * </ul>
 * 
 * @author Piero Dalle Pezze
 * @version 1.1
 */
public abstract class ProblemGenerator {

	/**
	 * The number of variables
	 */
	protected int N = 0;

	/**
	 * The capacity of the domain.
	 */
	protected int D = 0;

	/**
	 * The density of the problem.
	 */
	protected float d = 0.0f;

	/**
	 * The tightness of the problem.
	 */
	protected float T = 0.0f;

	/**
	 * The csp generated.
	 */
	protected CSP<Integer> csp = null;

	/**
	 * The random generator.
	 */
	protected Random random = null;

	/**
	 * It constructs a problem generator.
	 */
	public ProblemGenerator() { }

	/**
	 * It constructs a problem generator by defining the four parameters.
	 * @param N The number of variables
	 * @param D The capacity of the domain.
	 * @param d The density of the problem.
	 * @param T The tightness of the problem.
	 */
	public ProblemGenerator(int N, int D, float d, float T) {
		this.N = N;
		this.D = D;
		this.d = d;
		this.T = T;
	}

	/**
	 * It generates a csp, if parameters are correct.
	 * @return True if the CSP is generated, false otherwise.
	 */
	public boolean generate() {
		if (testParameters()) {
			csp = null;
			return false;
		}
		random = new Random();
		// it invokes specific methods to create variables and constraints.
		LinkedList<Variable<Integer>> variables = generateVariables();
		LinkedList<Constraint<Integer>> constraints = generateConstraints(variables);
		csp = new CSP<Integer>(variables, constraints);
		return true;
	}

	/**
	 * It returns true if parameters are correct, false otherwise.
	 * @return True if parameters are correct, false otherwise.
	 */
	public boolean testParameters() {
		if (N < 2)
			return true;
		if (D < 2)
			return true;
		if (d < 0.0 || d > 1.0)
			return true;
		if (T < 0.0 || T >= 1.0)
			return true;
		return false;
	}

	/**
	 * It generates a list of integer variables.
	 * @return A list of integer variables.
	 */
	protected abstract LinkedList<Variable<Integer>> generateVariables();

	/**
	 * It generates a list of constraints defined on integer variables.
	 * @param variables A list of variables.
	 * @return A list of constraints defined on integer variables.
	 */
	protected abstract LinkedList<Constraint<Integer>> generateConstraints(LinkedList<Variable<Integer>> variables);

	/**
	 * It gets the CSP generated.
	 * @return the CSP
	 */
	public CSP<Integer> getCSP() {
		return csp;
	}

	/**
	 * It gets the density of the problem.
	 * @return the density
	 */
	public float getDensity() {
		return d;
	}

	/**
	 * It sets the density of the problem.
	 * @param d The density to set
	 */
	public void setDensity(float d) {
		this.d = d;
	}

	/**
	 * It gets the domain capacity of the problem.
	 * @return the domain capacity
	 */
	public int getD() {
		return D;
	}

	/**
	 * It sets the domain capacity of the problem.
	 * @param D the domain capacity to set
	 */
	public void setD(int D) {
		this.D = D;
	}

	/**
	 * It gets the number of variables of the problem.
	 * @return the number of variables
	 */
	public int getN() {
		return N;
	}

	/**
	 * It sets the number of variables of the problem. 
	 * @param N The number of variables to set
	 */
	public void setN(int N) {
		this.N = N;
	}

	/**
	 * It gets the tightness of the problem.
	 * 
	 * @return the tightness
	 */
	public float getT() {
		return T;
	}

	/**
	 * It sets the tightness of the problem.
	 * @param T The tightness to set
	 */
	public void setT(float T) {
		this.T = T;
	}

	/**
	 * It returns the name of the generator.
	 * @return the name of the generator.
	 */
	public String toString() {
		return new String("Problem Generator");
	}
	
	/**
	 * It prints the generator data.
	 */
	public void print() {
		System.out.println("Generator:");
		System.out.println("\t" + toString());
		System.out.println("\t[" + "N=" + N +
								 ", D=" + D +
								 ", d=" + d +
								 ", T=" + T + "]");
	}
}
