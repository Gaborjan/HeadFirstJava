import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;


public class BeatBox {

	JPanel mailPanel;
	ArrayList<JCheckBox> checkboxList; //A jelölőnégyzeteket egy tömblistában tároljuk
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	JFrame theFrame;
	//Ezek a hangszerek nevei String tömbként, a grafikus felületen (egy-egy sorban) megjelenő szövegcímkék számára
	String[] instrumentNames= {"Bass Drum","Closed Hi-Hat", "Open Hi-Hat","Aqoustic Snare","Crash Cymbal",
			"Hand Clap","High Tom","Hi Bongo","Maracas","Open Triangle","Low Conga","Cowbell","Vibraslap",
			"Low-mid Tom","High Agogo","Open Hi Conga"};
	int[] instruments= {35,42,46,38,49,39,50,60,70,
			81,
			64,56,58,47,67,63};
	
	public static void main(String[] args) {
		new BeatBox().buildGUI();
	}
	
	public void buildGUI() {
		theFrame=new JFrame("Cyber BeatBox");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout=new BorderLayout();
		JPanel background = new JPanel(layout);
		//Az "üres szegély" margót biztosít a panel széla és az összetevők között - pusztán a vonzóbb 
		//látvány kedvéért
		
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		theFrame.getContentPane().add(background);
				
		Box buttonBox=new Box(BoxLayout.Y_AXIS);
		
		buttonBox.add(Box.createVerticalGlue());
		
		JButton start=new JButton("Start");
		start.addActionListener(new MyStartListener());
		buttonBox.add(start);
		
		buttonBox.add(Box.createVerticalGlue());
		
		JButton stop=new JButton("Stop");
		stop.addActionListener(new MyStopListener());
		buttonBox.add(stop);
		
		buttonBox.add(Box.createVerticalGlue());
		
		JButton upTempo=new JButton("Tempo (1,00) Up");
		upTempo.addActionListener(new MyUpTempoListener());
		buttonBox.add(upTempo);
		
		buttonBox.add(Box.createVerticalGlue());
		
		JButton downTempo=new JButton("Tempo (1,00) Down");
		downTempo.addActionListener(new MyDownTempoListener());
		buttonBox.add(downTempo);
		
		buttonBox.add(Box.createVerticalGlue());
		
		JButton eraseMusic=new JButton("Erase Music");
		eraseMusic.addActionListener(new MyEraseMusicListener());
		buttonBox.add(eraseMusic);
		
		buttonBox.add(Box.createVerticalGlue());
		
		JButton randomMusic=new JButton("Random music");
		randomMusic.addActionListener(new MyRandomMusicListener());
		buttonBox.add(randomMusic);
		
		buttonBox.add(Box.createVerticalGlue());
		
		Box nameBox=new Box(BoxLayout.Y_AXIS);
		for (int i=0;i<16;i++) {
			nameBox.add(new Label(instrumentNames[i]));
		}
				
		GridLayout grid=new GridLayout(16,16);
		grid.setVgap(1);
		grid.setHgap(2);
		JPanel mainPanel=new JPanel(grid);
		checkboxList=new ArrayList<JCheckBox>();
		
		for (int i=0;i<256;i++) {
			JCheckBox c=new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);
		}
		
		background.add(BorderLayout.EAST,buttonBox);
		background.add(BorderLayout.WEST,nameBox);
		background.add(BorderLayout.CENTER,mainPanel);
		
		setUpMidi();
		
		theFrame.setBounds(50,50,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}
	
	public void setUpMidi() {
		try {
			sequencer=MidiSystem.getSequencer();
			sequencer.open();
			sequence=new Sequence(Sequence.PPQ,4);
			track=sequence.createTrack();
			sequencer.setTempoInBPM(120);
		}
		catch (Exception e) {e.printStackTrace();}
	}
	
	public void buildTrackAndStart() {
		int[] trackList=null;
		
		sequence.deleteTrack(track);
		track=sequence.createTrack();
		
		for (int i=0;i<16;i++) {
			trackList=new int[16];
			int key=instruments[i];
			for (int j=0;j<16;j++) {
				JCheckBox jc=(JCheckBox) checkboxList.get(j+(16*i));
				if (jc.isSelected()) {
					trackList[j]=key;
				}
				else {
					trackList[j]=0;
				}
			}
			makeTracks(trackList);
			track.add(makeEvent(176,1,127,0,16));
		}
		track.add(makeEvent(192,9,1,0,15));
		try {
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		}
		catch (Exception e) { e.getStackTrace();}
	} //buildTrackAndStart
	
	public class MyStartListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			buildTrackAndStart();
		}
	}
	
	public class MyStopListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			sequencer.stop();
			refreshSpeedButtons();
		}
	}
	
	public class MyUpTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			float tempoFactor=sequencer.getTempoFactor();
			if (tempoFactor<3) {
				sequencer.setTempoFactor((float) (tempoFactor*1.03));
				refreshSpeedButtons();  
			}
		}
	}
	
	public class MyDownTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a ) {
			float tempoFactor=sequencer.getTempoFactor();
			if (tempoFactor>0.5) {
				sequencer.setTempoFactor((float) (tempoFactor*0.97));
				refreshSpeedButtons();   			
			}
		}
	}
	
	public class MyRandomMusicListener implements ActionListener {
		public void actionPerformed(ActionEvent a ) {
			Random rand = new Random();
			sequencer.stop();
			eraseCheckboxList();
			int randomNum;
			int fill;
			for (int i=0;i<256;i++) {
				fill=rand.nextInt((100-0)+1)+1;
				if ((fill % 6) ==0) {
   				randomNum = rand.nextInt((1 - 0) + 1) + 0;
   				if (randomNum==0) 
   					checkboxList.get(i).setSelected(false);
   				else 
   					checkboxList.get(i).setSelected(true);
				}
			}
		}
	}
	
	public class MyEraseMusicListener implements ActionListener {
		public void actionPerformed(ActionEvent a ) {
			sequencer.stop();
			eraseCheckboxList();
			sequencer.setTempoFactor(1);
			refreshSpeedButtons();
		}
	}
	
	
	public void eraseCheckboxList() {
		for (int i=0;i<256;i++) {
			checkboxList.get(i).setSelected(false);
		}
	}
	
	public void refreshSpeedButtons() {
		String bText;
		bText=String.format("Tempo (%2.2f) Up",sequencer.getTempoFactor());
		((JButton) (
			((Box) (
				((JPanel) theFrame.getContentPane().getComponent(0)) //background 
				.getComponent(0))) //buttonBox
			.getComponent(5))) //downTempo button
		.setText(bText); 
		bText=String.format("Tempo (%2.2f) Down",sequencer.getTempoFactor());
		((JButton) (
				((Box) (
					((JPanel) theFrame.getContentPane().getComponent(0)) //background 
					.getComponent(0))) //buttonBox
				.getComponent(7))) //downTempo button
			.setText(bText);
		
		/*JPanel c=((JPanel) theFrame.getContentPane().getComponent(0));
		Box b=(Box)c.getComponent(0);
		((JButton) (b.getComponent(7))).setText("HELLO");*/
	}
	
	public void makeTracks(int[] list) {
		for (int i=0;i<16;i++) {
			int key =list[i];
			if (key!=0) {
				track.add(makeEvent(144,9,key,100,i));
				track.add(makeEvent(128,9,key,100,i+1));
			}
		}
	} //makeTracks
	
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a=new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event= new MidiEvent(a,tick);
		}
		catch (Exception e) {e.printStackTrace();}
		return event;
	} //makeEvent
} //BeatBox

