package model;

public class WeightedUndirectedGraph extends WeightedDirectedGraph {

	public WeightedUndirectedGraph(int n_) {
		super(n_);
	}
	
	@Override
	public void setWeight(int i, int j, Double w) {
		super.setWeight(i, j, w);
		super.setWeight(j, i, w);
	}

}
