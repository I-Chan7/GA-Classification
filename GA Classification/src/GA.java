import java.text.DecimalFormat;

/**
 * 
 * @author: Ivan Chan
 * @version 1.0
 * 
 * A rule-based classifier using genetic algorithm.
 * Reads datasets, builds a ruleset using training data and tests model on on testing data
 */


public class GA {
	 
	 static String fileName;
	 static int tournamentSize;
	 static double mutationRate;
	 static double uniformRate;
	 static int maximumRule;
	 static boolean stop;
	 
    public static void main(String[] args) {   	
    	new SelectDataSet();

    }
    	
    /**
     * Executes algorithm
     */
    public static class performAlgorithm extends Thread {
    	
    	public void run() {
	    	fileName = SelectDataSet.getFileName();
	    	tournamentSize = GUI.getTournamentSize();
	    	mutationRate = GUI.getMutationRate();
	    	uniformRate = GUI.getUniformRate();
	    	maximumRule = GUI.getMaximumRule();
	        Population myPop = new Population(GUI.getPopulationSize(), true);
	        int generationCount = 0;
	        DecimalFormat df = new DecimalFormat("0.00");      
	        Individual fittest = null;
	        stop = false;
	        
	        //perform algorithm for the set number of generation
			while (generationCount < GUI.getGenerationCount() && stop == false) {
				double total_fitness = 0;
		        double average;
	            generationCount++;            
	            //calculate average fitness for population
	            for (int i = 0; i < myPop.size(); i++) {
	            	total_fitness = total_fitness + myPop.getIndividual(i).getFitness();
	           	}
	            average = total_fitness/myPop.size();
	           System.out.println("Generation: "+generationCount+"    "+
						"Fittest: "+ df.format(myPop.getFittest().getFitness())+"%    "+
						"Population Average: "+df.format(average)+"%");
	
	            fittest = myPop.getFittest();
	            //Evolve population if condition not satisfied
	            myPop = Algorithm.evolvePopulation(myPop);
	        }			
			printFittest(fittest);				        
	    }
    }
    
    /**
     * Prints fittest individual
     * @param fittest
     */
    public static void printFittest(Individual fittest) {
    	InputData input = new InputData();
    	DecimalFormat df = new DecimalFormat("0.00");
        
		System.out.println("");
		System.out.println("Best: ");            
        	int ruleCount = 1;
		
    		for (int j = 0; j < fittest.size(); j = j+(input.getAttributeCount()-1)*2+1) {
    			System.out.print("Rule "+ruleCount+":  IF ");
        		ruleCount++;
        		int attributeCount = 0;
        		
        		//print all non-null attributes
            	for (int k = j; k < j + (input.getAttributeCount()-1)*2; k = k+2) {
            		if (fittest.getGene(k) != 10000) {
            			//print int as range
            			if (input.getAttributeType().get(attributeCount).equals("int")) {
            				System.out.println(input.getAttributeName().get(attributeCount)+" = "
            						+(int)(fittest.getGene(k))+"-"
            						+(int)(fittest.getGene(k+1))+", ");
            			}
            			//print String as single value
            			else if(input.getAttributeType().get(attributeCount).equals("String")){
            				System.out.print(input.getAttributeName().get(attributeCount)+" = "+
            						+(int)(fittest.getGene(k))+", ");
            			}
            			//print double as range
            			else {
            				System.out.print(input.getAttributeName().get(attributeCount)+" = "
            						+df.format(fittest.getGene(k))+"-"
            						+ df.format(fittest.getGene(k+1))+", ");
            			}
            		}
        			attributeCount++;
        		}       	  
            	//print class attribute
            	System.out.print("THEN "+input.getAttributeName().get(attributeCount)+" = "
            		+(int)fittest.getGene(j+(input.getAttributeCount()-1)*2)+"   "+"\n");
    		}
		//pass fittest individual into TestData to perform classification of test set
		TestData test = new TestData();
		test.testIndividual(fittest);
		
		System.out.println("");
		System.out.println("Classification Accuracy: "+test.getAccuracy()); 
		System.out.println(" _________________________");
    }
    
    /**
     * Retrieves fileName
     * @return fileName
     */
    public static String getFileName() {
    	return fileName;
    }
    
    /**
     * Retrieves tournamentSize
     * @return tournamentSize
     */
    public static int getTournamentSize() {
		return tournamentSize;
	}
	
    /**
     * Retrieves mutationRate
     * @return mutationRate
     */
	public static double getMutationRate() {
		return mutationRate;
	}
	
	/**
	 * Retrieves uniformRate
	 * @return uniformRate
	 */
	public static double getUniformRate() {
		return uniformRate;
	}
	
	/**
	 * Retrieves maximumRule
	 * @return maximumRule
	 */
	public static int getMaximumRule() {
		return maximumRule;
	}
}