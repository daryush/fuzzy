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
    public Double implicate(List<Double> precedents, Double consequent, Norms norms)
    {
        
        Double relationResult;
        relationResult = this.getRelationResult(precedents, norms);
        Double result;
        
        if (relationResult < consequent) {
            result = 1.0;
        } else {
            result = consequent;
        }
                
        return result;
    }
    
    private Double getRelationResult(List<Double> precedents, Norms norms)
    {
        Double relationResult;
        if (precedents.size()>1) {
            List<Double> loopPrecedents;
            loopPrecedents = precedents.subList(1, precedents.size()-1);
            relationResult = precedents.get(0);
            for (Double element : precedents) {
                relationResult = norms.tNorm(relationResult, element);
            }
        } else {
            relationResult = precedents.get(0);
        }
        return relationResult;
    }
}
