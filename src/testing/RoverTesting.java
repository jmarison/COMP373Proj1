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
}
