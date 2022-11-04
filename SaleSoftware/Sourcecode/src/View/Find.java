/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.ConnectSQL;
import Model.Detail;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author OS
 */
public class Find extends javax.swing.JPanel {

    Connection cn;   
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    public Find(Detail d) {
        initComponents();
        connect();
        LoadProducts(sql);
        LoadCustomer(sqlcus);
        scaleImage();
    }
    
    private Boolean Add = false, Change = false;
    private String sql = "SELECT * FROM PRODUCT ORDER BY Quantity";
    private String sqlcus = "SELECT * FROM CUSTOMER";
    
    DefaultTableModel dm;
    private Detail detail;
    
    private void connect(){
        try {
            cn = ConnectSQL.getConnectSQL();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void scaleImage(){
        btFind.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btFind1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btPrint.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Print.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btPrint1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Print.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
    }
    
    private void filter(String query){
        dm = (DefaultTableModel)tbProducts.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        tbProducts.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    private void filter1(String query){
        dm = (DefaultTableModel)tbCus.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        tbCus.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    class jPanelGradient extends JPanel{
        public void paintComponent(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            Color color1 = new Color(191,211,231);
            Color color2 = new Color(191,211,231);
            GradientPaint gp = new GradientPaint(0,0,color1,180,height,color2);
            g2d.setPaint(gp);
            g2d.fillRect(0,0,width,height);
        }
    }
    private void LoadProducts(String sql){
        tbProducts.removeAll();
        try{
            String [] arr = {"Product ID","Classify","Product Name","Quantity Total","Quantity Remaining","Unit","Price"};
            DefaultTableModel modle = new DefaultTableModel(arr,0);
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("ProdID").trim());
                vector.add(rs.getString("Classifyp").trim());
                vector.add(rs.getString("ProdName").trim());
                vector.add(rs.getInt("Quantitytotal"));
                vector.add(rs.getInt("Quantity"));
                vector.add(rs.getString("Unit").trim());
                vector.add(rs.getString("Price").trim());
                modle.addRow(vector);
            }
            tbProducts.setModel(modle);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void LoadCustomer(String sqlcus){
        tbCus.removeAll();
        try{
            String [] arr = {"Customer ID","Customer Name","Customer Phone","Customer Address"};
            DefaultTableModel modle = new DefaultTableModel(arr,0);
            pst = cn.prepareStatement(sqlcus);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("CusID").trim());
                vector.add(rs.getString("CusName").trim());
                vector.add(rs.getString("Phone").trim());
                vector.add(rs.getString("Address").trim());
//                vector.add(rs.getString("TotalAmount").trim());
                modle.addRow(vector);
            }
            tbCus.setModel(modle);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtFind = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProducts = new javax.swing.JTable();
        btFind = new javax.swing.JButton();
        btPrint = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jtFind1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbCus = new javax.swing.JTable();
        btFind1 = new javax.swing.JButton();
        btPrint1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("FIND");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Find products");

        jtFind.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtFindKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(26, 26, 26)
                .addComponent(jtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        tbProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbProducts);

        btFind.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btFind.setText("Find");
        btFind.setIconTextGap(10);
        btFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindActionPerformed(evt);
            }
        });

        btPrint.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btPrint.setText("Print");
        btPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1559, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btFind, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btFind, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("PRODUCTS", jPanel1);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Find customer");

        jtFind1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtFind1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtFind1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(22, 22, 22)
                .addComponent(jtFind1, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtFind1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        tbCus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tbCus);

        btFind1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btFind1.setText("Find");
        btFind1.setIconTextGap(10);
        btFind1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFind1ActionPerformed(evt);
            }
        });

        btPrint1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btPrint1.setText("Print");
        btPrint1.setIconTextGap(10);
        btPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPrint1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btFind1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(723, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btFind1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CUSTOMER", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jtFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtFindKeyReleased
        String query = jtFind.getText();
        filter(query);
    }//GEN-LAST:event_jtFindKeyReleased

    private void jtFind1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtFind1KeyReleased
        String query = jtFind1.getText();
        filter1(query);
    }//GEN-LAST:event_jtFind1KeyReleased

    private void btFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindActionPerformed
//        String sqlFind = "SELECT ProdID, Classifyp, ProdName, Quantitytotal, Quantity, Unit, Price FROM PRODUCT WHERE Classifyp like N'%"+this.jtFind.getText()+"%' or ProdID like N'%"+this.jtFind.getText()+"%' or ProdName like N'%"+this.jtFind.getText()+"%'";
//        LoadProducts(sqlFind);
    }//GEN-LAST:event_btFindActionPerformed

    private void btFind1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFind1ActionPerformed
//        String sqlFind = "SELECT * FROM CUSTOMER WHERE CusID like N'%"+this.jtFind1.getText()+"%' or CusName like N'%"+this.jtFind1.getText()+"%' or Phone like N'%"+this.jtFind1.getText()+"%' or Address like N'%"+this.jtFind1.getText()+"%'";
//        LoadCustomer(sqlFind);
    }//GEN-LAST:event_btFind1ActionPerformed

    private void btPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPrint1ActionPerformed
        try{
            JasperDesign jas = JRXmlLoader.load("src/Report/ReportCustomer.jrxml");
            String sql = "SELECT * FROM CUSTOMER WHERE CusID like N'%"+this.jtFind1.getText()+"%' or CusName like N'%"+this.jtFind1.getText()+"%' or Phone like N'%"+this.jtFind1.getText()+"%' or Address like N'%"+this.jtFind1.getText()+"%'";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            JRDesignQuery newQ = new JRDesignQuery();
            newQ.setText(sql);
            jas.setQuery(newQ);
            
            JasperReport js = JasperCompileManager.compileReport(jas);
            JasperPrint jp = JasperFillManager.fillReport(js, null, cn);
            JasperViewer.viewReport(jp, false);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btPrint1ActionPerformed

    private void btPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPrintActionPerformed
        try{
            JasperDesign jas = JRXmlLoader.load("src/Report/ReportProduct.jrxml");
            String sql = "SELECT * FROM PRODUCT WHERE Classifyp like N'%"+this.jtFind.getText()+"%' or ProdID like N'%"+this.jtFind.getText()+"%' or ProdName like N'%"+this.jtFind.getText()+"%' ORDER BY Quantity";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            JRDesignQuery newQ = new JRDesignQuery();
            newQ.setText(sql);
            jas.setQuery(newQ);
            
            JasperReport js = JasperCompileManager.compileReport(jas);
            JasperPrint jp = JasperFillManager.fillReport(js, null, cn);
            JasperViewer.viewReport(jp, false);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btFind;
    private javax.swing.JButton btFind1;
    private javax.swing.JButton btPrint;
    private javax.swing.JButton btPrint1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jtFind;
    private javax.swing.JTextField jtFind1;
    private javax.swing.JTable tbCus;
    private javax.swing.JTable tbProducts;
    // End of variables declaration//GEN-END:variables
}
