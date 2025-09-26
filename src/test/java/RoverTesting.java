import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;



public class RoverTesting {
    //test 1, test left and right turn
    @Test
    void testTurn(){
        Rover rover = new Rover(0, 0, Rover.Direction.NORTH);
    }
    //test 2, ensure rover cannot leave bounds
    @Test
    void testBounds(){
        Rover rover = new Rover(0, 0, Rover.Direction.SOUTH);
        rover.move();
        Assertions.assertEquals(0, rover.position[1]);
        rover.directionfacing = Rover.Direction.WEST;
        rover.move();
        Assertions.assertEquals(0, rover.position[0]);
    }
    //test 3, test if rover properly moves in the correct direction
    @Test 
    void testMove(){
        Rover rover = new Rover(1, 1, Rover.Direction.NORTH);
        rover.move();
        Assertions.assertEquals(1, rover.position[0]);
        Assertions.assertEquals(2, rover.position[1]);
        rover.directionfacing = Rover.Direction.EAST;
        rover.move();
        Assertions.assertEquals(2, rover.position[0]);
        Assertions.assertEquals(2, rover.position[1]);
    }
}
