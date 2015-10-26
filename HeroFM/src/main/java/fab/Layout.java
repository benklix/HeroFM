package fab;

import java.util.ArrayList;

import model.WeightedUndirectedGraph;
import unit.*;

public class Layout {
	
	ArrayList<Unit> toolList = new ArrayList<Unit>();
	
	Tool tool1 = new Tool("1-FOT", 0, new Position(10,10));
	Tool tool2 = new Tool("2-FOT", 1, new Position(40,10));
	
	Tool tool3 = new Tool("3-FOT", 2, new Position(100,10));
	Tool tool4 = new Tool("4-ETCH", 3, new Position(100,40));
	
	Tool tool5 = new Tool("5-ETCH", 4, new Position(40,40));
	Tool tool6 = new Tool("6-BUF", 5, new Position(10,40));
	
	Tool tool7 = new Tool("7-BUF", 6, new Position(10,100));
	Tool tool8 = new Tool("8-WASH", 7, new Position(70,100));
	
	Tool tool9 = new Tool("9-MEAS", 8, new Position(100,100));
		
	WeightedUndirectedGraph g;

	public Layout() {
		
		fillToolList();
		g = new WeightedUndirectedGraph(toolList.size());
		// TODO create the graph automatically
		for(int i=0; i<toolList.size(); i++){
			for(int j=0; j<toolList.size(); j++){
				g.setWeight(toolList.get(i).getIndex(), toolList.get(j).getIndex(), getDistance(toolList.get(i),toolList.get(j)));
			}
		}

	}

	private void fillToolList() {
		toolList.add(tool1);
		toolList.add(tool2);
		toolList.add(tool3);
		toolList.add(tool4);
		toolList.add(tool5);
		toolList.add(tool6);
		toolList.add(tool7);
		toolList.add(tool8);
		toolList.add(tool9);
	}

	public Double getDistance(Unit source, Unit destination) {
		double distX = destination.getPos().getX()-source.getPos().getX();
		double distY = destination.getPos().getY()-source.getPos().getY();
		return Math.sqrt(Math.pow(distX, 2)+Math.pow(distY, 2));
	}
	
	public void printLayout(Layout fab) {
		for(int i=0; i<g.getSize(); i++){
			for(int j=0; j<g.getSize(); j++){
				System.out.println("Kante("+i+","+j+"): "+g.getWeight(toolList.get(i).getIndex(), toolList.get(j).getIndex()));
			}
		}
	}
	
	public Double getToolCoordX(int toolID) {
		return this.toolList.get(toolID).getPos().getX();
	}

	public double getToolCoordY(int toolID) {
		return this.toolList.get(toolID).getPos().getY();
	}

	public Unit getToolAt(int i) {

		return toolList.get(i);
	}
}
