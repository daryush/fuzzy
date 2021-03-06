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
public class FodorImplicationOperator implements ImplicationOperator{
    
    @Override
    public ImplicationResults implicate(List<Sentence> precedents, Double ruleTskParameter, Norms norms, HashMap<String, Double> userInput)
    {
        
//        Double relationResult;
//        relationResult = this.getRelationResult(precedents, norms);
//        Double miResult;
//        
//        if (relationResult < consequent) {
//            miResult = 1.0;
//        } else {
//            miResult = consequent;
//        }
        ImplicationResults result = new ImplicationResults(0.0, 0.0);
        return result;
    }
    
    private Double getRelationResult(List<Sentence> precedents, Norms norms)
    {
        Double relationResult;
        if (precedents.size()>1) {
            List<Sentence> loopPrecedents;
            loopPrecedents = precedents.subList(1, precedents.size()-1);
            relationResult = precedents.get(0).getMembershipValue();
            for (Sentence element : precedents) {
                relationResult = norms.tNorm(relationResult, element.getMembershipValue());
            }
        } else {
            relationResult = precedents.get(0).getMembershipValue();
        }
        return relationResult;
    }
}
