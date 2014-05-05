/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Wrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Franklin
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Symbol {
    
    @XmlAttribute
    private String alter;
    
    @XmlValue
    private String value;
    
    public Symbol(){
        
    }

    public Symbol(String value) {
        this.value = value;
    }
    
    public boolean mathByAlter(String alter){
        if(this.alter != null)
            return this.alter.equals(alter);
        return false;
    }
    
    public boolean mathValue(String value){
        return this.value.equals(value);
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAlter() {
        return alter;
    }

    public void setAlter(String alter) {
        this.alter = alter;
    }
    
}
