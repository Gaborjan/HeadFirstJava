import java.awt.Component;
import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.*;
 
public class AdatBekeroAblak extends JFrame {
    public AdatBekeroAblak() {
        JLabel label = new JLabel("Név:");;
        JTextField textField = new JTextField(40);
       
        JButton findButton = new JButton("Ok");
        JButton cancelButton = new JButton("Mégsem");
 
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
 
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(label)
            .addComponent(textField)
            .addComponent(findButton)
           .addComponent(cancelButton)
        );
        

 
        layout.setVerticalGroup(
      		  layout.createSequentialGroup()
      		  		.addGroup(
                .addComponent(label)
                .addComponent(textField))
                .addComponent(findButton)
                .addComponent(cancelButton));
        
 
        setTitle("Személyes adatok bekérése");
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
                new AdatBekeroAblak().setVisible(true);
            }
        });
    }
}


