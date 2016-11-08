package rover.shared.reasoning.beliefs;

import rover.shared.practical.RoverOffset;
import rover.shared.practical.ScenarioOptimisations;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.ontology.OntologyConcept;

/**
 * Created by rachelcabot on 08/11/2016.
 */
public class RoverEnergy extends ABelief {
    private double energyRemaining;
    private RoverOffset roverPosition;
    private int carrying;
    private double energyPerMovementUnit;
    private double energyPerDeposit;
    private double energyPotentialOverhead;

    public RoverEnergy(double energyRemaining, int movementSpeed) {
        this.energyRemaining = energyRemaining;
        this.energyPerMovementUnit = ScenarioOptimisations.getEnergyPerMovementUnit(movementSpeed);
        this.energyPerDeposit = ScenarioOptimisations.getEnergyPerDeposit();
        this.energyPotentialOverhead = ScenarioOptimisations.getEnergyPotentialOverhead();
    }

    @Override
    public void coalesceWith(APercept p) {
        energyRemaining = p.getEnergyRemaining();
        carrying = p.getCurrentLoad();
        roverPosition = p.getMyPosition();
    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        if(getAmountOfEnergyRequiredToGetToBaseAndDeposit()>energyRemaining)
            return OntologyConcept.i_have_just_enough_energy_left_to_deposit;
        else
            return null;
    }

    private double getAmountOfEnergyRequiredToGetToBaseAndDeposit() {
        return energyPotentialOverhead+(energyPerMovementUnit*roverPosition.magnitude()+carrying*energyPerDeposit);
    }
}
