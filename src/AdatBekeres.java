 
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

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
        JButton CancelButton = new JButton("Cancel");
        
        Box labelBox = new Box(BoxLayout.PAGE_AXIS);
        labelBox.add(lNev);
        labelBox.add(Box.createRigidArea(new Dimension(0, 15)));
        labelBox.add(lCim);
        labelBox.add(Box.createRigidArea(new Dimension(0, 15)));
        labelBox.add(lANeve);
       
        
        Box mezoBox = new Box(BoxLayout.PAGE_AXIS);
       
        mezoBox.add(tNev);
        mezoBox.add(Box.createRigidArea(new Dimension(0, 10)));
        mezoBox.add(tCim);
        mezoBox.add(Box.createRigidArea(new Dimension(0, 10)));
        mezoBox.add(tANeve);
       
        
        Box gombBox = new Box(BoxLayout.LINE_AXIS);
        gombBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        gombBox.add(Box.createHorizontalGlue()); 
        //OkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gombBox.add(OkButton);
        gombBox.add(Box.createRigidArea(new Dimension(10, 0)));
        //CancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gombBox.add(CancelButton);
        gombBox.add(Box.createHorizontalGlue());
               
        panelMezok.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
        panelMezok.add(BorderLayout.WEST,labelBox);
        panelMezok.add(BorderLayout.EAST,mezoBox);
        //panelMezok.add(new JSeparator(), BorderLayout.CENTER);
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
