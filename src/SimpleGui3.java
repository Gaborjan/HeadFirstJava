import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SimpleGui3 implements ActionListener {
   JFrame frame;
   
   public static void main(String[] args) {
      SimpleGui3 gui= new SimpleGui3();
      gui.go();
   }
   public void go() {
      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JButton button = new JButton("Change colors!");
      button.addActionListener(this);
      MyDrawPanel drawPanel=new MyDrawPanel();
      drawPanel.setBackground(Color.BLUE);
      frame.getContentPane().add(BorderLayout.SOUTH, button);
      frame.getContentPane().add(BorderLayout.CENTER,drawPanel);
      frame.setSize(300,300);
      frame.setVisible(true);
   }
   
   public void actionPerformed(ActionEvent event) {
      frame.repaint();
   }
   
   class MyDrawPanel extends JPanel {
      public void paintComponent(Graphics g) {
         int red = (int) (Math.random() *255);
         int green = (int) (Math.random() *255);
         int blue = (int) (Math.random() *255);
         Color cColor = new Color(red,green,blue);
         g.setColor(cColor);
         g.fillRect(0, 0, this.getWidth(), this.getHeight());
         red = (int) (Math.random() *255);
         green = (int) (Math.random() *255);
         blue = (int) (Math.random() *255);
         cColor = new Color(red,green,blue);
         g.setColor(cColor);
         g.fillOval(70, 70, 100, 100);
      }
   }
}
 