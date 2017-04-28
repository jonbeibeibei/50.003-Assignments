/**
 * Created by jonathanbeiqiyang on 11/2/17.
 */
public class Electorate {

    String name = null;
    boolean voted = false;
    public void castVote(Candidate candidate){
        candidate.voteFor();
        voted = true;
        System.out.println("Voted!");
    }
    public boolean getVotedStatus(){
        return voted;
    }

    @Override
    public String toString() {
        return name;
    }

    Electorate(String a){
        name = a;
    }
}
