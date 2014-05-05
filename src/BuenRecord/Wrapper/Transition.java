/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Wrapper;

import BuenRecord.Utils.SymbolComplexValueHelper;
import java.util.List;
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
    private String symbolRef;
    
    @XmlAttribute
    private String result;
    
    public Transition(){
        
    }

    public Transition(String s, String sy, String re) {
        this.state = s;
        this.symbol = sy;
        this.result = re;
    }
    
    public boolean matchByStateAndSymbol(String state, String symbol, Symbol symbolRef) throws Exception{
        if(!this.state.equals(state))
            return false;
        
        if(this.symbol != null && !this.symbol.isEmpty())
            return this.symbol.equals(symbol);
        
        return matchSymbolbySymbolRef(symbol, symbolRef);
    }
    
    public boolean matchByStateAndPureSymbol(String state, String symbol, Symbol symbolRef){
        if(!this.state.equals(state))
            return false;
        
        if(this.symbol != null && !this.symbol.isEmpty())
            return this.symbol.equals(symbol);
        
        return symbolRef.mathValue(symbol);
    }
    
    public boolean matchSymbolbySymbolRef(String symbol, Symbol symbolRef) throws Exception{
        if(symbolRef == null)
            return false;
        
        if(SymbolComplexValueHelper.isComplex(symbolRef)){
            return processComplexSymbolValue(symbol, symbolRef.getValue());
        }else{
            return symbolRef.mathValue(symbol);
        }
    }
    
    
    public boolean mathByStateValue(String stateValue){
        return this.state.equals(state);
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

    public String getSymbolRef() {
        return symbolRef;
    }

    public void setSymbolRef(String symbolRef) {
        this.symbolRef = symbolRef;
    }

    private boolean processComplexSymbolValue(String symbol, String symbolRef) throws Exception {
        List<String> values = SymbolComplexValueHelper.processComplexValue(symbolRef);
        
        for(String item : values ){
            if(item.equals(symbol))
                return true;
        }
        return false;
    }
    
}
