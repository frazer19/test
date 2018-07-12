package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Population {

    private Individual[] individuals;
    public int ruleFittness;
    private int populationSize = 10;
    private int genesSize = 6;
    private int meanFitness;
    private int untilBestFitness;
    private static final double mutationRate = 0.006;
    private static final Random randomNumber = new Random();
    public ArrayList listOfBestFitness = new ArrayList();
    public ArrayList listOfMeanFitness = new ArrayList();
    private Individual bestIndividual;
    private List arraylist = new ArrayList();

    //Rule
    public ArrayList dataFromFile = new ArrayList();
    public List<List<Integer>> choppedFileData;
    final int NumberOfCharacterBeforeSplit = 6;
    String data1 = "F:\\year 3\\bio assign\\BioComputationGA\\BioComputationGA\\src\\assignment\\data\\data1.txt";

    public void createInitialPopulation() {
        readFile(data1);
        choppedFileData = chopped(dataFromFile, NumberOfCharacterBeforeSplit);
        //System.out.println("Chooped file data = " + choppedFileData.toString());
        individuals = new Individual[populationSize];

        for (int i = 0; i < populationSize; i++) {
            individuals[i] = new Individual(genesSize);
        }

        // evulate fitness
        EvaluatePop();
        System.out.println("Initial population");
        System.out.println(toString());
        listOfBestFitness.add(getFittest());
        listOfMeanFitness.add(meanFitness);

    }

    public void evolveNGenerations(int generations) {
        for (int i = 0; i < generations; i++) {
            System.out.println("Generation " + i);
            evolve();
            EvaluatePop();
        }
    }

    public void evolveUntilBestFitness(int bestFitness) {
        this.untilBestFitness = bestFitness;
        int generation = 0;
        while (getFittest() < bestFitness) {
            System.out.println("Generation " + generation);
            evolve();
            EvaluatePop();
            generation++;

        }
    }

    private void evolve() {
        getFittestIndividual();
        selection();
        crossover();
        mutation();
        // fittest individual already stored in the attribute bestIndividual
        // replace the worst individual with the best individual from parent pop
        maintainBestFitness();
    }

    public void EvaluatePop() {
        // calcuate fitnes of all individuals
        for (Individual individual : individuals) {
            individual.setFitness(0);
            individual.setFitness(ruleFittness);
            for (int i = 0; i < individual.getGeneSize(); i++) {
                if (individual.getGene(i) == 1) {
                    individual.setFitness(individual.getFitness() + 1);
                }
            }
        }

        //this.totalFitnessOfThePopulation();
        this.meanFitnessOfThePopulation();
        System.out.println(toString());
        listOfBestFitness.add(getFittest());
        listOfMeanFitness.add(meanFitness);
    }

    private void selection() {
        int parent1;
        int parent2;

        Individual[] offSpring = new Individual[populationSize];

        for (int i = 0; i < populationSize; i++) {
            parent1 = randomNumber.nextInt(populationSize);
            parent2 = randomNumber.nextInt(populationSize);
            if (individuals[parent1].getFitness() >= individuals[parent2].getFitness()) {
                offSpring[i] = individuals[parent1];
            } else {
                offSpring[i] = individuals[parent2];
            }
        }
        individuals = offSpring;
    }

    public void setPopulationSize(int size) {
        this.populationSize = size;
    }

    private void meanFitnessOfThePopulation() {
        int totalFitness = 0;
        for (Individual individual : individuals) {
            totalFitness += individual.getFitness();
        }
        this.meanFitness = totalFitness / populationSize;
    }

    private Individual getIndividual(int index) {
        return individuals[index];
    }

    private int getFittest() {
        Individual fittest = individuals[0];
        for (int i = 0; i < populationSize; i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest.getFitness();
    }

    private Individual getFittestIndividual() {
        Individual fittest = individuals[0];
        for (int i = 0; i < populationSize; i++) {
            if (fittest.getFitness() < getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
                if (bestIndividual == null) {
                    bestIndividual = fittest;
                } else if (bestIndividual.getFitness() < fittest.getFitness()) {
                    bestIndividual = fittest;
                }
            }
        }
        return fittest;
    }

    private void maintainBestFitness() {
        // keep the best individual from parent generation.
        // and replace the worst individual from the offspring with this individual
        int worstIndex = 0;
        Individual worstIndividual = individuals[0];

        for (int i = 0; i < populationSize; i++) {
            if (individuals[i].getFitness() < worstIndividual.getFitness()) {
                worstIndividual = individuals[i];
                worstIndex = i;
            }
        }

        // replace the worst fit individual with best fit
        individuals[worstIndex] = this.bestIndividual;
    }

    private void crossover() {
        Individual parent1;
        Individual parent2;
        Individual offspring1;
        Individual offspring2;
        int crossPoint;
        int j;

        for (int i = 0; i < populationSize; i = i + 2) {
            // get two subsequent parents
            parent1 = individuals[i];
            parent2 = individuals[i + 1];
            // generate a random gene as the crossover point
            crossPoint = randomNumber.nextInt(genesSize);
            // create two new individuals for offspring 1 and 2
            offspring1 = new Individual(genesSize);
            offspring2 = new Individual(genesSize);

            // copy the first half of parent 1 to ofspring 1 and first half of
            // parent2 to offspring2
            for (j = 0; j < crossPoint; j++) {
                offspring1.setGene(j, parent1.getGene(j));
                offspring2.setGene(j, parent2.getGene(j));
            }
            // copy the second half of parent 1 to ofspring 1 and second half of
            // parent2 to offspring2
            for (; j < genesSize; j++) {
                offspring1.setGene(j, parent2.getGene(j));
                offspring2.setGene(j, parent1.getGene(j));
            }
            // replace parents with offspring
            individuals[i] = offspring1;
            individuals[i + 1] = offspring2;
        }
    }

    private void mutation() {
        double randomDouble;
        // for each individual
        for (Individual individual : individuals) {
            // loop through individual's genes
            for (int i = 0; i < genesSize; i++) {
                // generate a random double between 0 and 1
                randomDouble = randomNumber.nextDouble() * 1.0;
                // if randomDouble is less than mutationRate, mutate this gene
                if (randomDouble < mutationRate) {
                    // flip this gene
                    individual.setGene(i, 1 - individual.getGene(i));
                }
            }
        }
    }

    // TEMP for testing
    public void crossoverSingle(Individual parent1, Individual parent2) {
        System.out.println("parent 1 " + parent1.toString());
        System.out.println("parent 2 " + parent2.toString());
        int crossPoint = 4;
        System.out.println("crossover point " + crossPoint);

        Individual offspring1 = new Individual(genesSize);
        Individual offspring2 = new Individual(genesSize);
        int j;
        for (j = 0; j < crossPoint; j++) {
            offspring1.setGene(j, parent1.getGene(j));
            offspring2.setGene(j, parent2.getGene(j));
        }
        for (; j < genesSize; j++) {
            offspring1.setGene(j, parent2.getGene(j));
            offspring2.setGene(j, parent1.getGene(j));
        }
        System.out.println("child 1 " + offspring1.toString());
        System.out.println("child 2 " + offspring2.toString());
    }

    private void readFile(String fileName) {
        // This will reference one line at a time
        String line = null;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                String parts = line.replaceAll("\\s+", "");
                String part1 = parts;
                for (int i = 0; i < part1.length(); i++) {
                    char getACharFromTheString = part1.charAt(i);
                    int converetCharToInt = Character.getNumericValue(getACharFromTheString);
                    dataFromFile.add(converetCharToInt);
                }

            }
            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    private <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

    @Override
    public String toString() {
        String returnString = "";
        for (Individual individual : individuals) {
            returnString += individual.toString() + "\n";

        }
        returnString += "Fittest = " + getFittest() + "\n";
        returnString += "Mean fitness = " + meanFitness + "\n";
        returnString += "-----------------------------------";
        return returnString;
    }

}
