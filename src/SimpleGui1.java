import javax.swing.*;
import java.awt.event.*;

// ActionListener interfészt megvalósító objektum!
public class SimpleGui1 implements ActionListener {
   JButton button;
   int clicked=0;

   public static void main(String[] args) {
      SimpleGui1 gui = new SimpleGui1();
      gui.go();
   }
   
   public void go() {
      JFrame frame = new JFrame();
      button = new JButton("Click me");
      //Azt mondjuk a gombnak, hogy adjon hozzá minket (a SimpleGui1 objektumot - this) a figyelőinek
      //listájához. Az átadott objektumnak kötelező megvalósítania az ActionListener interfészt!
      button.addActionListener(this);
      frame.getContentPane().add(button);//a gombot hozzáadjuk a keret tartalomtáblájához
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ablak bezárás esetén programnak is legyen vége
      frame.setSize(300, 300);
      frame.setVisible(true);
   }
   //Az ActionListener interfész actionPerformed() metódusának megvalósítása
   public void actionPerformed(ActionEvent event) {
     //Toolkit.getDefaultToolkit().beep();
     //System.out.println("Rám kattintottak! :)");
     button.setText("I have been clicked "+(++clicked)+" times!");
   }
}
