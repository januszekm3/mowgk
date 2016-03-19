package pl.worker;

import java.util.HashSet;
import java.util.Set;

import pl.datamodel.DataHolder;

public class Worker {

	public static void main(String[] args) {
		long start, stop;
		Set<Integer> sasiedzi0, sasiedzi1;
		DataHolder dataHolder = new DataHolder("resources/ant.ply");
		//DataHolder dataHolder = new DataHolder("resources/cam.off");
		
		System.out.println("Tablica wierzcholkow:");
		for (int i = 0; i < dataHolder.vertices.length; i++) {
			System.out.println(dataHolder.vertices[i][0] + " " + dataHolder.vertices[i][1] + " " + dataHolder.vertices[i][2]);
		}
		
		System.out.println("\nTablica elementow:");
		for (int j = 0; j < dataHolder.faces.length; j++) {
			System.out.println(dataHolder.faces[j][0] + " " + dataHolder.faces[j][1] + " " + dataHolder.faces[j][2]);
		}
		System.out.println("\n3.1. Tablica wspolrzednych wierzcholkow oraz tzw. tablica polaczen"); 
		System.out.println("\n3.1.1.dla kazdego wierzcholka wyznaczanie otoczenia wierzcholkow (pierwsza i druga warstwa sasiednich wierzcholkow)");
		start = System.nanoTime();
		for (int i = 0; i < dataHolder.vertices.length; i ++){
			System.out.print("Wierzcholek nr " + i + " pierwsi sasiedzi: ");
			sasiedzi0 = new HashSet<Integer>();
			sasiedzi1 = new HashSet<Integer>();
			for (int j = 0; j < dataHolder.faces.length; j++) {
				if (dataHolder.faces[j][0] == i) {
					sasiedzi0.add(dataHolder.faces[j][1]);	//dodawanie
					sasiedzi0.add(dataHolder.faces[j][2]);	//obu sasiadow
				}
				if (dataHolder.faces[j][1] == i) {
					sasiedzi0.add(dataHolder.faces[j][0]);
					sasiedzi0.add(dataHolder.faces[j][2]);
				}
				if (dataHolder.faces[j][2] == i) {
					sasiedzi0.add(dataHolder.faces[j][0]);
					sasiedzi0.add(dataHolder.faces[j][1]);
				}
			}
			for (Integer k : sasiedzi0) {
				System.out.print(k + " ");
				for (int j = 0; j < dataHolder.faces.length; j++) {
					if (dataHolder.faces[j][0] == k) {
						//czy nie dodajemy tego samego wierzcholka jako drugiego sasiada
						if (dataHolder.faces[j][1] != i) sasiedzi1.add(dataHolder.faces[j][1]);
						if (dataHolder.faces[j][2] != i) sasiedzi1.add(dataHolder.faces[j][2]);
					}
					if (dataHolder.faces[j][1] == k) {
						if (dataHolder.faces[j][0] != i) sasiedzi1.add(dataHolder.faces[j][0]);
						if (dataHolder.faces[j][2] != i) sasiedzi1.add(dataHolder.faces[j][2]);
					}
					if (dataHolder.faces[j][2] == k) {
						if (dataHolder.faces[j][0] != i) sasiedzi1.add(dataHolder.faces[j][0]);
						if (dataHolder.faces[j][1] != i) sasiedzi1.add(dataHolder.faces[j][1]);
					}
				}
			}
			System.out.print("drudzy sasiedzi: ");
			for (Integer k : sasiedzi1) {
				System.out.print(k + " ");
			}
			System.out.println();
		}
		
		stop = System.nanoTime();
		System.out.println("Czas wykonania: "+(stop-start));
		
		System.out.println("\n3.1.2.dla kazdego wierzcholka znalezienie elementow, do ktorych nalezy");
		start = System.nanoTime();
		for (int i = 0; i < dataHolder.vertices.length; i ++){
			System.out.print("Wierzcholek nr " + i + " nalezy do elementow: ");
			for (int j = 0; j < dataHolder.faces.length; j++)
				if(dataHolder.faces[j][0] == i || dataHolder.faces[j][1] == i || dataHolder.faces[j][2] == i)
					System.out.print(j + " ");
			System.out.println();
		}
		stop = System.nanoTime();
		System.out.println("Czas wykonania: "+(stop-start));
		
		System.out.println("\n3.1.3.dla kazdego elementu wyznaczenie otoczenia elementow (pierwsza i druga warstwa sasiednich elementow)");
		start = System.nanoTime();
		int wierzcholek0, wierzcholek1;
		for (int i = 0; i < dataHolder.faces.length; i++){
			System.out.print("Element nr " + i + " pierwsi sasiedzi: ");
			sasiedzi0 = new HashSet<Integer>();
			sasiedzi1 = new HashSet<Integer>();
			for (int j = 0; j < 3; j++) {	//3 krawedzie w obrebie elementu
				wierzcholek0 =  dataHolder.faces[i][j];
				wierzcholek1 =  dataHolder.faces[i][(j+1)%3];
				for (int k = 0; k < dataHolder.faces.length; k++) {
					if ((i != k) &&	//czy nie dodajemy tego samego elementu (przechodzimy drugi raz po tej samej tablicy)
						(((wierzcholek0 == dataHolder.faces[k][0] && wierzcholek1 == dataHolder.faces[k][1]) 
							|| (wierzcholek0 == dataHolder.faces[k][1] && wierzcholek1 == dataHolder.faces[k][0]))
						|| ((wierzcholek0 == dataHolder.faces[k][1] && wierzcholek1 == dataHolder.faces[k][2]) 
							|| (wierzcholek0 == dataHolder.faces[k][2] && wierzcholek1 == dataHolder.faces[k][1]))
						|| ((wierzcholek0 == dataHolder.faces[k][0] && wierzcholek1 == dataHolder.faces[k][2]) 
							|| (wierzcholek0 == dataHolder.faces[k][2] && wierzcholek1 == dataHolder.faces[k][0]))))
								sasiedzi0.add(k);
				}
			}
			for (Integer k : sasiedzi0) {
				System.out.print(k + " ");
				for (int j = 0; j < 3; j++) {	//3 krawedzie w obrebie elementu
					wierzcholek0 =  dataHolder.faces[k][j];
					wierzcholek1 =  dataHolder.faces[k][(j+1)%3];
					for (int l = 0; l < dataHolder.faces.length; l++) {
						//czy nie dodajemy tego samego elementu jako drugiego sasiada
						if ((l != i) && (l != k) &&
							(((wierzcholek0 == dataHolder.faces[l][0] && wierzcholek1 == dataHolder.faces[l][1]) 
								|| (wierzcholek0 == dataHolder.faces[l][1] && wierzcholek1 == dataHolder.faces[l][0]))
							|| ((wierzcholek0 == dataHolder.faces[l][1] && wierzcholek1 == dataHolder.faces[l][2]) 
								|| (wierzcholek0 == dataHolder.faces[l][2] && wierzcholek1 == dataHolder.faces[l][1]))
							|| ((wierzcholek0 == dataHolder.faces[l][0] && wierzcholek1 == dataHolder.faces[l][2]) 
								|| (wierzcholek0 == dataHolder.faces[l][2] && wierzcholek1 == dataHolder.faces[l][0]))))
									sasiedzi1.add(l);
					}
				}
			}
			System.out.print("drudzy sasiedzi: ");
			for (Integer k : sasiedzi1) {
				System.out.print(k + " ");
			}
			System.out.println();
		}
		stop = System.nanoTime();
		System.out.println("Czas wykonania: "+(stop-start));
		
		System.out.println("\n3.1.4.zamiana krawedzi dla wskazanej pary przyleglych trojkatow wraz z odpowiednia zmiana struktury danych");
		start = System.nanoTime();
		stop = System.nanoTime();
		System.out.println("Czas wykonania: "+(stop-start));

		System.out.println("\n3.1.5.okreslenie, czy dana siatka posiada brzeg");
		start = System.nanoTime();
		stop = System.nanoTime();
		System.out.println("Czas wykonania: "+(stop-start));
		
		System.out.println("\n3.2. „half edge”/„winged edge”");
		System.out.println("\n3.2.1.dla kazdego wierzcholka wyznaczanie otoczenia wierzcholkow (pierwsza i druga warstwa sasiednich wierzcholkow)");
		start = System.nanoTime();
		stop = System.nanoTime();
		System.out.println("Czas wykonania: "+(stop-start));
		
		System.out.println("\n3.2.2.dla kazdego wierzcholka znalezienie elementow, do ktorych nalezy");
		start = System.nanoTime();
		stop = System.nanoTime();
		System.out.println("Czas wykonania: "+(stop-start));
		
		System.out.println("\n3.2.3.dla kazdego elementu wyznaczenie otoczenia elementow (pierwsza i druga warstwa sasiednich elementow)");
		start = System.nanoTime();
		stop = System.nanoTime();
		System.out.println("Czas wykonania: "+(stop-start));
		
		System.out.println("\n3.2.4.zamiana krawedzi dla wskazanej pary przyleglych trojkatow wraz z odpowiednia zmiana struktury danych");
		start = System.nanoTime();
		stop = System.nanoTime();
		System.out.println("Czas wykonania: "+(stop-start));

		System.out.println("\n3.2.5.okreslenie, czy dana siatka posiada brzeg");
		start = System.nanoTime();
		stop = System.nanoTime();
		System.out.println("Czas wykonania: "+(stop-start));
	}
}