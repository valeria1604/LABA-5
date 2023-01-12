package com.company.rogalinski;

import java.util.concurrent.ThreadLocalRandom;

class Bus implements Runnable {

	// Staіe okreњlaj№ce minimalny i maksymalny czas
	// oczeiwania na nowych pasaїerуw.
	public static final int MIN_BOARDING_TIME = 1000;
	public static final int MAX_BOARDING_TIME = 10000;

	// Staіa okreњlaj№ca czas dojazdu busa do mostu.
	public static final int GETTING_TO_BRIDGE_TIME = 500;

	// Staіa okreњlaj№ca czas przejazdu przez most.
	public static final int CROSSING_BRIDGE_TIME = 3000;

	// Staіa okreњlaj№ca czas przjezdu od mostu do koсcowego parkingu.
	public static final int GETTING_PARKING_TIME = 500;

	// Staіa okreњlaj№ca czas wysiadania pasaїerуw z busa
	public static final int UNLOADING_TIME = 500;


	// Liczba wszystkich busуw, ktуre zostaіu utworzone
	// od pocz№tku dziaіania programu
	private static int numberOfBuses = 0;


	// Metoda usypia w№tek na podany czas w milisekundach
	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	// Metoda usypia w№tek na losowo dobrany czas
	// z przedziaіu [min, max) milsekund
	public static void sleep(int min_millis, int max_milis) {
		sleep(ThreadLocalRandom.current().nextInt(min_millis, max_milis));
	}


	// Referencja na obiekt reprezentuj№cy most.
	NarrowBridgeAnimation bridge;

	// Unikalny identyfikator kaїdego busa.
	// Jako identyfikator zostanie uїyty numer busa,
	// ktуry zostaі utworzony od pocz№tku dziaіania programu
	int id;

	// Kierunek jazdy busa nadany w sposуb losowy
	BusDirection dir;


	public Bus(NarrowBridgeAnimation bridge){
		this.bridge = bridge;
		this.id = ++numberOfBuses;
		if (ThreadLocalRandom.current().nextInt(0, 2) == 0)
			this.dir = BusDirection.EAST;
		else this.dir = BusDirection.WEST;
	}


	// Wydruk w konsoli informacji o stanie busa
	private void printBusInfo(String message){
		System.out.println("Bus[" + id + "->"+dir+"]: " + message);
	}


	// Symulacja oczekiwania na nowych pasaїerуw.
	private void boarding() {
		printBusInfo("Czeka na nowych pasaїerуw");
		sleep(MIN_BOARDING_TIME, MAX_BOARDING_TIME);
	}

	// Symulacja dojazdu ze stacji pocz№tkowej do mostu
	private void goToTheBridge() {
		printBusInfo("Jazda w stronк mostu");
		sleep(GETTING_TO_BRIDGE_TIME);
	}

	// Symulacja przejazdu przez most
	private void rideTheBridge(){
		printBusInfo("Przejazd przez most");
		sleep(CROSSING_BRIDGE_TIME);
	}

	// Symulacja przejazdu od mostu do koсcowego parkingu
	private void goToTheParking(){
		printBusInfo("Jazda w stronк koсcowego parkingu");
		sleep(GETTING_PARKING_TIME);
	}

	// Symulacja opuszczenia pojazdu na przystanku koсcowym
	private void unloading(){
		printBusInfo("Rozіadunek pasaїerуw");
		sleep(UNLOADING_TIME);
	}


	// Metoda realizuje "cykl їycia" pojedynczego busa
	public void run() {
		// oczekiwanie na nowych pasaїerуw
		boarding();

		// jazda w kierunku mostu
		goToTheBridge();

		//
		bridge.getOnTheBridge(this);

		// przejazd przez most
		rideTheBridge();

		bridge.getOffTheBridge(this);

		// jazda w kierunku parkingu koсcowego
		goToTheParking();

		// wypuszczenie pasaїerуw
		unloading();

		// koniec "їycia" w№tku
	}

}  // koniec klasy Bus
