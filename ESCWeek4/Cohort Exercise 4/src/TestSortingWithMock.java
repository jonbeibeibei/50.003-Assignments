import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by jonathanbeiqiyang on 16/2/17.
 */
public class TestSortingWithMock {
    @Test
    public void testSorting(){
        Mockery context = new JUnit4Mockery();

        final Sorter sorter = context.mock(Sorter.class);

        context.checking(new Expectations(){{
            oneOf(sorter).sort((new int[]{2, 1, 4, 3}));
            will(returnValue(new int[]{1, 2, 3, 4}));

        }

        });
        FindMaxUsingSorting findMaxUsingSorting = new FindMaxUsingSorting();
        findMaxUsingSorting.findmax(new int[]{2, 1, 4, 3},sorter);

        context.assertIsSatisfied();
    }
}
