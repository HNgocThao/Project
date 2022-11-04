
package View;

import Model.ConnectSQL;
import Model.Detail;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import javax.swing.ImageIcon;

public class Coverimage extends javax.swing.JPanel {
    private Detail detail;
    Connection cn; 
    public Coverimage(Detail d) {
        initComponents();
        connect();
        scaleImage();
    }
    
    private void connect(){
        try {
            cn = ConnectSQL.getConnectSQL();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void scaleImage(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;

        setBounds((width - 1394) / 2, (height - 703) / 2, 1394, 703);
//        lbPicture.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Anh.jpg")).getImage().getScaledInstance(1300, 700, Image.SCALE_SMOOTH)));
//        btFind.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
//        btChange.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
//        btUp.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Up.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
//        btDown.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Down.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbPicture = new javax.swing.JLabel(){
            ImageIcon icon = new ImageIcon("src/Image/Anh.jpg");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, this);
                setOpaque(false);
                super.paintComponent(g);
            }
        };

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbPicture, javax.swing.GroupLayout.DEFAULT_SIZE, 1396, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbPicture, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbPicture;
    // End of variables declaration//GEN-END:variables
}
