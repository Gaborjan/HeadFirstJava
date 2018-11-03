import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.*;

public class GridLayoutProba extends JFrame {
	
	//GridLayout MezokLayout = new GridLayout(0,2);
	
	
	public GridLayoutProba(String name) {
       super(name);
       setResizable(false);
   }
	
	public void ElemekHozzaadasa(Container tabla) {
		JLabel lNev = new JLabel("Név:");
		//lNev.setHorizontalAlignment(JLabel.RIGHT);
		JLabel lCim = new JLabel("Cím:");
		JTextField tNev = new JTextField(20);
	   JTextField tCim = new JTextField(35); 

	   JButton okGomb = new JButton("Ok");
	   
	  /*Dimension labelMeret = new Dimension(20,20);
	   lNev.setPreferredSize(labelMeret);
	   lCim.setPreferredSize(labelMeret);*/
	   	   
	   JPanel MezokPanel = new JPanel();
	   MezokPanel.setLayout(new BoxLayout(MezokPanel, BoxLayout.PAGE_AXIS));
      JPanel GombokPanel = new JPanel();
      GombokPanel.setLayout(new GridLayout(0,5));
      
      MezokPanel.add(lNev);
      MezokPanel.add(tNev);
      MezokPanel.add(lCim);
      MezokPanel.add(tCim);
      GombokPanel.add(okGomb);
	   
      tabla.add(MezokPanel, BorderLayout.NORTH);
      tabla.add(new JSeparator(),BorderLayout.CENTER);
      tabla.add(GombokPanel, BorderLayout.SOUTH);
	}
	
	
	public static void main(String[] args) {
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
             GridLayoutProba keret = new GridLayoutProba("Grid Layout Próba");
             keret.ElemekHozzaadasa(keret.getContentPane());
             keret.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             keret.pack();
             keret.setVisible(true);
         }
     });
 }

}
