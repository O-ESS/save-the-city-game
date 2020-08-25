package model.units;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;

public class GasControlUnit extends FireUnit {

	public GasControlUnit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	public void treat() {
		getTarget().getDisaster().setActive(false);

		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		} else if (target.getGasLevel() > 0) {
			target.setGasLevel(target.getGasLevel() - 10);
			for(Citizen c: target.getOccupants()){
				c.setOxygenLevel(c.getOxygenLevel()+15);
			}
			}

		if (target.getGasLevel() == 0)
			jobsDone();

	}

}
