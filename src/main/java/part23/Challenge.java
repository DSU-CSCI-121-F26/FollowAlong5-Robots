package part23;

import mbot.*;

// Guide: GUIDE.md in this folder (extends and super(), steps 1–2; a scan is an array, step 4)
//
// SECTION D — Challenge. A robot class of your own: an explorer that IS AN MBot2 and
// also carries a Scanner. The test in src/test/java/part23/ChallengeTest.java checks it.
// Change only the lines marked YOUR CODE. The README explains each one.
//
// The test drives this robot in NEW worlds (worlds/part23-maze.json and
// worlds/part23-maze-mirror.json), not the one from class.

public class Challenge extends MBot2 {

    private Scanner scanner;

    // Build the MBot2 part first (that is the super() line), then mount the scanner.
    public Challenge() {
        super();
        scanner = new Scanner(this);
    }

    // Done for you. Notice: readUltrasonic() and straight() are not written in this class.
    // A Challenge IS AN MBot2, so it already has them.
    public int driveUntilBlocked() {
        int moves = 0;
        while (readUltrasonic() > 20) {
            straight(5);
            moves++;
        }
        return moves;
    }

    // Scan ONCE. Give back the index (0 to 6) of the biggest distance.
    // If two are equal, give back the smaller index.
    public int openestIndex() {
        double[] d = scanner.scan();
        int best = 0;
        for (int i = 1; i < d.length; i++) {
            if (d[i] > d[best]) {
                best = i;
            }
        }
        return best;
    }   // YOUR CODE


    // Scan ONCE. Give back how many of the 7 distances are MORE than cm.
    public int countOpen(double cm) {
        double[] d = scanner.scan();
        int count = 0;
        for (int i = 0; i < d.length; i++) {
            if (d[i] > cm) {
                count++;
            }
        }
        return count;   // YOUR CODE
    }

    // Turn the robot to face the direction the scanner looks at for this index.
    // Scanner.ANGLES[index] is that angle: positive means turn LEFT that much,
    // negative means turn RIGHT (by the same amount, without the minus), 0 means don't turn.
    public void turnToward(int index) {
        double angle = Scanner.ANGLES[index];
        if (angle > 0) {
            turnLeft(angle);
        } else if (angle < 0) {
            turnRight(-angle);
        }
    }

    // Find the way to the dead end at the Goal. Up to 10 times:
    //   drive until blocked;
    //   if NONE of the 7 directions is more than 60 cm away, you are at the dead end: stop (return);
    //   otherwise turn toward the most open direction.
    public void explore() {
        for (int i = 0; i < 10; i++) {
            driveUntilBlocked();
            if (countOpen(60) == 0) {
                return;

            }
            turnToward(openestIndex());
        }
    }
}

