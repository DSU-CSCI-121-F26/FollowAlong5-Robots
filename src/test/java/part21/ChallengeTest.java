package part21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import mbot.Testing;
import org.junit.jupiter.api.Test;

// You do not change this file. It checks your Challenge.java.
// Run it with the green arrow next to "class ChallengeTest".
//
// Each test drives your robot in a world from the worlds/ folder, with no 3D viewer.
// Want to watch one? Put @MBotLab(world = "worlds/part21-challenge.json") on a class with a main,
// make a Challenge in it, and call the method.

class ChallengeTest {

    // ---- the constructor ----

    @Test
    void constructorPlugsInBothSensors() {
        Testing.start("worlds/part21-challenge.json");
        new Challenge();
        assertEquals(Set.of("ultrasonic", "line"), Testing.parts(),
                "the constructor should plug an Ultrasonic AND a LineSensor into the chassis");
    }

    // ---- stepsToWall ----

    @Test
    void stepsToWallFarWall() {
        Testing.start("worlds/part21-challenge.json");
        Challenge robot = new Challenge();
        assertEquals(54, robot.stepsToWall());
        assertEquals(0, Testing.bumps(), "the robot should stop before it touches the wall");
    }

    @Test
    void stepsToWallCloseBox() {
        Testing.start("worlds/part21-boxes.json");
        Challenge robot = new Challenge();
        assertEquals(14, robot.stepsToWall());
        assertEquals(0, Testing.bumps(), "the robot should stop before it touches the box");
    }

    @Test
    void stepsToWallReallyDrives() {
        Testing.start("worlds/part21-boxes.json");
        Challenge robot = new Challenge();
        robot.stepsToWall();
        assertEquals(30 + 14 * 5, Testing.y(), 0.01, "each step should be one straight(5)");
    }

    // ---- park ----

    @Test
    void parkEndsInsideTheBox() {
        Testing.start("worlds/part21-challenge.json");
        Challenge robot = new Challenge();
        robot.park();
        assertTrue(Testing.insideZone("P"), "the robot should end inside the taped box P, but it is at y = "
                + Testing.y());
        assertEquals(0, Testing.bumps());
    }

    @Test
    void parkUsesTheLineSensor() {
        Testing.start("worlds/part21-challenge.json");
        Challenge robot = new Challenge();
        robot.park();
        assertTrue(Testing.sensorReads() >= 20,
                "park() should keep checking the line sensor as it drives, not just drive a fixed distance");
    }
}
