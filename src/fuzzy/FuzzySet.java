/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;
import java.util.*;
/**
 *
 * @author daryush
 */
public class FuzzySet {
    
    private String name;
    private MembershipFunction function;

    FuzzySet(String name)
    {
        this.name = name;
    }
    
    FuzzySet(String[] params, MembershipFunction membershipFunction)
    {
        this.name = params[0];
        HashMap<String, Double> mFunctionParams = new HashMap();
        for (int i = 1; i<params.length; i+=2) {
            mFunctionParams.put(params[i], Double.parseDouble(params[i+1]));
        }
        this.function = membershipFunction.getMembershipFunctionInstance(mFunctionParams);
    }
    
    void setMembershipFunction(MembershipFunction function)
    {
        this.function = function;
    }
    
    public String getName()
    {
        return this.name;
    }

    MembershipFunction getMembershipFunction() {
        return this.function;
    }
}
