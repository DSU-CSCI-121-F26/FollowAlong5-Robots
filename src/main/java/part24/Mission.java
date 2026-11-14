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

    public static void mission(MBot2 bot) {
        System.out.println("Mission start: " + bot);// Print a message indicating the mission is starting
        bot.run();// Run the robot's behavior
        System.out.println("Mission done: " + bot);// Print a message indicating the mission is complete
    }

    public static void main(String[] args) {
        int pick = 1;// Set the selection variable for the robot type
        MBot2 bot;// Declare a variable to store the robot instance
        if (pick == 1) {// Check if pick is set to 1
            bot = new CarefulBot();// Assign a new CarefulBot to the variable
        } else if (pick == 2) {// Check if pick is set to 2
            bot = new FlashyBot();// Assign a new FlashyBot to the variable
        } else {// Otherwise, use a standard MBot2
            bot = new MBot2();// Assign a new base MBot2 to the variable
        }
        mission(bot);// Call the mission method using the chosen robot

    }
}
