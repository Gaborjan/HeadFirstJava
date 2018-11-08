import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;


public class BeatBox {

	JFrame theFrame;
	JPanel mainPanel;

	ArrayList<JCheckBox> checkboxList; //A jelölőnégyzeteket egy tömblistában tároljuk
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	
	//Ezek a hangszerek nevei String tömbként, a grafikus felületen (egy-egy sorban) megjelenő szövegcímkék számára
	String[] instrumentNames= {"Bass Drum","Closed Hi-Hat", "Open Hi-Hat","Aqoustic Snare","Crash Cymbal",
			"Hand Clap","High Tom","Hi Bongo","Maracas","Open Triangle","Low Conga","Cowbell","Vibraslap",
			"Low-mid Tom","High Agogo","Open Hi Conga"};
	//Ezek a dob hangszerek, amik akkor szólalnak meg, ha a csatorna "9" a midi esemény message paraméterében
	int[] instruments= {35,42,46,38,49,39,50,60,70,81,64,56,58,47,67,63}; 
	
	public static void main(String[] args) {
	try {
            // Set System L&F
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
    }
    catch (ClassNotFoundException e) {
       // handle exception
    }
    catch (InstantiationException e) {
       // handle exception
    }
    catch (IllegalAccessException e) {
       // handle exception
    }
		new BeatBox().buildGUI();
	} //main
	
	//A grafikus felület létrehozása//
	public void buildGUI() {
		theFrame=new JFrame("Cyber BeatBox");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout bLayout=new BorderLayout();
		JPanel background = new JPanel(bLayout); //A backgroundra kerül rá a hangszerek boxa, a középső checkbox grid és 
		//a jobb szélső button box.
		//Az "üres szegély" margót biztosít a panel széla és az összetevők között - pusztán a vonzóbb látvány kedvéért
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		theFrame.getContentPane().add(background);
		
		//A hangszer felirat boxának létrehozása és feltöltése a hangszer nevekkel		
		Box nameBox=new Box(BoxLayout.Y_AXIS);
		for (int i=0;i<16;i++) {
			nameBox.add(new Label(instrumentNames[i]));
		}
		
		//A középső, grid elrendezésű rész létrehozása és feltöltése checkbox-okkal
		GridLayout grid=new GridLayout(16,16);
		grid.setVgap(0);
		grid.setHgap(2);
		mainPanel=new JPanel(grid);
		checkboxList=new ArrayList<JCheckBox>();
		
		for (int i=0;i<256;i++) {
			JCheckBox c=new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);
		}
		
		//A gombok box létrehozása és feltöltése gombokkal. A gombok közé rugalmas függőleges elválasztást teszünk.
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
		
		JButton mentesGomb=new JButton("Mentés");
		mentesGomb.addActionListener(new MyMentesGombListener());
		buttonBox.add(mentesGomb);
		
		buttonBox.add(Box.createVerticalGlue());
		
		//A háttér panelre rátesszük balra a név boxot, középre a checkboxgridet, a jobb szélre a gombokat		
		background.add(BorderLayout.EAST,buttonBox);
		background.add(BorderLayout.WEST,nameBox);
		background.add(BorderLayout.CENTER,mainPanel);
	
		setUpMidi(); //midi rész inicializálása
		
		theFrame.setBounds(50,50,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}//buildGUI metódus
	
	
	//midi rész inicializálása
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
		/*Létrehozunk egy 16 elemű tömböt, amelyben egy hangszer értékeit tároljuk a 16 ütemre
		 * Ha egy hangszernek meg kell szólalnia egy adott ütemben, a tömb adott elemének értéke
		 * lesz a kulcs. Ha a hangszernek nem kell megszólalnia az említett ütemben, akkor nullát
		 * szúrunk be. A metódus a checkboxokból kiolvasott ki/be állapotoktól függően létrehozza a
		 * lejátszandó tracket.
		 */
		int[] trackList=null;
		
		sequence.deleteTrack(track); // Megszabadulunk a régi sávtól és létrehozunk egy újat.
		track=sequence.createTrack();
		
		for (int i=0;i<16;i++) { //Mind a 16 sorra (hangszerre) elvégezzük a beállítást
			trackList=new int[16]; //Adott hangszerre érvényes "kotta"
			int key=instruments[i]; //Az adott hangszert jelképező számkód beállítása a hangszerek tömbből
			for (int j=0;j<16;j++) { //Az adott hangszer 16 ütemére elvégezzük a beállítást
				JCheckBox jc = (JCheckBox)checkboxList.get(j+(16*i));
				//Be van kapcsolva a jelölőnégyzet erre az ütemre? Ha igen, a tömb megfelelő rekeszébe (az
				//adot ütemeber jelképező rekeszbe) beszúrjuk a kulcsértéket (hangszert), ha viszont nem
				//a hangszernek nem kell megszólalnia ebben az ütemben, így nullát írunk be.
				if (jc.isSelected()) {
					trackList[j]=key;
				}
				else {
					trackList[j]=0;
				}
			} //belső for ciklus
			//Az adott hangszer mind a 16 üteméhez eseményeket hozunk létre és hozzáadjuk azokat a sávhoz
			makeTracks(trackList);
			track.add(makeEvent(176,1,127,0,16)); //vezérlőesemény hozzáadása
		} // külső for ciklus
		//Mindig gondoskodni kell róla, hogy legyen esemény a 16. ütemhez (a számozás 0-tól 15-ig tart),
		//különben a BeatBox esetleg nem megy végig mind a 16 ütemen, mielőtt újrakezdi a ciklust
		track.add(makeEvent(192,9,1,0,15));
		try {
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY); //a ciklus ismétléseinek száma, folyamatos lejátszás
			sequencer.start();
			sequencer.setTempoInBPM(120);
		}
		catch (Exception e) { e.getStackTrace();}
	} //buildTrackAndStart metódus
	
	//"Start" gomb figyelője, az aktuális checkbox beállítást kiolvasva lejátssza a beállított "kottát"
	public class MyStartListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			buildTrackAndStart();
		}
	} // MyStartListener class
	
	//"Stop" gomb eseténye, megállítja a lejátszást.
	public class MyStopListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			sequencer.stop();
			//refreshSpeedButtons();
		}
	} // MyStopListener class
	
	//Tempo gyorsítása gomb eseménye
	public class MyUpTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			float tempoFactor=sequencer.getTempoFactor();
			if (tempoFactor<3) {
				sequencer.setTempoFactor((float) (tempoFactor*1.03)); 
				refreshSpeedButtons();  
			}
		}
	} //MyUpTempoListener class
	
	//Tempo lassítás eseménye
	public class MyDownTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a ) {
			float tempoFactor=sequencer.getTempoFactor();
			if (tempoFactor>0.5) {
				sequencer.setTempoFactor((float) (tempoFactor*0.97));
				refreshSpeedButtons();   			
			}
		}
	} // MyDownTempoListener class
	
	//Véletlenszerűen felölti a checkbox-okat minden hangszerre
	public class MyRandomMusicListener implements ActionListener {
		public void actionPerformed(ActionEvent a ) {
			Random rand = new Random();
			sequencer.stop();
			eraseCheckboxList(); //checkboxok törlése
			int utem; //Az adott ütem beállítása, értéke 0 (checkbox üres) vagy 1 (checkbox pipálva)
			int suruseg; //Mennyire sűrűn legyen töltve a kotta. Minél kisebb, annál több checkbox lesz pipálva
			for (int i=0;i<256;i++) { //Minden checkboxon végigmegyünk
				suruseg=rand.nextInt((100-0)+1)+1;
				if ((suruseg % 6) ==0) {
   				utem = rand.nextInt((1 - 0) + 1) + 0;
   				if (utem==0) 
   					checkboxList.get(i).setSelected(false);
   				else 
   					checkboxList.get(i).setSelected(true);
				} //if
			} //for
		} //actionPerformed metódus
	} //MyRandomMusicListener class
	
	//Az "EraseMusic" gomb eseménye
	public class MyEraseMusicListener implements ActionListener {
		public void actionPerformed(ActionEvent a ) {
			sequencer.stop();
			eraseCheckboxList();
			sequencer.setTempoFactor(1);
			refreshSpeedButtons();
		}
	} // MyEraseMusicListener class
	
	//Segéd metódus, törli a checkboxokat
	public void eraseCheckboxList() {
		for (int i=0;i<256;i++) {
			checkboxList.get(i).setSelected(false);
		}
	} //eraseCheckboxList metódus

	//A két sebesség gomb szövegének frissítése
	public void refreshSpeedButtons() {
		String bText;
		bText=String.format("Tempo (%2.2f) Up",sequencer.getTempoFactor());
		((JButton) (
			((Box) (
				((JPanel) theFrame.getContentPane().getComponent(0)) //background 
				.getComponent(0))) //buttonBox
			.getComponent(5))) //upTempo button
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
	} //refreshSpeedButtons metódus
	
	public void makeTracks(int[] list) {
	/*Egyszerre egy hangszer számára hoz létre eseményeket, mind a 16 ütemre. Vagyis
	 * lehet egy int[] a basszusdobhoz és a tömb egyes rekeszeiben a hangszer kódja
	 * vagy nulla áll. Ha nulla, a hangszernek nem kell megszólalnia az adott ütemben,
	 * egyébként létrehozunk egy eseményt és hozzáadjuk a sávhoz.
	 */
		for (int i=0;i<16;i++) {//A hangszerhez tartozó ütemeken végigmegyünk
			int key =list[i]; //A key a hangszer kódja vagy nulla
			if (key!=0) { //Ha a hangszer kódja volt a tömbben
				track.add(makeEvent(144,9,key,100,i)); //Note on (144) esemény létrehozása
				track.add(makeEvent(128,9,key,100,i+1)); //Note off (128) esemény létrehozása
				//A 9, mint csatorna a dob csatorna, amin belül más-más dob típusokat lehet megszólaltatni
				//ami jelen esetben a key. Ha a 9-et 1-re cseréljük, akkor az instruments tömbben lévő kódoknak
				//megfelelő hangszerek szerepelnek meg, nem a különféle dob-típusok.
			}
		}
	} //makeTracks
	
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
	/*Egy midi esemény létrehozása*/
		MidiEvent event = null;
		try {
			ShortMessage a=new ShortMessage();
			a.setMessage(comd,chan,one,two); //parancs, csatorna, hangszer, sebesség
			event= new MidiEvent(a,tick); //tick->ütem
		}
		catch (Exception e) {e.printStackTrace();}
		return event;
	} //makeEvent
	
	public	class MyMentesGombListener implements ActionListener {
		public void actionPerformed(ActionEvent a ) {
			sequencer.stop();
			JFileChooser fajlValasztoAblak = new JFileChooser();
			fajlValasztoAblak.showSaveDialog(theFrame);
		}
	} //MyMentesGombListener class
	
} //BeatBox

