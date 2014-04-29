/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Wrapper;

import BuenRecord.Utils.TestUtils;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.JAXBException;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Franklin
 */

public class AFDParserTest {
    
    
    @Test(expected = UnmarshalException.class)
    public void testingXMLWithoutAnyStructure() throws IOException, JAXBException{
        String fileName = "testing.xml";
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        TestUtils.createFile(fileName, content);
        AFParser.unmarshal(new File(fileName));
    }
    
    @Test
    public void testingXMLWithAFDMainStructure() throws IOException, JAXBException{
        String fileName = "testing.xml";
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<AFD></AFD>";
        TestUtils.createFile(fileName, content);
        AFParser parse = AFParser.unmarshal(new File(fileName));
        Assert.assertNotNull(parse);
    }
    
    @Test
    public void testingXMLWithOneState() throws IOException, JAXBException{
        String fileName = "testing.xml";
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<AFD>"
                    + "<States>"
                        + "<State>q0</State>"
                    + "</States>"
                + "</AFD>";
        
        TestUtils.createFile(fileName, content);
        AFParser parse = AFParser.unmarshal(new File(fileName));
        Assert.assertEquals(1,parse.getStates().size());
        Assert.assertEquals("q0",parse.getStates().get(0).getValue());
    }
    
    @Test
    public void testingXMLWithOneStateAndOneSymbol() throws IOException, JAXBException{
        String fileName = "testing.xml";
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<AFD>"
                    + "<States>"
                        + "<State>q0</State>"
                    + "</States>"
                    + "<Alphabet>"
                        + "<Symbol>1</Symbol>"
                    + "</Alphabet>"
                + "</AFD>";
        
        TestUtils.createFile(fileName, content);
        AFParser parse = AFParser.unmarshal(new File(fileName));
        Assert.assertEquals(1,parse.getStates().size());
        Assert.assertEquals("q0",parse.getStates().get(0).getValue());
        
        Assert.assertEquals(1,parse.getAlphabet().size());
        Assert.assertEquals("1",parse.getAlphabet().get(0).getValue());
    }
    
    @Test
    public void testingXMLWithOneStateAndOneSymbolAndOneTransition() throws IOException, JAXBException{
        String fileName = "testing.xml";
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<AFD>"
                    + "<States>"
                        + "<State>q0</State>"
                    + "</States>"
                    + "<Alphabet>"
                        + "<Symbol>1</Symbol>"
                    + "</Alphabet>"
                    + "<Transitions>"
                        + "<Transition state=\"q0\" symbol=\"1\" result=\"q1\"/>"
                    + "</Transitions>"
                + "</AFD>";
        
        TestUtils.createFile(fileName, content);
        AFParser parse = AFParser.unmarshal(new File(fileName));
        Assert.assertEquals(1,parse.getStates().size());
        Assert.assertEquals("q0",parse.getStates().get(0).getValue());
        
        Assert.assertEquals(1,parse.getAlphabet().size());
        Assert.assertEquals("1",parse.getAlphabet().get(0).getValue());
        
        Assert.assertEquals(1,parse.getTransitions().size());
        Assert.assertEquals("q1",parse.getTransitions().get(0).getResult());
        Assert.assertEquals("q0",parse.getTransitions().get(0).getState());
        Assert.assertEquals("1",parse.getTransitions().get(0).getSymbol());
    }
    
    @Test
    public void testingXMLWithOneStateAndOneSymbolAndOneTransitionAndOneFinal() throws IOException, JAXBException{
        String fileName = "testing.xml";
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<AFD>"
                    + "<States>"
                        + "<State>q0</State>"
                    + "</States>"
                    + "<Alphabet>"
                        + "<Symbol>1</Symbol>"
                    + "</Alphabet>"
                    + "<Transitions>"
                        + "<Transition state=\"q0\" symbol=\"1\" result=\"q1\"/>"
                    + "</Transitions>"
                    + "<Finals>"
                        + "<Final>q1</Final>"
                    + "</Finals>"
                + "</AFD>";
        
        TestUtils.createFile(fileName, content);
        AFParser parse = AFParser.unmarshal(new File(fileName));
        Assert.assertEquals(1,parse.getStates().size());
        Assert.assertEquals("q0",parse.getStates().get(0).getValue());
        
        Assert.assertEquals(1,parse.getAlphabet().size());
        Assert.assertEquals("1",parse.getAlphabet().get(0).getValue());
        
        Assert.assertEquals(1,parse.getTransitions().size());
        Assert.assertEquals("q1",parse.getTransitions().get(0).getResult());
        Assert.assertEquals("q0",parse.getTransitions().get(0).getState());
        Assert.assertEquals("1",parse.getTransitions().get(0).getSymbol());
        
        Assert.assertEquals(1,parse.getFinals().size());
        Assert.assertEquals("q1",parse.getFinals().get(0).getValue());
    }
    
    @Test
    public void testingXMLWithBinaryStructureExample() throws IOException, JAXBException{
        String fileName = "testing.xml";
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<AFD name=\"AnyName\" initial=\"q0\">"
                    + "<States>"
                        + "<State>q0</State>"
                    + "</States>"
                    + "<Alphabet>"
                        + "<Symbol>1</Symbol>"
                    + "</Alphabet>"
                    + "<Transitions>"
                        + "<Transition state=\"q0\" symbol=\"1\" result=\"q1\"/>"
                    + "</Transitions>"
                    + "<Finals>"
                        + "<Final>q1</Final>"
                    + "</Finals>"
                + "</AFD>";
        
        TestUtils.createFile(fileName, content);
        AFParser parse = AFParser.unmarshal(new File(fileName));
        Assert.assertEquals("q0",parse.getInitial());
        Assert.assertEquals("AnyName",parse.getName());
        
        Assert.assertEquals(1,parse.getStates().size());
        Assert.assertEquals("q0",parse.getStates().get(0).getValue());
        
        Assert.assertEquals(1,parse.getAlphabet().size());
        Assert.assertEquals("1",parse.getAlphabet().get(0).getValue());
        
        Assert.assertEquals(1,parse.getTransitions().size());
        Assert.assertEquals("q1",parse.getTransitions().get(0).getResult());
        Assert.assertEquals("q0",parse.getTransitions().get(0).getState());
        Assert.assertEquals("1",parse.getTransitions().get(0).getSymbol());
        
        Assert.assertEquals(1,parse.getFinals().size());
        Assert.assertEquals("q1",parse.getFinals().get(0).getValue());
    }
    
}
