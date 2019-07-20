/**
 * 
 * @author: Ivan Chan
 * @version 1.0
 * 
 * Calculate fitness value for individuals
 */

public class FitnessCalc {

	/**
	 * Calculates fitness of individual 
	 * @param individual
	 * @return fitness score for individual
	 */
    static double getFitness(Individual individual) {
     	InputData data = new InputData();
    	double[][] trainSet = data.getTrainSet();
    	int attributeCount = individual.getAttributeCount();
    	int ruleLength = ((attributeCount-1)*2)+1;
    	int classified = 0;
    	double fitness = 0;
    	//for each instance in training set
    	for (int row = 0; row < trainSet.length; row++) {
    		int attMatch = 0; 
    		int classMatch = 0;
    		int classNotMatch = 0;
        	int col= 0;	       	 

        	//for each rule in the chromosome
        	for (int j = 0; j < individual.size(); j = j + ruleLength) {
        		col = 0;
        		int k = j;
        		//go through each attribute in rule
        		while (k < j + (ruleLength-1)){

        		//compare attribute values 
	        		if(trainSet[row][col] >= individual.getGene(k) && trainSet[row][col] <= individual.getGene(k+1) 
	        			|| individual.getGene(k) == 10000) {
		        		attMatch++;        				
	        		}       
	        		//compare class gene if all other attributes match
	        		if(attMatch == attributeCount -1) { 
		        		if (individual.getGene(j+(ruleLength-1)) == trainSet[row][attributeCount-1]){
		        			classMatch++;		              
		        		}
		        		else {
		        			classNotMatch++;
		        		}
		        	}
	        		col++;
	        		k = k+2;	
        		}     		
	        	attMatch = 0;        			        		
	        }  
        	//instance is considered correctly classified only when there is a match, and no mismatch        	   
        	if (classMatch >=1 && classNotMatch == 0) {
        		classified++;
        	}
    	}		
    	//fitness is the percentage of correctly identified instances
    	fitness = (((double)classified/(double)trainSet.length)*100);
    	return fitness;
    }
}