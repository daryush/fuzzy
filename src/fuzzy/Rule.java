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
    Sentence outputSentence;
    Double implicationResult;
    
    Rule()
    {
        this.inputSentences = new ArrayList();
    }
    
    void addInputSentence(Sentence sentence) {
        this.inputSentences.add(sentence);
    }

    void setOutputSentence(Sentence sentence) {
        this.outputSentence = sentence;
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
    
    Double getImplicationResult()
    {
        return this.implicationResult;
    }
    
    void setImplicationResult(Double result)
    {
        this.implicationResult = result;
    }

    void makeImplication(ImplicationOperator implicationOperator, Norms norms) {
        this.implicationResult = implicationOperator.implicate(this.getInputSentencesMemberships(), this.outputSentence.getMembershipValue(), norms);
    }

    Sentence getOutputSentence() {
        return this.outputSentence;
    }
}
