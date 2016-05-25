package pl.worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import pl.datamodel.DataHolder;

public class Exercise2 {

	public static void main(String[] args) {
		boolean flag;
		double dx, dy, dz, length, lenMin, lenMax, value;
		double[] point = new double [3];
		int i, j, k, multiplicity, borders = 0, vertex0, vertex1;
		List<Double> results;
		Map<Integer, Set<Integer>> edges = new HashMap<Integer, Set<Integer>>();
		
		DataHolder dataHolder = new DataHolder("resources/hammerhead_triang.off");
		// DataHolder dataHolder = new DataHolder("resources/mushroomt.off");
		// DataHolder dataHolder = new DataHolder("resources/airplane.ply");
		// DataHolder dataHolder = new DataHolder("resources/apple.ply");
		// DataHolder dataHolder = new DataHolder("resources/cow.ply");
		// DataHolder dataHolder = new DataHolder("resources/footbones.ply");
		// DataHolder dataHolder = new DataHolder("resources/galleon.ply");
		// DataHolder dataHolder = new DataHolder("resources/pump.ply");
		// DataHolder dataHolder = new DataHolder("resources/sandal.ply");
		// DataHolder dataHolder = new DataHolder("resources/stratocaster.ply");
		// DataHolder dataHolder = new DataHolder("resources/weathervane.ply");

		/*System.out.println("Tablica wierzcholkow:");
		for (i = 0; i < dataHolder.vertices.length; i++) {
			System.out.println(dataHolder.vertices[i][0] + " " + dataHolder.vertices[i][1] + " " + dataHolder.vertices[i][2]);
		}

		System.out.println("\nTablica elementow:");
		for (j = 0; j < dataHolder.faces.length; j++) {
			System.out.println(dataHolder.faces[j][0] + " " + dataHolder.faces[j][1] + " " + dataHolder.faces[j][2]);
		}*/
		
		System.out.println("\n1. Liczba wierzcholkow");
		System.out.println("Siatka zawiera " + dataHolder.vertices.length + " wierzcholkow");
		
		System.out.println("\n2. Krotnosc wierzcholkow");
		results = new ArrayList<Double>();
		for (i = 0; i < dataHolder.vertices.length; i++) {
			//System.out.print("Wierzcholek nr " + i + " krotnosc: ");
			multiplicity = 0;
			for (j = 0; j < dataHolder.faces.length; j++)
				if ((dataHolder.faces[j][0] == i) || (dataHolder.faces[j][1] == i) || (dataHolder.faces[j][2] == i))
					multiplicity++;
			results.add((double) multiplicity);
			//System.out.print(multiplicity);
		}
		PresentResults(results);
		
		System.out.println("\n3. Liczba scian");
		System.out.println("Siatka zawiera " + dataHolder.faces.length + " scian");
		
		System.out.println("\n4. Liczba brzegow");
		for (i = 0; i < dataHolder.faces.length; i++) {
			for (j = 0; j < 3; j++) {
				vertex0 = dataHolder.faces[i][j];
				vertex1 = dataHolder.faces[i][(j + 1) % 3];
				flag = true;	
				for (k = 0; k < dataHolder.faces.length; k++) {
					if ((i != k)  //czy nie porownujemy z tym samym elementem
						&& (((dataHolder.faces[k][0] == vertex0) && (dataHolder.faces[k][1] == vertex1))
							|| ((dataHolder.faces[k][1] == vertex0) && (dataHolder.faces[k][2] == vertex1))
							|| ((dataHolder.faces[k][2] == vertex0) && (dataHolder.faces[k][0] == vertex1))
							|| ((dataHolder.faces[k][0] == vertex1) && (dataHolder.faces[k][1] == vertex0))
							|| ((dataHolder.faces[k][1] == vertex1) && (dataHolder.faces[k][2] == vertex0))
							|| ((dataHolder.faces[k][2] == vertex1) && (dataHolder.faces[k][0] == vertex0)))) {
							flag = false;
							break;
					}
				}
				if (flag) borders++;
			}
		}
		System.out.println("Siatka zawiera " + borders + " brzegow");
		
		System.out.println("\n5. Liczba krawedzi");
		for (i = 0; i < dataHolder.faces.length; i++) {
			for (j = 0; j < 3; j++) {
				//min, max - aby nie dodac tej samej krawedzi dwa razy
				//raz jako AB, drugi jako BA
				vertex0 = Math.min(dataHolder.faces[i][j], dataHolder.faces[i][(j + 1) % 3]);
				vertex1 = Math.max(dataHolder.faces[i][j], dataHolder.faces[i][(j + 1) % 3]);
				if (edges.containsKey(vertex0)) {
					edges.get(vertex0).add(vertex1);
				} else {
					Set<Integer> edgesTempSet = new HashSet<Integer>();
					edgesTempSet.add(vertex1);
					edges.put(vertex0, edgesTempSet);
				}
			}
		}
		
		Iterator<Entry<Integer, Set<Integer>>> it = edges.entrySet().iterator();
		i = 0;
		while (it.hasNext()) {
		    Entry<Integer, Set<Integer>> record = it.next();
		    i += record.getValue().size();
		    //System.out.println(record.getKey() + " = " + record.getValue());
		}
		System.out.println("Siatka zawiera " + i + " krawedzi");
		
		System.out.println("\n6. Dlugosc krawedzi");
		results.removeAll(results);
		it = edges.entrySet().iterator();
		while (it.hasNext()) {
		    Entry<Integer, Set<Integer>> record = it.next();
		    vertex0 = record.getKey();
		    for(Integer W1 : record.getValue()) {
		    	dx = dataHolder.vertices[vertex0][0] - dataHolder.vertices[W1][0];
		    	dy = dataHolder.vertices[vertex0][1] - dataHolder.vertices[W1][1];
		    	dz = dataHolder.vertices[vertex0][2] - dataHolder.vertices[W1][2];
		    	length = Math.sqrt (dx * dx + dy * dy + dz * dz);		    	
		    	results.add(length);
		    	//System.out.printf("Wierzcholek0: %d, wierzcholek1: %d, dlugosc krawedzi: %.2f\n", vertex0, W1, length);
		    }
		}
		PresentResults(results);
		
		System.out.println("\n7. Aspect ratio");
		results.removeAll(results);
		for (i = 0; i < dataHolder.faces.length; i++) {
			lenMin = Double.MAX_VALUE;
		    lenMax = Double.MIN_VALUE;
			for (j = 0; j < 3; j++) {
				vertex0 = dataHolder.faces[i][j];
				vertex1 = dataHolder.faces[i][(j + 1) % 3];
				dx = dataHolder.vertices[vertex0][0] - dataHolder.vertices[vertex1][0];
		    	dy = dataHolder.vertices[vertex0][1] - dataHolder.vertices[vertex1][1];
		    	dz = dataHolder.vertices[vertex0][2] - dataHolder.vertices[vertex1][2];
		    	length = Math.sqrt (dx * dx + dy * dy + dz * dz);
		    	if (length < lenMin) lenMin = length;
		    	if (length > lenMax) lenMax = length;
			}
			if (lenMin > 0 && lenMax > 0){
				results.add(lenMax/lenMin);
				//System.out.printf("Element: %d, aspect ratio: %.2f\n", i, lenMax/lenMin);
			}
		}
		PresentResults(results);
		
		System.out.println("\n8. Katy wewnetrzne elementow");
		results.removeAll(results);
		for (i = 0; i < dataHolder.faces.length; i++) {
			value = GetAngleABC(dataHolder.vertices[dataHolder.faces[i][0]],
					dataHolder.vertices[dataHolder.faces[i][1]],
					dataHolder.vertices[dataHolder.faces[i][2]]);
			if (value > 0 && value < 180) results.add(value);
			
			value = GetAngleABC(dataHolder.vertices[dataHolder.faces[i][1]],
					dataHolder.vertices[dataHolder.faces[i][2]],
					dataHolder.vertices[dataHolder.faces[i][0]]);
			if (value > 0 && value < 180) results.add(value);
			
			value = GetAngleABC(dataHolder.vertices[dataHolder.faces[i][2]],
					dataHolder.vertices[dataHolder.faces[i][0]],
					dataHolder.vertices[dataHolder.faces[i][1]]);
			if (value > 0 && value < 180) results.add(value);
		}
		PresentResults(results);
		
		System.out.println("\n9. Katy dwuscienne elementow");
		results.removeAll(results);
		for (i = 0; i < dataHolder.faces.length; i++) {
			for (j = 0; j < 3; j++) { // 3 krawedzie w obrebie elementu
				vertex0 = dataHolder.faces[i][j];
				vertex1 = dataHolder.faces[i][(j + 1) % 3];
				for (k = 0; k < dataHolder.faces.length; k++) {
					if (i != k) {
						point[0] = (dataHolder.vertices[vertex0][0] + dataHolder.vertices[vertex1][0])/2;
						point[1] = (dataHolder.vertices[vertex0][1] + dataHolder.vertices[vertex1][1])/2;
						point[2] = (dataHolder.vertices[vertex0][2] + dataHolder.vertices[vertex1][2])/2;
						if ((vertex0 == dataHolder.faces[k][0] && vertex1 == dataHolder.faces[k][1])
								|| (vertex0 == dataHolder.faces[k][1] && vertex1 == dataHolder.faces[k][0])) {
							value = GetAngleABC(dataHolder.vertices[dataHolder.faces[i][(j + 2) % 3]],
									point, dataHolder.vertices[dataHolder.faces[k][2]]);
							if (value > 0 && value < 180) results.add(value);
						}
						
						if ((vertex0 == dataHolder.faces[k][1] && vertex1 == dataHolder.faces[k][2])
								|| (vertex0 == dataHolder.faces[k][2] && vertex1 == dataHolder.faces[k][1])) {
							value = GetAngleABC(dataHolder.vertices[dataHolder.faces[i][(j + 2) % 3]],
									point, dataHolder.vertices[dataHolder.faces[k][0]]);
							if (value > 0 && value < 180) results.add(value);
						}
						
						if ((vertex0 == dataHolder.faces[k][2] && vertex1 == dataHolder.faces[k][0])
								|| (vertex0 == dataHolder.faces[k][0] && vertex1 == dataHolder.faces[k][2])) {
							value = GetAngleABC(dataHolder.vertices[dataHolder.faces[i][(j + 2) % 3]],
									point, dataHolder.vertices[dataHolder.faces[k][1]]);
							if (value > 0 && value < 180) results.add(value);
						}
					}
				}
			}
		}
		PresentResults(results);
		
		System.out.println("\n10. Bounding box");

		double left = dataHolder.vertices[0][0];
		double right = dataHolder.vertices[0][0];
		double top = dataHolder.vertices[0][1];
		double bottom = dataHolder.vertices[0][1];
		double front = dataHolder.vertices[0][2];
		double back = dataHolder.vertices[0][2];
		
		for (i = 0; i < dataHolder.vertices.length; i++) {
			if (dataHolder.vertices[i][0] > right) right = dataHolder.vertices[i][0];
			if (dataHolder.vertices[i][0] < left) left = dataHolder.vertices[i][0];
			if (dataHolder.vertices[i][1] > top) top = dataHolder.vertices[i][1];
			if (dataHolder.vertices[i][1] < bottom) bottom = dataHolder.vertices[i][1];
			if (dataHolder.vertices[i][2] > front) front = dataHolder.vertices[i][2];
			if (dataHolder.vertices[i][2] < back) back = dataHolder.vertices[i][2];
		}
		
		//Cwiartki dla dodatnich wartosci wspolrzednej Z: 1-4, dla ujemnych: 5-8
		System.out.printf("Punkt nr 1: x = %.2f, y = %.2f, z = %.2f\n", right, top, front);
		System.out.printf("Punkt nr 2: x = %.2f, y = %.2f, z = %.2f\n", left, top, front);
		System.out.printf("Punkt nr 3: x = %.2f, y = %.2f, z = %.2f\n", left, bottom, front);
		System.out.printf("Punkt nr 4: x = %.2f, y = %.2f, z = %.2f\n", right, bottom, front);
		System.out.printf("Punkt nr 5: x = %.2f, y = %.2f, z = %.2f\n", right, top, back);
		System.out.printf("Punkt nr 6: x = %.2f, y = %.2f, z = %.2f\n", left, top, back);
		System.out.printf("Punkt nr 7: x = %.2f, y = %.2f, z = %.2f\n", left, bottom, back);
		System.out.printf("Punkt nr 8: x = %.2f, y = %.2f, z = %.2f\n", right, bottom, back);
		
		//System.out.println("\n11. Liczba skladowych");
		//System.out.println("\n12. Genus");
	}
	
	public static void PresentResults(List<Double> wyniki) {
		double AVG = 0;
		int ltAVG = 0;
		
		for (Double s : wyniki) AVG += s;
		AVG /= wyniki.size();
		for (Double s : wyniki) if (s < AVG) ltAVG++;
		Collections.sort(wyniki);
		System.out.printf("Minimum: %.2f\n", wyniki.get(0));
		System.out.printf("Maksimum: %.2f\n", wyniki.get(wyniki.size()-1));
		System.out.printf("Srednia: %.2f\n", AVG);
		System.out.println("Ilosc elementow ponizej sredniej: " + ltAVG);
		System.out.println("Ilosc elementow powyzej sredniej: " + (wyniki.size() - ltAVG));
	}
	
	public static double GetAngleABC(double a[], double b[], double c[]) {
		double[] ab = new double[3];
		ab[0] = b[0] - a[0];
		ab[1] = b[1] - a[1];
		ab[2] = b[2] - a[2];
		
		double[] bc = new double[3];
		bc[0] = c[0] - b[0];
		bc[1] = c[1] - b[1];
		bc[2] = c[2] - b[2];
		
	    double abVec = Math.sqrt(ab[0] * ab[0] + ab[1] * ab[1] + ab[2] * ab[2]);
	    double bcVec = Math.sqrt(bc[0] * bc[0] + bc[1] * bc[1] + bc[2] * bc[2]);

	    double[] abNorm = new double[3];
	    abNorm[0] = ab[0] / abVec;
	    abNorm[1] = ab[1] / abVec;
	    abNorm[2] = ab[2] / abVec;
		
		double[] bcNorm = new double[3];
		bcNorm[0] = bc[0] / bcVec;
		bcNorm[1] = bc[1] / bcVec;
		bcNorm[2] = bc[2] / bcVec;
		
	    double res = abNorm[0] * bcNorm[0] + abNorm[1] * bcNorm[1] + abNorm[2] * bcNorm[2];

	    return Math.acos(res) * 180.0 / 3.141592653589793;
	}
}