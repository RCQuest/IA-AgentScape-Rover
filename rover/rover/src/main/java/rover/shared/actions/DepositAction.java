package rover.shared.actions;

import rover.APracticalRover;
import rover.shared.practical.ARoverAction;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class DepositAction extends ARoverAction {
    @Override
    public boolean execute(APracticalRover rover) {
        try {
            System.out.println("attempting to deposit...");
            rover.deposit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
