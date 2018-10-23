import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TwoButtons {
   JFrame frame;
   JLabel label;
   int x;
   boolean circle_msg = false; // Ha körvváltoztató gombot nyomja meg, igazzá válik   
   int red = (int) (Math.random() *255);
   int green = (int) (Math.random() *255);
   int blue = (int) (Math.random() *255);

   public static void main(String[] args) {
      TwoButtons gui = new TwoButtons();
      gui.go();
   } //main

   public void go() {
      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      MyDrawPanel drawPanel = new MyDrawPanel();
      
      JButton labelButton = new JButton("Change Label");
      labelButton.addActionListener(new LabelListener());
      
      JButton colorButton = new JButton("Change Circle");
      colorButton.addActionListener(drawPanel); //A draw panel valósítja meg az ActionListenert
      
      label = new JLabel("I'm a label");
  
      
      frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
      frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
      frame.getContentPane().add(BorderLayout.EAST, labelButton);
      frame.getContentPane().add(BorderLayout.WEST, label);
      
      frame.setSize(400, 400);
      frame.setVisible(true);
   } //go metódus
   
   class LabelListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         x++;
         label.setText("Ouch!"+x);
         circle_msg=false;
      }
   }
   //A két Listeneres megoldás miatt maradt itt, de javítottunk rajta.   
   /*class ColorListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         frame.repaint();
      }
   }*/ 
   
   class MyDrawPanel extends JPanel implements ActionListener {
    
     //Ha a körváltoztató gombot nyomjuk meg, akkor a circle_msg-t igazra állítjuk
     // és meghívjuk az újrarajzolást
      public void actionPerformed(ActionEvent event) { 
         circle_msg=true; 
         repaint();
      }
      
      public void paintComponent(Graphics g) {
        if (circle_msg) { //Ha a körrajzolást nyomja meg, akkor új színt is csinálunk
           g.fillRect(0, 0, this.getWidth(), this.getHeight());
           red = (int) (Math.random() *255);
           green = (int) (Math.random() *255);
           blue = (int) (Math.random() *255);
           Color ovalColor = new Color(red,green,blue);
           g.setColor(ovalColor);
           g.fillOval(70, 70, 100, 100);
           circle_msg=false;
        }
        else //Ha nem a körrajzolás gombot nyomta meg, akkor csak újrarajzoljuk a panelt
             // az aktuális színnel.
        {
           g.fillRect(0, 0, this.getWidth(), this.getHeight());
           Color ovalColor = new Color(red,green,blue);
           g.setColor(ovalColor);
           g.fillOval(70, 70, 100, 100);
        } //if
      } // paintComponenet 
  } // class MyDrawPanel
} // class TwoButtons
