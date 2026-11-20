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
        System.out.println("Mission start");
        bot.run();
        System.out.println("Mission done");
    }

    public static void main(String[] args) {
        int pick = 3;
        MBot2 bot;
        if (pick == 1) {
            bot = new CarefulBot();
        } else if (pick == 2) {
            bot = new FlashyBot();
        } else {
            bot = new MBot2();
        }
        mission(bot);
    }


    }



