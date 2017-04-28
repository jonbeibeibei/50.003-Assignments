import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Apply SPMD (Single Program, Multiple Data) design pattern for concurrent programming to parallelize the program which 
 * approximates $\pi$ by integrating the following formula $4/(1+x^2 )$. Hint: In the SPMD design pattern, all threads 
 * run the same program, operating on different data.
 */
public class SPMDIntegration {
	public static void main(String[] args) throws Exception {
		int NTHREADS = 5;
		ExecutorService exec = Executors.newFixedThreadPool(NTHREADS - 1);
		// todo: complete the program by writing your code below.
		double piTotal = 0;
        double step = 1.0/NTHREADS;
//        final double[] ans = {0};
        for(int i = 0; i < NTHREADS; i++){
            final double a = i*step;
            final double b = (i+1)*step;
//            exec.execute(new Runnable() {
//                @Override
//                public void run() {
//                    ans[0] = integrate(a,b);
//                }
//            });
            Future<Double> answer = exec.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    return integrate(a,b);
                }
            });
            piTotal += answer.get();
            System.out.println("intermediate ans:" + piTotal);
        }


        System.out.println("Final Value of pi:" + piTotal);
        exec.shutdown();
	}

	public static double f(double x)   {
		return 4.0 / (1 + x * x);
	}

	// the following does numerical integration using Trapezoidal rule.
	public static double integrate(double a, double b) {
		int N = 10000; // preciseness parameter
		double h = (b - a) / (N - 1); // step size
		double sum = 1.0 / 2.0 * (f(a) + f(b)); // 1/2 terms

		for (int i = 1; i < N - 1; i++) {
			double x = a + h * i;
			sum += f(x);
		}

		return sum * h;
	}
}
