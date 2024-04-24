import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

public class RacingApp extends JFrame {
    private JButton startRaceButton; // Start Race button
    private JTextArea outputArea;
    private Horse[] horses; // Array to store horses

    public RacingApp() {
        setTitle("Racing Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Control panel with only the Start Race button
        JPanel controlPanel = new JPanel(new FlowLayout());
        startRaceButton = new JButton("Start Race");
        controlPanel.add(startRaceButton);

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Output area to display race output
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Redirect System.out to JTextArea
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(outputArea));
        System.setOut(printStream); // Redirect System.out

        add(mainPanel);

        // Initialize horses with fixed data
        horses = new Horse[]{
            new Horse('\u265E', "Horse 1", 0.8),
            new Horse('\u265F', "Horse 2", 0.5),
            new Horse('\u265A', "Horse 3", 0.6)
        };

        addListeners(); // Add action listeners for buttons
    }

    private void addListeners() {
        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    int trackLength = 30; // Fixed track length
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





