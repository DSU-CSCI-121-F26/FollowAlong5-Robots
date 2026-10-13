package part23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import mbot.Testing;
import org.junit.jupiter.api.Test;

// You do not change this file. It checks your Challenge.java.
// Run it with the green arrow next to "class ChallengeTest".
//
// Each test drives your robot in a world from the worlds/ folder, with no 3D viewer.
// Want to watch one? See "Want to watch?" in the README.

class ChallengeTest {

    private static final String MAZE = "worlds/part23-maze.json";
    private static final String MIRROR = "worlds/part23-maze-mirror.json";

    // ---- the constructor ----

    @Test
    void constructorMountsTheScanner() {
        Testing.start(MAZE);
        new Challenge();
        assertEquals(Set.of("ultrasonic", "line", "scanner"), Testing.parts(),
                "an MBot2 comes with an ultrasonic and a line sensor; your constructor adds the scanner");
    }

    // ---- openestIndex ----

    @Test
    void openestIndexAtTheStartIsStraightAhead() {
        Testing.start(MAZE);
        Challenge robot = new Challenge();
        assertEquals(3, robot.openestIndex(), "at the start, the long way is straight ahead");
    }

    @Test
    void openestIndexAtTheFirstCorner() {
        Testing.start(MAZE);
        Challenge robot = new Challenge();
        robot.driveUntilBlocked();
        assertEquals(6, robot.openestIndex(), "in this maze the first turn is to the right");
        Testing.start(MIRROR);
        robot = new Challenge();
        robot.driveUntilBlocked();
        assertEquals(0, robot.openestIndex(), "in the mirror maze the first turn is to the left");
    }

    @Test
    void openestIndexScansOnce() {
        Testing.start(MAZE);
        Challenge robot = new Challenge();
        robot.openestIndex();
        assertEquals(1, Testing.sensorReads(), "openestIndex() should call scan() exactly once");
    }

    // ---- countOpen ----

    @Test
    void countOpenCountsTheDistancesThatAreMoreThanCm() {
        Testing.start(MAZE);
        Challenge robot = new Challenge();
        // At the start the scan is 35, 37.7, 56.8, 117, 56.8, 37.7, 35
        assertEquals(7, robot.countOpen(30));
        assertEquals(3, robot.countOpen(40));
        assertEquals(1, robot.countOpen(60));
        assertEquals(0, robot.countOpen(200));
    }

    // ---- turnToward ----

    @Test
    void turnTowardFacesTheScannerAngle() {
        Testing.start(MAZE);
        Challenge robot = new Challenge();      // facing 90
        robot.turnToward(6);                    // -90: turn right 90
        assertEquals(0, Testing.heading(), 0.01);
        robot.turnToward(1);                    // 60: turn left 60
        assertEquals(60, Testing.heading(), 0.01);
        robot.turnToward(3);                    // 0: no turn
        assertEquals(60, Testing.heading(), 0.01);
        robot.turnToward(0);                    // 90: turn left 90
        assertEquals(150, Testing.heading(), 0.01);
    }

    // ---- explore ----

    @Test
    void exploreReachesTheGoal() {
        assertExplores(MAZE);
    }

    @Test
    void exploreReachesTheGoalInTheMirrorMaze() {
        assertExplores(MIRROR);
    }

    private static void assertExplores(String world) {
        Testing.start(world);
        Challenge robot = new Challenge();
        robot.explore();
        assertTrue(Testing.insideZone("Goal"), "the robot should end in the Goal, but it is at x = "
                + Testing.x() + ", y = " + Testing.y());
        assertEquals(0, Testing.bumps(), "no bumping into walls");
        assertTrue(Testing.sensorReads() >= 60,
                "explore() should keep reading its sensors, not drive a fixed route");
    }
}
