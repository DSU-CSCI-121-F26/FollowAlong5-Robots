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

    public Challenge() {
        base = new Chassis();
        eyes = new Ultrasonic(base);
        floor = new LineSensor(base);
    }

    public int stepsToWall() {
        int steps = 0;

        while (eyes.distance() >= 15) {
            base.straight(5);
            steps++;
        }

        return steps;
    }

    public void park() {
        while (!floor.seesTape()) {
            base.straight(2);
        }

        base.straight(30);
    }
}
