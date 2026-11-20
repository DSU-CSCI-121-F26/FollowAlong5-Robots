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
        super();
        this.name = name;
        this.scanner = new Scanner(this);
    }

    public int driveUntilBlocked() {
        int moves = 0;
        while (readUltrasonic() > 25) {
            straight(5);
            moves++;
        }
        return moves;
    }

    public int openestIndex() {
        double[] d = scanner.scan();
        int best = 0;
        for (int i = 1; i < d.length; i++) {
            if (d[i] > d[best]) {
                best = i;
            }
        }
        return best;
    }

    public void turnTowardOpenSide() {
        int i = openestIndex();
        if (Scanner.ANGLES[i] > 0) {
            turnLeft(90);
        } else {
            turnRight(90);
        }
    }

    public String toString() {
        return name + " the scout";
    }

}
