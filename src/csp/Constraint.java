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
 * Created on: 16/06/2007
 * Modifies:
 * 			v.1.1 (18/06/2007): Documentation of the class.
 * 			v.1.0 (16/06/2007): Codify of the class.
 */
package csp;

/**
 * This class represents a generic constraint between Variables.
 * 
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public abstract class Constraint<T extends Comparable<T>> {

	/**
	 * The first variable of the constraint.
	 */
	protected Variable<T> var1;
	
	/**
	 * The second variable of the constraint.
	 */
	protected Variable<T> var2;
	
    // CONSTRUCTORS
 
    /**
     * It initializes a binary constraint.
     * @param variable1 The first variable of the binary constraint.
     * @param variable2 The second variable of the binary constraint.
     */
    public Constraint(Variable<T> variable1, Variable<T> variable2) {
    	var1 = variable1;
    	var2 = variable2;
    	if(var1 == null) var1 = new Variable<T>("no-name");
    	if(var2 == null) var2 = new Variable<T>("no-name");
    }
    
    // VARIABLES
    /**
     * It returns the first variable of the constraint.
     * @return The first variable of the constraint.
     */
    public Variable<T> getFirstVariable() {
    	return var1;
    }
    
    /**
     * It returns the second variable of the constraint.
     * @return The second variable of the constraint.
     */
    public Variable<T> getSecondVariable() {
    	return var2;
    }
    
    /**
     * It returns true if the constraint is defined by var1 and var2, false otherwise.
     * 
     * @param var1 A variable.
     * @param var2 A variable.
     * @return True if the constraint is defined by var1 and var2, false otherwise
     */
    public boolean contains(Variable<T> var1, Variable<T> var2) {
    	return (this.var1.equals(var1) && this.var2.equals(var2)) ||
    		   (this.var1.equals(var2) && this.var2.equals(var1));
    }
    
    /**
     * It returns true if the constraint contains the variable var, false otherwise.
     * 
     * @param var A variable.
     * @return True if the constraint contains the variable var, false otherwise
     */
    public boolean contains(Variable<T> var) {
    	return var1.equals(var) || var2.equals(var);
    }
    
    /**
     * It returns true if the values value1 and value2, respectively for the first
     * variable and the second variable, holds the constraint satisfied.
     * @param value1 The value for the first variable.
     * @param value2 The value for the second variable.
     * @return True if the constraint is still consistent with this assignment, false
     * otherwise.
     */
    public abstract boolean isConsistent(T value1, T value2);
    
    /**
     * It returns a representation of the constraint.
     * @return A string that represents the constraint.
     */
    public String toString() {
    	return new String(var1.toString() + " constraint " + var2.toString());
    }
    
}

