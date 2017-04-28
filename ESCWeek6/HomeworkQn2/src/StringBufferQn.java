/**
 * Created by jonathanbeiqiyang on 5/3/17.
 */
public class StringBufferQn {
/*
  * String buffer is essential in this case because StringBuffer is mutable, whereas String is not.
  * As we are modifying the same object multiple times, the mutability is required.
  *
*/
    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer("A");
        StringBufferThread stringBufferThread1 = new StringBufferThread(stringBuffer);
        StringBufferThread stringBufferThread2 = new StringBufferThread(stringBuffer);
        StringBufferThread stringBufferThread3 = new StringBufferThread(stringBuffer);

        stringBufferThread1.start();
        stringBufferThread2.start();
        stringBufferThread3.start();

    }
}

class StringBufferThread extends Thread{

    StringBuffer stringBuffer;

    StringBufferThread(StringBuffer stringBuffer){
        this.stringBuffer = stringBuffer;
    }

    public void run(){
        synchronized (stringBuffer){
            for(int i = 0 ; i < 100 ; i++){
                System.out.print(stringBuffer);
            }
            char currentLetter = stringBuffer.charAt(0);
            char nextLetter = (char) (currentLetter + 1);
            stringBuffer.setCharAt(0,nextLetter);
        }
    }
}