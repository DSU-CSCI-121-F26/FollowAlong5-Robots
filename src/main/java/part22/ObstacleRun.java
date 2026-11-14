package part22;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 3–6
//
// SECTION A — FOLLOW ALONG: type the main method from the guide in this class.
//    It makes a Rover and sends it round the box to the goal.
//    Leave the package, import, @MBotLab and class lines alone. Type everything else.
//
// SECTION B — COMMENTS: put a // comment ABOVE every line of code, in YOUR OWN WORDS.

@MBotLab(world = "obstacles")
public class ObstacleRun { // class named obstacleRun
    public static void main(String[] args) { // here initializes our program
        Rover rover = new Rover("Dash"); // creates the rover and names it Dash
        rover.reachGoal(); // reachGoal is a method used on Dash to make it reach the Goal
        System.out.println(rover + " reached the goal"); // prints that that Dash reached the goal once reached
    }

}
