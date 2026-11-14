package part22;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 7–9
//
// SECTION A — FOLLOW ALONG: type the main method from the guide in this class.
//    It makes a Rover and follows the loop of tape all the way round.
//    Leave the package, import, @MBotLab and class lines alone. Type everything else.
//
// SECTION B — COMMENTS: put a // comment ABOVE every line of code, in YOUR OWN WORDS.

@MBotLab(world = "line-track")
public class LineRun {
    // our program starts
    public static void main(String[] args) {
        // Creates the object Tracker the rover
        Rover rover = new Rover("Tracker");
        //  method that makes Tracker follow the line for 400 steps
        rover.followLine(400);
        // prints halfway round
        System.out.println("Halfway round");
        // follows the line for 325 more steps
        rover.followLine(325);
        // prints that dash(the rover) finished a lap
        System.out.println(rover + " finished a lap");
    }

}
