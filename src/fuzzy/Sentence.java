/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

/**
 *
 * @author daryush
 */
public class Sentence {
    protected String name;
    protected String term;
    protected Double membershipFunctionValue;
    protected LinguisticVariable linguisticVariable;
    protected Double tskParameter;
    
    public void setTskParameter(Double parameter)
    {
        this.tskParameter = parameter;
    }
    
    public void setParams(LinguisticVariable variable, String term) throws Exception  
    {
        if (!variable.hasTerm(term)) {
            throw new Exception("Term "+term+" is not member of given linguistic variable "+name+".");
        }
        this.name = variable.getName();
        this.term = term;
    }
    
    public Double getMembershipValue() {
        return this.membershipFunctionValue;
    }

    String getName() {
        return this.name;
    }

    String getTerm() {
        return this.term;
    }

    void setMembershipValue(double value) {
        this.membershipFunctionValue = value;
    }

    Double getTskParam() {
        return this.tskParameter;
    }

    
}
