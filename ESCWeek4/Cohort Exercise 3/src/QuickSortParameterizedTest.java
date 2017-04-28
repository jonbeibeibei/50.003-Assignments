import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
@RunWith(Parameterized.class)
/**
 * Created by jonathanbeiqiyang on 16/2/17.
 */
public class QuickSortParameterizedTest {
    int[] input;
    int[] expected;
    QuickSort quickSort = new QuickSort();

    public QuickSortParameterizedTest(int[] input, int[] expected){
        this.input = input;
        this.expected = expected;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> parameters(){
        return Arrays.asList(new int[][][] {
                {{6,2,3,4,5,6,8},{2,3,4,5,6,6,8}},
                {{9,8,7,6,5,4,3,2,1},{1,2,3,4,5,6,7,8,9}}
        });
    }
    @Test
    public void parameterTest(){
        quickSort.sort(input);
        assertEquals(Arrays.toString(expected),Arrays.toString(input));
    }

//    @Test
//    public void TestQuicksort(){
//        int[] input = new int[]{6,2,3,4,5,6,8};
//        QuickSort sort = new QuickSort();
//        sort.sort(input);
//
//        assertEquals("[2, 3, 4, 5, 6, 6, 8]", Arrays.toString(input));
//}

}
