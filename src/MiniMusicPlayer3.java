import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer3 {
   
   static JFrame f = new JFrame("My First Music Video");
   static MyDrawPanel m1;
   
   public static void main(String[] args) {
      MiniMusicPlayer3 mini = new MiniMusicPlayer3();
      mini.go();
   }
   
   public void setUpGui() {
      m1 = new MyDrawPanel();
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.setContentPane(m1);
      f.setBounds(300,300,400,400);
      f.setVisible(true);
   }
   
   public void go() {
      setUpGui();
      
      try {
         Sequencer sequencer = MidiSystem.getSequencer();
         sequencer.open();
         //Hozzáadunk egy figyelőt, ami a midi eseményeket tudja figyelni.
         //A figyelőt az m1 panel implementálja.
         // A 127-es eseményt fogjuk figyelni, ami egyébként nem csinál semmit.
         //Bármilyen szám lehetne amúgy, de max 127.
         sequencer.addControllerEventListener(m1, new int[] {127});
         Sequence seq = new Sequence(Sequence.PPQ, 4);
         Track track = seq.createTrack();
         // A track feltöltése parancsokkal, zongora hangszerrel
         int r = 0; // hangmagasság
         for (int i = 0; i < 120; i+=4) { // A ciklusváltozó vége a "zene" hossza
            r = (int) ((Math.random() * 80)+1); // előállítunk egy hangot
            track.add(makeEvent(ShortMessage.NOTE_ON,1,r,50,i)); // hang be, 1 csat, hangmagasság, hangerő, i. ütem
            track.add(makeEvent(ShortMessage.CONTROL_CHANGE,1,127,0,i)); 
            // ez nem csinál semmit, de elhelyezzük a 127 sz. eseményt minden
            // ütemhez, amikor hangot szólaltaunk meg
            track.add(makeEvent(ShortMessage.NOTE_OFF,1,r,50,i+2)); // hang ki
         }
         sequencer.setSequence(seq); 
         sequencer.setTempoInBPM(220); // ütem állítása
         sequencer.start();
        
      } 
      catch (Exception ex) {ex.printStackTrace();}
   }
   //Segéd metódus, ami egy midi eventet hozz létre.
   public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
      MidiEvent event = null;
      try {
         ShortMessage a = new ShortMessage();
         a.setMessage(comd, chan, one, two);
         event = new MidiEvent(a, tick);
      }
      catch (Exception e) {}
      return event;
   }
   
   class MyDrawPanel extends JPanel implements ControllerEventListener {
      boolean msg = false; //Alap esetben nem kell rajzolni

      //Itt figyeljük lejátszás közben keletkezik-e CONTROL_CHANGE esemény
      //Ha igen, ez a metódus kerül végrehajtásra, meghívjuk a négyzetrajzolást
      public void controlChange(ShortMessage event) {
         msg = true;
         repaint();
      }
      
      public void paintComponent(Graphics g) {
         if (msg) { //Ha vezérlő eseményt kaptunk a figyelőtől
            Graphics2D g2 = (Graphics2D) g;
            //véletren rgb szín előállítása
            int r = (int) (Math.random() * 250);
            int gr = (int) (Math.random() * 250);
            int b = (int) (Math.random() * 250);
            
            g.setColor(new Color(r,gr,b));
            //véletlen négyzet szélesség és magasság előállítása
            int ht = (int) ((Math.random() * 300) + 10);
            int width = (int) ((Math.random() * 300) + 10);
            //véletlen bal felső koordináta előállítása
            int x = (int) ((Math.random() * 60) +10);
            int y = (int) ((Math.random() * 60) +10);
            //rajzolás
            g.fill3DRect(x, y, width, ht, true);
            //eseményt feldolgoztunk visszaállítjuk hamisra
            msg = false;
         } // if
      } // paintComponent metódus 
   } // class MyDrawPanel
} // class MiniMusicPlayer3
