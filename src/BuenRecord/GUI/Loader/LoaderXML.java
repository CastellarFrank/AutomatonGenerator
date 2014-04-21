/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LoaderXML.java
 *
 * Created on 04-10-2014, 07:37:09 PM
 */
package BuenRecord.GUI.Loader;

import BuenRecord.Logic.AFDProcessing;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Franklin
 */
public class LoaderXML extends javax.swing.JFrame {

    
    
    public AFDProcessing AFDManager;
    /** Creates new form LoaderXML */
    public LoaderXML() {
        initComponents();
        this.setResizable(false);
        AFDManager = new AFDProcessing();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtPath = new javax.swing.JTextField();
        btnPath = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnLoad = new javax.swing.JButton();
        btnValidate = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblADFLoaded = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Load XML File");

        btnPath.setText("...");
        btnPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPathActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("ADF XML Loader");

        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        btnValidate.setText("Expression");
        btnValidate.setEnabled(false);
        btnValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidateActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("ADF Loaded:");

        lblADFLoaded.setText("There isn't any ADF loaded.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblADFLoaded))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(24, 24, 24)
                            .addComponent(btnValidate, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtPath, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnPath, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel2))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblADFLoaded))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnPath)
                    .addComponent(txtPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnValidate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-467)/2, (screenSize.height-289)/2, 467, 289);
    }// </editor-fold>//GEN-END:initComponents

private void btnPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPathActionPerformed
    JFileChooser saveFile = new JFileChooser();
    
    int selected = saveFile.showOpenDialog(this);
    if(selected == JFileChooser.APPROVE_OPTION)
        txtPath.setText(saveFile.getSelectedFile().getAbsolutePath());
}//GEN-LAST:event_btnPathActionPerformed

private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
    String tempPath = txtPath.getText();
    if(tempPath.isEmpty()){
        JOptionPane.showMessageDialog(this, "You have to select a XML File first",
                "Missing Value",JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    File tempFile = new File(tempPath);
    if(!tempFile.exists() || tempFile.isDirectory()){
        JOptionPane.showMessageDialog(this, "The current selected path is invalid.",
                "Invalid Path", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    parseXMLFile(tempFile);
        
}//GEN-LAST:event_btnLoadActionPerformed

private void btnValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidateActionPerformed
    String expression = JOptionPane.showInputDialog(this, "Write an expression to validate.", 
            "Input Expression", JOptionPane.INFORMATION_MESSAGE);
    
    boolean isValid = AFDManager.validateExpression(expression);
    if(isValid)
        JOptionPane.showMessageDialog(this, "The expression: [" + expression + "] is valid.", 
                "Valid expression", JOptionPane.INFORMATION_MESSAGE);
    else
        JOptionPane.showMessageDialog(this, "The expression: [" + expression + "] is invalid.\nDetail: " + AFDManager.getErrorMessage(), 
                "Invalid expression", JOptionPane.ERROR_MESSAGE);
}//GEN-LAST:event_btnValidateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoaderXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoaderXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoaderXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoaderXML.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new LoaderXML().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnPath;
    private javax.swing.JButton btnValidate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblADFLoaded;
    private javax.swing.JTextField txtPath;
    // End of variables declaration//GEN-END:variables

    private void parseXMLFile(File tempFile) {
        try {
            AFDManager.processXML(tempFile);
        } catch (JAXBException ex) {
            invalidAFDLoaded("Error parsing xml file.");
        }
        
        String error = AFDManager.validateAFD();
        
        if(!error.isEmpty()){
            invalidAFDLoaded(error);
            return;
        }
        
        validAFDLoaded();
    }
    
    private void validAFDLoaded(){
        lblADFLoaded.setText("AFD with name: [" + AFDManager.getContent().getName() + "] has been loaded.");
        btnValidate.setEnabled(true);
    }
    
    private void invalidAFDLoaded(String msg){
        if(!msg.isEmpty())
            JOptionPane.showMessageDialog(this,msg, 
                    "Error XML", JOptionPane.ERROR_MESSAGE);
        
        lblADFLoaded.setText("There isn't any ADF loaded.");
        btnValidate.setEnabled(false);
    }
}
