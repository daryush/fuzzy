/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

/**
 *
 * @author daryush
 */
class ImplicationResults {
    protected Double mi;
    protected Double y;
    
    
    
    ImplicationResults(Double mi, Double y)
    {
        this.mi = mi;
        this.y = y;
    }

    ImplicationResults(ImplicationResults implicationResult) {
        this.mi = implicationResult.getMi();
        this.y = implicationResult.getY();
    }
    
    Double getMi()
    {
        return this.mi;
    }
    
    Double getY()
    {
        return this.y;
    }

    void setMi(Double consequent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setY(double d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
