/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.*;

/**
 *
 * @author daryush
 */
public class Rule {
    ArrayList<Sentence> inputSentences;
    LinguisticVariable outputVariable;
    ImplicationResults implicationResult;
    protected Double tskParameter;
    protected Double y;
    
    public void setTskParameter(Double parameter)
    {
        this.tskParameter = parameter;
    }
    
    Rule()
    {
        this.inputSentences = new ArrayList();
    }
    
    void addInputSentence(Sentence sentence) {
        this.inputSentences.add(sentence);
    }

    void setOutputVariable(LinguisticVariable variable) {
        this.outputVariable = variable;
    }
    
    List<Double> getInputSentencesMemberships()
    {
        ArrayList<Double> memberships = new ArrayList();
        
        for(Sentence sentence : this.inputSentences)
        {
            memberships.add(sentence.getMembershipValue());
        }
        
        return memberships;
    }
    
    ImplicationResults getImplicationResult()
    {
        return this.implicationResult;
    }
    
    void setImplicationResult(ImplicationResults result)
    {
        this.implicationResult = result;
    }

    void makeImplication(ImplicationOperator implicationOperator, Norms norms, HashMap<String, Double> userInput) {
        this.implicationResult = implicationOperator.implicate(this.inputSentences, this.tskParameter, norms, userInput);
    }

    LinguisticVariable getOutputVariable() {
        return this.outputVariable;
    }

    void calculateY(HashMap<String, Double>userInput) {
        
    }

    Double getY() {
        return this.y;
    }


}
