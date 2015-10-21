package unit;

public class Vehicle extends Unit {
	
	int c; // available capacity
	int u; // used capacity
	
	public Vehicle(String name_, int k_, Position pos_) {
		super(name_, k_, pos_);
		c=4;
		u=0;
	}
	
	protected void setPos(Position pos_) {
		pos = pos_;
	}
}
