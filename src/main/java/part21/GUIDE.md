# Part 21 Guide — Composition: your robot has parts

**There is no video for this part. This page is the lesson.**

Read one step at a time. **Type every line yourself.** Do not copy and paste from this page.
After some steps there is a **✅ Check**: run your program and make sure you see the same thing
before you go on.

You type into two files in the `part21` folder:

- **Steps 2–3 and 6–7:** `Rover.java`, your own robot class
- **Steps 4–5 and 8:** `FollowAlong.java`, the `main` that uses it

---

## Step 1 — Objects made of objects

Look at your real mBot2. It is not one thing. It is a **chassis** (the blue frame, the wheels,
the motors and the CyberPi on top), plus **sensors** plugged into it: the ultrasonic "eyes"
on the front, and the line sensor underneath.

Java lets you build an object the same way: out of other objects. That is called
**composition**.

```
        Rover  ("Scout")
        ├── name : String       "Scout"
        ├── base : Chassis      the wheels, motors and CyberPi
        └── eyes : Ultrasonic   plugged into base
```

You say it out loud with the words **has a**:

- A Rover **has a** name.
- A Rover **has a** Chassis.
- A Rover **has an** Ultrasonic.

Each "has a" becomes a **field** in your class. You already know fields from part 15 (a
`Car` has a `make` and a `model`). The only new thing: a field's type can be a **class**,
like `Chassis`, not just `String` or `int`.

Here are the three classes you will use. They come from the robot library (`mbot`), so you
do not write them. You only make objects from them:

| Class | What it is | How to make one |
|---|---|---|
| `Chassis` | The robot with **no sensors**: wheels, motors, lights | `new Chassis()` |
| `Ultrasonic` | A distance sensor. `distance()` gives back how far away the nearest thing in front is, in cm | `new Ultrasonic(base)`: you hand it the chassis to plug into |
| `LineSensor` | A tape sensor, for the floor. You use it in the Stretch | `new LineSensor(base)` |

---

## Step 2 — The fields

Open `Rover.java`. It already has the `package` line, `import mbot.*;` and the class line.
The `import` line is what lets you use `Chassis` and `Ultrasonic`.

Inside the class's `{ }`, type the three fields:

```java
    private String name;
    private Chassis base;
    private Ultrasonic eyes;
```

| Piece | Meaning |
|---|---|
| `private` | only code **inside** `Rover` can touch this field. `main` cannot reach in and grab the chassis |
| `Chassis` | the type: this field holds a `Chassis` object |
| `base` | its name. "Base" because the chassis is what everything else sits on |

Right now these fields hold **nothing**. A field that holds an object starts out as `null`,
which means "no object yet". Declaring a field does **not** make the object. Only `new` does.

---

## Step 3 — The constructor makes the parts

Under the fields, type the constructor:

```java
    public Rover(String name) {
        this.name = name;
        this.base = new Chassis();
        this.eyes = new Ultrasonic(base);
    }
```

Read it line by line:

1. `this.name = name;` keeps the name you were given (the same as part 16).
2. `this.base = new Chassis();` **makes** the chassis, and keeps it in the field `base`.
3. `this.eyes = new Ultrasonic(base);` **makes** a sensor and **plugs it into** `base`.

**The order matters.** The sensor needs a chassis to plug into. If line 3 came before line 2,
`base` would still be `null`, and the sensor would have nothing to plug into. (You will see
exactly what happens in the in-class exercise.)

---

## Step 4 — Make a Rover in main

Open `FollowAlong.java`. Look at the line above the class:

```java
@MBotLab(world = "wall-ahead")
```

That picks the room your robot starts in: a room with a wall about 1.5 m in front of the
robot. Leave it alone.

Inside the class, type `main`, and inside `main`:

```java
        Rover scout = new Rover("Scout");
        System.out.println(scout);
```

`new Rover("Scout")` runs your constructor. Your constructor makes the chassis and the
sensor. So **one** `new` in `main` builds the whole robot.

---

## Step 5 — Run it

Click the **green ▶** next to `main`.

**✅ Check 1.** The Run window shows something like this (your letters after `@` will be
different):

```
part21.Rover@1b6d3586
```

A browser tab opens with the 3D view. The robot sits still in front of the wall. Look at it:
it has **two blue eyes** on the front. That is your `Ultrasonic`. In the box on the top right,
**Line sensor** is greyed out: your rover does not have one. **Your robot only has the parts
your constructor made.**

Look at **What's happening** on the right, and step through with ▶| at the bottom. The first
two steps are your constructor's lines: *"A bare chassis turned on…"* and *"Plugged an
ultrasonic sensor into the front of the robot."*

---

## Step 6 — toString

`part21.Rover@1b6d3586` is not very friendly. Back in `Rover.java`, under the constructor,
add a `toString` method (you did this in part 17):

```java
    public String toString() {
        return name + " the rover";
    }
```

In `FollowAlong.java`, change the `println` line to:

```java
        System.out.println(scout + " is ready");
```

**✅ Check 2.** Run it:

```
Scout the rover is ready
```

---

## Step 7 — Ask the sensor

Now teach the rover to use its eyes. In `Rover.java`, add two methods:

```java
    public boolean somethingAhead(double cm) {
        return eyes.distance() < cm;
    }

    public void driveUntilBlocked() {
        while (!somethingAhead(20)) {
            base.straight(5);
        }
    }
```

**`somethingAhead`** asks the sensor how far away things are, with `eyes.distance()`. Then it
gives back `true` if that is less than `cm`.

**`driveUntilBlocked`** keeps driving 5 cm at a time **while** nothing is closer than 20 cm.
The `!` means "not": `while (!somethingAhead(20))` reads as *"while there is not something
ahead within 20 cm"*.

Look at **who does what**:

- `main` talks only to the `Rover`.
- The `Rover` talks to its parts: `eyes.distance()` and `base.straight(5)`.

That is the point of composition. The rover hands each job to the part that knows how to do it.

---

## Step 8 — Drive to the wall

In `FollowAlong.java`, under the `println`, add:

```java
        scout.driveUntilBlocked();
        System.out.println(scout + " stopped. Something is less than 20 cm ahead.");
```

**✅ Check 3.** Run it:

```
Scout the rover is ready
Scout the rover stopped. Something is less than 20 cm ahead.
```

In the 3D view, the robot drives forward in small jumps and stops in front of the wall. Each
time it reads the sensor, a blue cone shows where the sensor is looking, and a red dot shows
what it saw.

- **Ultrasonic** in the top-right box ends at **18.0 cm**.
- In **Your code**, `base.straight(5);` shows **×27**: it drove 27 times.
- The `return eyes.distance() < cm;` line shows **×28**: it looked 28 times. The first look
  said **153 cm**, then 148, 143, and so on. The last look said 18, which is less than 20, so
  the loop stopped **without** driving again. That is why it looks one more time than it
  drives.

Click a step where the robot reads the sensor. Under **What's happening** you see the line in
`Rover.java` that ran, and under it, **↳ called from Rover.java** (the `while` line), and
**↳ called from FollowAlong.java** (your `driveUntilBlocked` line in `main`). That is the
chain: `main` asked the rover, and the rover asked its eyes.

---

## Step 9 — What if a part is missing? (try it, then undo it)

1. In the constructor, put `//` in front of the `this.eyes = new Ultrasonic(base);` line.
2. Run `FollowAlong.java`.

It crashes:

```
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "mbot.Ultrasonic.distance()" because "this.eyes" is null
```

The 3D view has no eyes on the robot this time, and **What's happening** says:

> *"eyes" is null: it was declared, but never given an object.*

The field `eyes` was **declared** (step 2), but nothing ever **made** an `Ultrasonic` and put
it there. So when `somethingAhead` asked `eyes` for the distance, there was nobody to ask.

3. **Take the `//` away again.** Run it, and make sure Check 3 works.

This is the most common composition mistake. Remember the message: **"is null" means a
`new` is missing.**

---

## Your finished code

Compare only **after** you have typed it yourself.

<details>
<summary>Rover.java</summary>

```java
package part21;

import mbot.*;

public class Rover {
    private String name;
    private Chassis base;
    private Ultrasonic eyes;

    public Rover(String name) {
        this.name = name;
        this.base = new Chassis();
        this.eyes = new Ultrasonic(base);
    }

    public boolean somethingAhead(double cm) {
        return eyes.distance() < cm;
    }

    public void driveUntilBlocked() {
        while (!somethingAhead(20)) {
            base.straight(5);
        }
    }

    public String toString() {
        return name + " the rover";
    }
}
```

</details>

<details>
<summary>FollowAlong.java</summary>

```java
package part21;

import mbot.*;

@MBotLab(world = "wall-ahead")
public class FollowAlong {
    public static void main(String[] args) {
        Rover scout = new Rover("Scout");
        System.out.println(scout + " is ready");

        scout.driveUntilBlocked();
        System.out.println(scout + " stopped. Something is less than 20 cm ahead.");
    }
}
```

</details>
