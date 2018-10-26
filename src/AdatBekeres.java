 
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.*;
 
public class AdatBekeres extends JFrame {
    public AdatBekeres() {
        JLabel lNev = new JLabel("Név:");
        JLabel lCim = new JLabel("Cím:");
        JLabel lANeve = new JLabel("Anyja neve:"); 
        JTextField tNev = new JTextField(40);
        JTextField tCim = new JTextField(60);
        JTextField tANeve = new JTextField(40);
        BorderLayout layout = new BorderLayout();
        JPanel panelMezok = new JPanel(layout);
        JPanel panelGombok = new JPanel();
        JButton OkButton = new JButton("Ok");
        
        Box labelBox = new Box(BoxLayout.Y_AXIS);
 
        labelBox.add(lNev);
        labelBox.add(lCim);
        labelBox.add(lANeve);
        
        Box mezoBox = new Box(BoxLayout.Y_AXIS);
       
        mezoBox.add(tNev);
        mezoBox.add(tCim);
        mezoBox.add(tANeve);
       
        
        Box gombBox = new Box(BoxLayout.Y_AXIS);
         
        gombBox.add(OkButton);
               
        panelMezok.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
        panelMezok.add(BorderLayout.WEST,labelBox);
        panelMezok.add(BorderLayout.EAST,mezoBox);
        panelMezok.add(new JSeparator(), BorderLayout.CENTER);
        panelMezok.add(BorderLayout.SOUTH,gombBox);
 
        getContentPane().add(panelMezok);
        setSize(300,300);
        setTitle("Adatbekerés");
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
     
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                                  "javax.swing.plaf.metal.MetalLookAndFeel");
                                //  "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                                //UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                new AdatBekeres().setVisible(true);
            }
        });
    }
}
