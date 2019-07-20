import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author: Ivan Chan
 * @version 1.0
 * 
 * Read and store datasets
 */

public class InputData {
	
	private double[][] trainSet;
	private double[] classAttribute;
	private double[][] testSet;
	ArrayList<Double> classList = new ArrayList<Double>();
	ArrayList<String> attributeType = new ArrayList<String>();
	ArrayList<String> attributeName = new ArrayList<String>();
	private int instanceCount;
	private int attributeCount;
	private int testInstanceCount;
	private double lowest;
	private double highest;
		
	String fileName = GA.getFileName();
	
	//scan training set
	Scanner scanTrainSet; {
		try {
			scanTrainSet = new Scanner(new File(fileName+"_train.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} 
		//count number of attributes in training set
		String[] attributes = scanTrainSet.nextLine().split("\\,");
		int temp1 = attributes.length;	
		//count number of instances in training set
		int temp2 = 1;
		while (scanTrainSet.hasNextLine()){	
			scanTrainSet.nextLine();
			temp2++;   		
		}
		scanTrainSet.close();
		attributeCount = temp1;
		instanceCount = temp2;
		try {
			scanTrainSet = new Scanner(new File(fileName+"_train.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}	
		int dataInstance = 0;
		trainSet = new double[instanceCount][attributeCount];
		// assign data onto a two dimensional array
	   	while (scanTrainSet.hasNextLine()) {
			String[] line = scanTrainSet.nextLine().split("\\,");
			for (int j = 0; j < line.length; j++) {
				trainSet[dataInstance][j] = Double.parseDouble(line[j]);	
			}
			dataInstance++;
	   	}
	   	//put class attributes in a separate array
	   	classAttribute = new double[instanceCount];	
	   	for (int row = 0; row < instanceCount; row++) {
	   		classAttribute[row] = trainSet[row][attributeCount-1];
	   	} 
	   	//form an ArrayList of unique class attributes  	
	   	for (int j = 0; j < classAttribute.length; j++) {
	   	  	boolean duplicate = false;
	   		if(classList.contains(classAttribute[j])) {
	   			duplicate = true;		
	   		}	
	   		if (duplicate == false) {
				classList.add(classAttribute[j]);
			}
	   	}   	
	}
	
	//scan testing set
	Scanner scanTestSet; {
		try {
			scanTestSet = new Scanner(new File(fileName+"_test.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} 	
		//count number of instances in testing set
		int tempTest = 0;
		while (scanTestSet.hasNextLine()){	
			scanTestSet.nextLine();
			tempTest++;   		
		}
		scanTestSet.close();	
		testInstanceCount = tempTest;
		try {
			scanTestSet = new Scanner(new File(fileName+"_test.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}	
		int testInstance = 0;
		testSet = new double[testInstanceCount][attributeCount];
		// assign testing data onto a two dimensional array
		while (scanTestSet.hasNextLine()) {
			String[] line = scanTestSet.nextLine().split("\\,");
			for (int j = 0; j < line.length; j++) {
				testSet[testInstance][j] = Double.parseDouble(line[j]);	
			}
			testInstance++;
		}
		 scanTestSet.close();
	}
	
	//scan attribute type
	Scanner scanAttributeType; {
		try {
			scanAttributeType = new Scanner(new File(fileName+"_type.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} 
		//add attribute type to arrayList
		while (scanAttributeType.hasNext()) {
			attributeType.add(scanAttributeType.next());
		}
	}
	//scan attribute name
	Scanner scanAttributeName; {
		try {
			scanAttributeName = new Scanner(new File(fileName+"_attributes.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} 
		//add attribute names to arrayList
		while (scanAttributeName.hasNext()) {
			attributeName.add(scanAttributeName.next());
		}
	}
	
	/**
	 * Retrieves trainSet
	 * @return trainSet
	 */
	public double[][] getTrainSet() {
		return trainSet;
	}
	
	/**
	 * Retrieves testSet
	 * @return testSet
	 */
	public double[][] getTestSet(){
		return testSet;
	}
	
	/**
	 * Retrieves classList
	 * @return classList
	 */
	public ArrayList<Double> getClassList() {
		return classList;
	}
	
	/**
	 * Retrieves attributeType
	 * @return attributeType
	 */
	public ArrayList<String> getAttributeType(){
		return attributeType;
	}
	
	/**
	 * Retrieves attributeName
	 * @return attributeName
	 */
	public ArrayList<String> getAttributeName(){
		return attributeName;
	}
	
	/**
	 * Retrieves attributeCount
	 * @return attributeCount
	 */
	public int getAttributeCount() {
		return attributeCount;	
	}
	
	/**
	 * Retrieves instanceCount
	 * @return instanceCount
	 */
	public int getInstanceCount() {
		return instanceCount;
	}
	
	/**
	 * Finds and returns lowest value in column
	 * @param col
	 * @return lowest value found
	 */
	public double getLowest(int col) {
		lowest = trainSet[0][col];
		for (int row = 0; row < trainSet.length; row++) {   					
			double col_value = trainSet[row][col];
			if (col_value < lowest) {
				lowest = col_value;
			}
		}	
		return lowest;
	}
	
	/**
	 * Finds and returns highest value in column
	 * @param col
	 * @return highest value found
	 */
	public double getHighest(int col) {
		highest = trainSet[0][col];		
		for (int row = 0; row < trainSet.length; row++) {   					
			double col_value = trainSet[row][col];
			if (col_value > highest) {
				highest = col_value;
			}
		}	
		return highest;
	}
	
	/**
	 * Calculates and returns mean for column
	 * @param col
	 * @return mean 
	 */
	public double getMean(int col) {
		double sum = 0.0;
		double mean;
		for (int row = 0; row < trainSet.length; row++) {   					
			double col_value = trainSet[row][col];
			
			sum = sum + col_value;		
		}	
		mean = sum/trainSet.length;		
		return mean;		
	}

	/**
	 * Calculates and returns variance for column
	 * @param col
	 * @return variance
	 */
	public double getVariance(int col) {
		double mean = getMean(col);
		double variance;
		double temp = 0;
		for(int row = 0; row < trainSet.length; row++) {
			temp = temp + (trainSet[row][col]-mean)*(trainSet[row][col]-mean);
		}
		variance = temp/trainSet.length;
		return variance;		
	}
	
	/**
	 * Calculates and returns standard deviation for column
	 * @param col
	 * @return stdDev
	 */
	public double getStdDev(int col) {
		double stdDev = Math.sqrt(getVariance(col));
		return stdDev;
	}
}

