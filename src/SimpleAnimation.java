import javax.swing.*;
import java.awt.*;

public class SimpleAnimation {

   int x=70;
   int y=70;
   int red=0;
   int green = 255;
   int blue = 0;
   
   public static void main(String[] args) {
      SimpleAnimation gui = new SimpleAnimation();
      gui.go();
      //System.exit(0);

   }
   
   public void go() {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      MyDrawPanel drawPanel = new MyDrawPanel();
      
      frame.getContentPane().add(drawPanel);
      frame.setSize(500, 500);
      frame.setVisible(true);
      
      for (int i=0;i<=130;i++) {
         x++;
         y++;
         green--;
         drawPanel.repaint();
      
         try {
            Thread.sleep(50);
            
         } catch (Exception ex) {}
      }
      //frame.dispose();
  }
   
   class MyDrawPanel extends JPanel {
      public void paintComponent(Graphics g) {
         g.setColor(Color.white);
         g.fillRect(0, 0, this.getWidth(), this.getHeight());
         Color ovalColor = new Color(red,green,blue);
         g.setColor(ovalColor);
         g.fillOval(x, y, 40+x, 40+x);
      }
   }
}


