package part24;

import mbot.*;

// Guide: GUIDE.md in this folder (override and super.straight, steps 1–3;
//        polymorphism, steps 5–7)
//
// SECTION D — Challenge. A robot that never bumps into anything, and a method that works
// for ANY robot. The test in src/test/java/part24/ChallengeTest.java checks them.
// Change only the lines marked YOUR CODE. The README explains each one.
//
// The test drives this robot in NEW worlds (worlds/part24-hall.json and
// worlds/part24-yard.json), not the one from class.

public class Challenge extends MBot2 {

    public Challenge() {
        super();
    }

    // Override straight() so this robot NEVER bumps into anything.
    //   cm is 0 or less (backing up): just do what MBot2's straight() does.
    //   cm is more than 0: drive in steps of 5 cm (the last step can be smaller),
    //   reading the ultrasonic BEFORE each step. Stop when it reads 20 or less,
    //   or when you have driven cm in total.
    @Override
    public void straight(double cm) {
        // YOUR CODE
    }

    // Override run(): once round the yard. 4 times: straight(1000), then turn right 90.
    // Your safe straight() is what stops it at each wall.
    @Override
    public void run() {
        // YOUR CODE
    }

    // Works for ANY robot: an MBot2, a Challenge, or any other class that extends MBot2.
    // While bot reads more than 20 on its ultrasonic: bot.straight(10), and count it.
    // Give back the count.
    public static int stepsToWall(MBot2 bot) {
        return 0;   // YOUR CODE
    }
}
