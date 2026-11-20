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
public class ObstacleRun {
    public static void main(String[] args) {
        // makes a rover named Dash that's gonna handle the course
        Rover rover = new Rover("Dash");
        // sends Dash around the obstacle and toward the goal
        rover.reachGoal();
        // prints the finish message once the route is done
        System.out.println(rover + " reached the goal");
    }
}
