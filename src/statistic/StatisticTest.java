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
 * Created on: 20/06/2007
 * Modifies:
 * 			v.1.1 (20/06/2007): Documentation of the class.
 * 			v.1.0 (19/06/2007): Codify of the class.
 */
package statistic;

import java.io.*;
import java.text.*;
import java.util.*;
import algorithm.*;
import generator.*;
import csp.*;
import heuristic.*;

/**
 * This class generates statistics after having solved different kind of generated CSP.
 * It provides a lot of configurable parameters.
 * @author Piero Dalle Pezze
 * @version 1.1
 */
public class StatisticTest {

    /**
     * The number of variables
     */
    private int N = 10;
	
    /**
     * The capacity of the domain.
     */
    private int D = 10;
	
    /**
     * The density of the problem.
     */
    private float d = 0.4f;
	
    /**
     * The tightness of the problem.
     */
    private float T = 0.1f;
	
    /**
     * The number of iterations for problems of the same difficulty. It is used to 
     * compute averages and variances.
     */
    private int iterations = 200;
	
    /**
     * The number of tests. For each test a parameter can change increasing or decreasing
     * difficulty of the problems.
     */
    private int numOfTest = 10;
	
    /**
     * It defines the variable-heuristic used in the search phase.
     */
    private int variableHeuristic = 1;
	
    /**
     * It defines the value-heuristic used in the search phase.
     */
    private int valueHeuristic = 0;
	
    /**
     * It defines the name of the file to print statistics.
     */
    private String filename = "test.txt";
	
    // STEPS
    // They are used to increase or decrease the difficulty of the problem
    // when more than one test is setted.
    /**
     * The step of the number of variables.
     */
    private int stepN = 1;

    /**
     * The step of the domain capacity.
     */
    private int stepD = 1;
	
    /**
     * The step of the density.
     */
    private float stepd = 0.1f;

    /**
     * The step of the tightness.
     */
    private float stepT = 0.01f;
	
    // HEURISTICS
    /**
     * It selects the variable without changing the original ordering. It is a non-heuristic.
     */
    public static final int NATURAL_VARIABLE_HEURISTIC = 1;

    /**
     * It selects the most constrained variable.
     */
    public static final int MOST_CONSTRAINED_VARIABLE_HEURISTIC = 2;

    /**
     * It selects the variable in a casual way.
     */
    public static final int RANDOM_VARIABLE_HEURISTIC = 3;
	
    /**
     * It selects the variable with the minimum domain.
     */
    public static final int MINIMUM_DOMAIN_VARIABLE_HEURISTIC = 4;
	
    /**
     * It selects the value of the domain without changing the order. It is a non-heuristic.
     */
    public static final int NATURAL_VALUE_HEURISTIC = -1;
	
    /**
     * It selects the minimum value of the domain.
     */
    public static final int MINIMUM_VALUE_HEURISTIC = -2;
	
    /**
     * It selects the maximum value of the domain.
     */
    public static final int MAXIMUM_VALUE_HEURISTIC = -3;
	
    /**
     * It selects the median value of the domain.
     */
    public static final int MEDIAN_VALUE_HEURISTIC = -4;
	
    /**
     * The parameter to change during the advancement of tests.
     */
    private int parameter = 3;
	
    /**
     * The search algorithm used. 
     */
    private SearchAlgorithm<Integer> searchAlgorithm = null;
	
    /**
     * The problem generator used. 
     */
    private ProblemGenerator problemGenerator = null;
	
    // used for statistics
    private long[] times = new long[iterations];
    private long[] nodes = new long[iterations];
	
    // I moment (average) E[X] = SUMi(xi) * 1/N
    /**
     * The execution time average.
     */
    private double averageTime = 0.0;
	
    /**
     * The average of the number of nodes visited.
     */
    private double averageNodes = 0.0;
	
    // II moment (variance) VAR(X) = SUMi(xi - E[X]) * 1/N 
    /**
     * The execution time variance.
     */
    private double varianceTime = 0.0;
	
    /**
     * The variance of the number of nodes visited.
     */
    private double varianceNodes = 0.0;
	
    /**
     * The standard deviation of the time.
     */
    private double sdTime = 0.0;
	
    /**
     * The standard deviation of the nodes.
     */
    private double sdNodes = 0.0;
	
    // III moment / sd^3 (skewness) SUMi(xi - E[X])^3 * 1/N * 1/sd^3
    // It tells informations about the symmetry of the curve. 
    // 1. positive skew: The right tail is the longest; the mass of the distribution is 
    // concentrated on the left of the figure. The distribution is said to be right-skewed.
    // 2. negative skew: The left tail is the longest; the mass of the distribution is 
    // concentrated on the right of the figure. The distribution is said to be left-skewed.
    /**
     * The skewness of the execution times.
     */
    private double skewnessTime = 0.0;
	
    /**
     * The skewness of the number of nodes visited.
     */
    private double skewnessNodes = 0.0;
	
    // IV moment / sd^4 (kurtosis) SUMi(xi - E[X])^4 * 1/N * 1/sd^4
    // It measures the peakedness of the probability distribution
    // 1. positive kurt: The curve is peak respect to the normal distribution.
    // 2. negative kurt: The curve is flat respect to the normal distribution.
    /**
     * The kurtosis of the execution times.
     */
    private double kurtosisTime = 0.0;
	
    /**
     * The kurtosis of the number of nodes visited.
     */
    private double kurtosisNodes = 0.0;
	
    /**
     * The minimum time
     */
    private long minTime = Long.MAX_VALUE;
	
    /**
     * The maximum time
     */
    private long maxTime = 0;
	
    /**
     * The minimum nodes
     */
    private long minNodes = Long.MAX_VALUE;
	
    /**
     * The maximum nodes
     */
    private long maxNodes = 0;
	
    /**
     * The variance coefficient of time.
     */
    private double vcTime = 0.0;
	
    /**
     * The variance coefficient of nodes.
     */
    private double vcNodes = 0.0;
	
    /**
     * It creates a statistic test.
     */
    public StatisticTest() {}
	
    /**
     * It creates a statistic test by defining the four parameters.
     * @param N The number of variables
     * @param D The capacity of the domain.
     * @param d The density of the problem.
     * @param T The tightness of the problem.
     */
    public StatisticTest(int N, int D, float d, float T) { 
	this.N = N;
	this.D = D;
	this.d = d;
	this.T = T;
    }
	
    /**
     * It prints on file all computed statistics.
     * @param rowToPrint the row to print. 
     */
    public boolean printOnFile(String rowToPrint) {
	String row = rowToPrint;
	try {
	    FileOutputStream file = new FileOutputStream(filename, true);
	    PrintStream output = new PrintStream(file);
	    output.println(row);
	    file.close();
	    return true;
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    return false;
	}
    }
	
    /**
     * It generates problems and solves it.
     * @param generator The problem generator used.
     * @param algorithm The search algorithm used.
     */
    public void generateStatistics(ProblemGenerator generator, SearchAlgorithm<Integer> algorithm) {

	problemGenerator = generator;
	searchAlgorithm = algorithm;

	if(searchAlgorithm != null && problemGenerator != null) {

	    Calendar now = Calendar.getInstance();
	    SimpleDateFormat formatter = new SimpleDateFormat(
							      "E yyyy.MM.dd 'at' hh:mm:ss a zzz");
	    printOnFile("\n\n" + formatter.format(now.getTime()) + "\n");
	    printOnFile("N\tD\td\tT\tMIN(Time)\tMAX(Time)\tAV(Time)\tVAR(Time)\tSD(Time)\tVC(Time)\tSKEW(Time)\tKURT(Time)" + 
"\tMIN(Nodes)\tMAX(Nodes)\tAV(Nodes)\tVAR(Nodes)\tSD(Nodes)\tVC(Nodes)\tSKEW(Nodes)\tKURT(Nodes)");

	    // This cycle is used to change N, D, d or T values. 
	    for (int m = 0; m < numOfTest; m++) {

		// used for statistics
		times = new long[iterations];
		nodes = new long[iterations];
		averageTime = 0.0;
		averageNodes = 0.0;
		varianceTime = 0.0;
		varianceNodes = 0.0;
		minTime = Long.MAX_VALUE;
		maxTime = 0;
		minNodes = Long.MAX_VALUE;
		maxNodes = 0;
		sdTime = 0.0;
		sdNodes = 0.0;
		vcTime = 0.0;
		vcNodes = 0.0;
		skewnessTime = 0.0;
		skewnessNodes = 0.0;
		kurtosisTime = 0.0;
		kurtosisNodes = 0.0;
				
		System.out.print("\nTest n." + m);
				
		// This cycle is used to generate the same problems iterations times.
		// Then it computes averages of times and nodes visited.
		for (int i = 0; i < iterations; i++) {
		    problemGenerator.setN(N);
		    problemGenerator.setD(D);
		    problemGenerator.setDensity(d);
		    problemGenerator.setT(T);
		    problemGenerator.generate();
		    CSP<Integer> csp = generator.getCSP();
		    if (csp != null) {
			searchAlgorithm.setCsp(csp);
						
			// it selects the variable-heuristic
			switch (variableHeuristic) {
			case NATURAL_VARIABLE_HEURISTIC:
			    searchAlgorithm.setNextVariableHeuristic(
				  new NaturalVariableHeuristic<Integer>(csp.getVariables()));
			    break;
			case MOST_CONSTRAINED_VARIABLE_HEURISTIC:
			    searchAlgorithm.setNextVariableHeuristic(
				  new MostConstrainedVariableHeuristic<Integer>(csp
				      .getVariables(), csp.getConstraints()));
			    break;
			case RANDOM_VARIABLE_HEURISTIC:
			    searchAlgorithm.setNextVariableHeuristic(
				  new RandomVariableHeuristic<Integer>(csp.getVariables()));
			    break;
			case MINIMUM_DOMAIN_VARIABLE_HEURISTIC:
			    searchAlgorithm.setNextVariableHeuristic(
				  new MinimumDomainVariableHeuristic<Integer>(csp.getVariables()));
			    break;
			default:
			    searchAlgorithm.setNextVariableHeuristic(
				  new NaturalVariableHeuristic<Integer>(csp.getVariables()));
			    break;
			}
						
			// it selects the value-heuristic
			switch (valueHeuristic) {
			case NATURAL_VALUE_HEURISTIC:
			    searchAlgorithm.setNextValueHeuristic(
                                  new NaturalValueHeuristic<Integer>(csp.getVariables()));
			    break;
			case MINIMUM_VALUE_HEURISTIC:
			    searchAlgorithm.setNextValueHeuristic(
                                  new MinimumValueHeuristic<Integer>(csp.getVariables()));
			    break;
			case MAXIMUM_VALUE_HEURISTIC:
			    searchAlgorithm.setNextValueHeuristic(
                                  new MaximumValueHeuristic<Integer>(csp.getVariables()));
			    break;
			case MEDIAN_VALUE_HEURISTIC:
			    searchAlgorithm.setNextValueHeuristic(
                                  new MedianValueHeuristic<Integer>(csp.getVariables()));
			    break;
			default:
			    searchAlgorithm.setNextValueHeuristic(
                                  new NaturalValueHeuristic<Integer>(csp.getVariables()));
			    break;
			}
						
			// It avoid to print solutions during the search 
			searchAlgorithm.setPrintSolution(false);
						
			// It measures the running time
			Calendar time1 = Calendar.getInstance(), time2;
			searchAlgorithm.search();
			time2 = Calendar.getInstance();
			times[i] = time2.getTimeInMillis() - time1.getTimeInMillis();
			nodes[i] = algorithm.getNodesVisited();
		    }
		    //csp.printProblem();
		    //csp.printLastSolution();
					
		} // end iterations
		System.out.println(" executed!");

		//it computes averages, minimum and maximum values
		for (int i = 0; i < iterations; i++) {
		    averageTime = averageTime + times[i];
		    averageNodes = averageNodes + nodes[i];
		    if(times[i] < minTime) { minTime = times[i]; }
		    if(times[i] > maxTime) { maxTime = times[i]; }
		    if(nodes[i] < minNodes) { minNodes = nodes[i]; }
		    if(nodes[i] > maxNodes) { maxNodes = nodes[i]; }
		}
		if(iterations > 0) {
		    averageTime = averageTime / iterations;
		    averageNodes = averageNodes / iterations;
		}
		//it computes variances, skewness, kurtosis
		for (int i = 0; i < iterations; i++) {
		    varianceTime = varianceTime + Math.pow(times[i] - averageTime, 2);
		    varianceNodes = varianceNodes + Math.pow(nodes[i] - averageNodes, 2);
		    skewnessTime = skewnessTime + Math.pow(times[i] - averageTime, 3);
		    skewnessNodes = skewnessNodes + Math.pow(nodes[i] - averageNodes, 3);
		    kurtosisTime = kurtosisTime + Math.pow(times[i] - averageTime, 4);
		    kurtosisNodes = kurtosisNodes + Math.pow(nodes[i] - averageNodes, 4);
		}
		if(iterations > 0) {
		    varianceTime = varianceTime / iterations;
		    skewnessTime = skewnessTime / iterations;
		    kurtosisTime = kurtosisTime / iterations;
		    varianceNodes = varianceNodes / iterations;
		    skewnessNodes = skewnessNodes / iterations;
		    kurtosisNodes = kurtosisNodes / iterations;
		    sdTime = Math.sqrt(varianceTime);
		    if(averageTime != 0) vcTime = sdTime / averageTime;
		    sdNodes = Math.sqrt(varianceNodes);
		    if(averageNodes != 0) vcNodes = sdNodes / averageNodes;
		    if(sdTime != 0) {
			skewnessTime = skewnessTime / Math.pow(sdTime, 3);
			kurtosisTime = kurtosisTime / Math.pow(sdTime, 4);
		    } else {
			skewnessTime = 0.0;
			kurtosisTime = 0.0;						
		    }
		    if(sdNodes != 0) {
			skewnessNodes = skewnessNodes / Math.pow(sdNodes, 3);
			kurtosisNodes = kurtosisNodes / Math.pow(sdNodes, 4);
		    } else {
			skewnessNodes = 0.0;
			kurtosisNodes = 0.0;	
		    }
		}
		printTestResults();

		// Here N, D, d or T are incremented by the step defined before.
		switch(parameter) {
		case 0 : N = N + stepN; break;
		case 1 : D = D + stepD; break;
		case 2 : d = d + stepd; break;
		case 3 : T = T + stepT; break;
		}
	    }
	    printOnFile("\nUsing:");
	    printOnFile(searchAlgorithm.toString() + "\n");
	    printOnFile(problemGenerator.toString() + "\n");
	    printOnFile("********************************************************************************************************************");
			
	} // end test
	System.out.println("\nTest completed!");
    }

    /** 
     * It prints test results.
     */
    public void printTestResults() {
	if(searchAlgorithm != null) searchAlgorithm.printAlgorithm();
	if(problemGenerator != null) problemGenerator.print();
	System.out.println("Test Results on " + iterations + " iterations:");
	System.out.println("  - Statistics about times:");
	System.out.println("\tMinimum time:\t\t\t" + minTime  + " ms");
	System.out.println("\tMaximum time:\t\t\t" + maxTime + " ms");
	System.out.println("\tAverage time:\t\t\t" + averageTime + " ms");
	System.out.println("\tVariance time:\t\t\t" + varianceTime  + " ms^2");
	System.out.println("\tStandard deviation time:\t" + sdTime + " ms");
	System.out.println("\tVariance coefficient time:\t" + vcTime);
	System.out.println("\tSkewness time:\t\t\t" + skewnessTime);
	System.out.println("\tKurtosis time:\t\t\t" + kurtosisTime);
	System.out.println("  - Statistics about nodes:");
	System.out.println("\tMinimum nodes:\t\t\t" + minNodes + " nodes");
	System.out.println("\tMaximum nodes:\t\t\t" + maxNodes + " nodes");
	System.out.println("\tAverage nodes:\t\t\t" + averageNodes + " nodes");
	System.out.println("\tVariance nodes:\t\t\t" + varianceNodes + " nodes^2");
	System.out.println("\tStandard deviation nodes:\t" + sdNodes + " nodes");
	System.out.println("\tVariance coefficient nodes:\t" + vcNodes);
	System.out.println("\tSkewness nodes:\t\t\t" + skewnessNodes);
	System.out.println("\tKurtosis nodes:\t\t\t" + kurtosisNodes);
	printOnFile(String.valueOf(N) + "\t" + String.valueOf(D) + "\t" +
		    String.valueOf(d) + "\t" + String.valueOf(T) + "\t" +
		    String.valueOf(minTime) + "\t" + String.valueOf(maxTime) + "\t" +
		    String.valueOf(averageTime) + "\t" + String.valueOf(varianceTime) + "\t" +
		    String.valueOf(sdTime) + "\t" + String.valueOf(vcTime) + "\t" +
		    String.valueOf(skewnessTime) + "\t" + String.valueOf(kurtosisTime) + "\t" +
		    String.valueOf(minNodes) + "\t" + String.valueOf(maxNodes) + "\t" +
		    String.valueOf(averageNodes) + "\t" + String.valueOf(varianceNodes) + "\t" +
		    String.valueOf(sdNodes) + "\t" + String.valueOf(vcNodes) + "\t" +
		    String.valueOf(skewnessNodes) + "\t" + String.valueOf(kurtosisNodes));
    }
	
    /**
     * It returns the density
     * @return the d
     */
    public float getDensity() {
	return d;
    }

    /**
     * It returns the domain capacity.
     * @return the d
     */
    public int getD() {
	return D;
    }

    /**
     * It returns the filename
     * @return the filename
     */
    public String getFilename() {
	return filename;
    }

    /**
     * It returns the variable-heuristic
     * @return the heuristic
     */
    public int getVariableHeuristic() {
	return variableHeuristic;
    }

    /**
     * It returns the value-heuristic
     * @return the heuristic
     */
    public int getValueHeuristic() {
	return valueHeuristic;
    }
	
    /**
     * It returns the number of iterations
     * @return the iterations
     */
    public int getIterations() {
	return iterations;
    }

    /**
     * It returns the number of variable
     * @return the n
     */
    public int getN() {
	return N;
    }

    /**
     * It returns the number of test.
     * @return the numOfTest
     */
    public int getNumOfTest() {
	return numOfTest;
    }

    /**
     * It returns the step of the density
     * @return the stepd
     */
    public float getStepd() {
	return stepd;
    }

    /**
     * It returns the step of the domain capacity
     * @return the stepD
     */
    public int getStepD() {
	return stepD;
    }

    /**
     * It returns the step of the number of variable
     * @return the stepN
     */
    public int getStepN() {
	return stepN;
    }

    /**
     * It returns the tightness step
     * @return the stepT
     */
    public float getStepT() {
	return stepT;
    }

    /**
     * It returns the tightness
     * @return the t
     */
    public float getT() {
	return T;
    }

    /**
     * It sets the density of the problem
     * @param d the d to set
     */
    public void setDensity(float d) {
	if(d >= 0.0f && d <= 1.0f)
	    this.d = d;
    }

    /**
     * It sets the domain capacity of the problem.
     * @param d the d to set
     */
    public void setD(int d) {
	if(d > 1)
	    D = d;
    }

    /**
     * The name of the file to print results.
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
	this.filename = filename;
    }

    /**
     * It sets the variable-heuristic.
     * @param variableHeuristic the heuristic to set
     */
    public void setVariableHeuristic(int variableHeuristic) {
	if(variableHeuristic >= 1 && variableHeuristic <= 4)
	    this.variableHeuristic = variableHeuristic;
    }

    /**
     * It sets the value-heuristic.
     * @param valueHeuristic the heuristic to set
     */
    public void setValueHeuristic(int valueHeuristic) {
	if(valueHeuristic <= -1 && valueHeuristic >= -4)
	    this.valueHeuristic = valueHeuristic;
    }
	
    /**
     * It sets the number of iterations with the same configuration (the accuracy of the average).
     * @param iterations the iterations to set
     */
    public void setIterations(int iterations) {
	if(iterations >= 1)
	    this.iterations = iterations;
    }

    /**
     * It sets the number of variables
     * @param n the n to set
     */
    public void setN(int n) {
	if(n > 1)
	    N = n;
    }

    /**
     * It sets the number of test to do.
     * @param numOfTest the numOfTest to set
     */
    public void setNumOfTest(int numOfTest) {
	if(numOfTest > 0)
	    this.numOfTest = numOfTest;
    }

    /**
     * It sets the step of density.
     * @param stepd the stepd to set
     */
    public void setStepd(float stepd) {
	if(stepd > 0.0 && stepD < 1.0)
	    this.stepd = stepd;
    }

    /**
     * It sets the step of cardinality of domains
     * @param stepD the stepD to set
     */
    public void setStepD(int stepD) {
	if(stepD > 0)
	    this.stepD = stepD;
    }

    /**
     * It sets the step of the number of variables
     * @param stepN the stepN to set
     */
    public void setStepN(int stepN) {
	if(stepN > 0)
	    this.stepN = stepN;
    }

    /**
     * It sets the tightness step of the generator of statistics.
     * @param stepT the step of the tightness to set
     */
    public void setStepT(float stepT) {
	if(stepT > 0.0 && stepT < 1.0)
	    this.stepT = stepT;
    }

    /**
     * It sets the tightness.
     * @param t the tightness to set
     */
    public void setT(float t) {
	if(t >= 0.0f && t < 1.0)
	    T = t;
    }
	
    /**
     * The parameter that modify the test. For each test executed, 
     * this parameter will increment of a value defined on its step.
     * @param parameter It must be "N", "D", "d" or "T".
     */
    public void setTestParameter(String parameter) {
	if(parameter.equals("N")) {
	    this.parameter = 0;
	} else if(parameter.equals("D")) {
	    this.parameter = 1;
	} else if(parameter.equals("d")) {
	    this.parameter = 2;
	} else if(parameter.equals("T")) {
	    this.parameter = 3;
	}
    }

    /**
     * It returns the average of nodes visited on the last test.
     * @return the averageNode
     */
    public double getAverageNodes() {
	return averageNodes;
    }

    /**
     * It returns the average time on the last test.
     * @return the averageTime
     */
    public double getAverageTime() {
	return averageTime;
    }

    /**
     * It returns the array of nodes visited on the last test.
     * @return the nodes
     */
    public long[] getNodes() {
	return nodes;
    }

    /**
     * It returns the array of times on the last test.
     * @return the times
     */
    public long[] getTimes() {
	return times;
    }

    /**
     * It returns the variance of nodes visited on the last test.
     * @return the varianceNode
     */
    public double getVarianceNodes() {
	return varianceNodes;
    }

    /**
     * It returns the variance of times on the last test.
     * @return the varianceTime
     */
    public double getVarianceTime() {
	return varianceTime;
    }
	
    /**
     * It returns the minimum time on the last test.
     * @return the minTime
     */
    public long getMinimumTime() {
	return minTime;
    }

    /**
     * It returns the maximum time on the last test.
     * @return the maxTime
     */
    public long getMaximumTime() {
	return maxTime;
    }
	
    /**
     * It returns the minimum number of nodes visited on the last test.
     * @return the minNodes
     */
    public long getMinimumNodes() {
	return minNodes;
    }

    /**
     * It returns the maximum number of nodes on the last test.
     * @return the maxNodes
     */
    public long getMaximumNodes() {
	return maxNodes;
    }
	
    /**
     * It returns the standard deviation of the time on the last test.
     * @return the sdNodes
     */
    public double getStandardDeviationTime() {
	return sdTime;
    }

    /**
     * It returns the standard deviation of the number of nodes on the last test.
     * @return the sdNodes
     */
    public double getStandardDeviationNodes() {
	return sdNodes;
    }
	
    /**
     * It returns the skewness of the time on the last test.
     * @return the skewnessNodes
     */
    public double getSkewnessTime() {
	return skewnessTime;
    }

    /**
     * It returns the skewness of the number of nodes on the last test.
     * @return the skewnessNodes
     */
    public double getSkewnessNodes() {
	return skewnessNodes;
    }
	
    /**
     * It returns the kurtosis of the time on the last test.
     * @return the kurtosisNodes
     */
    public double getKurtosisTime() {
	return kurtosisTime;
    }

    /**
     * It returns the kurtosis of the number of nodes on the last test.
     * @return the kurtosisNodes
     */
    public double getKurtosisNodes() {
	return kurtosisNodes;
    }
	
}
