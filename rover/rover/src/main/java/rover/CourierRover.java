package rover;

import rover.reasoning.carrier.CollectorPlanFactory;
import rover.reasoning.simple.SimpleDesireFactory;
import rover.reasoning.simple.SimpleIntentionFilter;
import rover.reasoning.simple.SimplePerceptFactory;
import rover.shared.reasoning.intention.ObtainIntention;
import rover.shared.reasoning.intention.RetrieveIntention;

/**
 * Created by rachelcabot on 04/11/2016.
 */
public class CourierRover extends AReasoningRover {
    public CourierRover() {
        super(8, 0, 1, 1, false);
        this.i.add(new ObtainIntention());
        this.i.add(new RetrieveIntention());
        planFactory = new CollectorPlanFactory();
        desireFactory = new SimpleDesireFactory();
        intentionFilter = new SimpleIntentionFilter();
        perceptFactory = new SimplePerceptFactory(Math.round(Math.random()*3000));
    }
}
