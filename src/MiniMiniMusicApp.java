import javax.sound.midi.*;

public class MiniMiniMusicApp {
   public static void main(String[] args) {
      MiniMiniMusicApp mini = new MiniMiniMusicApp();
      mini.play();
   }
   
   public void play() {
      try {
         //létrehozunk egy Sequencert és megynyitjuk (másképp nem használhatjuk)
         int hangszer=102;
         int hang=50;
         Sequencer player = MidiSystem.getSequencer();
         player.open();
         Sequence seq = new Sequence(Sequence.PPQ,4);
         //kérünk egy Tracket a Sequence-től. A sáv (Track) az utasítássorozatban (Sequence)
         //található, a MIDI adatok pedig a sávban.
         Track track = seq.createTrack();
         ShortMessage first = new ShortMessage();
         // 192=hanszercsere, 1 csatornán, 42-es hanngszerre
         first.setMessage(192,1,hangszer,0);
         MidiEvent changeInstrument = new MidiEvent(first,1);
         track.add(changeInstrument);
         
         ShortMessage a = new ShortMessage(); // üzenet létrehozása
         //üzenettípus, csatorna, lejátszandó hang magassága (0-127), sebesség (0-100)
         a.setMessage(144,1,hang,80); 
         //Új MIDI esemény létrehozása az üzenettel, "a" üzenet az első ütemben
         MidiEvent noteOn = new MidiEvent(a,1);
         //MIDI esetmény hozzáadása a sávhoz
         track.add(noteOn);
         
         ShortMessage b = new ShortMessage();
         b.setMessage(128,1,hang,80);
         MidiEvent noteOff = new MidiEvent(b,16);
         track.add(noteOff);
         //A Sequencernek átadjuk a Sequencet-t (mintha egy CD-t tennénk a lejátszóba)
         player.setSequence(seq); 
         
         player.start(); // Mintha megnyomnánk a play gombot a lejátszón
      }
     catch (Exception ex) {
        ex.printStackTrace();
     }
   }
}
