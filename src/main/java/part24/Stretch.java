package part24;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 4–7 (overriding, and polymorphism)
//
// SECTION C — Stretch. Do Stretch A, then Stretch B. The README says what each one should print.
// Stretch B also changes CarefulBot.java, FlashyBot.java and Mission.java.
//
// There is no main method here yet. Typing it is part of the stretch.

@MBotLab(world = "open-room")
public class Stretch {
    public static void main(String[] args) {

        /*
        it's gonna print Flash!, Turning left 90.0, and true
        */

        MBot2 a = new FlashyBot();
        Chassis c = a;
        a.straight(10);
        c.turnLeft(90);
        System.out.println(a == c);

        System.out.println(a);

        /*
        with pick 3, the first line prints "Mission start: " followed by the
        regular robot's name. it's using MBot2's toString because the object
        is a regular MBot2, so it doesn't show refused moves or counted turns.
        */

    }

}
