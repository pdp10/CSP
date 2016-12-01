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
 * 			v.1.0 (16/06/2007): Documentation and codify of the class.
 */
package csp;

/**
 * This class represents a generic hard constraint between Variables.
 * 
 * @author Piero Dalle Pezze
 * @version 1.0
 * @param <T> the type of objects that this object may be compared to
 */
public abstract class HardConstraint<T extends Comparable<T>> extends Constraint<T> {
	
    // CONSTRUCTORS 
    /**
     * It initializes a binary hard constraint.
     * @param variable1 The first variable of the binary hard constraint.
     * @param variable2 The second variable of the binary hard constraint.
     */   
    public HardConstraint(Variable<T> variable1, Variable<T> variable2) {
        super(variable1, variable2);
    }
    
    /**
     * {@inheritDoc}
     */
    public String toString() {
    	return new String(var1.toString() + " hard-constraint " + var2.toString());
    }
    
}

