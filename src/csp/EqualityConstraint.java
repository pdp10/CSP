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
 * 			v.1.1 (18/06/2007): Documentation of the method toString().
 * 			v.1.0 (16/06/2007): Documentation and codify of the class.
 */
package csp;

/**
 * This class represents the equality hard constraint between Variables.
 * 
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public class EqualityConstraint<T extends Comparable<T>> extends HardConstraint<T> {
	
    // CONSTRUCTORS
    /**
	 * It initializes an equality binary hard constraint.
	 * 
	 * @param var1
	 *            The first variable of the binary hard constraint.
	 * @param var2
	 *            The second variable of the binary hard constraint.
	 */   
    public EqualityConstraint(Variable<T> var1, Variable<T> var2) {
    	super(var1, var2);
    }
    
    /**
     * It returns true if the values value1 and value2, respectively for the first
     * variable and the second variable, holds the equality constraint satisfied.
     * @param value1 The value for the first variable.
     * @param value2 The value for the second variable.
     * @return True if the constraint is still consistent with this assignment, false
     * otherwise.
     */
    public boolean isConsistent(T value1, T value2) {
    	if(value1 != null && value2 != null && value1.equals(value2)) {
    		return true;
    	}
    	return false;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
    	return new String(var1.toString() + " == " + var2.toString());
    }
    
}