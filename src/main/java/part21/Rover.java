package part21;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 2–8
//
// Part 21 — composition: a Rover HAS-A Chassis and HAS-AN Ultrasonic.
//
// SECTION A — FOLLOW ALONG: type the Rover class from the guide in this class.
//    Leave the "package part21;" line, the "import mbot.*;" line and the class line alone.
//    Type everything else yourself.
//
// SECTION B — COMMENTS: when you finish, put a // comment ABOVE every line of code,
//    saying in YOUR OWN WORDS what that line does.
//
// SECTION C — STRETCH B adds a LineSensor and two methods to this class.

public class Rover {
    private String name;
    private Chassis base;
    private Ultrasonic eyes;

    public Rover(String name) {
        this.name = name;
        this.base = new Chassis();
        this.eyes = new Ultrasonic(base);

    }

    public String toString() {
        return name + " the rover";
    }

    public boolean somethingAhead(double cm) {
        return eyes.distance() < cm;
    }

    public void driveUntilBlocked() {
        while (!somethingAhead(20)) {
            base.straight(5);
        }
    }

}
