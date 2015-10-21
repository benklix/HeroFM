/**
 * 
 */
package model;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Benjamin Pietke
 *
 */
public abstract class GraphMatrixRepresentation<Type> extends Graph<Type> {
	private ArrayList<ArrayList<Type>> a;
	
	public GraphMatrixRepresentation(int _n) {
		super(_n);
		//Darstellung des Graphen als Matrix durch ArrayList von ArrayLists
		a = new ArrayList<ArrayList<Type>>();
		for(int i=0; i<n; i++){
			a.add(new ArrayList<Type>());
			for(int j=0; j<n; j++){
				a.get(i).add(noEdge());
			}
		}
	}
	@Override
	public Type getWeight(int i, int j) {
		return a.get(i).get(j);
	}
	@Override
	public void setWeight(int i, int j, Type w) {
		a.get(i).set(j,  w);
	}
	@Override
	public Iterator<Integer> getNeighborIterator(int i) {
		return new NeighborIterator(this, i);
	}

}
