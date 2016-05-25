package pl.datamodel;

public class Face {
	public int id;
	public HalfEdge x;
	public HalfEdge y;
	public HalfEdge z;
	
	public Face(HalfEdge x, HalfEdge y, HalfEdge z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
