/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Rule {

    private int conSize;
    public int[] cond;
    public List listOfGenes = new ArrayList();
    public ArrayList inputDataFromFile = new ArrayList();
    public ArrayList outputDataFromFile = new ArrayList();
    int[] inputDataFromIndividual = new int[5];
    final int NumberOfCharacterBeforeSplit = 6;
    public List<List<Integer>> choppedIndvidualData;
    String data1 = "F:\\year 3\\bio assign\\BioComputationGA\\BioComputationGA\\src\\assignment\\data\\data1.txt";
    private final Random randomNumber = new Random();

    public Rule(int condsSize) {
        this.conSize = condsSize;
        this.cond = new int[condsSize];
        CreateRule();
        choppedIndvidualData = chopped(listOfGenes, NumberOfCharacterBeforeSplit);
        readFile(data1);
        compare();
    }

    private void CreateRule() {
        for (int i = 0; i < cond.length; i++) {
            int gene = randomNumber.nextInt(2);
            cond[i] = gene;
            listOfGenes.add(i, gene);
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

    private void compare() {
        System.out.println("Comparing");
        System.out.println("Input file size = " + inputDataFromFile.size());
        System.out.println("Choopedup dtata size = " + choppedIndvidualData.size());

        for (int m = 0; m < inputDataFromFile.size(); m++) {
            for (int p = 0; p < choppedIndvidualData.size(); p++) {
                populateTheInputDataFromIndvivdual(p);
                String convertedInputDataFromIndvivdual = Arrays.toString(inputDataFromIndividual)
                        .replaceAll("\\s+", "")
                        .replace(",", "") //remove the commas
                        .replace("[", "") //remove the right bracket
                        .replace("]", "") //remove the left bracket
                        .trim();
                System.out.println("Formated string = " + convertedInputDataFromIndvivdual + " and file data = " + inputDataFromFile.get(m));
                if (inputDataFromFile.get(m).equals(convertedInputDataFromIndvivdual)) {
                    System.out.println("Yes");
                    System.out.println("OutputDataFormFile = " + outputDataFromFile.get(m) + " and chooped from ind = " + choppedIndvidualData.get(p).get(NumberOfCharacterBeforeSplit - 1));
                    String outPutDataFromIndvidual = Integer.toString(choppedIndvidualData.get(p).get(NumberOfCharacterBeforeSplit - 1));
                    if (outputDataFromFile.get(m).equals(outPutDataFromIndvidual)) {
                        System.out.println("Match");
                    }
                    break;
                }
            }

        }

    }

    private void populateTheInputDataFromIndvivdual(int index) {
        for (int i = 0; i < 5; i++) {
            Integer input = choppedIndvidualData.get(index).get(i);
            inputDataFromIndividual[i] = input;

        }
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
                String[] parts = line.split("\\s+");
                String part1 = parts[0];
                String part2 = parts[1];
                inputDataFromFile.add(part1);
                outputDataFromFile.add(part2);

            }
            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    @Override
    public String toString() {
        String returnString = "";
        returnString += Arrays.toString(cond) + "\n";
        return returnString;
    }
}
