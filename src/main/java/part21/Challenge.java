package part21;

import mbot.*;

// Guide: GUIDE.md in this folder (the constructor, steps 2–4; the while loop, step 7)
//
// SECTION D — Challenge. A robot class with a constructor and two methods.
// The test in src/test/java/part21/ChallengeTest.java checks all three.
// Change only the lines marked YOUR CODE. The README explains each one.
//
// The test drives this robot in the worlds in the worlds/ folder (part21-challenge.json and
// part21-boxes.json), not in the worlds from class.

public class Challenge {

    private Chassis base;
    private Ultrasonic eyes;
    private LineSensor floor;

    // Build the robot. Make the chassis FIRST. Then plug an Ultrasonic AND a LineSensor into it.
    public Challenge() {
        // YOUR CODE
    }

    // Drive forward 5 cm at a time, until the ultrasonic reads LESS than 15 cm.
    // Give back how many 5 cm moves it made.
    //   (A robot that is already closer than 15 cm makes 0 moves.)
    public int stepsToWall() {
        return 0;   // YOUR CODE
    }

    // Drive forward 2 cm at a time until the line sensor sees tape.
    // Then drive 30 cm more, so the robot ends inside the taped box.
    public void park() {
        // YOUR CODE
    }
}
