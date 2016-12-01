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
 * Created on: 17/06/2007
 * Modifies:
 * 			v.1.1 (18/06/2007): Documentation of the class.
 * 			v.1.0 (17/06/2007): Codify of the class.
 */
package main;

import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import csp.*;
import generator.*;
import heuristic.*;
import algorithm.*;
import statistic.*;

/**
 * This class is used to test the software: "CSP - Backtracking search with
 * forward checking and heuristics."
 * 
 * @author Piero Dalle Pezze
 * @version 1.1
 */
public class CSPTester {

	Integer N, D;
	Float d, T;
	
	Integer Nstep, Dstep;
	Float dstep, Tstep;
	
	String inFile, outFile, testParameter;
	Integer nTests, nIterations, valueHeuristic, variableHeuristic;
	StatisticTest sg = null;
	
	/**
	 * It sets the configuration and starts tests.
	 *
	 */
	void setConfiguration() {
		sg = new StatisticTest();
		sg.setN(N.intValue());
		sg.setD(D.intValue());
		sg.setDensity(d.floatValue());
		sg.setT(T.floatValue());
	    
		// step setting
		sg.setStepN(Nstep.intValue());
		sg.setStepD(Dstep.intValue());
		sg.setStepd(dstep.floatValue());
		sg.setStepT(Tstep.floatValue());
		
		sg.setFilename(outFile);
		// test setting
		sg.setNumOfTest(nTests.intValue());
		sg.setIterations(nIterations.intValue());
		sg.setTestParameter(testParameter);
		sg.setVariableHeuristic(variableHeuristic.intValue());
		sg.setValueHeuristic(valueHeuristic.intValue());
		
	}
	
	/**
	 * It runs the test.
	 */
	public void runTest() {
	    System.out.println("\nSTART TEST" );
		sg.generateStatistics(new UniformRandomBinaryGenerator(), new ForwardChecking<Integer>());
	}
	
	/**
	 * It returns true if the parsing is ok, false otherwise.
	 * @return true if the parsing is ok, false otherwise.
	 */
	boolean parse() {
		boolean parsing = true;
		if(N < 2) {
			System.out.println( "Error. N > 1" );
			parsing = false;
		}
		if(D < 2) {
			System.out.println( "Error. D > 1" );
			parsing = false;
		}
		if(d < 0.0f || d > 1.0f) {
			System.out.println( "Error. d >= 0.0f and d <= 1.0f" );
			parsing = false;
		}
		if(T < 0.0f || T >= 1.0f) {
			System.out.println( "Error. T >= 0.0f and T < 1.0f" );
			parsing = false;
		}
		if(Nstep < 1) {
			System.out.println( "Error. Nstep > 0" );
			parsing = false;
		}
		if(Dstep < 1) {
			System.out.println( "Error. Dstep > 0" );
			parsing = false;
		}
		if(dstep <= 0.0f || dstep >= 1.0f) {
			System.out.println( "Error. dstep > 0.0f and dstep < 1.0f" );
			parsing = false;
		}
		if(Tstep <= 0.0f || Tstep >= 1.0f) {
			System.out.println( "Error. Tstep > 0.0f and Tstep < 1.0f" );
			parsing = false;
		}	
		if(variableHeuristic < 1 || variableHeuristic > 4) {
			System.out.println( "Error. variableHeuristic >= 1 and variableHeuristic <= 4" );
			parsing = false;			
		}
		if(valueHeuristic > -1 || valueHeuristic < -4) {
			System.out.println( "Error. valueHeuristic <= -1 and valueHeuristic >= -4" );
			parsing = false;		
		}
		if(nIterations < 1) {
			System.out.println( "Error. nIteration > 0" );
			parsing = false;
		}
		if(nTests < 1) {
			System.out.println( "Error. nTest > 0" );
			parsing = false;
		}
		if(!testParameter.equals("N") && !testParameter.equals("D") &&
			       !testParameter.equals("d") && !testParameter.equals("T")) {
			System.out.println( "Error. testParameter must be N, D, d or T" );
			parsing = false;
		}
		return parsing;
	}
	
	/**
	 * It makes an interactive test.
	 *
	 */
	public void interactiveTest() {
		BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
		
		try {
		    System.out.println( "\nMAIN PARAMETERS\n" );
		    System.out.print( "Number of variables? " );
		    N = new Integer( in.readLine() );
		    System.out.print( "Domain capacity? " );
		    D = new Integer( in.readLine() );
		    System.out.print( "Density? " );
		    d = new Float( in.readLine() );
		    System.out.print( "Tightness? " );
		    T = new Float( in.readLine() );
		    
		    System.out.println( "\nPARAMETERS of STEP (the step to increment or decrement the problem difficulty\n" );
		    System.out.print( "Step of the number of variables? " );
		    Nstep = new Integer( in.readLine() );
		    System.out.print( "Step of the domain capacity? " );
		    Dstep = new Integer( in.readLine() );
		    System.out.print( "Step of the density? " );
		    dstep = new Float( in.readLine() );
		    System.out.print( "Step of the tightness? " );
		    Tstep = new Float( in.readLine() );
		    
		    System.out.println( "\nOTHERS PARAMETERS\n" );
		    System.out.print( "Name of the file (to print statistics)? " );
		    outFile = new String( in.readLine() );
			outFile = "examples/" + outFile;
		    System.out.print( "Number of tests? " );
		    nTests = new Integer( in.readLine() );
		    System.out.print( "Number of iterations for each test? " );
		    nIterations = new Integer( in.readLine() );
		    System.out.println( "Choose the number of the variable-heuristic?" );
		    System.out.println("\t1. Natural Variable");
		    System.out.println("\t2. Most Constrained Variable");
		    System.out.println("\t3. Random Variable");
		    System.out.println("\t4. Minimum Domain\n? ");
		    variableHeuristic = new Integer( in.readLine() );
		    System.out.println( "Choose the number of the value-heuristic? " );
		    System.out.println("\t1. Natural Value");
		    System.out.println("\t2. Minimum Value");
		    System.out.println("\t3. Maximum Value");
		    System.out.println("\t4. Median Value\n? ");
		    valueHeuristic = new Integer( in.readLine() );
		    valueHeuristic = new Integer(0 - valueHeuristic.intValue());
		    System.out.print( "Choose the test parameter (N, D, d, T)? " );
		    testParameter = new String( in.readLine());
		    
            if(parse())
            	setConfiguration();

		} 
		catch( NumberFormatException e) { System.out.println("Error: number format exception"); }
		catch( IOException e ) { System.out.println("Error: input not read correctly"); }
	}
	
	/**
	 * It reads from a xml configuration file.
	 * @return true if it the file is correct, false otherwise.
	 */
	public boolean readFile() {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;

		BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );

		try {
			System.out.print("Name of the xml configuration file (it must be inside the folder \"examples/\")? ");
			inFile = new String( in.readLine() );
			inFile = "examples/" + inFile;
			System.out.print("Name of the file to print statistics (it will saved in the folder \"examples/\")? ");
			outFile = new String( in.readLine() );
			outFile = "examples/" + outFile;
		}
		catch( IOException e ) { System.out.println("Error: input not read correctly"); }

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inFile);
			N = Integer.parseInt(doc.getElementsByTagName(
					"config").item(0).getAttributes().getNamedItem(
					"N").getNodeValue());
			D = Integer.parseInt(doc.getElementsByTagName(
			"config").item(1).getAttributes().getNamedItem(
			"D").getNodeValue());
			d = Float.parseFloat(doc.getElementsByTagName(
			"config").item(2).getAttributes().getNamedItem(
			"d").getNodeValue());
			T = Float.parseFloat(doc.getElementsByTagName(
			"config").item(3).getAttributes().getNamedItem(
			"T").getNodeValue());
			Nstep = Integer.parseInt(doc.getElementsByTagName(
			"config").item(4).getAttributes().getNamedItem(
			"Nstep").getNodeValue());
			Dstep = Integer.parseInt(doc.getElementsByTagName(
			"config").item(5).getAttributes().getNamedItem(
			"Dstep").getNodeValue());
			dstep = Float.parseFloat(doc.getElementsByTagName(
			"config").item(6).getAttributes().getNamedItem(
			"dstep").getNodeValue());
			Tstep = Float.parseFloat(doc.getElementsByTagName(
			"config").item(7).getAttributes().getNamedItem(
			"Tstep").getNodeValue());
			testParameter = doc.getElementsByTagName(
			"config").item(8).getAttributes().getNamedItem(
			"testParameter").getNodeValue();
			nTests = Integer.parseInt(doc.getElementsByTagName(
			"config").item(9).getAttributes().getNamedItem(
			"nTests").getNodeValue());
			nIterations = Integer.parseInt(doc.getElementsByTagName(
			"config").item(10).getAttributes().getNamedItem(
			"nIterations").getNodeValue());
			valueHeuristic = Integer.parseInt(doc.getElementsByTagName("config").item(11)
					.getAttributes().getNamedItem("valueHeuristic").getNodeValue());
			variableHeuristic = Integer.parseInt(doc.getElementsByTagName("config").item(12)
					.getAttributes().getNamedItem("variableHeuristic").getNodeValue());
			return true;
		} catch (NumberFormatException e) {
			System.out.println(inFile + " doesn't contains numeric values.");
		} catch (IOException ex) {
			System.out.println("Some I/O errors was found in the file: " + inFile);
		} catch (SAXException ex) {
			System.out.println("Parsing error");
		} catch (FactoryConfigurationError ex) {
			System.out.println("Factory error");
		} catch (ParserConfigurationException ex) {
			System.out.println("Parsing error");
		} catch (Exception ex) {
			System.out.println("Corrupted file: " + inFile);
		}
		return false;
	}
	
	/**
	 * It makes a test from file.
	 */
	public void fileTest() {
        if(readFile() && parse()) {
        	setConfiguration();
        	runTest();
        }
	}
	
	/**
	 * It makes a test from file testing all heuristics (4 x 4 tests).
	 */
	public void testHeuristics() {
		if(readFile() && parse()) {
			setConfiguration();
			for(int i = 1; i <= 4; i++) {
				sg.setValueHeuristic(-i);
				for(int j = 1; j <= 4; j++) {
					// it restores main parameters
					sg.setN(N.intValue());
					sg.setD(D.intValue());
					sg.setDensity(d.floatValue());
					sg.setT(T.floatValue());
					sg.setVariableHeuristic(j);
					System.out.println("Variable-Heuristic: ");
					switch(j) {
					case 1: System.out.println("\tNatural Variable"); break;
					case 2: System.out.println("\tMost Constrained Variable"); break;
					case 3: System.out.println("\tRandom Variable"); break;
					case 4: System.out.println("\tMinimum Domain"); break;
					}
					System.out.println("Value-Heuristic: ");
					switch(i) {
					case 1: System.out.println("\tNatural Value"); break;
					case 2: System.out.println("\tMinimum Value"); break;
					case 3: System.out.println("\tMaximum Value"); break;
					case 4: System.out.println("\tMedian Value"); break;
					}
					runTest();
				}
			}
		}
	}
	
	
	// THE FOLLOWING FUNCTIONS ARE USED ONLY TO TEST:
	//  1. THE SEARCH IN A CSP;
	//  2. THE GENERATOR OF PROBLEMS (it generate the CSP);
	//  3. THE GENERATOR OF PROBLEMS AND THE SEARCH OF SOLUTIONS;
	//  4. THE COMPLETE TEST WITH STATISTICS.
	
	/**
	 * It tests the search in a CSP.
	 */
	public void manualTest() {
		// STEP 1
		// It initializes domains and variables.
		LinkedList<Integer> domainV0 = new LinkedList<Integer>();
		domainV0.add(new Integer(0));
		domainV0.add(new Integer(1));
		domainV0.add(new Integer(2));
		Variable<Integer> v0 = new Variable<Integer>("x0", domainV0);

		LinkedList<Integer> domainV1 = new LinkedList<Integer>();
		domainV1.add(new Integer(0));
		domainV1.add(new Integer(1));
		domainV1.add(new Integer(2));
		Variable<Integer> v1 = new Variable<Integer>("x1", domainV1);

		LinkedList<Integer> domainV2 = new LinkedList<Integer>();
		domainV2.add(new Integer(0));
		domainV2.add(new Integer(1));
		domainV2.add(new Integer(2));
		Variable<Integer> v2 = new Variable<Integer>("x2", domainV2);

		LinkedList<Integer> domainV3 = new LinkedList<Integer>();
		domainV3.add(new Integer(0));
		domainV3.add(new Integer(1));
		domainV3.add(new Integer(2));
		Variable<Integer> v3 = new Variable<Integer>("x3", domainV3);

		// STEP 2
		// It initializes hard constraints beetween variables
		DisequalityConstraint<Integer> dc0 = new DisequalityConstraint<Integer>(v0, v1);
		DisequalityConstraint<Integer> dc1 = new DisequalityConstraint<Integer>(v1, v3);
		DisequalityConstraint<Integer> dc2 = new DisequalityConstraint<Integer>(v0,v3);
		EqualityConstraint<Integer> ec0 = new EqualityConstraint<Integer>(v2, v3);

		// STEP 3
		// It adds variables and constraints to two LinkedList
		LinkedList<Variable<Integer>> variables = new LinkedList<Variable<Integer>>();
		variables.add(v0);
		variables.add(v1);
		variables.add(v2);
		variables.add(v3);

		LinkedList<Constraint<Integer>> constraints = new LinkedList<Constraint<Integer>>();
		constraints.add(dc0);
		constraints.add(dc1);
		constraints.add(dc2);
		constraints.add(ec0);

		// STEP 4
		// It initializes the problem
		CSP<Integer> csp = new CSP<Integer>(variables, constraints);

		//STEP 5
		// It sets the search algorithm and changes the original heuristic
		SearchAlgorithm<Integer> algorithm = new ForwardChecking<Integer>(csp);
		//algorithm.setNextVariableHeuristic(new NaturalVariableHeuristic<Integer>(csp.getVariables()));
		algorithm.setNextVariableHeuristic(new MostConstrainedVariableHeuristic<Integer>(csp.getVariables(),csp.getConstraints()));
		//algorithm.setNextVariableHeuristic(new RandomVariableHeuristic<Integer>(csp.getVariables()));
		//algorithm.setNextVariableHeuristic(new MinimumDomainVariableHeuristic<Integer>(csp.getVariables()));

		//algorithm.setNextValueHeuristic(new NaturalValueHeuristic<Integer>(variables));
		//algorithm.setNextValueHeuristic(new MinimumValueHeuristic<Integer>(variables));
		//algorithm.setNextValueHeuristic(new MaximumValueHeuristic<Integer>(variables));
		algorithm.setNextValueHeuristic(new MedianValueHeuristic<Integer>(variables));
		
		// STEP 6
		// It prints the problem and solves it.
		csp.printProblem();
		algorithm.printAlgorithm();
		algorithm.search();
		//algorithm.search(5);
		//algorithm.searchAll();
		//csp.printLastSolution();
			
		// STEP 7
		// It prints statistics
		algorithm.printStatistics();
	}

	/**
	 * It tests the generator of problems
	 */
	public void generatorTest() {
		ProblemGenerator generator = new UniformRandomBinaryGenerator(25, 10,
				0.1f, 0.1f);
		generator.print();
		generator.generate();
		CSP csp = generator.getCSP();
		csp.printProblem();
	}

	/**
	 * It makes a test with the generator of problems and the search algorithm.
	 */
	public void generatedTest() {
		ProblemGenerator generator = new UniformRandomBinaryGenerator(25, 10,
				0.3f, 0.4f);
		CSP<Integer> csp = generator.getCSP();
		generator.print();
		if (csp != null) {
			csp.printProblem();
			// It sets the search algorithm and changes the original heuristic
			SearchAlgorithm<Integer> algorithm = new ForwardChecking<Integer>(csp);
			//algorithm.setNextVariableHeuristic(new NaturalVariableHeuristic<Integer>(csp.getVariables()));
			algorithm.setNextVariableHeuristic(new MostConstrainedVariableHeuristic<Integer>(csp.getVariables(),csp.getConstraints()));
			//algorithm.setNextVariableHeuristic(new RandomVariableHeuristic<Integer>(csp.getVariables()));
			//algorithm.setNextVariableHeuristic(new MinimumDomainVariableHeuristic<Integer>(csp.getVariables()));
			csp.printProblem();
			algorithm.printAlgorithm();
			algorithm.search();
			//algorithm.search(5);
			//algorithm.searchAll();
		}
	}
	
	/**
	 * It makes a complete test with statistics.
	 */
	public void generateStatistics() {
		StatisticTest sg = new StatisticTest(15, 10, 0.3f, 0.2f);
		// step setting
		sg.setStepN(1);
		sg.setStepD(1);
		sg.setStepd(0.1f);
		sg.setStepT(0.05f);
		
		sg.setFilename("Test.txt");
		// test setting
		sg.setNumOfTest(10);
		sg.setIterations(1000);
		sg.setTestParameter("T");
		sg.setVariableHeuristic(StatisticTest.MOST_CONSTRAINED_VARIABLE_HEURISTIC);
		sg.setValueHeuristic(StatisticTest.MINIMUM_VALUE_HEURISTIC);
		sg.generateStatistics(new UniformRandomBinaryGenerator(), new ForwardChecking<Integer>());
	}
	
	
	/**
	 * The main of the application
	 * 
	 * @param args
	 *            A list of input arguments.
	 */
	public static void main(String[] args) {
		
		CSPTester tester = new CSPTester();
		BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
		Integer input = null;
		
		boolean exit = false;
		System.out.print( "\n\t\t*** Constraint Programming Project ***\n\n" );
		while(!exit) {
			System.out.println("Choose one modality:");
			System.out.println("\t1. Manual configuration");
			System.out.println("\t2. File configuration");
			System.out.println("\t3. File configuration to test with all heuristics");
			System.out.println("\t4. It tests the CSP (used for testing)");
			System.out.println("\t5. It tests the generator (used for testing)");
			System.out.println("\t6. It tests the generator and the CSP (used for testing)");
			System.out.println("\t7. It tests the generator, the CSP and the statistical module (used for testing)");
			System.out.println("\t0. Exit");
			try {
				input = new Integer(in.readLine());
				switch(input.intValue()) {
				case 0: exit = true; break;
				case 1: tester.interactiveTest(); break;
				case 2: tester.fileTest(); break;
				case 3: tester.testHeuristics(); break;
				case 4: tester.manualTest(); break;
				case 5: tester.generatorTest(); break;
				case 6: tester.generatedTest(); break;
				case 7: tester.generateStatistics(); break;
				default: System.out.println("You have to choose an option in [0..7].");
				}

			}
			catch( NumberFormatException e) { System.out.println("Error: number format exception"); }
			catch( IOException e ) { System.out.println("Error: input not read correctly"); }
			if(!exit)
			System.out.println("\n*********************************************************\n\n" );
		}
		System.out.println("\nGoodbye!");
	}

}
