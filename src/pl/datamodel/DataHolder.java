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
					line = line.split("  ")[1];
					faces[i][0] = Integer.parseInt(line.split("\\s+")[0]);
					faces[i][1] = Integer.parseInt(line.split("\\s+")[1]);
					faces[i][2] = Integer.parseInt(line.split("\\s+")[2]);
				}
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}
		}

		for (int i = 0; i < faces.length; i++) {
			Face face = new Face(new HalfEdge(), new HalfEdge(), new HalfEdge());
			face.x.face = face;
			face.y.face = face;
			face.z.face = face;
			face.x.next = face.y;
			face.y.next = face.z;
			face.z.next = face.x;
			face.x.previous = face.z;
			face.y.previous = face.x;
			face.z.previous = face.y;
			face.x.endpoint = faces[i][1];
			face.y.endpoint = faces[i][2];
			face.z.endpoint = faces[i][0];
			hedges.addElement(face.x);
			hedges.addElement(face.y);
			hedges.addElement(face.z);
			vv[faces[i][0]].hedges.addElement(face.x);
			vv[faces[i][1]].hedges.addElement(face.y);
			vv[faces[i][2]].hedges.addElement(face.z);
		}
		for (HalfEdge hedge1 : hedges) {
			for (HalfEdge hedge2 : hedges) {
				if (hedge1.previous.endpoint == hedge2.endpoint && hedge2.previous.endpoint == hedge1.endpoint)
					hedge1.opposite = hedge2;
			}
		}

	}

}
