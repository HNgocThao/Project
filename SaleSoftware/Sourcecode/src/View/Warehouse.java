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
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author OS
 */
public class Warehouse extends javax.swing.JPanel {

    Connection cn;   
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    public Warehouse(Detail d) {
        initComponents();
        connect();
        LoadWarehouse(sql);
        Disable();
        scaleImage();
    }
    private String sql = "SELECT * FROM PRODUCT";
    
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
        btRefresh.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btFind.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btChange.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btStastics.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Statistics.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btUp.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Up.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btDown.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Down.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
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
    private void Disable(){
        jtQuantity.setEnabled(false);
        btChange.setEnabled(false);
        btUp.setEnabled(false);
        btDown.setEnabled(false);
        lbStatus.setText("");
    }
    public boolean CheckNull(){
        boolean kq = true;
        if(String.valueOf(this.jtQuantity.getText()).length()==0){
            lbStatus.setText("Please enter quantity!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        return kq;
    }
    private void LoadWarehouse(String sql){
        tbWare.removeAll();
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
            tbWare.setModel(modle);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
//    private void filter(String query){
//        dm = (DefaultTableModel)tbWare.getModel();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
//        tbWare.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(query));
//    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbWare = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btFind = new javax.swing.JButton();
        btRefresh = new javax.swing.JButton();
        btStastics = new javax.swing.JButton();
        btChange = new javax.swing.JButton();
        btUp = new javax.swing.JButton();
        btDown = new javax.swing.JButton();
        jtQuantity = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtFind = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        tbWare.setModel(new javax.swing.table.DefaultTableModel(
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
        tbWare.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbWareMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbWare);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("WAREHOUSE");

        lbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        btFind.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btFind.setText("Find");
        btFind.setIconTextGap(3);
        btFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindActionPerformed(evt);
            }
        });

        btRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btRefresh.setText("Refesh");
        btRefresh.setIconTextGap(3);
        btRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRefreshActionPerformed(evt);
            }
        });

        btStastics.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btStastics.setText("Statistics");
        btStastics.setIconTextGap(3);
        btStastics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStasticsActionPerformed(evt);
            }
        });

        btChange.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btChange.setText("Change");
        btChange.setIconTextGap(10);
        btChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChangeActionPerformed(evt);
            }
        });

        btUp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btUp.setText("UP");
        btUp.setIconTextGap(10);
        btUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpActionPerformed(evt);
            }
        });

        btDown.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btDown.setText("DOWN");
        btDown.setIconTextGap(10);
        btDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDownActionPerformed(evt);
            }
        });

        jtQuantity.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Quantity");

        jtFind.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtFindKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Find products");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addComponent(jtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btFind, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btStastics, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btChange, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btUp, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btDown, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btRefresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btFind, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btUp, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btDown, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btChange, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(btStastics, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRefreshActionPerformed
        Disable();
        jtFind.setText("");
        LoadWarehouse(sql);
    }//GEN-LAST:event_btRefreshActionPerformed

    private void btChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChangeActionPerformed
        btChange.setEnabled(false);
        
        jtQuantity.setEnabled(true);
        btUp.setEnabled(true);
        btDown.setEnabled(true);
    }//GEN-LAST:event_btChangeActionPerformed
            
    private void btFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindActionPerformed
        String sqlFind = "SELECT Classifyp, ProdID, ProdName, Quantitytotal, Quantity, Unit, Price FROM PRODUCT WHERE Classifyp like N'%"+this.jtFind.getText()+"%' or ProdID like N'%"+this.jtFind.getText()+"%' or ProdName like N'%"+this.jtFind.getText()+"%'";
        LoadWarehouse(sqlFind);
    }//GEN-LAST:event_btFindActionPerformed

    private void btUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpActionPerformed
        int Click = tbWare.getSelectedRow();
        TableModel model = tbWare.getModel();
        String sqlTemp = "SELECT * FROM PRODUCT WHERE ProdID='"+model.getValueAt(Click,0).toString().trim()+"'";
        if (CheckNull()){
            try{
                PreparedStatement pstTemp = cn.prepareStatement(sqlTemp);
                ResultSet rsTemp = pstTemp.executeQuery();
                if(rsTemp.next()){
                    String sqlUpdate = "UPDATE PRODUCT SET Quantitytotal=?,Quantity=? WHERE ProdID='"+rsTemp.getString("ProdID").trim()+"'";
                    try{
                        pst = cn.prepareStatement(sqlUpdate);
                        pst.setInt(1, rsTemp.getInt("Quantitytotal")+Integer.parseInt(jtQuantity.getText()));
                        pst.setInt(2, rsTemp.getInt("Quantity")+Integer.parseInt(jtQuantity.getText()));
                        pst.executeUpdate();
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                String sqll = "SELECT Classifyp, ProdID, ProdName, Quantitytotal, Quantity, Unit, Price FROM PRODUCT WHERE Classifyp like N'%"+this.jtFind.getText()+"%' or ProdID like N'%"+this.jtFind.getText()+"%' or ProdName like N'%"+this.jtFind.getText()+"%'";
                LoadWarehouse(sqll);
                Disable();
                lbStatus.setText("Add product is successfull!");
                lbStatus.setForeground(Color.BLUE);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btUpActionPerformed

    private void btDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDownActionPerformed
        int Click = tbWare.getSelectedRow();
        TableModel model = tbWare.getModel();
        if (CheckNull()){
            try{
            String sqlTemp = "SELECT * FROM PRODUCT WHERE ProdID='"+model.getValueAt(Click,0).toString().trim()+"'";
            PreparedStatement pstTemp = cn.prepareStatement(sqlTemp);
            ResultSet rsTemp = pstTemp.executeQuery();
            if(rsTemp.next()){
                String sqlUpdate = "UPDATE PRODUCT SET Quantitytotal=?,Quantity=? WHERE ProdID='"+rsTemp.getString("ProdID").trim()+"'";
                try{
                    pst = cn.prepareStatement(sqlUpdate);
                    pst.setInt(1, rsTemp.getInt("Quantitytotal")-Integer.parseInt(jtQuantity.getText()));
                    pst.setInt(2, rsTemp.getInt("Quantity")-Integer.parseInt(jtQuantity.getText()));
                    pst.executeUpdate();
                    lbStatus.setForeground(Color.GREEN);
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            String sqll = "SELECT Classifyp, ProdID, ProdName, Quantitytotal, Quantity, Unit, Price FROM PRODUCT WHERE Classifyp like N'%"+this.jtFind.getText()+"%' or ProdID like N'%"+this.jtFind.getText()+"%' or ProdName like N'%"+this.jtFind.getText()+"%'";    
            LoadWarehouse(sqll);
            Disable();
            lbStatus.setText("Reduce product is successfull!");
            lbStatus.setForeground(Color.BLUE);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btDownActionPerformed

    private void tbWareMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbWareMouseClicked
        btChange.setEnabled(true);
        
    }//GEN-LAST:event_tbWareMouseClicked

    private void jtFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtFindKeyReleased
//        String query = jtFind.getText();
//        filter(query);
    }//GEN-LAST:event_jtFindKeyReleased

    private void btStasticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStasticsActionPerformed
        try{
            String query = "SELECT ProdName, (Quantitytotal-Quantity) as tt FROM PRODUCT WHERE Classifyp like N'%"+this.jtFind.getText()+"%' or ProdID like N'%"+this.jtFind.getText()+"%'";
            pst = cn.prepareStatement(query);
            rs = pst.executeQuery();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while(rs.next()){
               dataset.setValue(Integer.parseInt(rs.getString("tt")),"Quantity", rs.getString("ProdName"));
            }
            JFreeChart chart = ChartFactory.createBarChart3D("QUANTITY STATISTICS", "CLASSIFY", "QUANTITY SOLD", dataset, PlotOrientation.VERTICAL, false, true, true);
            chart.setBackgroundPaint(new java.awt.Color(251, 242, 233));
            chart.getTitle().setPaint(Color.GREEN);
            BarRenderer re = null;
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setRangeGridlinePaint(Color.BLACK);
            re = new BarRenderer();
            ChartFrame frame = new ChartFrame("QUANTITY STATISTICS", chart);
            frame.setVisible(true);
            frame.setSize(900,650);
            URL url = getClass().getResource("/Image/Statistics.png");
            ImageIcon img = new ImageIcon(url);
            frame.setIconImage(img.getImage());
            frame.setLocationRelativeTo(null);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btStasticsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btChange;
    private javax.swing.JButton btDown;
    private javax.swing.JButton btFind;
    private javax.swing.JButton btRefresh;
    private javax.swing.JButton btStastics;
    private javax.swing.JButton btUp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtFind;
    private javax.swing.JTextField jtQuantity;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JTable tbWare;
    // End of variables declaration//GEN-END:variables
}
