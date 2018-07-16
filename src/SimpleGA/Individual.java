/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleGA;

import java.util.Arrays;
import java.util.Random;

public class Individual {

    private int geneSize;
    private int[] genes;
    private int fitness;

    private final Random randomNumber = new Random();

    public Individual(int genesSize) {
        this.geneSize = genesSize;
        this.genes = new int[geneSize];
        
        CreateIndiviual();
        this.fitness = 0;
    }

    public Individual() {
        
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

    public int evaluateFitness(){
    this.fitness = 0;
    int decimal = 0, p = 0;
    for (int i = 0; i < genes.length; i++){
    decimal += ((genes[i] % 10) * Math.pow(2, p));
    p++;
    }
    this.fitness = decimal * decimal;
    return fitness;
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
