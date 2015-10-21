package fab;

import unit.*;

public class Request {
	Tool source, destination;
	int prio;
	
	public Request(Tool source_, Tool destination_, int prio_) {
		source=source_;
		destination=destination_;
		prio=prio_;
	}
	
	public Unit getSource() {return source;}
	public Unit getDestination() {return destination;}

}
