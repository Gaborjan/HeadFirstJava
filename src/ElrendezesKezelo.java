import javax.swing.*;
import java.awt.*;

public class ElrendezesKezelo {

   public static void main(String[] args) {
      ElrendezesKezelo gui = new ElrendezesKezelo();
      gui.go();

   }
   public void go() {
      JFrame frame = new JFrame("Elrendezés kezelő gyakorlás");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(500, 500);
      JButton button = new JButton("Click like you mean it");
      JButton button1 = new JButton("Click like you mean it");
      JButton button2 = new JButton("Click like you mean it");
      JButton button3 = new JButton("Click like you mean it");
      JButton button4 = new JButton("Click like you mean it");
      Font bigFont = new Font("serif", Font.BOLD, 18);
      button.setFont(bigFont);
      button4.setFont(bigFont);
      frame.getContentPane().add(BorderLayout.NORTH, button);
      frame.getContentPane().add(BorderLayout.EAST, button1);
      frame.getContentPane().add(BorderLayout.SOUTH, button2);
      frame.getContentPane().add(BorderLayout.WEST, button3);
      frame.getContentPane().add(BorderLayout.CENTER, button4);
      frame.setSize(500, 500);
      frame.setVisible(true);
      
      JFrame frame1 = new JFrame("Elrendezés kezelő gyakorlás 1");
      frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JPanel panel = new JPanel();
      panel.setBackground(Color.ORANGE);
      panel.setLayout(new GridBagLayout());
      
      GridBagConstraints c = new GridBagConstraints();

      c.weightx = 1;
      c.weighty = .25;
      c.insets = new Insets(50, 5, 50, 5);
      c.gridwidth = GridBagConstraints.REMAINDER;
      c.fill = GridBagConstraints.BOTH;
      
      JButton button5 = new JButton("Rendben");
      JButton button6 = new JButton("Ok");
      panel.add(button5,c);
      panel.add(button6,c);
      frame1.getContentPane().add(BorderLayout.EAST, panel);
      frame1.setSize(400, 400);
      frame1.setVisible(true);
      
   }
}
