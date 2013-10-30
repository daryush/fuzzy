/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daryush
 */
public class FuzzySystem {
    
    private MembershipFunction membershipFunction;
    private ImplicationOperator implicationOperator;
    private AggregationOperator aggregationOperator;
    private Norms norms;
    
    private ArrayList<Sentence> sentences;
    private ArrayList<Rule> rules;
    private HashMap<String, Double> userInput;
    private HashMap<String, Double> aggregationResult;
    private HashMap<String, LinguisticVariable> outputLinguisticVariables;
    private HashMap<String, LinguisticVariable> inputLinguisticVariables;
    private final BufferedReader reader;
    
    FuzzySystem()
    {
        this.aggregationResult = new HashMap();
        this.inputLinguisticVariables = new HashMap();
        this.outputLinguisticVariables = new HashMap();
        this.sentences = new ArrayList();
        this.rules = new ArrayList();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void run() 
    {
        this.getProgrammingConfigurationFromUser();
        this.createSystem();
        this.createRules();
        this.getSystemInput();
        this.calculateSentencesMembership();
        this.makeImplications();
        this.aggregateResults();
        this.showResult();
    }
    
    public void getProgrammingConfigurationFromUser(){
        
      String mFunctonName = null;
      String implicationOperatorName = null;
      String aggregationOperatorName = null;
      String normsName = null;

      try {
        System.out.print("Enter mFunction type name: ");
        mFunctonName = this.reader.readLine();
        System.out.print("Enter implication operator type name: ");
        implicationOperatorName = this.reader.readLine();
        System.out.print("Enter aggregation operator type name: ");
        aggregationOperatorName = this.reader.readLine();
        System.out.print("Enter norms type name: ");
        aggregationOperatorName = this.reader.readLine();
      } catch (IOException ioe) {
         System.out.println("IO error trying to read your name!");
         System.exit(1);
      }
      this.initialize(mFunctonName, implicationOperatorName, aggregationOperatorName, normsName);
    }
    
    public void initialize(
            String membershipFunctionName,
            String implicationOperatorName,
            String aggregationOperatorName,
            String normsName
            )
    {
        FunctionsFactory fFactory = new FunctionsFactory();
        try {
            this.membershipFunction = fFactory.getMembershipFunction(membershipFunctionName);
            this.implicationOperator = fFactory.getImplicationOperator(implicationOperatorName);
            this.aggregationOperator = fFactory.getAggregationOperator(aggregationOperatorName);
            this.norms = fFactory.getNorms(normsName);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        
    }
    
    public void createSystem()
    {
        //stworzenie zmienny lingwistycznych i termsów (z funkcjamiprzynaleznosci)
        String name = "temperatura";
        LinguisticVariable temp = new LinguisticVariable(name);
        
        HashMap<String, Double> niskaParams = new HashMap();
        HashMap<String, Double> sredniaParams = new HashMap();
        HashMap<String, Double> wysokaParams = new HashMap();
        HashMap<String, Double> piecMocnoParams = new HashMap();
        HashMap<String, Double> piecSrednioParams = new HashMap();
        HashMap<String, Double> piecLekkoParams = new HashMap();
        
        niskaParams.put("a", 0.0);
        niskaParams.put("b", 5.0);
        niskaParams.put("c", 10.0);
        
        sredniaParams.put("a", 7.0);
        sredniaParams.put("b", 12.0);
        sredniaParams.put("c", 17.0);
        
        wysokaParams.put("a", 15.0);
        wysokaParams.put("b", 20.0);
        wysokaParams.put("c", 25.0);
        
        piecMocnoParams.put("a", 70.0);
        piecMocnoParams.put("b", 80.0);
        piecMocnoParams.put("c", 85.0);
        
        piecSrednioParams.put("a", 60.0);
        piecSrednioParams.put("b", 65.0);
        piecSrednioParams.put("c", 75.0);
        
        piecLekkoParams.put("a", 50.0);
        piecLekkoParams.put("b", 55.0);
        piecLekkoParams.put("c", 65.0);
        
        
        FuzzySet niska = new FuzzySet("niska");
        niska.setMembershipFunction(this.membershipFunction.getMembershipFunctionInstance(niskaParams));
        FuzzySet srednia = new FuzzySet("srednia");
        srednia.setMembershipFunction(this.membershipFunction.getMembershipFunctionInstance(sredniaParams));
        FuzzySet wysoka = new FuzzySet("wysoka");
        wysoka.setMembershipFunction(this.membershipFunction.getMembershipFunctionInstance(wysokaParams));
        temp.addFuzzySet(niska);
        temp.addFuzzySet(srednia);
        temp.addFuzzySet(wysoka);
        this.inputLinguisticVariables.put(temp.getName(), temp);
        
        String piecName = "piec";
        LinguisticVariable piec = new LinguisticVariable(piecName);
        FuzzySet piecMocno = new FuzzySet("mocno");
        piecMocno.setMembershipFunction(this.membershipFunction.getMembershipFunctionInstance(piecMocnoParams));
        FuzzySet piecSrednio = new FuzzySet("srednio");
        piecSrednio.setMembershipFunction(this.membershipFunction.getMembershipFunctionInstance(piecSrednioParams));
        FuzzySet piecLekko = new FuzzySet("lekko");
        piecLekko.setMembershipFunction(this.membershipFunction.getMembershipFunctionInstance(piecLekkoParams));
        piec.addFuzzySet(piecMocno);
        piec.addFuzzySet(piecSrednio);
        piec.addFuzzySet(piecLekko);
        this.outputLinguisticVariables.put(piec.getName(), piec);
                
    }
    
    public void createRules()
    {
        Sentence zimno = new Sentence(), letnio = new Sentence(), cieplo = new Sentence(), grzejLekko = new Sentence(), grzejSrednio = new Sentence(), grzejMocno = new Sentence();
        try {
            zimno.setParams(this.inputLinguisticVariables.get("temperatura"), "niska");
            letnio.setParams(this.inputLinguisticVariables.get("temperatura"), "srednia");
            cieplo.setParams(this.inputLinguisticVariables.get("temperatura"), "wysoka");

            grzejLekko.setParams(this.outputLinguisticVariables.get("piec"), "lekko");
            grzejSrednio.setParams(this.outputLinguisticVariables.get("piec"), "srednio");
            grzejMocno.setParams(this.outputLinguisticVariables.get("piec"), "mocno");
        } catch(Exception e) {
            System.out.printf("%s", e.getMessage());
        }
        
        this.sentences.add(zimno);
        this.sentences.add(letnio);
        this.sentences.add(cieplo);
        this.sentences.add(grzejLekko);
        this.sentences.add(grzejSrednio);
        this.sentences.add(grzejMocno);
        Rule rule1 = new Rule();
        rule1.addInputSentence(zimno);
        rule1.setOutputSentence(grzejMocno);
        Rule rule2 = new Rule();
        rule2.addInputSentence(letnio);
        rule2.setOutputSentence(grzejSrednio);
        Rule rule3 = new Rule();
        rule3.addInputSentence(cieplo);
        rule3.setOutputSentence(grzejLekko);
        
        this.rules.add(rule1);
        this.rules.add(rule2);
        this.rules.add(rule3);
    }
    
    public void calculateSentencesMembership()
    {
        for (Sentence sentence : this.sentences) {
             String name = sentence.getName();
             String term = sentence.getTerm();
             LinguisticVariable variable = this.getLinguisticVariableByName(name);
             FuzzySet set = variable.getSetByName(term);
             MembershipFunction mFunction = set.getMembershipFunction();
             Double value = this.userInput.get(name);
             sentence.setMembershipValue(mFunction.calculateMembership(value));
             
        }
    }
    
    public void getSystemInput()
    { 
        this.userInput = new HashMap();
        this.userInput.put("piec", 80.0);
        this.userInput.put("temperatura", 10.1);
    }
    
    public void makeImplications()
    {
        for (Rule rule : this.rules) {
            rule.makeImplication(this.implicationOperator, this.norms);            
        }
    }
        
    public void aggregateResults()
    {
        
        for (String key : this.outputLinguisticVariables.keySet()) {
            String variableName = outputLinguisticVariables.get(key).getName();
            ArrayList<Double> rulesValues = this.getRulesImplicatonsValues(variableName);
            this.aggregationResult.put(variableName, this.aggregationOperator.aggregateResults(rulesValues)); 
        }
       
       
    }
    
    public void showResult()
    {
        
        System.out.println(this.aggregationResult.toString());
    }
    
    LinguisticVariable getLinguisticVariableByName(String name)
    {
        if(this.outputLinguisticVariables.containsKey(name)) {
            return this.outputLinguisticVariables.get(name);
        } else if (this.inputLinguisticVariables.containsKey(name)) {
            return this.inputLinguisticVariables.get(name);
        }
        return null;
    }
    
    Norms getNorms()
    {
        return this.norms;
    }
    
    public ImplicationOperator getImplicationOperator(){
        return this.implicationOperator;
    }

    private ArrayList<Double> getRulesImplicatonsValues(String variableName) {
        ArrayList<Double> rulesValues = new ArrayList();
        for (Iterator<Rule> it = this.rules.iterator(); it.hasNext();) {
            Rule rule = it.next();
            if (rule.getOutputSentence().getName().equals(variableName)) {
                rulesValues.add(rule.getImplicationResult());
            }
        }
        
        return rulesValues;
    }
    
}
