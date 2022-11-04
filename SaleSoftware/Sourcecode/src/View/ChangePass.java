package View;

import Model.ConnectSQL;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ChangePass extends javax.swing.JFrame {
    
    Connection cn;   
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    public ChangePass() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
//        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        connect();
//        LoadUser();
        Disbled();
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
        setBounds((width - 376) / 2, (height - 310) / 2, 376, 320);
        btOK.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/LOGIN.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        btBack.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Backs.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        setFont(new Font("Verdana", Font.BOLD, 10));
    }
    private void Enabled(){
        jtPass.setEnabled(true);
        jtPass1.setEnabled(true);
        jtPass2.setEnabled(true);
    }
    private void Disbled(){
        jtPass.setEnabled(false);
        jtPass1.setEnabled(false);
        jtPass2.setEnabled(false);
    }
    private void Refresh(){
        jtPass.setText("");
        jtPass1.setText("");
        jtPass2.setText("");
    }
    
//    private void LoadUser(){
//        String sql = "SELECT * FROM EMPLOYEE";
//        try {
//            pst = cn.prepareStatement(sql);
//            rs=pst.executeQuery();
//            while(rs.next()){
//                this.cbName.addItem(rs.getString("UserName").trim());
//            }
//        }  
//        catch (Exception e) {  
//            e.printStackTrace();  
//        }
//    }
    
    private boolean CheckNull(){
        boolean kq = true;
        if(String.valueOf(this.jtUser.getText()).length()==0){
            lbStatus.setText("Please enter your username!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.jtPass.getPassword()).length()==0){
            lbStatus.setText("Please enter your old password!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.jtPass1.getPassword()).length()==0){
            lbStatus.setText("Please enter your new password!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.jtPass2.getPassword()).length()==0){
            lbStatus.setText("Please enter your new password again!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        return kq;
    }
    
    private boolean Check(){
        boolean kq = false;
        String sql = "SELECT * FROM EMPLOYEE WHERE UserName=? AND PassWord=?";
        try {
            pst = cn.prepareStatement(sql);
//            pst.setString(1, (String)cbName.getSelectedItem());
            pst.setString(1, String.valueOf(this.jtUser.getText()));
            pst.setString(2, String.valueOf(this.jtPass.getPassword()));
            rs = pst.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return kq;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel(){
            public void paintComponent(Graphics g){
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(251, 242, 233);
                Color color2 = new Color(251, 242, 233);
                GradientPaint gp = new GradientPaint(0,0,color1,180,height,color2);
                g2d.setPaint(gp);
                g2d.fillRect(0,0,width,height);
            }
        };
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtPass = new javax.swing.JPasswordField();
        jtPass1 = new javax.swing.JPasswordField();
        jtPass2 = new javax.swing.JPasswordField();
        jtUser = new javax.swing.JTextField();
        lbStatus = new javax.swing.JLabel();
        btBack = new javax.swing.JButton();
        btOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CHANGE PASSWORD");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CHANGE PASSWORD");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("User Name:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Old Password:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("New Password:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Repeat new password:");

        jtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtUserKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtPass)
                    .addComponent(jtPass1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(jtPass2)
                    .addComponent(jtUser))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtPass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtPass2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btBack.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btBack.setText("Back");
        btBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBackActionPerformed(evt);
            }
        });

        btOK.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btOK.setText("Agree");
        btOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btOK, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(btBack, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btBack)
                    .addComponent(btOK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOKActionPerformed
        if(CheckNull())
        if(Check()){
            if( String.valueOf(this.jtPass1.getPassword()).equals(String.valueOf(this.jtPass2.getPassword()))){
                String sqlChange = "UPDATE EMPLOYEE SET PassWord=? WHERE UserName=N'"+(String)jtUser.getText()+"'";
                try{
                    pst = cn.prepareStatement(sqlChange);
                    pst.setString(1, String.valueOf(this.jtPass1.getPassword()));
                    pst.executeUpdate();
                    Disbled();
                    lbStatus.setText("Change your password is successfull!");
                    lbStatus.setForeground(Color.BLUE);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }else{
                lbStatus.setText("New password is wrong!");
                lbStatus.setForeground(Color.RED);
            }
        }else{
            lbStatus.setText("Username or old password is wrong!");
            lbStatus.setForeground(Color.RED);
        }
    }//GEN-LAST:event_btOKActionPerformed

    private void btBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBackActionPerformed
        Login lo = new Login();
        this.setVisible(false);
        lo.setVisible(true);
    }//GEN-LAST:event_btBackActionPerformed

    private void jtUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtUserKeyReleased
        Enabled();
    }//GEN-LAST:event_jtUserKeyReleased

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
            java.util.logging.Logger.getLogger(ChangePass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChangePass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChangePass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChangePass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChangePass().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBack;
    private javax.swing.JButton btOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jtPass;
    private javax.swing.JPasswordField jtPass1;
    private javax.swing.JPasswordField jtPass2;
    private javax.swing.JTextField jtUser;
    private javax.swing.JLabel lbStatus;
    // End of variables declaration//GEN-END:variables
}
