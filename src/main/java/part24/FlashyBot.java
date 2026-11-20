package part24;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 4 and 6
//
// SECTION A — FOLLOW ALONG: type the FlashyBot class from the guide.
//    Leave the package, import and class lines alone. Type everything else.
//
// SECTION B — COMMENTS: put a // comment ABOVE every line of code, in YOUR OWN WORDS.

public class FlashyBot extends MBot2 {

    private int turns;

    // sets up this robot using the parent constructor
    public FlashyBot() {
        // builds the inherited robot parts first
        super();
    }

    // replaces the inherited left turn with a light-up version
    @Override
    // adds a message and blue light around the actual left turn
    public void turnLeft(double degrees) {
        // prints how far the robot is about to turn left
        System.out.println("Flash! Turning left " + degrees);
        // turns led 1 blue before the turn starts
        turnLedOn(1, 0, 0, 255);
        // lets the parent perform the turn without calling this method again
        super.turnLeft(degrees);
        // switches off led 1 after the turn finishes
        turnLedOff(1);
        turns++;
    }

    // replaces the inherited right turn with a light-up version
    @Override
    // adds a message and green light around the actual right turn
    public void turnRight(double degrees) {
        // prints how far the robot is about to turn right
        System.out.println("Flash! Turning right " + degrees);
        // turns led 1 green before the turn starts
        turnLedOn(1, 0, 255, 0);
        // lets the parent handle the actual right turn
        super.turnRight(degrees);
        // switches off led 1 after the turn finishes
        turnLedOff(1);
        turns++;
    }

    // replaces the inherited run method with a square-driving routine
    @Override
    // drives four sides with a left turn after each one
    public void run() {
        // repeats once for each side of the square
        for (int i = 0; i < 4; i++) {
            // drives forward 40 cm along the current side
            straight(40);
            // uses this robot's flashy left turn for the next corner
            turnLeft(90);
        }
    }

    @Override
    public String toString() {
        return "FlashyBot (" + turns + " turns)";
    }

}
