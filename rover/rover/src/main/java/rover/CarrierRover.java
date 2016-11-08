package rover;

import rover.reasoning.carrier.CarrrierPlanFactory;
import rover.reasoning.simple.SimpleDesireFactory;
import rover.reasoning.simple.SimpleIntentionFilter;
import rover.reasoning.simple.SimplePerceptFactory;
import rover.reasoning.simple.SimplePlanFactory;
import rover.shared.reasoning.intention.ObtainIntention;
import rover.shared.reasoning.intention.RetrieveIntention;

/**
 * Created by rachelcabot on 04/11/2016.
 */
public class CarrierRover extends AReasoningRover {
    public CarrierRover() {
        super(4, 0, 5);
        this.i.add(new ObtainIntention());
        this.i.add(new RetrieveIntention());
        planFactory = new CarrrierPlanFactory();
        desireFactory = new SimpleDesireFactory();
        intentionFilter = new SimpleIntentionFilter();
        perceptFactory = new SimplePerceptFactory();
    }
}
