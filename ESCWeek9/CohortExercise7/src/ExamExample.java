/**
 * Created by jonathanbeiqiyang on 23/3/17.
 */

import java.util.concurrent.*;
import java.util.*;


public class ExamExample {
	private static int numberOfStudents = 10;
	
	public static void main (String[] args) {
	    Student[] students = new Student[numberOfStudents];
	    Phaser examPhaser = new Phaser();
	    examPhaser.register();
	    examPhaser.bulkRegister(numberOfStudents);

	    for(int i = 0; i<numberOfStudents; i++){
	    	students[i] = new Student(examPhaser);
	    	students[i].start();

	    }

//	    System.out.println("Phase is: " + examPhaser.getPhase());
	    examPhaser.arriveAndAwaitAdvance();
//	    System.out.println("All Students arrived! ");
//	    System.out.println("Phase is: " + examPhaser.getPhase());
	    examPhaser.arriveAndAwaitAdvance();
	    System.out.println("End of Exam!");


	}

}

class Student extends Thread{

	private Phaser phaser;

	public Student(Phaser phaser){
		this.phaser = phaser;
	}

	public void run(){
		System.out.println("Student Arrived @ Exam Hall");
		phaser.arriveAndAwaitAdvance();

		System.out.println("Student Doing Exam");
		try{
			Thread.sleep(1000);

		}
		catch(InterruptedException e){
			e.printStackTrace();
		}

		System.out.println("Student Finished Exam...Submitting and Leaving");
		phaser.arriveAndDeregister();
	}
}