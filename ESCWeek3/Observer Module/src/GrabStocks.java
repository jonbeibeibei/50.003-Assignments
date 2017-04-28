/**
 * Created by jonathanbeiqiyang on 10/2/17.
 */
public class GrabStocks {

    public static void main(String[] args) {
        StockGrabber stockGrabber  = new StockGrabber();

        StockObserver observer1 = new StockObserver(stockGrabber);

        System.out.printf("Original Prices");
        stockGrabber.setIBMPrice(120);
        stockGrabber.setAAPLPrice(212);
        stockGrabber.setGOOGPrice(400);

        Runnable getIBM = new MoreEfficientGrabStocks(stockGrabber, 2, "IBM", 120.00);
        Runnable getAAPL = new MoreEfficientGrabStocks(stockGrabber, 2, "AAPL", 450.00);
        Runnable getGOOG = new MoreEfficientGrabStocks(stockGrabber, 2, "GOOG", 789.00);

        new Thread(getIBM).start();
        new Thread(getAAPL).start();
        new Thread(getGOOG).start();
    }

}
