/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.data1;

public class GA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        Population population = new Population();
        

         population.evolveUntilBestFitness(10);
        System.out.println("emp " + population.bestIndividual);
        
    }

}
