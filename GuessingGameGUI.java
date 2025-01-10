import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class GuessingGameGUI extends JFrame {
    private int numberToGuess;
    private int attempts;
    private final int maxAttempts = 5;
    private int totalScore = 0;
    
    private JTextField guessField;
    private JButton guessButton;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JButton playAgainButton;

    public GuessingGameGUI() {
        setTitle("Number Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new GridLayout(6, 1));
        
        JLabel instructionLabel = new JLabel("Guess the number between 1 and 100:");
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(instructionLabel);

        guessField = new JTextField();
        guessField.setHorizontalAlignment(JTextField.CENTER);
        add(guessField);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(new GuessButtonListener());
        add(guessButton);
        
        feedbackLabel = new JLabel(" ");
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(feedbackLabel);

        attemptsLabel = new JLabel("Attempts left: " + maxAttempts);
        attemptsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(attemptsLabel);

        scoreLabel = new JLabel("Total Score: " + totalScore);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreLabel);

        playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(new PlayAgainButtonListener());
        playAgainButton.setVisible(false);
        add(playAgainButton);

        resetGame();
    }

    private void resetGame() {
        Random random = new Random();
        numberToGuess = random.nextInt(100) + 1;
        attempts = 0;
        feedbackLabel.setText(" ");
        attemptsLabel.setText("Attempts left: " + maxAttempts);
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        playAgainButton.setVisible(false);
    }

    private void endRound(boolean guessedCorrectly) {
        guessField.setEnabled(false);
        guessButton.setEnabled(false);
        playAgainButton.setVisible(true);
        
        if (guessedCorrectly) {
            int roundScore = maxAttempts - attempts;
            totalScore += roundScore;
            feedbackLabel.setText("Correct! You scored " + roundScore + " points this round.");
        } else {
            feedbackLabel.setText("No attempts left. The number was: " + numberToGuess);
        }

        scoreLabel.setText("Total Score: " + totalScore);
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String guessText = guessField.getText();
            try {
                int userGuess = Integer.parseInt(guessText);
                attempts++;
                attemptsLabel.setText("Attempts left: " + (maxAttempts - attempts));
                
                if (userGuess == numberToGuess) {
                    endRound(true);
                } else if (userGuess > numberToGuess) {
                    feedbackLabel.setText("Too high! Try again.");
                } else {
                    feedbackLabel.setText("Too low! Try again.");
                }

                if (attempts >= maxAttempts && !feedbackLabel.getText().contains("Correct")) {
                    endRound(false);
                }

            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number.");
            }

            guessField.setText("");
        }
    }

    private class PlayAgainButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuessingGameGUI game = new GuessingGameGUI();
            game.setVisible(true);
        });
    }
}
