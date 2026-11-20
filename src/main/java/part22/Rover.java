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

    private String name;
    private Chassis base;
    private Ultrasonic eyes;
    private LineSensor floor;

    public Rover(String name) {
        this.name = name;
        this.base = new Chassis();
        this.eyes = new Ultrasonic(base);
        this.floor = new LineSensor(base);
    }

    public int driveUntilBlocked(double cm) {
        int moves = 0;
        while (eyes.distance() > cm) {
            base.straight(5);
            moves++;
        }
        return moves;
    }

    public void goAroundLeft(double sideways) {
        base.turnLeft(90);
        base.straight(sideways);
        base.turnRight(90);
    }

    public void reachGoal() {
        int first = driveUntilBlocked(15);
        System.out.println("Blocked after " + first + " moves. Going round.");
        goAroundLeft(60);
        int second = driveUntilBlocked(15);
        System.out.println("Blocked after " + second + " moves. That is the far wall.");
        base.turnRight(90);
        base.straight(50);
    }

    private boolean lineWasLeft;

    public void followLine(int steps) {
        for (int i = 0; i < steps; i++) {
            int s = floor.status();
            if (s == 4 || s == 8 || s == 12) {
                base.turnLeft(4);
                lineWasLeft = true;
            } else if (s == 1 || s == 2 || s == 3) {
                base.turnRight(4);
                lineWasLeft = false;
            } else if (s == 0) {
                if (lineWasLeft) {
                    base.turnLeft(4);
                } else {
                    base.turnRight(4);
                }
            } else {
                base.straight(1);
            }
        }
    }

    public String toString() {
        return name + " the rover";
    }

}
