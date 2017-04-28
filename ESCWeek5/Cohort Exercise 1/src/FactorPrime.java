import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jonathanbeiqiyang on 20/2/17.
 */
public class FactorPrime {


    public ArrayList<BigInteger> semiPrimeFactors(BigInteger inputNum){
        ArrayList<BigInteger> primeFactors = new ArrayList<>();
        BigInteger[] fac = new BigInteger[2];
        for(BigInteger i = BigInteger.valueOf(2); i.compareTo(inputNum) == -1; i = i.add(BigInteger.ONE)){
            if(inputNum.mod(i) == BigInteger.ZERO){
                if(!(primeFactors.contains(i))){
                    primeFactors.add(i);
                    primeFactors.add(inputNum.divide(i));
                }
            }
        }

        return primeFactors;

    }

    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//        BigInteger one = input.nextBigInteger();

        FactorPrime semiPrime = new FactorPrime();
        BigInteger sum = new BigInteger("1679");

        ArrayList<BigInteger> fac = semiPrime.semiPrimeFactors(sum);
        for (BigInteger factor: fac){
            System.out.println(factor);
        }
    }
}
