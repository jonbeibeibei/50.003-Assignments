/**
 * Created by jonathanbeiqiyang on 10/2/17.
 */
import java.text.DecimalFormat;

public class MoreEfficientGrabStocks implements Runnable {

    private int beginTime;
    private String stock;
    private double stockPrice;

    private iSubject stockGrabber;

    public MoreEfficientGrabStocks(iSubject stockGrabber, int newBeginTime, String newStock,
                                   double newStockPrice){

        this.stockGrabber = stockGrabber;
        beginTime = newBeginTime;
        stock = newStock;
        stockPrice = newStockPrice;
    }


    @Override
    public void run() {

        for (int i = 0; i <= 20; i++){

            try{
                //wait for 2 seconds before each thread execution
                Thread.sleep(beginTime * 1000);

            }
            catch (InterruptedException e){

            }
            double randomNumber = Math.random()*(.06) - 0.03;

            DecimalFormat df = new DecimalFormat("#.##");

            stockPrice = Double.valueOf(df.format(stockPrice + randomNumber));

            if (stock == "IBM"){
                ((StockGrabber) stockGrabber).setIBMPrice(stockPrice);
            }
            if (stock == "AAPL"){
                ((StockGrabber) stockGrabber).setAAPLPrice(stockPrice);
            }
            if (stock == "GOOG"){
                ((StockGrabber) stockGrabber).setGOOGPrice(stockPrice);
            }

            System.out.println(stock + ": " + df.format(stockPrice+randomNumber) + " "
                    + df.format(randomNumber) + "\n");

            System.out.println();

        }
    }
}
