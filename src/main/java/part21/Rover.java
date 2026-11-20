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
    // saves this rover's name so we can tell it apart
    private String name;
    // holds the chassis that handles the rover's movement
    private Chassis base;
    // holds the sensor that's gonna check the distance ahead
    private Ultrasonic eyes;
    private LineSensor floor;
    // builds a rover with its own name, chassis, and sensor
    public Rover(String name) {
        // saves the name passed in for this specific rover
        this.name = name;
        // makes the chassis before the sensor needs to use it
        this.base = new Chassis();
        // connects the distance sensor to this rover's chassis
        this.eyes = new Ultrasonic(base);
        this.floor = new LineSensor(base);

    }

    public String toString() {
        // adds a label to the name so the output describes the rover
        return name + " the rover";
    }

    public boolean somethingAhead(double cm) {
        // gives back true when the sensor sees something inside that distance
        return eyes.distance() < cm;
    }
    // keeps the rover moving until something is less than 20 cm ahead
    public void driveUntilBlocked() {
        // keeps going while the sensor says the path is still clear
        while (!somethingAhead(20)) {
            // moves forward 5 cm before checking the distance again
            base.straight(5);
        }
    }

    public void driveToTape() {
        while (!floor.seesTape()) {
            base.straight(2);
        }
    }

    public void forward(double cm) {

        // hands the movement over to the chassis
        base.straight(cm);
    }

}
