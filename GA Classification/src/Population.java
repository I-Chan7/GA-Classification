/**
 * 
 * @author: Ivan Chan
 * @version 1.0
 * 
 * Create a population, allows retrieval of individuals from the population
 */

public class Population {

    Individual[] individuals;

    /**
     * Creates a population
     * @param populationSize
     * @param initialise
     */
    public Population(int populationSize, boolean initialise) {
        individuals = new Individual[populationSize];
        // Initialise population
        if (initialise) {
            for (int i = 0; i < size(); i++) {
                Individual newIndividual = new Individual();
                newIndividual.generateIndividual();
                saveIndividual(i, newIndividual);
            }
        }
    }

    /**
     * Retrieves individual at index
     * @param index
     * @return individual at index
     */
    public Individual getIndividual(int index) {
        return individuals[index];
    }
    
    /**
     * Retrieves fittest individual in population
     * @return fittest individual
     */
    public Individual getFittest() {
        Individual fittest = individuals[0];
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /**
     * Retrieves length of individual
     * @return length of individual
     */
    public int size() {
        return individuals.length;
    }

    /**
     * Saves individual indiv to index
     * @param index
     * @param indiv
     */
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}