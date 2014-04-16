/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Wrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Franklin
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Transition {
    
    @XmlAttribute
    private String state;
    
    @XmlAttribute
    private String symbol;
    
    @XmlAttribute
    private String result;
    
    public Transition(){
        
    }

    public Transition(String s, String sy, String re) {
        this.state = s;
        this.symbol = sy;
        this.result = re;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
}
