import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private JTextField guessField;
    private JLabel feedbackLabel;
    private JButton guessButton;
    private int randomNumber;
    private int attempts;
    public NumberGuessingGame() {    
        setTitle("Number Guessing Game");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());  
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;
        attempts = 0;
        JLabel promptLabel = new JLabel("Guess a number between 1 and 100: ");
        guessField = new JTextField(10);
        feedbackLabel = new JLabel(" ");
        guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });        
        add(promptLabel);
        add(guessField);
        add(guessButton);
        add(feedbackLabel);
        setVisible(true);
    }
    private void checkGuess() {
        try {
            int userGuess = Integer.parseInt(guessField.getText());
            attempts++;

            if (userGuess < randomNumber) {
                feedbackLabel.setText("Too low! Try again.");
            } else if (userGuess > randomNumber) {
                feedbackLabel.setText("Too high! Try again.");
            } else {
                feedbackLabel.setText("Correct! You guessed it in " + attempts + " attempts.");
                guessButton.setEnabled(false);
            }

            
            guessField.setText("");
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        new NumberGuessingGame();
    }
}
