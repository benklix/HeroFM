package model;

public class WeightedDirectedGraph extends GraphMatrixRepresentation<Double> {
	
	public WeightedDirectedGraph(int n_) {
		super(n_);
	}

	@Override
	public Double noEdge() {
		return Double.POSITIVE_INFINITY;
	}
	

}
