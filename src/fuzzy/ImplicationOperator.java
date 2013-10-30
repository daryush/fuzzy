/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.List;

/**
 *
 * @author daryush
 */
interface ImplicationOperator {
    public Double implicate(List<Double> precedents, Double consequent, Norms norms);
    
}
