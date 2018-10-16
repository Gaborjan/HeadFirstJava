import javax.sound.midi.*;

public class MiniMiniMusicApp {
   public static void main(String[] args) {
      MiniMiniMusicApp mini = new MiniMiniMusicApp();
      mini.play();
   }
   
   public void play() {
      try {
         Sequencer player = MidiSystem.getSequencer();
         player.open();
         Sequence seq = new Sequence(Sequence.PPQ,4);
         Track track = seq.createTrack();
       
         ShortMessage first = new ShortMessage();
         first.setMessage(192,1,42,0);
         MidiEvent changeInstrument = new MidiEvent(first,1);
         track.add(changeInstrument);
         
         ShortMessage a = new ShortMessage();
         a.setMessage(144,1,50,80);
         MidiEvent noteOn = new MidiEvent(a,1);
         track.add(noteOn);
         
         ShortMessage b = new ShortMessage();
         b.setMessage(128,1,80,80);
         MidiEvent noteOff = new MidiEvent(b,10);
         track.add(noteOff);
         player.setSequence(seq);
         
         player.start();
      }
     catch (Exception ex) {
        ex.printStackTrace();
     }
   }
}
