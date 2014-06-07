/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.RegExp.Parser;

/**
 *
 * @author Franklin
 */
public class Parser {
    private String regExp;
    private int currentInputPosition;
    private char currentSymbol;
    
    public Parser(String regExp){
        this.regExp = regExp;
        this.currentInputPosition = 0;
        this.currentSymbol = getNextSymbol();
    }

    private char getNextSymbol() {
        if (currentInputPosition >= regExp.length())
                return '\0';
        
        char nextSymbol = regExp.charAt(currentInputPosition);
        currentInputPosition++;
        return nextSymbol;
    }
    
    public RegularExpresionNode Parse() throws ParserException
    {
        RegularExpresionNode node =  ER();
        if (currentSymbol != '\0')
            throw new ParserException("Was expecting a end of file");
        return node;
    }

    private RegularExpresionNode ER() throws ParserException {
        RegularExpresionNode operandNode =  N2();
        return  ERp(operandNode);
    }

    private RegularExpresionNode ERp(RegularExpresionNode leftOperandNode) throws ParserException {
        if (currentSymbol == '+'){
            currentSymbol = getNextSymbol();
            RegularExpresionNode rightOperandNode = N2();
            OrNode orNode = new OrNode();
            orNode.leftOperandNode = leftOperandNode;
            orNode.rightOperandNode = rightOperandNode;
            return ERp(orNode);
        }
        else
        {
            return leftOperandNode;
        }
    }
    
    private RegularExpresionNode N2() throws ParserException
    {
        RegularExpresionNode operandNode = N3();
        return N2p(operandNode);
    }
    
    private RegularExpresionNode N2p(RegularExpresionNode leftOperandNode) throws ParserException{
        if (currentSymbol == '.')
        {
            currentSymbol = getNextSymbol();
            RegularExpresionNode rightOperandNode = N3();
            ConcatNode cNode = new ConcatNode();
            cNode.leftOperandNode = leftOperandNode;
            cNode.rightOperandNode = rightOperandNode;
            
            return N2p(cNode);
        }
        else
        {
            return leftOperandNode;
        }
    }
    
    private RegularExpresionNode N3() throws ParserException
    {
        RegularExpresionNode operandNode = F();
        return N3p(operandNode);
    }
    
    private RegularExpresionNode N3p(RegularExpresionNode operandNode)
    {
        if (currentSymbol == '*')
        {
            currentSymbol = getNextSymbol();
            CycleNode cycleNode = new CycleNode();
            cycleNode.OperandNode = operandNode;
            return cycleNode;
        }
        else
        {
            return operandNode;
        }
    }
    
    private RegularExpresionNode F() throws ParserException
    {
        if (currentSymbol == '(')
        {
            currentSymbol = getNextSymbol();
            RegularExpresionNode subExpresionNode = ER();
            if (currentSymbol != ')')
                throw new ParserException("Was expecting a )");
            currentSymbol = getNextSymbol();
            return subExpresionNode;
        }
        else
            return Symbol();
    }
    
    private RegularExpresionNode Symbol() throws ParserException
    {
        if (Character.isLetterOrDigit(currentSymbol))
        {
            SymbolNode sNode = new SymbolNode();
            sNode.Value = currentSymbol;
            RegularExpresionNode symbolNode = sNode;
            currentSymbol = getNextSymbol();
            return symbolNode;
        }
        else if(currentSymbol=='$')
        {
             currentSymbol = getNextSymbol();
            return new EpsilonSymbol();
        }
        else
        {
           throw new ParserException("Symbol not recognized");    
        }
    }
}
