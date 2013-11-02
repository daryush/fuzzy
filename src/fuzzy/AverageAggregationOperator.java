/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.HashMap;
import java.lang.Math.*;

/**
 *
 * @author daryush
 */
public class AverageAggregationOperator implements AggregationOperator{
    
    private Double min;

    @Override
    public Double aggregateResults(HashMap<String, ImplicationResults> rulesValues) {
        
        Double overSum = 0.0;
        Double underSum = 0.0;
        Double result = 0.0;
        
        for (String key : rulesValues.keySet()) {
            overSum += rulesValues.get(key).getMi()*rulesValues.get(key).getY();
            underSum += rulesValues.get(key).getMi();
        }
        result = overSum/underSum;
        return result;
    }
    
}
