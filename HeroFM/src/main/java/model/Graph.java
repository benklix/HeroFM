package model;

import java.util.Iterator;

public abstract class Graph<Type> {
	protected int n;
	
	public Graph(int _n) {
		n = _n;
	}
	
	// Anzahl der Knoten
	public int getSize() {
		return n;
	}
	
	// löscht eine Kante
	public void deleteEdge(int i, int j) {
		setWeight(i, j, noEdge());
	}
	
	// löscht alle Kanten
	protected void initialize() {
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				deleteEdge(i,j);
	}
	
	// true, wenn Kante existiert
	public boolean isEdge(int i, int j) {
		return !getWeight(i, j).equals(noEdge());
	}
	
	// Wert noEdge für Nicht-Kanten
	public abstract Type noEdge();
	// liefert Wichtung einer Kante
	public abstract Type getWeight(int i, int j);
	// setzt Wichtung einer Kante
	public abstract void setWeight(int i, int j, Type w);
	//iteriert über alle Nachbarn von Knoten i
	public abstract Iterator<Integer> getNeighborIterator(int i);

}
