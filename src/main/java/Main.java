import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Entry point and CLI for the Mars Rover Kata.
 *
 * Responsibilities:
 *   - Read plateau dimensions from stdin.
 *   - Read pairs of lines for each rover (initial position/direction, then commands).
 *   - Instantiate a Rover and execute its commands.
 *   - Print each rover's final state.
 *
 * Notes:
 *   - Keeps paring/IO concerns here and delegates movement/turning to Rover.
 *   - Assumes Rover exposes Direction and Turn enums and a public position[].
 */

public class Main {
	public static void main(String[] args){

		JFrame frame = new JFrame("Mars Rover");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1080, 720);
		frame.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2, 5, 5));

		JTextField plateauField = new JTextField();
        JTextField roverPosField = new JTextField();
        JTextField roverCmdField = new JTextField();
        JButton runButton = new JButton("Run Commands");

		panel.add(new JLabel("Plateau upper-right (e.g., 5 5):"));
        panel.add(plateauField);
        panel.add(new JLabel("Rover position & direction (e.g., 1 2 N):"));
        panel.add(roverPosField);
        panel.add(new JLabel("Rover commands (e.g., LMLMRLMLMRM):"));
        panel.add(roverCmdField);
        panel.add(new JLabel());
        panel.add(runButton);
		
		JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

		frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
		//plateauPanel = new PlateauPanel();
        //frame.add(plateauPanel, BorderLayout.CENTER);

		runButton.addActionListener(e -> {
            String plateauText = plateauField.getText().trim();
            String posText = roverPosField.getText().trim();
            String cmdText = roverCmdField.getText().trim();
			
			try {
                String[] plateauParts = plateauText.split("\\s+");
				if (plateauParts.length != 2) throw new IllegalArgumentException("Plateau must have two numbers.");
                int maxX = Integer.parseInt(plateauParts[0]);
                int maxY = Integer.parseInt(plateauParts[1]);

                String[] parts = posText.split("\\s+");
				if (parts.length != 3) throw new IllegalArgumentException("Rover position must be in format 'X Y D'.");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                String dirStr = parts[2].toUpperCase();

                Rover.Direction dir;
                switch (dirStr) {
					case "N": dir = Rover.Direction.NORTH; break;
                    case "E": dir = Rover.Direction.EAST; break;
                    case "S": dir = Rover.Direction.SOUTH; break;
                    case "W": dir = Rover.Direction.WEST; break;
                    default: throw new IllegalArgumentException("Unknown direction: " + dirStr);
                }
			Rover rover = new Rover(x, y, dir);
            executeCommands(rover, cmdText, maxX, maxY);
            outputArea.append("Rover final position: " + rover + "\n");
			 } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
				 "Error: " + ex.getMessage(),
                 "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
	 frame.setVisible(true);
	}


	public static void executeCommands(Rover rover, String commands, int maxX, int maxY){
		for(char c : commands.toCharArray()){
			switch(c){
				case 'L':
					rover.turn(Rover.Turn.LEFT);
					break;
				case 'R':
					rover.turn(Rover.Turn.RIGHT);
					break;
				case 'M':
					rover.move(maxX, maxY);
					break;
			}
		}
	}

	

}