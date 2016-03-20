package pl.datamodel;

public class HalfEdge {
	public HalfEdge opposite;
	public HalfEdge previous;
	public HalfEdge next;
	public int endpoint;
	public Face face;

	public String getPrintableHalfEdge() {
		return Integer.toString(previous.endpoint) + ":" + Integer.toString(endpoint) + " previous:"
				+ Integer.toString(previous.previous.endpoint) + ":" + Integer.toString(previous.endpoint) + " next:"
				+ Integer.toString(endpoint) + ":" + Integer.toString(next.endpoint);
	}
}
