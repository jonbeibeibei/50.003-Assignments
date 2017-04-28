import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by jonathanbeiqiyang on 13/2/17.
 */

public class FindMaxTest {
    @Test
    public void max(){
        FindMax a = new FindMax();
        int[] c = {1,2,3,4};
        int ans = a.max(c);
        assertTrue(ans==4);
    }

    @Test
    public void maxError(){
        FindMax a = new FindMax();
        int[] c = {};
        int ans = a.max(c);
    }

    @Test
    public  void maxFail(){
        FindMax a = new FindMax();
        int[] c = {2,4,3};
        int ans = a.max(c);
        assertTrue(ans == 4);
    }


}
