/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.ConnectSQL;
import Model.Detail;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author OS
 */
public class Login extends javax.swing.JFrame {
    
    Connection cn;   
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    public Login() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
//        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        connect();
        setFrameStyle();
        Icon();
        lbStatus.setForeground(Color.GREEN);
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
        setBounds((width - 630) / 2, (height - 300) / 2, 630, 300);
        Login.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/LOGIN.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        Logout.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/EXITS.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        Changepass.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/LOGOUT.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        jtPic.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Anhdai.jpg")).getImage().getScaledInstance(220,220, Image.SCALE_SMOOTH)));
        setFont(new Font("Verdana", Font.BOLD, 10));
    }
    private boolean CheckNull(){
        boolean kg = true;
        if(this.userName.getText().length() == 0){
            lbStatus.setText("Please enter your username!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.passWord.getText()).length() == 0){
            lbStatus.setText("Please enter your password!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        return kg;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userName = new javax.swing.JTextField();
        Logout = new javax.swing.JButton();
        lbPass = new javax.swing.JLabel();
        passWord = new javax.swing.JPasswordField();
        lbUser = new javax.swing.JLabel();
        Login = new javax.swing.JButton();
        Changepass = new javax.swing.JButton();
        jtPic = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOG IN");

        userName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        Logout.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Logout.setText("Exits");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        lbPass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbPass.setText("Password");

        passWord.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lbUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbUser.setText("User");

        Login.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Login.setText("Log In");
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });

        Changepass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Changepass.setText("Change Pass");
        Changepass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangepassActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LOG IN");

        lbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jtPic, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbPass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passWord, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(Changepass, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(Logout, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                        .addGap(15, 15, 15))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbUser))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(passWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbPass, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Changepass)
                            .addComponent(Login)
                            .addComponent(Logout)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jtPic, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ChangepassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangepassActionPerformed
        ChangePass change = new ChangePass();
        this.setVisible(false);
        change.setVisible(true);
    }//GEN-LAST:event_ChangepassActionPerformed

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
        String sql = "SELECT * FROM EMPLOYEE WHERE UserName=? and PassWord=?";
        if (CheckNull())
        try{
            pst = cn.prepareStatement(sql);
            pst.setString(1,this.userName.getText());
            pst.setString(2, String.valueOf(this.passWord.getPassword()));
            rs = pst.executeQuery();
            if (rs.next()){
                Detail detail = new Detail(rs.getString("UserName").trim(), rs.getString("EmplName").trim());
                if(rs.getString("Username").trim().toString().equals("Admin")){
                    Home ho = new Home(detail);
                    this.setVisible(false);
                    ho.setVisible(true);
                }
                else{
                    HomeUsers ho = new HomeUsers(detail);
                    this.setVisible(false);
                    ho.setVisible(true);
                }
            }else{
                lbStatus.setText("Oops! This doesn't match our records!");
                lbStatus.setForeground(Color.RED);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_LoginActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        int n = JOptionPane.showConfirmDialog(null, "Do you want to out program?", "Exits", 0);
        if(n == JOptionPane.YES_OPTION){
            System.exit(0);
        }else{
            if(n == JOptionPane.NO_OPTION){
                this.setVisible(true);
            }
        }
    }//GEN-LAST:event_LogoutActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Changepass;
    private javax.swing.JButton Login;
    private javax.swing.JButton Logout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jtPic;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbUser;
    private javax.swing.JPasswordField passWord;
    private javax.swing.JTextField userName;
    // End of variables declaration//GEN-END:variables
}
