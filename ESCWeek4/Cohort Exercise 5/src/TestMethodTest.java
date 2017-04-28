import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
@RunWith(Parameterized.class)

/**
 * Created by jonathanbeiqiyang on 17/2/17.
 */
public class TestMethodTest {
    int input;
    int expected;

    public TestMethodTest(int input, int expected){
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters(){
        return Arrays.asList(new Object[][]{
                {99998,1},
                {99999,1},
                {-99998,-1},
                {-99999,-1},
                {0,0}
        });
    }

    @Test
    public void testValues(){
        TestMethod testMethod = new TestMethod();
        int a = testMethod.test(input);
        assertEquals(expected,a);
    }

}
