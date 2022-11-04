
package View;

import Model.ConnectSQL;
import Model.Detail;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Products extends javax.swing.JPanel {

    Connection cn;   
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    private Boolean Add = false, Change = false;
    private String sql = "SELECT * FROM PRODUCT";
    private String sql1 = "SELECT * FROM CLASSSIFYPRODUCT";
    private String sql2 = "SELECT * FROM PRODUCER";
    DefaultTableModel dm;
    
    private Detail detail;
    
    public Products(Detail d) {
        initComponents();
        scaleImage();
        connect();
        LoadProduct(sql);
        LoadClassifyProduct(sql1);
        LoadProducer(sql2);
        detail = new Detail(d);
        LoadLoai();
        LoadProducer();
        LoadTime();
        Disabled();
        Disabled1();
        Disabled2();
        
    }

//    private void filter(String query){
//        dm = (DefaultTableModel) tbProducts.getModel();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
//        tbProducts.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(query));
//    }
    
    private void connect(){
        try {
            cn = ConnectSQL.getConnectSQL();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void scaleImage(){
        btRefresh.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btAdd.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btChange.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btSave.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btSort.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Sort.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btRefresh1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btAdd1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btChange1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btSave1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btRefresh2.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btAdd2.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btChange2.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btSave2.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
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
    private void Enabled(){
        cbClassifyp.setEnabled(true);
        cbProducer.setEnabled(true);
        jtProdID.setEnabled(true);
        jtProdName.setEnabled(true);
        jtWarranty.setEnabled(true);
        cbDate.setEnabled(true);
        jtUnit.setEnabled(true);
        jtPrice.setEnabled(true);
        lbStatus.setText("");
    }
    private void Enabled1(){
        jtClassify.setEnabled(true);
        jtClassifyID.setEnabled(true);
        lbStatus.setText("");
    } 
    private void Enabled2(){
        jtProcerID.setEnabled(true);
        jtProcerName.setEnabled(true);
        jtAddress.setEnabled(true);
        jtPhone.setEnabled(true);
        jtEmail.setEnabled(true);
        lbStatus.setText("");
    }
    public void Disabled(){
        cbClassifyp.setEnabled(false);
        cbProducer.setEnabled(false);
        jtProdID.setEnabled(false);
        jtProdName.setEnabled(false);
        jtWarranty.setEnabled(false);
        cbDate.setEnabled(false);
        jtUnit.setEnabled(false);
        jtPrice.setEnabled(false);
    }
    private void Disabled1(){
        jtClassify.setEnabled(false);
        jtClassifyID.setEnabled(false);
    }
    private void Disabled2(){
        jtProcerID.setEnabled(false);
        jtProcerName.setEnabled(false);
        jtAddress.setEnabled(false);
        jtPhone.setEnabled(false);
        jtEmail.setEnabled(false);
    }
    
    public void Refresh(){
        Add = false;
        Change = false;
        
        cbClassifyp.removeAllItems();
        cbProducer.removeAllItems();
        jtProdID.setText("");
        jtProdName.setText("");
        jtWarranty.setText("");
        cbDate.removeAllItems();
        jtUnit.setText("");
        jtPrice.setText("");
        btAdd.setEnabled(true);
        btChange.setEnabled(false);
        btSave.setEnabled(false);
        lbStatus.setText("");
        
        jtClassify.setText("");
        jtClassifyID.setText("");
        btAdd1.setEnabled(true);
        btChange1.setEnabled(false);
        btSave1.setEnabled(false);
        lbStatus.setText("");
        
        jtProcerID.setText("");
        jtProcerName.setText("");
        jtAddress.setText("");
        jtPhone.setText("");
        jtEmail.setText("");
        btAdd2.setEnabled(true);
        btChange2.setEnabled(false);
        btSave2.setEnabled(false);
        lbStatus.setText("");
        
        Disabled();
        Disabled1();
        Disabled2();
    }
    
    public void LoadLoai(){
        cbClassifyp.removeAllItems();
        String sql = "SELECT * FROM CLASSSIFYPRODUCT";
        try{
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                this.cbClassifyp.addItem(rs.getString("Classify").trim());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void LoadProducer(){
        cbProducer.removeAllItems();
        String sql = "SELECT * FROM PRODUCER";
        try{
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                this.cbProducer.addItem(rs.getString("ProcerID").trim());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void LoadTime(){
        cbDate.removeAllItems();
        this.cbDate.addItem("Ngày");
        this.cbDate.addItem("Tháng");
        this.cbDate.addItem("Năm");
    }
    
    public boolean Check(){
        boolean kq = true;
        String sqlCheck="SELECT * FROM PRODUCT";
        try{
            pst = cn.prepareStatement(sqlCheck);
            rs = pst.executeQuery();
            while(rs.next()){
                if(this.jtProdID.getText().equals(rs.getString("ProdID").toString().trim())){
                    return false;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    public boolean CheckNull(){
        boolean kq = true;
        if(String.valueOf(this.jtProdID.getText()).length()==0){
            lbStatus.setText("Please enter product's ID!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.jtProdName.getText()).length()==0){
            lbStatus.setText("Please enter product's fullname!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.jtWarranty.getText()).length()==0){
            lbStatus.setText("Please enter product's warranty!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.jtUnit.getText()).length()==0){
            lbStatus.setText("Please enter product's unit!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.jtPrice.getText()).length()==0){
            lbStatus.setText("Please enter product's price!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        return kq;
    }
    private boolean CheckNull1(){
        boolean kq = true;
        if(String.valueOf(this.jtClassify.getText()).length()==0){
            lbStatus.setText("Please enter classify!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        return kq;
    }
    private boolean CheckNull2(){
        boolean kq = true;
        if(String.valueOf(this.jtProcerID.getText()).length()==0){
            lbStatus.setText("Please enter producer's ID!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.jtProcerName.getText()).length()==0){
            lbStatus.setText("Please enter producer's name!");
            lbStatus.setForeground(Color.RED);
            return false;        
        }
        return kq;
    }
    
    private void LoadProduct(String sql){
        tbProducts.removeAll();
        try{
            String [] arr = {"Classify","Producer ID","Product ID","Product Name","Quantity Remaining","Warranty","Unit","Price"};
            DefaultTableModel modle = new DefaultTableModel(arr,0);
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("Classifyp").trim());
                vector.add(rs.getString("ProcerIDp").trim());
                vector.add(rs.getString("ProdID").trim());
                vector.add(rs.getString("ProdName").trim());
                vector.add(rs.getInt("Quantity"));
                vector.add(rs.getInt("Waranty")+" "+rs.getString("SingleTime").trim());
                vector.add(rs.getString("Unit").trim());
                vector.add(rs.getString("Price").trim());
                modle.addRow(vector);
            }
            tbProducts.setModel(modle);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void LoadClassifyProduct(String sql1){
        tbProducts1.removeAll();
        try{
            String [] arr = {"Classify","Classify ID"};
            DefaultTableModel modle = new DefaultTableModel(arr,0);
            pst = cn.prepareStatement(sql1);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("Classify").trim());
                vector.add(rs.getString("ClassifyID").trim());
                modle.addRow(vector);
            }
            tbProducts1.setModel(modle);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void LoadProducer(String sql2){
        tbProducts2.removeAll();
        try{
            String [] arr = {"Producer ID","Producer Name","Address","Phone","Email"};
            DefaultTableModel modle = new DefaultTableModel(arr,0);
            pst = cn.prepareStatement(sql2);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector = new Vector();
                vector.add(rs.getString("ProcerID").trim());
                vector.add(rs.getString("ProcerName").trim());
                vector.add(rs.getString("Address").trim());
                vector.add(rs.getString("Phone").trim());
                vector.add(rs.getString("Email").trim());
                modle.addRow(vector);
            }
            tbProducts2.setModel(modle);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private double conver(String s){
        String number = "";
        String []array = s.replace(","," ").split("\\s");
        for(String i:array){
            number=number.concat(i);
        }
        return Double.parseDouble(number);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbClassifyp = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtProdID = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jtUnit = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cbProducer = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jtProdName = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jtWarranty = new javax.swing.JTextField();
        cbDate = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jtPrice = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        rbClassify = new javax.swing.JRadioButton();
        rbIdProducer = new javax.swing.JRadioButton();
        jPanel16 = new javax.swing.JPanel();
        rbIdProduct = new javax.swing.JRadioButton();
        rbName = new javax.swing.JRadioButton();
        jPanel9 = new javax.swing.JPanel();
        btRefresh = new javax.swing.JButton();
        btAdd = new javax.swing.JButton();
        btChange = new javax.swing.JButton();
        btSort = new javax.swing.JButton();
        btSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProducts = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jtClassify = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jtClassifyID = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        btRefresh1 = new javax.swing.JButton();
        btAdd1 = new javax.swing.JButton();
        btChange1 = new javax.swing.JButton();
        btSave1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbProducts1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jtProcerID = new javax.swing.JTextField();
        jPanel47 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jtAddress = new javax.swing.JTextField();
        jPanel48 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jtPhone = new javax.swing.JTextField();
        jPanel46 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jtProcerName = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jtEmail = new javax.swing.JTextField();
        jPanel45 = new javax.swing.JPanel();
        btRefresh2 = new javax.swing.JButton();
        btAdd2 = new javax.swing.JButton();
        btChange2 = new javax.swing.JButton();
        btSave2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbProducts2 = new javax.swing.JTable();
        lbStatus = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1408, 715));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("PRODUCTS");

        jPanel4.setLayout(new java.awt.GridLayout(3, 3, 20, 5));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Classify");

        cbClassifyp.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbClassifypPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbClassifyp, 0, 180, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbClassifyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("ID Product");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtProdID, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtProdID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel6);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Units");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtUnit, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel8);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("ID Producer");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbProducer, 0, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbProducer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel10);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Name Product");

        jtProdName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtProdNameKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtProdName, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtProdName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel11);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Warranty");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtWarranty, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbDate, 0, 139, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtWarranty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel12);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Price");

        jtPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtPriceKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel13);

        buttonGroup1.add(rbClassify);
        rbClassify.setText("Classify");

        buttonGroup1.add(rbIdProducer);
        rbIdProducer.setText("ID Producer");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(rbClassify)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(rbIdProducer)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbIdProducer)
                    .addComponent(rbClassify))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel15);

        buttonGroup1.add(rbIdProduct);
        rbIdProduct.setText("ID Product");

        buttonGroup1.add(rbName);
        rbName.setText("Name Product");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(rbIdProduct)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbName)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbName)
                    .addComponent(rbIdProduct))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel16);

        jPanel9.setLayout(new java.awt.GridLayout(2, 0, 50, 5));

        btRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btRefresh.setText("Refresh");
        btRefresh.setIconTextGap(15);
        btRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRefreshActionPerformed(evt);
            }
        });
        jPanel9.add(btRefresh);

        btAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btAdd.setText("Add");
        btAdd.setIconTextGap(15);
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });
        jPanel9.add(btAdd);

        btChange.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btChange.setText("Change");
        btChange.setEnabled(false);
        btChange.setIconTextGap(15);
        btChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChangeActionPerformed(evt);
            }
        });
        jPanel9.add(btChange);

        btSort.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btSort.setText("Sort");
        btSort.setIconTextGap(15);
        btSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSortActionPerformed(evt);
            }
        });
        jPanel9.add(btSort);

        btSave.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btSave.setText("Save");
        btSave.setEnabled(false);
        btSave.setIconTextGap(15);
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });
        jPanel9.add(btSave);

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
        tbProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbProducts);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("INFORMATION", jPanel1);

        jPanel26.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Classify ");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jtClassify, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jtClassify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel26.add(jPanel24);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Classify ID");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtClassifyID, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jtClassifyID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel26.add(jPanel25);

        jPanel27.setLayout(new java.awt.GridLayout(1, 0, 50, 5));

        btRefresh1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btRefresh1.setText("Refresh");
        btRefresh1.setIconTextGap(15);
        btRefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRefresh1ActionPerformed(evt);
            }
        });
        jPanel27.add(btRefresh1);

        btAdd1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btAdd1.setText("Add");
        btAdd1.setIconTextGap(15);
        btAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdd1ActionPerformed(evt);
            }
        });
        jPanel27.add(btAdd1);

        btChange1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btChange1.setText("Change");
        btChange1.setEnabled(false);
        btChange1.setIconTextGap(15);
        btChange1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChange1ActionPerformed(evt);
            }
        });
        jPanel27.add(btChange1);

        btSave1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btSave1.setText("Save");
        btSave1.setEnabled(false);
        btSave1.setIconTextGap(15);
        btSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSave1ActionPerformed(evt);
            }
        });
        jPanel27.add(btSave1);

        tbProducts1.setModel(new javax.swing.table.DefaultTableModel(
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
        tbProducts1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProducts1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbProducts1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("UPDATE PRODUCTS", jPanel2);

        jPanel28.setPreferredSize(new java.awt.Dimension(1615, 36));
        jPanel28.setLayout(new java.awt.GridLayout(2, 1, 15, 0));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("ID Producer");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtProcerID, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jtProcerID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel28.add(jPanel29);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("Address");

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel28.add(jPanel47);

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("Phone");

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel28.add(jPanel48);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("Name Producer");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtProcerName, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jtProcerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel28.add(jPanel46);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Email");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel28.add(jPanel30);

        jPanel45.setLayout(new java.awt.GridLayout(1, 0, 15, 5));

        btRefresh2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btRefresh2.setText("Refresh");
        btRefresh2.setIconTextGap(15);
        btRefresh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRefresh2ActionPerformed(evt);
            }
        });
        jPanel45.add(btRefresh2);

        btAdd2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btAdd2.setText("Add");
        btAdd2.setIconTextGap(15);
        btAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdd2ActionPerformed(evt);
            }
        });
        jPanel45.add(btAdd2);

        btChange2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btChange2.setText("Change");
        btChange2.setEnabled(false);
        btChange2.setIconTextGap(15);
        btChange2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChange2ActionPerformed(evt);
            }
        });
        jPanel45.add(btChange2);

        btSave2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btSave2.setText("Save");
        btSave2.setEnabled(false);
        btSave2.setIconTextGap(15);
        btSave2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSave2ActionPerformed(evt);
            }
        });
        jPanel45.add(btSave2);

        tbProducts2.setModel(new javax.swing.table.DefaultTableModel(
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
        tbProducts2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProducts2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbProducts2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("UPDATE PRODUCER", jPanel3);

        lbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 1068, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRefreshActionPerformed
        Refresh();
    }//GEN-LAST:event_btRefreshActionPerformed

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        Refresh();
        Add = true;
        btAdd.setEnabled(false);
        btSave.setEnabled(true);
        Enabled();
        LoadLoai();
        LoadTime();
        LoadProducer();
    }//GEN-LAST:event_btAddActionPerformed

    private void btChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChangeActionPerformed
        Add = false;
        Change = true;
        btAdd.setEnabled(false);
        btChange.setEnabled(false);
        btSave.setEnabled(true);
        Enabled();
    }//GEN-LAST:event_btChangeActionPerformed

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        if(Add == true){
            if(Check()){
                if(CheckNull()){
                    String sqlInsert="INSERT INTO PRODUCT (Classifyp,ProcerIDp,ProdID,ProdName,Waranty,SingleTime,Unit,Price) VALUES(?,?,?,?,?,?,?,?)";
                    try{
                        pst = cn.prepareStatement(sqlInsert);
                        pst.setString(1, String.valueOf(this.cbClassifyp.getSelectedItem()));
                        pst.setString(2,String.valueOf(cbProducer.getSelectedItem()));
                        pst.setString(3, (String)jtProdID.getText());
                        pst.setString(4, (String)jtProdName.getText());
//                        pst.setInt(5, Integer.parseInt(jtQuantity.getText()));
                        pst.setInt(5,Integer.parseInt(jtWarranty.getText()));
                        pst.setString(6, String.valueOf(this.cbDate.getSelectedItem()));
                        pst.setString(7, String.valueOf(this.jtUnit.getText()));
                        pst.setString(8, jtPrice.getText()+" "+"VND");
                        pst.executeUpdate();
                        lbStatus.setText("Add product is successfull!");
                        lbStatus.setForeground(Color.BLUE);
                        Disabled();
                        Refresh();
                        LoadProduct(sql);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }else{
                lbStatus.setText("Can't add product because the product ID you entered already exists!");
                lbStatus.setForeground(Color.RED);
            }
        }else if(Change == true){
            int Click = tbProducts.getSelectedRow();
            TableModel model = tbProducts.getModel();
        
            if(CheckNull()){
                String sqlChange = "UPDATE PRODUCT SET Classifyp=?,ProcerIDp=?,ProdID=?,ProdName=?,Waranty=?,SingleTime=?,Unit=?,Price=? WHERE ProdID='"+model.getValueAt(Click,2).toString().trim()+"'";
                try{
                    pst = cn.prepareStatement(sqlChange);
                    pst.setString(1, String.valueOf(this.cbClassifyp.getSelectedItem()));
                    pst.setString(2,String.valueOf(cbProducer.getSelectedItem()));
                    pst.setString(3, (String)jtProdID.getText());
                    pst.setString(4, (String)jtProdName.getText());
//                    pst.setInt(5, Integer.parseInt(jtQuantity.getText()));
                    pst.setInt(5,Integer.parseInt(jtWarranty.getText()));
                    pst.setString(6, String.valueOf(this.cbDate.getSelectedItem()));
                    pst.setString(7, String.valueOf(this.jtUnit.getText()));
                    pst.setString(8, jtPrice.getText()+" "+"VND");
                    pst.executeUpdate();
                    lbStatus.setText("Save product is successfull!");
                    lbStatus.setForeground(Color.BLUE);
                    Disabled();
                    Refresh();
                    LoadProduct(sql);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btSaveActionPerformed

    private void btSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSortActionPerformed
        if (rbClassify.isSelected() == false && rbIdProducer.isSelected() == false && rbIdProduct.isSelected() == false && rbName.isSelected() == false) {
            lbStatus.setText("You need to choose 1 sort!");
        }else {
            if (rbClassify.isSelected()) {
                String sql = "SELECT * FROM PRODUCT ORDER BY Classifyp";
                LoadProduct(sql);
            } else if (rbIdProducer.isSelected()) {
                String sql = "SELECT * FROM PRODUCT ORDER BY ProcerIDp";
                LoadProduct(sql);
            } else if (rbIdProduct.isSelected()) {
                String sql = "SELECT * FROM PRODUCT ORDER BY ProdID";
                LoadProduct(sql);
            } else if (rbName.isSelected()) {
                String sql = "SELECT * FROM PRODUCT ORDER BY ProdName";
                LoadProduct(sql);
            }
        }
    }//GEN-LAST:event_btSortActionPerformed

    private void jtPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPriceKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        if(jtPrice.getText().equals("")){
            return;
        }else{
            jtPrice.setText(formatter.format(conver(jtPrice.getText())));
        }
    }//GEN-LAST:event_jtPriceKeyReleased

    private void cbClassifypPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbClassifypPopupMenuWillBecomeInvisible
//        jtProdID.setText("");
//        String sql = "SELECT * FROM Products where Classify=?";
//        try {
//            pst = cn.prepareStatement(sql);
//            pst.setString(1, this.cbClassifyp.getSelectedItem().toString());
//            rs = pst.executeQuery();
//            while(rs.next()){
//                jtProdID.setText(rs.getString("IdProduct").trim());
//            }
//        }catch (Exception e) { 
//            e.printStackTrace();  
//        }
    }//GEN-LAST:event_cbClassifypPopupMenuWillBecomeInvisible

    private void tbProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductsMouseClicked
        Refresh();
        int Click = tbProducts.getSelectedRow();
        TableModel model = tbProducts.getModel();
        cbClassifyp.addItem(model.getValueAt(Click,0).toString());
        cbProducer.addItem(model.getValueAt(Click,1).toString());
        jtProdID.setText(model.getValueAt(Click,2).toString());
        jtProdName.setText(model.getValueAt(Click,3).toString());
//        jtQuantity.setText(model.getValueAt(Click,4).toString());
        String [] s = model.getValueAt(Click,5).toString().trim().split("\\s");
        jtWarranty.setText(s[0]);
        cbDate.addItem(s[1]);
        jtUnit.setText(model.getValueAt(Click,6).toString());
        jtPrice.setText(model.getValueAt(Click,7).toString());
        
        btChange.setEnabled(true);
    }//GEN-LAST:event_tbProductsMouseClicked

    private void btRefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRefresh1ActionPerformed
        Refresh();
    }//GEN-LAST:event_btRefresh1ActionPerformed

    private void btAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdd1ActionPerformed
        Refresh();
        Add = true;
        btAdd1.setEnabled(false);
        btSave1.setEnabled(true);
        Enabled1();
    }//GEN-LAST:event_btAdd1ActionPerformed

    private void btChange1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChange1ActionPerformed
        Add = false;
        Change = true;
        btAdd1.setEnabled(false);
        btChange1.setEnabled(false);
        btSave1.setEnabled(true);
        Enabled1();
    }//GEN-LAST:event_btChange1ActionPerformed

    private void btSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSave1ActionPerformed
        if(Add == true){
            if(CheckNull1()){
                String sqlInsert = "INSERT INTO CLASSSIFYPRODUCT (Classify,ClassifyID) VALUES(?,?)";
                try{
                    pst = cn.prepareStatement(sqlInsert);
                    pst.setString(1, jtClassify.getText());
                    pst.setString(2, jtClassifyID.getText());
                    pst.executeUpdate();
                    lbStatus.setText("Add classify is successfull!");
                    lbStatus.setForeground(Color.BLUE);
                    Disabled2();
                    Refresh();
                    LoadClassifyProduct(sql1);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }else{
                lbStatus.setText("Can't add classify because the classify you entered already exists!");
                lbStatus.setForeground(Color.RED);
            }
        }else if(Change == true){
            int Click = tbProducts1.getSelectedRow();
            TableModel model = tbProducts1.getModel();
            if(CheckNull1()){
                String sqlChange = "UPDATE CLASSSIFYPRODUCT SET Classify=?, ClassifyID=? WHERE Classify='"+model.getValueAt(Click,0).toString().trim()+"'";
                try{
                    pst = cn.prepareStatement(sqlChange);
                    pst.setString(1, jtClassify.getText());
                    pst.setString(2, jtClassifyID.getText());
                    pst.executeUpdate();
                    lbStatus.setText("Save classify is successfull!");
                    lbStatus.setForeground(Color.BLUE);
                    Disabled2();
                    Refresh();
                    LoadClassifyProduct(sql1);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btSave1ActionPerformed

    private void tbProducts1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProducts1MouseClicked
        int Click = tbProducts1.getSelectedRow();
        TableModel model = tbProducts1.getModel();
        
        jtClassify.setText(model.getValueAt(Click,0).toString());
        jtClassifyID.setText(model.getValueAt(Click,1).toString());
        
        btChange1.setEnabled(true);
    }//GEN-LAST:event_tbProducts1MouseClicked

    private void btRefresh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRefresh2ActionPerformed
        Refresh();
    }//GEN-LAST:event_btRefresh2ActionPerformed

    private void btAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdd2ActionPerformed
        Refresh();
        Add = true;
        btAdd2.setEnabled(false);
        btSave2.setEnabled(true);
        Enabled2();
    }//GEN-LAST:event_btAdd2ActionPerformed

    private void btChange2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChange2ActionPerformed
        Add = false;
        Change = true;
        btAdd2.setEnabled(false);
        btChange2.setEnabled(false);
        btSave2.setEnabled(true);
        Enabled2();
    }//GEN-LAST:event_btChange2ActionPerformed

    private void btSave2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSave2ActionPerformed
        if(Add == true){
            if(CheckNull2()){
                String sqlInsert = "INSERT INTO PRODUCER (ProcerID,ProcerName,Address,Phone,Email) VALUES(?,?,?,?,?)";
                try{
                    pst = cn.prepareStatement(sqlInsert);
                    pst.setString(1, jtProcerID.getText());
                    pst.setString(2, jtProcerName.getText());
                    pst.setString(3, jtAddress.getText());
                    pst.setString(4, jtPhone.getText());
                    pst.setString(5, jtEmail.getText());
                    pst.executeUpdate();
                    lbStatus.setText("Add producer is successfull!");
                    lbStatus.setForeground(Color.BLUE);
                    Disabled2();
                    Refresh();
                    LoadProducer(sql2);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }else{
                lbStatus.setText("Can't add producer ID because the producer ID you entered already exists!");
                lbStatus.setForeground(Color.RED);
            }
        }else if(Change == true){
            int Click = tbProducts2.getSelectedRow();
            TableModel model = tbProducts2.getModel();
            if(CheckNull2()){
                String sqlChange = "UPDATE PRODUCER SET ProcerID=?, ProcerName=?, Address=?, Phone=?,Email=? WHERE ProcerID='"+model.getValueAt(Click,0).toString().trim()+"'";;
                try{
                    pst = cn.prepareStatement(sqlChange);
                    pst.setString(1, jtProcerID.getText());
                    pst.setString(2, jtProcerName.getText());
                    pst.setString(3, jtAddress.getText());
                    pst.setString(4, jtPhone.getText());
                    pst.setString(5, jtEmail.getText());
                    pst.executeUpdate();
                    lbStatus.setText("Save producer is successfull!");
                    lbStatus.setForeground(Color.BLUE);
                    Disabled2();
                    Refresh();
                    LoadProducer(sql2);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btSave2ActionPerformed

    private void tbProducts2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProducts2MouseClicked
        int Click = tbProducts2.getSelectedRow();
        TableModel model = tbProducts2.getModel();
        
        jtProcerID.setText(model.getValueAt(Click,0).toString());
        jtProcerName.setText(model.getValueAt(Click,1).toString());
        jtAddress.setText(model.getValueAt(Click,2).toString());
        jtPhone.setText(model.getValueAt(Click,3).toString());
        jtEmail.setText(model.getValueAt(Click,4).toString());
        
        btChange2.setEnabled(true);
    }//GEN-LAST:event_tbProducts2MouseClicked

    private void jtProdNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProdNameKeyReleased
//        String query = jtProdName.getText();
//        filter(query);
    }//GEN-LAST:event_jtProdNameKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btAdd1;
    private javax.swing.JButton btAdd2;
    private javax.swing.JButton btChange;
    private javax.swing.JButton btChange1;
    private javax.swing.JButton btChange2;
    private javax.swing.JButton btRefresh;
    private javax.swing.JButton btRefresh1;
    private javax.swing.JButton btRefresh2;
    private javax.swing.JButton btSave;
    private javax.swing.JButton btSave1;
    private javax.swing.JButton btSave2;
    private javax.swing.JButton btSort;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbClassifyp;
    private javax.swing.JComboBox<String> cbDate;
    private javax.swing.JComboBox<String> cbProducer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jtAddress;
    private javax.swing.JTextField jtClassify;
    private javax.swing.JTextField jtClassifyID;
    private javax.swing.JTextField jtEmail;
    private javax.swing.JTextField jtPhone;
    private javax.swing.JTextField jtPrice;
    private javax.swing.JTextField jtProcerID;
    private javax.swing.JTextField jtProcerName;
    private javax.swing.JTextField jtProdID;
    private javax.swing.JTextField jtProdName;
    private javax.swing.JTextField jtUnit;
    private javax.swing.JTextField jtWarranty;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JRadioButton rbClassify;
    private javax.swing.JRadioButton rbIdProducer;
    private javax.swing.JRadioButton rbIdProduct;
    private javax.swing.JRadioButton rbName;
    private javax.swing.JTable tbProducts;
    private javax.swing.JTable tbProducts1;
    private javax.swing.JTable tbProducts2;
    // End of variables declaration//GEN-END:variables
}
