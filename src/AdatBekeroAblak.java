
import javax.swing.*;
import java.awt.*;

public class AdatBekeroAblak {
   JFrame keret;
   public static void main(String[] args) {
      AdatBekeroAblak ablak = new AdatBekeroAblak();
      ablak.Bekeres();
   }

   public void AblakElemek() {
      keret = new JFrame("Személyi adatok bekérése");
      keret.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      keret.setSize(600, 400);
      keret.setResizable(false);
      
      JPanel panelf = new JPanel();
      SpringLayout layout = new SpringLayout();
      JPanel panela = new JPanel();
      panela.setBackground(Color.DARK_GRAY);
      panelf.setBackground(Color.ORANGE);
      keret.getContentPane().add(BorderLayout.SOUTH, panela);
      keret.getContentPane().add(BorderLayout.NORTH, panelf);
      keret.setVisible(true);
      
      
   }
      
   public void Bekeres() {
      AblakElemek();
   }
}


