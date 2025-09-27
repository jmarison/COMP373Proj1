/**
 * Represents a Mars Rover that an move on a rectangular plateau.
 *
 * Responsibilities:
 *   - Store the rover's current position (x, y) and facing direction.
 *   - Turn left or right relative to its current direction.
 *   - Move forward one grid cell if within plateau bounds.
 *   - Prevent moves that would push the rover outside the plateau.
 *
 * Notes:
 *   - Plateau boundaries are provided externally (maxX, maxY).
 *   - Position is stored as a 2-element int array [x, y].
 */
public class Rover {
    /** Current position of the rover: index 0 = x-coordinate, index 1 = y-coordinate */
    public int[] position = new int[2];

    /** Possible directions the rover can face */
    public enum Direction {NORTH, EAST, SOUTH, WEST}

    /** Possible turning actions relative to current direction */
    public enum Turn {LEFT, RIGHT}

    /** The current direction the rover is facing */
    public Direction directionfacing;

    /**
     * Constructs a rover at a given starting position and direction.
     *
     * @param x starting x-coordinate
     * @param y starting y-coordinate
     * @param direction initial direction facing
     */
    public Rover(int x, int y, Direction direction) {
        this.position = new int[]{x, y};
        this.directionfacing = direction;
    }

    /**
     * Turns the rover 90 degrees left or right relative to its current direction.
     *
     * @param turnDirection the turn (LEFT or RIGHT)
     */
    public void turn(Turn turnDirection){
        switch(directionfacing){
            case NORTH:
                directionfacing = (turnDirection == Turn.LEFT) ? Direction.WEST : Direction.EAST;
                break;
            case EAST:
                directionfacing = (turnDirection == Turn.LEFT) ? Direction.NORTH : Direction.SOUTH;
                break;
            case SOUTH:
                directionfacing = (turnDirection == Turn.LEFT) ? Direction.EAST : Direction.WEST;
                break;
            case WEST:
                directionfacing = (turnDirection == Turn.LEFT) ? Direction.SOUTH : Direction.NORTH;
                break;
        }
    }

    /**
     * Moves the rover forward one grid cell if the move is within plateau bounds.
     *
     * @param maxX the maximum x-coordinate of the plateau
     * @param maxY the maximum y-coordinate of the plateau
     */
    public void move(int maxX, int maxY){
        if (canMove(maxX, maxY)){
            switch(directionfacing){
                case NORTH:
                    this.position[1] += 1;
                    break;
                case EAST:
                    this.position[0] += 1;
                    break;
                case SOUTH:
                    this.position[1] -= 1;
                    break;
                case WEST:
                    this.position[0] -= 1;
                    break;
            }
        }
    }

    /**
     * Returns a string representation of the rover's state.
     * Example: "1 3 NORTH"
     */
    @Override
    public String toString(){
        return position[0] + " " + position[1] + " " + directionfacing.name();
    }

    /**
     * Gets the rover's current facing direction.
     *
     * @return the current direction
     */
    public Direction getDirection(){
        return this.directionfacing;
    }

    /**
     * Determines whether the rover can move forward without leaving plateau bounds.
     *
     * @param maxX the maximum x-coordinate of the plateau
     * @param maxY the maximum y-coordinate of the plateau
     * @return true if the move is possible, false if it would exceed bounds
     */
    public boolean canMove(int maxX, int maxY){
        boolean canMove = true;
        Direction dir = this.getDirection();
					switch(dir){
						case NORTH:
							if(this.position[1] == maxY) canMove = false;
							break;
						case EAST:
							if(this.position[0] == maxX) canMove = false;
							break;
						case SOUTH:
							if(this.position[1] == 0) canMove = false;
							break;
						case WEST:
							if(this.position[0] == 0) canMove = false;
							break;
					}
					return canMove;
    }
}