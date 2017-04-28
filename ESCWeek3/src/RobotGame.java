import java.util.Scanner;

class Robot {
    String name;

    public IBehaviour behaviorType;

    public Robot (String name)
    {
        this.name = name;
    }

    public void behave ()
    {
        if (behaviorType.moveCommand() == 2){
            System.out.println(name + " is Aggressive");
        }
        if (behaviorType.moveCommand() == 0){
            System.out.println(name + " is Defensive");
        }
        if (behaviorType.moveCommand() == 1){
            System.out.println(name + " is Normal");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBehavior() {
        Scanner readBehavior = new Scanner(System.in);
        String option = "";
        System.out.println("Choose your behavior type for "+name+ " -- can be Aggressive, Defensive or Normal");

        if (readBehavior.hasNextLine()){
            option = readBehavior.nextLine();
        }
        if (option.equals("Aggressive")){
            behaviorType = new Aggressive();
        }
        else if (option.equals("Defensive")){
            behaviorType = new Defensive();
        }
        else if (option.equals("Normal")){
            behaviorType = new Normal();
        }
//        behave();
    }
}

interface IBehaviour {
    public int moveCommand();
}


class Aggressive implements IBehaviour{

    @Override
    public int moveCommand() {
        return 2;
    }
}
class Defensive implements IBehaviour{

    @Override
    public int moveCommand() {
        return 0;
    }
}
class Normal implements IBehaviour{

    @Override
    public int moveCommand() {
        return 1;
    }
}

public class RobotGame {

    public static void main(String[] args) {

        Robot r1 = new Robot("Big Robot");
        Robot r2 = new Robot("George v.2.1");
        Robot r3 = new Robot("R2");

        r1.setBehavior();
        r2.setBehavior();
        r3.setBehavior();

        r1.behave();
        r2.behave();
        r3.behave();

        //change the behaviors of each robot.
        r1.setBehavior();
        r2.setBehavior();
        r3.setBehavior();

        r1.behave();
        r2.behave();
        r3.behave();
    }
}