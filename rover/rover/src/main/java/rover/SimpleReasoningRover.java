package rover;

import rover.reasoning.simple.SimpleDesireFactory;
import rover.reasoning.simple.SimpleIntentionFilter;
import rover.reasoning.simple.SimplePlanFactory;
import rover.reasoning.simple.SimplePerceptFactory;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class SimpleReasoningRover extends AReasoningRover {
    public SimpleReasoningRover(){
        super(3,3,3);
        planFactory = new SimplePlanFactory();
        desireFactory = new SimpleDesireFactory();
        intentionFilter = new SimpleIntentionFilter();
        perceptFactory = new SimplePerceptFactory();
    }
}
