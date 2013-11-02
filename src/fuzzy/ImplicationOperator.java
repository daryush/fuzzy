/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author daryush
 */
interface ImplicationOperator {
    public ImplicationResults implicate(List<Sentence> precedents, Double ruleTskParameter, Norms norms, HashMap<String, Double> userInput);
    
}
