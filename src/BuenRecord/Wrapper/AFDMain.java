/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Wrapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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
@XmlRootElement(name = "AFD")
@XmlAccessorType(XmlAccessType.FIELD)
public class AFDMain {
    @XmlAttribute
    private String name;
    
    @XmlAttribute
    private String initial;
    
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
    
    public AFDMain(){
        states = new ArrayList<State>();
        alphabet = new ArrayList<Symbol>();
        transitions = new ArrayList<Transition>();
    }
    
    public static  AFDMain unmarshal(String fileName) throws JAXBException{
        File file = new File(fileName);
        return unmarshal(file);
    }
    
    public static AFDMain unmarshal(File file) throws JAXBException{
        JAXBContext jaxbContext;
        AFDMain parse = null;
        jaxbContext = JAXBContext.newInstance(AFDMain.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        parse = (AFDMain) jaxbUnmarshaller.unmarshal(file);
        return parse;
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
    
    
    
    
}
