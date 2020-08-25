package model.units;

import exceptions.CannotTreatException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;

public class FireTruck extends FireUnit {
	public FireTruck(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() throws CannotTreatException {
		
		getTarget().getDisaster().setActive(false);
		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		} else if (target.getFireDamage() > 0)
			target.setFireDamage(target.getFireDamage() - 10);
		if (target.getFireDamage() == 0)
			jobsDone();
	}
}
