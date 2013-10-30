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
interface MembershipFunction {
        public MembershipFunction getMembershipFunctionInstance(HashMap<String, Double> params);
        public double calculateMembership(double element);
}
