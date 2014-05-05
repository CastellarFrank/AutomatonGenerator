/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Wrapper;

import BuenRecord.Logic.AFProcessing;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Franklin
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class State {
    
    @XmlValue
    private String value;
    
    public State(){
        
    }

    public State(String value) {
        this.value = value;
    }
    
    public boolean mathByValue(String value){
        List<String> valueStates = AFProcessing.parseStateName(value);
        List<String> currentStates = AFProcessing.parseStateName(this.value);
        
        if(valueStates.size() != currentStates.size())
            return false;
        
        for(String tValue : valueStates){
            if(!currentStates.contains(tValue))
                return false;
        }
        return true;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
