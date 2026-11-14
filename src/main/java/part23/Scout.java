package part23;

import mbot.*;

// Guide: GUIDE.md in this folder, steps 1–5
//
// Part 23 — inheritance: your own robot class, built on top of MBot2.
//
// SECTION A — FOLLOW ALONG: type the Scout class from the guide.
//    Step 1 changes the class line below: that is the one line you DO change.
//    Leave the "package part23;" line and the "import mbot.*;" line alone.
//    Type everything else yourself.
//
// SECTION B — COMMENTS: when you finish, put a // comment ABOVE every line of code,
//    saying in YOUR OWN WORDS what that line does.

public class Scout extends MBot2 {
    private String name;
    private Scanner scanner;

    public Scout(String name) {
        super();// Call the parent MBot constructor
        this.name = name;// Save the robot's name
        this.scanner = new Scanner(this);// Initialize a new scanner for this robot
    }
    public int driveUntilBlocked() {
        int moves = 0;// Start the move counter at zero
        while (readUltrasonic() > 25) { // Keep moving while the path ahead is clear (distance greater than 25cm)
            straight(5);// Move straight forward by 5 centimeters
            moves++;// Add one to the move counter
        }
        return moves;// Return the total number of moves made
    }
    public int openestIndex() {
        double[] d = scanner.scan();// Scan distances in all directions and store them in an array
        int best = 0;// Assume the first direction is the best initially
        for (int i = 1; i < d.length; i++) {// Loop through all scanned directions to find the maximum distance
            if (d[i] > d[best]) {// Check if the current direction has a larger distance than the best found so far
                best = i;// Update the index of the best direction
            }
        }
        return best;// Return the index of the openest direction
    }
    public void turnTowardOpenSide() {
        int i = openestIndex();// Find the index of the direction with the most space
        if (Scanner.ANGLES[i] > 0) {// Check if the open angle is on the left (positive angle)
            turnLeft(90);// Turn left by 90 degrees
        } else {// Otherwise, turn right by 90 degrees
            turnRight(90);
        }
    }
    public String toString() {// Return a text description combining the robot's name
        return name + " the scout";
    }

}
