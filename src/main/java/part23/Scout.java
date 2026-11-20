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
    // saves the scout's name for printed messages
    private String name;
    // holds the scanner that's gonna compare the paths ahead
    private Scanner scanner;

    // builds a scout with its inherited robot parts and a scanner
    public Scout(String name) {
        // runs the parent constructor to set up the robot first
        super();
        // saves the name given to this scout
        this.name = name;
        // connects the scanner to this scout using this
        this.scanner = new Scanner(this);
    }

    // drives until something is within 25 cm and counts the moves
    public int driveUntilBlocked() {
        // starts at zero since the scout hasn't moved yet
        int moves = 0;
        // keeps moving while there's more than 25 cm of space ahead
        while (readUltrasonic() > 25) {
            // uses the inherited movement method to go forward 5 cm
            straight(5);
            // counts the move that just finished
            moves++;
        }
        // gives back the number of moves made
        return moves;
    }

    // finds the position of the scan reading with the most open space
    public int openestIndex() {
        // scans the different directions and saves their distances
        double[] d = scanner.scan();
        // starts by treating the first reading as the best one
        int best = 0;
        // checks the remaining readings against the best one so far
        for (int i = 1; i < d.length; i++) {
            // checks whether this direction has more room
            if (d[i] > d[best]) {
                // saves this position as the new best direction
                best = i;
            }
        }
        // gives back the array position with the greatest distance
        return best;
    }

    // turns the scout toward the side with the most room
    public void turnTowardOpenSide() {
        // gets the position of the most open direction
        int i = openestIndex();
        // checks whether that direction's angle is positive
        if (Scanner.ANGLES[i] > 0) {
            // turns left 90 degrees toward that side
            turnLeft(90);
            // handles the direction when its angle isn't positive
        } else {
            // turns right 90 degrees toward the other side
            turnRight(90);
        }
    }

    // controls how the scout appears when it's printed
    public String toString() {
        // adds the scout label to its saved name
        return name + " the scout";
    }
}
