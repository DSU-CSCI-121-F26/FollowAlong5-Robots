package part23;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 3, 5 and 6
//
// SECTION A — FOLLOW ALONG: type the main method from the guide in this class.
//    It makes a Scout and sends it through the junctions to the goal.
//    Leave the package, import, @MBotLab and class lines alone. Type everything else.
//
// SECTION B — COMMENTS: put a // comment ABOVE every line of code, in YOUR OWN WORDS.

@MBotLab(world = "junctions")
public class Explore {
    public static void main(String[] args) {
        // Initializes a new Scout robot named Pathfinder
        Scout scout = new Scout("Pathfinder");
        // Repeat the movement loop for 3 legs
        for (int leg = 1; leg <= 3; leg++) {
            // Move forward until blocked and save the step count
            int moves = scout.driveUntilBlocked();
            // Display the current leg number and moves taken
            System.out.println("Leg " + leg + ": " + moves + " moves");
            // Check if it is not yet the final leg
            if (leg < 3) {
                // Turn toward the open path for the next leg
                scout.turnTowardOpenSide();
            }
        }
        // Print a message confirming the goal has been reached
        System.out.println(scout + " reached the goal");
    }
}