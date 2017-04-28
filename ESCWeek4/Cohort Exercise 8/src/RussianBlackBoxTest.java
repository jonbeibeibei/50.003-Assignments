import org.junit.Before;
import org.junit.Test;

/**
 * Created by jonathanbeiqiyang on 17/2/17.
 */
public class RussianBlackBoxTest {
    Russian ru;
    @Before
    public void runBeforeEachTest(){
        ru = new Russian();
    }

    @Test
    public void testNull(){ // Black Box
        Integer a = null;
        Integer b = null;
        int ans = ru.multiply(a, b);
    }
}
