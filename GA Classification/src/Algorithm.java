import java.util.Random;

/**
 * 
 * @author: Ivan Chan
 * @version 1.0
 * 
 * Handles methods for genetic operators
 */

public class Algorithm {

    //GA parameters	
    private static double uniformRate = GA.getUniformRate();
    private static double mutationRate = GA.getMutationRate();
    private static int tournamentSize = GA.getTournamentSize();
    private static boolean elitism = true;
    static InputData data = new InputData();
    static int attributeCount = data.getAttributeCount();
    
    
    /**
     * Evolves the population
     * @param original population
     * @return updated population
     */
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);

        // Always carry over the best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Select individuals and create new individuals using crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1;
            Individual indiv2;
            
            do {
            	indiv1 = tournamentSelection(pop);
            	indiv2 = tournamentSelection(pop);
            //re-select if the two selected chromosomes are identical
            }while (indiv1 == indiv2);

            Individual newIndiv = crossover(indiv1, indiv2);
            
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    /**
     * Performs Crossover
     * @param parent 1
     * @param parent 2
     * @return new individual
     */
    private static Individual crossover(Individual indiv1, Individual indiv2) {
    	Individual newSol;
    	do {
        	newSol = new Individual();
        //length of offspring cannot be longer than length of both parents
        }while(newSol.size() > indiv1.size() && newSol.size() > indiv2.size());
      
        for (int i = 0; i < newSol.size(); i = i + ((attributeCount-1)*2+1)) {
            //Uniform crossover - select which parent to inherit from depending on uniformRate           	        	
        	 if (Math.random() <= uniformRate) {   
        		//if gene position exceeds length of parent 1, automatically inherit value from parent 2
        		if (i >= indiv1.size()) {
            		for (int j = i; j < i+(((attributeCount-1)*2)+1); j++) {          			     	
                		newSol.setGene(j, indiv2.getGene(j));		         		
                	}
            	}
        		else {
	            	for (int j = i; j < i+(((attributeCount-1)*2)+1); j++) { 
	            		newSol.setGene(j, indiv1.getGene(j));            		              		  
	            	}     
        		}
            } 
        	else {  
        		//if gene position exceeds length of parent 2, automatically inherit value from parent 1
        		if (i >= indiv2.size()) {
            		for (int j = i; j < i+(((attributeCount-1)*2)+1); j++) { 
                		newSol.setGene(j, indiv1.getGene(j));            		              		  
                	}     		
            	}
        		else {
	            	for (int j = i; j < i+(((attributeCount-1)*2)+1); j++) {          			     	
	            		newSol.setGene(j, indiv2.getGene(j));		         		
	            	}
        		}
            }    
        	
        }  
        return newSol;      
    }
        
    /**
     * Mutates an individual
     * @param individual
     */
    private static void mutate(Individual indiv) {  	
      
    	for (int i = 0; i < indiv.size(); i = i + ((attributeCount-1)*2+1)) {
    		int col = 0;
    		for (int j = i; j < i+(attributeCount-1)*2; j = j + 2) {   
    			// Each gene is assigned a random number between 0-1 and compared to the set mutation rate    	
    			
    			if (Math.random() <= mutationRate) {
    				//value change operation
    				if (Math.random() >= 0.5 && indiv.getGene(j) != 10000) {
    					boolean validRange;
    					do {
    						validRange = true;
    						Random random = new Random();
    						int newGeneInt = 0;
    						double newGeneDec = 0;  	
    					
    						//if attribute type is integer
    						if(data.getAttributeType().get(col).equals("int")) { 
    							if(Math.random()>0.7) {
	    							//generate random value for lower bound
	    							newGeneInt = random.nextInt((int)data.getHighest(col)-(int)data.getLowest(col)+1)+(int)data.getLowest(col);
	    							//50% to add random to lower bound
	    							if(Math.random()>0.5) {							
	    								indiv.setGene(j, indiv.getGene(j)+newGeneInt);
	    								if (indiv.getGene(j)>data.getHighest(col)) {
	    									indiv.setGene(j, data.getHighest(col));
		        						}
		    						}
		    						//50% to subtract random from lower bound
		    						else {    							
		    							indiv.setGene(j, indiv.getGene(j)-newGeneInt);
		        						if (indiv.getGene(j)<data.getLowest(col)) {
		        							indiv.setGene(j, data.getLowest(col));
		        						}
		    						}
    							}
    							if(Math.random()>0.7) {
		    						//generate random value for higher bound
		    						newGeneInt = random.nextInt((int)data.getHighest(col)-(int)data.getLowest(col)+1)+(int)data.getLowest(col);
		    						//50% to add random to higher bound
		    						if(Math.random()>0.5) {    							
		    	        				indiv.setGene(j+1, indiv.getGene(j+1)+newGeneInt);	
		    	        				if (indiv.getGene(j+1)>data.getHighest(col)) {
		        							indiv.setGene(j+1, data.getHighest(col));
		    	        				}
		    						}
		    						//50% to subtract random from higher bound
		    	        			else { 	        	
		        						indiv.setGene(j+1, indiv.getGene(j+1)-newGeneInt);
		        						if (indiv.getGene(j+1)<data.getLowest(col)) {
		        							indiv.setGene(j+1, data.getLowest(col));
		        						}
		    	        			}   
    							}
	    					}
    						//if attribute type is String
    						else if (data.getAttributeType().get(col).toString().equals("String")) { 
		    					indiv.setGene(j, data.getTrainSet()[random.nextInt(data.getTrainSet().length)][col]);
		    					indiv.setGene(j+1, indiv.getGene(j));
		    				}
	    					//if attribute type is double
	    					else {
	    						if(Math.random()>0.7) {
		    						//generate random value for lower bound
		    						newGeneDec = data.getLowest(col) + (data.getHighest(col) - data.getLowest(col)) * random.nextDouble();
		    						//50% to add random to lower bound
		    						if(Math.random()>0.5) {
		    							indiv.setGene(j, indiv.getGene(j)+newGeneDec);
		    							if (indiv.getGene(j)>data.getHighest(col)) {
		        							indiv.setGene(j, data.getHighest(col));
		        						}
		    						}
		    						//50% to subtract random from lower bound
		    						else {
		    							indiv.setGene(j, indiv.getGene(j)-newGeneDec);
		    							if (indiv.getGene(j)<data.getLowest(col)) {
		        							indiv.setGene(j, data.getLowest(col));
		        						}
		    						}
	    						}
		    					if(Math.random()>0.7) {
		    						//generate random value for higher bound
		    						newGeneDec = data.getLowest(col) + (data.getHighest(col) - data.getLowest(col)) * random.nextDouble();
		    						//50% to add random to higher bound
		    						if(Math.random()>0.5) {   						
				        				indiv.setGene(j+1, indiv.getGene(j+1)+newGeneDec);
				        				if (indiv.getGene(j+1)>data.getHighest(col)) {
		        							indiv.setGene(j+1, data.getHighest(col));
		        						}
		    						}
		    						//50% to subtract random from higher bound
		    						else {
		    							indiv.setGene(j+1, indiv.getGene(j+1)-newGeneDec);
		    							if (indiv.getGene(j+1)<data.getLowest(col)) {
		        							indiv.setGene(j+1, data.getLowest(col));
		        						}
		    						}
		    					}
	    					}
	    					//re-generate value if lower bound is larger than higher bound
	        				if (indiv.getGene(j) > indiv.getGene(j+1)) {
	        					validRange = false;
	        				}
	    				}while(validRange == false);    	
    				}
    				//add or remove attribute
    				else {
    					//remove attribute operation
    					if (indiv.getGene(j) != 10000) {
    						int emptyCount = 0;
    						for (int k = i; k < i+ ((attributeCount-1)*2); k = k+2) {
    							if (indiv.getGene(k) == 10000) {
    								emptyCount++;
    							}
    						}   			  		    
    						//only perform the operation if there are at least 2 attributes in use
    						if (emptyCount < attributeCount-2) {  
    							indiv.setGene(j, 10000);
    							indiv.setGene(j+1, 10000);
    						}
    					}   
    					//add attribute operation
    					else if (indiv.getGene(j) == 10000) {
    						double interval1;
    						double interval2;
    						Random random = new Random();
    						//if attribute type is integer
							if (data.getAttributeType().get(col).toString().equals("int")) {
								//generate random interval
		    					do {
									interval1 = random .nextInt((int)data.getHighest(col)-(int)data.getLowest(col))+(int)data.getLowest(col); 
									interval2 = random.nextInt((int)data.getHighest(col)-(int)data.getLowest(col))+(int)data.getLowest(col);
								} while (interval1 > interval2 || interval2 - interval1 > data.getStdDev(col));
			    				indiv.setGene(j, interval1);
			    				indiv.setGene(j+1, interval2);
		    				}
							//if attribute type is String
		    				if (data.getAttributeType().get(col).toString().equals("String")) { 
		    					//replace gene with value in the same column and random row from training data
		    					indiv.setGene(j, data.getTrainSet()[random.nextInt(data.getTrainSet().length)][col]);
		    					indiv.setGene(j+1, indiv.getGene(j));
		    				}
		    				//if attribute type is double
		    				else {
		    					//generate random interval
				    			do {
									interval1 = data.getLowest(col) + (data.getHighest(col) - data.getLowest(col)) * random.nextDouble(); 
									interval2 = data.getLowest(col) + (data.getHighest(col) - data.getLowest(col)) * random.nextDouble();
								} while (interval1 > interval2 || interval2 - interval1 > data.getStdDev(col));
			    				indiv.setGene(j, interval1);
			    				indiv.setGene(j+1, interval2);
		    				} 	
    					}
    				}
    			}  
    			col++;
    		}
        }  	
    }

    /**
     * Selects individual for crossover
     * @param population
     * @return selected individual
     */
    private static Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest from tournament population
        Individual fittest = tournament.getFittest();
        return fittest;
    }
}