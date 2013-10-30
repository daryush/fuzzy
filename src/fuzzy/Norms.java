/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

/**
 *
 * @author daryush
 */
interface Norms {
     public double tNorm(double element1, double element2);
     public double sNorm(double element1, double element2);
     public double negation(double element);
}
