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
public class Rule implements Runnable{
    ArrayList<Sentence> inputSentences;
    LinguisticVariable outputVariable;
    ImplicationResults implicationResult;
    protected Double tskParameter;
    protected Double y;
    
    protected ImplicationOperator implicationOperator;
    protected Norms norms;
    protected HashMap<String, Double> userInput;
    
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

    Double getY() {
        return this.y;
    }

    @Override
    public void run() {
        this.makeImplication(this.implicationOperator, this.norms, this.userInput);
    }

    void setImplicationParameters(ImplicationOperator implicationOperator, Norms norms, HashMap<String, Double> userInput) {
        this.implicationOperator = implicationOperator;
        this.norms = norms;
        this.userInput = userInput;
                
    }


}
