package rover;

import rover.reasoning.simple.SimpleDesireFactory;
import rover.reasoning.simple.SimpleIntentionFilter;
import rover.reasoning.simple.SimplePlanFactory;
import rover.reasoning.simple.SimplePerceptFactory;
import rover.shared.reasoning.intention.ObtainIntention;
import rover.shared.reasoning.intention.RetrieveIntention;
import rover.shared.reasoning.intention.SearchIntention;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class GeneralRover extends AReasoningRover {
    public GeneralRover(){
        super(3,3,3,1);
        this.i.add(new SearchIntention());
        this.i.add(new ObtainIntention());
        this.i.add(new RetrieveIntention());
        planFactory = new SimplePlanFactory();
        desireFactory = new SimpleDesireFactory();
        intentionFilter = new SimpleIntentionFilter();
        perceptFactory = new SimplePerceptFactory();
    }
}
