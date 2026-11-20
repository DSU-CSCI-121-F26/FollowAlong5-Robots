package part24;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 1–3 and 6
//
// Part 24 — overriding: your robot keeps a method's name, but changes what it does.
//
// SECTION A — FOLLOW ALONG: type the CarefulBot class from the guide.
//    Leave the "package part24;" line, the "import mbot.*;" line and the class line alone.
//    Type everything else yourself.
//
// SECTION B — COMMENTS: when you finish, put a // comment ABOVE every line of code,
//    saying in YOUR OWN WORDS what that line does.

public class CarefulBot extends MBot2 {
    // sets up the careful robot using the parent constructor
    public CarefulBot() {
        // runs the parent constructor to build the robot
        super();
    }

    // replaces the inherited straight method with a safety check
    @Override
    // checks for enough space before moving the requested distance
    public void straight(double cm) {
        // only moves if more than 20 cm will still be clear ahead
        if (readUltrasonic() > cm + 20) {
            // uses the parent's movement code to make the actual move
            super.straight(cm);
            // handles the move when there isn't enough room
        } else {
            // turns led 1 red to show the robot isn't gonna move
            turnLedOn(1, 255, 0, 0);
            // prints the requested distance and why the robot won't drive
            System.out.println("CarefulBot: not driving " + cm + " cm, too close");
        }
    }

    // replaces the inherited run method with this robot's routine
    @Override
    // tries five forward moves using the safety check in straight
    public void run() {
        // repeats the movement attempt five times
        for (int i = 0; i < 5; i++) {
            // asks this robot's straight method to move forward 40 cm
            straight(40);
        }
    }
}
