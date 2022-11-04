
package View;

import Model.ConnectSQL;
import Model.Detail;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;

public class Revenue extends javax.swing.JPanel {

    Connection cn;   
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    private Boolean Add = false, Change = false;
    private String sql = "SELECT BillID,EmplName,CusIDb,CusName,TotalAmount,BillDate,BillTime FROM BILL join EMPLOYEE on BILL.EmplID = EMPLOYEE.EmplID join CUSTOMER on BILL.CusIDb = CUSTOMER.CusID ORDER BY BillID DESC";
    private String sql1 = "SELECT BillIDb,ProdIDb,Classifyp,ProdName,DETAIL_BILL.Quantity,DETAIL_BILL.Price,Amount from DETAIL_BILL join PRODUCT on DETAIL_BILL.ProdIDb = PRODUCT.ProdID ORDER BY BillIDb DESC";
    private Detail detail;
    private String date = "1/1/2022";
    DefaultTableModel dm;

    
    public Revenue(Detail d) {
        initComponents();
        scaleImage();
        connect();
        LoadDate();
        LoadRevenue(sql);
        LoadDetailBill(sql1);
        detail = new Detail(d);
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
        btRevenue.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Revenue.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btSort.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Sort.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btDelete.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btStatistics.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Statistics.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btPrint.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Print.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btRefresh1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btDelete1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btFind.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
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
    private void LoadDate(){
        try {
            dcDate1.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        dcDate2.setDate(new java.util.Date());
    }
    
    private void LoadRevenue(String sql){
        int count = 0;
        long tongTien = 0;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        try{
            String [] arr = {"Bill ID","Employee Name","Customer ID","Customer Name","Total Amount","BillDate","Bill Time"};
            DefaultTableModel modle = new DefaultTableModel(arr,0);
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector = new Vector();
                vector.add(rs.getString("BillID").trim());
                vector.add(rs.getString("EmplName").trim());
                vector.add(rs.getString("CusIDb").trim());
                vector.add(rs.getString("CusName").trim());
                vector.add(rs.getString("TotalAmount").trim());
                vector.add(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("BillDate")));
                vector.add(rs.getString("BillTime").trim());
                modle.addRow(vector);
                String [] s = rs.getString("TotalAmount").trim().split("\\s");
                tongTien = conver(s[0])+tongTien;
                count++;
            }
            tbRevenue.setModel(modle);
            jtBill.setText(String.valueOf(count));
            jtMoney.setText(formatter.format(tongTien)+" "+"VND");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void LoadDetailBill(String sql1){
        tbDetail.removeAll();
        try{
            String [] arr = {"ID Bill","Product ID","Classify","Product Name","Quantity","Price","Amount"};
            DefaultTableModel modle = new DefaultTableModel(arr,0);
            pst = cn.prepareStatement(sql1);
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
            tbDetail.setModel(modle);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void Quantityreturn(){
        String sqlBill = "SELECT * FROM DETAIL_BILL";
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
                            pst.setInt(1, rsTemp.getInt("Quantity")+rsBill.getInt("Quantity"));
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
    private long conver(String s){
        String number = "";
        String [] array = s.replace(","," ").split("\\s");//spilit tach chuoi dua tren khoang trang
        for(String i : array){//in chuỗi đã tách theo hàng dọc
            number = number.concat(i);//concat() nối thêm chuỗi được chỉ định vào cuối chuỗi đã cho
        }
        return Long.parseLong(number);
    }
//    private void filter(String query){
//        dm = (DefaultTableModel)tbDetail.getModel();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
//        tbDetail.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(query));
//    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        dcDate1 = new com.toedter.calendar.JDateChooser();
        jPanel15 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        dcDate2 = new com.toedter.calendar.JDateChooser();
        jPanel16 = new javax.swing.JPanel();
        btRevenue = new javax.swing.JButton();
        btStatistics = new javax.swing.JButton();
        btRefresh = new javax.swing.JButton();
        btPrint = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        btSort = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        rbID = new javax.swing.JRadioButton();
        rbEmplName = new javax.swing.JRadioButton();
        rbDate = new javax.swing.JRadioButton();
        jScrollPane = new javax.swing.JScrollPane();
        tbRevenue = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jtBill = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jtMoney = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDetail = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btFind = new javax.swing.JButton();
        btRefresh1 = new javax.swing.JButton();
        btDelete1 = new javax.swing.JButton();
        jtFind = new javax.swing.JTextField();
        lbBillID = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        jtBillID = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1408, 715));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("REVENUE");

        jPanel13.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("From");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dcDate1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel14);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("To");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dcDate2, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcDate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel15);

        jPanel16.setLayout(new java.awt.GridLayout(1, 4, 5, 0));

        btRevenue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btRevenue.setText("Revenue");
        btRevenue.setIconTextGap(5);
        btRevenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRevenueActionPerformed(evt);
            }
        });
        jPanel16.add(btRevenue);

        btStatistics.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btStatistics.setText("Statistics");
        btStatistics.setIconTextGap(5);
        btStatistics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStatisticsActionPerformed(evt);
            }
        });
        jPanel16.add(btStatistics);

        btRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btRefresh.setText("Refresh");
        btRefresh.setIconTextGap(5);
        btRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRefreshActionPerformed(evt);
            }
        });
        jPanel16.add(btRefresh);

        btPrint.setText("Print");
        btPrint.setIconTextGap(5);
        btPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPrintActionPerformed(evt);
            }
        });
        jPanel16.add(btPrint);

        btDelete.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btDelete.setText("Delete");
        btDelete.setIconTextGap(5);
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });
        jPanel16.add(btDelete);

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btSort.setText("Sort");
        btSort.setIconTextGap(5);
        btSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSortActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbID);
        rbID.setText("Bill ID");

        buttonGroup1.add(rbEmplName);
        rbEmplName.setText("Employee Name");

        buttonGroup1.add(rbDate);
        rbDate.setText("Date");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(rbEmplName)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(rbID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(rbDate)
                        .addGap(14, 14, 14))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbID)
                    .addComponent(rbDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbEmplName))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btSort, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSort, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tbRevenue.setModel(new javax.swing.table.DefaultTableModel(
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
        tbRevenue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRevenueMouseClicked(evt);
            }
        });
        jScrollPane.setViewportView(tbRevenue);

        jPanel19.setLayout(new java.awt.GridLayout(1, 0, 750, 0));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("Total Bill");

        jtBill.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jtBill, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jtBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel19.add(jPanel20);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setText("Total Revenue");

        jtMoney.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jtMoney.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtMoney, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel19.add(jPanel21);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Total Revenue", jPanel12);

        tbDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        tbDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbDetail);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        btFind.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btFind.setText("Find");
        btFind.setIconTextGap(5);
        btFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindActionPerformed(evt);
            }
        });
        jPanel2.add(btFind);

        btRefresh1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btRefresh1.setText("Refresh");
        btRefresh1.setIconTextGap(5);
        btRefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRefresh1ActionPerformed(evt);
            }
        });
        jPanel2.add(btRefresh1);

        btDelete1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btDelete1.setText("Delete");
        btDelete1.setIconTextGap(5);
        btDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDelete1ActionPerformed(evt);
            }
        });
        jPanel2.add(btDelete1);

        jtFind.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtFindKeyReleased(evt);
            }
        });

        lbBillID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbBillID.setText("Find ID Bill");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbBillID, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtFind)
                    .addComponent(lbBillID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(473, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Detail Bill", jPanel1);

        lbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jtBillID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jtBillID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btRevenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRevenueActionPerformed
        String sqlrv = "SELECT BillID,EmplName,CusIDb,CusName,TotalAmount,BillDate,BillTime FROM BILL join EMPLOYEE on BILL.EmplID = EMPLOYEE.EmplID join CUSTOMER on BILL.CusIDb = CUSTOMER.CusID WHERE BillDate BETWEEN '"+new java.sql.Date(dcDate1.getDate().getTime())+"' AND '"+new java.sql.Date(dcDate2.getDate().getTime())+"' ORDER BY BillID";
        LoadRevenue(sqlrv);
    }//GEN-LAST:event_btRevenueActionPerformed

    private void btRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRefreshActionPerformed
        LoadDate();
        LoadRevenue(sql);
    }//GEN-LAST:event_btRefreshActionPerformed

    private void btSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSortActionPerformed
        if (rbID.isSelected() == false && rbEmplName.isSelected() == false && rbDate.isSelected() == false) {
        }else {
            if (rbID.isSelected()) {
                String sql = "SELECT BillID,EmplName,CusIDb,CusName,TotalAmount,BillDate,BillTime FROM BILL join EMPLOYEE on BILL.EmplID = EMPLOYEE.EmplID join CUSTOMER on BILL.CusIDb = CUSTOMER.CusID WHERE BillDate BETWEEN '"+new java.sql.Date(dcDate1.getDate().getTime())+"' AND '"+new java.sql.Date(dcDate2.getDate().getTime())+"' ORDER BY BillID";
                LoadRevenue(sql);
            } else if (rbEmplName.isSelected()) {
                String sql = "SELECT BillID,EmplName,CusIDb,CusName,TotalAmount,BillDate,BillTime FROM BILL join EMPLOYEE on BILL.EmplID = EMPLOYEE.EmplID join CUSTOMER on BILL.CusIDb = CUSTOMER.CusID WHERE BillDate BETWEEN '"+new java.sql.Date(dcDate1.getDate().getTime())+"' AND '"+new java.sql.Date(dcDate2.getDate().getTime())+"' ORDER BY EmplName";
                LoadRevenue(sql);
            } else if (rbDate.isSelected()) {
                String sql = "SELECT BillID,EmplName,CusIDb,CusName,TotalAmount,BillDate,BillTime FROM BILL join EMPLOYEE on BILL.EmplID = EMPLOYEE.EmplID join CUSTOMER on BILL.CusIDb = CUSTOMER.CusID WHERE BillDate BETWEEN '"+new java.sql.Date(dcDate1.getDate().getTime())+"' AND '"+new java.sql.Date(dcDate2.getDate().getTime())+"' ORDER BY BillDate";
                LoadRevenue(sql);
            } 
        }
    }//GEN-LAST:event_btSortActionPerformed

    private void tbRevenueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRevenueMouseClicked
        int Click = tbRevenue.getSelectedRow();
        TableModel model = tbRevenue.getModel();
        jtBillID.setText(model.getValueAt(Click,0).toString());
    }//GEN-LAST:event_tbRevenueMouseClicked

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Do you want to remove the bill?", "Delete",0);
        if(Click == JOptionPane.YES_OPTION){
            String sqlDelete = "DELETE FROM BILL WHERE BillID = ?";
            try{
                pst = cn.prepareStatement(sqlDelete);
                pst.setString(1, jtBillID.getText());
                pst.executeUpdate();
                LoadRevenue(sql);
                lbStatus.setText("Delete bill is successfull!");
                lbStatus.setForeground(Color.BLUE);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btDeleteActionPerformed

    private void btDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDelete1ActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Do you want to remove the detail bill?", "Delete",0);
        if(Click == JOptionPane.YES_OPTION){
            Quantityreturn();
            String sqlDelete = "DELETE FROM DETAIL_BILL WHERE BillIDb = ?";
            try{
                pst = cn.prepareStatement(sqlDelete);
                pst.setString(1, jtFind.getText());
                pst.executeUpdate();
                LoadDetailBill(sql1);
                lbStatus.setText("Delete bill is successfull!");
                lbStatus.setForeground(Color.BLUE);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btDelete1ActionPerformed

    private void tbDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailMouseClicked
        int Click1 = tbDetail.getSelectedRow();
        TableModel model = tbDetail.getModel();
        jtFind.setText(model.getValueAt(Click1,0).toString());
    }//GEN-LAST:event_tbDetailMouseClicked

    private void jtFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtFindKeyReleased
//        String query = jtFind.getText();
//        filter(query);
    }//GEN-LAST:event_jtFindKeyReleased

    private void btFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindActionPerformed
        String sqlFind = "SELECT BillIDb,ProdIDb,Classifyp,ProdName,DETAIL_BILL.Quantity,DETAIL_BILL.Price,Amount from DETAIL_BILL join PRODUCT on DETAIL_BILL.ProdIDb = PRODUCT.ProdID WHERE BillIDb like N'%"+this.jtFind.getText()+"%'";
        LoadDetailBill(sqlFind);
    }//GEN-LAST:event_btFindActionPerformed

    private void btRefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRefresh1ActionPerformed
        jtFind.setText("");
        LoadDetailBill(sql1);
    }//GEN-LAST:event_btRefresh1ActionPerformed

    private void btStatisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStatisticsActionPerformed
        try{
            String query = "SELECT BillDate, convert(int,replace(substring(TotalAmount,0,charindex(' ',TotalAmount)),',',''))as tt FROM BILL WHERE BillDate BETWEEN '"+new java.sql.Date(dcDate1.getDate().getTime())+"' AND '"+new java.sql.Date(dcDate2.getDate().getTime())+"'";
            pst = cn.prepareStatement(query);
            rs = pst.executeQuery();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while(rs.next()){
               dataset.setValue(Integer.parseInt(rs.getString("tt")),"Total Amount", rs.getString("BillDate"));
            }
            JFreeChart chart = ChartFactory.createBarChart3D("REVENUE STATISTICS", "DATE", "AMOUNT", dataset, PlotOrientation.VERTICAL, false, true, true);
            chart.setBackgroundPaint(new java.awt.Color(251, 242, 233));
            chart.getTitle().setPaint(Color.RED);
            BarRenderer re = null;
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setRangeGridlinePaint(Color.BLACK);
            re = new BarRenderer();
            ChartFrame frame = new ChartFrame("REVENUE STATISTICS", chart);
            frame.setVisible(true);
            frame.setSize(900,650);
            URL url = getClass().getResource("/Image/Statistics.png");
            ImageIcon img = new ImageIcon(url);
            frame.setIconImage(img.getImage());
            frame.setLocationRelativeTo(null);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btStatisticsActionPerformed

    private void btPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPrintActionPerformed
        try{
            JasperDesign jas = JRXmlLoader.load("src/Report/ReportRevenue.jrxml");
            String sql = "SELECT BillID, BillDate, BillTime, TotalAmount FROM BILL WHERE BillDate BETWEEN '"+new java.sql.Date(dcDate1.getDate().getTime())+"' AND '"+new java.sql.Date(dcDate2.getDate().getTime())+"'";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            JRDesignQuery newQ = new JRDesignQuery();
            newQ.setText(sql);
            jas.setQuery(newQ);
            
            HashMap<String, Object> has = new HashMap<>();
            has.put("Fromdate", ((JTextField)dcDate1.getDateEditor().getUiComponent()).getText().toString());
            has.put("Todate", ((JTextField)dcDate2.getDateEditor().getUiComponent()).getText().toString());
            has.put("TotalBill", jtBill.getText());
            has.put("TotalRevenue", jtMoney.getText());
            
            JasperReport js = JasperCompileManager.compileReport(jas);
            JasperPrint jp = JasperFillManager.fillReport(js, has, cn);
            JasperViewer.viewReport(jp, false);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btDelete1;
    private javax.swing.JButton btFind;
    private javax.swing.JButton btPrint;
    private javax.swing.JButton btRefresh;
    private javax.swing.JButton btRefresh1;
    private javax.swing.JButton btRevenue;
    private javax.swing.JButton btSort;
    private javax.swing.JButton btStatistics;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dcDate1;
    private com.toedter.calendar.JDateChooser dcDate2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jtBill;
    private javax.swing.JLabel jtBillID;
    private javax.swing.JTextField jtFind;
    private javax.swing.JLabel jtMoney;
    private javax.swing.JLabel lbBillID;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JRadioButton rbDate;
    private javax.swing.JRadioButton rbEmplName;
    private javax.swing.JRadioButton rbID;
    private javax.swing.JTable tbDetail;
    private javax.swing.JTable tbRevenue;
    // End of variables declaration//GEN-END:variables
}
