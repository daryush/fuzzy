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
public class TSKImplicationOperator implements ImplicationOperator{
    
    @Override
    public ImplicationResults implicate(List<Sentence> precedents, Double ruleTskParameter, Norms norms, HashMap<String, Double> userInput)
    {
        
        Double relationResult;
        relationResult = this.getRelationResult(precedents, norms);
        
        Double yParam = 0.0;
        for (Sentence sentence : precedents) {
            yParam += sentence.getTskParam()*userInput.get(sentence.getName());
        }
        
        ImplicationResults result = new ImplicationResults(relationResult, yParam);
        return result;
//        if (relationResult < consequent) {
//            result = 1.0;
//        } else {
//            result = consequent;
//        }
//                
//        return result;
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
