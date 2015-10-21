package fab;

import unit.*;

public class Request {
	Unit source, destination;
	int prio;
	
	public Request(Unit unit, Unit unit2, int prio_) {
		source=unit;
		destination=unit2;
		prio=prio_;
	}
	
	public Unit getSource() {return source;}
	public Unit getDestination() {return destination;}

}
