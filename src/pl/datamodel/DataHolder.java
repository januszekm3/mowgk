package pl.datamodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class DataHolder {
	public double[][] vertices;
	public int[][] faces;
	public Vector<HalfEdge> hedges = new Vector<>();
	public Vertice[] vv;
	public Face[] ff;

	private boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public DataHolder(String filename) {
		Path file = Paths.get(filename);
		// vertices = new Vector<>();
		// faces = new Vector<>();
		Charset charset = Charset.forName("US-ASCII");

		if (filename.endsWith(".ply")) {
			try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
				String line = reader.readLine();
				while (!line.startsWith("element vertex "))
					line = reader.readLine();
				int v = Integer.parseInt(line.split("element vertex ")[1].split(" ")[0]);
				while (!line.startsWith("element face "))
					line = reader.readLine();
				int f = Integer.parseInt(line.split("element face ")[1].split(" ")[0]);
				while (!isNumeric(line.split(" ")[0]))
					line = reader.readLine();
				vertices = new double[v][3];
				vv = new Vertice[v];
				faces = new int[f][3];
				ff = new Face[f];
				for (int i = 0; i < v; i++) {
					vertices[i][0] = Float.parseFloat(line.split("\\s+")[0]);
					vertices[i][1] = Float.parseFloat(line.split("\\s+")[1]);
					vertices[i][2] = Float.parseFloat(line.split("\\s+")[2]);
					vv[i]=new Vertice(i);
					line = reader.readLine();
				}
				for (int i = 0; i < f; i++) {
					faces[i][0] = Integer.parseInt(line.split("\\s+")[1]);
					faces[i][1] = Integer.parseInt(line.split("\\s+")[2]);
					faces[i][2] = Integer.parseInt(line.split("\\s+")[3]);
					line = reader.readLine();
				}
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}
		} else if (filename.endsWith(".off")) {
			try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
				String line = reader.readLine();
				while (!isNumeric(line.split(" ")[0]))
					line = reader.readLine();
				int v = Integer.parseInt(line.split(" ")[0]);
				int f = Integer.parseInt(line.split(" ")[1]);
				int e = Integer.parseInt(line.split(" ")[2]);
				vertices = new double[v][3];
				vv = new Vertice[v];
				faces = new int[f][3];
				ff = new Face[f];
				for (int i = 0; i < v; i++) {
					line = reader.readLine();
					if (line.startsWith(" ")) {
						vertices[i][0] = Float.parseFloat(line.split("\\s+")[1]);
						vertices[i][1] = Float.parseFloat(line.split("\\s+")[2]);
						vertices[i][2] = Float.parseFloat(line.split("\\s+")[3]);
					} else {
						vertices[i][0] = Float.parseFloat(line.split("\\s+")[0]);
						vertices[i][1] = Float.parseFloat(line.split("\\s+")[1]);
						vertices[i][2] = Float.parseFloat(line.split("\\s+")[2]);
					}
					vv[i]=new Vertice(i);
				}
				for (int i = 0; i < f; i++) {
					line = reader.readLine();
					faces[i][0] = Integer.parseInt(line.split("\\s+")[1]);
					faces[i][1] = Integer.parseInt(line.split("\\s+")[2]);
					faces[i][2] = Integer.parseInt(line.split("\\s+")[3]);
				}
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}
		}

		for (int i = 0; i < faces.length; i++) {
			ff[i] = new Face(new HalfEdge(), new HalfEdge(), new HalfEdge());
			ff[i].id = i;
			ff[i].x.face = ff[i];
			ff[i].y.face = ff[i];
			ff[i].z.face = ff[i];
			ff[i].x.next = ff[i].y;
			ff[i].y.next = ff[i].z;
			ff[i].z.next = ff[i].x;
			ff[i].x.previous = ff[i].z;
			ff[i].y.previous = ff[i].x;
			ff[i].z.previous = ff[i].y;
			ff[i].x.endpoint = faces[i][1];
			ff[i].y.endpoint = faces[i][2];
			ff[i].z.endpoint = faces[i][0];
			hedges.addElement(ff[i].x);
			hedges.addElement(ff[i].y);
			hedges.addElement(ff[i].z);
			vv[faces[i][0]].hedges.addElement(ff[i].x);
			vv[faces[i][1]].hedges.addElement(ff[i].y);
			vv[faces[i][2]].hedges.addElement(ff[i].z);
		}
		for (HalfEdge hedge1 : hedges) {
			for (HalfEdge hedge2 : hedges) {
				if (hedge1.previous.endpoint == hedge2.endpoint && hedge2.previous.endpoint == hedge1.endpoint)
					hedge1.opposite = hedge2;
			}
		}
	}

}
