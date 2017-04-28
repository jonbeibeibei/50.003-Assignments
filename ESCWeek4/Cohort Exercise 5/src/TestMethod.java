/**
 * Created by jonathanbeiqiyang on 17/2/17.
 */
public class TestMethod {
    public int test(int input){
        if (input == 0){
            return 0;
        }
        if(input < 99999 && input > 0){
            return 1;
        }
        if(input < 0){
            return -1;
        }
        return 1;
    }
}
