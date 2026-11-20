package part22;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 1–9
//
// Part 22 — behaviors: methods that use the sensors to decide what the robot does.
//
// SECTION A — FOLLOW ALONG: type the Rover class from the guide in this class.
//    Leave the "package part22;" line, the "import mbot.*;" line and the class line alone.
//    Type everything else yourself.
//
// SECTION B — COMMENTS: when you finish, put a // comment ABOVE every line of code,
//    saying in YOUR OWN WORDS what that line does.

public class Rover {
    // saves the rover's name for its printed messages
    private String name;
    // holds the chassis that handles movement and turns
    private Chassis base;
    // holds the sensor that checks the distance ahead
    private Ultrasonic eyes;
    // holds the sensor that reads the tape underneath
    private LineSensor floor;

    // builds the rover and connects both sensors to its chassis
    public Rover(String name) {
        // saves the name given to this rover
        this.name = name;
        // makes the chassis before the sensors need it
        this.base = new Chassis();
        // connects the distance sensor to this rover's chassis
        this.eyes = new Ultrasonic(base);
        // connects the tape sensor to the same chassis
        this.floor = new LineSensor(base);
    }

    // drives toward something and gives back how many moves it made
    public int driveUntilBlocked(double cm) {
        // starts the move count at zero
        int moves = 0;
        // keeps driving while the distance ahead is greater than the limit
        while (eyes.distance() > cm) {

            // moves forward 5 cm before checking again
            base.straight(5);
            // counts the move the rover just finished
            moves++;
        }
        // gives back the total number of moves
        return moves;
    }
    // shifts the rover left and gets it facing forward again
    public void goAroundLeft(double sideways) {
        // turns left so the rover can move beside the obstacle
        base.turnLeft(90);
        // moves sideways by the distance passed into the method
        base.straight(sideways);
        // turns right so the rover faces its original direction
        base.turnRight(90);
    }
    // guides the rover around the obstacle and toward the goal
    public void reachGoal() {
        // drives toward the first obstacle and saves the move count
        int first = driveUntilBlocked(15);
        // prints how many moves happened before going around
        System.out.println("Blocked after " + first + " moves. Going round.");
        // shifts 60 cm to the left to get around the obstacle
        goAroundLeft(60);
        // drives toward the next stopping point and saves that move count
        int second = driveUntilBlocked(15);
        // prints the second count and identifies the far wall
        System.out.println("Blocked after " + second + " moves. That is the far wall.");
        // turns right to face the goal
        base.turnRight(90);
        // moves forward 50 cm toward the goal
        base.straight(50);
    }
    // remembers which side the tape was on if the rover loses it
    private boolean lineWasLeft;
    // follows the tape for the number of checks passed in
    public void followLine(int steps) {
        // repeats the sensor check and steering decision the requested number of times
        for (int i = 0; i < steps; i++) {
            // saves the floor sensor's current reading
            int s = floor.status();
            // checks for readings that need a left correction
            if (s == 4 || s == 8 || s == 12) {
                // turns left a little to get back toward the tape
                base.turnLeft(4);
                // remembers that the tape was on the left
                lineWasLeft = true;

            } else if (s == 1 || s == 2 || s == 3) {
                // turns right a little to get back toward the tape
                base.turnRight(4);
                // remembers that the tape was on the right
                lineWasLeft = false;

            } else if (s == 0) {
                // checks whether the tape was last seen on the left
                if (lineWasLeft) {
                    // looks left since that's where the tape was last seen
                    base.turnLeft(4);

                } else {
                    // looks right to try finding the tape again
                    base.turnRight(4);
                }
            } else {
                // moves forward 1 cm before the next sensor check
                base.straight(1);
            }
        }
    }

    // controls how the rover appears in printed messages
    public String toString() {
        // adds the rover label to its saved name
        return name + " the rover";
    }
}