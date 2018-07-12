package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GA {

    static ArrayList inputData = new ArrayList();
    static ArrayList outData = new ArrayList();
    
    private static void readFile(String fileName) {
        // This will reference one line at a time
        String line = null;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                String[] parts = line.split("\\s+");
                String part1 = parts[0];
                String part2 = parts[1];
                inputData.add(part1);
                outData.add(part2);
            }
            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    public static void main(String[] args) {
        Population population = new Population();
        population.createInitialPopulation();
        //population.evolveNGenerations(50);
        population.evolveUntilBestFitness(10);
        
    }

}
