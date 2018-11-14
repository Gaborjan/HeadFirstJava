package Tanulokartya;

import java.util.*;
import java.awt.event.*;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.*;
import java.awt.*;
import java.io.*;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;

public class QuizCardPlayer {
	private JTextArea display;
	private ArrayList<QuizCard> cardList;
	private QuizCard currentCard;
	private int currentCardIndex;
	private JFrame frame;
	private JButton nextButton;
	private boolean isShowAnswer;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		QuizCardPlayer player = new QuizCardPlayer();
		player.go();
	} // main

	public void go() {
		frame = new JFrame("Quiz Card Player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();

		Font bigFont = new Font("Sanserif", Font.ITALIC, 24);

		display = new JTextArea(10, 20);
		display.setFont(bigFont);

		display.setWrapStyleWord(true);
		display.setLineWrap(true);
		display.setEditable(false);

		JScrollPane qScroller = new JScrollPane(display);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		nextButton = new JButton("Show Answer");
		nextButton.setPreferredSize(new Dimension(120, 30));
		nextButton.setEnabled(false);

		JButton playButton = new JButton("Play");
		playButton.setPreferredSize(new Dimension(120, 30));
		playButton.setEnabled(true);

		mainPanel.add(qScroller);
		mainPanel.add(nextButton);
		mainPanel.add(playButton);
		nextButton.addActionListener(new NextCardListener());
		playButton.addActionListener(new PlayButtonListener());

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load card set");
		loadMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(700, 450);
		frame.pack();
		frame.setVisible(true);

	} // go

	public class NextCardListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (isShowAnswer) {
				display.setText(currentCard.getAnswer());
				nextButton.setText("Next Card");
				isShowAnswer = false;
			} else {
				if (currentCardIndex < cardList.size()) {
					showNextCard();
				} else {
					display.setText("That was last card!");
					nextButton.setEnabled(false);
				}
			}
		}
	} // NextCardListener

	public class OpenMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			JFileChooser fileOpen = new JFileChooser();
			int returnVal = fileOpen.showOpenDialog(frame);
			if (returnVal != JFileChooser.CANCEL_OPTION) {
				loadFile(fileOpen.getSelectedFile());
			}
		}
	} // OpenMenuListener

	public class PlayButtonListener implements ActionListener {
		boolean playCompleted = false;
		public void actionPerformed(ActionEvent ev) {
			JFXPanel p = new javafx.embed.swing.JFXPanel();
			String uriString = new File("Beep.mp3").toURI().toString();
			MediaPlayer player = new MediaPlayer(new Media(uriString));
			player.play();
		}
	}

	private void loadFile(File file) {
		cardList = new ArrayList<QuizCard>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				makeCard(line);
			}
			reader.close();
			nextButton.setEnabled(true);
		} catch (Exception ex) {
			System.out.println("Couldn't read the card file!");
			ex.printStackTrace();
		}
		currentCardIndex = 0;
		showNextCard();
	}// loadFile

	private void makeCard(String lineToParse) {
		String[] result = lineToParse.split(";");
		if (result.length == 2) {
			QuizCard card = new QuizCard(result[0], result[1]);
			cardList.add(card);
			System.out.println("Made a card.");
		} else {
			QuizCard card = new QuizCard("NULL", "NULL");
			cardList.add(card);
			System.out.println("No text in the current line!");
		}
	} // lineToParse

	private void showNextCard() {
		currentCard = cardList.get(currentCardIndex);
		currentCardIndex++;
		display.setText(currentCard.getQuestion());
		nextButton.setText("Show Answer");
		isShowAnswer = true;
	} // showNextCard

}// QuizCardPlayer class
