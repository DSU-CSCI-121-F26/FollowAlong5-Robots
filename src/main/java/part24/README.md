# Part 24 — Overriding and polymorphism: same command, different robot

**We start this together in class · due before the next lecture · No AI**

In part 23 your robot **inherited** `straight` and `turnLeft` from MBot2, and added new
methods of its own. Today your robot **keeps** a method's name, but **changes** what it does.
Then you write one method that works for **any** robot, and each robot does it its own way.

**What you learn:**

- **overriding**: writing a method in your subclass with the **same name and the same
  parameters** as one in the superclass. Your version replaces the old one, for your robot
- **`@Override`**: a note you put above an overriding method. If the method does **not**
  really override anything (a typo, a wrong parameter type), Java gives a red error
- **`super.straight(cm)`**: inside your override, this runs the **superclass's** version.
  You keep the old behavior and add to it
- **polymorphism**: an `MBot2` variable can hold any robot that extends MBot2. When you call
  a method, the **object** decides which version runs, not the variable
- **a method for any robot**: a method with an `MBot2` parameter works for a CarefulBot, a
  FlashyBot, a plain MBot2, and every robot class anyone writes later

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
in the 3D view in your browser. (If no tab opens, go to **http://localhost:8121**.)

**Watch "What's happening" in the 3D view.** Inside an override, it says things like
*"Inside CarefulBot.straight(), super.straight() runs Chassis's version."* That is your
override calling the old method.

## How this part is graded

This part has **four graded sections**. Each one is worth **25 points**.

| Section | File | Points | Your commit message |
|---|---|---|---|
| **A · Follow along** | `CarefulBot.java`, `FlashyBot.java`, `Mission.java` | 25 | `part24 follow-along: CarefulBot and FlashyBot override, one mission for any robot` |
| **B · Comments** | the same three files | 25 | `part24 comments: explained every line of the follow-along code` |
| **C · Stretch** | `Stretch.java` (and the three follow-along files) | 25 | `part24 stretch: which version runs, toString overrides` |
| **D · Challenge** | `Challenge.java` | 25 | `part24 challenge: a safe straight, a lap of the yard, stepsToWall for any robot` |

When you finish a section, you **commit** it with the message in the table. That commit is
how I know you finished it. **You do not send me anything on Canvas.** A program I run reads
your commit messages, so **type each one exactly as shown.**

> **No commit, no credit.** If a section has no commit, it gets **0**, even if the code is
> on your laptop. Everything must be **pushed before the next lecture starts.**

The warm-up and the in-class exercise are practice. They are not graded, but do them. They
make the rest easier.

---

## Warm-up — 5 minutes (practice, not graded)

Open `part24/Warmup.java`. **Without looking at part 23**:

1. At the bottom of the file, write a class `Tiny` that **extends** `MBot2`. Give it a
   constructor that calls `super()`, and a method `look()` that prints what
   `readUltrasonic()` reads.
2. In the `Warmup` class, write a `main` that makes a `Tiny` and calls `look()`.

Run it. It should print:

```
153.0
```

The **Your robot** box in the 3D view shows `Tiny` under `MBot2`. You have to type
`public static void main(String[] args)` yourself. Look back only if you are really stuck.

---

## Section A — Follow along (25 points)

*We start this together in class. Finish it on your own if we run out of time.*

**There is no video for this part.** The lesson is written out in
**[GUIDE.md](GUIDE.md)**, in this folder. Read it one step at a time and type every line
yourself. Do not copy and paste.

You type into three files. They all run in the **wall ahead** world:

- **`CarefulBot.java`**: a robot that looks before it drives. It **overrides** `straight`.
- **`FlashyBot.java`**: a robot that flashes a light when it turns. It **overrides**
  `turnLeft` and `turnRight`.
- **`Mission.java`**: one method, `mission(MBot2 bot)`, that works for **any** robot.

**Leave the `package part24;`, `import mbot.*;`, `@MBotLab(...)` and `public class ...`
lines alone.** Type everything else.

### Check your work as you go

**Check 1 (guide step 2): run `Mission.java`.**

```
Mission start
Mission done
```

The robot does **not** move. Under **What's happening**, the 3D view says *"run() was called,
but CarefulBot does not override it, so MBot2's empty run() ran and nothing happened."* That
is right, for now.

**Check 2 (guide step 3): run `Mission.java` again.**

```
Mission start
CarefulBot: not driving 40.0 cm, too close
CarefulBot: not driving 40.0 cm, too close
Mission done
```

In the 3D view: the robot drives 40 cm three times, then refuses twice, with light 1 red. It
stops 33 cm from the wall. Nothing gets bumped.

**Check 3 (guide step 5): set `pick` to 2. Run it.**

```
Mission start
Flash! Turning left 90.0
Flash! Turning left 90.0
Flash! Turning left 90.0
Flash! Turning left 90.0
Mission done
```

In the 3D view: a 40 cm square, with light 1 flashing blue at every corner. The robot ends
where it started.

**Check 4 (guide step 7): set `pick` to 3. Run it.**

```
Mission start
Mission done
```

The robot does not move, and the 3D view says *"MBot2's own run() does nothing."* Same
`mission(bot)` call, three different robots, three different results.

### Stuck? Check these first

| What you see | What is usually wrong |
|---|---|
| `method does not override or implement a method from a supertype` | The name or the parameter is different from MBot2's. It must be exactly `public void straight(double cm)` (or `turnLeft(double degrees)`, or `run()`) |
| It crashes with `StackOverflowError` | Inside your override you wrote `straight(cm)` instead of `super.straight(cm)`. The method calls itself, forever |
| The robot never moves, but no error | Your override is missing the `super.` line. The old behavior never runs |
| With `pick` 2 the robot does nothing, and the 3D view says FlashyBot *"does not override"* `run()` | You have not typed FlashyBot's `run()` yet (guide step 4) |
| `variable bot might not have been initialized` | The `if`/`else if`/`else` must give `bot` a robot in **every** case. Check the last `else` |

### ✅ Commit Section A

When all four checks match, set `pick` back to 1, then type this in the terminal:

```bash
git add -A
git commit -m "part24 follow-along: CarefulBot and FlashyBot override, one mission for any robot"
```

---

## In-class exercise — a counting robot (together, not graded)

We do this one together in class, in `InClass.java`. Everyone types their own copy. It runs
in the **open room** world.

### Step 1 — A robot that counts its moves

At the bottom of `InClass.java`, finish the class `Counter` together:

- **One field:** an `int` called `moves`.
- **Override `straight`:** put `@Override` above `public void straight(double cm)`. Inside,
  add 1 to `moves`, then call `super.straight(cm);`.
- **Override `toString`:** put `@Override` above `public String toString()`. Give back
  `"Counter: " + moves + " moves"`.

Then, in `main` under `STEP 1`:

```java
MBot2 bot = new Counter();
for (int i = 0; i < 4; i++) {
    bot.straight(30);
    bot.turnLeft(90);
}
System.out.println(bot);
```

Your output:

```
Counter: 4 moves
```

In the 3D view: a 30 cm square.

Things to figure out together:

- `bot` is an `MBot2` variable. So why does `bot.straight(30)` run **Counter's** straight?
  (The object is a Counter. The **object** decides.)
- Counter's `toString` replaced an **old** one. Where did the old one come from?
  (From **MBot2**, which gives back the robot's name: `MBot2`, unless you made it with
  `new MBot2("Sparky", "red", "yellow")`. And MBot2's version replaced one from `Object`:
  every class in Java extends `Object`, even when it does not say so.)
- Why must `super.straight(cm)` be there? Try taking it out for a moment.

### Step 2 — Fix the bugs

Under `STEP 2` there are three groups of lines inside a `/* ... */` comment so they don't run.
Each group uses a robot class with **one mistake in its `straight` method**: `CounterA`,
`CounterB` and `CounterC`, at the bottom of the file.

**First, put `//` in front of your STEP 1 lines in `main`**, so each buggy robot is the only
robot in the run. Then, one group at a time:

1. Move **one** group of three lines up, above the `/*` line. Now Java runs it.
2. Run it. Read what it **prints**, what the **3D view** shows, or the **crash**.
3. Fix **that class's `straight`**. Run the program again.
4. Do the next group.

| Group | What happens | Where the mistake is |
|---|---|---|
| `CounterA` | It prints `A: 0 moves`, but the robot **does** drive 30 cm | `straight(int cm)`: the parameter is an `int`, not a `double`. That is a **different** method, not an override, so MBot2's `straight` ran and never counted. Change it to `double`, and add `@Override` |
| `CounterB` | It prints `B: 1 moves`, but the robot does **not** move | The override counts, but never calls `super.straight(cm)`. The old behavior is gone |
| `CounterC` | It **crashes**: `StackOverflowError`, and the same red line many times in the Run window | `straight(cm);` calls **itself**, which calls itself, forever, until Java runs out of room. It should be `super.straight(cm);` |

After you fix CounterA, try this: change `double` back to `int` for a moment, **with**
`@Override` above it. You get a red error: `method does not override or implement a method
from a supertype`. Without `@Override`, the mistake was silent. **With it, Java catches it.**
Change it back to `double`.

When all three are fixed, take the `//` off your STEP 1 lines. Your output:

```
Counter: 4 moves
A: 1 moves
B: 1 moves
C: 1 moves
```

You will also see the note *"A second robot was made."* That is fine. There is only one robot
in the simulator, so all the robots in this file share it.

### ✅ Commit the in-class exercise

```bash
git add -A
git commit -m "part24 in-class: a robot that counts its moves"
```

---

## Section B — Comment every line (25 points)

*On your own.*

Now explain your own code. In **all three files** (`CarefulBot.java`, `FlashyBot.java`,
`Mission.java`), put a `//` comment **above every line of code**, saying **in your own words**
what that line does.

**Which lines need a comment?**

- **Yes:** every line that does something. That includes `public class ...`, every field,
  every `@Override`, the constructor's first line, `super();`, every method's first line,
  every `super.` line, every `if`, `else if`, `else` and `for`, and every line in `main`.
- **No:** lines that are only `}`, and empty lines. You can skip the `package` and `import`
  lines.

**What makes a good comment?** Say what the line **does** and **why**. Do not just repeat
the code in English.

| ❌ Not enough | ✅ Good |
|---|---|
| `// override` | `// replace MBot2's straight with my own careful version, same name and parameter` |
| `// super.straight` | `// it is safe, so do the normal drive that MBot2 inherited from Chassis` |
| `// MBot2 bot` | `// any kind of robot can go in this variable; the robot decides what run() does` |
| `// call run` | `// runs this robot's own run(): CarefulBot's, FlashyBot's, or MBot2's empty one` |

**Rules:**

1. **Your own words.** Do not copy sentences from the guide.
2. **Not sure what a line does?** Write your best guess, and add `(not sure)` at the end.
   An honest guess gets credit. A skipped line does not.
3. **Run `Mission.java` again when you're done.** Comments must not change what happens.

### ✅ Commit Section B

```bash
git add -A
git commit -m "part24 comments: explained every line of the follow-along code"
```

---

## Section C — Stretch (25 points)

*On your own.*

Stretch A is in `Stretch.java`. It runs in the **open room** world. There is no `main` in
that file yet. **Type it yourself.** Stretch B changes your three follow-along files.

### Stretch A — guess first: which version runs?

1. Type this code inside `main` in `Stretch.java`. **Do not run it yet.**

```java
MBot2 a = new FlashyBot();
Chassis c = a;
a.straight(10);
c.turnLeft(90);
System.out.println(a == c);
```

2. **Above that code**, write what you think it will print, inside a comment. **Also**
   answer: `c` is a `Chassis` variable. Does `c.turnLeft(90)` run Chassis's `turnLeft` or
   FlashyBot's? And whose `straight` does `a.straight(10)` run?
3. Now run it.
4. Were you wrong about anything? **Keep your wrong guess.** Under it, add a comment that
   says why you were wrong.

<details>
<summary>Click to see the answer. Only after you have run it!</summary>

```
Flash! Turning left 90.0
true
```

- `a.straight(10)` prints nothing. FlashyBot does not override `straight`, so it runs the
  inherited one, from **Chassis**.
- `c.turnLeft(90)` runs **FlashyBot's** `turnLeft`, even though `c` is a `Chassis` variable.
  The **object** is a FlashyBot, and the object decides. That is polymorphism.
- `a == c` is `true`: `a` and `c` are two names for the **same** object.

</details>

### Stretch B — override `toString`

`println(bot)` calls `bot.toString()`. Every class has a `toString`, because every class
extends `Object`, and `Object` has one. MBot2 overrides it to give back the robot's name.
That is better than Object's version, but it does not tell you anything about *your* robot.

**B1.** In `CarefulBot.java`:

- Add a field `private int refused;`.
- In `straight`, in the `else` part, add 1 to `refused`.
- Override `toString`, with `@Override`. Give back `"CarefulBot (refused " + refused + " moves)"`.

**B2.** In `FlashyBot.java`:

- Add a field `private int turns;`.
- In **both** turn methods, add 1 to `turns` at the end.
- Override `toString`, with `@Override`. Give back `"FlashyBot (" + turns + " turns)"`.

**B3.** In `Mission.java`, change the two prints in `mission` so they print the robot too:

```java
System.out.println("Mission start: " + bot);
```

and the same for `"Mission done: "`.

Run `Mission.java` with `pick` 1:

```
Mission start: CarefulBot (refused 0 moves)
CarefulBot: not driving 40.0 cm, too close
CarefulBot: not driving 40.0 cm, too close
Mission done: CarefulBot (refused 2 moves)
```

With `pick` 2, the first and last lines are `Mission start: FlashyBot (0 turns)` and
`Mission done: FlashyBot (4 turns)`.

In `Stretch.java`, add `System.out.println(a);` at the end of `main`. It prints
`FlashyBot (1 turns)`.

**B4.** Run `Mission.java` with `pick` **3**. What does the first line print now? In a
comment in `Stretch.java`, write what it printed and explain **why** it looks like that.

<details>
<summary>Click to see the answer. Only after you have tried it!</summary>

It prints `Mission start: MBot2`. A plain MBot2 uses **MBot2's own** `toString`, which gives
back the robot's name. `new MBot2()` uses the default name, `"MBot2"`. Made with
`new MBot2("Sparky", "red", "yellow")`, it would print `Mission start: Sparky`.

If MBot2 did not override `toString`, Java would use **Object**'s version: the class name,
`@`, and a number, like `mbot.MBot2@2077d4de`.

</details>

Set `pick` back to 1 when you are done.

### ✅ Commit Section C

```bash
git add -A
git commit -m "part24 stretch: which version runs, toString overrides"
```

---

## Section D — Challenge (25 points)

*On your own.*

Open `Challenge.java`. It is a robot class: `public class Challenge extends MBot2`. You write
**two overrides** and **one method that works for any robot**. A **test** checks all three.

| Method | What it must do |
|---|---|
| `void straight(double cm)` (override) | A robot that **never bumps into anything**. If `cm` is **0 or less** (backing up), just do what MBot2's `straight` does. Otherwise, drive in steps of **5 cm** (the last step can be smaller), reading the ultrasonic **before each step**. Stop when it reads **20 or less**, or when you have driven `cm` in total |
| `void run()` (override) | Once round the yard: **4 times**, `straight(1000)`, then turn right 90. Your safe `straight` is what stops it at each wall |
| `static int stepsToWall(MBot2 bot)` | Works for **any** robot. **While** `bot` reads more than 20 on its ultrasonic: `bot.straight(10)`, and count it. Give back the count |

**The test uses new worlds**, in the `worlds/` folder of this repo:

- `part24-hall.json`: a long, empty hall. The robot starts 40 cm in, facing the far end.
- `part24-yard.json`: a yard with a box in the middle, and a **Home** corner where the robot
  starts.

The test calls `stepsToWall` with **three different kinds of robot**: your `Challenge`, a plain
`MBot2`, and a robot class written inside the test, whose `straight` only goes half as far. Each
one gives a different answer. Your method does not change at all. That is polymorphism.

**How to work on it:**

1. Change only the lines marked `// YOUR CODE`.
2. Press `⌘ ⇧ T` (Mac) or `Ctrl ⇧ T` (Windows) to jump to `ChallengeTest.java`.
3. Click the **green ▶** next to `class ChallengeTest` → **Run**.
4. **Red ✗:** click it. Compare **expected** with **but was**. **Green ✔:** that one is right.
5. Fix, run again, until **all** tests are green.

The tests run the robot **without** the 3D view. **Never change `ChallengeTest.java`.**

**Want to watch?** Make a class `Watch.java` in the `part24` folder:

```java
package part24;

import mbot.*;

@MBotLab(world = "worlds/part24-yard.json")
public class Watch {
    public static void main(String[] args) {
        MBot2 bot = new Challenge();
        bot.run();
    }
}
```

(For the hall, use `"worlds/part24-hall.json"` and call `bot.straight(500);`.)

**Hints:**

- `straight`: handle `cm <= 0` first, with `super.straight(cm)` and `return`. Then keep a
  variable `left` for how far is still to go, and use a `while` loop with two conditions,
  joined by `&&`. `Math.min(5, left)` gives the smaller of the two.
- Inside your `straight`, the real driving is `super.straight(step)`. If you write
  `straight(step)`, your method calls itself forever (remember CounterC).
- `run` is a `for` loop with two lines in it.
- `stepsToWall` is your part 21 `stepsToWall`, but it uses `bot.` in front of everything.

### ✅ Commit Section D

When every test is green:

```bash
git add -A
git commit -m "part24 challenge: a safe straight, a lap of the yard, stepsToWall for any robot"
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
a1b2c3d part24 challenge: a safe straight, a lap of the yard, stepsToWall for any robot
e4f5a6b part24 stretch: which version runs, toString overrides
c7d8e9f part24 comments: explained every line of the follow-along code
9d8c7b6 part24 in-class: a robot that counts its moves
0a1b2c3 part24 follow-along: CarefulBot and FlashyBot override, one mission for any robot
```

*(The letters and numbers at the start will be different for you. That is normal.)*
Press `q` to get out of the list.

**Missing one?** That section gets **0** right now. Go back, finish it, and make its
commit. You have until the next lecture starts.

**Typed a commit message wrong?** Make one more commit with the right message, with
`--allow-empty`:

```bash
git commit --allow-empty -m "part24 comments: explained every line of the follow-along code"
```

### 3. Push

```bash
git add -A
git commit -m "part24 log"
git push
```

### 4. Check GitHub

Go to your fork on GitHub (`github.com/your-username/FollowAlong5-Robots`). Click the
**commits** link (clock icon) above the file list. **All of this part's commits must be
there.**

> **No commit, no credit.** A section with no commit gets 0. A commit that is not pushed
> before the next lecture starts does not count.

**Coming up in lecture:** **interfaces** and **abstract classes**. They let you say *"every
robot must have a `run()`"*, without writing an empty one first, like MBot2 had to.

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
