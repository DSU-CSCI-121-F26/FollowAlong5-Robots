# Java Follow-Along 5: Robots (Parts 21–24)

**CSCI-121 · Fall 2026 · No AI**

This is **repo 5**. It holds Parts 21 to 24. Everything works the same as the last four
repos: same rules, same sections, same commits.

**One big difference: every program drives a robot.** You build your own robot class out of
parts, give it sensors, and teach it skills. Then you build robot classes **on top of** the
MBot2, and give each one its own way of doing things. When you run your code, the robot drives in 3D
in your browser. The code you write here is the same kind of code that will drive your real
mBot2 later in the course.

**👉 Start here:** do **[Set up this repo](#set-up-this-repo-5-minutes)** below, then open
[Part 21](src/main/java/part21/README.md).

---

## Set up this repo (5 minutes)

You did this once already, in Part 00. This time is shorter: your IntelliJ settings carry
over, so there is nothing to turn off again.

### 1. Make your fork

1. Go to **github.com/DSU-CSCI-121-F26/FollowAlong5-Robots**.
2. Click **Fork** → **Create fork**.
3. On your fork, click the **Actions** tab → **I understand my workflows, go ahead and
   enable them.**

**✅ Check:** the top of the page says `your-username / FollowAlong5-Robots`.

### 2. Download it to your laptop

**Do not do this inside your last repo's folder.** If your terminal is still in your last
repo, type `cd ..` first to step out of it.

```bash
git clone https://github.com/YOUR-USERNAME/FollowAlong5-Robots.git
cd FollowAlong5-Robots
git remote add upstream https://github.com/DSU-CSCI-121-F26/FollowAlong5-Robots.git
git remote -v
```

Replace `YOUR-USERNAME` in the **first line only**.

**✅ Check:** four lines. The two `origin` lines show **your username**. The two `upstream`
lines show `DSU-CSCI-121-F26`.

### 3. Open it in IntelliJ

**File → Open** → choose the `FollowAlong5-Robots` folder → **Trust Project**. If IntelliJ asks,
open it in a **New Window**. If it asks about the JDK, pick version **21 or higher**, the same
as Part 00.

IntelliJ now downloads the **robot simulator** (a library called `mbot-sim`). This needs the
internet, and only happens once. Wait until the progress bar at the bottom is gone.

**✅ Check:** open `src/main/java/part21/FollowAlong.java`. The line `import mbot.*;` is **not**
red. (If it is red: right-click `pom.xml` → **Maven** → **Reload project**, and wait.)

**✅ Check:** click inside any `.java` file and type `Sys`. Wait two seconds. **No list should
pop up.** If one does, redo
[Part 00, step 4](https://github.com/DSU-CSCI-121-F26/FollowAlong1-Basics/blob/main/src/main/java/part00/README.md#step-4--turn-off-the-autocomplete-and-the-ai).
Delete what you typed.

### 4. About the 3D view

The first time you run a robot program (in Part 21), a tab opens in your browser at
**http://localhost:8121**. Keep that tab open: every time you run a robot program again, it
replays the new run by itself.

Now go to **[Part 21](src/main/java/part21/README.md)**.

---

## The five repos

| Repo | Parts | What it covers |
|---|---|---|
| **1. [FollowAlong1-Basics](https://github.com/DSU-CSCI-121-F26/FollowAlong1-Basics)** | 00–05 | setup, printing, variables, user input, math, random numbers |
| **2. [FollowAlong2-ControlFlow](https://github.com/DSU-CSCI-121-F26/FollowAlong2-ControlFlow)** | 06–09 | `if`, `switch`, logical operators, loops, checkpoint: guessing game |
| **3. [FollowAlong3-DataAndMethods](https://github.com/DSU-CSCI-121-F26/FollowAlong3-DataAndMethods)** | 10–14 | arrays, String methods, ArrayList, methods, `printf` |
| **4. [FollowAlong4-Objects](https://github.com/DSU-CSCI-121-F26/FollowAlong4-Objects)** | 15–20 | objects, constructors, `toString`, `static`, checkpoint: Receiver and Quarterback |
| **5. [FollowAlong5-Robots](https://github.com/DSU-CSCI-121-F26/FollowAlong5-Robots)** | 21–24 | composition, sensors, behaviors, inheritance, overriding, polymorphism |

---

## The parts in this repo

| Part | What you learn | Challenge |
|---|---|---|
| **21** | **Composition:** a `Rover` that **has a** `Chassis` and **has an** `Ultrasonic` sensor. Building parts in the constructor. Why a missing `new` means `null` | a robot class: its constructor, `stepsToWall` and `park` |
| **22** | **Behaviors:** methods that read a sensor, decide and move. The line sensor's numbers. A behavior made of behaviors. A field that remembers | `driveUntilBlocked`, `sidestepRight`, `reachGoal` and `followLine`, in new worlds |
| **23** | **Inheritance:** your own robot class that **is an** `MBot2` (`extends`, `super()`). Inherited methods. Choosing extra sensors as fields: the `Scanner` | an explorer robot: its constructor, `openestIndex`, `countOpen`, `turnToward` and `explore`, in two mazes |
| **24** | **Overriding and polymorphism:** replacing a method (`@Override`, `super.straight`). One method that works for any robot. `toString` | a safe `straight`, a lap of the yard, and `stepsToWall` for any robot |

**There is no video for these parts.** Each part's lesson is its `GUIDE.md`, written out step
by step.

### What we do in class

The dates for these parts are announced in class. As always: **we start each part together in
class, and it is due before the next lecture starts.**

**Ahead of the plan? Keep going.** You can always start the next part early.

---

## The robot library

Every robot program in this repo starts like this:

```java
import mbot.*;

@MBotLab(world = "wall-ahead")
public class FollowAlong {
    public static void main(String[] args) {
        ...
    }
}
```

- **`import mbot.*;`** lets you use the robot classes.
- **`@MBotLab(world = "...")`** picks the room the robot starts in. Leave it alone unless the
  instructions say to change it.

| Class | What it is |
|---|---|
| `Chassis` | A robot with **no sensors**: wheels, motors, and the CyberPi with its five lights. `straight(cm)`, `turnLeft(degrees)`, `turnRight(degrees)`, `turnLedOn(...)`, and more |
| `Ultrasonic` | A distance sensor you plug into a chassis: `new Ultrasonic(base)`. `distance()` gives back how far away the nearest thing in front is, in cm (300 if nothing is in range) |
| `LineSensor` | A tape sensor you plug into a chassis: `new LineSensor(base)`. `status()` gives back 0–15; `seesTape()` gives back `true` or `false` |
| `MBot2` | The whole robot, already put together with both sensors: `readUltrasonic()`, `readLineStatus()`, and an empty `run()`. You used it before Part 21. From Part 23 you write classes that **extend** it |
| `Scanner` | An extra sensor (Part 23): an ultrasonic on a small motor. `new Scanner(robot)`. `scan()` gives back 7 distances, far left to far right; `look(angle)` gives back one. **Simulator only** |
| `ColorSensor` | An extra sensor (Part 23): `new ColorSensor(robot)`. `color()` gives back the floor's color: `"white"`, `"black"`, `"red"`, `"green"`, `"blue"` or `"yellow"`. **Simulator only** |

**In the 3D view**, your robot only shows the sensors you plugged in. When your robot is a
class you wrote, the **Your robot** box draws it, and the classes it extends.

**Simulator only** means the real mBot2 has the parts, but its software cannot use them yet.

### The worlds

| World | What is in it |
|---|---|
| `open-room` | An empty room |
| `wall-ahead` | A wall about 1.5 m in front of the robot |
| `parking` | A parking box made of tape, marked **P**, with a wall behind it |
| `obstacles` | Boxes and cans between the robot and a green **Goal** |
| `line-track` | A loop of tape on the floor |
| `junctions` | A corridor that turns right, then left, to a green **Goal** |
| `color-zones` | Colored stripes across the floor, then a red stop pad |
| `worlds/...json` | The challenge worlds, in the `worlds/` folder of this repo. The tests use these |

---

## How everything works

### How to run your code

1. Open the file.
2. Click the **green ▶** next to `public static void main`.
3. Choose **Run**.

Your printed output appears in the **Run** window at the bottom of IntelliJ, as always. If it
is a robot program, the 3D view in your browser replays the run.

### The 3D view

| Part of the page | What it shows |
|---|---|
| The room | Your robot, the walls, boxes, cans and tape, and a blue line for everywhere it has driven |
| **What's happening** | The line of your code that is running now, in plain English, and which line called it |
| **Your code** | Your file. The yellow line is the one running. `×27` means that line has run 27 times |
| **Printed output** | What you printed with `System.out.println` |
| The buttons at the bottom | ▶ plays. ◀ and ▶\| go one step at a time. Drag the slider to any step. Change the speed |

**Overview**, **Follow** and **Top** change the camera. Tick **Play new runs** (top right) so
each new run plays by itself.

### Nothing runs at all? Look for a mistake in another file

Before IntelliJ runs **any** file, it checks **every** file in the project. If **one** file
anywhere has a red error, **nothing runs**. Look at the **Build** window at the bottom: it
lists the broken file and line. Fix it, or put `/*` before it and `*/` after it for now.

### How to run a test

1. Open `Challenge.java`.
2. Press `⌘ ⇧ T` (Mac) or `Ctrl ⇧ T` (Windows) to jump to `ChallengeTest.java`.
3. Click the **green ▶** next to `class ChallengeTest`. Choose **Run**.
4. **Green ✔** means that one is right. **Red ✗** means it is wrong: click it and compare
   **expected** with **but was**.

The tests drive your robot **without** the 3D view, so no browser tab opens. **Never change a
test file**, and never change the files in `worlds/`.

---

## How to hand in your work

You do not send me anything. A program I run reads the commit messages in your fork.

For each part you make **four commits**, one for each graded section, using the messages in
that part's instructions. Then you **push**:

```bash
git push
```

You always work on **`main`**. Never make a new branch.

**Before you push, check your commits.** Type `git log --oneline` and make sure all four of
the part's commits are there. (Press `q` to get out of the list.)

**Typed a commit message wrong?** Make one more commit with the right message, with
`--allow-empty`:

```bash
git commit --allow-empty -m "part21 comments: explained every line of the follow-along code"
```

> A **green ✔** next to your commit on GitHub only means your code has no typing errors. It
> does **not** mean your robot does the right thing. You check that yourself, in the 3D view
> and with the tests.

---

## Getting updates

If I fix a mistake or add something, I will tell you in class. To get the update, type this in
the terminal:

```bash
git pull --no-rebase --no-edit upstream main
```

**See `fatal: 'upstream' does not appear to be a git repository`?** You skipped one line in
[step 2 of Set up this repo](#2-download-it-to-your-laptop). Type this, then try again:

```bash
git remote add upstream https://github.com/DSU-CSCI-121-F26/FollowAlong5-Robots.git
```

---

## Getting help

- **Tutoring.** Bring your laptop and the part you are stuck on.
- **Office hours.** Bring the error message and tell me what you already tried.
- **Classmates.** Talk about it as much as you want. But **type your own code.**
- **Think Java.** A free online book: [books.trinket.io/thinkjava2](https://books.trinket.io/thinkjava2/).
