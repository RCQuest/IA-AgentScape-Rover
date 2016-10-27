package rover;

import rover.messaging.MessagingSystem;
import rover.shared.practical.ARoverAction;
import rover.shared.reasoning.*;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public abstract class AReasoningRover extends APracticalRover {
    protected APlanFactory planFactory;
    protected ADesireFactory desireFactory;
    protected AIntentionFilter intentionFilter;
    protected APerceptFactory perceptFactory;

    private ArrayList<ABelief> b;
    private boolean lastActionWasSuccessful;
    private ArrayList<Desire> d;
    protected ArrayList<AIntention> i;
    private APlan pl;


    public AReasoningRover(int s, int r, int c){
        super(s,r,c);
        b = new ArrayList<>();
        d = new ArrayList<>();
        i = new ArrayList<>();
        lastActionWasSuccessful = true;
        try {
            MessagingSystem.getInstance().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    void begin(){
        this.setUpPracticalAttributes();
        MessagingSystem.sendNewMessage("hello");
        APercept p = perceptFactory.create(null,this);
        b = setUpBeliefBase(p);
        d = options(b,i);
        i = filter(b,d,i);
        pl = plan(b,i);
        System.out.println("initial state");
        System.out.println(b);
        System.out.println(d);
        System.out.println(i);

        execute(pl.popStep());
    }

    @Override
    void poll(PollResult pr){
        do {
            if (!(empty(pl) || succeeded(i, b) || impossible(i, b))) {
                APercept p = perceptFactory.create(pr, this);
                b = brf(b, p);
                if (reconsider(i, b)) {
                    d = options(b, i);
                    i = filter(b, d, i);
                }
                if (!sound(pl, i, b)) {
                    pl = plan(b, i);
                }
            } else {
                APercept p = perceptFactory.create(pr, this);
                b = brf(b, p);
                d = options(b, i);
                i = filter(b, d, i);
                pl = plan(b, i);
            }

            execute(pl.popStep());
            if(!lastActionWasSuccessful){
                pr.setResultType(PollResult.FAILED);
                pr.setResultStatus(PollResult.FAILED);
            }
        } while(!lastActionWasSuccessful);
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
        if(roverAction==null) {
            lastActionWasSuccessful = false;
        } else {
            lastActionWasSuccessful = roverAction.execute(this);
        }
    }

    boolean impossible(ArrayList<AIntention> i, ArrayList<ABelief> b) {
//        for (AIntention intention:i) {
//            if(!intention.possible(b))
//                return true;
//        }
        //TODO: this needs to be revised for intentions.
        return false;
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
        for(ABelief belief : b){
            belief.coalesceWith(p);
        }
        return b;
    }

    private ArrayList<ABelief> setUpBeliefBase(APercept initialP){
        return initialP.initialiseBeliefs();
    }

    @Override
    public boolean previousActionWasSuccessful() {
        return lastActionWasSuccessful;
    }

}
