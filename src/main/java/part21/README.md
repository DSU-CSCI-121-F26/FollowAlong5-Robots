# Part 21 — Composition: your robot has parts

**We start this together in class · due before the next lecture · No AI**

Until now, your robot came already put together: `new MBot2()` gave you wheels **and**
sensors in one object. A real mBot2 comes in a box, in parts. Today you build your own robot
class out of parts, and you decide which sensors it gets.

**What you learn:**

- **composition**: building an object out of other objects. A `Rover` is made of a
  `Chassis` and an `Ultrasonic`
- **has-a**: how to say composition out loud. *A Rover **has a** Chassis. A Rover **has an**
  Ultrasonic.*
- **an object as a field**: a field (a variable that belongs to the object) whose type is a
  class, like `private Chassis base;`
- **making the parts in the constructor**: the constructor builds each part with `new`, in
  the right order
- **sensor**: a part that tells the robot about the world. The **ultrasonic** sensor measures
  how far away things are. The **line sensor** sees tape on the floor
- **`null`**: what a field holds if you never give it an object. Using it crashes the program

> **Before you start:** open any `.java` file. Click inside it. Type `Sys` and wait two
> seconds. If a list pops up, **stop.** Autocomplete is back on. Do
> [Part 00, step 4](https://github.com/DSU-CSCI-121-F26/FollowAlong1-Basics/blob/main/src/main/java/part00/README.md#step-4--turn-off-the-autocomplete-and-the-ai) again
> before you type anything else.

---

## How this part works

**In class, together:** we start Section A (follow along), and we do the **in-class
exercise** together.

**On your own:** you finish whatever we did not get to, then do Sections B, C and D.

**Every program in this part drives a robot.** When you press the green ▶, a tab opens in
your browser and your robot drives in 3D. The first run takes a few seconds. After that, the
tab replays each new run by itself. (If no tab opens, go to **http://localhost:8121**.)

## How this part is graded

This part has **four graded sections**. Each one is worth **25 points**.

| Section | File | Points | Your commit message |
|---|---|---|---|
| **A · Follow along** | `Rover.java`, `FollowAlong.java` | 25 | `part21 follow-along: a Rover that has a chassis and an ultrasonic` |
| **B · Comments** | the same two files | 25 | `part21 comments: explained every line of the follow-along code` |
| **C · Stretch** | `Stretch.java` (and `Rover.java`) | 25 | `part21 stretch: prediction, line sensor, driveToTape, parked in P` |
| **D · Challenge** | `Challenge.java` | 25 | `part21 challenge: constructor, stepsToWall and park` |

When you finish a section, you **commit** it with the message in the table. That commit is
how I know you finished it. **You do not send me anything on Canvas.** A program I run reads
your commit messages, so **type each one exactly as shown.**

> **No commit, no credit.** If a section has no commit, it gets **0**, even if the code is
> on your laptop. Everything must be **pushed before the next lecture starts.**

The warm-up and the in-class exercise are practice. They are not graded, but do them. They
make the rest easier.

---

## Warm-up — 5 minutes (practice, not graded)

1. In your **FollowAlong4-Objects** repo, open `part20/Stretch.java`. Look at your
   **Stretch B1** (integer division, three ways) for **30 seconds**.
2. Close it.
3. Open `part21/Warmup.java`. **Without looking back**, type it again and make it print the
   same thing.

You have to type `public static void main(String[] args)` yourself. You will probably make
a mistake. **That is fine. That is what the warm-up is for.**

4. Run it with the **green ▶** next to `main`. This one has no robot, so no browser tab opens.
5. If it does not match, try to fix it yourself first. Look back only if you are really
   stuck.

---

## Section A — Follow along (25 points)

*We start this together in class. Finish it on your own if we run out of time.*

**There is no video for this part.** The lesson is written out in
**[GUIDE.md](GUIDE.md)**, in this folder. Read it one step at a time and type every line
yourself. Do not copy and paste.

You type into two files:

- **`Rover.java`**: your own robot class. It **has a** `Chassis` and **has an** `Ultrasonic`.
- **`FollowAlong.java`**: a `main` that makes a `Rover` and drives it to a wall.

**Leave the `package part21;`, `import mbot.*;`, `@MBotLab(...)` and `public class ...`
lines alone.** Type everything else.

### Check your work as you go

Run **`FollowAlong.java`** with the green ▶ next to `main`.

**Check 1 (guide step 5): the robot exists.** Your `Rover` has its fields and its
constructor. `main` makes one and prints it. Your letters after `@` will be different:

```
part21.Rover@1b6d3586
```

In the 3D view: the robot sits still. It **has its two blue "eyes"** on the front. In the box
on the top right, **Line sensor** says it is not plugged in. That is right: your rover only
has the parts you gave it.

**Check 2 (guide step 6): `toString`.**

```
Scout the rover is ready
```

**Check 3 (guide step 8): drive to the wall.**

```
Scout the rover is ready
Scout the rover stopped. Something is less than 20 cm ahead.
```

In the 3D view: the robot drives forward in small jumps and stops in front of the wall.
**Ultrasonic** in the top-right box says **18.0 cm**. In the **Your code** panel,
`base.straight(5);` shows **×27** and the `eyes.distance()` line shows **×28**. (Why one
more? The last time it looks, the wall is close, so the loop stops without moving.)

### Stuck? Check these first

| What you see | What is usually wrong |
|---|---|
| `cannot find symbol` and `class Chassis` (or `Ultrasonic`) | The `import mbot.*;` line is missing at the top of the file. Put it back |
| `package mbot does not exist` | IntelliJ has not loaded the robot library yet. Right-click `pom.xml` → **Maven** → **Reload project** |
| It crashes: `NullPointerException ... because "this.eyes" is null` | The constructor never made the sensor. Check for `eyes = new Ultrasonic(base);` |
| It crashes: `An Ultrasonic needs a robot to plug into, but it was given null` | You made the sensor **before** the chassis. Make `base` first |
| `constructor Rover in class Rover cannot be applied to given types;` | You wrote `new Rover()` with nothing inside. The constructor needs a name: `new Rover("Scout")` |
| The robot drives into the wall | The loop checks the wrong thing. It should keep going **while nothing** is ahead: `while (!somethingAhead(20))` |
| No browser tab opened | Go to **http://localhost:8121** yourself |
| You fixed your code, but the 3D view did not change | You ran a **different file**. Use the ▶ **next to `main`** in `FollowAlong.java` |

### ✅ Commit Section A

When your output and the 3D view match Check 3, type this in the terminal:

```bash
git add -A
git commit -m "part21 follow-along: a Rover that has a chassis and an ultrasonic"
```

---

## In-class exercise — a sentry (together, not graded)

We do this one together in class, in `InClass.java`. Everyone types their own copy. It runs
in the **obstacle course** world.

### Step 1 — A sentry that looks around

A **sentry** is a guard. Ours stands still, looks in one direction, and turns its light red
if something is close.

At the bottom of `InClass.java`, finish the class `Sentry` together:

- **Two fields:** a `Chassis` called `base` and an `Ultrasonic` called `eyes`.
- **A constructor** `Sentry()` that makes the chassis, then plugs the sensor into it.
- **`void check()`**: read the distance. If it is **less than 60**, turn light 1 red
  (`255, 0, 0`) and print `Something <distance> cm away!`. Otherwise turn light 1 green
  (`0, 255, 0`) and print `All clear`.
- **`void turn()`**: turn right 90.

Then, in `main` under `STEP 1`, make one `Sentry`. Use a `for` loop that runs **4 times**:
`check()`, then `turn()`.

Your output:

```
Something 58.0 cm away!
All clear
Something 18.0 cm away!
All clear
```

Things to figure out together:

- The sentry faces the box (58 cm), then the right side, then the wall behind it (18 cm),
  then the left side. Why are the two sides "clear"? (Look at the blue cone in the 3D view.
  The sensor sees about 97 cm before the cone touches a wall.)
- Who owns the `Chassis`: `main`, or the `Sentry`? (The sentry. `main` never sees it.)

### Step 2 — Fix the bugs

Under `STEP 2` there are three lines, inside a `/* ... */` comment so they don't run. Each
one makes a different sentry: `SentryA`, `SentryB` and `SentryC`, at the bottom of the file.
**Each of those classes has one mistake in its constructor.** One at a time:

1. Move **one** line up, above the `/*` line. Now Java runs it.
2. Run it. Read the **crash** in IntelliJ's Run window, **and** what the 3D view says under
   **What's happening**.
3. Fix **that class's constructor**. Run the program again.
4. Do the next line.

| Line | What happens | Where the mistake is |
|---|---|---|
| `new SentryA().check();` | It **crashes**: `NullPointerException: Cannot invoke "mbot.Ultrasonic.distance()" because "this.eyes" is null`. The 3D view says *"eyes" is null: it was declared, but never given an object.* | The constructor never makes the sensor |
| `new SentryB().check();` | It **crashes**: `IllegalArgumentException: An Ultrasonic needs a robot to plug into, but it was given null.` | The two lines in the constructor are in the wrong order |
| `new SentryC().check();` | It **crashes** with the same `"this.eyes" is null` as SentryA. But the constructor **does** say `new Ultrasonic(base)`! | Look closely at the start of that line. `Ultrasonic eyes = ...` makes a **new local variable** that happens to have the same name. The **field** `eyes` is still `null` |

When all three are fixed, the end of your output is:

```
A sees 58.0 cm
B sees 58.0 cm
C sees 58.0 cm
```

**None of these are red errors.** Java is happy with the code. The program crashes when it
runs. These are the three most common composition mistakes, and now you have seen all three.

You will also see the note *"A second robot was made."* That is fine. Each sentry makes a
`Chassis`, but there is only one robot in the simulator, so they all share it.

### ✅ Commit the in-class exercise

```bash
git add -A
git commit -m "part21 in-class: a sentry with an ultrasonic"
```

---

## Section B — Comment every line (25 points)

*On your own.*

Now explain your own code. In **both files** (`Rover.java`, `FollowAlong.java`), put a `//`
comment **above every line of code**, saying **in your own words** what that line does.

**Which lines need a comment?**

- **Yes:** every line that does something. That includes `public class ...`, every field,
  the constructor's first line, every `this.` line, every method's first line, and every
  line in `main`.
- **No:** lines that are only `}`, and empty lines. You can skip the `package` and `import`
  lines.

**What makes a good comment?** Say what the line **does** and **why**. Do not just repeat
the code in English.

| ❌ Not enough | ✅ Good |
|---|---|
| `// chassis` | `// the rover HAS a chassis: its wheels, motors and CyberPi. Empty (null) until the constructor makes one` |
| `// new ultrasonic` | `// plugs a distance sensor into THIS rover's chassis; it has to come after the chassis exists` |
| `// while loop` | `// keep going as long as nothing is closer than 20 cm` |
| `// return` | `// asks the sensor how far away things are, and answers true if that is less than cm` |

Here is what a commented line looks like in your file:

```java
        // makes the chassis first, because the sensor needs something to plug into
        this.base = new Chassis();
```

**Rules:**

1. **Your own words.** Do not copy sentences from the guide. I want to know what **you**
   think the line does.
2. **Not sure what a line does?** Write your best guess, and add `(not sure)` at the end.
   An honest guess gets credit. A skipped line does not.
3. **Run `FollowAlong.java` again when you're done.** Comments must not change what
   happens. If it changed, a comment is missing its `//`.

### ✅ Commit Section B

```bash
git add -A
git commit -m "part21 comments: explained every line of the follow-along code"
```

---

## Section C — Stretch (25 points)

*On your own.*

Do these in `Stretch.java`. It runs in the **parking** world: a box made of tape, with a wall
behind it. There is no `main` in that file yet. **Type it yourself.** Stretch B also adds to
your `Rover` class.

### Stretch A — guess first

1. Type this code inside `main`. **Do not run it yet.**

```java
Rover ada = new Rover("Ada");
System.out.println(ada);
System.out.println(ada.somethingAhead(500));
System.out.println(ada.somethingAhead(5));
```

2. **Above that code**, write what you think it will print, inside a comment:

```java
/* MY GUESS:
   ...
*/
```

3. Now run it.
4. Were you wrong about any line? **Keep your wrong guess.** Under it, add a comment that
   says why you were wrong.

<details>
<summary>Click to see the answer. Only after you have run it!</summary>

```
Ada the rover
true
false
```

- Printing a `Rover` calls your `toString`.
- The wall in this world is **223 cm** in front of the sensor. 223 is less than 500, so
  something **is** ahead within 500 cm: `true`.
- 223 is **not** less than 5: `false`.

</details>

### Stretch B — give your rover a second sensor

**B1.** In **`Rover.java`**:

1. Add a third sensor field: `private LineSensor floor;`
2. In the constructor, **after** the chassis is made, plug it in:
   `this.floor = new LineSensor(base);`

Run `Stretch.java` again. Same output as Stretch A. **In the 3D view**, the robot now has a
small black board under its front, and **Line sensor** in the top-right box is no longer
greyed out.

**B2.** In **`Rover.java`**, add two methods:

- **`void driveToTape()`**: drive forward **2 cm** at a time, **while** the line sensor does
  **not** see tape. (`floor.seesTape()` gives back `true` or `false`.)
- **`void forward(double cm)`**: drive straight `cm`. One line: it hands the job to `base`.

**B3.** In `Stretch.java`, under your Stretch A code, park Ada in the box:

```java
ada.driveToTape();
System.out.println("Found the tape");
ada.forward(30);
System.out.println(ada + " is parked");
```

Output:

```
Ada the rover
true
false
Found the tape
Ada the rover is parked
```

In the 3D view: Ada creeps forward, stops with her front on the edge of the tape, then rolls
into the middle of the box marked **P**. `base.straight(2);` shows **×62**.

**Hint for B2:** `driveToTape` looks a lot like `driveUntilBlocked`. Only the sensor and the
question change.

### ✅ Commit Section C

```bash
git add -A
git commit -m "part21 stretch: prediction, line sensor, driveToTape, parked in P"
```

---

## Section D — Challenge (25 points)

*On your own.*

Open `Challenge.java`. This time the challenge **is a robot class**. It has three fields
already: a `Chassis`, an `Ultrasonic` and a `LineSensor`. You write its constructor and two
methods. A **test** checks all three.

| What | What it must do |
|---|---|
| `Challenge()` (the constructor) | Make the chassis **first**. Then plug **both** sensors into it |
| `int stepsToWall()` | Drive forward **5 cm** at a time, until the ultrasonic reads **less than 15**. Give back **how many** 5 cm moves it made |
| `void park()` | Drive forward **2 cm** at a time until the line sensor sees tape. Then drive **30 cm** more |

**The test uses new worlds**, in the `worlds/` folder of this repo:
`part21-challenge.json` (a taped box, then a wall far behind it) and `part21-boxes.json` (a box
close by). The distances are different from the worlds in class, so your code has to **use
the sensors**. A fixed distance will not pass.

**How to work on it:**

1. Change only the lines marked `// YOUR CODE`.
2. Press `⌘ ⇧ T` (Mac) or `Ctrl ⇧ T` (Windows) to jump to `ChallengeTest.java`.
3. Click the **green ▶** next to `class ChallengeTest` → **Run**.
4. **Red ✗:** click it. Compare **expected** (the right answer) with **but was** (yours).
   **Green ✔:** that one is right.
5. Fix, run again, until **all** tests are green.

The tests run the robot **without** the 3D view, so no browser tab opens. That is normal.

**Never change `ChallengeTest.java`.** Only change `Challenge.java`.

**Want to watch your challenge robot drive?** Make a new class in the `part21` folder, for
example `Watch.java`:

```java
package part21;

import mbot.*;

@MBotLab(world = "worlds/part21-challenge.json")
public class Watch {
    public static void main(String[] args) {
        Challenge robot = new Challenge();
        System.out.println(robot.stepsToWall());
    }
}
```

**Hints:**

- The constructor is three lines. **Order matters**: look back at SentryB.
- `stepsToWall`: start a counter at 0. Use a `while` loop. Add 1 each time you drive.
  Return the counter after the loop.
- `park`: one `while` loop, then one more `straight`.

### ✅ Commit Section D

When every test is green:

```bash
git add -A
git commit -m "part21 challenge: constructor, stepsToWall and park"
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
a1b2c3d part21 challenge: constructor, stepsToWall and park
e4f5a6b part21 stretch: prediction, line sensor, driveToTape, parked in P
c7d8e9f part21 comments: explained every line of the follow-along code
9d8c7b6 part21 in-class: a sentry with an ultrasonic
0a1b2c3 part21 follow-along: a Rover that has a chassis and an ultrasonic
```

*(The letters and numbers at the start will be different for you. That is normal.)*
Press `q` to get out of the list.

**Missing one?** That section gets **0** right now. Go back, finish it, and make its
commit. You have until the next lecture starts.

**Typed a commit message wrong?** Make one more commit with the right message. Copy the
right message from the table, and add `--allow-empty` so Git lets you commit without
changing a file:

```bash
git commit --allow-empty -m "part21 comments: explained every line of the follow-along code"
```

### 3. Push

```bash
git add -A
git commit -m "part21 log"
git push
```

### 4. Check GitHub

Go to your fork on GitHub (`github.com/your-username/FollowAlong5-Robots`). Click the
**commits** link (clock icon) above the file list. **All of this part's commits must be
there.** If they are on your laptop but not on GitHub, you did not push, and I cannot see
them.

> **No commit, no credit.** A section with no commit gets 0. A commit that is not pushed
> before the next lecture starts does not count.

**Next part's warm-up:** from memory, build a robot from parts and print what its ultrasonic
sensor reads.

**Finished early? Start Part 22.** You don't have to wait for class.

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

- **Think Java** (free online book): [Chapter 11 — Designing Classes](https://books.trinket.io/thinkjava2/chapter11.html)
  and [Chapter 13 — Objects of Arrays](https://books.trinket.io/thinkjava2/chapter13.html)
  (its `Deck` **has** `Card`s: that is composition too).
- **Tutoring:** bring your laptop and this page.
- **Office hours:** bring the error message and tell me what you already tried.
- **Classmates:** talk it over as much as you want. But **type your own code.**
