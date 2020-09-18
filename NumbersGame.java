import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.Formatter;
import java.util.Arrays;

/**
 * 
 * @author Luc Garabrant
 *
 */

/*
 * Constructor for the Numbers Game class.
 * Initializes the java frame.
 */

public class NumbersGame {
	
	private ArrayList<Player> playerArray;
	private Player tempPlayer;
	double start;
	Random rand;
	
	//global JComponenents
	JTextArea statsTextArea;
	JTextField guessField;
 	JCheckBox timeCheckBox; 
 	JCheckBox turnsCheckBox;
 	JCheckBox difficultyCheckBox;

	
	public NumbersGame() throws FileNotFoundException {
		
		this.playerArray = new ArrayList<Player>();
		
		JFrame jfrm = new JFrame("Numbers Game");
		jfrm.setSize(new Dimension(600,400));
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel northPanel = new JPanel(new GridLayout(2,0));
		northPanel.setPreferredSize(new Dimension(600,67));
		
			JLabel gameLabel = new JLabel("Numbers Game");
			gameLabel.setBorder(BorderFactory.createLineBorder(Color.black));
			gameLabel.setOpaque(true);
			gameLabel.setFont(new Font ("Georgia", Font.PLAIN, 13));
			gameLabel.setHorizontalAlignment(JLabel.CENTER);
			gameLabel.setVerticalAlignment(JLabel.CENTER);
			
			JPanel northSubPanel = new JPanel(new GridLayout(0,2));
			
				JButton gameButton = new JButton("Start Game Button");
				gameButton.addActionListener(new ButtonListener());
				guessField = new JTextField("Enter your guess here: ",10);
				guessField.addKeyListener(new KeyBoardListener());
				
			northSubPanel.add(gameButton);
			northSubPanel.add(guessField);
			
		northPanel.add(gameLabel);
		northPanel.add(northSubPanel);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
			this.statsTextArea = new JTextArea();
			JScrollPane scrollPane = new JScrollPane(statsTextArea); 
		centerPanel.add(scrollPane);	
			
		JPanel southPanel = new JPanel(new GridLayout(2,0));
		southPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
				JPanel southSubPanel1 = new JPanel(new GridLayout(0,2));

					JButton clearDisplayButton = new JButton("Clear Display Button");
					clearDisplayButton.addActionListener(new ButtonListener());
			        JPanel statsOptionsPanel = new JPanel(new BorderLayout());
			         
			         	JLabel statsOptionsLabel = new JLabel("Statistics Options");
			         	statsOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
			         	statsOptionsLabel.setVerticalAlignment(JLabel.BOTTOM);
			         	
			         statsOptionsPanel.add(statsOptionsLabel,BorderLayout.NORTH);
					 
			         	JPanel subPanel = new JPanel();

			         	timeCheckBox = new JCheckBox("Time");
			         	turnsCheckBox = new JCheckBox("# of plays");
			         	subPanel.add(timeCheckBox);
			         	subPanel.add(turnsCheckBox);

			        
			        statsOptionsPanel.add(subPanel,BorderLayout.CENTER);
			         
			         
					southSubPanel1.add(clearDisplayButton);
					southSubPanel1.add(statsOptionsPanel);
					
				JPanel southSubPanel2 = new JPanel(new GridLayout(0,2));
				
					JButton displayStatisticsButton = new JButton("Display Statistics Button");
					displayStatisticsButton.addActionListener(new ButtonListener());
					JPanel checkBoxPanel = new JPanel();
		
						difficultyCheckBox = new JCheckBox("Most Difficult Games");

					checkBoxPanel.add(difficultyCheckBox);

				southSubPanel2.add(displayStatisticsButton);
				southSubPanel2.add(checkBoxPanel);
					

		southPanel.add(southSubPanel1);
		southPanel.add(southSubPanel2);
		
			
		jfrm.add(northPanel,BorderLayout.NORTH);
		jfrm.add(centerPanel,BorderLayout.CENTER);
		jfrm.add(southPanel,BorderLayout.SOUTH);
		
		jfrm.setVisible(true);	
	}

	
	/*
	 * Implementation of the KeyListener Infterface.
	 * Used to enter input guesses into the program.
	 */
	class KeyBoardListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * the keyPressed method will record the guess any time the enter Key is input. 
		 */

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				//create a reference to the guess entered by the user and increment the players number of guesses.
				
				JTextField source = (JTextField) e.getSource();
				String guess = source.getText();
				guess = guess.replaceAll(" ","");
				guess = guess.replaceAll(":","");
				tempPlayer.incrementGuesses();		
				
				//check what level the player is on.
				
				if (tempPlayer.getLevel() == 1) {
					
					//If the player is on level one, the integer guess must be a single number, therefore we can parse the string as our guess.
					
					int guessInt = Integer.parseInt(guess);
					if(tempPlayer.getGeneratedInt() == guessInt) {
						
						statsTextArea.append("Congratulations! You WON! To play a new game, click the 'Start Game' Button! \n");
						guessField.setText("");
						guessField.setEnabled(false);
						double finish = System.currentTimeMillis();
						double timePlayed = finish - start;
						timePlayed = timePlayed/1000;
						playerArray.add(tempPlayer);

					}
					else if((tempPlayer.getGeneratedInt() - guessInt) > 0) {
						guessField.setText("");
						statsTextArea.append("Greater\n");
					}else {
						guessField.setText("");
						statsTextArea.append("Smaller\n");
					}
				

			} else {
				
				//if the level is not equal to one, the guess enter will contain at least two digits form 0-9, therefore we can turn it into an array of character.
				
				char[] charArray = guess.toCharArray();
				ArrayList<Integer> guessValues = new ArrayList<Integer>();
				
				for(char ch : charArray) {
					guessValues.add(Character.getNumericValue(ch));
				}
				
				int amountCorrect = 0;
				int correctPosition = 0;
				
				//use a nested for loop to count both the correct position and amount.
				
				for(int i = 0; i < tempPlayer.getLevel(); i++) {
					for(int g = 0; g < tempPlayer.getLevel(); g++) {
						if((i == g) && tempPlayer.getGeneratedValues().get(i) == guessValues.get(g)) {
							correctPosition++;
							amountCorrect++;
							}
						else if(tempPlayer.getGeneratedValues().get(i) == guessValues.get(g)){
							amountCorrect++;
							}
						}
					}
				
				//if we guess the correct amount of digits and they're all in the same corresponding position, display a message to the text area and disable the text field. 
				
				if(amountCorrect == tempPlayer.getLevel() && correctPosition == tempPlayer.getLevel()) {
					
					statsTextArea.append("Congratulations! You WON! To play a new game, click the 'Start Game' Button! \n");
					guessField.setText("");
					guessField.setEnabled(false);
					double finish = System.currentTimeMillis();
					double timePlayed = finish - start;
					timePlayed = timePlayed/1000;
					tempPlayer.setTimePlayed(timePlayed);
					playerArray.add(tempPlayer);
					
				//else, display a clue to the textArea so that the user can enter another guses.
					
				}else {
				
					guessField.setText("");	
					statsTextArea.append("Guess: (");
					for (int i = 0; i<tempPlayer.getLevel();i++) {
						if (i == tempPlayer.getLevel() - 1) {
								statsTextArea.append(""+guessValues.get(i));
						}
						else
							statsTextArea.append(""+guessValues.get(i)+",");
						}
					statsTextArea.append(") Hint: ("+amountCorrect+","+correctPosition+")\n");
					
					}
	
				}		
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	/*
	 * The ButtonListener class is an implementation of the ActionListener class.
	 */
	
	class ButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			//if a button is pressed, create a reference to that button to determine what kind of button it is.
			
			JButton myb = (JButton) e.getSource();
			
			// if the button is the start game button, we prompt the user for information to create an instance of our player class. 
			
			if (myb.getText().contentEquals("Start Game Button")){

					String name = JOptionPane.showInputDialog("What is your name? ");
					String strLevel = JOptionPane.showInputDialog("What level would you like to play on? Choose between 1,2,3, 4, and 5");
					int level = Integer.parseInt(strLevel);
					int numberOfGuesses = 0;
					start = System.currentTimeMillis();
					rand = new Random();
					guessField.setEnabled(true);
					
					//if the level is equal to one, generate a single random number and add it to class.
					
					if (level == 1){
						
						String strMax = JOptionPane.showInputDialog("Please enter a maximum value for the random number generator: ");
						int max = Integer.parseInt(strMax);
						int generatedInt = rand.nextInt(max+1);
						System.out.printf("Printing generated value for testing purposes: %d\n",generatedInt);
						JOptionPane.showMessageDialog(null,"A Random number less than or equal to your max value has been generated. Please clear the text guess box and enter your guess!");
						tempPlayer = new Player(name,level,numberOfGuesses,max,generatedInt);
						
						}
					
					//if the level is greater than one and less than or equal to 5, generate x numbers corresponding to the level.
					
					else if (level >= 2 && level <= 5) {
						
						
						int randomInteger;
						ArrayList<Integer> generatedValues = new ArrayList<Integer>();
						
						//use a for loop that generates our random values and stores them into an Integer array list. 
						
						for(int i = 0; i < level; i++) {
							randomInteger = rand.nextInt(10);
							while(generatedValues.contains(randomInteger)) {
								randomInteger = rand.nextInt(10);
							}
							generatedValues.add(randomInteger);
						}
						
						System.out.println("Printing generated values for testing purposes: ");
						for(int num : generatedValues)
							System.out.print(num+" ");
						System.out.println();
						
						JOptionPane.showMessageDialog(null, "Random numbers have been generated. Please clear the guess text box and enter your guess!");
						tempPlayer = new Player(name,level,numberOfGuesses,generatedValues);
						
					}
					else
						JOptionPane.showMessageDialog(null, "Level entered was outside the range of levels. To try again, click the 'Start Game' Button.");

			}
			
			//if the button is our clear display, clear display.
			
			else if(myb.getText().contentEquals("Clear Display Button")) {
				statsTextArea.setText("");
			}
			
			//if the use clicks the display statistics button, there is series of checks we have to go through to determine the correct response.
			
			else if(myb.getText().contentEquals("Display Statistics Button")) {
				
				if(timeCheckBox.isSelected() && difficultyCheckBox.isSelected() && turnsCheckBox.isSelected()) {
						statsTextArea.setText("Invalid Statistics Option. Numbers Game distinguishes top rated players by the amount time played and turns taken.\n"
								+ "Difficulty of games is used as an additonal paramter to further organize results.\n"
								+ "Time and Number of plays cannot both be a paramter. Please try again.");
					
					
				}
				else if(timeCheckBox.isSelected() && turnsCheckBox.isSelected() ) {
					statsTextArea.setText("Invalid Statistics Option. Numbers Game distinguishes top rated players by the amount time played and turns taken.\n"
							+ "Difficulty of games is used as an additonal paramter to further organize results.\n"
							+ "Time and Number of plays cannot both be a paramter. Please try again.");
				}
				
				else if(timeCheckBox.isSelected() && difficultyCheckBox.isSelected()) {
					
					//to use the sorting comparator, we first need to generate an array.
					
					Player[] playerArr = new Player[playerArray.size()];
					int index = 0;
					for (Player player: playerArray) {
						
						playerArr[index] = player;
						index++;
					}
					
					//sort the array
					
					Arrays.sort(playerArr,new TimeSortingComparator());
					
					//iterate through through the sorted array 5 times, each time checking if the level is equivalent to the one were checking for.
					
					statsTextArea.append("Level 5 Results - Number of Time:\n");
					for(Player player : playerArr) {
						if(player.getLevel() == 5)
							statsTextArea.append(player.getName()+": "+player.getTimePlayed()+"\n");
					}
					
					statsTextArea.append("Level 4 Results - Number of Time:\n");
					for(Player player : playerArr) {
						if(player.getLevel() == 4)
							statsTextArea.append(player.getName()+": "+player.getTimePlayed()+"\n");
					}
					
					statsTextArea.append("Level 3 Results - Number of Time:\n");
					for(Player player : playerArr) {
						if(player.getLevel() == 3)
							statsTextArea.append(player.getName()+": "+player.getTimePlayed()+"\n");
					}
					
					statsTextArea.append("Level 2 Results - Number of Time:\n");
					for(Player player : playerArr) {
						if(player.getLevel() == 2)
							statsTextArea.append(player.getName()+": "+player.getTimePlayed()+"\n");
					}
					
					statsTextArea.append("Level 1 Results - Number of Time:\n");
					for(Player player : playerArr) {
						if(player.getLevel() == 1)
							statsTextArea.append(player.getName()+": "+player.getTimePlayed()+"\n");
					}
					
				}
				
				else if(turnsCheckBox.isSelected() && difficultyCheckBox.isSelected()) {
					
					//created the sorted array
					
					Player[] playerArr = new Player[playerArray.size()];
					int index = 0;
					for (Player player: playerArray) {
						
						playerArr[index] = player;
						index++;
					}
					
					Arrays.sort(playerArr,new GuessesSortingComparator());
					
					//display the sorted results by level
					
					statsTextArea.append("Level 5 Results - Number of Guesses:\n");
					for(Player player : playerArr) {
						if(player.getLevel() == 5)
							statsTextArea.append(player.getName()+": "+player.getNumberOfGuesses()+"\n");
					}
					
					statsTextArea.append("Level 4 Results - Number of Guesses:\n");
					for(Player player : playerArr) {
						if(player.getLevel() == 4)
							statsTextArea.append(player.getName()+": "+player.getNumberOfGuesses()+"\n");
					}
					
					statsTextArea.append("Level 3 Results - Number of Guesses:\n");
					for(Player player : playerArr) {
						if(player.getLevel() == 3)
							statsTextArea.append(player.getName()+": "+player.getNumberOfGuesses()+"\n");
					}
					
					statsTextArea.append("Level 2 Results - Number of Guesses:\n");
					for(Player player : playerArr) {
						if(player.getLevel() == 2)
							statsTextArea.append(player.getName()+": "+player.getNumberOfGuesses()+"\n");
					}
					
					statsTextArea.append("Level 1 Results - Number of Guesses:\n");
					for(Player player : playerArr) {
						if(player.getLevel() == 1)
							statsTextArea.append(player.getName()+": "+player.getNumberOfGuesses()+"\n");
					}
				
					
				}
				
				else if(timeCheckBox.isSelected()) {
					
					
					Player[] playerArr = new Player[playerArray.size()];
					int index = 0;
					for (Player player: playerArray) {
						
						playerArr[index] = player;
						index++;
					}
					
					Arrays.sort(playerArr,new TimeSortingComparator());
					statsTextArea.append("Results - Time\n");
					for(Player player : playerArr) {
						statsTextArea.append(""+player.getName()+": "+player.getTimePlayed()+"\n");
					}
				}
				
				else if(turnsCheckBox.isSelected()) {
					
					Player[] playerArr = new Player[playerArray.size()];
					int index = 0;
					for (Player player: playerArray) {
						
						playerArr[index] = player;
						index++;
					}
					
					Arrays.sort(playerArr,new GuessesSortingComparator());
					statsTextArea.append("Results - Number of Guesses\n");
					for(Player player : playerArr) {
						statsTextArea.append(""+player.getName()+": "+player.getNumberOfGuesses()+"\n");
					}
					
				}
				
				else if(difficultyCheckBox.isSelected()) {
					
					//break the players down by level //need to retrieve level and name in no particular order.
					
					statsTextArea.append("Level 5 Results - No Partiular Order:\n");
					for(Player player : playerArray) {
						if(player.getLevel() == 5)
							statsTextArea.append(player.getName()+"\n");
					}
					
					statsTextArea.append("Level 4 Results - No Partiular Order:\n");
					for(Player player : playerArray) {
						if(player.getLevel() == 4)
							statsTextArea.append(player.getName()+"\n");
					}
					
					statsTextArea.append("Level 3 Results - No Partiular Order:\n");
					for(Player player : playerArray) {
						if(player.getLevel() == 3)
							statsTextArea.append(player.getName()+"\n");
					}
					
					statsTextArea.append("Level 2 Results - No Partiular Order:\n");
					for(Player player : playerArray) {
						if(player.getLevel() == 2)
							statsTextArea.append(player.getName()+"\n");
					}
					
					statsTextArea.append("Level 1 Results - No Partiular Order:\n");
					for(Player player : playerArray) {
						if(player.getLevel() == 1)
							statsTextArea.append(player.getName()+"\n");
					}
				
				}
			}	
		}	
	}
}
