# Part 22 Guide — Behaviors: teach your robot a skill

**There is no video for this part. This page is the lesson.**

Read one step at a time. **Type every line yourself.** Do not copy and paste from this page.
After some steps there is a **✅ Check**: run your program and make sure you see the same thing
before you go on.

You type into three files in the `part22` folder:

- **`Rover.java`**: steps 1, 2, 4, 5 and 8
- **`ObstacleRun.java`**: steps 3 and 6 (the **obstacle course** world)
- **`LineRun.java`**: step 9 (the **line track** world)

---

## Step 1 — The Rover, with both sensors

This part's `Rover` is like part 21's, with one more part: a **line sensor**. Open
`Rover.java` and type the fields, the constructor and `toString`:

```java
    private String name;
    private Chassis base;
    private Ultrasonic eyes;
    private LineSensor floor;

    public Rover(String name) {
        this.name = name;
        this.base = new Chassis();
        this.eyes = new Ultrasonic(base);
        this.floor = new LineSensor(base);
    }

    public String toString() {
        return name + " the rover";
    }
```

A Rover **has a** chassis, **has an** ultrasonic and **has a** line sensor. The chassis comes
first, because both sensors plug into it.

---

## Step 2 — A behavior: drive until blocked

A **behavior** is a skill: a method that **reads a sensor, decides, and moves**, again and
again, until the job is done.

Under the constructor, type:

```java
    public int driveUntilBlocked(double cm) {
        int moves = 0;
        while (eyes.distance() > cm) {
            base.straight(5);
            moves++;
        }
        return moves;
    }
```

Read the loop as a sentence: **while** the nearest thing ahead is **more than `cm`** away,
drive 5 cm and count it.

Two things are new compared with part 21:

- **`cm` is a parameter.** The caller decides how close is "too close": 15 cm, 40 cm,
  anything.
- **It gives back an `int`**: how many moves it took. A behavior can report what happened.

The sensor is read **every time** the loop checks its `( )`. That is what makes this a
behavior and not a fixed plan. Put the robot anywhere and it still stops in the right place.

---

## Step 3 — Try it

Open `ObstacleRun.java`. Look at the line above the class: `@MBotLab(world = "obstacles")`. In
this world there is an orange box in front of the robot, two cans, another box, and a green
**Goal** at the far end.

Type `main`, and inside it:

```java
        Rover rover = new Rover("Dash");
        int moves = rover.driveUntilBlocked(15);
        System.out.println("Blocked after " + moves + " moves");
```

**✅ Check 1.** Run it:

```
Blocked after 9 moves
```

In the 3D view, the robot drives toward the box in 5 cm jumps and stops. The first reading
was **58 cm**. After 9 moves it reads **13 cm**, which is not more than 15, so the loop
stops.

---

## Step 4 — A second behavior: go round on the left

Back in `Rover.java`, add:

```java
    public void goAroundLeft(double sideways) {
        base.turnLeft(90);
        base.straight(sideways);
        base.turnRight(90);
    }
```

Turn left, slide past the box, turn back. The robot ends up `sideways` cm to the left,
facing the same way as before.

This one does **not** read a sensor. It is a fixed move. Most skills are a mix: some steps
look, some steps just move.

---

## Step 5 — A behavior made of behaviors

Now put the small skills together into a big one. Add to `Rover.java`:

```java
    public void reachGoal() {
        int first = driveUntilBlocked(15);
        System.out.println("Blocked after " + first + " moves. Going round.");
        goAroundLeft(60);
        int second = driveUntilBlocked(15);
        System.out.println("Blocked after " + second + " moves. That is the far wall.");
        base.turnRight(90);
        base.straight(50);
    }
```

`reachGoal` reads almost like a plan written in English. That is the point of splitting a job
into small methods: each one does one thing, has a name that says what it does, and can be
used again. `driveUntilBlocked` is used **twice** here.

---

## Step 6 — Run the whole plan

In `ObstacleRun.java`, **delete** the two lines from step 3 that call `driveUntilBlocked` and
print. Replace them, so `main` is:

```java
        Rover rover = new Rover("Dash");
        rover.reachGoal();
        System.out.println(rover + " reached the goal");
```

**✅ Check 2.** Run it:

```
Blocked after 9 moves. Going round.
Blocked after 42 moves. That is the far wall.
Dash the rover reached the goal
```

In the 3D view: the robot stops at the box, steps left 60 cm, drives up past the box and the
red can, stops at the far wall, turns right, and ends inside the green **Goal**. Nothing gets
bumped.

In **Your code**, the `while (eyes.distance() > cm)` line shows **×53** and
`base.straight(5);` shows **×51**. That is 9 + 42 = 51 moves, and two extra looks (one at the
end of each loop).

Click any step inside `driveUntilBlocked`. Under **What's happening** you see **↳ called from
Rover.java** (a line in `reachGoal`) and **↳ called from ObstacleRun.java**: `main` called
`reachGoal`, and `reachGoal` called `driveUntilBlocked`.

---

## Step 7 — How the line sensor sees

The line sensor sits under the front of the robot. It has **four probes** in a row. Each one
can tell if it is over dark tape. Each probe has a number:

```
              front of the robot
     ┌─────────────────────────────────┐
     │  far left   left   right   far right
     │     8        4      2        1
     └─────────────────────────────────┘
```

`floor.status()` adds up the numbers of the probes that see tape:

| What the probes see | Number | What it means |
|---|---|---|
| none | **0** | no tape under the robot. It lost the line |
| left + right (the two middle ones) | 4 + 2 = **6** | the tape is right in the middle: go straight |
| left only | **4** | the tape is a little to the left |
| far left + left | 8 + 4 = **12** | the tape is to the left |
| right only | **2** | the tape is a little to the right |
| right + far right | 2 + 1 = **3** | the tape is to the right |
| all four | 8 + 4 + 2 + 1 = **15** | a wide patch of tape, or a corner |

The rule for following a line: **steer toward the tape.** Tape on the left, turn left. Tape on
the right, turn right. Tape in the middle, go straight.

---

## Step 8 — A behavior that remembers

What if the robot runs off the tape and sees **0**? Which way should it turn? It cannot tell
from 0 alone. But it can **remember** which side the tape was on last time.

Add one more field to `Rover.java`, under the other four:

```java
    private boolean lineWasLeft;
```

A `boolean` field starts as `false`. The rover will change it as it drives.

Now add the behavior:

```java
    public void followLine(int steps) {
        for (int i = 0; i < steps; i++) {
            int s = floor.status();
            if (s == 4 || s == 8 || s == 12) {
                base.turnLeft(4);
                lineWasLeft = true;
            } else if (s == 1 || s == 2 || s == 3) {
                base.turnRight(4);
                lineWasLeft = false;
            } else if (s == 0) {
                if (lineWasLeft) {
                    base.turnLeft(4);
                } else {
                    base.turnRight(4);
                }
            } else {
                base.straight(1);
            }
        }
    }
```

Each time round the loop is one **step**: read the sensor **once**, then do **one** small
move. The `else` at the end catches 6, 15 and anything else: the tape is under the middle,
so it drives straight 1 cm.

The field `lineWasLeft` is the rover's **memory**. A local variable inside `followLine` would
be forgotten every time the method ends. A field stays with the object.

---

## Step 9 — Follow the loop

Open `LineRun.java`. The world is `line-track`: a loop of tape, and the robot starts on it.
Type `main`:

```java
        Rover rover = new Rover("Tracker");
        rover.followLine(400);
        System.out.println("Halfway round");
        rover.followLine(325);
        System.out.println(rover + " finished a lap");
```

**✅ Check 3.** Run it. At the bottom of the 3D view, set the speed to **10×**.

```
Halfway round
Tracker the rover finished a lap
```

The robot wiggles along the tape. Every wiggle is one `turnLeft(4)` or `turnRight(4)`. After
400 steps it is on the far side of the loop, heading back. After 725 steps (400 + 325) it is
back near where it started. The lap takes about **61 seconds** of robot time. Nothing gets
bumped.

Step through a few steps with ▶| and watch the **Line sensor** squares in the top-right box.
Dark squares are probes that see tape. Check that each turn matches the table in step 7.

---

## Why behaviors?

Look at `main` in `ObstacleRun.java` and `LineRun.java`. Each is three to five lines. All the
thinking is inside the `Rover`, in small methods with clear names. To change **how** the robot
follows a line, you change one method. Nothing in `main` changes.

Later in the course you will meet **interfaces**. They let you swap one whole behavior for
another, like changing a robot's skill without rebuilding the robot.

---

## Your finished code

Compare only **after** you have typed it yourself.

<details>
<summary>Rover.java</summary>

```java
package part22;

import mbot.*;

public class Rover {
    private String name;
    private Chassis base;
    private Ultrasonic eyes;
    private LineSensor floor;
    private boolean lineWasLeft;

    public Rover(String name) {
        this.name = name;
        this.base = new Chassis();
        this.eyes = new Ultrasonic(base);
        this.floor = new LineSensor(base);
    }

    public int driveUntilBlocked(double cm) {
        int moves = 0;
        while (eyes.distance() > cm) {
            base.straight(5);
            moves++;
        }
        return moves;
    }

    public void goAroundLeft(double sideways) {
        base.turnLeft(90);
        base.straight(sideways);
        base.turnRight(90);
    }

    public void reachGoal() {
        int first = driveUntilBlocked(15);
        System.out.println("Blocked after " + first + " moves. Going round.");
        goAroundLeft(60);
        int second = driveUntilBlocked(15);
        System.out.println("Blocked after " + second + " moves. That is the far wall.");
        base.turnRight(90);
        base.straight(50);
    }

    public void followLine(int steps) {
        for (int i = 0; i < steps; i++) {
            int s = floor.status();
            if (s == 4 || s == 8 || s == 12) {
                base.turnLeft(4);
                lineWasLeft = true;
            } else if (s == 1 || s == 2 || s == 3) {
                base.turnRight(4);
                lineWasLeft = false;
            } else if (s == 0) {
                if (lineWasLeft) {
                    base.turnLeft(4);
                } else {
                    base.turnRight(4);
                }
            } else {
                base.straight(1);
            }
        }
    }

    public String toString() {
        return name + " the rover";
    }
}
```

</details>

<details>
<summary>ObstacleRun.java</summary>

```java
package part22;

import mbot.*;

@MBotLab(world = "obstacles")
public class ObstacleRun {
    public static void main(String[] args) {
        Rover rover = new Rover("Dash");
        rover.reachGoal();
        System.out.println(rover + " reached the goal");
    }
}
```

</details>

<details>
<summary>LineRun.java</summary>

```java
package part22;

import mbot.*;

@MBotLab(world = "line-track")
public class LineRun {
    public static void main(String[] args) {
        Rover rover = new Rover("Tracker");
        rover.followLine(400);
        System.out.println("Halfway round");
        rover.followLine(325);
        System.out.println(rover + " finished a lap");
    }
}
```

</details>
