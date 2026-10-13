package part22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mbot.Testing;
import org.junit.jupiter.api.Test;

// You do not change this file. It checks your Challenge.java.
// Run it with the green arrow next to "class ChallengeTest".
//
// Each test drives your robot in a world from the worlds/ folder, with no 3D viewer.
// Want to watch one? See "Watch your challenge run" in the README.

class ChallengeTest {

    // ---- driveUntilBlocked ----

    @Test
    void driveUntilBlockedCountsTheMoves() {
        Testing.start("worlds/part22-maze.json");
        Challenge robot = new Challenge();
        assertEquals(11, robot.driveUntilBlocked(15));
        assertEquals(25 + 11 * 5, Testing.y(), 0.01, "each move should be one straight(5)");
    }

    @Test
    void driveUntilBlockedUsesTheDistanceYouGiveIt() {
        Testing.start("worlds/part22-maze.json");
        Challenge robot = new Challenge();
        assertEquals(6, robot.driveUntilBlocked(40));
    }

    @Test
    void driveUntilBlockedDoesNotMoveWhenAlreadyBlocked() {
        Testing.start("worlds/part22-maze.json");
        Challenge robot = new Challenge();
        robot.driveUntilBlocked(15);
        assertEquals(0, robot.driveUntilBlocked(15), "already close: zero moves");
    }

    // ---- sidestepRight ----

    @Test
    void sidestepRightMovesSidewaysAndFacesTheSameWay() {
        Testing.start("worlds/part22-maze.json");
        Challenge robot = new Challenge();
        robot.sidestepRight(70);
        assertEquals(80 + 70, Testing.x(), 0.01, "70 cm to the right");
        assertEquals(25, Testing.y(), 0.01, "no closer to the goal");
        assertEquals(90, Testing.heading(), 0.01, "facing the same way as before");
    }

    // ---- reachGoal ----

    @Test
    void reachGoalEndsInTheGoal() {
        Testing.start("worlds/part22-maze.json");
        Challenge robot = new Challenge();
        robot.reachGoal();
        assertTrue(Testing.insideZone("Goal"), "the robot should end in the Goal, but it is at x = "
                + Testing.x() + ", y = " + Testing.y());
        assertEquals(0, Testing.bumps(), "no bumping into boxes or walls");
    }

    @Test
    void reachGoalUsesTheUltrasonic() {
        Testing.start("worlds/part22-maze.json");
        Challenge robot = new Challenge();
        robot.reachGoal();
        assertTrue(Testing.sensorReads() >= 20,
                "reachGoal() should keep checking the ultrasonic as it drives, not just drive fixed distances");
    }

    // ---- followLine ----

    @Test
    void followLineGoesRoundTheSharpRightHandLoop() {
        assertLap("worlds/part22-track.json");
    }

    @Test
    void followLineGoesRoundTheRoundLeftHandLoop() {
        assertLap("line-track");
    }

    @Test
    void followLineDoesExactlyThatManySteps() {
        Testing.start("worlds/part22-track.json");
        Challenge robot = new Challenge();
        robot.followLine(50);
        assertEquals(50, Testing.sensorReads(), "one line-sensor reading per step");
    }

    // Calls followLine 25 steps at a time, up to 1200 steps, and checks the robot leaves the
    // Lap box, stays on the track, and comes back to it.
    private static void assertLap(String world) {
        Testing.start(world);
        Challenge robot = new Challenge();
        boolean leftTheStart = false;
        for (int chunk = 1; chunk <= 48; chunk++) {
            robot.followLine(25);
            assertEquals(0, Testing.bumps(), "the robot bumped into something");
            assertTrue(Testing.x() > 35 && Testing.x() < 285 && Testing.y() > 25 && Testing.y() < 235,
                    "the robot left the track: it is at x = " + Testing.x() + ", y = " + Testing.y());
            if (!Testing.insideZone("Lap")) {
                leftTheStart = true;
            }
            if (leftTheStart && chunk > 10 && Testing.insideZone("Lap")) {
                return;
            }
        }
        throw new AssertionError("followLine did not get all the way round in 1200 steps. It is at x = "
                + Testing.x() + ", y = " + Testing.y());
    }
}
