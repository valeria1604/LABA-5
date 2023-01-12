//package com.company.rogalinski;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//public class NarrowBridgeConsole{
//
//	// Parametr TRAFFIC okreœla natê¿enie ruchu busów.
//	// Mo¿e przyjmowaæ wartoœci z przedzia³u [0, 5000]
//	//    0 - bardzo ma³e natê¿enie (nowy bus co 5500 ms)
//	// 5000 - bardzo du¿e natê¿enie (nowy bus co 500 ms )
//	private static int TRAFFIC = 1000;
//
//	public static void main(String[] args) {
//		NarrowBridge bridge = new NarrowBridge();
//		// new Thread(bridge).start();
//
//		// Zadaniem tego w¹tka jest tworzenie kolejnych busów,
//		// które maj¹ przewoziæ przez most pasa¿erów:
//		// Przerwy pomiêdzy kolejnymi busami s¹ generowane losowo.
//		while (true) {
//			// Utworzenie nowego busa i uruchomienie w¹tku,
//			// który symuluje przejazd busa przez most.
//			Bus bus = new Bus(bridge);
//			new Thread(bus).start();
//
//			// Przerwa przed utworzeniem kolejnego busa
//			try {
//				Thread.sleep(5500 - TRAFFIC);
//			} catch (InterruptedException e) {
//			}
//		}
//	}
//
//}  // koniec klasy NarrowBridgeConsole




