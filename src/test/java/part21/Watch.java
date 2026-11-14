package part21;
import mbot.*;
public class Watch {
    public static void main(String[] args) {



        @MBotLab(world = "worlds/part21-challenge.json")
         class tWatch {
            public static void main(String[] args) {
                Challenge robot = new Challenge();
                System.out.println(robot.stepsToWall());
            }
        }
    }
}
