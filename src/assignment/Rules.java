/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

public class Rules {
  private int ruleSize = 10;
    private int cond = 60;
    private Rule[] rules;

    public void createRule() {
        rules = new Rule[ruleSize];

        for (int i = 0; i < ruleSize; i++) {
            rules[i] = new Rule(cond);
        }
        System.out.println(toString());
    }

    public String toString() {
        String returnString = "";
        for (Rule rule : rules) {
            returnString += rule.toString() + "\n";
        }
        return returnString;
    }
}
