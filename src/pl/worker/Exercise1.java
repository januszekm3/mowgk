package pl.worker;

import java.util.HashSet;
import java.util.Set;

import pl.datamodel.DataHolder;
import pl.datamodel.Face;
import pl.datamodel.HalfEdge;
import pl.datamodel.Vertice;

public class Exercise1 {

	public static void main(String[] args) {
		boolean flag;
		int i, j, k, component0, component1, vertex0, vertex1, mutualVertex0, mutualVertex1, aloneVertex0, aloneVertex1;
		long start, stop;
		Set<Integer> neighbors0, neighbors1;
		DataHolder dataHolder = new DataHolder("resources/ant.ply");
		// DataHolder dataHolder = new DataHolder("resources/cam.off");
		// DataHolder dataHolder = new DataHolder("resources/elk.off");
		// DataHolder dataHolder = new DataHolder("resources/face-HY.off");
		// DataHolder dataHolder = new DataHolder("resources/neptune.off");
		// DataHolder dataHolder = new DataHolder("resources/twirl.off");
		// DataHolder dataHolder = new DataHolder("resources/vase-lion.off");

		System.out.println("Tablica wierzcholkow:");
		for (i = 0; i < dataHolder.vertices.length; i++) {
			System.out.println(dataHolder.vertices[i][0] + " " + dataHolder.vertices[i][1] + " " + dataHolder.vertices[i][2]);
		}

		System.out.println("\nTablica elementow:");
		for (j = 0; j < dataHolder.faces.length; j++) {
			System.out.println(dataHolder.faces[j][0] + " " + dataHolder.faces[j][1] + " " + dataHolder.faces[j][2]);
		}
		System.out.println("\n3.1. Tablica wspolrzednych wierzcholkow oraz tzw. tablica polaczen");
		System.out.println("\n3.1.1. dla kazdego wierzcholka wyznaczanie otoczenia wierzcholkow (pierwsza i druga warstwa sasiednich wierzcholkow)");
		start = System.nanoTime();
		for (i = 0; i < dataHolder.vertices.length; i++) {
			System.out.print("Wierzcholek nr " + i + " pierwsi sasiedzi: ");
			neighbors0 = new HashSet<Integer>();
			neighbors1 = new HashSet<Integer>();
			for (j = 0; j < dataHolder.faces.length; j++) {
				if (dataHolder.faces[j][0] == i) {
					neighbors0.add(dataHolder.faces[j][1]); // dodawanie
					neighbors0.add(dataHolder.faces[j][2]); // obu sasiadow
				}
				if (dataHolder.faces[j][1] == i) {
					neighbors0.add(dataHolder.faces[j][0]);
					neighbors0.add(dataHolder.faces[j][2]);
				}
				if (dataHolder.faces[j][2] == i) {
					neighbors0.add(dataHolder.faces[j][0]);
					neighbors0.add(dataHolder.faces[j][1]);
				}
			}
			for (Integer a : neighbors0) {
				System.out.print(a + " ");
				for (j = 0; j < dataHolder.faces.length; j++) {
					if (dataHolder.faces[j][0] == a) {
						// czy nie dodajemy tego samego wierzcholka jako
						// drugiego sasiada
						if (dataHolder.faces[j][1] != i)
							neighbors1.add(dataHolder.faces[j][1]);
						if (dataHolder.faces[j][2] != i)
							neighbors1.add(dataHolder.faces[j][2]);
					}
					if (dataHolder.faces[j][1] == a) {
						if (dataHolder.faces[j][0] != i)
							neighbors1.add(dataHolder.faces[j][0]);
						if (dataHolder.faces[j][2] != i)
							neighbors1.add(dataHolder.faces[j][2]);
					}
					if (dataHolder.faces[j][2] == a) {
						if (dataHolder.faces[j][0] != i)
							neighbors1.add(dataHolder.faces[j][0]);
						if (dataHolder.faces[j][1] != i)
							neighbors1.add(dataHolder.faces[j][1]);
					}
				}
			}
			System.out.print("drudzy sasiedzi: ");
			for (Integer a : neighbors1) {
				System.out.print(a + " ");
			}
			System.out.println();
		}

		stop = System.nanoTime();
		System.out.println("3.1.1. Czas wykonania: " + (stop - start));

		System.out.println("\n3.1.2. dla kazdego wierzcholka znalezienie elementow, do ktorych nalezy");
		start = System.nanoTime();
		for (i = 0; i < dataHolder.vertices.length; i++) {
			System.out.print("Wierzcholek nr " + i + " nalezy do elementow: ");
			for (j = 0; j < dataHolder.faces.length; j++)
				if (dataHolder.faces[j][0] == i || dataHolder.faces[j][1] == i || dataHolder.faces[j][2] == i)
					System.out.print(j + " ");
			System.out.println();
		}
		stop = System.nanoTime();
		System.out.println("3.1.2. Czas wykonania: " + (stop - start));

		System.out.println("\n3.1.3. dla kazdego elementu wyznaczenie otoczenia elementow (pierwsza i druga warstwa sasiednich elementow)");
		start = System.nanoTime();
		for (i = 0; i < dataHolder.faces.length; i++) {
			System.out.print("Element nr " + i + " pierwsi sasiedzi: ");
			neighbors0 = new HashSet<Integer>();
			neighbors1 = new HashSet<Integer>();
			for (j = 0; j < dataHolder.faces.length; j++) {
				if ((i != j) // czy nie dodajemy tego samego elementu
						// (przechodzimy drugi raz po tej samej tablicy
						&& (dataHolder.faces[i][0] == dataHolder.faces[j][0]
						|| dataHolder.faces[i][0] == dataHolder.faces[j][1]
						|| dataHolder.faces[i][0] == dataHolder.faces[j][2]						
						|| dataHolder.faces[i][1] == dataHolder.faces[j][0]
						|| dataHolder.faces[i][1] == dataHolder.faces[j][1]
						|| dataHolder.faces[i][1] == dataHolder.faces[j][2]
						|| dataHolder.faces[i][2] == dataHolder.faces[j][0]
						|| dataHolder.faces[i][2] == dataHolder.faces[j][1]
						|| dataHolder.faces[i][2] == dataHolder.faces[j][2]))
					neighbors0.add(j);
			}
			for (Integer a : neighbors0) {
				System.out.print(a + " ");
				for (j = 0; j < dataHolder.faces.length; j++) {
					if ((a != j) // czy nie dodajemy tego samego elementu
							// (przechodzimy drugi raz po tej samej tablicy
							&& (dataHolder.faces[a][0] == dataHolder.faces[j][0]
							|| dataHolder.faces[a][0] == dataHolder.faces[j][1]
							|| dataHolder.faces[a][0] == dataHolder.faces[j][2]						
							|| dataHolder.faces[a][1] == dataHolder.faces[j][0]
							|| dataHolder.faces[a][1] == dataHolder.faces[j][1]
							|| dataHolder.faces[a][1] == dataHolder.faces[j][2]
							|| dataHolder.faces[a][2] == dataHolder.faces[j][0]
							|| dataHolder.faces[a][2] == dataHolder.faces[j][1]
							|| dataHolder.faces[a][2] == dataHolder.faces[j][2]))
						neighbors1.add(j);
				}
			}
			System.out.print("drudzy sasiedzi: ");
			for (Integer a : neighbors1) {
				System.out.print(a + " ");
			}
			System.out.println();
		}
		stop = System.nanoTime();
		System.out.println("3.1.3. Czas wykonania: " + (stop - start));

		System.out.println("\n3.1.4. zamiana krawedzi dla wskazanej pary przyleglych trojkatow wraz z odpowiednia zmiana struktury danych");
		start = System.nanoTime();
		mutualVertex0 = -1;
		mutualVertex1 = -1;
		component0 = -1;
		component1 = -1;
		aloneVertex0 = -1;
		aloneVertex1 = -1;
		i = 0;
		while (i < dataHolder.faces.length && component0 == -1) {
			j = 0;
			while (j < 3 && component0 == -1) { // 3 krawedzie w obrebie elementu
				vertex0 = dataHolder.faces[i][j];
				vertex1 = dataHolder.faces[i][(j + 1) % 3];
				k = 0;
				while (k < dataHolder.faces.length && component0 == -1) {
					if (i != k)
						if (vertex0 == dataHolder.faces[k][0] && vertex1 == dataHolder.faces[k][1]) {
							component0 = i;
							component1 = k;
							mutualVertex0 = dataHolder.faces[i][j];
							mutualVertex1 = dataHolder.faces[i][(j + 1) % 3];
							aloneVertex0 = dataHolder.faces[i][(j + 2) % 3];
							aloneVertex1 = dataHolder.faces[k][2];
						} else if (vertex0 == dataHolder.faces[k][1] && vertex1 == dataHolder.faces[k][0]) {
							component0 = i;
							component1 = k;
							mutualVertex0 = dataHolder.faces[i][j];
							mutualVertex1 = dataHolder.faces[i][(j + 1) % 3];
							aloneVertex0 = dataHolder.faces[i][(j + 2) % 3];
							aloneVertex1 = dataHolder.faces[k][2];
						} else if (vertex0 == dataHolder.faces[k][1] && vertex1 == dataHolder.faces[k][2]) {
							component0 = i;
							component1 = k;
							mutualVertex0 = dataHolder.faces[i][j];
							mutualVertex1 = dataHolder.faces[i][(j + 1) % 3];
							aloneVertex0 = dataHolder.faces[i][(j + 2) % 3];
							aloneVertex1 = dataHolder.faces[k][0];
						} else if (vertex0 == dataHolder.faces[k][2] && vertex1 == dataHolder.faces[k][1]) {
							component0 = i;
							component1 = k;
							mutualVertex0 = dataHolder.faces[i][j];
							mutualVertex1 = dataHolder.faces[i][(j + 1) % 3];
							aloneVertex0 = dataHolder.faces[i][(j + 2) % 3];
							aloneVertex1 = dataHolder.faces[k][0];
						} else if (vertex0 == dataHolder.faces[k][0] && vertex1 == dataHolder.faces[k][2]) {
							component0 = i;
							component1 = k;
							mutualVertex0 = dataHolder.faces[i][j];
							mutualVertex1 = dataHolder.faces[i][(j + 1) % 3];
							aloneVertex0 = dataHolder.faces[i][(j + 2) % 3];
							aloneVertex1 = dataHolder.faces[k][1];
						} else if (vertex0 == dataHolder.faces[k][2] && vertex1 == dataHolder.faces[k][0]) {
							component0 = i;
							component1 = k;
							mutualVertex0 = dataHolder.faces[i][j];
							mutualVertex1 = dataHolder.faces[i][(j + 1) % 3];
							aloneVertex0 = dataHolder.faces[i][(j + 2) % 3];
							aloneVertex1 = dataHolder.faces[k][1];
						}
					k++;
				}
				j++;
			}
			i++;
		}

		if (component0 != -1 && component1 != -1 && aloneVertex0 != -1 && aloneVertex1 != -1
				&& mutualVertex0 != -1 && mutualVertex1 != -1) {
			System.out.println("Element0: " + component0);
			System.out.println("Element1: " + component1);
			System.out.println("Samotny wierzcholek0: " + aloneVertex0);
			System.out.println("Samotny wierzcholek1: " + aloneVertex1);
			System.out.println("Wspolny wierzcholek0: " + mutualVertex0);
			System.out.println("Wspolny wierzcholek0: " + mutualVertex1);

			dataHolder.faces[component0][0] = aloneVertex0;
			dataHolder.faces[component0][1] = aloneVertex1;
			dataHolder.faces[component0][2] = mutualVertex0;
			dataHolder.faces[component1][0] = aloneVertex0;
			dataHolder.faces[component1][1] = aloneVertex1;
			dataHolder.faces[component1][2] = mutualVertex1;

			System.out.println("\nTablica elementow po zamianie krawedzi:");
			for (j = 0; j < dataHolder.faces.length; j++) {
				System.out.println(dataHolder.faces[j][0] + " " + dataHolder.faces[j][1] + " " + dataHolder.faces[j][2]);
			}
			stop = System.nanoTime();
			System.out.println("3.1.4. Czas wykonania: " + (stop - start));
		} else {
			System.out.println("Brak przyleglych trojkatow.");
		}

		System.out.println("\n3.1.5. okreslenie, czy dana siatka posiada brzeg");
		start = System.nanoTime();
		flag = false;
		i = 0;
		j = 0;
		k = 0;
		while (i < dataHolder.faces.length && !flag) {
			while (j < 3 && !flag) { // 3 krawedzie w obrebie elementu
				vertex0 = dataHolder.faces[i][j];
				vertex1 = dataHolder.faces[i][(j + 1) % 3];
				while (k < dataHolder.faces.length && !flag) {
					if (i != k) // czy nie porownujemy z tym samym elementem
						if (!(((vertex0 == dataHolder.faces[k][0] && vertex1 == dataHolder.faces[k][1])
								|| (vertex0 == dataHolder.faces[k][1] && vertex1 == dataHolder.faces[k][0]))
								|| ((vertex0 == dataHolder.faces[k][1] && vertex1 == dataHolder.faces[k][2])
										|| (vertex0 == dataHolder.faces[k][2]
												&& vertex1 == dataHolder.faces[k][1]))
								|| ((vertex0 == dataHolder.faces[k][0] && vertex1 == dataHolder.faces[k][2])
										|| (vertex0 == dataHolder.faces[k][2]
												&& vertex1 == dataHolder.faces[k][0])))) {
							flag = true;
						}
					k++;
				}
				j++;
			}
			i++;
		}
		if (flag)
			System.out.println("Siatka posiada brzeg");
		else
			System.out.println("Siatka nie posiada brzegu");
		stop = System.nanoTime();
		System.out.println("3.1.5. Czas wykonania: " + (stop - start));

		System.out.println("\n3.2. half edge");
		System.out.println("\n3.2.1. dla kazdego wierzcholka wyznaczanie otoczenia wierzcholkow (pierwsza i druga warstwa sasiednich wierzcholkow)");
		start = System.nanoTime();
		for (Vertice v : dataHolder.vv) {
			neighbors0 = new HashSet<Integer>();
			neighbors1 = new HashSet<Integer>();
			System.out.print("Wierzcholek nr " + v.id + " pierwsi sasiedzi: ");
			for (HalfEdge edge : dataHolder.hedges) {
				if (edge.endpoint == v.id) {
					neighbors0.add(edge.previous.endpoint);
					neighbors0.add(edge.next.endpoint);
				}
			}
			for (Integer k1 : neighbors0) {
				for (HalfEdge edge : dataHolder.hedges) {
					if (edge.endpoint == k1) {
						neighbors1.add(edge.previous.endpoint);
						neighbors1.add(edge.next.endpoint);
					}
				}
				System.out.print(k1 + " ");
			}
			neighbors1.remove(v.id);
			System.out.print("drudzy sasiedzi: ");
			for (Integer k1 : neighbors1) {
				System.out.print(k1 + " ");
			}
			System.out.println();
		}

		stop = System.nanoTime();
		System.out.println("3.2.1. Czas wykonania: " + (stop - start));

		System.out.println("\n3.2.2. dla kazdego wierzcholka znalezienie elementow, do ktorych nalezy");
		start = System.nanoTime();
		for (Vertice vv : dataHolder.vv) {
			System.out.print("Wierzcholek nr " + vv.id + " nalezy do elementow: ");
			for (HalfEdge edge : vv.hedges) {
				System.out.print(edge.face.id + " ");
			}
			System.out.println();
		}
		stop = System.nanoTime();
		System.out.println("3.2.2. Czas wykonania: " + (stop - start));

		System.out.println("\n3.2.3. dla kazdego elementu wyznaczenie otoczenia elementow (pierwsza i druga warstwa sasiednich elementow)");
		start = System.nanoTime();
		for (Face face : dataHolder.ff) {
			System.out.print("Element nr " + face.id + " pierwsi sasiedzi: ");
			if (face.x.opposite != null)
				System.out.print(face.x.opposite.face.id + " ");
			if (face.y.opposite != null)
				System.out.print(face.y.opposite.face.id + " ");
			if (face.z.opposite != null)
				System.out.print(face.z.opposite.face.id + " ");
			System.out.print("drudzy sasiedzi: ");
			if (face.x.opposite != null) {
				if (face.x.opposite.face.x.opposite != null && face.x.opposite.face.x.opposite.face.id != face.id)
					System.out.print(face.x.opposite.face.x.opposite.face.id + " ");
				if (face.x.opposite.face.y.opposite != null && face.x.opposite.face.y.opposite.face.id != face.id)
					System.out.print(face.x.opposite.face.y.opposite.face.id + " ");
				if (face.x.opposite.face.z.opposite != null && face.x.opposite.face.z.opposite.face.id != face.id)
					System.out.print(face.x.opposite.face.z.opposite.face.id + " ");
			}
			if (face.y.opposite != null) {
				if (face.y.opposite.face.x.opposite != null && face.y.opposite.face.x.opposite.face.id != face.id)
					System.out.print(face.y.opposite.face.x.opposite.face.id + " ");
				if (face.y.opposite.face.y.opposite != null && face.y.opposite.face.y.opposite.face.id != face.id)
					System.out.print(face.y.opposite.face.y.opposite.face.id + " ");
				if (face.y.opposite.face.z.opposite != null && face.y.opposite.face.z.opposite.face.id != face.id)
					System.out.print(face.y.opposite.face.z.opposite.face.id + " ");
			}
			if (face.z.opposite != null) {
				if (face.z.opposite.face.x.opposite != null && face.z.opposite.face.x.opposite.face.id != face.id)
					System.out.print(face.z.opposite.face.x.opposite.face.id + " ");
				if (face.z.opposite.face.y.opposite != null && face.z.opposite.face.y.opposite.face.id != face.id)
					System.out.print(face.z.opposite.face.y.opposite.face.id + " ");
				if (face.z.opposite.face.z.opposite != null && face.z.opposite.face.z.opposite.face.id != face.id)
					System.out.print(face.z.opposite.face.z.opposite.face.id + " ");
			}
			System.out.println();
		}
		stop = System.nanoTime();
		System.out.println("3.2.3. Czas wykonania: " + (stop - start));

		System.out.println("\n3.2.4. zamiana krawedzi dla wskazanej pary przyleglych trojkatow wraz z odpowiednia zmiana struktury danych");
		start = System.nanoTime();
		flag = false;
		for (Face face : dataHolder.ff) {
			if (face.x.opposite != null) {
				flag = true;
				int tmp1 = face.x.opposite.next.endpoint;
				int tmp2 = face.x.endpoint;
				int tmp3 = face.x.next.endpoint;
				face.x.endpoint = face.x.next.endpoint;
				face.y.endpoint = face.x.opposite.endpoint;
				face.z.endpoint = face.x.opposite.next.endpoint;
				face.x.opposite.endpoint = tmp1;
				face.x.opposite.next.endpoint = tmp2;
				face.x.opposite.next.next.endpoint = tmp3;
				break;
			}
			if (face.y.opposite != null) {
				flag = true;
				int tmp1 = face.y.opposite.next.endpoint;
				int tmp2 = face.y.endpoint;
				int tmp3 = face.y.next.endpoint;
				face.x.endpoint = face.y.opposite.next.endpoint;
				face.y.endpoint = face.y.next.endpoint;
				face.z.endpoint = face.y.opposite.endpoint;
				face.y.opposite.endpoint = tmp1;
				face.y.opposite.next.endpoint = tmp2;
				face.y.opposite.next.next.endpoint = tmp3;
				break;
			}
			if (face.z.opposite != null) {
				flag = true;
				int tmp1 = face.z.opposite.next.endpoint;
				int tmp2 = face.z.endpoint;
				int tmp3 = face.z.next.endpoint;
				face.x.endpoint = face.z.opposite.endpoint;
				face.y.endpoint = face.z.opposite.next.endpoint;
				face.z.endpoint = face.z.next.endpoint;
				face.z.opposite.endpoint = tmp1;
				face.z.opposite.next.endpoint = tmp2;
				face.z.opposite.next.next.endpoint = tmp3;
				break;
			}
		}
		if (flag) {
			System.out.println("\nTablica elementow po zamianie krawedzi:");
			for (Face face : dataHolder.ff)
				System.out.println(face.z.endpoint + " " + face.x.endpoint + " " + face.y.endpoint);
			stop = System.nanoTime();
			System.out.println("3.2.4. Czas wykonania: " + (stop - start));
		} else System.out.println("Brak przyleglych trojkatow.");
		
		System.out.println("\n3.2.5. okreslenie, czy dana siatka posiada brzeg");
		start = System.nanoTime();
		flag = false;
		for (HalfEdge edge : dataHolder.hedges) {
			if (edge.opposite == null) {
				flag = true;
				break;
			}
		}
		if (flag)
			System.out.println("Siatka posiada brzeg");
		else
			System.out.println("Siatka nie posiada brzegu");
		stop = System.nanoTime();
		System.out.println("3.2.5. Czas wykonania: " + (stop - start));
	}
}