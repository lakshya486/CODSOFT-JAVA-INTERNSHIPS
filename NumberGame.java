import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGame extends JFrame implements ActionListener {

    private final int minRange = 1;
    private final int maxRange = 100;
    private final int attemptsLimit = 5;
    private int randomNumber;
    private int attempts;
    private boolean guessedCorrectly;

    private JLabel titleLabel, messageLabel, attemptsLabel;
    private JTextField guessField;
    private JButton guessButton, newGameButton;

    public NumberGame() {
        super("Number Guessing Game");

        titleLabel = new JLabel("Welcome to the Number Guessing Game!");
        messageLabel = new JLabel("");
        attemptsLabel = new JLabel("Attempts: 0/" + attemptsLimit);
        guessField = new JTextField(5);
        guessButton = new JButton("Guess");
        newGameButton = new JButton("New Game");

        guessButton.addActionListener(this);
        newGameButton.addActionListener(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        panel.add(titleLabel, gbc);
        panel.add(messageLabel, gbc);
        panel.add(attemptsLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Enter your guess:"), gbc);
        panel.add(guessField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(guessButton);
        buttonPanel.add(newGameButton);
        panel.add(buttonPanel, gbc);

        add(panel);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startGame();
    }

    private void startGame() {
        // Explicitly seed the Random object for better variation
        randomNumber = new Random(System.currentTimeMillis()).nextInt(maxRange - minRange + 1) + minRange;
        attempts = 0;
        guessedCorrectly = false;
        updateMessage();
        updateAttemptsLabel();
        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
    }

    private void updateMessage() {
        if (guessedCorrectly) {
            messageLabel.setText("Congratulations! You guessed the number!");
        } else {
            messageLabel.setText("Guess a number between " + minRange + " and " + maxRange);
        }
    }

    private void updateAttemptsLabel() {
        attemptsLabel.setText("Attempts: " + attempts + "/" + attemptsLimit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            if (!guessedCorrectly) {
                try {
                    int userGuess = Integer.parseInt(guessField.getText());
                    attempts++;
                    updateAttemptsLabel();
                    if (userGuess == randomNumber) {
                        guessedCorrectly = true;
                        updateMessage();
                        guessField.setEnabled(false);
                        guessButton.setEnabled(false);
                    } else if (userGuess < randomNumber) {
                        messageLabel.setText("Too low! Try again.");
                    } else {
                        messageLabel.setText("Too high! Try again.");
                    }
                    if (attempts >= attemptsLimit && !guessedCorrectly) {
                        messageLabel.setText("Sorry, you've run out of attempts. The correct number was: " + randomNumber);
                        guessField.setEnabled(false);
                        guessButton.setEnabled(false);
                    }
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Invalid input. Please enter a number.");
                }
            }
        } else if (e.getSource() == newGameButton) {
            startGame();
        }
    }

    public static void main(String[] args) {
        new NumberGame();
    }
}
