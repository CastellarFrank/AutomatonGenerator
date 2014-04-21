/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuenRecord.Utils;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Franklin
 */
public class TestUtils {
    public static void createFile(String fileName, String content) throws IOException{
        FileWriter tempFile = new FileWriter(fileName);
        tempFile.write(content);
        tempFile.close();
    }
}
