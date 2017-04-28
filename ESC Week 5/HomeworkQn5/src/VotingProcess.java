import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by jonathanbeiqiyang on 11/2/17.
 */
public class VotingProcess {
    //instantiate the Candidates A & B
    static Candidate candidateA = new Candidate();
    static Candidate candidateB = new Candidate();

    static String vote;

    // Instantiate the Electorates
    Electorate electorateA = new Electorate("A");
    Electorate electorateB = new Electorate("B");
    Electorate electorateC = new Electorate("C");
    Electorate electorateD = new Electorate("D");
    Electorate electorateE = new Electorate("E");

    ArrayList<Electorate> electorates;


    Scanner voterInput = new Scanner(System.in);


    public VotingProcess() {
        electorates = new ArrayList<Electorate>();
        electorates.add(electorateA);
        electorates.add(electorateB);
        electorates.add(electorateC);
        electorates.add(electorateD);
        electorates.add(electorateE);

    }


    public static void main(String[] args) throws Exception {
        VotingProcess vote1 = new VotingProcess();

        for (Electorate e : vote1.electorates) {
            boolean success = false;
            if (e.getVotedStatus() != true) {
                System.out.println("Electoral College " + e.toString() + " please vote A or B");


                while (success != true) {
                    try {
                        vote = vote1.voterInput.next();
                        Exception exception = new Exception();
                        if (vote.equals("A")) {
                            e.castVote(candidateA);
                            success = true;

                        }
                        else if (vote.equals("B")) {
                            e.castVote(candidateB);
                            success = true;

                        }
                        else {
                            throw new Exception();

                        }

                    } catch (Exception e1) {
                        System.out.println("Invalid Input, please vote for A or B");
                    }


                }
            }

        }
        System.out.println("Vote ended");
        int numberofVotesA = candidateA.getVotes();
        int numberofVotesB = candidateB.getVotes();

        if (numberofVotesA > numberofVotesB){
            System.out.println("Candidate A wins!");
        }
        if (numberofVotesA < numberofVotesB){
            System.out.println("Candidate B wins!");
        }
    }
}
