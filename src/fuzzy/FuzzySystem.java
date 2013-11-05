/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    
    private HashMap<String, Sentence> sentences;
    private ArrayList<Rule> rules;
    private HashMap<String, Double> userInput;
    private HashMap<String, Double> aggregationResult;
    private HashMap<String, LinguisticVariable> outputLinguisticVariables;
    private HashMap<String, LinguisticVariable> inputLinguisticVariables;
    private final BufferedReader reader;
    private BufferedReader fileReader;
    
    FuzzySystem()
    {
        this.aggregationResult = new HashMap();
        this.inputLinguisticVariables = new HashMap();
        this.outputLinguisticVariables = new HashMap();
        this.sentences = new HashMap();
        this.rules = new ArrayList();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void run() 
    {
        Integer userChoose = this.getUserInputChoose();
        if (userChoose.equals(1)) {
            this.getProgrammingConfigurationFromUser();
            this.getInputVariablesFromUser();
            this.getOutputVariablesFromUser();
            this.generateSentences();
            try {
                this.createRules();
            } catch (Exception ex) {
                this.writeOutput("Something is wrong with this rule. "+ex.getMessage());
                System.exit(1);
            }
        } else if (userChoose.equals(2)) {
            String filePath = this.getInputLine("Type file path to read configuration: ");
            try {
                this.fileReader = new BufferedReader(new FileReader(new File(filePath)));
            } catch (FileNotFoundException ex) {
                this.writeOutput("There is no such file");
                System.exit(1);
            }
            try {
                this.readConfigurationFromFile();
            } catch (Exception ex) {
                this.writeOutput("Something goes wrong while reading config file. "+ex.getMessage());
                System.exit(1);
            }
        } else {
            writeOutput("Wrong choose");
            System.exit(1);
        }
        this.getSystemInput();
        this.calculateSentencesMembership();
        this.makeImplications();
        this.aggregateResults();
        this.showResult();
    }
    
    public void getProgrammingConfigurationFromUser()
    {   
        String mFunctonName = this.getInputLine("Enter mFunction type name (triangle): ");
        String implicationOperatorName = this.getInputLine("Enter implication operator type name (fodor): ");
        String aggregationOperatorName = this.getInputLine("Enter aggregation operator type name (minimum): ");
        String normsName = this.getInputLine("Enter norms type name (standard): ");
        this.initialize(mFunctonName, implicationOperatorName, aggregationOperatorName, normsName);
        
    }
    
    public void getInputVariablesFromUser()
    {
        this.writeOutput("Define input variables (END_INPUT_VARS to finish): " + System.getProperty("line.separator"));
        String varsInput;
        while (!(varsInput = this.getInputLine(System.getProperty("line.separator"))).equals("END_INPUT_VARS")) {
            if (!varsInput.equals("BEGIN_INPUT_VARS")) {
                String lingVarName = varsInput;
                LinguisticVariable lingVar = new LinguisticVariable(lingVarName);
                String termsInput;
                while (!(termsInput = this.getInputLine(System.getProperty("line.separator"))).equals("END_TERMS")) {
                    if (!termsInput.equals("BEGIN_TERMS")) {
                        String[] termParams = termsInput.split(" ");
                        FuzzySet term = new FuzzySet(termParams, this.membershipFunction);
                        lingVar.addFuzzySet(term);
                    }
                }
                this.inputLinguisticVariables.put(lingVar.getName(), lingVar);
            }
        }
    }
    
    public void getOutputVariablesFromUser()
    {
        this.writeOutput("Define output variables (END_OUTPUT_VARS to finish): " + System.getProperty("line.separator"));
        String varsInput;
        while (!(varsInput = this.getInputLine(System.getProperty("line.separator"))).equals("END_OUTPUT_VARS")) {
            if (!varsInput.equals("BEGIN_OUTPUT_VARS")) {
                String lingVarName = varsInput;
                LinguisticVariable lingVar = new LinguisticVariable(lingVarName);
                String termsInput;
                while (!(termsInput = this.getInputLine(System.getProperty("line.separator"))).equals("END_TERMS")) {
                    if (!termsInput.equals("BEGIN_TERMS")) {
                        String[] termParams = termsInput.split(" ");
                        FuzzySet term = new FuzzySet(termParams, this.membershipFunction);
                        lingVar.addFuzzySet(term);
                    }
                }
                this.outputLinguisticVariables.put(lingVar.getName(), lingVar);
            }
        }
    }
    
    public void initialize(
            String membershipFunctionName,
            String implicationOperatorName,
            String aggregationOperatorName,
            String normsName
            )
    {
        FunctionsFactory fFactory = new FunctionsFactory();
        fFactory.registerMembershipFunction("triangle", TriangleMembershipFunction.class);
        fFactory.registerImplicationOperator("fodor", FodorImplicationOperator.class);
        fFactory.registerImplicationOperator("tsk", TSKImplicationOperator.class);
        fFactory.registerAggregationOperator("minimum", MinimumAggregationOperator.class);
        fFactory.registerAggregationOperator("average", AverageAggregationOperator.class);
        fFactory.registerNorm("standard", StandardNorms.class);
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
    
    
    public void generateSentences()
    {
        this.generateOneTypeSentences(inputLinguisticVariables);
        //this.generateOneTypeSentences(outputLinguisticVariables);
    }
    
    private void generateOneTypeSentences(HashMap<String, LinguisticVariable> variables)
    {
        for (String key : variables.keySet()) {
            LinguisticVariable variable = variables.get(key);
            String variableName = variable.getName();
            
            for (String termKey : variable.getSetsKeys()) {
                Sentence sentence = new Sentence();
                try {
                    sentence.setParams(variable, termKey);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
                this.sentences.put(variableName+termKey, sentence);
            }
        }
    }
    
    public void createRules() throws Exception
    {
        
        this.writeOutput("Define rules (END_RULES to finish): " + System.getProperty("line.separator"));
        String rulesInput;
        while (!(rulesInput = this.getInputLine(System.getProperty("line.separator"))).equals("END_RULES")) {
            if (!rulesInput.equals("BEGIN_RULES")) {
                String[] splitedByThen = rulesInput.split("THEN");
                String[] precedents = splitedByThen[0].trim().split(" ");
                String consequent = splitedByThen[1].trim().split("is")[0].trim();
                String[] consequentParams = splitedByThen[1].split("is")[1].trim().split(" ");
                
                if (precedents.length+1 != consequentParams.length) {
                   throw new Exception("Somethins is wrong with consequent parameters in rule: "+rulesInput);
                }
                Rule rule = new Rule();
                rule.setTskParameter(Double.parseDouble(consequentParams[consequentParams.length-1]));
                Integer precedentIndex = 0;
                for (String precedent : precedents) {
                    String[] sentenceStrings = precedent.split("=");
                    Sentence sentence = this.sentences.get(sentenceStrings[0]+sentenceStrings[1]);
                    if (sentence == null) {
                        throw new Exception("There is no possible sentence: "+sentenceStrings[0]+"="+sentenceStrings[1]+". Check your config file.");
                    }
                    sentence.setTskParameter(Double.parseDouble(consequentParams[precedentIndex]));
                    rule.addInputSentence(sentence);
                    precedentIndex++;
                }
                rule.setOutputVariable(this.outputLinguisticVariables.get(consequent));
                this.rules.add(rule);
            }
        }
        
    }
    
    public void calculateSentencesMembership()
    {
        for (String sentenceKey : this.sentences.keySet()) {
            Sentence sentence = this.sentences.get(sentenceKey);
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
        this.writeOutput("Please specify system datas."+System.getProperty("line.separator"));
        for (String key : this.inputLinguisticVariables.keySet()) {
            Double value = Double.parseDouble(this.getInputLine("Insert value for "+key+": "));
            this.userInput.put(key, value);
        }
    }
    
    public void makeImplications()
    {
        ExecutorService exec = Executors.newFixedThreadPool(Math.min(this.rules.size(), Runtime.getRuntime().availableProcessors()));
        for (Rule rule : this.rules) {
            rule.setImplicationParameters(this.implicationOperator, this.norms, this.userInput);
            exec.execute(rule);
        }
        exec.shutdown();
        while (!exec.isTerminated())
            ;
    }
        
    public void aggregateResults()
    {        
        for (String key : this.outputLinguisticVariables.keySet()) {
            String variableName = outputLinguisticVariables.get(key).getName();
            HashMap<String, ImplicationResults> rulesValues = this.getRulesImplicatonsValues(variableName);
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

    private HashMap<String, ImplicationResults> getRulesImplicatonsValues(String variableName) {
        HashMap<String, ImplicationResults> rulesValues = new HashMap();
        Integer ruleIndex = 1;
        for (Iterator<Rule> it = this.rules.iterator(); it.hasNext();) {
            Rule rule = it.next(); 
            if (rule.getOutputVariable().getName().equals(variableName)) {
                rulesValues.put(variableName+ruleIndex.toString(), rule.getImplicationResult());
            }
            ruleIndex++;
        }
        
        return rulesValues;
    }
    
    public void writeOutput(String message)
    {
        System.out.print(message);
    }
    
    public String getInputLine(String requetMessage)
    {
        String input = null;
        try {
            System.out.print(requetMessage);
            input = this.reader.readLine();
        } catch (IOException ioe) {
             System.out.println("IO error trying to read your name!");
             System.exit(1);
        }
        
        return input;
        
    }

    private Integer getUserInputChoose() {
        return Integer.parseInt(this.getInputLine("Type 1 for input configuration manually or 2 for input from file: "));
    }

    private void readConfigurationFromFile() throws IOException, Exception {
        
        String mFunctionName = this.fileReader.readLine();
        String implicationOperatorName = this.fileReader.readLine();
        String aggregationOperatorName = this.fileReader.readLine();
        String normsName = this.fileReader.readLine();
        this.initialize(mFunctionName, implicationOperatorName, aggregationOperatorName, normsName);            
        
        
        String varsInput;
        while (!(varsInput = this.fileReader.readLine()).equals("END_INPUT_VARS")) {
            if (!varsInput.equals("BEGIN_INPUT_VARS")) {
                String lingVarName = varsInput;
                LinguisticVariable lingVar = new LinguisticVariable(lingVarName);
                String termsInput;
                while (!(termsInput = this.fileReader.readLine()).equals("END_TERMS")) {
                    if (!termsInput.equals("BEGIN_TERMS")) {
                        String[] termParams = termsInput.split(" ");
                        FuzzySet term = new FuzzySet(termParams, this.membershipFunction);
                        lingVar.addFuzzySet(term);
                    }
                }
                this.inputLinguisticVariables.put(lingVar.getName(), lingVar);
            }
        }
        
        while (!(varsInput = this.fileReader.readLine()).equals("END_OUTPUT_VARS")) {
            if (!varsInput.equals("BEGIN_OUTPUT_VARS")) {
                String lingVarName = varsInput;
                LinguisticVariable lingVar = new LinguisticVariable(lingVarName);
                this.outputLinguisticVariables.put(lingVar.getName(), lingVar);
            }
        }
        
        this.generateSentences();
        
        String rulesInput;
        while (!(rulesInput = this.fileReader.readLine()).equals("END_RULES")) {
            if (!rulesInput.equals("BEGIN_RULES")) {
                String[] splitedByThen = rulesInput.split("THEN");
                String[] precedents = splitedByThen[0].trim().split(" ");
                String consequent = splitedByThen[1].trim().split("is")[0].trim();
                String[] consequentParams = splitedByThen[1].split("is")[1].trim().split(" ");
                
                if (precedents.length+1 != consequentParams.length) {
                   throw new Exception("Somethins is wrong with consequent parameters in rule: "+rulesInput);
                }
                Rule rule = new Rule();
                rule.setTskParameter(Double.parseDouble(consequentParams[consequentParams.length-1]));
                Integer precedentIndex = 0;
                for (String precedent : precedents) {
                    
                    String[] sentenceStrings = precedent.split("=");
                    Sentence sentence = this.sentences.get(sentenceStrings[0]+sentenceStrings[1]);
                    if (sentence == null) {
                        throw new Exception("There is no possible sentence: "+sentenceStrings[0]+"="+sentenceStrings[1]+". Check your config file.");
                    }
                    sentence.setTskParameter(Double.parseDouble(consequentParams[precedentIndex]));
                    rule.addInputSentence(sentence);
                    precedentIndex++;
                }
                rule.setOutputVariable(this.outputLinguisticVariables.get(consequent));
                this.rules.add(rule);
            }
        }
        
    }
    
}
