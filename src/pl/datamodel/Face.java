package pl.datamodel;

public class Face {
	private int x;
	private int y;
	private int z;
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public Face(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
