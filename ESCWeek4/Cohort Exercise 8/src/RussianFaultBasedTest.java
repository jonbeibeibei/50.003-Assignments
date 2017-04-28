import org.junit.Before;
import org.junit.Test;

/**
 * Created by jonathanbeiqiyang on 17/2/17.
 */
public class RussianFaultBasedTest {

    Russian ru;
    @Before
    public void runBeforeEachTest(){
        ru = new Russian();
    }

    @Test
    public void testRussianNegative(){ //Fault based Testing
        int ans = ru.multiply(2,-4);
        assert (ans == -8);
    }
}
