package part22;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 1–9
//
// Part 22 — behaviors: methods that use the sensors to decide what the robot does.
//
// SECTION A — FOLLOW ALONG: type the Rover class from the guide in this class.
//    Leave the "package part22;" line, the "import mbot.*;" line and the class line alone.
//    Type everything else yourself.
//
// SECTION B — COMMENTS: when you finish, put a // comment ABOVE every line of code,
//    saying in YOUR OWN WORDS what that line does.

public class Rover { // class named rover which defines the object rover
    private String name; // this attribute stores the rover's name
    private Chassis base; // this attribute controls the rover's movement
    private Ultrasonic eyes; //attribute measures the distance rover is near an object
    private LineSensor floor; //attribute detects lines on the floor
    private boolean lineWasLeft; // Remembers if my line was left
    public int driveUntilBlocked(double cm) {
        int moves = 0; // counts each move
        while (eyes.distance() > cm) { // loop repeats while eye distance is greater than cm
            // Drives forward 5 cm
            base.straight(5);
            // Adds one move more  each time distance is greater than the cm
            moves++;
        }
        // Returns the move count
        return moves;
    }
    // creates a rover
    public Rover(String name) {
        this.name = name; // saves its given name
        this.base = new Chassis(); // creates the movement system
        this.eyes = new Ultrasonic(base); // creates the distance sensor
        this.floor = new LineSensor(base); // creates the line sensor
    }
    //method responsible for left go rounds for the rover
    public void goAroundLeft(double sideways) {
        base.turnLeft(90); // Turns left 90 degrees
        base.straight(sideways); // moves sideways
        base.turnRight(90); // turns right 90 degrees
    }
    // method liable for r\Rover driving around obstacles to reach its goal
    public void reachGoal() {
        // Counts moves to the first obstacle
        int first = driveUntilBlocked(15);
        // prints the first move count
        System.out.println("Blocked after " + first + " moves. Going round.");
        // goes left around the obstacle
        goAroundLeft(60);
        // Counts moves till the next obstacle is caught
        int second = driveUntilBlocked(15);
        // prints the second move count
        System.out.println("Blocked after " + second + " moves. That is the far wall.");
        // Turns right 90 degrees
        base.turnRight(90);
        // Drives forward 50 cm
        base.straight(50);
    }
    // Follows the line for a set number of steps
    public void followLine(int steps) {
        // Repeats for the given steps
        for (int i = 0; i < steps; i++) {
            // reads the line sensor
            int s = floor.status();
            // checks if the line is left
            if (s == 4 || s == 8 || s == 12) {
                base.turnLeft(4); // turns right 4 degrees
                lineWasLeft = true; // Remembers the line was left
            } else if (s == 1 || s == 2 || s == 3) { // checks if the line is right
                base.turnRight(4); // turns right 4 degrees
                lineWasLeft = false; // remembers the line was right
            } else if (s == 0) { // checks if the line is lost
                if (lineWasLeft) { // checks the last known side
                    base.turnLeft(4); // Searches left
                } else {
                    base.turnRight(4); // searches the right
                }
            } else {
                base.straight(1); // moves forward 1 cm
            }
        }
    }

    // Returns the rover's name and description
    public String toString() {
        return name + " the rover";
    }

}
