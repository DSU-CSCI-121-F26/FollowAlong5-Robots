package part24;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 1–3 and 6
//
// Part 24 — overriding: your robot keeps a method's name, but changes what it does.
//
// SECTION A — FOLLOW ALONG: type the CarefulBot class from the guide.
//    Leave the "package part24;" line, the "import mbot.*;" line and the class line alone.
//    Type everything else yourself.
//
// SECTION B — COMMENTS: when you finish, put a // comment ABOVE every line of code,
//    saying in YOUR OWN WORDS what that line does.

public class CarefulBot extends MBot2 {
    private int refused = 0;

    public CarefulBot() {

        super();// Call the parent MBot2 constructor
    }

    @Override
    public void straight(double cm) {
        if (readUltrasonic() > cm + 20) {
            super.straight(cm);// Call the parent straight method to move forward
        } else {
            refused++;
            turnLedOn(1, 255, 0, 0);
            System.out.println("CarefulBot: not driving " + cm + " cm, too close");

        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {// Loop 5 times to execute movement attempts
            straight(40);// Try to move straight by 40 centimeters safely
        }
    }

    @Override
    public String toString() {
        return "CarefulBot (refused " +  refused + " moves)";
    }
}

