import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

/**
 * Created by jonathanbeiqiyang on 17/2/17.
 */
public class RussianWhiteBoxTest {
    Russian ru;
    @Before
    public void runBeforeEachTest(){
        ru = new Russian();
    }


    @Test
    public void testRussianOdd(){ // White Box
        int ans = ru.multiply(4,3);
        assert (ans == 12);
    }
    @Test
    public void testRussianEven(){ //White Box
        int ans = ru.multiply(2,4);
        assert (ans == 8);
    }

    @Test
    public void testRussianZero(){
        int ans = ru.multiply(0,4); //White Box
        assert (ans == 0);
    }




}
