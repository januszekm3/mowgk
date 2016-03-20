package pl.datamodel;

import java.util.Vector;

public class Vertice {
	public Vector<HalfEdge> hedges;
	public int id;
	
	public Vertice(int id) {
		this.id = id;
		this.hedges = new Vector<>();
	}
}