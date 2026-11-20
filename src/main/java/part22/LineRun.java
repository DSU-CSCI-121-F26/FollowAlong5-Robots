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
    public static void main(String[] args) {
        // makes a rover named Tracker with its chassis and sensors
        Rover rover = new Rover("Tracker");
        // follows the tape for the first 400 checks
        rover.followLine(400);
        // prints the halfway message after that first stretch
        System.out.println("Halfway round");
        // follows the tape for another 325 checks to finish the lap
        rover.followLine(325);
        // prints which rover finished using its toString method
        System.out.println(rover + " finished a lap");
    }
}
