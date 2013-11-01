/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 *
 * @author daryush
 */
public class FunctionsFactory {
    
    protected MembershipFunction membershipFunction;
    private Norms norm;
    private AggregationOperator aggregationOperator;
    private ImplicationOperator implicationOperator;
    
    private HashMap<String, Class<MembershipFunction>> registeredMembershipFunctions;
    private HashMap<String, Class<ImplicationOperator>> registeredImplicationOperators;
    private HashMap<String, Class<AggregationOperator>> registeredAggregationOperators;
    private HashMap<String, Class<Norms>> registeredNorms;

    public FunctionsFactory() {
        this.registeredMembershipFunctions = new HashMap();
        this.registeredImplicationOperators = new HashMap();
        this.registeredAggregationOperators = new HashMap();
        this.registeredNorms = new HashMap();
    }
    
    
    
    public void registerMembershipFunction (String productID, Class productClass)
    {
            this.registeredMembershipFunctions.put(productID, productClass);
    }
    
    public void registerAggregationOperator (String productID, Class productClass)
    {
            this.registeredAggregationOperators.put(productID, productClass);
    }
    
    public void registerImplicationOperator (String productID, Class productClass)
    {
            this.registeredImplicationOperators.put(productID, productClass);
    }
    
    public void registerNorm (String productID, Class productClass)
    {
            this.registeredNorms.put(productID, productClass);
    }
    
    
    public MembershipFunction getMembershipFunction(String membershipFunctionName) throws Exception
    {
        Class<MembershipFunction> productClass = (Class<MembershipFunction>)this.registeredMembershipFunctions.get(membershipFunctionName);
        Constructor<MembershipFunction> constructor = productClass.getDeclaredConstructor();
        return constructor.newInstance();        
    }

    public ImplicationOperator getImplicationOperator(String implicationOperatorName) throws Exception {
        Class<ImplicationOperator> productClass = (Class<ImplicationOperator>)this.registeredImplicationOperators.get(implicationOperatorName);
        Constructor<ImplicationOperator> constructor = productClass.getDeclaredConstructor();
        return constructor.newInstance();
    }

    public AggregationOperator getAggregationOperator(String aggregationOperatorName) throws Exception {
        Class<AggregationOperator> productClass = (Class<AggregationOperator>)this.registeredAggregationOperators.get(aggregationOperatorName);
        Constructor<AggregationOperator> constructor = productClass.getDeclaredConstructor();
        return constructor.newInstance();
    }

    public Norms getNorms(String normsName) throws Exception {
        Class<Norms> productClass = (Class<Norms>)this.registeredNorms.get(normsName);
        Constructor<Norms> constructor = productClass.getDeclaredConstructor();
        return constructor.newInstance();
    }
}
