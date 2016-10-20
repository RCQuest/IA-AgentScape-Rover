package rover.reasoning.simple;

import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.AIntention;
import rover.shared.reasoning.Desire;
import rover.shared.reasoning.intention.ObtainIntention;
import rover.shared.reasoning.intention.RetrieveIntention;
import rover.shared.reasoning.intention.SearchIntention;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class SimpleIntentionFilter extends rover.shared.reasoning.AIntentionFilter {
    @Override
    public ArrayList<AIntention> filter(ArrayList<ABelief> b, ArrayList<Desire> d, ArrayList<AIntention> i) {
        if(i==null){
            i = new ArrayList<>();
            i.add(new SearchIntention());
            i.add(new ObtainIntention());
            i.add(new RetrieveIntention());
        }
        return i;
    }
}
