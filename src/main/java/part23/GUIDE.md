# Part 23 Guide — Inheritance: build your own robot

**There is no video for this part. This page is the lesson.**

Read one step at a time. **Type every line yourself.** Do not copy and paste from this page.
After some steps there is a **✅ Check**: run your program and make sure you see the same thing
before you go on.

You type into two files in the `part23` folder:

- **`Scout.java`**: steps 1, 2 and 4
- **`Explore.java`**: steps 3, 5 and 6 (the **junctions** world)

---

## Step 1 — A robot that IS an MBot2

Open `Scout.java`. The class line says:

```java
public class Scout {
```

Change it to:

```java
public class Scout extends MBot2 {
```

That one word, **`extends`**, is **inheritance**. It means: *a Scout **is an** MBot2, plus
whatever we add.* A Scout gets every method an MBot2 has, without writing any of them:
`straight`, `turnLeft`, `turnRight`, `readUltrasonic`, the lights, all of it.

Some words for this:

- **`MBot2` is the superclass**: the class you build on.
- **`Scout` is the subclass**: the new class, built on top.
- And `MBot2` itself `extends Chassis`. So a Scout **is an** MBot2, and an MBot2 **is a**
  Chassis. It is a chain.

Compare part 21. There, a `Rover` **had a** `Chassis` inside it, as a field. That was
**composition**: *has-a*. This is **inheritance**: *is-a*. Both are ways to build a big
object out of smaller pieces.

---

## Step 2 — Fields and a constructor with `super()`

Inside the class, type the fields and the constructor:

```java
    private String name;
    private Scanner scanner;

    public Scout(String name) {
        super();
        this.name = name;
        this.scanner = new Scanner(this);
    }
```

Three new ideas:

- **`super();`** builds the **MBot2 part** of the Scout: the wheels, the motors, and the
  kit's ultrasonic and line sensor. It must be the **first** line of the constructor. You
  cannot add parts to a robot before the robot exists.
- **`private Scanner scanner;`** is a sensor this robot **chose** to carry. An MBot2 does not
  come with one. A **scanner** is an ultrasonic sensor on a small motor (a **servo**), so it
  can turn and look in 7 directions, without the robot turning.
- **`new Scanner(this)`**: `this` is the Scout being built. A scanner plugs into a
  `Chassis`. A Scout **is an** MBot2, which **is a** Chassis. So Java is happy to plug the
  scanner into it.

Then add `toString`, under the constructor:

```java
    public String toString() {
        return name + " the scout";
    }
```

---

## Step 3 — An inherited skill

Add this method under the constructor:

```java
    public int driveUntilBlocked() {
        int moves = 0;
        while (readUltrasonic() > 25) {
            straight(5);
            moves++;
        }
        return moves;
    }
```

Look closely: `readUltrasonic()` and `straight(5)` have **nothing in front of them**. No
`base.`, no `eyes.`. In part 21 the Rover had to ask its parts: `eyes.distance()`,
`base.straight(5)`. A Scout does not ask anyone. It **is** the robot, so it calls its own
inherited methods.

Now open `Explore.java`. Look at the line above the class: `@MBotLab(world = "junctions")`.
This world is a corridor. It goes up, turns right, then turns left, up to a green **Goal**.

Type `main`, and inside it:

```java
        Scout scout = new Scout("Pathfinder");
        int moves = scout.driveUntilBlocked();
        System.out.println("Leg 1: " + moves + " moves");
```

**✅ Check 1.** Run it:

```
Leg 1: 26 moves
```

In the 3D view, the robot drives up the corridor and stops at the wall. Look at the **Your
robot** box on the right. It draws your inheritance: `Chassis` at the top, then `MBot2`, then
your `Scout` at the bottom, with its fields: `String name` and `Scanner scanner ✓`. The ✓
means the scanner is really plugged in.

Click any step in the run. Under **What's happening** you see *"readUltrasonic() is
inherited: Scout gets it from MBot2"* and *"straight() is inherited: Scout gets it from
Chassis"*.

---

## Step 4 — Your own skill: find the most open way

A scan gives back an **array** of 7 distances. They are in this order, the same order as
`Scanner.ANGLES`:

| index | 0 | 1 | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|---|---|
| angle (`Scanner.ANGLES[i]`) | 90 | 60 | 30 | 0 | -30 | -60 | -90 |
| which way | far left | | | straight ahead | | | far right |

A **positive** angle is to the **left**. A **negative** angle is to the **right**.

Back in `Scout.java`, add:

```java
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
```

This is the "find the biggest" pattern from part 10, but it keeps the **index** of the biggest
(`best`), not the number itself. Start by guessing index 0. Then look at every other index.
If it is bigger than the best so far, it becomes the new best.

`scanner.scan()` is **not** inherited. `scanner` is a field: the Scout **has a** scanner. So
you ask it, with `scanner.`. A class can use inheritance **and** composition at the same time.

Then add one more method, to turn toward that open way:

```java
    public void turnTowardOpenSide() {
        int i = openestIndex();
        if (Scanner.ANGLES[i] > 0) {
            turnLeft(90);
        } else {
            turnRight(90);
        }
    }
```

In a corridor, the open way is always a quarter turn: left or right.

---

## Step 5 — Look before you turn

In `Explore.java`, add two lines at the end of `main`:

```java
        int i = scout.openestIndex();
        System.out.println("Most open way: index " + i + ", angle " + Scanner.ANGLES[i]);
```

**✅ Check 2.** Run it:

```
Leg 1: 26 moves
Most open way: index 6, angle -90
```

In the 3D view: at the wall, the scanner on top turns and sends out **7 orange lines**, one at
a time. The one to the right goes all the way down the corridor. In the box at the top right,
**Scanner** shows the 7 distances, far left first:

```
40 43 29 27 29 44 260
```

Index 6 is 260: the biggest. Its angle is -90: to the right.

---

## Step 6 — Explore the whole corridor

Now let the Scout find its own way. **Delete** everything in `main` after the line that makes
the Scout. Replace it with a loop, so `main` is:

```java
        Scout scout = new Scout("Pathfinder");
        for (int leg = 1; leg <= 3; leg++) {
            int moves = scout.driveUntilBlocked();
            System.out.println("Leg " + leg + ": " + moves + " moves");
            if (leg < 3) {
                scout.turnTowardOpenSide();
            }
        }
        System.out.println(scout + " reached the goal");
```

Three legs: drive, look, turn; drive, look, turn; drive. After the last leg there is nothing
to turn toward, so the `if` skips the turn.

**✅ Check 3.** Run it:

```
Leg 1: 26 moves
Leg 2: 46 moves
Leg 3: 22 moves
Pathfinder the scout reached the goal
```

In the 3D view: up, scan, turn **right**, along, scan, turn **left**, up into the green
**Goal**. Nothing gets bumped. At the second scan, the biggest distance is on the **left**
(index 0), so the same method turns the other way.

In **Your code**, the `while (readUltrasonic() > 25)` line shows **×97** and `straight(5);`
shows **×94**. That is 26 + 46 + 22 = 94 moves, and three extra looks.

---

## Why inheritance?

Look at `Scout.java`. It has **no** driving code in it: no wheels, no motors. It only has what
makes a Scout different from a plain MBot2: a name, a scanner, and two skills. Everything else
came from `MBot2` and `Chassis`, with one word: `extends`.

Another student can write a **different** robot that also `extends MBot2`, with different
sensors and different skills. You do that in the stretch: a `Painter` with a color sensor.
Both are MBot2s. Neither one copies the other.

---

## Your finished code

Compare only **after** you have typed it yourself.

<details>
<summary>Scout.java</summary>

```java
package part23;

import mbot.*;

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
```

</details>

<details>
<summary>Explore.java</summary>

```java
package part23;

import mbot.*;

@MBotLab(world = "junctions")
public class Explore {
    public static void main(String[] args) {
        Scout scout = new Scout("Pathfinder");
        for (int leg = 1; leg <= 3; leg++) {
            int moves = scout.driveUntilBlocked();
            System.out.println("Leg " + leg + ": " + moves + " moves");
            if (leg < 3) {
                scout.turnTowardOpenSide();
            }
        }
        System.out.println(scout + " reached the goal");
    }
}
```

</details>
