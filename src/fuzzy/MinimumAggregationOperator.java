/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.ArrayList;
import java.lang.Math.*;

/**
 *
 * @author daryush
 */
public class MinimumAggregationOperator implements AggregationOperator{
    
    private Double min;

    @Override
    public Double aggregateResults(ArrayList<Double> rulesValues) {
        this.min = 0.0;
        for (Double ruleValue : rulesValues) {
            if (ruleValue < min) {
                this.min = ruleValue;
            }
        }
        return this.min;
    }
    
}
