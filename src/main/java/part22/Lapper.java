package part22;

public class Lapper {
    private Rover rover;
    private int done;

    public Lapper(Rover rover){ // I believe this is the case because I think Lapper is a specific type of rover
        this.rover = rover;
        this.done = 0;
    }

    public void laps(int n){
        for (int i = 0; i < n ; i++) {
            rover.followLine(725);
            done++;
            System.out.println("Lap " + done + " done");
        }
    }
}
