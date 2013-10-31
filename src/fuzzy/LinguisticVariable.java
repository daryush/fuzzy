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
public class LinguisticVariable {
    
    private String name;
    private HashMap<String, FuzzySet> sets;

    LinguisticVariable(String name) {
        this.name = name;
        this.sets = new HashMap();
    }

    public void addFuzzySet(FuzzySet fuzzySet) {
        this.sets.put(fuzzySet.getName(), fuzzySet);
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public Set<String> getSetsKeys()
    {
        return this.sets.keySet();
    }
    
    public FuzzySet getSetByName(String name)
    {
        return this.sets.get(name);
    }

    boolean hasTerm(String termName)
    {
        return this.sets.containsKey(termName);
    }
}
