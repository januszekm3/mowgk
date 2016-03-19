package pl.worker;

import pl.datamodel.DataHolder;

public class Worker {

	public static void main(String[] args) {
		DataHolder dataHolder = new DataHolder(args[0]);
		for (int i = 0; i < dataHolder.vertices.length; i ++) {
			System.out.println(dataHolder.vertices[i][0] + " " + dataHolder.vertices[i][1] + " " + dataHolder.vertices[i][2]);
		}
		for (int i = 0; i < dataHolder.faces.length; i ++) {
			System.out.println(dataHolder.faces[i][0] + " " + dataHolder.faces[i][1] + " " + dataHolder.faces[i][2]);
		}
	}
}