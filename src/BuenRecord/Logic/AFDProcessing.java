/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Logic;

import BuenRecord.Wrapper.AFDParser;
import BuenRecord.Wrapper.Transition;
import java.io.File;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Franklin
 */
public class AFDProcessing {
    private AFDParser content;
    private String errorMessage;
    
    public AFDParser getContent() {
        return content;
    }

    public void setContent(AFDParser content) {
        this.content = content;
    }

    public void processXML(File tempFile) throws JAXBException {
        content = AFDParser.unmarshal(tempFile);
    }

    public String validateAFD() {
        return "";
    }
    
    public boolean validateExpression(String expression){
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
}
