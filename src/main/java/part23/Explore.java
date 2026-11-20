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

        Scout scout = new Scout("Pathfinder");
        for (int leg = 1; leg <= 3; leg++) {
            int moves = scout.driveUntilBlocked();
            System.out.println("Leg " + leg + ": " + moves + " moves");
            if (leg < 3) {
                scout.turnTowardOpenSide();
            }
        }
        System.out.println(scout + " reached the goal");
    }

}
