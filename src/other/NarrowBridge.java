//package other;
//
//import com.company.rogalinski.Bus;
//
//import java.util.LinkedList;
//import java.util.List;
//
//class NarrowBridge {
//
//    // Lista busуw (kolejka) oczekuj№cych na wjazd na most
//    List<Bus> busesWaiting = new LinkedList<Bus>();
//
//    // Lista busуw poruszaj№cych siк aktualnie po moњcie
//    List<Bus> busesOnTheBridge = new LinkedList<Bus>();
//
//    // Wydruk informacji o busach oczekuj№cych w kolejce oraz
//    // aktualnie przejeїdїaj№cych przez most.
//    private void printBridgeInfo(Bus bus, String message) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Bus[" + bus.id + "->" + bus.dir + "]  ");
//        sb.append(message + "\n");
//        sb.append("           Na moњcie: ");
//        for (Bus b : busesOnTheBridge) {
//            sb.append(b.id + "  ");
//
//        }
//        sb.append("    Kolejka: ");
//        for (Bus b : busesWaiting) {
//            sb.append(b.id + "  ");
//        }
//        System.out.println(sb);
//    }
//
//    // Procedura monitora, ktуry wpuszcza busy na most
//    private synchronized void getOnTheBridge(Bus bus) {
//        // Prosty warunek wjazudu na most:
//        // DOPУKI LISTA BUSУW NA MOЊCIE NIE JEST PUSTA
//        // KOLEJNY BUS MUSI CZEKAЖ NA WJAZD
//        while (!busesOnTheBridge.isEmpty()) {
//            // dodanie busa do listy oczekuj№cych
//            busesWaiting.add(bus);
//            printBridgeInfo(bus, "CZEKA NA WJAZD");
//            try {
//                wait();
//            } catch (InterruptedException e) {
//            }
//            // usuniкcie busa z listy oczekuj№cych.
//            busesWaiting.remove(bus);
//        }
//        // dodanie busa do listy jad№cych przez most
//        busesOnTheBridge.add(bus);
//        printBridgeInfo(bus, "WJEЇDЇA NA MOST");
//    }
//
//    // Procedura monitora, ktуra rejestruje busy opuszczaj№ce most
//    // i powiadamia inne busy oczekuj№ce w kolejce na wjazd
//    private synchronized void getOffTheBridge(Bus bus) {
//        // usuniкcie busa z listy poruszaj№cych siк przez most
//        busesOnTheBridge.remove(bus);
//        printBridgeInfo(bus, "OPUSZCZA MOST");
//        // powiadomienie innych oczekuj№cych.
//        notify();
//    }
//
//}  // koniec klasy NarrowBridge
