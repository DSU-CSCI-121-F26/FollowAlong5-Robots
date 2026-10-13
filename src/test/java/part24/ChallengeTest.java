package part24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mbot.MBot2;
import mbot.Testing;
import org.junit.jupiter.api.Test;

// You do not change this file. It checks your Challenge.java.
// Run it with the green arrow next to "class ChallengeTest".
//
// Each test drives a robot in a world from the worlds/ folder, with no 3D viewer.
// Want to watch one? See "Want to watch?" in the README.

class ChallengeTest {

    private static final String HALL = "worlds/part24-hall.json";   // start y = 40, end wall at y = 300
    private static final String YARD = "worlds/part24-yard.json";

    /** Another robot class, only for these tests. Its straight() only goes half as far. */
    static class HalfStep extends MBot2 {
        @Override
        public void straight(double cm) {
            super.straight(cm / 2);
        }
    }

    // ---- straight() ----

    @Test
    void straightStopsBeforeTheWall() {
        Testing.start(HALL);
        Challenge robot = new Challenge();
        robot.straight(500);
        assertEquals(0, Testing.bumps(), "a safe robot never bumps into anything");
        double left = robot.readUltrasonic();
        assertTrue(left > 15 && left <= 20,
                "it should stop when the ultrasonic reads 20 or less; it reads " + left);
    }

    @Test
    void straightDrivesTheWholeWayWhenTheWayIsClear() {
        Testing.start(HALL);
        Challenge robot = new Challenge();
        robot.straight(30);
        assertEquals(70, Testing.y(), 0.01, "30 cm, as asked");
        robot.straight(32);
        assertEquals(102, Testing.y(), 0.01, "32 cm: six steps of 5, then one of 2");
    }

    @Test
    void straightReadsTheSensorBeforeEachStep() {
        Testing.start(HALL);
        Challenge robot = new Challenge();
        robot.straight(30);
        assertTrue(Testing.sensorReads() >= 6, "30 cm is 6 steps: read the ultrasonic before each one");
    }

    @Test
    void straightStillBacksUp() {
        Testing.start(HALL);
        Challenge robot = new Challenge();
        robot.straight(-20);
        assertEquals(20, Testing.y(), 0.01, "a negative distance backs up, like MBot2's straight()");
    }

    // ---- run() ----

    @Test
    void runGoesRoundTheYardAndComesHome() {
        Testing.start(YARD);
        MBot2 robot = new Challenge();      // an MBot2 variable: your run() must still be the one that runs
        robot.run();
        assertTrue(Testing.insideZone("Home"), "the robot should end at Home, but it is at x = "
                + Testing.x() + ", y = " + Testing.y());
        assertEquals(0, Testing.bumps(), "no bumping into the walls");
        assertEquals(90, Testing.heading(), 0.01, "after four right turns it faces the way it started");
        assertTrue(Testing.time() > 30, "it should really drive round the yard, not just turn on the spot");
    }

    // ---- stepsToWall(MBot2 bot): one method, any robot ----

    @Test
    void stepsToWallWithYourRobot() {
        Testing.start(HALL);
        assertEquals(24, Challenge.stepsToWall(new Challenge()));
        assertEquals(275, Testing.y(), 0.01, "your safe straight() stops each step early near the wall");
    }

    @Test
    void stepsToWallWithAPlainMBot2() {
        Testing.start(HALL);
        assertEquals(24, Challenge.stepsToWall(new MBot2()));
        assertEquals(280, Testing.y(), 0.01);
    }

    @Test
    void stepsToWallWithAnotherKindOfRobot() {
        Testing.start(HALL);
        assertEquals(47, Challenge.stepsToWall(new HalfStep()),
                "this robot's straight(10) only goes 5 cm, so it takes about twice as many steps");
    }
}
