import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author: Ivan Chan
 * @version 1.0
 * 
 * Generates rules as individuals
 */

public class Individual {
	
   	InputData data = new InputData();
   	double[][] values = data.getTrainSet();
   	ArrayList<Double> classList = data.getClassList();
   	private int attributeCount = data.getAttributeCount();
   	private int instanceCount = data.getInstanceCount();
   	private int ruleLength = (attributeCount-1)*2+1;
    private int defaultGeneLength = ruleLength*classList.size(); 
    Random rand = new Random();
    private double[] genes = new double[defaultGeneLength+(ruleLength*rand.nextInt(GA.getMaximumRule()))];
    private double fitness = 0;
	
    /**
     * Creates a random individual
     */
    public void generateIndividual() { 	  	
    	//loop through entire chromosome (classifier)
    	for (int i = 0; i < genes.length; i = i+ruleLength) {
    		int col = 0;
    		int unusedAttCount;
    		do {
    			unusedAttCount = 0;
    			//loop through a section of the chromosome (individual rule)
	    		for (int j = i; j < i+(ruleLength-1); j = j+2) {  			
	    			double interval1 = 0;
	    			double interval2 = 0;
	    			double highest = data.getHighest(col);
	    			double lowest = data.getLowest(col);
	    			Random random = new Random();
	    			//Generate 2 values to act as an interval for the attribute value, the second value must be larger of the two  
	    			//unless attribute type is String, in which case both values will be equal
		    			if (Math.random() > 0.5) {
		    				//if attribute type is int
		    				if (data.getAttributeType().get(col).toString().equals("int")) {
		    					do {
									interval1 = random.nextInt((int)highest-(int)lowest+1)+(int)lowest; 
									interval2 = random.nextInt((int)highest-(int)lowest+1)+(int)lowest;
								} while (interval1 > interval2 || interval2 - interval1 > data.getStdDev(col));
		    				}
		    				//if attribute type is String
		    				else if (data.getAttributeType().get(col).toString().equals("String")) { 
		    					interval1 = values[random.nextInt(values.length)][col];
		    					interval2 = interval1;
		    				}
		    				//if attribute type is double
		    				else {
				    			do {
									interval1 = lowest + (highest - lowest) * random.nextDouble(); 
									interval2 = lowest + (highest - lowest) * random.nextDouble();
								} while (interval1 > interval2 || interval2 - interval1 > data.getStdDev(col));
		    				}
		    			}
		    			//generate empty values for unused attributes, since arrays cannnot be left as Null, the number 10000
		    			//is used to signify empty values, none of the data sets used contain the value 10000
		    			else {
		    				interval1 = 10000;
		    				interval2 = 10000;
		    				unusedAttCount++;
		    			}
	    			genes[j] = interval1;
	    			genes[j+1] = interval2;	    			
	    			col++;
	    		}
	    		col = 0;
	    	//re-generate chromosome if all attributes are selected as unused
    		}while (unusedAttCount >= attributeCount-1);
        }   
    	//assign class genes
    	int count = 0;
    	for (int i = (attributeCount-1)*2; i < genes.length; i = i+ruleLength) {    			    	
	    		//assign one of each class into chromosome to ensure all classes are present in the
    			//population, then assign randomly afterwards 					
				if (count == classList.size()) {
					Random random = new Random();
					int index = random.nextInt(classList.size());
					genes[i] = classList.get(index);
				}
				else { 
					genes[i] = classList.get(count);
					count++;
				}	
    	}
    }
    
    /**
     * Retrieves gene at index
     * @param index
     * @return gene at index
     */
    public double getGene(int index) {
        return genes[index];
    }
    
    /**
     * Sets gene at index as j
     * @param index
     * @param j
     */
    public void setGene(int index, double j) {
        genes[index] = j;
        fitness = 0;
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
	 * Retrieves length of individual
	 * @return length of individual
	 */
    public int size() {
        return genes.length;
    }

    /**
     * Retrieves fitness score for individual
     * @return fitness score
     */
    public double getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }
    
    /**
     * Sets gene to String
     */
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}