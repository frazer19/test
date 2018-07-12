/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.data1;

import java.util.ArrayList;
import java.util.Random;

public class Population {

    private Individual[] individuals;
    private int populationSize = 10;
    private int genesSize = 10;
    private int totalFitness;
    private int meanFitness;
    private int untilBestFitness;
    private static final double mutationRate = 0.01;
    private static final Random randomNumber = new Random();
    public ArrayList listOfBestFitness = new ArrayList();
    public ArrayList listOfMeanFitness = new ArrayList();
    public Individual bestIndividual;

    public void createInitialPopulation() {
        individuals = new Individual[populationSize];

        for (int i = 0; i < populationSize; i++) {
            individuals[i] = new Individual(genesSize);
        }

        this.totalFitnessOfThePopulation();
        this.meanFitnessOfThePopulation();
        System.out.println("Initial population");
        System.out.println(toString());
        listOfBestFitness.add(getFittest());
        listOfMeanFitness.add(meanFitness);

    }

    public void evolveNGenerations(int generations) {
        for (int i = 0; i < generations; i++) {
            System.out.println("Generation " + i);
            evolve();
        }
    }

    public void evolveUntilBestFitness(int bestFitness) {
        this.untilBestFitness = bestFitness;
        int generation = 0;
        while (getFittest() < bestFitness) {
            System.out.println("Generation " + generation);
            evolve();
            generation++;
        }
    }

    public void evolve() {
        getFittestIndividual();
        selection();
        crossover();
        mutation();
        // fittest individual already stored in the attribute bestIndividual
        // replace the worst individual with the best individual from parent pop
        maintainBestFitness();

        this.totalFitnessOfThePopulation();
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

    public void setIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }

    public void setPopulationSize(int size) {
        this.populationSize = size;
    }

    private int totalFitnessOfThePopulation() {
        this.totalFitness = 0;
        for (int i = 0; i < populationSize; i++) {
            this.totalFitness += individuals[i].evaluateFitness();
        }
        return this.totalFitness;
    }

    public int meanFitnessOfThePopulation() {
        this.meanFitness = 0;
        this.meanFitness = totalFitness / populationSize;
        return meanFitness;
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public int getFittest() {
        Individual fittest = individuals[0];
        for (int i = 0; i < fittest.getGeneSize(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest.getFitness();
    }

    public Individual getFittestIndividual() {
        Individual fittest = individuals[0];
        for (int i = 0; i < fittest.getGeneSize(); i++) {
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

    public void maintainBestFitness(){
        // keep the best individual from parent generation.
        // and replace the worst individual from the offspring with this individual
        int worstIndex = 0;
        Individual worstIndividual = individuals[0];
     
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].getFitness() < worstIndividual.getFitness()){
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

    @Override
    public String toString() {
        String returnString = "";
        for (Individual individual : individuals) {
            returnString += individual.toString() + "\n";
        }
        returnString += "Fittest = " + getFittest() + "\n";
        //returnString += "Fittest = " + getFittestIndividual() + "\n";
        returnString += "Fittest in the whole gemneration = " + bestIndividual + "\n";
        returnString += "Total Fitness = " + totalFitness + "\n";
        returnString += "Mean fitness = " + meanFitness + "\n";
        returnString += "-----------------------------------";
        return returnString;
    }
}
