/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.ArrayList;

/**
 *
 * @author daryush
 */
interface AggregationOperator {
    Double aggregateResults(ArrayList<Double> rulesValues);
}
