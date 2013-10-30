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
