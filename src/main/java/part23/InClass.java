package part23;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 1–4 (extends, super(), and a sensor field)
//
// In-class exercise — we do this together in class. Not graded, but commit it.
// The README (In-class exercise) has the steps and the output to match.

@MBotLab(world = "junctions")
public class InClass {
    public static void main(String[] args) {

        // STEP 1 — we finish the Lookout class at the bottom, then make one here and
        //          call report(), together.



        // STEP 2 — fix the bugs. Each line below uses a robot class with ONE mistake
        // (BotA, BotB and BotC, further down).
        // Move ONE line at a time above the /* line, so Java runs it.
        // Read the crash, the red error, or what the 3D viewer says. Fix that class. Run it.
        // Then do the next line.
        /*
        new BotA().report();
        new BotB().report();
        MBot2 c = new BotC();
        c.run();
        */
    }
}

// STEP 1 — we build this class together.
class Lookout {

}

// STEP 2 — each of these three classes has ONE mistake.
class BotA extends MBot2 {
    private Scanner scanner;

    BotA() {
        super();
        Scanner scanner = new Scanner(this);
    }

    void report() {
        System.out.println("A sees " + scanner.scan()[3] + " cm ahead");
    }
}

// BotB is inside a comment, because its mistake stops EVERY file from running.
// When you get to BotB, delete the /* and */ lines around it.
/*
class BotB {
    private Scanner scanner;

    BotB() {
        super();
        scanner = new Scanner(this);
    }

    void report() {
        straight(5);
        System.out.println("B sees " + scanner.look(0) + " cm ahead");
    }
}
*/

class BotC extends MBot2 {
    public void Run() {
        straight(10);
        System.out.println("C ran its own run()");
    }
}
