
public class Rover {
    public int[] position = new int[2];
    public enum Direction {NORTH, EAST, SOUTH, WEST}
    public enum Turn {LEFT, RIGHT}
    public Direction directionfacing;

    public Rover(int x, int y, Direction direction) {
        this.position = new int[]{x, y};
        this.directionfacing = direction;
    }

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

    @Override
    public String toString(){
        return position[0] + " " + position[1] + " " + directionfacing.name();
    }

    public Direction getDirection(){
        return this.directionfacing;
    }

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
