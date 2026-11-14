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
    // Sets up the rover
    public Rover(String name) {
        this.name = name;
        this.base = new Chassis();
        this.eyes = new Ultrasonic(base);
        this.floor = new LineSensor(base);
    }
    // Checks for objects within a distance
    public boolean somethingAhead(double cm) {
        return eyes.distance() < cm;
    }
    // Drives until it reaches a nearby object in front of its view
    public void driveUntilBlocked() {
        while (!somethingAhead(20)) {
            base.straight(5);
        }
    }
    void driveToTape() {
        while (!floor.seesTape()) {
            base.straight(2);
        }
    }
    void forward(double cm) {
        base.straight(cm);
    }
    // Returns the rover's name
    public String toString() {
        return name + " the rover";
    }
    // Stores each of the rover's parts
    private String name;
    // creates rover's base for movement
    private Chassis base;
    // Creates the rover's eyes to detect nearby objects
    private Ultrasonic eyes;

    private LineSensor floor;
    // was supposed to run the program
    public static void main(String[] args) {

    }

}
