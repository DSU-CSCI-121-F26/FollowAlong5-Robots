# Part 24 Guide — Overriding and polymorphism: same command, different robot

**There is no video for this part. This page is the lesson.**

Read one step at a time. **Type every line yourself.** Do not copy and paste from this page.
After some steps there is a **✅ Check**: run your program and make sure you see the same thing
before you go on.

You type into three files in the `part24` folder. All three run in the **wall ahead** world:
a wall about 1.5 m in front of the robot.

- **`CarefulBot.java`**: steps 1 and 3
- **`FlashyBot.java`**: step 4
- **`Mission.java`**: steps 2, 5 and 7

---

## Step 1 — A new robot class

Open `CarefulBot.java`. The class line already says `public class CarefulBot extends MBot2`.
From part 23 you know what that means: a CarefulBot **is an** MBot2.

Type the constructor:

```java
    public CarefulBot() {
        super();
    }
```

That is all, for now. A CarefulBot can already do everything an MBot2 can.

---

## Step 2 — One mission, for any robot

Open `Mission.java`. Inside the class, type a method **and** `main`:

```java
    public static void mission(MBot2 bot) {
        System.out.println("Mission start");
        bot.run();
        System.out.println("Mission done");
    }

    public static void main(String[] args) {
        int pick = 1;
        MBot2 bot;
        if (pick == 1) {
            bot = new CarefulBot();
        } else if (pick == 2) {
            bot = new FlashyBot();
        } else {
            bot = new MBot2();
        }
        mission(bot);
    }
```

Look at the **types**:

- The parameter of `mission` is an **`MBot2`**. You can pass it **any** robot that extends
  MBot2.
- The variable `bot` in `main` is an **`MBot2`** too. It can hold a `CarefulBot`, a
  `FlashyBot`, or a plain `MBot2`, because each of them **is an** MBot2.

`run()` is a method every MBot2 has. It is meant to be the robot's **mission**. But MBot2's
own `run()` is **empty**: it does nothing. A plain MBot2 has no mission yet.

**✅ Check 1.** Run it:

```
Mission start
Mission done
```

The robot does **not** move. Under **What's happening**, the 3D view says *"run() was called,
but CarefulBot does not override it, so MBot2's empty run() ran and nothing happened."*

That is exactly right. CarefulBot inherited the empty `run()`. Next, we replace it.

---

## Step 3 — Override: same name, new behavior

Back in `CarefulBot.java`, under the constructor, type:

```java
    @Override
    public void straight(double cm) {
        if (readUltrasonic() > cm + 20) {
            super.straight(cm);
        } else {
            turnLedOn(1, 255, 0, 0);
            System.out.println("CarefulBot: not driving " + cm + " cm, too close");
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            straight(40);
        }
    }
```

This is **overriding**: a method in the subclass with **the same name and the same
parameters** as one in the superclass. For a CarefulBot, this new `straight` **replaces** the
old one.

Three things to notice:

- **`@Override`** is a note to Java: *this method is meant to replace one from the
  superclass.* If you get the name or the parameter wrong, Java gives a red error. Without
  `@Override`, a typo just makes a new, different method, and nothing tells you.
- **`super.straight(cm)`** runs the **old** `straight`, the one from Chassis. The careful
  robot keeps the normal way of driving, and only adds a check in front of it.
- **`run()`** calls `straight(40)`. That runs the **CarefulBot's** `straight`, the careful
  one. Inside a CarefulBot, every `straight` is the careful one.

**✅ Check 2.** Run `Mission.java` again:

```
Mission start
CarefulBot: not driving 40.0 cm, too close
CarefulBot: not driving 40.0 cm, too close
Mission done
```

In the 3D view: the robot drives 40 cm three times. The wall starts 153 cm away, so after
three moves it is 33 cm away, and 33 is not more than 40 + 20. It refuses twice, with light 1
red. Nothing gets bumped.

Click a step where it drives. Under **What's happening**: *"Inside CarefulBot.straight(),
super.straight() runs Chassis's version."*

---

## Step 4 — A second robot that overrides different methods

Open `FlashyBot.java`. Type:

```java
    public FlashyBot() {
        super();
    }

    @Override
    public void turnLeft(double degrees) {
        System.out.println("Flash! Turning left " + degrees);
        turnLedOn(1, 0, 0, 255);
        super.turnLeft(degrees);
        turnLedOff(1);
    }

    @Override
    public void turnRight(double degrees) {
        System.out.println("Flash! Turning right " + degrees);
        turnLedOn(1, 0, 255, 0);
        super.turnRight(degrees);
        turnLedOff(1);
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            straight(40);
            turnLeft(90);
        }
    }
```

A FlashyBot does **not** override `straight`, so it drives the normal way. It overrides the
two **turns**: light on, the old turn (`super.turnLeft`), light off. Its mission is a square.

---

## Step 5 — Same mission, different robot

In `Mission.java`, change `int pick = 1;` to `int pick = 2;`.

**✅ Check 3.** Run it:

```
Mission start
Flash! Turning left 90.0
Flash! Turning left 90.0
Flash! Turning left 90.0
Flash! Turning left 90.0
Mission done
```

In the 3D view: a 40 cm square, with light 1 flashing blue at every corner. It ends where it
started.

---

## Step 6 — What just happened: polymorphism

Look at `mission` again. It did not change. It still says `bot.run()`. But:

- with a **CarefulBot** in `bot`, it ran CarefulBot's `run()`: careful steps to the wall;
- with a **FlashyBot** in `bot`, it ran FlashyBot's `run()`: a flashing square.

The **variable** is an `MBot2` both times. The **object** in it was different, and **the
object decides** which version of a method runs. This is called **polymorphism**: one call,
`bot.run()`, and many forms of behavior.

Why does that matter? `mission` was written **before** FlashyBot existed. Anyone can write a
new robot class tomorrow, and `mission` will run it, without changing a single line.

---

## Step 7 — And a plain MBot2?

Change `pick` to `3`.

**✅ Check 4.** Run it:

```
Mission start
Mission done
```

The robot does not move, and the 3D view says *"MBot2's own run() does nothing."* A plain MBot2
uses MBot2's empty `run()`, because nothing overrides it.

**Set `pick` back to 1** before you commit.

---

## Overloading is not overriding

One trap, before the in-class exercise. This is **not** an override:

```java
    public void straight(int cm) { ... }
```

MBot2's `straight` takes a **`double`**. This one takes an **`int`**. Different parameter
types make it a **different** method that happens to have the same name. (That is called
**overloading**: you saw it in part 14.) Code that uses an `MBot2` variable calls
`straight(double)`, so this method never runs. `@Override` is how you catch it: Java gives a
red error, because there is nothing to override.

---

## Your finished code

Compare only **after** you have typed it yourself. (Stretch B adds a field and a `toString` to
the two robots, and changes the two prints in `mission`.)

<details>
<summary>CarefulBot.java</summary>

```java
package part24;

import mbot.*;

public class CarefulBot extends MBot2 {

    public CarefulBot() {
        super();
    }

    @Override
    public void straight(double cm) {
        if (readUltrasonic() > cm + 20) {
            super.straight(cm);
        } else {
            turnLedOn(1, 255, 0, 0);
            System.out.println("CarefulBot: not driving " + cm + " cm, too close");
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            straight(40);
        }
    }
}
```

</details>

<details>
<summary>FlashyBot.java</summary>

```java
package part24;

import mbot.*;

public class FlashyBot extends MBot2 {

    public FlashyBot() {
        super();
    }

    @Override
    public void turnLeft(double degrees) {
        System.out.println("Flash! Turning left " + degrees);
        turnLedOn(1, 0, 0, 255);
        super.turnLeft(degrees);
        turnLedOff(1);
    }

    @Override
    public void turnRight(double degrees) {
        System.out.println("Flash! Turning right " + degrees);
        turnLedOn(1, 0, 255, 0);
        super.turnRight(degrees);
        turnLedOff(1);
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            straight(40);
            turnLeft(90);
        }
    }
}
```

</details>

<details>
<summary>Mission.java</summary>

```java
package part24;

import mbot.*;

@MBotLab(world = "wall-ahead")
public class Mission {

    public static void mission(MBot2 bot) {
        System.out.println("Mission start");
        bot.run();
        System.out.println("Mission done");
    }

    public static void main(String[] args) {
        int pick = 1;
        MBot2 bot;
        if (pick == 1) {
            bot = new CarefulBot();
        } else if (pick == 2) {
            bot = new FlashyBot();
        } else {
            bot = new MBot2();
        }
        mission(bot);
    }
}
```

</details>
