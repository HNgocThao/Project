
package View;

import javax.swing.UIManager;


public class STOREBODYMIST {
    
    public static Login log;
    
    public static void main(String[] args) {
        
        try{
            String str ="com.jtattoo.plaf.texture.TextureLookAndFeel";
            UIManager.setLookAndFeel(str);
            lg();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static void lg(){
        log = new Login();
        log.setVisible(true);       
    }  
    
}
