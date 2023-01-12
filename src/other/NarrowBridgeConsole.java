//package com.company.rogalinski;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//public class NarrowBridgeConsole{
//
//	// Parametr TRAFFIC okre�la nat�enie ruchu bus�w.
//	// Mo�e przyjmowa� warto�ci z przedzia�u [0, 5000]
//	//    0 - bardzo ma�e nat�enie (nowy bus co 5500 ms)
//	// 5000 - bardzo du�e nat�enie (nowy bus co 500 ms )
//	private static int TRAFFIC = 1000;
//
//	public static void main(String[] args) {
//		NarrowBridge bridge = new NarrowBridge();
//		// new Thread(bridge).start();
//
//		// Zadaniem tego w�tka jest tworzenie kolejnych bus�w,
//		// kt�re maj� przewozi� przez most pasa�er�w:
//		// Przerwy pomi�dzy kolejnymi busami s� generowane losowo.
//		while (true) {
//			// Utworzenie nowego busa i uruchomienie w�tku,
//			// kt�ry symuluje przejazd busa przez most.
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




