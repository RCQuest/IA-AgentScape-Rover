package rover;

import rover.shared.*;
import rover.shared.reasoning.*;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public abstract class AReasoningRover extends AGeneralRover {
    protected APlanFactory planFactory;
    protected ADesireFactory desireFactory;
    protected AIntentionFilter intentionFilter;
    protected APerceptFactory perceptFactory;

    private ArrayList<ABelief> b;
    private boolean lastActionWasSuccessful;
    private ArrayList<Desire> d;
    private ArrayList<AIntention> i;
    private APlan pl;


    public AReasoningRover(int s, int r, int c){
        super(s,r,c);
    }

    @Override
    void begin(){
        APercept p = perceptFactory.create(null);
        b = brf(b,p);
        d = options(b,i);
        i = filter(b,d,i);
        pl = plan(b,i);
    }

    @Override
    void poll(PollResult pr){
        if(!(empty(pl)||succeeded(i,b)||impossible(i,b))){
            execute(pl.popStep());
            APercept p = perceptFactory.create(pr);
            b = brf(b,p);
            if(reconsider(i,b)){
                d = options(b,i);
                i = filter(b,d,i);
            }
            if(!sound(pl,i,b)){
                pl = plan(b,i);
            }
        } else {
            APercept p = perceptFactory.create(pr);
            b = brf(b,p);
            d = options(b,i);
            i = filter(b,d,i);
            pl = plan(b,i);
        }
    }

    boolean sound(APlan pl, ArrayList<AIntention> i, ArrayList<ABelief> b) {
        return pl.isSound(i,b);
    }

    boolean reconsider(ArrayList<AIntention> i, ArrayList<ABelief> b) {
        for (AIntention intention:i) {
            if(intention.shouldBeReconsidered(b,i))
                return true;
        }

        return false;
    }

    void execute(ARoverAction roverAction) {
        lastActionWasSuccessful=roverAction.execute(this);
    }

    boolean impossible(ArrayList<AIntention> i, ArrayList<ABelief> b) {
        for (AIntention intention:i) {
            if(!intention.possible(b))
                return false;
        }
        return true;
    }

    boolean succeeded(ArrayList<AIntention> i, ArrayList<ABelief> b) {
        for (AIntention intention:i) {
            if(!intention.fulfilled(b))
                return false;
        }
        return true;
    }

    boolean empty(APlan pl) {
        return pl.empty();
    }

    APlan plan(ArrayList<ABelief> b, ArrayList<AIntention> i) {
        return planFactory.createPlan(b,i);
    }

    ArrayList<AIntention> filter(ArrayList<ABelief> b, ArrayList<Desire> d, ArrayList<AIntention> i) {
        return intentionFilter.filter(b,d,i);
    }

    ArrayList<Desire> options(ArrayList<ABelief> b, ArrayList<AIntention> i) {
        // tell me what you desire
        return desireFactory.generateDesires(b,i);
    }

    ArrayList<ABelief> brf(ArrayList<ABelief> b, APercept p) {
        // turn your percept into beliefs, and reevaluate the beliefs you have
        ArrayList<ABelief> evaluatedBeliefs = new ArrayList<>();
        for(ABelief belief : b){
            if(belief.agreesWith(p))
                evaluatedBeliefs.add(belief);
        }
        evaluatedBeliefs.addAll(p.toBeliefs());
        return evaluatedBeliefs;
    }

    public boolean wasLastActionSuccessful() {
        return lastActionWasSuccessful;
    }
}
