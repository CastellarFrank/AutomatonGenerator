/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Wrapper;
import BuenRecord.Logic.AutomatonType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Franklin
 */
@XmlRootElement(name = "AF")
@XmlAccessorType(XmlAccessType.FIELD)
public class AFParser {
    @XmlAttribute
    private String name;
    
    @XmlAttribute
    private String initial;
    
    @XmlAttribute
    private String type;
    
    @XmlElement(name="State")
    @XmlElementWrapper(name="States")
    private List<State> states;
    
    @XmlElement(name="Symbol")
    @XmlElementWrapper(name="Alphabet")
    private List<Symbol> alphabet;
    
    @XmlElement(name="Transition")
    @XmlElementWrapper(name="Transitions")
    private List<Transition> transitions;
    
    @XmlElement(name="Final")
    @XmlElementWrapper(name="Finals")
    private List<Final> finals;
    
    public AFParser(){
        states = new ArrayList<State>();
        alphabet = new ArrayList<Symbol>();
        transitions = new ArrayList<Transition>();
        finals = new ArrayList<Final>();
    }
    
    public static void marshal(String fileName, AFParser content) throws JAXBException{
        File file = new File(fileName);
        JAXBContext jaxbContext  = JAXBContext.newInstance(AFParser.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbMarshaller.marshal(content, file);
    }
    
    public static  AFParser unmarshal(String fileName) throws JAXBException{
        File file = new File(fileName);
        return unmarshal(file);
    }
    
    public static AFParser unmarshal(File file) throws JAXBException{
        JAXBContext jaxbContext;
        AFParser parse = null;
        jaxbContext = JAXBContext.newInstance(AFParser.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        parse = (AFParser) jaxbUnmarshaller.unmarshal(file);
        return parse;
    }
    
    public State getStateByValue(String value){
        for(State state : states){
            if(state.mathByValue(value))
                return state;
        }
        return null;
    }
    
    public List<Transition> getTransitionsByState(String stateValue){
        List<Transition> tempList = new ArrayList<Transition>();
        for(Transition tran: transitions){
            if(tran.mathByStateValue(stateValue))
                tempList.add(tran);
        }
        return tempList;
    }
    
    public Transition getTransitionByStateAndSymbol(String state, String symbol) throws Exception{
        for(Transition tran: transitions){
            if(tran.matchByStateAndSymbol(state, symbol, getSymbolByAlter(tran.getSymbolRef())))
                return tran;
        }
        return null;
    }
    
    public List<Transition> getTransitionsByStateAndSymbol(String state, String symbol) throws Exception{
        List<Transition> result = new ArrayList<Transition>();
        for(Transition tran: transitions){
            if(tran.matchByStateAndSymbol(state, symbol, getSymbolByAlter(tran.getSymbolRef())))
                result.add(tran);
        }
        return result;
    }
    
    public List<String> getResultingStatesValuesByStateAndSymbol(String state, String symbol) throws Exception{
        List<String> result = new ArrayList<String>();
        for(Transition tran: transitions){
            if(tran.matchByStateAndSymbol(state, symbol, getSymbolByAlter(tran.getSymbolRef())))
                result.add(tran.getResult());
        }
        return result;
    }
    
    public List<String> getResultingStatesValuesByStateAndPureSymbol(String state, String symbol) throws Exception{
        List<String> result = new ArrayList<String>();
        for(Transition tran: transitions){
            if(tran.matchByStateAndPureSymbol(state, symbol, getSymbolByAlter(tran.getSymbolRef())))
                result.add(tran.getResult());
        }
        return result;
    }
    
    public Final findFinalByValue(String value){
        for(Final fin: finals){
            if(fin.matchByValue(value))
                return fin;
        }
        return null;
    }
    
    public Final fingFinalByValues(List<String> values){
        for(String value : values){
            Final finalElement = findFinalByValue(value);
            if(finalElement != null)
                return finalElement;   
        }
        return null;
    }
    
    public void addState(State s){
        states.add(s);
    }
    
    public void addTransition(Transition tran){
        transitions.add(tran);
    }
    
    public void addTransitions(List<Transition> transitions){
        for(Transition tran : transitions){
            this.addTransition(tran);
        }
    }
    
    public void addSymbol(Symbol symbol){
        boolean notFinded = true;
        for(Symbol s : alphabet)
            if(s.getValue().equals(symbol.getValue()))
                notFinded = false;
        if(notFinded)
            alphabet.add(symbol);
    }
    
    public void addAlphabet(List<Symbol> alpha){
        for(Symbol s : alpha){
            this.addSymbol(s);
        }
    }
    
    public void addStates(List<State> states){
        for(State s: states){
            this.addState(s);
        }
    }
    
    
    public void addFinal(Final fin){
        boolean notFinded = true;
        for(Final f : finals)
            if(f.getValue().equals(fin.getValue()))
                notFinded = false;
        if(notFinded)
            finals.add(fin);
    }
    
    public boolean findStateByName(String name){
        for(State state : states){
            if(state.mathByValue(name))
                return true;
        }
        return false;
    }
    
    public AutomatonType getAutomatonType(){
        if(this.type!= null)
            if(this.type.equals("AFD"))
                return  AutomatonType.AFD;
            else if(this.type.equals("AFN"))
                return AutomatonType.AFN;
            else if(this.type.equals("AFNE"))
                return AutomatonType.AFNE;
            else
                return AutomatonType.UNKNOWN;
        return AutomatonType.UNKNOWN;
    }

    private Symbol getSymbolByAlter(String alter){
        if(alter != null && !alter.isEmpty())
            for(Symbol symbol : this.alphabet){
                if(symbol.mathByAlter(alter))
                    return symbol;  
            }
        
        return null;
    }
    public List<Symbol> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(List<Symbol> alphabet) {
        this.alphabet = alphabet;
    }
    

    public List<Final> getFinals() {
        return finals;
    }

    public void setFinals(List<Final> finals) {
        this.finals = finals;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }    
    
    public void setType(AutomatonType type){
        if(type == AutomatonType.AFD){
            this.type = "AFD";
        }else if(type == AutomatonType.AFN){
            this.type = "AFN";
        }else if(type == AutomatonType.AFNE){
            this.type = "AFNE";
        }else{
            this.type = "UNKNOWN";
        }
    }
    
    public AFParser copy(){
        AFParser result = new AFParser();
        result.setAlphabet(new ArrayList<Symbol>(this.alphabet));
        result.setFinals(new ArrayList<Final>(this.finals));
        result.setInitial(this.initial);
        result.setName(this.name);
        result.setStates(new ArrayList<State>(this.states));
        result.setTransitions(new ArrayList<Transition>(this.transitions));
        result.setType(this.type);
        return result;
    }
}
