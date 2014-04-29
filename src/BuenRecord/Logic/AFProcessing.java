/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Logic;

import BuenRecord.Wrapper.AFParser;
import BuenRecord.Wrapper.Final;
import BuenRecord.Wrapper.Symbol;
import BuenRecord.Wrapper.Transition;
import BuenRecord.Wrapper.State;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Franklin
 */
public class AFProcessing {
    private AFParser content;
    private String errorMessage;
    
    public AFParser getContent() {
        return content;
    }

    public void setContent(AFParser content) {
        this.content = content;
    }

    public void processXML(File tempFile) throws JAXBException {
        content = AFParser.unmarshal(tempFile);
    }

    public String validateAF() {
        if(content.getAutomatonType() == AutomatonType.UNKNOWN)
            return "You have to specify the automaton Type at xml.";
        
        return "";
    }
    
    public boolean validateExpression(String expression){
        if(content.getAutomatonType() == AutomatonType.AFD)
            return validateAFDExpression(expression);
        else if(content.getAutomatonType() == AutomatonType.AFN)
            return validateAFNExpression(expression);
        else{
            errorMessage = "Can't validate expression: [" + expression+ "], Missing Automaton Type.";
            return false;
        }
    }
    
    public boolean validateAFNExpression(String expression){
        errorMessage = "";
        List<String> currentStates = new ArrayList<String>();
        currentStates.add(content.getInitial());
        for(int i=0; i<expression.length();i++){
            String currentSymbol = "" + expression.charAt(i);
            List<Transition> trans = new ArrayList<Transition>();
            for(String current : currentStates){
                 List<Transition> tempTransitions = content.getTransitionsByStateAndSymbol(current, currentSymbol);
                 if(tempTransitions != null && !tempTransitions.isEmpty())
                     trans.addAll(tempTransitions);                 
            }
            if(trans.isEmpty()){
                errorMessage = "There isn't any transition for States: " + currentStates + " and Symbol: [" + currentSymbol + "].";
                return false;
            }
            List<String> newsCurrent = new ArrayList<String>();
            for(Transition tran : trans)
                newsCurrent.add(tran.getResult());
            currentStates = newsCurrent;
        }
        
        if(content.fingFinalByValues(currentStates) != null)
            return true;
        else{
            errorMessage = "The ending States:"+ currentStates + " for the expression: [" + expression + "] aren't final States.";
            return false;
        }
    }
    
    public void convertAFNtoAFD(String path) throws JAXBException{
        AFParser afd = null;
        if(this.content.getAutomatonType() == AutomatonType.AFD){
            afd = this.content;
            
        }else if(this.content.getAutomatonType() == AutomatonType.AFN){
            AFParser afn = this.content;
            afd = new AFParser();
            afd.setInitial(afn.getInitial());
            afd.addState(new State(afn.getInitial()));
            afd.setAlphabet(new ArrayList<Symbol>(afn.getAlphabet()));
            
            List<Symbol> symbols = afn.getAlphabet();
            List<List<String>> pendingStates = new ArrayList<List<String>>();
            pendingStates.add(parseStateName(afn.getInitial()));
            while(!pendingStates.isEmpty()){
                List<String> currentStates = pendingStates.remove(pendingStates.size() - 1);
                for(Symbol symbol : symbols){
                    String symbolValue = symbol.getValue();
                    List<String> statesResult = new ArrayList<String>();
                    for(String state : currentStates){
                        statesResult.addAll(afn.getResultingStatesValuesByStateAndSymbol(state, symbolValue));
                    }
                    if(!statesResult.isEmpty()){
                        String newstateName = createStateNameFromStatesList(statesResult);
                        if(!afd.findStateByName(newstateName)){
                            pendingStates.add(statesResult);
                            afd.addState(new State(newstateName));
                            if(afn.fingFinalByValues(statesResult) != null)
                                afd.addFinal(new Final(newstateName));
                        }
                        String oldStateName = createStateNameFromStatesList(currentStates);
                        afd.addTransition(new Transition(oldStateName, symbolValue, newstateName));
                    }
                }
            }
        }
        AFParser.marshal(path, afd);
    }
    
    public boolean validateAFDExpression(String expression){
        errorMessage = "";
        String currentState = content.getInitial();
        for(int i=0; i<expression.length();i++){
            String currentSymbol = "" + expression.charAt(i);
            Transition tran = content.getTransitionByStateAndSymbol(currentState, currentSymbol);
            if(tran == null){
                errorMessage = "There isn't any transition for State: [" + currentState + "] and Symbol: [" + currentSymbol + "].";
                return false;
            }
            currentState = tran.getResult();
        }
        if(content.findFinalByValue(currentState)!=null)
            return true;
        else{
            errorMessage = "The ending State:["+ currentState + "] for the expression: [" + expression + "] isn't a final State.";
            return false;
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    public String createStateNameFromStatesList(List<String> states){
        if(states.isEmpty())
            return "";
        String result = states.get(0);
        if(states.size() >= 2){
            for(int i=1; i< states.size(); i++){
                result += "|" + states.get(i);
            }
        }
        return result;
    }
    
    public List<String> parseStateName(String name){
        return new ArrayList<String>(Arrays.asList(name.split("\\|")));
    }
}
