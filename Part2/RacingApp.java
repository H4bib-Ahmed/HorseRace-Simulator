import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

public class RacingApp extends JFrame {
    private JButton startRaceButton;
    private JButton customizeHorseButton; // Button to customize horses
    private JTextField trackLengthField;
    private JTextArea outputArea;
    private Horse[] horses; // Array to store horses

    public RacingApp() {
        setTitle("Racing Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel controlPanel = new JPanel(new FlowLayout());
        startRaceButton = new JButton("Start Race");
        customizeHorseButton = new JButton("Customize Horse");
        trackLengthField = new JTextField("30", 10); // Default track length
        controlPanel.add(trackLengthField);
        controlPanel.add(startRaceButton);
        controlPanel.add(customizeHorseButton);

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Redirect System.out to JTextArea
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(outputArea));
        System.setOut(printStream); // Redirect System.out

        add(mainPanel);

        // Initialize horses
        horses = new Horse[]{
            new Horse('H', "PIPPI LONGSTOCKING", 0.6),
            new Horse('X', "KOKOMO", 0.7),
            new Horse('Y', "EL JEFE", 0.5)
        };

        addListeners(); // Add action listeners for buttons
    }

    private void addListeners() {
        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int trackLength;
                try {
                    trackLength = Integer.parseInt(trackLengthField.getText());
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid track length. Please enter a valid number.");
                    return;
                }

                new Thread(() -> {
                    Race race = new Race(trackLength);

                    // Add horses to the race
                    race.addHorse(horses[0], 1);
                    race.addHorse(horses[1], 2);
                    race.addHorse(horses[2], 3);

                    System.out.println("Starting the race...");
                    race.startRace(); // Output redirected to JTextArea
                    System.out.println("Race completed!");
                }).start(); // Separate thread to avoid GUI freeze
            }
        });

        customizeHorseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomHorseDialog customDialog = new CustomHorseDialog(horses); // Open custom dialog
                customDialog.setVisible(true); // Show the dialog
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




