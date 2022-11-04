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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Employees extends javax.swing.JPanel {
    
    Connection cn;   
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    private Boolean Add = false, Change = false;
    private String sql = "SELECT EmplID,EmplName,UserName,EMPLOYEE.Positione,Salary,DayOfBirth,Address,Gender,Phone,Email "
                        + "FROM EMPLOYEE join POSITION on EMPLOYEE.Positione = POSITION.Positionp";
    String sql1 = "SELECT * FROM POSITION";
    private String sql2 = "SELECT EmplID, EmplName, UserName, PassWord, Positione, Phone, Salary FROM EMPLOYEE join POSITION on EMPLOYEE.Positione = POSITION.Positionp";
    private Detail detail;
    
    public Employees(Detail d) {
        initComponents();
        scaleImage();
        connect();
        detail = new Detail(d);
        LoadEmployees(sql);
        LoadPosition(sql1);
        LoadAccount(sql2);
        ChoosePosition();
        LoadSex();
        
    }
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
        btRefresh1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btAdd1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btChange1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btSave1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
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
            setOpaque(false);
            g2d.fillRect(0,0,width,height);
        }
    }
    private void Enabled() {
        jtEmplID.setEnabled(true);
        jtEmplName.setEnabled(true);
//        jtUser.setEnabled(true);
        cbPosition.setEnabled(true);
        dcDate.setEnabled(true);
        jtAddress.setEnabled(true);
        cbGender.setEnabled(true);
        jtPhone.setEnabled(true);
        jtEmail.setEnabled(true);
    }
    private void EnabledPosition(){
        jtPosition1.setEnabled(true);
        jtSalary1.setEnabled(true);
    }
    
    private void Disabled() {
        jtEmplID.setEnabled(false);
        jtEmplName.setEnabled(false);
        jtUser.setEnabled(false);
        jtPass.setEnabled(false);
        cbPosition.setEnabled(false);
        dcDate.setEnabled(false);
        jtAddress.setEnabled(false);
        cbGender.setEnabled(false);
        jtPhone.setEnabled(false);
        jtEmail.setEnabled(false);
    }
    private void DisabledPosition(){
        jtPosition1.setEnabled(false);
        jtSalary1.setEnabled(false);
    }
    
    private void Refresh() {
        Add = false;
        Change = false;
        
        jtEmplID.setText("");
        jtEmplName.setText("");
        jtUser.setText("");
        jtPass.setText("");
        cbPosition.removeAllItems();
        ((JTextField) dcDate.getDateEditor().getUiComponent()).setText("");
        jtAddress.setText("");
        cbGender.removeAllItems();
        jtPhone.setText("");
        jtEmail.setText("");
        btAdd.setEnabled(true);
        btChange.setEnabled(false);
        btSave.setEnabled(false);
        Disabled();
//        ChoosePosition();
//        LoadSex();
        jtPosition1.setText("");
        jtSalary1.setText("");
        btAdd1.setEnabled(true);
        btChange1.setEnabled(false);
        btSave1.setEnabled(false);
        DisabledPosition();
    }
    private void ChoosePosition(){
        cbPosition.removeAllItems();
        String sqlcbxPosition = "SELECT * FROM POSITION";
        try {
            pst = cn.prepareStatement(sqlcbxPosition);
            rs = pst.executeQuery();
            while(rs.next()){
                this.cbPosition.addItem(rs.getString("Positionp").trim());
            }
        }catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    private void LoadSex(){
        cbGender.removeAllItems();
        cbGender.addItem("Male");
        cbGender.addItem("Female");
        cbGender.addItem("3D");
    }
    private boolean Check(){
        boolean kq = true;
        String sqlCheck = "SELECT * FROM EMPLOYEE";
        try{
            pst = cn.prepareStatement(sqlCheck);
            rs = pst.executeQuery();
            while(rs.next()){
                if(this.jtUser.getText().equals(rs.getString("EmplID").toString().trim())){
                    return false;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    private boolean CheckNull() {
        boolean kq = true;
        if (this.jtEmplName.getText().equals("")) {
            lbStatus.setText("Please enter your fullname!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if (this.jtUser.getText().equals("")) {
            lbStatus.setText("Please enter your user!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if (this.jtPass.getText().equals("")) {
            lbStatus.setText("Please enter your pass!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if (((JTextField) dcDate.getDateEditor().getUiComponent()).getText().equals("")) {
            lbStatus.setText("Please enter your birthday!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
//        if (this.jtAddress.getText().equals("")) {
//            lbStatus.setText("Please enter your address!");
//            lbStatus.setForeground(Color.RED);
//            return false;
//        }
        return kq;
    }
    private boolean CheckNullchange() {
        boolean kq = true;
        if (this.jtEmplName.getText().equals("")) {
            lbStatus.setText("Please enter your fullname!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if (this.jtAddress.getText().equals("")) {
            lbStatus.setText("Please enter your address!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if (((JTextField) dcDate.getDateEditor().getUiComponent()).getText().equals("")) {
            lbStatus.setText("Please enter your birthday!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        return kq;
    }
    private boolean CheckNullPosition(){
        boolean kq = true;
        if(String.valueOf(this.jtPosition1.getText()).length() == 0){
            lbStatus.setText("Please enter position!");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        if(String.valueOf(this.jtSalary1.getText()).length() == 0){
            lbStatus.setText("Please enter salary");
            lbStatus.setForeground(Color.RED);
            return false;
        }
        return kq;
    }
    
    
    private void LoadEmployees(String sql){
        try{
            String [] arr = {"Employee ID","Employee Name","User Name","Position","Day of Birth","Address","Gender","Phone","Email"};
            DefaultTableModel modle = new DefaultTableModel(arr,0);
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("EmplID").trim());
                vector.add(rs.getString("EmplName").trim());
                vector.add(rs.getString("UserName").trim());
                vector.add(rs.getString("Positione").trim());
                vector.add(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DayOfBirth")));
                vector.add(rs.getString("Address").trim());
                vector.add(rs.getString("Gender").trim());
                vector.add(rs.getString("Phone").trim());
                vector.add(rs.getString("Email").trim());
                modle.addRow(vector);
            }
            tbEmployees.setModel(modle);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void LoadPosition(String sql){
        tbPosition.removeAll();
        try{
            String [] arr = {"Position","Salary"};
            DefaultTableModel modle = new DefaultTableModel(arr,0);
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("Positionp").trim());
                vector.add(rs.getString("Salary").trim());
                modle.addRow(vector);
            }
            tbPosition.setModel(modle);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void LoadAccount(String sql2){
        try{
            String [] arr = {"Employee ID","Employee Name","User Name","Pass Word","Position","Phone","Salary"};
            DefaultTableModel modle = new DefaultTableModel(arr,0);
            pst = cn.prepareStatement(sql2);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("EmplID").trim());
                vector.add(rs.getString("EmplName").trim());
                vector.add(rs.getString("UserName").trim());
                vector.add(rs.getString("PassWord").trim());
                vector.add(rs.getString("Positione").trim());
                vector.add(rs.getString("Phone").trim());
                vector.add(rs.getString("Salary").trim());
                modle.addRow(vector);
            }
            tbAccounts.setModel(modle);
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jtEmplID = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jtUser = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbPosition = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jtAddress = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtPhone = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jtEmplName = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jtPass = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        dcDate = new com.toedter.calendar.JDateChooser();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cbGender = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jtEmail = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btRefresh = new javax.swing.JButton();
        btAdd = new javax.swing.JButton();
        btChange = new javax.swing.JButton();
        btSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbEmployees = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jtPosition1 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jtSalary1 = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        btRefresh1 = new javax.swing.JButton();
        btAdd1 = new javax.swing.JButton();
        btChange1 = new javax.swing.JButton();
        btSave1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPosition = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbAccounts = new javax.swing.JTable();
        lbStatus = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1408, 715));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("EMPLOYEES");

        jPanel1.setPreferredSize(new java.awt.Dimension(1408, 715));

        jPanel3.setLayout(new java.awt.GridLayout(2, 5, 5, 0));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("     EmplID");

        jtEmplID.setEnabled(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtEmplID, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtEmplID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel9);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("UserName");

        jtUser.setEnabled(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtUser, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel10);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("       Position");

        cbPosition.setEnabled(false);
        cbPosition.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbPositionPopupMenuWillBecomeInvisible(evt);
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
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbPosition, 0, 181, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Address");

        jtAddress.setEnabled(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel7);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Phone");

        jtPhone.setEnabled(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel8);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("EmplName");

        jtEmplName.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtEmplName, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtEmplName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel6);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("PassWord");

        jtPass.setEnabled(false);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(15, 15, 15)
                .addComponent(jtPass, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel19);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Date of Birth");

        dcDate.setEnabled(false);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcDate, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel11);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Gender");

        cbGender.setEnabled(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(16, 16, 16)
                .addComponent(cbGender, 0, 203, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel12);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Email");

        jtEmail.setEnabled(false);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel13);

        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        btRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btRefresh.setText("Refresh");
        btRefresh.setIconTextGap(20);
        btRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRefreshActionPerformed(evt);
            }
        });
        jPanel4.add(btRefresh);

        btAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btAdd.setText("Add");
        btAdd.setIconTextGap(20);
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });
        jPanel4.add(btAdd);

        btChange.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btChange.setText("Change");
        btChange.setEnabled(false);
        btChange.setIconTextGap(20);
        btChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChangeActionPerformed(evt);
            }
        });
        jPanel4.add(btChange);

        btSave.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btSave.setText("Save");
        btSave.setEnabled(false);
        btSave.setIconTextGap(20);
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });
        jPanel4.add(btSave);

        tbEmployees.setModel(new javax.swing.table.DefaultTableModel(
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
        tbEmployees.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbEmployeesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbEmployees);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1402, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("INFORMATION", jPanel1);

        jPanel14.setLayout(new java.awt.GridLayout(1, 2, 20, 10));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Position");

        jtPosition1.setEnabled(false);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jtPosition1)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jtPosition1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.add(jPanel17);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Salary");

        jtSalary1.setEnabled(false);
        jtSalary1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtSalary1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jtSalary1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jtSalary1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.add(jPanel18);

        jPanel15.setLayout(new java.awt.GridLayout(1, 0, 50, 0));

        btRefresh1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btRefresh1.setText("Refresh");
        btRefresh1.setIconTextGap(20);
        btRefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRefresh1ActionPerformed(evt);
            }
        });
        jPanel15.add(btRefresh1);

        btAdd1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btAdd1.setText("Add");
        btAdd1.setIconTextGap(20);
        btAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdd1ActionPerformed(evt);
            }
        });
        jPanel15.add(btAdd1);

        btChange1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btChange1.setText("Change");
        btChange1.setEnabled(false);
        btChange1.setIconTextGap(20);
        btChange1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChange1ActionPerformed(evt);
            }
        });
        jPanel15.add(btChange1);

        btSave1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btSave1.setText("Save");
        btSave1.setEnabled(false);
        btSave1.setIconTextGap(20);
        btSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSave1ActionPerformed(evt);
            }
        });
        jPanel15.add(btSave1);

        tbPosition.setModel(new javax.swing.table.DefaultTableModel(
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
        tbPosition.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPositionMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbPosition);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("UPDATE POSITION", jPanel2);

        tbAccounts.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tbAccounts);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1396, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("ACCOUNTS", jPanel16);

        lbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbPositionPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbPositionPopupMenuWillBecomeInvisible
//        String sql = "SELECT * FROM Positionemp where Position=?";
//        try {
//            pst = cn.prepareStatement(sql);
//            pst.setString(1, this.cbPosition.getSelectedItem().toString());
//            rs = pst.executeQuery();
//            while(rs.next()){
//                jtEmplID.setText(rs.getString("Salary").trim());
//            }
//        }  
//        catch (Exception e) {  
//            e.printStackTrace();  
//        }
    }//GEN-LAST:event_cbPositionPopupMenuWillBecomeInvisible

    private void tbEmployeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEmployeesMouseClicked
//        cbPosition.removeAllItems();
//        cbGender.removeAllItems();
        Refresh();
        int Click = tbEmployees.getSelectedRow();
        TableModel model = tbEmployees.getModel();
        
        jtEmplID.setText(model.getValueAt(Click,0).toString());
        jtEmplName.setText(model.getValueAt(Click,1).toString());
        jtUser.setText(model.getValueAt(Click,2).toString());
//        jtPass.setText(model.getValueAt(Click,3).toString());
        cbPosition.addItem(model.getValueAt(Click,3).toString());
        ((JTextField)dcDate.getDateEditor().getUiComponent()).setText(model.getValueAt(Click,4).toString());
        jtAddress.setText(model.getValueAt(Click,5).toString());
        cbGender.addItem(model.getValueAt(Click,6).toString());
        jtPhone.setText(model.getValueAt(Click,7).toString());
        jtEmail.setText(model.getValueAt(Click,8).toString());
        ChoosePosition();
        LoadSex();
//        btDelete.setEnabled(true);
        btChange.setEnabled(true);
    }//GEN-LAST:event_tbEmployeesMouseClicked

    private void btRefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRefresh1ActionPerformed
        Refresh();
    }//GEN-LAST:event_btRefresh1ActionPerformed

    private void btAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdd1ActionPerformed
        Refresh();
        Add = true;
        btAdd1.setEnabled(false);
        btSave1.setEnabled(true);
        EnabledPosition();
    }//GEN-LAST:event_btAdd1ActionPerformed

    private void btChange1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChange1ActionPerformed
        Add = false;
        Change = true;
        btAdd1.setEnabled(false);
        btChange1.setEnabled(false);
        btSave1.setEnabled(true);
        EnabledPosition();
    }//GEN-LAST:event_btChange1ActionPerformed

    private void btSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSave1ActionPerformed
        if(Add == true){
            if(Check()){
                if(CheckNullPosition()){
                    String sqlInsert="INSERT INTO POSITION (Positionp,Salary) VALUES(?,?)";
                    try{
                        pst = cn.prepareStatement(sqlInsert);
                        pst.setString(1, jtPosition1.getText());
                        pst.setString(2, jtSalary1.getText()+" "+"VND");
                        pst.executeUpdate();
                        lbStatus.setText("Add position is successfull!");
                        lbStatus.setForeground(Color.BLUE);
                        DisabledPosition();
                        Refresh();
                        LoadPosition(sql1);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }else{
                lbStatus.setText("Can't add position ID because the position ID you entered already exists!");
                lbStatus.setForeground(Color.RED);
            }
        }else if (Change == true){
            int Click = tbPosition.getSelectedRow();
            TableModel model = tbPosition.getModel();

            if(CheckNullPosition()){
                String sqlChange="UPDATE POSITION SET Positionp=?,Salary=? WHERE Position='"+model.getValueAt(Click,0).toString().trim()+"'";
                try{
                    pst = cn.prepareStatement(sqlChange);
                    pst.setString(1, jtPosition1.getText());
                    pst.setString(2, jtSalary1.getText()+" "+"VND");
                    pst.executeUpdate();
                    lbStatus.setText("Save position is successfull!");
                    lbStatus.setForeground(Color.BLUE);
                    DisabledPosition();
                    Refresh();
                    LoadPosition(sql1);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btSave1ActionPerformed

    private void tbPositionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPositionMouseClicked
        int Click = tbPosition.getSelectedRow();
        TableModel model = tbPosition.getModel();
        
        jtPosition1.setText(model.getValueAt(Click,0).toString());
        String []s = model.getValueAt(Click,1).toString().split("\\s");
        jtSalary1.setText(s[0]);
        
        btChange1.setEnabled(true);
    }//GEN-LAST:event_tbPositionMouseClicked

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        if(Add == true){
            if(Check()){
                if(CheckNull()){
                    String sqlInsert="INSERT INTO EMPLOYEE (EmplID,EmplName,UserName,Password,Positione,DayOfBirth,Address,Gender,Phone,Email) VALUES(?,?,?,?,?,?,?,?,?,?)";
                    try{
                        pst = cn.prepareStatement(sqlInsert);
                        pst.setString(1, jtEmplID.getText());
                        pst.setString(2, jtEmplName.getText());
                        pst.setString(3, jtUser.getText());
                        pst.setString(4, jtPass.getText());
                        pst.setString(5, (String)cbPosition.getSelectedItem());
                        pst.setDate(6,new java.sql.Date(this.dcDate.getDate().getTime()));
                        pst.setString(7, jtAddress.getText());
                        pst.setString(8, (String)cbGender.getSelectedItem());
                        pst.setString(9, jtPhone.getText());
                        pst.setString(10, jtEmail.getText());
                        pst.executeUpdate();
                        lbStatus.setText("Add employee is successfull!");
                        lbStatus.setForeground(Color.BLUE);
                        Disabled();
                        Refresh();
                        LoadEmployees(sql);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }else{
                lbStatus.setText("Can't add employee because the employee ID you entered already exists!");
                lbStatus.setForeground(Color.RED);
            }
        }else if(Change == true){
            int Click = tbEmployees.getSelectedRow();
            TableModel model = tbEmployees.getModel();

            if(CheckNullchange()){
                String sqlChange = "UPDATE EMPLOYEE SET EmplID=?,EmplName=?,UserName=?,PassWord=?,Positione=?,DayOfBirth=?,Address=?,Gender=?,Phone=?,Email=? WHERE EmplID='"+model.getValueAt(Click,0).toString().trim()+"'";;
                try{
                    pst = cn.prepareStatement(sqlChange);
                    pst.setString(1, this.jtEmplID.getText());
                    pst.setString(2, jtEmplName.getText());
                    pst.setString(3, jtUser.getText());
                    pst.setString(4, jtPass.getText());
                    pst.setString(5, (String)cbPosition.getSelectedItem());
                    pst.setDate(6,new java.sql.Date(this.dcDate.getDate().getTime()));
                    pst.setString(7, jtAddress.getText());
                    pst.setString(8, (String)cbGender.getSelectedItem());
                    pst.setString(9, jtPhone.getText());
                    pst.setString(10, jtEmail.getText());
                    pst.executeUpdate();
                    lbStatus.setText("Save employee is successfull!");
                    lbStatus.setForeground(Color.BLUE);
                    Disabled();
                    Refresh();
                    LoadEmployees(sql);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btSaveActionPerformed

    private void btChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChangeActionPerformed
        Add = false;
        Change = true;
        btAdd.setEnabled(false);
        btChange.setEnabled(false);
        btSave.setEnabled(true);
        Enabled();

        ((JTextField) dcDate.getDateEditor().getUiComponent()).setText("");
    }//GEN-LAST:event_btChangeActionPerformed

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        Refresh();
        Add = true;
        jtUser.setEnabled(true);
        jtPass.setEnabled(true);
        btAdd.setEnabled(false);
        btSave.setEnabled(true);
        Enabled();
        ChoosePosition();
        LoadSex();
    }//GEN-LAST:event_btAddActionPerformed

    private void btRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRefreshActionPerformed
        Refresh();
    }//GEN-LAST:event_btRefreshActionPerformed

    private void jtSalary1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtSalary1KeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        if(jtSalary1.getText().equals("")){
            return;
        }else{
            jtSalary1.setText(formatter.format(conver(jtSalary1.getText())));
        }
    }//GEN-LAST:event_jtSalary1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btAdd1;
    private javax.swing.JButton btChange;
    private javax.swing.JButton btChange1;
    private javax.swing.JButton btRefresh;
    private javax.swing.JButton btRefresh1;
    private javax.swing.JButton btSave;
    private javax.swing.JButton btSave1;
    private javax.swing.JComboBox<String> cbGender;
    private javax.swing.JComboBox<String> cbPosition;
    private com.toedter.calendar.JDateChooser dcDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jtAddress;
    private javax.swing.JTextField jtEmail;
    private javax.swing.JTextField jtEmplID;
    private javax.swing.JTextField jtEmplName;
    private javax.swing.JTextField jtPass;
    private javax.swing.JTextField jtPhone;
    private javax.swing.JTextField jtPosition1;
    private javax.swing.JTextField jtSalary1;
    private javax.swing.JTextField jtUser;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JTable tbAccounts;
    private javax.swing.JTable tbEmployees;
    private javax.swing.JTable tbPosition;
    // End of variables declaration//GEN-END:variables
}
