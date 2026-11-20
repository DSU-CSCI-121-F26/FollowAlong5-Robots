package part22;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 7–9 (the line sensor's numbers, and followLine)
//
// SECTION C — Stretch. Do Stretch A, then Stretch B. The README says what each one should print.
// Stretch B adds a new class, Lapper, at the bottom of this file.
//
// There is no main method here yet. Typing it is part of the stretch.

@MBotLab(world = "line-track")
public class Stretch {
    public static void main(String[] args) {

        int[] readings = {6, 12, 3, 0};
        for (int s : readings) {
            if (s == 4 || s == 8 || s == 12) {
                System.out.println(s + ": turn left");
            } else if (s == 1 || s == 2 || s == 3) {
                System.out.println(s + ": turn right");
            } else if (s == 0) {
                System.out.println(s + ": lost it");
            } else {
                System.out.println(s + ": straight");
            }
        }
        /*
        ummmmm i believe based off of what s equals it is going to run through the loop and do what it says
        so its going straight then, turning left, then turning right, then we are going to lose it
         */

        Lapper lapper = new Lapper(new Rover("Loop"));
        lapper.laps(2);
        // taking a rover lets main pick its name and choose which rover the lapper uses

    }

    static class Lapper {
        private Rover rover;
        private int done;

        public Lapper(Rover rover) {
            this.rover = rover;
            done = 0;
        }

        public void laps(int n) {
            for (int i = 0; i < n; i++) {
                rover.followLine(725);
                done++;
                System.out.println("Lap " + done + " done");
            }
        }
    }

}
