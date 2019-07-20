/**
 * 
 * @author: Ivan Chan
 * @version 1.0
 * 
 * Performs classification on testing data
 */

public class TestData {
	double accuracy = 0;

	/**
	 * Performs classification on testing set
	 * @param fittest ruleset generated
	 */
	public void testIndividual(Individual fittest) {
		InputData data = new InputData();
		double[][] testSet = data.getTestSet();
		int attributeCount = fittest.getAttributeCount();
		int ruleLength = ((attributeCount-1)*2)+1;
    	int classified = 0;
    	
    	//for each instance in test set
    	for (int row = 0; row < testSet.length; row++) {
    		int attMatch = 0; 
        	int col= 0;	     
        	int classMatch = 0;
        	int classNotMatch = 0;

        	//for each rule in individual 
        	for (int j = 0; j < fittest.size(); j = j+ruleLength) {
        		col = 0;
        		int k = j;
        		//go through each attribute in rule
        		while (k < j + (ruleLength-1)){

        		//compare attribute values 
	        		if(testSet[row][col] >= fittest.getGene(k) && testSet[row][col] <= fittest.getGene(k+1) 
	        			|| fittest.getGene(k) == 10000) {
		        		attMatch++;        				
	        		}       
	        		//compare class gene if all other attributes match
	        		if(attMatch == attributeCount -1) { 
		        		if (fittest.getGene(j+(ruleLength-1)) == testSet[row][attributeCount-1]){
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
        	if (classMatch >= 1 && classNotMatch == 0) {
        		classified++;
        	}        	
    	}	
    	//accuracy of the classifier is measured by the percentage of instances correctly classified
    	accuracy = (((double)classified/(double)testSet.length)*100);  
	}
	
	/**
	 * Retrieves classification accuracy
	 * @return accuracy
	 */
	public double getAccuracy() {
		return accuracy;		
	}
	
	
}
