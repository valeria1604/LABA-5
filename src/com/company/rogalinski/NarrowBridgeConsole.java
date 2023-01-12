package com.company.rogalinski;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NarrowBridgeConsole{
	
	// Parametr TRAFFIC okreœla natê¿enie ruchu busów.
	// Mo¿e przyjmowaæ wartoœci z przedzia³u [0, 5000]
	//    0 - bardzo ma³e natê¿enie (nowy bus co 5500 ms)
	// 5000 - bardzo du¿e natê¿enie (nowy bus co 500 ms )
	private static int TRAFFIC = 1000;
	
	public static void main(String[] args) {
		NarrowBridge bridge = new NarrowBridge();
		// new Thread(bridge).start();

		// Zadaniem tego w¹tka jest tworzenie kolejnych busów,
		// które maj¹ przewoziæ przez most pasa¿erów:
		// Przerwy pomiêdzy kolejnymi busami s¹ generowane losowo.
		while (true) {
			// Utworzenie nowego busa i uruchomienie w¹tku,
			// który symuluje przejazd busa przez most.
			Bus bus = new Bus(bridge);
			new Thread(bus).start();

			// Przerwa przed utworzeniem kolejnego busa
			try {
				Thread.sleep(5500 - TRAFFIC);
			} catch (InterruptedException e) {
			}
		}
	}
		
}  // koniec klasy NarrowBridgeConsole


enum BusDirection{
	EAST,
	WEST;
	
	@Override
	public String toString(){
		switch(this){
		case EAST: return "W";
		case WEST: return "Z";
		}
		return "";
	}
} // koniec typu wyliczeniowego BusDirection



class NarrowBridge {
	
	// Lista busów (kolejka) oczekuj¹cych na wjazd na most
	List<Bus> busesWaiting = new LinkedList<Bus>();
	
	// Lista busów poruszaj¹cych siê aktualnie po moœcie
	List<Bus> busesOnTheBridge = new LinkedList<Bus>();

	// Wydruk informacji o busach oczekuj¹cych w kolejce oraz
	// aktualnie przeje¿d¿aj¹cych przez most. 
	void printBridgeInfo(Bus bus, String message){
		StringBuilder sb = new StringBuilder();
		sb.append("Bus["+bus.id+"->"+bus.dir+"]  ");
		sb.append(message+"\n");
		sb.append("           Na moœcie: ");
		for(Bus b: busesOnTheBridge) sb.append(b.id + "  "); 
		sb.append("    Kolejka: ");
		for(Bus b: busesWaiting) sb.append(b.id + "  ");
		System.out.println(sb);
	}
	
	// Procedura monitora, który wpuszcza busy na most
	synchronized void getOnTheBridge(Bus bus){
		// Prosty warunek wjazudu na most:
		// DOPÓKI LISTA BUSÓW NA MOŒCIE NIE JEST PUSTA
		// KOLEJNY BUS MUSI CZEKAÆ NA WJAZD
		while( !busesOnTheBridge.isEmpty()){
			// dodanie busa do listy oczekuj¹cych
			busesWaiting.add(bus);
			printBridgeInfo(bus, "CZEKA NA WJAZD");
			try {
				wait();
			} catch (InterruptedException e) { }
			// usuniêcie busa z listy oczekuj¹cych.
			busesWaiting.remove(bus);
		}
		// dodanie busa do listy jad¹cych przez most
		busesOnTheBridge.add(bus);
		printBridgeInfo(bus, "WJE¯D¯A NA MOST");
	}
	
	// Procedura monitora, która rejestruje busy opuszczaj¹ce most
	// i powiadamia inne busy oczekuj¹ce w kolejce na wjazd
	synchronized void getOffTheBridge(Bus bus){
		// usuniêcie busa z listy poruszaj¹cych siê przez most
		busesOnTheBridge.remove(bus);
		printBridgeInfo(bus, "OPUSZCZA MOST");
		// powiadomienie innych oczekuj¹cych.
		notify();
	}

}  // koniec klasy NarrowBridge




class Bus implements Runnable {

	// Sta³e okreœlaj¹ce minimalny i maksymalny czas
	// oczeiwania na nowych pasa¿erów.
	public static final int MIN_BOARDING_TIME = 1000;
	public static final int MAX_BOARDING_TIME = 10000;

	// Sta³a okreœlaj¹ca czas dojazdu busa do mostu.
	public static final int GETTING_TO_BRIDGE_TIME = 500;
	
	// Sta³a okreœlaj¹ca czas przejazdu przez most.
	public static final int CROSSING_BRIDGE_TIME = 3000;
	
	// Sta³a okreœlaj¹ca czas przjezdu od mostu do koñcowego parkingu.
	public static final int GETTING_PARKING_TIME = 500;
	
	// Sta³a okreœlaj¹ca czas wysiadania pasa¿erów z busa
	public static final int UNLOADING_TIME = 500;
	
	
	// Liczba wszystkich busów, które zosta³u utworzone
	// od pocz¹tku dzia³ania programu
	private static int numberOfBuses = 0;
	
	
	// Metoda usypia w¹tek na podany czas w milisekundach
	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	// Metoda usypia w¹tek na losowo dobrany czas
	// z przedzia³u [min, max) milsekund
	public static void sleep(int min_millis, int max_milis) {
		sleep(ThreadLocalRandom.current().nextInt(min_millis, max_milis));
	}
	
	
	// Referencja na obiekt reprezentuj¹cy most.
	NarrowBridge bridge;
	
	// Unikalny identyfikator ka¿dego busa. 
	// Jako identyfikator zostanie u¿yty numer busa,
	// który zosta³ utworzony od pocz¹tku dzia³ania programu
	int id;
	
	// Kierunek jazdy busa nadany w sposób losowy
	BusDirection dir;
	
	
	Bus(NarrowBridge bridge){
		this.bridge = bridge;
		this.id = ++numberOfBuses;
		if (ThreadLocalRandom.current().nextInt(0, 2) == 0)
			this.dir = BusDirection.EAST;
		else this.dir = BusDirection.WEST;
	}
	
	
	// Wydruk w konsoli informacji o stanie busa
	void printBusInfo(String message){
		System.out.println("Bus[" + id + "->"+dir+"]: " + message);
	}
	
	
	// Symulacja oczekiwania na nowych pasa¿erów.
	void boarding() {
		printBusInfo("Czeka na nowych pasa¿erów");
		sleep(MIN_BOARDING_TIME, MAX_BOARDING_TIME);
	}

	// Symulacja dojazdu ze stacji pocz¹tkowej do mostu
	void goToTheBridge() {
		printBusInfo("Jazda w stronê mostu");
		sleep(GETTING_TO_BRIDGE_TIME);
	}

	// Symulacja przejazdu przez most
	void rideTheBridge(){
		printBusInfo("Przejazd przez most");
		sleep(CROSSING_BRIDGE_TIME);
	}

	// Symulacja przejazdu od mostu do koñcowego parkingu
	void goToTheParking(){
		printBusInfo("Jazda w stronê koñcowego parkingu");
		sleep(GETTING_PARKING_TIME);
	}
	
	// Symulacja opuszczenia pojazdu na przystanku koñcowym
	void unloading(){
		printBusInfo("Roz³adunek pasa¿erów");
		sleep(UNLOADING_TIME);
	}

	
	// Metoda realizuje "cykl ¿ycia" pojedynczego busa
	public void run() {
		// oczekiwanie na nowych pasa¿erów
		boarding();

		// jazda w kierunku mostu
		goToTheBridge();

		// 
		bridge.getOnTheBridge(this);

		// przejazd przez most
		rideTheBridge();

		bridge.getOffTheBridge(this);

		// jazda w kierunku parkingu koñcowego
		goToTheParking();

		// wypuszczenie pasa¿erów
		unloading();

		// koniec "¿ycia" w¹tku
	}

}  // koniec klasy Bus

