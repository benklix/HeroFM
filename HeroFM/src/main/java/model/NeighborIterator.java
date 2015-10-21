package model;
import java.util.Iterator;

public class NeighborIterator implements Iterator<Integer> {
	
	private Graph<?> g;
	private int i, j;
	
	public NeighborIterator(Graph<?> g_, int i_) {
		g=g_;
		i=i_;
		j=0;
		tryNext();
	}
	
	public boolean hasNext() {
		return j<g.getSize();
	}

	public Integer next() {
		int k=j;
		j++;
		tryNext();
		return k;
	}
	
	private void tryNext() {
		while(j<g.getSize())
			if(g.isEdge(i, j))
				return;
			else
				j++;
	}

}
