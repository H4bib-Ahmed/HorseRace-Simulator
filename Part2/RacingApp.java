import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.*;

public class RacingApp extends JFrame {
    private JButton startRaceButton;
    private JButton customizeHorseButton; 
    private JButton changeColorButton;
    private JButton displayBalanceButton;
    private JButton placeBetButton; // New button to place bets
    private JTextField trackLengthField;
    private JTextArea outputArea;
    private Horse[] horses;
    private JPanel trackPanel;
    private JButton displayWinnings;
    private BettingSystem bettingSystem; // Betting system for virtual bets


    public RacingApp() {
        setTitle("Racing Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Control panel with all buttons
        JPanel controlPanel = new JPanel(new FlowLayout());
        JLabel title = new JLabel("Horse Racing Simulation");
        displayBalanceButton = new JButton("Display Balance");
        displayWinnings = new JButton("Display Winnings");
        startRaceButton = new JButton("Start Race");
        customizeHorseButton = new JButton("Customize Horse");
        changeColorButton = new JButton("Change Track Color");
        placeBetButton = new JButton("Place Bet"); 
        trackLengthField = new JTextField("30", 10); // Default track length

        controlPanel.add(title);
        controlPanel.add(trackLengthField);
        controlPanel.add(startRaceButton);
        controlPanel.add(customizeHorseButton);
        controlPanel.add(changeColorButton);
        controlPanel.add(placeBetButton); // Add the place bet button to the control panel
        controlPanel.add(displayBalanceButton);
        controlPanel.add(displayWinnings);

        

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        PrintStream printStream = new PrintStream(new TextAreaOutputStream(outputArea));
        System.setOut(printStream);

        trackPanel = new JPanel();
        trackPanel.setBackground(Color.WHITE);
        mainPanel.add(trackPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Initialize the horses
        horses = new Horse[]{
                new Horse('H', "PIPPI LONGSTOCKING", 0.6),
                new Horse('X', "KOKOMO", 0.7),
                new Horse('Y', "EL JEFE", 0.5)
        };

        // Initialize the betting system with a default balance
        bettingSystem = new BettingSystem();

        addListeners(); // Add action listeners for buttons
    }

    private void addListeners() {
        // Listener for the Start Race button
        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int trackLength;
                try {
                    trackLength = Integer.parseInt(trackLengthField.getText());
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid track length.");
                    return;
                }

                new Thread(() -> {
                    Race race = new Race(trackLength);
                    race.addHorse(horses[0], 1);
                    race.addHorse(horses[1], 2);
                    race.addHorse(horses[2], 3);
                    race.startRace();

                    System.out.println("Race completed!");

                    // Process betting outcomes (assuming race has a way to get winners)
                    String winner = "Horse 1"; // This should come from the race result
                    bettingSystem.processBetOutcome(winner, true, bettingSystem.getOdds().get(winner));
                }).start(); // Separate thread to avoid GUI freeze
            }
        });


        // Listener for Display Balance button
        displayBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaceBetDialog dialog = new PlaceBetDialog(bettingSystem, horses);
                dialog.displayBalance(bettingSystem);
            }
        });

        // Listener for Display Winnings button
        
        // Listener for Customize Horse button
        customizeHorseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomHorseDialog customDialog = new CustomHorseDialog(horses); // Open custom dialog
                customDialog.setVisible(true);
            }
        });

        // Listener for Place Bet button
        placeBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaceBetDialog dialog = new PlaceBetDialog(bettingSystem, horses); // Dialog to place bets
                dialog.setVisible(true);
            }
        });

        // Listener for Change Color button
        changeColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(RacingApp.this, "Choose Track Color", trackPanel.getBackground());
                if (newColor != null) {
                    outputArea.setBackground(newColor);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RacingApp().setVisible(true);
        });
    }

    // Custom OutputStream to redirect System.out to JTextArea
    class TextAreaOutputStream extends java.io.OutputStream {
        private JTextArea textArea;

        public TextAreaOutputStream(JTextArea textArea) {
            this.textArea = textArea;
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        }

        @Override
        public void write(int b) {
            textArea.append(String.valueOf((char) b));
            textArea.setCaretPosition(textArea.getDocument().getLength()); // Auto-scroll to the bottom
        }
    }
}


// Dialog for placing bets
class PlaceBetDialog extends JDialog {
    public PlaceBetDialog(BettingSystem bettingSystem, Horse[] horses) {
        setTitle("Place Bet");
        setSize(300, 200);
        setLayout(new BorderLayout());

        JPanel betPanel = new JPanel(new GridLayout(2, 2));

        JComboBox<String> horseSelector = new JComboBox<>(new String[]{
                "Horse 1",
                "Horse 2",
                "Horse 3"
        });
        JTextField betAmountField = new JTextField("100", 10); // Default bet amount

        betPanel.add(new JLabel("Select Horse:"));
        betPanel.add(horseSelector);
        betPanel.add(new JLabel("Bet Amount:"));
        betPanel.add(betAmountField);

        add(betPanel, BorderLayout.CENTER);

        // Save and Cancel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton placeBetButton = new JButton("Place Bet");
        JButton cancelBetButton = new JButton("Cancel");

        buttonPanel.add(placeBetButton);
        buttonPanel.add(cancelBetButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action to place the bet
        placeBetButton.addActionListener(e -> {
            int betAmount;
            try {
                betAmount = Integer.parseInt(betAmountField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid bet amount.");
                return;
            }

            String selectedHorse = horseSelector.getSelectedItem().toString();
            if (bettingSystem.placeBet(selectedHorse, betAmount)) {
                JOptionPane.showMessageDialog(this, "Bet placed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient virtual currency.");
            }

            dispose(); // Close the dialog
        });

        // Cancel button action
        cancelBetButton.addActionListener(e -> dispose());
    }

    // display balance
    public void displayBalance(BettingSystem bettingSystem) {
        JOptionPane.showMessageDialog(this, "Current balance: " + bettingSystem.getVirtualCurrency());
    }

    
    // display winnings
    public void displayWinnings(BettingSystem bettingSystem) {
        JOptionPane.showMessageDialog(this, "Total winnings: " + bettingSystem.getVirtualCurrency());
    }

    // display odds
    public void displayOdds(BettingSystem bettingSystem) {
        Map<String, Double> odds = bettingSystem.getOdds();
        StringBuilder oddsText = new StringBuilder("Current Odds:\n");
        for (Map.Entry<String, Double> entry : odds.entrySet()) {
            oddsText.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(this, oddsText.toString());
    }

    

}


// Custom Horse Dialog with Unicode Symbol Selection
class CustomHorseDialog extends JDialog {
    private Horse[] horses; // Array of horses to customize
    private JTextField nameField;
    private JComboBox<Character> symbolComboBox; // ComboBox for Unicode symbols
    private JComboBox<String> horseSelector;
    private JButton saveButton;
    private JButton cancelButton;

    public CustomHorseDialog(Horse[] horses) {
        this.horses = horses;

        setTitle("Customize Horse");
        setSize(300, 200);
        setLayout(new BorderLayout());

        JPanel customPanel = new JPanel(new GridLayout(3, 2)); // 3 rows, 2 columns

        // Horse selection
        horseSelector = new JComboBox<>(new String[]{
                "Horse 1",
                "Horse 2",
                "Horse 3"
        });
        customPanel.add(new JLabel("Select Horse:"));
        customPanel.add(horseSelector);

        // Horse name field
        nameField = new JTextField();
        customPanel.add(new JLabel("Horse Name:"));
        customPanel.add(nameField);

        // Unicode symbol selection
        symbolComboBox = new JComboBox<>(new Character[]{
                '\u265E', // Black Chess Knight
                '\u265F', // Black Chess Pawn
                '\u265A', // Black Chess King
                '\u265B', // Black Chess Queen
                '\u265C'  // Black Chess Rook
        });
        customPanel.add(new JLabel("Select Horse Symbol:"));
        customPanel.add(symbolComboBox);

        add(customPanel, BorderLayout.CENTER);

        // Save and Cancel buttons
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Save and Cancel actions
        saveButton.addActionListener(e -> {
            int selectedHorseIndex = horseSelector.getSelectedIndex();
            Horse selectedHorse = horses[selectedHorseIndex];

            // Set horse name and Unicode symbol
            selectedHorse.setName(nameField.getText());
            selectedHorse.setSymbol((char) symbolComboBox.getSelectedItem());

            // Close the dialog after saving
            dispose();
        });

        cancelButton.addActionListener(e -> {
            dispose(); // Close the dialog without saving
        });
    }

}