package com.company.rogalinski;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class NarrowBridgeAnimation extends JFrame implements ItemListener {
    private static final long serialVersionUID = 1L;
    public static final int MAX_CARS_ON_THE_BRIDGE = 3;
    public static final int MAX_WAITING_BUSES = 10;
    private static int TRAFFIC = 1000;
    private List<Bus> allBuses = new LinkedList();
    private List<Bus> busesWaiting = new LinkedList();

    private List<Bus> busesWaitingEast = new LinkedList();
    private List<Bus> busesWaitingWest = new LinkedList();
    private int counterBuses;

    private List<Bus> busesOnTheBridge = new LinkedList();
    private JTextField bridgeField = new JTextField(30);
    private JTextField queueField = new JTextField(30);
    private JTextArea textArea = new JTextArea(25, 50);

    private RestrictionType chosenRestrictionType = RestrictionType.UNLIMITED;

    private BusDirection chosenBusDirection = BusDirection.EAST;


    private JComboBox<RestrictionType> restrictionTypeJComboBox = new JComboBox<RestrictionType>(RestrictionType.values());

    public static void main(String[] args) {
        NarrowBridgeAnimation bridge = new NarrowBridgeAnimation();


        while (true) {
            Bus bus = new Bus(bridge);
            (new Thread(bus)).start();

            try {
                Thread.sleep((long) (5500 - TRAFFIC));
            } catch (InterruptedException var4) {
            }
        }
    }

    //TODO
    private void printBridgeInfo(Bus bus, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("Bus[" + bus.id + "->" + bus.dir + "]  ");
        sb.append(message + "\n");
        this.textArea.insert(sb.toString(), 0);
        sb = new StringBuilder();
        Iterator var5 = this.busesWaiting.iterator();

        Bus b;
        while (var5.hasNext()) {
            b = (Bus) var5.next();
            sb.append(b.id);
            sb.append(" ");
        }

        this.queueField.setText(sb.toString());
        sb = new StringBuilder();
        var5 = this.busesOnTheBridge.iterator();

        while (var5.hasNext()) {
            b = (Bus) var5.next();
            sb.append(b.id);
            sb.append(" ");
        }

        this.bridgeField.setText(sb.toString());
    }

    private void printInfo(String message) {
        this.textArea.insert(message + "\n", 0);
    }

    public synchronized void getOnTheBridge(Bus bus) {
        if (chosenRestrictionType.equals(RestrictionType.UNLIMITED)) {
            unlimitedCarsMoving(bus);
        } else if (chosenRestrictionType.equals(RestrictionType.THREE_CARS_ON_BRIDGE)) {
            threeCarsMoving(bus);
        } else if (chosenRestrictionType.equals(RestrictionType.ONE_SIDE)) {
            oneSideRoadMoving(bus);
        } else if (chosenRestrictionType.equals(RestrictionType.TWO_SIDE)) {
            twoSideRoadMoving(bus);
        }
    }

    private void unlimitedCarsMoving(Bus bus) {
        busesOnTheBridge.add(bus);
        printBridgeInfo(bus, "WJEŻDŻA NA MOST");
    }

    private void twoSideRoadMoving(Bus bus) {
        while (!busesOnTheBridge.isEmpty()) {
            addWaitBus(bus, busesWaiting);
        }
        busesOnTheBridge.add(bus);
        printBridgeInfo(bus, "WJEŻDŻA NA MOST");
    }

    private void threeCarsMoving(Bus bus) {
        if (busesOnTheBridge.size() >= MAX_CARS_ON_THE_BRIDGE) {
            addWaitBus(bus, busesWaiting);
        }
        busesOnTheBridge.add(bus);
        printBridgeInfo(bus, "WJEŻDŻA NA MOST");
    }

    private void oneSideRoadMoving(Bus bus) {
        if(chosenBusDirection.equals(BusDirection.WEST)){

            if (bus.dir.equals(BusDirection.EAST)) {
                addWaitBus(bus, busesWaitingEast);
            }
            else if (bus.dir.equals(BusDirection.WEST)) {
                busesOnTheBridge.add(bus);
                counterBuses++;
                printBridgeInfo(bus, "WJEŻDŻA NA MOST");
            }
            if(counterBuses>5){
                chosenBusDirection = BusDirection.EAST;
                printInfo("EAST");
                counterBuses=0;
            }
        }

        if(chosenBusDirection.equals(BusDirection.EAST)){

            if (bus.dir.equals(BusDirection.WEST)) {
                addWaitBus(bus, busesWaitingWest);
            }
            else if (bus.dir.equals(BusDirection.EAST)) {
                busesOnTheBridge.add(bus);
                counterBuses++;
                printBridgeInfo(bus, "WJEŻDŻA NA MOST");
            }
            if(counterBuses>5){
                chosenBusDirection = BusDirection.WEST;
                printInfo("WEST");
                counterBuses=0;
            }
        }
    }



    private void addWaitBus(Bus bus, List busesWaiting) {
        busesWaiting.add(bus);
        printBridgeInfo(bus, "CZEKA NA WJAZD");
        try {
            wait();
        } catch (InterruptedException var3) {
        }
        busesWaiting.remove(bus);
    }


    public synchronized void getOffTheBridge(Bus bus) {
        busesOnTheBridge.remove(bus);
        printBridgeInfo(bus, "OPUSZCZA MOST");
        notify();
    }

    private NarrowBridgeAnimation() {
        super("Symulacja przejazdu przez wąski most");
        setSize(550, 710);
        setResizable(false);
        setDefaultCloseOperation(3);
        JPanel leftPanel = new JPanel();
        Font font = new Font("MonoSpaced", 1, 16);
        final JSlider slider = new JSlider(0, 500, 5000, 2000);
        slider.setFont(font);
        slider.setSize(300, 20);
        slider.setMajorTickSpacing(1000);
        slider.setMinorTickSpacing(500);
        slider.setPaintLabels(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put(0, new JLabel("Małe"));
        labelTable.put(5000, new JLabel("Duże"));
        slider.setLabelTable(labelTable);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                NarrowBridgeAnimation.TRAFFIC = slider.getValue();
            }
        });

        JLabel comboBoxLabel = new JLabel("    Ograniczenie ruchu:");
        JLabel sliderLabel = new JLabel("Natężenie ruchu:");
        JLabel bridgeLabel = new JLabel("       Na moście:");
        JLabel queueLabel = new JLabel("         Kolejka:");
        comboBoxLabel.setFont(font);
        sliderLabel.setFont(font);
        bridgeLabel.setFont(font);
        queueLabel.setFont(font);
        textArea.setFont(font);
        bridgeField.setFont(font);
        queueField.setFont(font);
        bridgeField.setEditable(false);
        queueField.setEditable(false);
        leftPanel.add(comboBoxLabel);

        restrictionTypeJComboBox.addItemListener(this);
        leftPanel.add(restrictionTypeJComboBox);

        leftPanel.add(sliderLabel);
        leftPanel.add(slider);
        leftPanel.add(bridgeLabel);
        leftPanel.add(bridgeField);
        leftPanel.add(queueLabel);
        leftPanel.add(queueField);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scroll_bars = new JScrollPane(textArea, 22, 30);
        leftPanel.add(scroll_bars);
        setContentPane(leftPanel);
        setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        chosenRestrictionType = (RestrictionType) restrictionTypeJComboBox.getSelectedItem();
    }

}

