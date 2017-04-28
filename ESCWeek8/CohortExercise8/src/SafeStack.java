import java.util.Stack;

/**
 * Created by jonathanbeiqiyang on 16/3/17.
 */
public class SafeStack<E> extends Stack<E> {
    E e;


    public static void main(String[] args) {
        SafeStack safeStack = new SafeStack();
        SafeStack1 safeStack1 = new SafeStack1();
        SafeStack2 safeStack2 = new SafeStack2();

        safeStack1.ensureCapacity(10);
        safeStack2.stack.ensureCapacity(10);

        long currentTimeMillis = System.nanoTime();
        long b = System.nanoTime();
        safeStack1.pushIfNotFull(safeStack.e);
        safeStack1.pushIfNotFull(safeStack.e);
        safeStack1.pushIfNotFull(safeStack.e);
        safeStack1.pushIfNotFull(safeStack.e);
        safeStack1.pushIfNotFull(safeStack.e);
        safeStack1.pushIfNotFull(safeStack.e);
        safeStack1.pushIfNotFull(safeStack.e);
        safeStack1.pushIfNotFull(safeStack.e);
        safeStack1.pushIfNotFull(safeStack.e);
        safeStack1.pushIfNotFull(safeStack.e);
        safeStack1.pushIfNotFull(safeStack.e);
        safeStack1.pushIfNotFull(safeStack.e);

        long endTime1 = System.nanoTime();
        System.out.println(endTime1 - currentTimeMillis);

        long currentTimeMillis2 = System.nanoTime();
        safeStack2.pushIfNotFull(safeStack.e);
        safeStack2.pushIfNotFull(safeStack.e);
        safeStack2.pushIfNotFull(safeStack.e);
        safeStack2.pushIfNotFull(safeStack.e);
        safeStack2.pushIfNotFull(safeStack.e);
        safeStack2.pushIfNotFull(safeStack.e);
        safeStack2.pushIfNotFull(safeStack.e);
        safeStack2.pushIfNotFull(safeStack.e);
        safeStack2.pushIfNotFull(safeStack.e);
        safeStack2.pushIfNotFull(safeStack.e);
        safeStack2.pushIfNotFull(safeStack.e);
        safeStack2.pushIfNotFull(safeStack.e);

        long endTime2 = System.nanoTime();
        System.out.println(endTime2 - currentTimeMillis2);
    }
}

class SafeStack1<E> extends Stack<E> {
    private final int maxSize = 10;

    public synchronized boolean pushIfNotFull(E e){
        if(size() < capacity()){
            push(e);
            return true;
        }
        else{
            return false;
        }
    }

    public synchronized E popIfNotEmpty(E e){
        if(empty()){
            return null;
        }
        else{
            return pop();
        }
    }
}


//Client side locking
class SafeStack2<E>{
    public Stack<E> stack = new Stack<E>();

    private final int maxSize = 10;

    public boolean pushIfNotFull(E e){
        synchronized (stack){
            if(stack.size() < stack.capacity()){
                stack.push(e);
                return true;
            }
            else{
                return false;
            }
        }
    }

    public E popIfNotEmpty(E e){
        synchronized (stack){
            if (stack.empty()){
                return null;
            }
            else{
                return stack.pop();
            }
        }
    }


}
