package part24;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 4 and 6
//
// SECTION A — FOLLOW ALONG: type the FlashyBot class from the guide.
//    Leave the package, import and class lines alone. Type everything else.
//
// SECTION B — COMMENTS: put a // comment ABOVE every line of code, in YOUR OWN WORDS.

public class FlashyBot extends MBot2 {
    private int turns = 0;
    public FlashyBot() {
        super();// Call the parent MBot2 constructor
    }

    @Override
    public void turnLeft(double degrees) {
        turns++;
        // Print a message indicating the left turn and angle
        System.out.println("Flash! Turning left " + degrees);
        turnLedOn(1, 0, 0, 255);// Turn LED 1 blue to flash during the turn
        super.turnLeft(degrees);// Call the parent turnLeft method to execute the turn
        turnLedOff(1);// Turn LED 1 off after the turn finishes
    }

    @Override
    public void turnRight(double degrees) {
        turns++;
        // Print a message indicating the right turn and angle
        System.out.println("Flash! Turning right " + degrees);
        turnLedOn(1, 0, 255, 0);// Turn LED 1 green to flash during the turn
        super.turnRight(degrees);// Call the parent turnRight method to execute the turn
        turnLedOff(1);// Turn LED 1 off after the turn finishes
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {// Loop 4 times to repeat the movement pattern
            straight(40);// Move straight forward by 40 centimeters
            turnLeft(90);// Turn left by 90 degrees
        }
    }
}
