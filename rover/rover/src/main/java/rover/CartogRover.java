package rover;

import rover.reasoning.scout.ScoutPlanFactory;
import rover.reasoning.simple.SimpleDesireFactory;
import rover.reasoning.simple.SimpleIntentionFilter;
import rover.reasoning.simple.SimplePerceptFactory;
import rover.reasoning.simple.SimplePlanFactory;
import rover.shared.reasoning.intention.SearchIntention;

/**
 * Created by rachelcabot on 04/11/2016.
 */
public class CartogRover extends AReasoningRover {
    public CartogRover() {
        super(3, 6, 0);
        this.i.add(new SearchIntention());
        planFactory = new ScoutPlanFactory();
        desireFactory = new SimpleDesireFactory();
        intentionFilter = new SimpleIntentionFilter();
        perceptFactory = new SimplePerceptFactory();
    }
}
