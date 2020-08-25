package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit {

	public Ambulance(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() throws Exception {
		if (this.getTarget() instanceof ResidentialBuilding) {
			throw new IncompatibleTargetException(this, this.getTarget(),"Not a compatble target");
		}
		getTarget().getDisaster().setActive(false);

		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getBloodLoss() > 0) {
			target.setBloodLoss(target.getBloodLoss() - getTreatmentAmount());
			if (target.getBloodLoss() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getBloodLoss() == 0)

			heal();

	}

	public void respond(Rescuable r) throws Exception {
		if (r instanceof ResidentialBuilding) {
			throw new IncompatibleTargetException(this, r,"Not a compatble target");
		}
		if(((Citizen)r).getBloodLoss()==0){
			throw new CannotTreatException(this, r,"Target is safe");
		}
		if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0 && getState() == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
	}

}
