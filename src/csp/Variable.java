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
 * 			v.1.1 (19/06/2007): Added: getSolution(), setSolution(), id, counter, getId().
 * 								Changed method: toStringSolution().
 * 			v.1.0 (16/06/2007): Documentation and codify of the class.
 */
package csp;

import java.util.*;

/**
 * This class represents a generic variable.
 * 
 * @author Piero Dalle Pezze
 * @version 1.1
 * @param <T> the type of objects that this object may be compared to
 */
public class Variable<T extends Comparable<T>> {

	/**
	 * The counter of variables created
	 */
	private static long counter = 0;
	
	/**
	 * The id of the variable
	 */
	private long id = 0;
	
	// DATA FIELDS	
	/** 
	 * The name of the variable. 
	 */
	private String name;	

	/** 
	 * The current assignment of the variable. 
	 */
	private T assignment = null;
	
	/** 
	 * The current assignment of the variable which represents a solution of the problem. 
	 */
	private T solution = null;
	
	/** 
	 * The domain of the variable
	 */
	private LinkedList<T> domain;
	
	
    // CONSTRUCTORS
	/**
	 * It constructs a Variable Object with a name and an empty domain.
	 * @param name The name of the variable.
	 */
    public Variable(String name) {
    	id = counter;
    	counter++;
    	if(name != null)
    		this.name = name;
       	else 
       		this.name = "var_null";
    	// It initializes with the empty domain.
    	domain = new LinkedList<T>();
    }
    
	/**
	 * It constructs a Variable Object with a name and a domain.
	 * @param name The name of the variable.
	 * @param domain The domain where the variable is defined.
	 */ 
    public Variable(String name, LinkedList<T> domain) {
    	id = counter;
    	counter++;
       	if(name != null)
       		this.name = name;
       	else 
       		this.name = "var_null";
    	// It initializes with the received domain.
       	if(domain != null)
       		this.domain = domain;
       	else
       		this.domain = new LinkedList<T>();
    }
    
    
    // METHODS
    /**
     * It returns the current assignment to the variable.
     * @return The current assignment, if it exists, to the variable, null otherwise.
     */
    public T getAssignment() {
    	if(assignment != null) {
    		return assignment;
    	} else {
    		return null;
    	}
    }
    
    /**
     * It gives an assignment to the variable.
     * @param assignment A domain value gived to the variable.
     */
    public void assign(T assignment) {
    	this.assignment = assignment;
    }
    
    /**
     * It removes the current assignment to the variable.
     */
    public void clearAssignment() {
    	assignment = null;
    }

    /**
     * It sets a new domain to the variable.
     * @param domain The new domain of the variable.
     */
    public void setDomain(LinkedList<T> domain) {
    	// It initializes with the received domain.
    	this.domain = domain;
    }
    
    /**
     * It returns the domain of the variable.
     * @return The domain of the variable.
     */
    public LinkedList<T> getDomain() {
    	return domain;
    }
    
    /**
     * It returns true if the domain is empty, false otherwise.
     * @return True if the domain is empty, false otherwise.
     */
    public boolean domainIsEmpty() {
    	if(domain == null) 
    		return true;
    	return domain.isEmpty();
    }   
    
    /**
     * It removes from the domain the object value.
     * @param object The object of the domain which will be removed.
     */
    public void remove(T object) {
    	domain.remove(object);
    }
    
    /**
     * It removes from the domain a list of values.
     * @param objects The list of Objects of the domain which will be removed.
     */
    public void remove(LinkedList<T> objects) {
    	if(domain != null) {
    		for(int i = 0; i < objects.size(); i++) {
    			domain.remove(objects.get(i));
    		}
    	}
    }
   
    /**
     * It adds to the domain the object value.
     * @param object The object of the domain which will be added.
     */
    public void add(T object) {
    	if(domain == null) {
    		domain = new LinkedList<T>();
    	}
    	domain.add(object);
    }
    
    /**
     * It adds to the domain a list of values.
     * @param objects The list of Objects of the domain which will be added.
     */
    public void add(LinkedList<T> objects) {
    	if(domain == null) {
    		domain = new LinkedList<T>();
    	}
    	for(int i = 0; i < objects.size(); i++) {
    		domain.add(objects.get(i));
    	}
    }
    
   
    // PRINT METHODS
    /**
     * It returns the variable name.
     * @return The variable name.
     */
    public String toString() {
    	return name;
    }
    
    /**
     * It returns the domain of the variable in the form {v1,v2,v3,..}.
     * @return The domain of the variable.
     */
    public String toStringDomain() {
    	String print = new String("{");
    	if(domain != null) {
    		for(int i = 0; i < domain.size(); i++) {
    			print = print + domain.get(i).toString();
    			if(i + 1 < domain.size()) {
    				print = print + ",";
    			}
    		}
    	}
    	print = print + "}";
    	return print;
    }
    
    /**
     * It returns the variable assignment.
     * @return The variable assignment.
     */
    public String toStringAssignment() {
    	if(assignment != null)
    		return assignment.toString();
    	else
    		return "no assignment";
    }
 
    /**
     * It returns the variable assignment which represents the solution.
     * @return The variable assignment.
     */
    public String toStringSolution() {
    	if(solution != null)
    		return solution.toString();
    	else
    		return "---";
    }
    
    /**
     * It returns true if this variable and var are equals, false otherwise.
     * @param var The variable to compare.
     * @return True if this variable and var are equals, false otherwise.
     */
    public final boolean equals(Variable var) {
		return id == var.getId();
	}

	/**
	 * It returns the current attribution to this variable that makes a solution of the problem.
	 * @return the solution.
	 */
	public T getSolution() {
		return solution;
	}

	/**
	 * It sets the current value for this variable which represents a solution of the problem
	 * @param solution the solution to set.
	 */
	public void setSolution(T solution) {
		this.solution = solution;
	}
    
	/**
	 * It returns the id of the variable.
	 * @return the id of the variable.
	 */
	public long getId() {
		return id;
	}
	
}

