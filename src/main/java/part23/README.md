# Part 23 — Inheritance: build your own robot

**We start this together in class · due before the next lecture · No AI**

In part 21 your `Rover` **had** a chassis inside it. Today you do it the other way round:
your robot **is** an MBot2. You write your own robot class on top of `MBot2`. It can already
do everything an MBot2 can, and you add what your robot needs: extra sensors, and new skills.

**What you learn:**

- **inheritance**: making a new class from an existing one. The new class gets all the
  methods of the old one for free
- **`extends`**: the word that says it. `public class Scout extends MBot2` means *a Scout is
  an MBot2, plus more*
- **is-a**: how to say inheritance out loud. *A Scout **is an** MBot2. An MBot2 **is a**
  Chassis.* Compare part 21: *a Rover **has a** Chassis* (that was composition)
- **superclass and subclass**: `MBot2` is the **superclass** (the one you build on).
  `Scout` is the **subclass** (the new one)
- **`super()`**: the first line of the subclass's constructor. It builds the MBot2 part of
  the object first
- **inherited methods**: `straight`, `turnLeft` and `readUltrasonic` are not written in
  `Scout`, but a Scout can call them, because it **is an** MBot2
- **choosing fields**: each robot class decides which extra sensors it needs, and keeps them
  as fields. The `Scanner` is a new sensor that turns and looks in 7 directions

> **Before you start:** open any `.java` file. Click inside it. Type `Sys` and wait two
> seconds. If a list pops up, **stop.** Autocomplete is back on. Do
> [Part 00, step 4](https://github.com/DSU-CSCI-121-F26/FollowAlong1-Basics/blob/main/src/main/java/part00/README.md#step-4--turn-off-the-autocomplete-and-the-ai) again
> before you type anything else.

---

## How this part works

**In class, together:** we start Section A (follow along), and we do the **in-class
exercise** together.

**On your own:** you finish whatever we did not get to, then do Sections B, C and D.

**Every program in this part drives a robot.** When you press the green ▶, your robot drives
in the 3D view in your browser. (If no tab opens, go to **http://localhost:8121**.) Some runs
are long: change the speed at the bottom of the 3D view to **4×** or **10×**.

**New in the 3D view: the "Your robot" box.** When your robot is a class you wrote, the 3D
view draws it: your class at the bottom, with its fields and methods, and the classes it
extends above it. It is a picture of your inheritance.

**A note on the new sensors.** The `Scanner` and the `ColorSensor` work in the simulator
only. The real mBot2 has the parts, but its software cannot use them yet.

## How this part is graded

This part has **four graded sections**. Each one is worth **25 points**.

| Section | File | Points | Your commit message |
|---|---|---|---|
| **A · Follow along** | `Scout.java`, `Explore.java` | 25 | `part23 follow-along: a Scout that extends MBot2 and scans for the open way` |
| **B · Comments** | the same two files | 25 | `part23 comments: explained every line of the follow-along code` |
| **C · Stretch** | `Stretch.java` | 25 | `part23 stretch: where each method comes from, a Painter that extends MBot2` |
| **D · Challenge** | `Challenge.java` | 25 | `part23 challenge: constructor, openestIndex, countOpen, turnToward and explore` |

When you finish a section, you **commit** it with the message in the table. That commit is
how I know you finished it. **You do not send me anything on Canvas.** A program I run reads
your commit messages, so **type each one exactly as shown.**

> **No commit, no credit.** If a section has no commit, it gets **0**, even if the code is
> on your laptop. Everything must be **pushed before the next lecture starts.**

The warm-up and the in-class exercise are practice. They are not graded, but do them. They
make the rest easier.

---

## Warm-up — 5 minutes (practice, not graded)

Open `part23/Warmup.java`. **Without looking at part 21 or 22**, write a `main` that:

1. makes a `Chassis`,
2. plugs an `Ultrasonic` into it,
3. drives forward 5 cm at a time **while** the ultrasonic reads more than 20,
4. prints how many moves it made.

Run it. It should print:

```
27
```

and the 3D view shows the robot stop in front of the wall. You have to type
`public static void main(String[] args)` yourself. Look back only if you are really stuck.

---

## Section A — Follow along (25 points)

*We start this together in class. Finish it on your own if we run out of time.*

**There is no video for this part.** The lesson is written out in
**[GUIDE.md](GUIDE.md)**, in this folder. Read it one step at a time and type every line
yourself. Do not copy and paste.

You type into two files:

- **`Scout.java`**: your own robot class. It **extends** `MBot2` and carries a `Scanner`.
- **`Explore.java`**: a `main` that sends a Scout through the **junctions** world: a corridor
  that turns right, then left, to a green **Goal**.

**Leave the `package part23;`, `import mbot.*;` and `@MBotLab(...)` lines alone.** In
`Scout.java`, step 1 tells you to change the class line. Type everything else.

### Check your work as you go

**Check 1 (guide step 3): run `Explore.java`.**

```
Leg 1: 26 moves
```

In the 3D view: the robot drives up the corridor and stops in front of the wall. The **Your
robot** box shows three classes: `Chassis`, then `MBot2`, then your `Scout` at the bottom,
with its `Scanner scanner ✓` field.

**Check 2 (guide step 5): run `Explore.java` again.**

```
Leg 1: 26 moves
Most open way: index 6, angle -90
```

In the 3D view: at the wall, the scanner on top turns and sends out 7 orange lines. The one to
the right is by far the longest: **260 cm**, down the corridor. In the box at the top right,
**Scanner** shows all 7 distances, far left first: `40 43 29 27 29 44 260`.

**Check 3 (guide step 6): run `Explore.java` one more time.**

```
Leg 1: 26 moves
Leg 2: 46 moves
Leg 3: 22 moves
Pathfinder the scout reached the goal
```

In the 3D view: up, scan, turn right, along, scan, turn left, up into the green **Goal**.
Nothing gets bumped.

Click any step inside `driveUntilBlocked`. Under **What's happening** you see
*"readUltrasonic() is inherited: Scout gets it from MBot2"* or *"straight() is inherited:
Scout gets it from Chassis"*. Your class never wrote those methods. It inherited them.

### Stuck? Check these first

| What you see | What is usually wrong |
|---|---|
| `cannot find symbol` and `method straight(int)` | The class line does not say `extends MBot2` (guide step 1) |
| `incompatible types: Scout cannot be converted to Chassis` | Same thing: without `extends MBot2`, a Scout is not a Chassis, so a sensor cannot plug into it |
| `call to super must be first statement in constructor` | `super();` has to be the **first** line of the constructor |
| It crashes: `... because "this.scanner" is null` | The constructor never made the scanner, or made a **local** one: `Scanner scanner = new Scanner(this);` (see the in-class exercise) |
| `missing return statement` | `openestIndex` must end with `return best;`, **after** the loop |
| The robot turns the wrong way at a junction | In `turnTowardOpenSide`, a **positive** angle means **left** |

### ✅ Commit Section A

When all three checks match, type this in the terminal:

```bash
git add -A
git commit -m "part23 follow-along: a Scout that extends MBot2 and scans for the open way"
```

---

## In-class exercise — a lookout (together, not graded)

We do this one together in class, in `InClass.java`. Everyone types their own copy. It runs
in the **junctions** world.

### Step 1 — A lookout that reports what it sees

A **lookout** stands still and reports what it sees in every direction.

At the bottom of `InClass.java`, finish the class `Lookout` together:

- Change the class line to `class Lookout extends MBot2 {`.
- **One field:** a `Scanner` called `scanner`.
- **A constructor** `Lookout()` that calls `super();` first, then mounts the scanner:
  `scanner = new Scanner(this);`
- **`void report()`**: scan once into a `double[]`. Then use a `for` loop over the array.
  For each index `i`, print `Scanner.ANGLES[i]`, then `: `, then the distance, then ` cm`.

Then, in `main` under `STEP 1`, make one `Lookout` and call `report()`.

Your output:

```
90: 40.0 cm
60: 43.1 cm
30: 65.0 cm
0: 157.0 cm
-30: 65.0 cm
-60: 43.1 cm
-90: 40.0 cm
```

Things to figure out together:

- Why are the left and right numbers the same? (The robot starts in the middle of the
  corridor: 40 cm from the wall on each side.)
- `Scanner.ANGLES[i]` and `d[i]` use the **same** index. Why does that line them up?
- `new Scanner(this)`: what is `this`, and why is Java happy to plug a scanner into it?
  (`this` is the Lookout. A Lookout **is an** MBot2, which **is a** Chassis.)

### Step 2 — Fix the bugs

Under `STEP 2` there are lines inside a `/* ... */` comment so they don't run. Each one uses
a robot class with **one mistake**: `BotA`, `BotB` and `BotC`, at the bottom of the file.

**First, put `//` in front of your two STEP 1 lines in `main`.** The simulator has only one
robot, and the 3D view describes the **first** robot class you make. With the Lookout out of
the way, it describes the buggy robot instead. Then, one at a time:

1. Move **one** line (for BotC, the two lines) up, above the `/*` line. Now Java runs it.
2. Run it. Read the **crash**, the **red error**, or what the 3D view says under **What's
   happening**.
3. Fix **that class**. Run the program again.
4. Do the next one.

**BotB is special:** its whole class is inside its own `/* */`, because its mistake stops
**every** file from running. Delete the `/*` and `*/` lines around the class too.

| Line | What happens | Where the mistake is |
|---|---|---|
| `new BotA().report();` | It **crashes**: `NullPointerException: Cannot invoke "mbot.Scanner.scan()" because "this.scanner" is null`. The 3D view says *"BotA declares Scanner scanner, but it is still null"*, and the **Your robot** box shows `Scanner scanner = null` in red | `Scanner scanner = new Scanner(this);` makes a **local variable** with the same name. The **field** is still `null`. Take `Scanner` off the front |
| `new BotB().report();` | **Red errors**: `incompatible types: BotB cannot be converted to Chassis`, and `cannot find symbol: method straight(int)` | The class line has no `extends MBot2`. Without it, BotB is not a robot: it has no `straight`, and a scanner cannot plug into it |
| `MBot2 c = new BotC();` `c.run();` | No error, no crash, **nothing happens**. The 3D view says *"run() was called, but BotC does not override it, so MBot2's empty run() ran"* | `Run` has a capital R. Java names care about capitals, so `Run()` is a **new** method, not MBot2's `run()`. Rename it `run` |

Then put **`@Override`** on the line above BotC's `public void run()`. `@Override` tells Java:
*this method is meant to replace one from the superclass*. Now change `run` back to `Run` for
a moment. You get a red error: `method does not override or implement a method from a
supertype`. **`@Override` turns the silent bug into a red error.** Change it back to `run`.
(Part 24 is all about this.)

When all three are fixed, take the `//` off your STEP 1 lines again. The end of your output
is:

```
A sees 157.0 cm ahead
B sees 152.0 cm ahead
C ran its own run()
```

You will also see the note *"A second robot was made."* That is fine. There is only one robot
in the simulator, so all the robots in this file share it.

### ✅ Commit the in-class exercise

```bash
git add -A
git commit -m "part23 in-class: a lookout that extends MBot2"
```

---

## Section B — Comment every line (25 points)

*On your own.*

Now explain your own code. In **both files** (`Scout.java`, `Explore.java`), put a `//`
comment **above every line of code**, saying **in your own words** what that line does.

**Which lines need a comment?**

- **Yes:** every line that does something. That includes `public class ...`, every field,
  the constructor's first line, `super();`, every `this.` line, every method's first line,
  every `if`, `else`, `while` and `for`, and every line in `main`.
- **No:** lines that are only `}`, and empty lines. You can skip the `package` and `import`
  lines.

**What makes a good comment?** Say what the line **does** and **why**. Do not just repeat
the code in English.

| ❌ Not enough | ✅ Good |
|---|---|
| `// extends MBot2` | `// a Scout is an MBot2, so it gets driving and the kit's sensors for free` |
| `// super` | `// build the MBot2 part of this object first, before adding my own parts` |
| `// for loop` | `// look at every distance after the first, to find the biggest` |
| `// call straight` | `// drive 5 cm: straight is inherited from Chassis, I never wrote it` |

**Rules:**

1. **Your own words.** Do not copy sentences from the guide.
2. **Not sure what a line does?** Write your best guess, and add `(not sure)` at the end.
   An honest guess gets credit. A skipped line does not.
3. **Run `Explore.java` again when you're done.** Comments must not change what happens.

### ✅ Commit Section B

```bash
git add -A
git commit -m "part23 comments: explained every line of the follow-along code"
```

---

## Section C — Stretch (25 points)

*On your own.*

Do these in `Stretch.java`. It runs in the **color zones** world: colored stripes across the
floor, then a red stop pad. There is no `main` in that file yet. **Type it yourself.**

### Stretch A — guess first: where does each method come from?

Here is a robot class you will type in Stretch B. **Do not type it yet.** Just read it.

```java
class Painter extends MBot2 {
    private ColorSensor eye;

    Painter() {
        super();
        eye = new ColorSensor(this);
    }

    void showColor() {
        String c = eye.color();
        if (c.equals("red")) {
            turnLedOn(1, 255, 0, 0);
        } else if (c.equals("green")) {
            turnLedOn(1, 0, 255, 0);
        } else if (c.equals("blue")) {
            turnLedOn(1, 0, 0, 255);
        } else if (c.equals("yellow")) {
            turnLedOn(1, 255, 200, 0);
        } else {
            turnLedOff(1);
        }
    }

    void driveToRed() {
        String last = "";
        while (!eye.color().equals("red")) {
            String c = eye.color();
            if (!c.equals(last)) {
                System.out.println("Now on " + c);
                last = c;
            }
            showColor();
            straight(5);
        }
        showColor();
        System.out.println("Stopped on red");
    }
}
```

In `Stretch.java`, at the top of the class, write a comment that answers this for each of
these five calls: **which class's code runs?** Choose from `Painter`, `MBot2`, `Chassis` and
`ColorSensor`.

1. `super()`
2. `eye.color()`
3. `turnLedOn(1, 255, 0, 0)`
4. `straight(5)`
5. `showColor()`

You check your guesses in Stretch B.

### Stretch B — a Painter that extends MBot2

**B1.** Type the `Painter` class from Stretch A **below** the `Stretch` class, in the same
file.

**B2.** Type `main` in the `Stretch` class:

```java
Painter p = new Painter();
System.out.println(p instanceof Painter);
System.out.println(p instanceof MBot2);
System.out.println(p instanceof Chassis);
p.driveToRed();
```

`instanceof` asks a yes-or-no question: *is this object one of these?* Your output:

```
true
true
true
Now on white
Now on green
Now on white
Now on blue
Now on white
Now on yellow
Now on white
Stopped on red
```

In the 3D view: light 1 changes color as the robot crosses each stripe, and the robot stops
with its color sensor on the red pad. The **Your robot** box shows `Painter` with
`ColorSensor eye ✓`.

A Painter is a Painter, **and** an MBot2, **and** a Chassis. That is three `true`s. One
object can be all three, because each class builds on the one above it.

**B3. Check your guesses from Stretch A.** Step through the run with ▶|. Under **What's
happening**, the 3D view says where each robot method comes from, for example
*"straight() is inherited: Painter gets it from Chassis"*. Fix any wrong guesses: **keep the
wrong guess**, and under it add a comment that says why you were wrong.

<details>
<summary>Click to see the answers. Only after you have checked in the 3D view!</summary>

1. `super()` runs **MBot2**'s constructor: it builds the MBot2 part of the Painter.
2. `eye.color()` runs **ColorSensor**'s code. It is **not** inherited. `eye` is a field: the
   Painter **has a** ColorSensor. That is composition, inside a class that uses inheritance.
3. `turnLedOn(...)` runs **Chassis**'s code: inherited, through MBot2.
4. `straight(5)` runs **Chassis**'s code: inherited, through MBot2.
5. `showColor()` runs **Painter**'s own code.

</details>

**B4.** In a comment under your code, answer: the Scout has a `Scanner`, the Painter has a
`ColorSensor`. Both extend MBot2. Why do they have **different** fields? (Think about the job
each robot does.)

### ✅ Commit Section C

```bash
git add -A
git commit -m "part23 stretch: where each method comes from, a Painter that extends MBot2"
```

---

## Section D — Challenge (25 points)

*On your own.*

Open `Challenge.java`. It is your own robot class again: `public class Challenge extends
MBot2`. It has a `Scanner` field. **`driveUntilBlocked` is done for you.** You write the
constructor and four methods. A **test** checks all of them.

| What | What it must do |
|---|---|
| `Challenge()` (the constructor) | `super();` is already there. Then **mount the scanner** |
| `int openestIndex()` | Scan **once**. Give back the **index** (0 to 6) of the **biggest** distance. If two are equal, give back the smaller index |
| `int countOpen(double cm)` | Scan **once**. Give back **how many** of the 7 distances are **more than** `cm` |
| `void turnToward(int index)` | Turn to face the direction `Scanner.ANGLES[index]`. A **positive** angle: turn **left** that much. **Negative**: turn **right** that much (without the minus). **0**: do not turn |
| `void explore()` | Up to **10** times: drive until blocked. Then, if `countOpen(60)` is **0**, you are at the dead end: **stop** (`return`). Otherwise, turn toward the most open direction |

**The test uses new worlds**, in the `worlds/` folder of this repo:

- `part23-maze.json`: a corridor that turns **right**, then **left**, then **left**, and ends
  in a dead end. The **Goal** is the dead end.
- `part23-maze-mirror.json`: the same maze, flipped. Its turns go the **other** way.

The robot does not know which maze it is in. Only the scanner can tell it which way to go.

**How to work on it:**

1. Change only the lines marked `// YOUR CODE`.
2. Press `⌘ ⇧ T` (Mac) or `Ctrl ⇧ T` (Windows) to jump to `ChallengeTest.java`.
3. Click the **green ▶** next to `class ChallengeTest` → **Run**.
4. **Red ✗:** click it. Compare **expected** with **but was**. **Green ✔:** that one is right.
5. Fix, run again, until **all** tests are green.

The tests run the robot **without** the 3D view. **Never change `ChallengeTest.java`.**

**Want to watch?** Make a class `Watch.java` in the `part23` folder:

```java
package part23;

import mbot.*;

@MBotLab(world = "worlds/part23-maze.json")
public class Watch {
    public static void main(String[] args) {
        new Challenge().explore();
    }
}
```

(For the other maze, use `"worlds/part23-maze-mirror.json"`.)

**Hints:**

- The constructor needs one line. Look at your `Scout`.
- `openestIndex` is the one from your `Scout`.
- `countOpen`: a counter, a loop over the scan, and an `if`.
- `turnToward`: get the angle out of `Scanner.ANGLES` first. Then an `if`, an `else if`, and
  nothing at all for 0.
- `explore`: a `for` loop that runs 10 times, and three lines inside it. The dead-end check
  uses `return`, which ends the method straight away.

### ✅ Commit Section D

When every test is green:

```bash
git add -A
git commit -m "part23 challenge: constructor, openestIndex, countOpen, turnToward and explore"
```

---

## Hand it in: push before the next lecture

### 1. Fill in your log

At the bottom of this page, fill in the three lines under **My log**.

### 2. Check your commits

In the terminal, type:

```bash
git log --oneline
```

Near the top of the list, you should see **your commits for this part**, newest first:

```
a1b2c3d part23 challenge: constructor, openestIndex, countOpen, turnToward and explore
e4f5a6b part23 stretch: where each method comes from, a Painter that extends MBot2
c7d8e9f part23 comments: explained every line of the follow-along code
9d8c7b6 part23 in-class: a lookout that extends MBot2
0a1b2c3 part23 follow-along: a Scout that extends MBot2 and scans for the open way
```

*(The letters and numbers at the start will be different for you. That is normal.)*
Press `q` to get out of the list.

**Missing one?** That section gets **0** right now. Go back, finish it, and make its
commit. You have until the next lecture starts.

**Typed a commit message wrong?** Make one more commit with the right message, with
`--allow-empty`:

```bash
git commit --allow-empty -m "part23 comments: explained every line of the follow-along code"
```

### 3. Push

```bash
git add -A
git commit -m "part23 log"
git push
```

### 4. Check GitHub

Go to your fork on GitHub (`github.com/your-username/FollowAlong5-Robots`). Click the
**commits** link (clock icon) above the file list. **All of this part's commits must be
there.**

> **No commit, no credit.** A section with no commit gets 0. A commit that is not pushed
> before the next lecture starts does not count.

**Coming up in part 24:** your robot keeps a method's **name**, but changes what it **does**.
That is called **overriding**, and `@Override` is how you say it.

---

## My log

Fill this in before you push. Type your answers after the colons.

```
Started:
Finished:
The line that took me the longest:
```

---

## Want more help?

- **Think Java** (free online book): [Chapter 14 — Extending Classes](https://books.trinket.io/thinkjava2/chapter14.html).
- **Tutoring:** bring your laptop and this page.
- **Office hours:** bring the error message and tell me what you already tried.
- **Classmates:** talk it over as much as you want. But **type your own code.**
