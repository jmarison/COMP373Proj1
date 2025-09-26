<<<<<<< HEAD:src/java/Main.java
=======
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


>>>>>>> 4dcbcbe786d2bc1ecbb03ad20f72af254a4b9aec:src/Main.java

public class Main {
	public static void main(String[] args){
		// Scanner for reading all user input from standard input...
		java.util.Scanner scanner = new java.util.Scanner(System.in);

		// Plateau Setup...
		System.out.println("Enter plateau upper-right coordinates (for example: \"5 5\"):");
		String plateauLine = scanner.nextLine().trim();
		String[] plateauParts = plateauLine.split("\\s+");
		if(plateauParts.length < 2){
			System.err.println("Invalid plateau size");
			return; // Abort if the plateau cannot be parsed.
		}
		// Upper-right corner (0,0) is implied as the lower-left...
		int maxX = Integer.parseInt(plateauParts[0]);
		int maxY = Integer.parseInt(plateauParts[1]);

		// Input instructions for users...
		System.out.println("Enter rover positions and commands. For each rover, provide two lines:\n" +
				"1) initial position and direction (for example: \"1 2 N\")\n" +
				"2) instructions for rover (for example: \"LMLMLMLMM\").\n");

		// Process rovers until input ends...
		while(true){
			if(!scanner.hasNextLine()) break;				// Stop on EOF
			String posLine = scanner.nextLine().trim();		// Line 1: Initial position & direction
			if(posLine.isEmpty()) continue;					// Skip blank lines between rover blocks

			if(!scanner.hasNextLine()){
				// If we got a position but no command line, that's an input error...
				System.err.println("Expected command line after position");
				break;
			}
			String cmdLine = scanner.nextLine().trim();		// Line 2: command string

			// Parse initial rover state...
			String[] parts = posLine.split("\\s+");
			if(parts.length < 3){
				System.err.println("Invalid rover position");
				continue;  // Skip this rover and try to read the next pair
			}

			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			String d = parts[2].toUpperCase();		// Normalize direction to uppercase

			// Map the single-letter direction to Rover.Direction enum
			Rover.Direction dir;
			switch(d){
				case "N": dir = Rover.Direction.NORTH; break;
				case "E": dir = Rover.Direction.EAST; break;
				case "S": dir = Rover.Direction.SOUTH; break;
				case "W": dir = Rover.Direction.WEST; break;
				default:
					System.err.println("Unknown direction");
					continue;  // Skip invalid rover declarations
			}

			// Create the rover at the requested starting pose
			Rover rover = new Rover(x, y, dir);

			// Execute the provided command sequence, enforcing plateau bounds
			executeCommands(rover, cmdLine, maxX, maxY);

			// Print rover's final state (relies on Rover.toString())
			System.out.println(rover);
		}

		// Clean up resources......
		scanner.close();
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
					rover.move();
					if(rover.position[0] < 0) rover.position[0] = 0;
					if(rover.position[1] < 0) rover.position[1] = 0;
					if(rover.position[0] > maxX) rover.position[0] = maxX;
					if(rover.position[1] > maxY) rover.position[1] = maxY;
					break;
			}
		}
	}

}