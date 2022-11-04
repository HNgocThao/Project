
package View;

import Model.ConnectSQL;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author OS
 */
public class AddCustomer extends javax.swing.JFrame {
    Connection cn;   
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    public AddCustomer() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        connect();
        LoadIdCus();
        Crown();
        setFrameStyle();
        Icon();
    }
    private void connect(){
        try {
            cn = ConnectSQL.getConnectSQL();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void Icon(){
        URL url = getClass().getResource("/Image/Item.jpg");
        ImageIcon img = new ImageIcon(url);
        super.setIconImage(img.getImage());
    }
    
    private void setFrameStyle() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        getContentPane().setBackground(new java.awt.Color(251, 242, 233));
        setBounds((width - 440) / 2, (height - 320) / 2, 440, 320);
        btAdd.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/LOGIN.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        btOut.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/EXITS.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        setFont(new Font("Verdana", Font.BOLD, 10));
    }
    
    private boolean CheckNull(){
        boolean kg = true;
        if(this.jtID.getText().length() == 0){
            lbStatus.setText("Please enter customer's ID!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(this.jtName.getText().length() == 0){
            lbStatus.setText("Please enter customer's name!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(this.jtPhone.getText().length() == 0){
            lbStatus.setText("Please enter customer's phone!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        return kg;
    }
    
    private void LoadIdCus(){
        String sqlbill = "SELECT top(1) CusID FROM CUSTOMER ORDER BY CusID desc";
        try {
            pst = cn.prepareStatement(sqlbill);
            rs = pst.executeQuery();
            while(rs.next()){
                this.jtID.setText(rs.getString("CusID").trim());
            }
        }catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    public int getMaCus() {
        String sqlcheck = "SELECT CusID FROM CUSTOMER";
        int max = 0;
        ArrayList list = new ArrayList();
        try {
            pst = cn.prepareStatement(sqlcheck);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("CusID").trim().substring(0, 2).endsWith("KH")) {
                    list.add(rs.getString("CusID").trim().substring(2, rs.getString("CusID").trim().length()));
                }
            }
            if (list.size() == 0) {
                return list.size();
            } else {
                for (int i = 0; i < list.size(); i++) {
                    if (max < Integer.parseInt(list.get(i).toString())) {
                        max = Integer.parseInt(list.get(i).toString());
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return max;
    }
    
    public void Crown(){
        if(getMaCus() < 9){
            jtID.setText("KH" + "0" + String.valueOf(getMaCus()+ 1));///chỉnh getmaHD ở trên dạng public int là tự động dưới này nó nhảy theo thứ tự
        }else{
            jtID.setText("KH" + String.valueOf(getMaCus()+ 1));
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtName = new javax.swing.JTextField();
        jtPhone = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtAddress = new javax.swing.JTextField();
        btAdd = new javax.swing.JButton();
        btOut = new javax.swing.JButton();
        lbStatus = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Customer ID");

        jtID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtID.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Customer Name");

        jtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Customer Phone");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Customer Address");

        jtAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btAdd.setText("Add ");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        btOut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btOut.setText("Exits");
        btOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOutActionPerformed(evt);
            }
        });

        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("ADD CUSTOMER");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(btOut, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3))
                            .addGap(26, 26, 26)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jtName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addComponent(jtPhone)
                                .addComponent(jtID)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(18, 18, 18)
                            .addComponent(jtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btOut)
                    .addComponent(btAdd))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
//        int n = JOptionPane.showConfirmDialog(null, "Do you want to add customer?", "Add customer", 0);
//        if(n == JOptionPane.YES_OPTION){
            if (CheckNull()){
                String sqlInsert = "INSERT INTO CUSTOMER (CusID, CusName, Address, Phone) VALUES(?,?,?,?)";
                try{   
                    pst = cn.prepareStatement(sqlInsert);
                    pst.setString(1, jtID.getText());
                    pst.setString(2, jtName.getText());
                    pst.setString(3, jtAddress.getText());
                    pst.setString(4, jtPhone.getText());
                    pst.executeUpdate();
                    lbStatus.setText("Add customer is successfull!");
                    lbStatus.setForeground(Color.BLUE);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
//        }
    }//GEN-LAST:event_btAddActionPerformed

    private void btOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOutActionPerformed
        int n = JOptionPane.showConfirmDialog(null, "Do you want to exits?", "Exits", 0);
        if(n == JOptionPane.YES_OPTION){
            this.setVisible(false);
        }else{
            if(n == JOptionPane.NO_OPTION){
                this.setVisible(true);
            }
        }
    }//GEN-LAST:event_btOutActionPerformed

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
            java.util.logging.Logger.getLogger(AddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddCustomer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jtAddress;
    private javax.swing.JTextField jtID;
    private javax.swing.JTextField jtName;
    private javax.swing.JTextField jtPhone;
    private javax.swing.JLabel lbStatus;
    // End of variables declaration//GEN-END:variables
}
