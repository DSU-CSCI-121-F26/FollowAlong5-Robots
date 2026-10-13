package part21;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 2–6 (fields, the constructor, and plugging in a sensor)
//
// In-class exercise — we do this together in class. Not graded, but commit it.
// The README (In-class exercise) has the steps and the output to match.

@MBotLab(world = "obstacles")
public class InClass {
    public static void main(String[] args) {

        // STEP 1 — we finish the Sentry class at the bottom, then make one here and
        //          check all four directions, together.



        // STEP 2 — fix the bugs. Each line below uses a Sentry class with ONE mistake
        // in its constructor (SentryA, SentryB and SentryC, further down).
        // Move ONE line at a time above the /* line, so Java runs it.
        // Read the crash, and what the 3D viewer says. Fix that class. Run it. Then do the next line.
        /*
        new SentryA().check();
        new SentryB().check();
        new SentryC().check();
        */
    }
}

// STEP 1 — we build this class together.
class Sentry {

}

// STEP 2 — each of these three classes has ONE mistake in its constructor.
class SentryA {
    private Chassis base;
    private Ultrasonic eyes;

    SentryA() {
        base = new Chassis();
    }

    void check() {
        System.out.println("A sees " + eyes.distance() + " cm");
    }
}

class SentryB {
    private Chassis base;
    private Ultrasonic eyes;

    SentryB() {
        eyes = new Ultrasonic(base);
        base = new Chassis();
    }

    void check() {
        System.out.println("B sees " + eyes.distance() + " cm");
    }
}

class SentryC {
    private Chassis base;
    private Ultrasonic eyes;

    SentryC() {
        base = new Chassis();
        Ultrasonic eyes = new Ultrasonic(base);
    }

    void check() {
        System.out.println("C sees " + eyes.distance() + " cm");
    }
}
