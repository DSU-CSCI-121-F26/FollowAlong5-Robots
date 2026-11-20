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

        Rover rover = new Rover("Tracker");
        rover.followLine(400);
        System.out.println("Halfway round");
        rover.followLine(325);
        System.out.println(rover + " finished a lap");
    }

}
