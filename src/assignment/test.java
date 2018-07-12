/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class test {

    /**
     * @param args the command line arguments
     */
    public static int[] array;

    public static void main(String[] args) {
        Rules rules = new Rules();
        rules.createRule();

        ArrayList integerOne = new ArrayList();
        ArrayList integerTwo = new ArrayList();

        integerOne.add("10");
        integerOne.add(20);

        integerTwo.add("10");
        integerTwo.add(20);

        boolean equals = integerOne.get(0).equals(integerTwo.get(0));
        boolean containsAll = integerOne.containsAll(integerTwo)
                && integerTwo.containsAll(integerOne);

        System.out.println(equals);
    }

}
