package rover;

import rover.shared.*;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public abstract class ReasoningRover extends AGeneralRover {
    private ArrayList<Belief> b;
    private ArrayList<Desire> d;
    private ArrayList<Intention> i;
    private Plan pl;


    public ReasoningRover(){
        super(3,3,3);
    }

    @Override
    void begin(){
        Percept p = new Percept(null);
        b = brf(b,p);
        d = options(b,i);
        i = filter(b,d,i);
        pl = plan(b,i);
    }

    @Override
    void poll(PollResult pr){
        if(!(empty(pl)||succeeded(i,b)||impossible(i,b))){
            execute(pl.popStep());
            Percept p = new Percept(pr);
            b = brf(b,p);
            if(reconsider(i,b)){
                d = options(b,i);
                i = filter(b,d,i);
            }
            if(!sound(pl,i,b)){
                pl = plan(b,i);
            }
        } else {
            Percept p = new Percept(pr);
            b = brf(b,p);
            d = options(b,i);
            i = filter(b,d,i);
            pl = plan(b,i);
        }
    }

    abstract boolean sound(Plan pl, ArrayList<Intention> i, ArrayList<Belief> b);

    abstract boolean reconsider(ArrayList<Intention> i, ArrayList<Belief> b);

    abstract void execute(RoverAction roverAction);

    abstract boolean impossible(ArrayList<Intention> i, ArrayList<Belief> b);

    abstract boolean succeeded(ArrayList<Intention> i, ArrayList<Belief> b);

    abstract boolean empty(Plan pl);

    abstract Plan plan(ArrayList<Belief> b, ArrayList<Intention> i);

    abstract ArrayList<Intention> filter(ArrayList<Belief> b, ArrayList<Desire> d, ArrayList<Intention> i);

    abstract ArrayList<Desire> options(ArrayList<Belief> b, ArrayList<Intention> i);

    abstract ArrayList<Belief> brf(ArrayList<Belief> b, Percept p);
}
