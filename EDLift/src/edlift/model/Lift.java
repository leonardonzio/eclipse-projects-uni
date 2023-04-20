package edlift.model;

public abstract class Lift {
	
	private int currentFloor, requestedFloor;
	private final int minFloor, maxFloor;
	private LiftState currentState;
	private final double speed;
		
	protected Lift(int minFloor, int maxFloor, int initialFloor, double speed) {
		
		if (minFloor >= maxFloor)
			throw new IllegalArgumentException("min floor >= max floor");
		if (initialFloor < minFloor)
			throw new IllegalArgumentException("current floor < min floor");
		if (maxFloor < initialFloor)
			throw new IllegalArgumentException("current floor > max floor");
		if (speed <= 0)
			throw new IllegalArgumentException("speed must be positive");
		
		this.maxFloor = maxFloor;
		this.minFloor = minFloor;
		this.currentFloor = initialFloor;
		this.requestedFloor = initialFloor;
		this.currentState = LiftState.REST;
		this.speed = speed;
	}
	
	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int current) {
		if (current != this.currentFloor)
		{
			this.currentFloor = current;
		}
	}

	public int getMinFloor() {
		return minFloor;
	}

	public int getMaxFloor() {
		return maxFloor;
	}
	
	public double getSpeed() {
		return speed;
	}

	public LiftState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(LiftState newState) {
		if (this.currentState != newState) {
			this.currentState = newState;
		}
	}
			
	public int nextPendingFloor(LiftState state) {
		// per multipiano
		return Integer.MIN_VALUE;
	}

	public boolean hasPendingFloors() {
		// per multipiano
		return false;
	}

	
	public abstract String getMode();
	
	// riceve un intero e restituisce una request-result
	public abstract RequestResult goToFloor(int floor);

	
	public void setRequestedFloor(int floor) {
		this.requestedFloor = floor;
	}
	
	public int getRequestedFloor() {
		return this.requestedFloor;
	}
	
	@Override
	public String toString() {
		return getMode() + " lift serving floor " + minFloor + " to " + maxFloor ; 
	}

	public static Lift of(String mode, int minFloor, int maxFloor, double speed) {
		int initialFloor = (minFloor <= 0 && maxFloor >= 1) ? 0 : minFloor;
		
		return switch(mode) {
			case "BASIC"   		-> new BasicLift(minFloor, maxFloor, initialFloor, speed); 
			case "HEALTHY" 		-> new HealthyLift(minFloor, maxFloor, initialFloor, speed);
			case "MULTIFLOOR"	-> new MultiFloorLift(minFloor, maxFloor, initialFloor, speed);
			
			default        -> throw new IllegalArgumentException("Unsupported lift mode");
		};
	}
	
	protected void checkArrivalFloor(int floor) {
		if (floor > getMaxFloor() || floor < getMinFloor()) 
			throw new IllegalArgumentException("Arrival floor out of range");
	}
}
