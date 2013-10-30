/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;
import java.lang.Math.*;
/**
 *
 * @author daryush
 */
class StandardNorms implements Norms{
    
    @Override
    public double tNorm(double element1, double element2)
    {
        return Math.min(element1, element2);
    }
    
    @Override
    public double sNorm(double element1, double element2)
    {
        return Math.max(element1, element2);
    }
    
    @Override
    public double negation(double element)
    {
        return 1.0 - element;
    }
}
