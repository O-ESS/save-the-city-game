package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;


public class GasLeak extends Disaster {

	public GasLeak(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
	}
	
	@Override
	public void strike() throws Exception 
	{
		if(((ResidentialBuilding) this.getTarget()).getStructuralIntegrity()==0){
			throw new BuildingAlreadyCollapsedException(this,"The building has collabsed");
		}
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setGasLevel(target.getGasLevel()+10);
		for(Citizen c: target.getOccupants()){
			c.setOxygenLevel(c.getOxygenLevel()-15);
		}
		super.strike();
	}
	@Override
	public void cycleStep() {
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setGasLevel(target.getGasLevel()+15);
		for(Citizen c: target.getOccupants()){
			c.setOxygenLevel(c.getOxygenLevel()-15);
		}
		
	}

}
