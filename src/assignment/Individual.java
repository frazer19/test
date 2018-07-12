package assignment;

import java.util.Arrays;
import java.util.Random;

public class Individual {

    private final int geneSize;
    private int[] genes;
    private int fitness;
    private Population p = new Population();
    private final Random randomNumber = new Random();

    public Individual(int genesSize) {
        this.geneSize = genesSize;
        this.genes = new int[geneSize];

        CreateIndiviual();
        this.fitness = 0;
    }

    public int getGeneSize() {
        return this.geneSize;
    }

    private void CreateIndiviual() {
        for (int i = 0; i < genes.length; i++) {
            int gene = randomNumber.nextInt(2);
            genes[i] = gene;
        }
    }
    
    public int getFitness() {
        return this.fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, int value) {
        genes[index] = value;
        fitness = 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(genes) + " = " + getFitness();
    }

    // TEMP
    public void setGenes(int[] genes) {
        this.genes = genes;
    }

}
