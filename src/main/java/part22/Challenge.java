package part22;

import mbot.*;

// Guide: GUIDE.md in this folder (driveUntilBlocked, step 2; a behavior made of behaviors, step 5;
//        followLine and the line sensor's numbers, steps 7–8)
//
// SECTION D — Challenge. Four behaviors. The test in src/test/java/part22/ChallengeTest.java
// checks all four. Change only the lines marked YOUR CODE. The README explains each one.
//
// The test drives this robot in NEW worlds (worlds/part22-maze.json and worlds/part22-track.json),
// not the ones from class. Your code has to use the sensors to work there.

public class Challenge {

    private Chassis base;
    private Ultrasonic eyes;
    private LineSensor floor;
    private boolean lineWasLeft;      // for followLine: which side the tape was on last time

    // Done for you: the robot, with both sensors plugged in. (You did this in part 21.)
    public Challenge() {
        base = new Chassis();
        eyes = new Ultrasonic(base);
        floor = new LineSensor(base);
    }

    // Drive forward 5 cm at a time, while the ultrasonic reads MORE than cm.
    // Give back how many 5 cm moves it made.
    public int driveUntilBlocked(double cm) {
        return 0;   // YOUR CODE
    }

    // Step to the right without turning for good: turn right 90, drive cm, turn left 90.
    // The robot ends up cm to the right, facing the same way as before.
    public void sidestepRight(double cm) {
        // YOUR CODE
    }

    // Get to the Goal in worlds/part22-maze.json. Use the two methods above:
    //   drive until blocked (15 cm), step right 70 cm, drive until blocked (15 cm),
    //   step right 70 cm, drive until blocked (15 cm).
    public void reachGoal() {
        // YOUR CODE
    }

    // Follow the tape for this many steps. Each step: read the line sensor ONCE, then do ONE move.
    //   4, 8 or 12  → the tape is on the left:  turn left 4,  and remember it was on the left
    //   1, 2 or 3   → the tape is on the right: turn right 4, and remember it was on the right
    //   0           → lost it: turn 4 toward the side you remembered
    //   anything else (like 6) → drive straight 1
    public void followLine(int steps) {
        // YOUR CODE
    }
}
