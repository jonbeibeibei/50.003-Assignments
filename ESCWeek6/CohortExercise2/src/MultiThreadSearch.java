

/**
 * Created by jonathanbeiqiyang on 27/2/17.
 */
public class MultiThreadSearch {
    public static boolean found = false;

    public static void main(String[] args) {
        int[] testArray = {1,2,2,7,4,4,5,5,9};
        int num = 5;

        SearchElement search1 = new SearchElement(testArray,0,testArray.length/2,num);
        SearchElement search2 = new SearchElement(testArray, testArray.length/2,testArray.length/1, num);

        search1.start();
        search2.start();

        while(true){
            if(!MultiThreadSearch.found && !search1.isAlive() && !search2.isAlive()){
                System.out.println("Not found!");
                break;
            }
            if(MultiThreadSearch.found){
                if(search1.isAlive()){
                    search1.interrupt();
                }
                if(search2.isAlive()){
                    search2.interrupt();
                }
                break;
            }

        }
    }


}

class SearchElement extends Thread{
    public int[] array;
    public int lower;
    public int upper;
    public int num;

    public SearchElement(int[] array, int lower, int upper, int num){
        this.array = array;
        this.lower = lower;
        this.upper = upper;
        this.num = num;
    }

    @Override
    public void run() {
        for(int i = lower; i < upper; i++){
            if(isInterrupted()){
                break;
            }
            if(array[i] == num){
                System.out.println("Found!");
                MultiThreadSearch.found = true;
                break;
            }
        }
    }


}
