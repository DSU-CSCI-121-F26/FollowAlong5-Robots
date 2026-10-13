# Part 22 — Behaviors: teach your robot a skill

**We start this together in class · due before the next lecture · No AI**

In part 21 you built a robot out of parts. Now you teach it **skills**: drive until something
is in the way, go round a box, follow a line of tape. Each skill is a method that **reads a
sensor, decides, and moves**, over and over.

**What you learn:**

- **behavior**: a method that uses the sensors to decide what the robot does next. For
  example, `driveUntilBlocked` keeps reading the ultrasonic and only drives while the way is
  clear
- **a sensor loop**: a `while` or `for` loop that reads a sensor **every time round**, so the
  robot reacts to what it sees
- **a behavior made of behaviors**: one method that calls smaller ones, like `reachGoal`
  calling `driveUntilBlocked` and `goAroundLeft`
- **the line sensor's numbers**: four probes, and how one number from 0 to 15 says which
  ones see tape
- **a field that remembers**: a `boolean` field that keeps what the robot saw last time, so it
  can decide better next time

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

## How this part is graded

This part has **four graded sections**. Each one is worth **25 points**.

| Section | File | Points | Your commit message |
|---|---|---|---|
| **A · Follow along** | `Rover.java`, `ObstacleRun.java`, `LineRun.java` | 25 | `part22 follow-along: driveUntilBlocked, goAroundLeft, reachGoal and followLine` |
| **B · Comments** | the same three files | 25 | `part22 comments: explained every line of the follow-along code` |
| **C · Stretch** | `Stretch.java` | 25 | `part22 stretch: prediction, a Lapper that has a Rover, two laps` |
| **D · Challenge** | `Challenge.java` | 25 | `part22 challenge: driveUntilBlocked, sidestepRight, reachGoal and followLine` |

When you finish a section, you **commit** it with the message in the table. That commit is
how I know you finished it. **You do not send me anything on Canvas.** A program I run reads
your commit messages, so **type each one exactly as shown.**

> **No commit, no credit.** If a section has no commit, it gets **0**, even if the code is
> on your laptop. Everything must be **pushed before the next lecture starts.**

The warm-up and the in-class exercise are practice. They are not graded, but do them. They
make the rest easier.

---

## Warm-up — 5 minutes (practice, not graded)

Open `part22/Warmup.java`. **Without looking at part 21**, write a `main` that:

1. makes a `Chassis`,
2. plugs an `Ultrasonic` into it,
3. prints what the ultrasonic reads.

Run it. It should print:

```
153.0
```

and the 3D view shows a robot with eyes, in front of a wall. You have to type
`public static void main(String[] args)` yourself. Look back at part 21 only if you are
really stuck.

---

## Section A — Follow along (25 points)

*We start this together in class. Finish it on your own if we run out of time.*

**There is no video for this part.** The lesson is written out in
**[GUIDE.md](GUIDE.md)**, in this folder. Read it one step at a time and type every line
yourself. Do not copy and paste.

You type into three files:

- **`Rover.java`**: your robot, with both sensors, and its behaviors.
- **`ObstacleRun.java`**: a `main` that sends the rover round a box to the goal (the
  **obstacle course** world).
- **`LineRun.java`**: a `main` that follows a loop of tape all the way round (the **line
  track** world).

**Leave the `package part22;`, `import mbot.*;`, `@MBotLab(...)` and `public class ...`
lines alone.** Type everything else.

### Check your work as you go

**Check 1 (guide step 3): run `ObstacleRun.java`.**

```
Blocked after 9 moves
```

In the 3D view: the robot drives toward the orange box and stops **13 cm** in front of it.

**Check 2 (guide step 6): run `ObstacleRun.java` again.**

```
Blocked after 9 moves. Going round.
Blocked after 42 moves. That is the far wall.
Dash the rover reached the goal
```

In the 3D view: the robot stops at the box, steps left, drives past the box, stops at the far
wall, turns right, and ends in the green **Goal**. Nothing gets bumped.

**Check 3 (guide step 9): run `LineRun.java`.** Turn the speed up to **10×**.

```
Halfway round
Tracker the rover finished a lap
```

In the 3D view: the robot wiggles along the tape, all the way round the loop, and ends near
where it started. After 400 steps (when it prints `Halfway round`) it is on the far side of
the loop, heading back. The whole lap is **725 steps** and about **61 seconds** of robot time.

### Stuck? Check these first

| What you see | What is usually wrong |
|---|---|
| `cannot find symbol` and `class Chassis` (or `LineSensor`) | The `import mbot.*;` line is missing at the top of the file |
| It crashes: `... because "this.floor" is null` | The constructor never made the line sensor: `this.floor = new LineSensor(base);` |
| `missing return statement` | `driveUntilBlocked` must end with `return moves;`, **after** the loop |
| The robot drives into the box | `driveUntilBlocked` checks the wrong thing. It should drive **while** the distance is **more than** `cm` |
| The robot drives off the tape and spins | Check the numbers in `followLine` against the guide: 4, 8, 12 turn **left**; 1, 2, 3 turn **right** |
| *"the simulator stopped it"* | A loop never ends. Look at the line with the biggest `×` number in **Your code** |
| The robot never moves, and the run is huge | A `while` loop reads the sensor but has no move inside it |

### ✅ Commit Section A

When all three checks match, type this in the terminal:

```bash
git add -A
git commit -m "part22 follow-along: driveUntilBlocked, goAroundLeft, reachGoal and followLine"
```

---

## In-class exercise — to the wall and back (together, not graded)

We do this one together in class, in `InClass.java`. Everyone types their own copy. It runs
in the **open room** world. This time there is no `Rover` class: we build the robot right
inside `main`.

### Step 1 — Drive to the wall, turn round, look

In `main` under `STEP 1`:

1. Make a `Chassis` called `base`. Plug an `Ultrasonic` called `eyes` into it.
2. Make an `int moves = 0;`.
3. **While** the distance is more than 20: drive 5 cm, and add 1 to `moves`.
4. Print `Moves to the wall: ` and the number.
5. Turn left 180.
6. **If** the distance is less than 20, print `Still blocked!`. **Otherwise** print
   `The way back is clear`.

Your output:

```
Moves to the wall: 31
The way back is clear
```

Things to figure out together:

- The first reading is 173 cm. Why does it take **31** moves, not 34? (173 − 31 × 5 = 18, and
  18 is not more than 20.)
- Why is the sensor read **inside** the loop's `( )`, and not once before the loop?

### Step 2 — Fix the bugs

Under `STEP 2` there are three broken lines, inside a `/* ... */` comment so they don't stop
your program from running. Each one uses the `base` and `eyes` from Step 1. One at a time:

1. Move **one** broken line up, above the `/*` line. Now Java can see it.
2. Run it. Read the **red error**, the **crash**, or what the **3D view** shows.
3. Fix the line. Run the program.
4. Do the next line.

| Broken line | What happens |
|---|---|
| `while (eyes.distance() > 20) { }` | It runs for a long time, then stops with *"The program made 20000 robot calls, so the simulator stopped it. Is there a loop that never ends?"* The robot never moves, so the distance never changes. **Fix:** drive 5 cm inside the `{ }` |
| `if (eyes.distance() > 250); { System.out.println("Nothing ahead for 2.5 m"); }` | It prints `Nothing ahead for 2.5 m`, but the wall is only 18 cm away! The `;` right after the `( )` **ends** the `if`. The `{ }` after it always runs |
| `if (eyes.distance() = 20) { base.stop(); }` | Red error: `unexpected type`, `required: variable`, `found: value`. One `=` **stores** a value. To **compare**, use `<=` here |

After all three are fixed, add one more line at the end of `main`:

```java
System.out.println("Done: " + eyes.distance() + " cm from the wall");
```

It prints `Done: 18.0 cm from the wall`.

**Only one of these is a red error.** The other two run, but do the wrong thing. With robots,
watching the 3D view is how you catch those.

### ✅ Commit the in-class exercise

```bash
git add -A
git commit -m "part22 in-class: drive to the wall and back"
```

---

## Section B — Comment every line (25 points)

*On your own.*

Now explain your own code. In **all three files** (`Rover.java`, `ObstacleRun.java`,
`LineRun.java`), put a `//` comment **above every line of code**, saying **in your own words**
what that line does.

**Which lines need a comment?**

- **Yes:** every line that does something. That includes `public class ...`, every field,
  the constructor's first line, every `this.` line, every method's first line, every `if`,
  `else if`, `else`, `while` and `for`, and every line in `main`.
- **No:** lines that are only `}`, and empty lines. You can skip the `package` and `import`
  lines.

**What makes a good comment?** Say what the line **does** and **why**. Do not just repeat
the code in English.

| ❌ Not enough | ✅ Good |
|---|---|
| `// while loop` | `// keep going as long as the nearest thing ahead is farther than cm away` |
| `// if 4 8 12` | `// only the left-side probes see tape, so the line is to my left: steer left` |
| `// boolean` | `// remembers which side the tape was on last time, for when I lose it` |
| `// goAroundLeft` | `// a skill made of three moves: turn left, slide past the box, face forward again` |

**Rules:**

1. **Your own words.** Do not copy sentences from the guide.
2. **Not sure what a line does?** Write your best guess, and add `(not sure)` at the end.
   An honest guess gets credit. A skipped line does not.
3. **Run both programs again when you're done.** Comments must not change what happens.

### ✅ Commit Section B

```bash
git add -A
git commit -m "part22 comments: explained every line of the follow-along code"
```

---

## Section C — Stretch (25 points)

*On your own.*

Do these in `Stretch.java`. It runs in the **line track** world. There is no `main` in that
file yet. **Type it yourself.**

### Stretch A — guess first

This code makes the same decisions as your `followLine`, but for four readings that are made
up. No robot moves.

1. Type this code inside `main`. **Do not run it yet.**

```java
int[] readings = {6, 12, 3, 0};
for (int s : readings) {
    if (s == 4 || s == 8 || s == 12) {
        System.out.println(s + ": turn left");
    } else if (s == 1 || s == 2 || s == 3) {
        System.out.println(s + ": turn right");
    } else if (s == 0) {
        System.out.println(s + ": lost it");
    } else {
        System.out.println(s + ": straight");
    }
}
```

2. **Above that code**, write what you think it will print, inside a comment. **Also** write
   which probes see tape for each number (far left, left, right, far right).
3. Now run it.
4. Were you wrong about any line? **Keep your wrong guess.** Under it, add a comment that
   says why you were wrong.

<details>
<summary>Click to see the answer. Only after you have run it!</summary>

```
6: straight
12: turn left
3: turn right
0: lost it
```

- **6** = 4 + 2: the two middle probes. The tape is right under the robot: go straight.
- **12** = 8 + 4: the two left probes. The tape is to the left: turn left.
- **3** = 2 + 1: the two right probes. Turn right.
- **0**: no probe sees tape. The robot lost the line.

</details>

### Stretch B — a Lapper that has a Rover

A behavior can be **its own class**. Below the `Stretch` class, in the same file, write a
class `Lapper`. A Lapper **has a** `Rover`, and its skill is driving laps.

**B1.** The class:

- **Two fields:** a `Rover` called `rover`, and an `int` called `done` (laps finished so far).
- **A constructor** `Lapper(Rover rover)` that keeps the rover it is given. This time the part
  is **handed in**, not made inside (like `park(Car car)` in part 18).
- **`void laps(int n)`**: a `for` loop that runs `n` times. Each time: `rover.followLine(725)`,
  add 1 to `done`, and print `Lap <done> done`.

**B2.** In `main`, under your Stretch A code:

```java
Lapper lapper = new Lapper(new Rover("Loop"));
lapper.laps(2);
```

Output (after Stretch A's four lines):

```
Lap 1 done
Lap 2 done
```

In the 3D view (use **10×**): two laps round the track, with no bumps. Count the objects:
`main` made a `Lapper`, which has a `Rover`, which has a `Chassis`, an `Ultrasonic` and a
`LineSensor`. That is composition, three levels deep.

**B3.** In a comment under your code, answer: why does `Lapper` take the `Rover` as a
parameter, instead of making its own with `new Rover(...)` inside the constructor? (There is
no single right answer. Think about who decides the rover's name.)

### ✅ Commit Section C

```bash
git add -A
git commit -m "part22 stretch: prediction, a Lapper that has a Rover, two laps"
```

---

## Section D — Challenge (25 points)

*On your own.*

Open `Challenge.java`. It is a robot class again. **The constructor is done for you**: it
makes the chassis and plugs in both sensors. You write **four behaviors**. A **test** checks
all four.

| Method | What it must do |
|---|---|
| `int driveUntilBlocked(double cm)` | Drive forward **5 cm** at a time, **while** the ultrasonic reads **more than** `cm`. Give back how many moves it made |
| `void sidestepRight(double cm)` | Turn right 90, drive `cm`, turn left 90. The robot ends up `cm` to the right, facing the same way |
| `void reachGoal()` | Use the two methods above: drive until blocked (15), sidestep right 70, drive until blocked (15), sidestep right 70, drive until blocked (15) |
| `void followLine(int steps)` | Do exactly `steps` steps. Each step: read the line sensor **once**, then do **one** move. The rules are in the comment above the method |

**The test uses new worlds**, in the `worlds/` folder of this repo:

- `part22-maze.json`: two boxes. You go round each one on its **right** side.
- `part22-track.json`: a loop of tape with **sharp** corners that all turn **right**. Your
  `followLine` must **also** still work on the round track from class.

On the sharp corners the robot runs off the end of the tape and sees **0**. That is what the
`lineWasLeft` field is for: when you lose the line, turn toward the side you saw it last.

**How to work on it:**

1. Change only the lines marked `// YOUR CODE`.
2. Press `⌘ ⇧ T` (Mac) or `Ctrl ⇧ T` (Windows) to jump to `ChallengeTest.java`.
3. Click the **green ▶** next to `class ChallengeTest` → **Run**.
4. **Red ✗:** click it. Compare **expected** with **but was**. **Green ✔:** that one is right.
5. Fix, run again, until **all** tests are green. One may be green before you start. That
   does not mean you are done.

The tests run the robot **without** the 3D view. **Never change `ChallengeTest.java`.**

**Want to watch?** Make a class `Watch.java` in the `part22` folder:

```java
package part22;

import mbot.*;

@MBotLab(world = "worlds/part22-track.json")
public class Watch {
    public static void main(String[] args) {
        new Challenge().followLine(900);
    }
}
```

(For the maze, use `"worlds/part22-maze.json"` and call `reachGoal()`.)

**Hints:**

- Do `driveUntilBlocked` **first**. It is your part 21 `stepsToWall`, with `cm` instead of 15.
- `reachGoal` is five lines, and every one is a call to one of your own methods.
- `followLine`: copy the **shape** from your `Rover`. The difference is the `else if (s == 0)`
  part: an `if` inside it checks `lineWasLeft`.

### ✅ Commit Section D

When every test is green:

```bash
git add -A
git commit -m "part22 challenge: driveUntilBlocked, sidestepRight, reachGoal and followLine"
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
a1b2c3d part22 challenge: driveUntilBlocked, sidestepRight, reachGoal and followLine
e4f5a6b part22 stretch: prediction, a Lapper that has a Rover, two laps
c7d8e9f part22 comments: explained every line of the follow-along code
9d8c7b6 part22 in-class: drive to the wall and back
0a1b2c3 part22 follow-along: driveUntilBlocked, goAroundLeft, reachGoal and followLine
```

*(The letters and numbers at the start will be different for you. That is normal.)*
Press `q` to get out of the list.

**Missing one?** That section gets **0** right now. Go back, finish it, and make its
commit. You have until the next lecture starts.

**Typed a commit message wrong?** Make one more commit with the right message, with
`--allow-empty`:

```bash
git commit --allow-empty -m "part22 comments: explained every line of the follow-along code"
```

### 3. Push

```bash
git add -A
git commit -m "part22 log"
git push
```

### 4. Check GitHub

Go to your fork on GitHub (`github.com/your-username/FollowAlong5-Robots`). Click the
**commits** link (clock icon) above the file list. **All of this part's commits must be
there.**

> **No commit, no credit.** A section with no commit gets 0. A commit that is not pushed
> before the next lecture starts does not count.

**Coming up in lecture:** in Week 11 you meet **interfaces**. A `Behavior` interface would
let a robot swap one skill for another, like `Lapper` for a wall-follower, without changing
the robot. Keep your `Lapper`: we will come back to it.

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

- **Think Java** (free online book): [Chapter 6 — Loops and Strings](https://books.trinket.io/thinkjava2/chapter6.html)
  for `while` loops, and [Chapter 11 — Designing Classes](https://books.trinket.io/thinkjava2/chapter11.html).
- **Tutoring:** bring your laptop and this page.
- **Office hours:** bring the error message and tell me what you already tried.
- **Classmates:** talk it over as much as you want. But **type your own code.**
