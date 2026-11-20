package part24;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 4 and 6
//
// SECTION A — FOLLOW ALONG: type the FlashyBot class from the guide.
//    Leave the package, import and class lines alone. Type everything else.
//
// SECTION B — COMMENTS: put a // comment ABOVE every line of code, in YOUR OWN WORDS.

public class FlashyBot extends MBot2 {
    public FlashyBot() {
        super();
    }

    @Override
    public void turnLeft(double degrees) {
        System.out.println("Flash! Turning left " + degrees);
        turnLedOn(1, 0, 0, 255);
        super.turnLeft(degrees);
        turnLedOff(1);
    }

    @Override
    public void turnRight(double degrees) {
        System.out.println("Flash! Turning right " + degrees);
        turnLedOn(1, 0, 255, 0);
        super.turnRight(degrees);
        turnLedOff(1);
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            straight(40);
            turnLeft(90);
        }
    }

}
