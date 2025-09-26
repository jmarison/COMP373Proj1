
public class Main {
	public static void main(String[] args){
		java.util.Scanner scanner = new java.util.Scanner(System.in);

		System.out.println("Enter plateau upper-right coordinates (for example: \"5 5\"):");
		String plateauLine = scanner.nextLine().trim();
		String[] plateauParts = plateauLine.split("\\s+");
		if(plateauParts.length < 2){
			System.err.println("Invalid plateau size");
			return;
		}
		int maxX = Integer.parseInt(plateauParts[0]);
		int maxY = Integer.parseInt(plateauParts[1]);

		System.out.println("Enter rover positions and commands. For each rover, provide two lines:\n" +
				"1) initial position and direction (for example: \"1 2 N\")\n" +
				"2) instructions for rover (for example: \"LMLMLMLMM\").\n");

		while(true){
			if(!scanner.hasNextLine()) break;
			String posLine = scanner.nextLine().trim();
			if(posLine.isEmpty()) continue;

			if(!scanner.hasNextLine()){
				System.err.println("Expected command line after position");
				break;
			}
			String cmdLine = scanner.nextLine().trim();

			String[] parts = posLine.split("\\s+");
			if(parts.length < 3){
				System.err.println("Invalid rover position");
				continue;
			}

			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			String d = parts[2].toUpperCase();
			Rover.Direction dir;
			switch(d){
				case "N": dir = Rover.Direction.NORTH; break;
				case "E": dir = Rover.Direction.EAST; break;
				case "S": dir = Rover.Direction.SOUTH; break;
				case "W": dir = Rover.Direction.WEST; break;
				default:
					System.err.println("Unknown direction");
					continue;
			}

			Rover rover = new Rover(x, y, dir);
			executeCommands(rover, cmdLine, maxX, maxY);
			System.out.println(rover);
		}
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