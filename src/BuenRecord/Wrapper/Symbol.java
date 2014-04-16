/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Wrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Franklin
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Symbol {
    
    @XmlValue
    private String value;
    
    public Symbol(){
        
    }

    public Symbol(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
