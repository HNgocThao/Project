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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class Sale extends javax.swing.JPanel {
    
    Connection cn;   
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    private Boolean Add = false, Change = false, Pay = false;
    
    private String sql = "SELECT * FROM SALE";
    private String sqlcus = "SELECT CusID, CusName, Phone, Address FROM CUSTOMER";
    private Detail detail;
    
    public Sale(Detail d) {
        initComponents();
        scaleImage();
        connect();
        Disabled();
        LoadLoai();
        deleteInformation();
        LoadSale(sql);
        LoadCustomer(sqlcus);
        detail = new Detail(d);
        setData();
        Pays();
        LoadIDBills();
        LoadID();
        AutoCompleteDecorator.decorate(cbProdName);
    }
    
    private void connect(){
        try {
            cn = ConnectSQL.getConnectSQL();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    private void LoadID(){
        String sql = "SELECT * FROM EMPLOYEE WHERE EmplName=?";
        try {
            pst = cn.prepareStatement(sql);
            pst.setString(1, this.lbName.getText().toString());
            rs = pst.executeQuery();
            while(rs.next()){
                lbEmplID.setText(rs.getString("EmplID").trim());
            }
        }catch (Exception e) {  
            e.printStackTrace();  
        }
    }           
    private void setData(){
        Disabled();
        lbName.setText(detail.getName());
        lbDate1.setText(String.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())));
        lbTime.setText(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date())));
    }
    
    public void scaleImage(){
        btRefresh.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btAdd.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btChange.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btDelete.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btSave.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btNewBill.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Bill.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btPay.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Payment.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btPrint.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Print.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btFind.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        btAddCus.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Backs.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        btNoname.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Account.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
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
//        jtCusID.setEnabled(true);
//        jtCusName.setEnabled(true);
        jtCusPhone.setEnabled(true);
        cbClassify.setEnabled(true);
//        lbStatus.setText("Status!");
    }
    private void Disabled(){
        jtIdBill.setEnabled(false);
        jtCusID.setEnabled(false);
        jtCusName.setEnabled(false);
        jtCusPhone.setEnabled(false);
        cbClassify.setEnabled(false);
        cbProdName.setEnabled(false);
        cbProdID.setEnabled(false);
        jtQuantity.setEnabled(false);
        jtPrice.setEnabled(false);
        jtAmount.setEnabled(false);
        jtGive.setEnabled(false);
    }
    private void Refresh(){
        Add = false;
        Change = false;
        Pay = false;
        cbClassify.removeAllItems();
        cbProdName.removeAllItems();
        cbProdID.removeAllItems();
//        jtCusID.setText("");
//        jtCusName.setText("");
//        jtCusAddress.setText("");
//        jtCusPhone.setText("");
        jtQuantity.setText("");
        jtPrice.setText("");
        jtAmount.setText("");
        lbTotalMoney.setText("0 VND");
        jtGive.setText("");
        lbSurplus.setText("0 VND");
        btAdd.setEnabled(false);
        btChange.setEnabled(false);
        btDelete.setEnabled(false);
        btSave.setEnabled(false);
        btPay.setEnabled(false);
        btNewBill.setEnabled(true);
        Disabled();
    }
    
    private boolean CheckNull(){
        boolean kq = true;
        if(String.valueOf(this.jtCusID.getText()).length()==0){
            lbStatus.setText("Please enter customer's ID!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.jtCusPhone.getText()).length()==0){
            lbStatus.setText("Please enter customer's phone!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.jtQuantity.getText()).length()==0){
            lbStatus.setText("Please enter quantity!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        return kq;
    }
    private boolean CheckNull1(){
        boolean kq = true;
        if(String.valueOf(this.jtCusID.getText()).length()==0){
            lbStatus.setText("Please enter customer's ID!");
            return false;
        }
        if(String.valueOf(this.jtCusName.getText()).length()==0){
            lbStatus.setText("Please enter customer's name!");
            return false;
        }
        if(String.valueOf(this.jtQuantity.getText()).length()==0){
            lbStatus.setText("Please enter quantityyyyyy!");
            return false;
        }
        return kq;
    }
    private void LoadIDBills(){
        String sqlbill = "SELECT top(1) BillID FROM BILL ORDER BY BillID desc";
        try {
            pst = cn.prepareStatement(sqlbill);
            rs = pst.executeQuery();
            while(rs.next()){
                this.jtIdBill.setText(rs.getString("BillID").trim());
            }
        }catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    private void LoadLoai(){
        String sql = "SELECT * FROM CLASSSIFYPRODUCT";
        try {
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                this.cbClassify.addItem(rs.getString("Classify").trim());
            }
        }catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    private void LoadSale(String sql){
        tbSale.removeAll();
        try{
            String [] arr = {"ID Bill","Product ID","Classify","Product Name","Quantity","Price","Amount"};
            DefaultTableModel modle = new DefaultTableModel(arr,0);
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("BillIDb").trim());
                vector.add(rs.getString("ProdIDb").trim());
                vector.add(rs.getString("Classifyp").trim());
                vector.add(rs.getString("ProdName").trim());
                vector.add(rs.getInt("Quantity"));
                vector.add(rs.getString("Price").trim());
                vector.add(rs.getString("Amount").trim());
                modle.addRow(vector);
            }
            tbSale.setModel(modle);
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
                modle.addRow(vector);
            }
            tbCus.setModel(modle);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void Pays(){
        lbTotalMoney.setText("0 VND");
        String sqlPay="SELECT * FROM SALE";
        try{
            pst = cn.prepareStatement(sqlPay);
            rs = pst.executeQuery();
            while(rs.next()){
                String []s1 = rs.getString("Amount").toString().trim().split("\\s");
                String []s2 = lbTotalMoney.getText().split("\\s");
                double totalMoney = conver(s1[0])+ conver(s2[0]);
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                lbTotalMoney.setText(formatter.format(totalMoney)+" "+s1[1]);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private double conver(String s){
        String number = "";
        String [] array = s.replace(","," ").split("\\s");//spilit tach chuoi dua tren khoang trang
        for(String i : array){//in chuỗi đã tách theo hàng dọc
            number = number.concat(i);//concat() nối thêm chuỗi được chỉ định vào cuối chuỗi đã cho
        }
        return Double.parseDouble(number);
    }
    
    private void adddetailbill(){
        String sqldetail = "INSERT INTO DETAIL_BILL SELECT BillIDb,ProdIDb,Quantity,Price,Amount FROM SALE";
//        LoadSale(sqldetail);
        try{
            pst = cn.prepareStatement(sqldetail);
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(1, rs.getString(String.valueOf(jtIdBill.getName())));
                vector.add(2, rs.getString(String.valueOf(cbProdID.getName())));
                vector.add(3, rs.getString(String.valueOf(jtQuantity.getName())));
                vector.add(4, rs.getString(String.valueOf(jtPrice.getName())));
                vector.add(5, rs.getString(String.valueOf(jtAmount.getName())));
            }
                pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void deleteInformation(){
        String sqlDelete="DELETE FROM SALE";
        try{
            pst = cn.prepareStatement(sqlDelete);
            pst.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public int getMaHD() {
        String sqlcheck = "SELECT BillID FROM BILL";
        int max = 0;
        ArrayList list = new ArrayList();
        try {
            pst = cn.prepareStatement(sqlcheck);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("BillID").trim().substring(0, 2).endsWith("HĐ")) {
                    list.add(rs.getString("BillID").trim().substring(2, rs.getString("BillID").trim().length()));
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
    private void QuantityRemaining(){
        String sqlBill = "SELECT * FROM SALE";
        try{
            PreparedStatement pstBill = cn.prepareStatement(sqlBill);
            ResultSet rsBill = pstBill.executeQuery();
            while(rsBill.next()){
                try{
                    String sqlTemp = "SELECT * FROM PRODUCT WHERE ProdID ='"+rsBill.getString("ProdIDb")+"'";
                    PreparedStatement pstTemp = cn.prepareStatement(sqlTemp);
                    ResultSet rsTemp = pstTemp.executeQuery();
                    if(rsTemp.next()){
                        String sqlUpdate = "UPDATE PRODUCT SET Quantity=? WHERE ProdID='"+rsBill.getString("ProdIDb").trim()+"'";
                        try{
                            pst = cn.prepareStatement(sqlUpdate);
                            pst.setInt(1, rsTemp.getInt("Quantity")-rsBill.getInt("Quantity"));
                            pst.executeUpdate();
                        }catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }  
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtIdBill = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jtPrice = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbClassify = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jtQuantity = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbProdName = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jtAmount = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        cbProdID = new javax.swing.JComboBox<>();
        jPanel24 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        lbDate1 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        btAddCus = new javax.swing.JButton();
        btNoname = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jtCusPhone = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jtCusName = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jtCusID = new javax.swing.JTextField();
        jPanel3 = new jPanelGradient();
        jPanel15 = new javax.swing.JPanel();
        lbTotalMoney = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jtGive = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        lbSurplus = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lbTime = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSale = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        btFind = new javax.swing.JButton();
        btRefresh = new javax.swing.JButton();
        btNewBill = new javax.swing.JButton();
        btAdd = new javax.swing.JButton();
        btChange = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();
        btSave = new javax.swing.JButton();
        btPay = new javax.swing.JButton();
        btPrint = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        lbEmplID = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbCus = new javax.swing.JTable();
        jPanel2 = new jPanelGradient();
        jLabel2 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1408, 715));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("SALE PRODUCTS");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new java.awt.GridLayout(4, 2, 2, 0));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("ID Bill");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtIdBill, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtIdBill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel7);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Price");

        jtPrice.setEnabled(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel9);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Classify");

        cbClassify.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbClassifyPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbClassify, 0, 269, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbClassify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Quantity");

        jtQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtQuantityActionPerformed(evt);
            }
        });
        jtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtQuantityKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel8);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Name Product");

        cbProdName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbProdNamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbProdName, 0, 275, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbProdName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Amount");

        jtAmount.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("ID Product");

        cbProdID.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbProdIDPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbProdID, 0, 269, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cbProdID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel23);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Date");

        lbDate1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbDate1.setText("Date");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(lbDate1))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel24);

        jPanel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel20.setLayout(new java.awt.GridLayout(4, 1, 20, 0));

        btAddCus.setText("Add customer");
        btAddCus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddCusActionPerformed(evt);
            }
        });

        btNoname.setText("No name");
        btNoname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNonameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btAddCus, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btNoname, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAddCus)
                    .addComponent(btNoname))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel18);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Customer Phone");

        jtCusPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtCusPhoneKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(jtCusPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jtCusPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel22);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Customer Name ");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jtCusName, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jtCusName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel21);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Customer ID  ");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jtCusID, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jtCusID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel19);

        jPanel3.setLayout(new java.awt.GridLayout(4, 0, 10, 0));

        lbTotalMoney.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTotalMoney.setText("0 VND");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Total Bill Amount");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTotalMoney, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lbTotalMoney))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel15);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Customer's money");

        jtGive.setEnabled(false);
        jtGive.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtGiveKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtGive)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jtGive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel16);

        lbSurplus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbSurplus.setText("0 VND");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Surplus");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbSurplus, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(lbSurplus))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel17);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Time");

        lbTime.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbTime.setText("Time");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTime, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lbTime))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel14);

        lbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tbSale.setModel(new javax.swing.table.DefaultTableModel(
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
        tbSale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSaleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbSale);

        jPanel10.setLayout(new java.awt.GridLayout(1, 0, 5, 10));

        btFind.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btFind.setText("Find Customer");
        btFind.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btFind.setIconTextGap(5);
        btFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindActionPerformed(evt);
            }
        });
        jPanel10.add(btFind);

        btRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btRefresh.setText("Refresh");
        btRefresh.setIconTextGap(20);
        btRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRefreshActionPerformed(evt);
            }
        });
        jPanel10.add(btRefresh);

        btNewBill.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btNewBill.setText("New Bill");
        btNewBill.setIconTextGap(20);
        btNewBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNewBillActionPerformed(evt);
            }
        });
        jPanel10.add(btNewBill);

        btAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btAdd.setText("Add");
        btAdd.setEnabled(false);
        btAdd.setIconTextGap(20);
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });
        jPanel10.add(btAdd);

        btChange.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btChange.setText("Change");
        btChange.setEnabled(false);
        btChange.setIconTextGap(20);
        btChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChangeActionPerformed(evt);
            }
        });
        jPanel10.add(btChange);

        btDelete.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btDelete.setText("Delete");
        btDelete.setEnabled(false);
        btDelete.setIconTextGap(20);
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });
        jPanel10.add(btDelete);

        btSave.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btSave.setText("Save");
        btSave.setEnabled(false);
        btSave.setIconTextGap(20);
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });
        jPanel10.add(btSave);

        btPay.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btPay.setText("Payment");
        btPay.setEnabled(false);
        btPay.setIconTextGap(20);
        btPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPayActionPerformed(evt);
            }
        });
        jPanel10.add(btPay);

        btPrint.setText("Print");
        btPrint.setIconTextGap(20);
        btPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPrintActionPerformed(evt);
            }
        });
        jPanel10.add(btPrint);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel11.setText("ID Name");

        lbEmplID.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lbEmplID.setText("ID");

        lbName.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lbName.setText("Name");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel9.setText("Name");

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
        tbCus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCusMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbCus);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TABLE CUSTOMER VIP");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbEmplID, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(lbEmplID))
                    .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    private void btRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRefreshActionPerformed
        Refresh();
        deleteInformation();
        LoadSale(sql);
        LoadCustomer(sqlcus);
    }//GEN-LAST:event_btRefreshActionPerformed

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        Refresh();
        Add = true;
        btAdd.setEnabled(false);
        btSave.setEnabled(true);
        Enabled();
        LoadLoai();
    }//GEN-LAST:event_btAddActionPerformed

    private void btChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChangeActionPerformed
        Add = false;
        Change = true;
        btAdd.setEnabled(false);
        btChange.setEnabled(false);
        btDelete.setEnabled(false);
        btPay.setEnabled(false);
        btSave.setEnabled(true);
        Enabled();
        LoadLoai();
    }//GEN-LAST:event_btChangeActionPerformed

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Do you want to remove the product from the bill?", "Delete",0);
        if(Click == JOptionPane.YES_OPTION){
            String sqlDelete = "DELETE FROM SALE WHERE ProdIDb = ?";
            try{
                pst = cn.prepareStatement(sqlDelete);
                pst.setString(1, String.valueOf(cbProdID.getSelectedItem()));
                pst.executeUpdate();
                this.lbStatus.setText("Delete product is successfull!");
                lbStatus.setForeground(Color.BLUE);
                Refresh();
                LoadSale(sql);
//                Pays();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btDeleteActionPerformed

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        if(Add == true){
            if(CheckNull()){
                String sqlInsert = "INSERT INTO SALE (BillIDb,ProdIDb,Classifyp,ProdName,Quantity,Price,Amount) VALUES(?,?,?,?,?,?,?)";
                try{
                    pst = cn.prepareStatement(sqlInsert);
                    pst.setString(1, jtIdBill.getText());
                    pst.setString(2, String.valueOf(cbProdID.getSelectedItem()));
                    pst.setString(3, String.valueOf(cbClassify.getSelectedItem()));
                    pst.setString(4, String.valueOf(cbProdName.getSelectedItem()));
                    pst.setInt(5, Integer.parseInt(jtQuantity.getText()));
                    pst.setString(6, jtPrice.getText());
                    pst.setString(7, jtAmount.getText());
                    pst.executeUpdate();
                    lbStatus.setText("Add order is successfull!");
                    lbStatus.setForeground(Color.BLUE);
                    Refresh();
                    LoadSale(sql);
                    btPay.setEnabled(true);
                    btAdd.setEnabled(true);
                    jtGive.setEnabled(true);
                }catch(Exception ex){
                   ex.printStackTrace();
                }   
            }
        }else if(Change == true){
            int Click = tbSale.getSelectedRow();
            TableModel model = tbSale.getModel();
            if(CheckNull()){
                String sqlChange = "UPDATE SALE SET BillIDb=?,ProdIDb=?,Classifyp=?,ProdName=?,Quantity=?,Price=?,Amount=? WHERE ProdIDb='"+model.getValueAt(Click,1).toString().trim()+"'"; 
                try{
                    pst = cn.prepareStatement(sqlChange);
                    pst.setString(1, (String)jtIdBill.getText());
                    pst.setString(2, String.valueOf(cbProdID.getSelectedItem()));
                    pst.setString(3, String.valueOf(cbClassify.getSelectedItem()));
                    pst.setString(4, String.valueOf(cbProdName.getSelectedItem()));
                    pst.setInt(5, Integer.parseInt(jtQuantity.getText()));
                    pst.setString(6, jtPrice.getText());
                    pst.setString(7, jtAmount.getText());
                    pst.executeUpdate();
                    Refresh();
                    lbStatus.setText("Save order is successfull!");
                    lbStatus.setForeground(Color.BLUE);
                    LoadSale(sql);
                    btPay.setEnabled(true);
                    jtGive.setEnabled(true);
                    btAdd.setEnabled(true);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        Pays();
    }//GEN-LAST:event_btSaveActionPerformed

    private void btNewBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNewBillActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Do you want to create a new bill?", "Delete",0);
        if(Click == JOptionPane.YES_OPTION){
            String sqlDelete = "DELETE FROM SALE";
            try{
                pst = cn.prepareStatement(sqlDelete);
                pst.executeUpdate();
                if(getMaHD() < 9){
                    jtIdBill.setText("HĐ" + "0" + String.valueOf(getMaHD()+ 1));///chỉnh getmaHD ở trên dạng public int là tự động dưới này nó nhảy theo thứ tự
                }else{
                    jtIdBill.setText("HĐ" + String.valueOf(getMaHD()+ 1));
                }
                Refresh();
                btAdd.setEnabled(true);
                btNewBill.setEnabled(false);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btNewBillActionPerformed

    private void btPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPayActionPerformed
            
        if(Pay == true){
            String sqlPay = "INSERT INTO BILL (BillID,EmplID,CusIDb,BillDate,BillTime,TotalAmount) VALUES(?,?,?,?,?,?)";
            String []s = lbTotalMoney.getText().split("\\s");
            try{
                pst = cn.prepareStatement(sqlPay);
                
                pst.setString(1, jtIdBill.getText());
                pst.setString(2, lbEmplID.getText());
                pst.setString(3, jtCusID.getText());
                pst.setDate(4,new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(lbDate1.getText()).getTime()));
                pst.setString(5, lbTime.getText());
                pst.setString(6, lbTotalMoney.getText());
                pst.executeUpdate();
                lbStatus.setText("Payment is successfull!");
                lbStatus.setForeground(Color.BLUE);
                QuantityRemaining();
                adddetailbill();
//                addcustomer();
                Refresh();
//                btNewBill.setEnabled(true);
//                btPay.setEnabled(false);
//                jtGive.setEnabled(false);
            }catch(Exception ex){
                ex.printStackTrace();
            }
//            deleteInformation();
        }else if(Pay == false){
            JOptionPane.showMessageDialog(null, "You need to insert customer's money!");
            lbStatus.setForeground(Color.RED);
        }
    }//GEN-LAST:event_btPayActionPerformed

    private void jtQuantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtQuantityKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        if(jtQuantity.getText().equals("")){
            String []s = jtPrice.getText().split("\\s");
            jtAmount.setText("0"+" "+s[1]);
        }else{
            String sqlCheck = "SELECT Quantity FROM PRODUCT WHERE ProdID='"+cbProdID.getSelectedItem()+"'";
            try{
            pst = cn.prepareStatement(sqlCheck);
            rs = pst.executeQuery();
                while(rs.next()){
                    if((rs.getInt("Quantity")-Integer.parseInt(jtQuantity.getText()))<0){
                        String []s = jtPrice.getText().split("\\s");
                        jtAmount.setText("0"+" "+s[1]);
                        lbStatus.setText("The number of products sold cann't exceed the number of goods in stock!");
                        lbStatus.setForeground(Color.RED);
                        btSave.setEnabled(false);
                    }else{
                        int soluong=Integer.parseInt(jtQuantity.getText().toString());
                        String []s = jtPrice.getText().split("\\s");
                        jtAmount.setText(formatter.format(conver(s[0])*soluong)+" "+s[1]);
                        lbStatus.setText("Product's quantity for sale is valid!");
                        lbStatus.setForeground(Color.BLUE);
                        btSave.setEnabled(true);
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jtQuantityKeyReleased

    private void jtGiveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtGiveKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        if(jtGive.getText().equals("")){
            String []s = lbTotalMoney.getText().split("\\s");
            lbSurplus.setText("0"+" "+s[1]);
        }
        else{
            jtGive.setText(formatter.format(conver(jtGive.getText())));
            
            String s1 = jtGive.getText();
            String[] s2=lbTotalMoney.getText().split("\\s");
            if((conver(s1)-conver(s2[0]))>=0){
                lbSurplus.setText(formatter.format((conver(s1)-conver(s2[0])))+" "+s2[1]);
                lbStatus.setText("The amount given by the customer is valid!");
                lbStatus.setForeground(Color.BLUE);
                Pay = true;
            }else {
                lbSurplus.setText(formatter.format((conver(s1)-conver(s2[0])))+" "+s2[1]);
                lbStatus.setText("The customer's money gives is less than the total purchase amount in the invoice!");
                lbStatus.setForeground(Color.RED);
                Pay = false;
            }
        }
    }//GEN-LAST:event_jtGiveKeyReleased

    private void cbClassifyPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbClassifyPopupMenuWillBecomeInvisible
        cbProdName.removeAllItems();
        String sql2 = "SELECT * FROM PRODUCT where Classifyp=?";
        try {
            pst = cn.prepareStatement(sql2);
            pst.setString(1, this.cbClassify.getSelectedItem().toString());
            rs = pst.executeQuery();
            while(rs.next()){
                this.cbProdName.addItem(rs.getString("ProdName").trim());
                cbProdName.setEnabled(true);
            }
        }catch (Exception e) {  
            e.printStackTrace(); 
        }
    }//GEN-LAST:event_cbClassifyPopupMenuWillBecomeInvisible

    private void cbProdNamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbProdNamePopupMenuWillBecomeInvisible
        cbProdID.removeAllItems();
        String sql3 = "SELECT * FROM PRODUCT where ProdName=? and Classifyp=?";
        try {
            pst = cn.prepareStatement(sql3);
            pst.setString(1, this.cbProdName.getSelectedItem().toString());
            pst.setString(2, this.cbClassify.getSelectedItem().toString());
            rs = pst.executeQuery();
            while(rs.next()){
                this.cbProdID.addItem(rs.getString("ProdID").trim());
                jtPrice.setText(rs.getString("Price").trim());
                jtQuantity.setEnabled(true);
//                cbProdID.setEnabled(true);
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
        //Kiểm tra xem sản phẩm còn hàng không
        String sqlCheck="SELECT Quantity FROM PRODUCT WHERE ProdID='" + cbProdID.getSelectedItem()+"'";
        try{
            pst = cn.prepareCall(sqlCheck);
            rs = pst.executeQuery();
            while(rs.next()){
                if(rs.getInt("Quantity")== 0){
                    lbStatus.setText("The product is sold!!");
                    lbStatus.setForeground(Color.RED);
                    btSave.setEnabled(false);
                    jtQuantity.setEnabled(false);
                }else{
                    lbStatus.setText("The item has "+rs.getInt("Quantity")+" products!!");
                    lbStatus.setForeground(Color.BLUE);
                    btSave.setEnabled(true);
                    jtQuantity.setEnabled(true);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cbProdNamePopupMenuWillBecomeInvisible

    private void tbSaleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSaleMouseClicked
        cbClassify.removeAllItems();
        cbProdName.removeAllItems();
        
        int Click = tbSale.getSelectedRow();
        TableModel model = tbSale.getModel();
        
        jtIdBill.setText(model.getValueAt(Click,0).toString());
        cbProdID.addItem(model.getValueAt(Click,1).toString());
        cbClassify.addItem(model.getValueAt(Click,2).toString());
        cbProdName.addItem(model.getValueAt(Click,3).toString());
        jtQuantity.setText(model.getValueAt(Click,4).toString());
        jtPrice.setText(model.getValueAt(Click,5).toString());
        jtAmount.setText(model.getValueAt(Click,6).toString());
        
        btChange.setEnabled(true);
        btDelete.setEnabled(true);
    }//GEN-LAST:event_tbSaleMouseClicked

    private void cbProdIDPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbProdIDPopupMenuWillBecomeInvisible
//        String sql5 = "SELECT * FROM PRODUCT where ProdID=?";
//        try {
//            pst = cn.prepareStatement(sql5);
//            pst.setString(1, this.cbProdID.getSelectedItem().toString());
//            rs = pst.executeQuery();
//            while(rs.next()){
//                jtPrice.setText(rs.getString("Price").trim());
//                jtQuantity.setEnabled(true);
//            }
//        }  
//        catch (Exception e) {  
//            e.printStackTrace();  
//        }
        
    }//GEN-LAST:event_cbProdIDPopupMenuWillBecomeInvisible

    private void jtCusPhoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtCusPhoneKeyReleased
//        DefaultTableModel table = (DefaultTableModel)tbCus.getModel();
//        String search = jtCusPhone.getText();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
//        tbCus.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_jtCusPhoneKeyReleased

    private void tbCusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCusMouseClicked
        int Click1 = tbCus.getSelectedRow();
        TableModel model = tbCus.getModel();
        
        jtCusID.setText(model.getValueAt(Click1,0).toString());
        jtCusName.setText(model.getValueAt(Click1,1).toString());
        jtCusPhone.setText(model.getValueAt(Click1,2).toString());
        
    }//GEN-LAST:event_tbCusMouseClicked

    private void btFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindActionPerformed
        String sqlFind = "SELECT * FROM CUSTOMER WHERE Phone like N'%"+this.jtCusPhone.getText()+"%'";
        LoadCustomer(sqlFind);
    }//GEN-LAST:event_btFindActionPerformed

    private void btAddCusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddCusActionPerformed
        AddCustomer acc = new AddCustomer();
        this.setVisible(true);
        acc.setVisible(true);
    }//GEN-LAST:event_btAddCusActionPerformed

    private void btNonameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNonameActionPerformed
        String sqlFind = "SELECT * FROM CUSTOMER WHERE CusID like N'KH00'";
        LoadCustomer(sqlFind);
    }//GEN-LAST:event_btNonameActionPerformed

    private void btPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPrintActionPerformed
        try{
            JasperDesign jas = JRXmlLoader.load("src/Report/ReportBill.jrxml");
            String sql = "SELECT BillIDb, Classifyp, ProdName, Quantity, Price, Amount, TotalAmount as TTAmount, BillDate, BillTime, EmplName, CusName FROM SALE join BILL on SALE.BillIDb = BILL.BillID join CUSTOMER on BILL.CusIDb = CUSTOMER.CusID join EMPLOYEE on BILL.EmplID = EMPLOYEE.EmplID";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            JRDesignQuery newQ = new JRDesignQuery();
            newQ.setText(sql);
            jas.setQuery(newQ);
//            HashMap<String, Object> has = new HashMap<>();
//            has.put("TotalAmount", lbTotalMoney.getText());
            JasperReport js = JasperCompileManager.compileReport(jas);
            JasperPrint jp = JasperFillManager.fillReport(js, null,cn);
            JasperViewer.viewReport(jp, false);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btPrintActionPerformed

    private void jtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtQuantityActionPerformed
    }//GEN-LAST:event_jtQuantityActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btAddCus;
    private javax.swing.JButton btChange;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btFind;
    private javax.swing.JButton btNewBill;
    private javax.swing.JButton btNoname;
    private javax.swing.JButton btPay;
    private javax.swing.JButton btPrint;
    private javax.swing.JButton btRefresh;
    private javax.swing.JButton btSave;
    private javax.swing.JComboBox<String> cbClassify;
    private javax.swing.JComboBox<String> cbProdID;
    private javax.swing.JComboBox<String> cbProdName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jtAmount;
    private javax.swing.JTextField jtCusID;
    private javax.swing.JTextField jtCusName;
    private javax.swing.JTextField jtCusPhone;
    private javax.swing.JTextField jtGive;
    private javax.swing.JTextField jtIdBill;
    private javax.swing.JTextField jtPrice;
    private javax.swing.JTextField jtQuantity;
    private javax.swing.JLabel lbDate1;
    private javax.swing.JLabel lbEmplID;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbSurplus;
    private javax.swing.JLabel lbTime;
    private javax.swing.JLabel lbTotalMoney;
    private javax.swing.JTable tbCus;
    private javax.swing.JTable tbSale;
    // End of variables declaration//GEN-END:variables
}
