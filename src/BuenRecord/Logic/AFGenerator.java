/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Logic;

import BuenRecord.RegExp.Parser.ConcatNode;
import BuenRecord.RegExp.Parser.CycleNode;
import BuenRecord.RegExp.Parser.OrNode;
import BuenRecord.RegExp.Parser.RegularExpresionNode;
import BuenRecord.RegExp.Parser.SymbolNode;
import BuenRecord.Wrapper.AFParser;
import BuenRecord.Wrapper.Final;
import BuenRecord.Wrapper.State;
import BuenRecord.Wrapper.Symbol;
import BuenRecord.Wrapper.Transition;

/**
 *
 * @author Franklin
 */
public class AFGenerator {
    private final String EPSILONVALUE = "epsilon";
    private int statesCounter;
    private AFParser contentGenerated;
    private String currentRegExp;
    private boolean isInverted;

    public boolean isInverted() {
        return isInverted;
    }

    public void setInverted(boolean isInverted) {
        this.isInverted = isInverted;
    }
    
    public String getCurrentRegExp() {
        return currentRegExp;
    }

    public void setCurrentRegExp(String currentRegExp) {
        this.currentRegExp = currentRegExp;
    }
    
    public AFParser getAFNE(){
        return this.contentGenerated;
    }
    
    public AFGenerator(){
    }
    
    public AFParser concatenateAutomatas(AFParser left, AFParser right){
        AFParser result = left.copy();
        
        result.setName(left.getName() + "<U>" + right.getName());
        result.addAlphabet(right.getAlphabet());
        result.addStates(right.getStates());
        result.addTransitions(right.getTransitions());
        result.getFinals().clear();
        result.addFinal(right.getFinals().get(0));
        
        result.addTransition(new Transition(left.getFinals().get(0).getValue(), EPSILONVALUE, right.getInitial()));
        return result;
    }
    
    public AFParser orAutomatas(AFParser left, AFParser right){
        AFParser result = left.copy();
        result.setName(left.getName() + "<OR>" + right.getName());
        result.addAlphabet(right.getAlphabet());
        result.addStates(right.getStates());
        result.addTransitions(right.getTransitions());
        
        State initialState = this.getNextState();
        result.addState(initialState);
        result.setInitial(initialState.getValue());
        
        State finalState = this.getNextState();
        result.addState(finalState);
        result.getFinals().clear();
        result.addFinal(new Final(finalState.getValue()));
        
        result.addTransition(new Transition(initialState.getValue(), EPSILONVALUE, left.getInitial()));
        result.addTransition(new Transition(initialState.getValue(), EPSILONVALUE, right.getInitial()));
        
        result.addTransition(new Transition(left.getFinals().get(0).getValue(), EPSILONVALUE, finalState.getValue()));
        result.addTransition(new Transition(right.getFinals().get(0).getValue(), EPSILONVALUE, finalState.getValue()));
        
        return result;
    }
    
    public AFParser asteriscAutomata(AFParser automata){
        AFParser result = automata.copy();
        
        result.setName("(*" + automata.getName() + ")");
        State initialState = this.getNextState();
        result.addState(initialState);
        result.setInitial(initialState.getValue());
        
        State finalState = this.getNextState();
        result.addState(finalState);
        result.getFinals().clear();
        result.addFinal(new Final(finalState.getValue()));
        
        result.addTransition(new Transition(result.getInitial(), EPSILONVALUE, automata.getInitial()));
        result.addTransition(new Transition(result.getInitial(), EPSILONVALUE, finalState.getValue()));
        
        result.addTransition(new Transition(automata.getFinals().get(0).getValue(), EPSILONVALUE, automata.getInitial()));
        result.addTransition(new Transition(automata.getFinals().get(0).getValue(), EPSILONVALUE, finalState.getValue()));
        
        return result;
    }
    
    public AFParser generateLeafAutomata(String leafValue){
        AFParser leafAf = new AFParser();
        State initialState = this.getNextState();
        State finalState = this.getNextState();
        
        leafAf.addState(initialState);
        leafAf.setInitial(initialState.getValue());
        
        leafAf.addState(finalState);
        leafAf.addFinal(new Final(finalState.getValue()));
        
        leafAf.addSymbol(new Symbol(leafValue));
        leafAf.setType(AutomatonType.AFNE);
        
        leafAf.addTransition(new Transition(initialState.getValue(), leafValue, finalState.getValue()));
        leafAf.setName("leaf->" + leafValue);
        
        return leafAf;
    }
    
    public State getNextState(){
        String name = "q" + (statesCounter++);
        return new State(name);
    }

    public void generateAFNEAutomata(RegularExpresionNode startNode, boolean invert) {
        this.statesCounter = 0;
        this.isInverted = invert;
        if(invert){
            
            this.contentGenerated = recursiveInvertedAFNEGenerate(startNode);
        }else{
            this.contentGenerated = recursiveAFNEGenerate(startNode);
        }
    }
    
    private AFParser recursiveInvertedAFNEGenerate(RegularExpresionNode node) {
        AFParser result = null;
        if(node instanceof ConcatNode){
            RegularExpresionNode left = ((ConcatNode)node).leftOperandNode;
            RegularExpresionNode right = ((ConcatNode)node).rightOperandNode;
            result = this.concatenateAutomatas(this.recursiveInvertedAFNEGenerate(right), this.recursiveInvertedAFNEGenerate(left));
        }else if(node instanceof OrNode){
            RegularExpresionNode left = ((OrNode)node).leftOperandNode;
            RegularExpresionNode right = ((OrNode)node).rightOperandNode;
            result = this.orAutomatas(this.recursiveInvertedAFNEGenerate(right), this.recursiveInvertedAFNEGenerate(left));
        }else if(node instanceof CycleNode){
            RegularExpresionNode unique = ((CycleNode)node).OperandNode;
            result = this.asteriscAutomata(this.recursiveInvertedAFNEGenerate(unique));
        }else if(node instanceof SymbolNode){
            String value = "" + ((SymbolNode)node).Value;
            result = this.generateLeafAutomata(value);
        }
        return result;
    }

    private AFParser recursiveAFNEGenerate(RegularExpresionNode node) {
        AFParser result = null;
        if(node instanceof ConcatNode){
            RegularExpresionNode left = ((ConcatNode)node).leftOperandNode;
            RegularExpresionNode right = ((ConcatNode)node).rightOperandNode;
            result = this.concatenateAutomatas(this.recursiveAFNEGenerate(left), this.recursiveAFNEGenerate(right));
        }else if(node instanceof OrNode){
            RegularExpresionNode left = ((OrNode)node).leftOperandNode;
            RegularExpresionNode right = ((OrNode)node).rightOperandNode;
            result = this.orAutomatas(this.recursiveAFNEGenerate(left), this.recursiveAFNEGenerate(right));
        }else if(node instanceof CycleNode){
            RegularExpresionNode unique = ((CycleNode)node).OperandNode;
            result = this.asteriscAutomata(this.recursiveAFNEGenerate(unique));
        }else if(node instanceof SymbolNode){
            String value = "" + ((SymbolNode)node).Value;
            result = this.generateLeafAutomata(value);
        }
        return result;
    }
}
