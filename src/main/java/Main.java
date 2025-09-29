import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Entry point and GUI for the Mars Rover Kata.
 *
 * Responsibilities:
 * - Provide a simple Swing-based interface for entering input.
 * - Collect plateau size, rover starting position and command sequence.
 * - Create a Rover object, execute the commands and display results.
 *
 * Notes:
 * - Parsing and input validation is handled here.
 * - Rover class is responsible for movement and turning logic.
 */

public class Main {
    public static void main(String[] args) {

        // *** Frame Setup ***
        JFrame frame = new JFrame("Mars Rover");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
        frame.setLayout(new BorderLayout());

        // *** Input Panel (Top Section) ***
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 5, 5)); // 4 rows, 2 columns

        // Input Fields
        JTextField plateauField = new JTextField(); // e.g., "5 5"
        JTextField roverPosField = new JTextField(); // e.g., "1 2 N"
        JTextField roverCmdField = new JTextField(); // e.g., "LMLMRM"
        JButton runButton = new JButton("Run Commands");

        // Labels + fields in grid
        panel.add(new JLabel("Plateau upper-right (e.g., 5 5):"));
        panel.add(plateauField);
        panel.add(new JLabel("Rover position & direction (e.g., 1 2 N):"));
        panel.add(roverPosField);
        panel.add(new JLabel("Rover commands (e.g., LMLMRLMLMRM):"));
        panel.add(roverCmdField);
        panel.add(new JLabel());
        panel.add(runButton);

        // *** Output Area (Center Section) ***
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        PlateauPanel plateauPanel = new PlateauPanel(5, 5);

        // SplitPane to hold grid (left) and log (right)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, plateauPanel, scrollPane);
        splitPane.setDividerLocation(600);

        // Add components to frame...
        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(splitPane, BorderLayout.CENTER);
        // plateauPanel = new PlateauPanel();
        // frame.add(plateauPanel, BorderLayout.CENTER);

        // *** Button Logic ***
        runButton.addActionListener(e -> {
            String plateauText = plateauField.getText().trim();
            String posText = roverPosField.getText().trim();
            String cmdText = roverCmdField.getText().trim();

            try {
                // Parse plateau bounds...
                String[] plateauParts = plateauText.split("\\s+");
                if (plateauParts.length != 2)
                    throw new IllegalArgumentException("Plateau must have two numbers.");
                int maxX = Integer.parseInt(plateauParts[0]);
                int maxY = Integer.parseInt(plateauParts[1]);

                // Parse rover starting position...
                String[] parts = posText.split("\\s+");
                if (parts.length != 3)
                    throw new IllegalArgumentException("Rover position must be in format 'X Y D'.");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                String dirStr = parts[2].toUpperCase();

                // Map direction letter to enum...
                Rover.Direction dir;
                switch (dirStr) {
                    case "N":
                        dir = Rover.Direction.NORTH;
                        break;
                    case "E":
                        dir = Rover.Direction.EAST;
                        break;
                    case "S":
                        dir = Rover.Direction.SOUTH;
                        break;
                    case "W":
                        dir = Rover.Direction.WEST;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown direction: " + dirStr);
                }

                // Create rover and execute commands...
                Rover rover = new Rover(x, y, dir);
                List<Point> path = executeCommands(rover, cmdText, maxX, maxY);

                plateauPanel.setBounds(maxX, maxY);
                plateauPanel.updatePath(path, rover.getDirection());

                // Display result...
                outputArea.append("Rover final position: " + rover + "\n");

                // Clear inputs for next run...
                plateauField.setText("");
                roverPosField.setText("");
                roverCmdField.setText("");

            } catch (Exception ex) {
                // Show error dialog for invalid input...
                JOptionPane.showMessageDialog(frame,
                        "Error: " + ex.getMessage(),
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Show window...
        frame.setVisible(true);
    }

    /**
     * Executes a string of commands ('L', 'R', 'M') on a rover.
     * Delegates to rover.turn() and rover.move().
     */
    public static List<Point> executeCommands(Rover rover, String commands, int maxX, int maxY) {
        List<Point> path = new ArrayList<>();
        path.add(new Point(rover.getX(), rover.getY()));
        for (char c : commands.toCharArray()) {
            switch (c) {
                case 'L':
                    rover.turn(Rover.Turn.LEFT);
                    break;
                case 'R':
                    rover.turn(Rover.Turn.RIGHT);
                    break;
                case 'M':
                    rover.move(maxX, maxY);
                    break;
                // No default case: ignores invalid commands
            }
            path.add(new Point(rover.getX(), rover.getY()));
        }
        return path;
    }
}