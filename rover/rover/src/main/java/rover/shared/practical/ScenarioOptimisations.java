package rover.shared.practical;

import java.util.HashMap;

/**
 * Created by rachelcabot on 08/11/2016.
 */
public class ScenarioOptimisations {
    private static final int[] scenarioToResourceVolumes = {10,5,5,1,1,2,1,5,15,2};
    private static final int[] scenarioToResourceNumbers = {1,5,10,10,15,30,10,25,5,50};
    private static int scenario;
    private static final double energyPerDeposit = 2;
    private static final double energyPotentialOverhead = 50;


    public static int getResourceVolume() {
        return scenarioToResourceVolumes[scenario];
    }

    public static void setScenario(int scenario) {
        ScenarioOptimisations.scenario = scenario;
    }

    public static double getEnergyPerMovementUnit(int movementSpeed) {
        return 2;
    }

    public static double getEnergyPerDeposit() {
        return energyPerDeposit;
    }

    public static double getEnergyPotentialOverhead(){
        return energyPotentialOverhead;
    }
}
