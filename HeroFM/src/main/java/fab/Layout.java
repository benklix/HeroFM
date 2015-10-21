package fab;

import java.util.ArrayList;

import model.WeightedUndirectedGraph;
import unit.*;

public class Layout {
	
	ArrayList<Unit> units = new ArrayList<Unit>();
	
	
	Tool tool1 = new Tool("Tool-1", 0, new Position(0,3));
	Tool tool2 = new Tool("Tool-2", 1, new Position(3,3));
	Tool tool3 = new Tool("Tool-3", 2, new Position(4,2));
	Tool tool4 = new Tool("Tool-4", 3, new Position(4,0));
	Tool tool5 = new Tool("Tool-5", 4, new Position(3,0));
	Tool tool6 = new Tool("Tool-6", 5, new Position(2,0));
	Tool tool7 = new Tool("Tool-7", 6, new Position(0,0));
	Tool tool8 = new Tool("Tool-8", 7, new Position(0,1));
	
	Vehicle hero1 = new Vehicle("Hero-1", 1, tool5.getPos());
	Vehicle hero2 = new Vehicle("Hero-2", 2, tool8.getPos());
	
	WeightedUndirectedGraph g;

	public Layout() {
//		units.add(hero1);
//		units.add(hero2);
		units.add(tool1);
		units.add(tool2);
		units.add(tool3);
		units.add(tool4);
		units.add(tool5);
		units.add(tool6);
		units.add(tool7);
		units.add(tool8);

		g = new WeightedUndirectedGraph(units.size());
		// TODO create the graph automatically
		for(int i=0; i<units.size(); i++){
			for(int j=0; j<units.size(); j++){
				g.setWeight(units.get(i).getIndex(), units.get(j).getIndex(), getDistance(units.get(i),units.get(j)));
			}
		}

	}

	protected Double getDistance(Unit source, Unit destination) {
		double distX = destination.getPos().getX()-source.getPos().getX();
		double distY = destination.getPos().getY()-source.getPos().getY();
		return Math.sqrt(Math.pow(distX, 2)+Math.pow(distY, 2));
	}
	
	protected void printLayout(Layout fab) {
		for(int i=0; i<g.getSize(); i++){
			for(int j=0; j<g.getSize(); j++){
				System.out.println("Kante("+i+","+j+"): "+g.getWeight(units.get(i).getIndex(), units.get(j).getIndex()));
			}
		}
	}
}
