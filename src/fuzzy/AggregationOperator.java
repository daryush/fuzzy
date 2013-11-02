/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.HashMap;

/**
 *
 * @author daryush
 */
interface AggregationOperator {
    Double aggregateResults(HashMap<String, ImplicationResults> rulesValues);
}
