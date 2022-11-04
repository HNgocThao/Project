
package View;

import Model.Detail;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Home extends javax.swing.JFrame implements Runnable{
    private Detail detail;
    private Thread thread;
    Connection cn;   
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    public Home(Detail d) {
        initComponents();
        setFrameStyle();
        detail = new Detail(d);
        Icon();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        CoverImage();
        lbName.setText(detail.getName());
        Start();
    }
    public void Icon(){
        URL url = getClass().getResource("/Image/Item.jpg");
        ImageIcon img = new ImageIcon(url);
        super.setIconImage(img.getImage());
    }
    public void CoverImage(){
        Coverimage cov = new Coverimage(detail);
        SplitPane.setRightComponent(cov);
    }        
    private void setFrameStyle() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;

        setBounds((width - 1630) / 2, (height - 750) / 2, 1550, 750);
        btWarehouse.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/WAREHOUSE.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        btEmployees.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/EMPLOYEES.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        btRevenue.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/REVENUES.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        btProducts.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/PRODUCTS.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        btSale.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/SALES.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        btFind.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/FINDS.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        btLogout.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/LOGOUT.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
//        jtPic.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Anh.jpg")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH)));
        setFont(new Font("Verdana", Font.BOLD, 10));
        SplitPane.setRightComponent(Pan2);
    }
    
    public String getName(String ID) {
        String arry = new String();
        String sql = "SELECT * FROM EMPLOYEE WHERE EmplID=?";
        try {
            pst = cn.prepareStatement(sql);
            pst.setString(1, ID);
            rs = pst.executeQuery();
            while (rs.next()) {
                arry = rs.getString("EmplName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arry;
    }
    
    private void Start(){
        if(thread==null){
            thread = new Thread(this);
            thread.start();
        }
    }
    @Override
    public void run() {
        long FPS = 10;
        long period = 100*1000/FPS;
        long beginTime,sleepTime;
        
        beginTime=System.nanoTime();
        while(true){
//            lbName.setForeground(Color.BLUE);
            lbName.setLocation(lbName.getX()-1, lbName.getY());
            if(lbName.getX()+lbName.getWidth()<0){
                lbName.setLocation(this.getWidth()-800, lbName.getY());
            }
            
            long deltaTime = System.nanoTime()-beginTime;
            sleepTime = period - deltaTime;
            try{
                if(sleepTime > 0)
                    Thread.sleep(sleepTime/1500);
                else    Thread.sleep(period/3000);
                
            }catch(Exception ex){
                ex.printStackTrace();
            }
            beginTime = System.nanoTime();
        }
//        String txt = lbName.getText();
//        while (true){
//            txt = txt.charAt(txt.length()-1) + txt.substring(0, txt.length()-1);
//            try{
//                sleep(1000);
//            }catch(Exception ex){
//                ex.printStackTrace();
//            }
//            lbName.setText(txt);
//        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SplitPane = new javax.swing.JSplitPane();
        Pan1 = new javax.swing.JPanel(){
            public void paintComponent(Graphics g){
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(52,143,80);
                Color color2 = new Color(86,180,211);
                GradientPaint gp = new GradientPaint(0,0,color1,180,height,color2);
                g2d.setPaint(gp);
                g2d.fillRect(0,0,width,height);
            }
        };
        btEmployees = new javax.swing.JButton();
        btProducts = new javax.swing.JButton();
        btWarehouse = new javax.swing.JButton();
        btRevenue = new javax.swing.JButton();
        btSale = new javax.swing.JButton();
        btFind = new javax.swing.JButton();
        btLogout = new javax.swing.JButton();
        Pan2 = new javax.swing.JPanel();
        jtPic = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Perfumed's Store Management");

        Pan1.setLayout(new java.awt.GridLayout(0, 1, 0, 15));

        btEmployees.setText("Employees");
        btEmployees.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btEmployees.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btEmployees.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btEmployees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEmployeesActionPerformed(evt);
            }
        });
        Pan1.add(btEmployees);

        btProducts.setText("Products");
        btProducts.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btProducts.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btProducts.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProductsActionPerformed(evt);
            }
        });
        Pan1.add(btProducts);

        btWarehouse.setText("Warehouse");
        btWarehouse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btWarehouse.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btWarehouse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btWarehouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btWarehouseActionPerformed(evt);
            }
        });
        Pan1.add(btWarehouse);

        btRevenue.setText("Revenue");
        btRevenue.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btRevenue.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btRevenue.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btRevenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRevenueActionPerformed(evt);
            }
        });
        Pan1.add(btRevenue);

        btSale.setText("Sale");
        btSale.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btSale.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btSale.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaleActionPerformed(evt);
            }
        });
        Pan1.add(btSale);

        btFind.setText("Find");
        btFind.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btFind.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btFind.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindActionPerformed(evt);
            }
        });
        Pan1.add(btFind);

        btLogout.setText("Log Out");
        btLogout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btLogout.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btLogout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLogoutActionPerformed(evt);
            }
        });
        Pan1.add(btLogout);

        SplitPane.setLeftComponent(Pan1);

        jtPic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout Pan2Layout = new javax.swing.GroupLayout(Pan2);
        Pan2.setLayout(Pan2Layout);
        Pan2Layout.setHorizontalGroup(
            Pan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pan2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtPic, javax.swing.GroupLayout.DEFAULT_SIZE, 1524, Short.MAX_VALUE)
                .addContainerGap())
        );
        Pan2Layout.setVerticalGroup(
            Pan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtPic, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
        );

        SplitPane.setRightComponent(Pan2);

        lbName.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lbName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbName.setText("Username");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SplitPane, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SplitPane)
                .addGap(0, 0, 0)
                .addComponent(lbName)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btWarehouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btWarehouseActionPerformed
        Warehouse acc = new Warehouse(detail);
        SplitPane.setRightComponent(acc);
    }//GEN-LAST:event_btWarehouseActionPerformed

    private void btEmployeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEmployeesActionPerformed
        Employees emp = new Employees(detail);
        SplitPane.setRightComponent(emp);
    }//GEN-LAST:event_btEmployeesActionPerformed

    private void btRevenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRevenueActionPerformed
        Revenue rev = new Revenue(detail);
        SplitPane.setRightComponent(rev);
    }//GEN-LAST:event_btRevenueActionPerformed

    private void btProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProductsActionPerformed
        Products pro = new Products(detail);
        SplitPane.setRightComponent(pro);
    }//GEN-LAST:event_btProductsActionPerformed

    private void btSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaleActionPerformed
        Sale sal = new Sale(detail);
        SplitPane.setRightComponent(sal);
    }//GEN-LAST:event_btSaleActionPerformed

    private void btFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindActionPerformed
        Find find = new Find(detail);
        SplitPane.setRightComponent(find);
    }//GEN-LAST:event_btFindActionPerformed

    private void btLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLogoutActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Do you want to log out of your account from the system??", "Log out",0);
        if(Click == JOptionPane.YES_OPTION){
            Login lo = new Login();
            this.setVisible(false);
            lo.setVisible(true);
        }
    }//GEN-LAST:event_btLogoutActionPerformed

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail = new Detail();
                new Home(detail).setVisible(true);
            }
            
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pan1;
    private javax.swing.JPanel Pan2;
    private javax.swing.JSplitPane SplitPane;
    private javax.swing.JButton btEmployees;
    private javax.swing.JButton btFind;
    private javax.swing.JButton btLogout;
    private javax.swing.JButton btProducts;
    private javax.swing.JButton btRevenue;
    private javax.swing.JButton btSale;
    private javax.swing.JButton btWarehouse;
    private javax.swing.JLabel jtPic;
    private javax.swing.JLabel lbName;
    // End of variables declaration//GEN-END:variables
}
