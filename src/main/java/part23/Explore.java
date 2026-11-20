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

        // makes a scout named Pathfinder with its robot parts and scanner
        Scout scout = new Scout("Pathfinder");
        // runs the three parts of the route one at a time
        for (int leg = 1; leg <= 3; leg++) {
            // drives until blocked and saves how many moves it took
            int moves = scout.driveUntilBlocked();
            // prints the current leg and its move count
            System.out.println("Leg " + leg + ": " + moves + " moves");
            // checks whether another leg still needs to be driven
            if (leg < 3) {
                // scans for the open side and turns toward the next path
                scout.turnTowardOpenSide();
            }
        }
        // prints the finish message after all three legs are done
        System.out.println(scout + " reached the goal");
    }
}
