package part24;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 1–3 (override, @Override and super.straight)
//
// In-class exercise — we do this together in class. Not graded, but commit it.
// The README (In-class exercise) has the steps and the output to match.

@MBotLab(world = "open-room")
public class InClass {
    public static void main(String[] args) {

        // STEP 1 — we finish the Counter class at the bottom, then drive a square
        //          with it here and print it, together.



        // STEP 2 — fix the bugs. Each pair of lines below uses a robot class with ONE
        // mistake in its straight() (CounterA, CounterB and CounterC, further down).
        // Move ONE group of lines at a time above the /* line, so Java runs it.
        // Read what it prints, what the 3D viewer shows, or the crash. Fix that class.
        // Run it. Then do the next group.
        /*
        MBot2 a = new CounterA();
        a.straight(30);
        System.out.println(a);

        MBot2 b = new CounterB();
        b.straight(30);
        System.out.println(b);

        MBot2 c = new CounterC();
        c.straight(30);
        System.out.println(c);
        */
    }
}

// STEP 1 — we build this class together.
class Counter extends MBot2 {

}

// STEP 2 — each of these three classes has ONE mistake in its straight() method.
class CounterA extends MBot2 {
    private int moves;

    public void straight(int cm) {
        moves++;
        super.straight(cm);
    }

    @Override
    public String toString() {
        return "A: " + moves + " moves";
    }
}

class CounterB extends MBot2 {
    private int moves;

    @Override
    public void straight(double cm) {
        moves++;
    }

    @Override
    public String toString() {
        return "B: " + moves + " moves";
    }
}

class CounterC extends MBot2 {
    private int moves;

    @Override
    public void straight(double cm) {
        moves++;
        straight(cm);
    }

    @Override
    public String toString() {
        return "C: " + moves + " moves";
    }
}
