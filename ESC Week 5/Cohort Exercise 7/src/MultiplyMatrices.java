import java.util.Arrays;

/**
 * Created by jonathanbeiqiyang on 24/2/17.
 */
public class MultiplyMatrices {

    public static void main(String[] args) throws InterruptedException {
        int[][] A = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] B = {{9,8,7},{6,5,4},{3,2,1}};
        int cRows = A.length;
        int cCols = B[0].length;
        int[][] C = new int[cRows][cCols];

        MultiplyMatrices multiplyMatrices = new MultiplyMatrices();
//        multiplyMatrices.matrixMult(A,B,C,0,cRows);
//        System.out.println(Arrays.deepToString(C));

        //splitting the roles to 2 Threads
        int cRowsHalved = cRows/2;
        MultiplicationThread multiplicationThread1 = new MultiplicationThread(A,B,C,0,cRowsHalved);
        MultiplicationThread multiplicationThread2 = new MultiplicationThread(A,B,C,cRowsHalved, cRows);

        multiplicationThread1.start();
        multiplicationThread2.start();
        multiplicationThread1.join();
        multiplicationThread2.join();

        System.out.println("Resulting Matrix is: ");
        System.out.println(Arrays.deepToString(C));

    }

    public static void matrixMult(int[][] A, int[][] B, int[][] C, int start, int end){
        //row A row C
        for(int m = start; m < end; m++){
            //column B, column C
            for(int n = 0; n < C[0].length; n++){
                C[m][n] = 0;
                // column A, row B
                for(int k = 0; k < A[0].length; k++){
                    C[m][n] += A[m][k] * B[k][n];
                }

            }
        }
    }




    public static class MultiplicationThread extends Thread{
        private int[][] A;
        private int[][] B;
        private int[][] C;

        private int start;
        private int end;

        public MultiplicationThread(int[][] A, int[][] B, int[][] C, int start, int end){
            this.A = A;
            this.B = B;
            this.C = C;
            this.start = start;
            this.end = end;

        }
        public void run(){
            System.out.println("Thread running");
            matrixMult(A,B,C,start,end);
        }

    }


}
