/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Utils;

import BuenRecord.Wrapper.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Franklin
 */
public class SymbolComplexValueHelper {
    
     public static boolean isComplex(Symbol symbol){
        if(symbol == null || symbol.getAlter() == null || symbol.getAlter().isEmpty())
            return false;
        
        return isComplex(symbol.getValue());
    }
    
    public static boolean isComplex(String symbolValue){
        if(symbolValue == null || symbolValue.isEmpty())
            return false;
        
        return symbolValue.startsWith("#{") && symbolValue.endsWith("}");
    }
    
    public static List<String> processComplexValue(String complexValue) throws Exception{
        if(!SymbolComplexValueHelper.isComplex(complexValue))
            throw new Exception("Symbol: [" + complexValue + "] isn't a complex valid type.");
        
        String realValue = cleanComplexValue(complexValue);
        
        if(realValue.contains("..")){
            String array [] = realValue.split("\\.\\.");
            int first = Integer.parseInt(array[0]);
            int last = Integer.parseInt(array[array.length - 1]);
            if(first > last)
                return null;
            
            List<String> result = new ArrayList<String>();
            for(int i = first; i<=last; i++){
                result.add(String.valueOf(i));
            }
            return result;
            
        }else if(realValue.contains(",")){
            String array [] = realValue.split(",");
            List<String> result = Arrays.<String>asList(array);
            return result;
            
        }else{
            return null;
        }
    }
    
    private static String cleanComplexValue(String complexValue){
        String result = complexValue.replace("#{", "");
        return result.replace("}", "");       
    }
}
