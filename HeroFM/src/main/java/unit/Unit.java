package unit;

public abstract class Unit {
	String id;
	int k;
	Position pos;
	
	public Unit(String id_, int k_, Position pos_){
		id=id_;
		k=k_;
		pos = pos_;
	}
	
	public Position getPos() {return pos;}
	public int getIndex() {return k;}

}
