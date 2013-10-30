/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

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
    
    public MembershipFunction getMembershipFunction(String membershipFunctionName) throws Exception
    {
        switch(membershipFunctionName) {
            case "triangle":
                this.membershipFunction = new TriangleMembershipFunction();
                break;
            default:
                throw new Exception("There is no membership function named "+membershipFunctionName+".");
        }
        return this.membershipFunction;
    }

    public ImplicationOperator getImplicationOperator(String implicationOperatorName) throws Exception {
        switch(implicationOperatorName) {
            case "fodor":
                this.implicationOperator = new FodorImplicationOperator();
                break;
            default:
                throw new Exception("There is no implication operator named "+implicationOperatorName+".");
        }
        return this.implicationOperator;
    }

    public AggregationOperator getAggregationOperator(String aggregationOperatorName) throws Exception {
        switch(aggregationOperatorName) {
            case "minimum":
                this.aggregationOperator = new MinimumAggregationOperator();
                break;
            default:
                throw new Exception("There is no aggregation operator named "+aggregationOperatorName+".");
        }
        return this.aggregationOperator;
    }

    public Norms getNorms(String normsName) throws Exception {
        switch(normsName) {
            case "standard":
                this.norm = new StandardNorms();
                break;
            default:
                throw new Exception("There is no norms named "+normsName+".");
        }
        return this.norm;
    }
}
