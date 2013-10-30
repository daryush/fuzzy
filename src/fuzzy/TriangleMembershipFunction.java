/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.util.*;
import java.lang.Math.*;
/**
 *
 * @author daryush
 */
public class TriangleMembershipFunction implements MembershipFunction{
    
    private double a;
    private double b;
    private double c;

    public TriangleMembershipFunction() {
    }
    
    
    public TriangleMembershipFunction(HashMap<String, Double> params) {
        this.a = params.get("a");
        this.b = params.get("b");
        this.c = params.get("c");
    }
    
    
    @Override
    public MembershipFunction getMembershipFunctionInstance(HashMap<String, Double> params)
    {
        return new TriangleMembershipFunction(params);
    }
    
    @Override
    public double calculateMembership(double element)
    {
        double element1 = (element - this.a) / (this.b - this.a);
        double element2 = (this.c - element) / (this.c - this.b);
        Double result = Math.max(Math.min(element1, element2), 0);
        return result;
    }
}
