package part23;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 1–4 (extends, super(), and a sensor field)
//
// SECTION C — Stretch. Do Stretch A, then Stretch B. The README says what each one should print.
// Stretch B adds a new class, Painter, at the bottom of this file.
//
/* super() runs the MBot2 constructor to set up the robot.
   eye.color() runs ColorSensor's code to read the color underneath.
   turnLedOn(1, 255, 0, 0) runs MBot2's code to turn led 1 red.
   straight(5) runs Chassis's code to move forward 5 cm.
   showColor() runs Painter's code to match the led to the floor color.
*/

// lil scout uses a Scanner to find the open path while Painter uses a ColorSensor
// to read floor colors both inherit movement from MBot but their different
// jobs need different sensors so they have different fields

@MBotLab(world = "color-zones")
public class Stretch {
    public static void main(String [] args) {

        Painter p = new Painter();
        System.out.println(p instanceof Painter);
        System.out.println(p instanceof MBot2);
        System.out.println(p instanceof Chassis);
        p.driveToRed();

    }

    static class Painter extends MBot2 {
        private ColorSensor eye;

        Painter() {
            super();
            eye = new ColorSensor(this);
        }

        void showColor() {
            String c = eye.color();
            if (c.equals("red")) {
                turnLedOn(1, 255, 0, 0);
            } else if (c.equals("green")) {
                turnLedOn(1, 0, 255, 0);
            } else if (c.equals("blue")) {
                turnLedOn(1, 0, 0, 255);
            } else if (c.equals("yellow")) {
                turnLedOn(1, 255, 200, 0);
            } else {
                turnLedOff(1);
            }
        }

        void driveToRed() {
            String last = "";
            while (!eye.color().equals("red")) {
                String c = eye.color();
                if (!c.equals(last)) {
                    System.out.println("Now on " + c);
                    last = c;
                }
                showColor();
                straight(5);
            }
            showColor();
            System.out.println("Stopped on red");
        }
    }
}
