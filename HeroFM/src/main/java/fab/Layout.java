package fab;

import java.util.ArrayList;

import model.WeightedUndirectedGraph;
import unit.*;

public class Layout {
	
	ArrayList<Unit> toolList = new ArrayList<Unit>();
	
	Tool tool1 = new Tool("1-FOT", 0, new Position(10,10));
	Tool tool2 = new Tool("2-FOT", 1, new Position(40,10));
	
	Tool tool3 = new Tool("3-FOT", 2, new Position(100,10));
	Tool tool4 = new Tool("4-ETC", 3, new Position(100,40));
	
	Tool tool5 = new Tool("5-ETC", 4, new Position(40,40));
	Tool tool6 = new Tool("6-BUF", 5, new Position(10,40));
	
	Tool tool7 = new Tool("7-BUF", 6, new Position(10,100));
	Tool tool8 = new Tool("8-WAS", 7, new Position(70,100));
	
	Tool tool9 = new Tool("9-MEA", 8, new Position(100,100));
	Tool tool10 = new Tool("10-MEA", 9, new Position(200,100));
	
	Tool tool11 = new Tool("11-MEA", 10, new Position(200,200));
	Tool tool12 = new Tool("12-MEA", 11, new Position(200,400));
	
	Tool tool13 = new Tool("13-MEA", 12, new Position(400,700));
	Tool tool14 = new Tool("14-MEA", 13, new Position(100,200));
		
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
		toolList.add(tool10);
		toolList.add(tool11);
		toolList.add(tool12);
		toolList.add(tool13);
		toolList.add(tool14);
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
