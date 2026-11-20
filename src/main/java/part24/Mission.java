package part24;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 2, 5 and 7
//
// SECTION A — FOLLOW ALONG: type the mission method and the main method from the guide.
//    Leave the package, import, @MBotLab and class lines alone. Type everything else.
//
// SECTION B — COMMENTS: put a // comment ABOVE every line of code, in YOUR OWN WORDS.

@MBotLab(world = "wall-ahead")
public class Mission {
    // accepts any MBot2 robot and runs its own version of run
    public static void mission(MBot2 bot) {
        // prints that the mission is starting
        System.out.println("Mission start: " + bot);
        // runs the version belonging to the actual robot passed in
        bot.run();
        // prints this once the robot's routine finishes
        System.out.println("Mission done: " + bot);
    }

    public static void main(String[] args) {
        // picks which kind of robot the mission is gonna use
        int pick = 3;
        // makes a variable that can hold any of these robot types
        MBot2 bot;
        // checks whether the careful robot was selected
        if (pick == 1) {
            // makes a robot that checks the space before driving
            bot = new CarefulBot();
            // checks whether the flashy robot was selected
        } else if (pick == 2) {
            // makes a robot with lights and messages during turns
            bot = new FlashyBot();
            // handles any other choice with the regular robot
        } else {
            // makes a regular robot that uses its default behavior
            bot = new MBot2();
        }
        // passes the chosen robot into the same mission method
        mission(bot);
    }


    }



