import java.util.Arrays;

/**
 * Created by jonathanbeiqiyang on 24/2/17.
 */
public class MatrixThreadArray {

    public static void main(String[] args) throws InterruptedException {
        int[][] A = new int[200][200];
        int[][] B = new int[200][200];
        int cRows = A.length;
        int cCols = B[0].length;
        int[][] C = new int[cRows][cCols];


//        multiplyMatrices.matrixMult(A,B,C,0,cRows);
//        System.out.println(Arrays.deepToString(C));

        //splitting the roles to number of Threads
        int[] threadNumbers = {1,2,3,4,5,6,7,8,9,10};


        for(int i = 0; i < threadNumbers.length; i++) {
            long startTime = System.currentTimeMillis();
//            C = new int[cRows][cCols];
            int cRowsHalved = cRows / threadNumbers[i];

            MultiplicationThread[] threads = new MultiplicationThread[threadNumbers[i]];

            for (int j = 0; j < threadNumbers[i]; j++) {
                threads[i] = new MultiplicationThread(A, B, C, i * cRows / threadNumbers[i], (i + 1) * cRows / threadNumbers[i]);
                threads[i].start();
            }
            for (int k = 0; k < threadNumbers[i]; k++) {
                threads[i].join();
            }

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Resulting Matrix Time of Threads:" + (i+1) +  " is: " + totalTime);

//            System.out.println(Arrays.deepToString(C));
        }

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
//            System.out.println("Thread running");
            matrixMult(A,B,C,start,end);
        }

    }
}
