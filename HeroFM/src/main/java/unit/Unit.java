package unit;

public abstract class Unit {
	String toolName;
	int k;
	Position pos;
	
	public Unit(String id_, int k_, Position pos_){
		toolName=id_;
		k=k_;
		pos = pos_;
	}
	
	public Position getPos() {return pos;}
	public int getIndex() {return k;}
	public String getName() {return toolName;}

}
