/**
 * Created by jonathanbeiqiyang on 11/2/17.
 */
public class Candidate {
    private int votes = 0;

    public void voteFor(){
        votes += 1;
    }

    public int getVotes(){
        return votes;
    }
}
