//package com.company.rogalinski;
//
//import javax.swing.*;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import java.awt.*;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//
//public class NarrowBridgeAnimation extends JFrame {
//    private static final long serialVersionUID = 1L;
//    private static int TRAFFIC = 1000;
//    List<Bus> allBuses = new LinkedList();
//    List<Bus> busesWaiting = new LinkedList();
//    List<Bus> busesOnTheBridge = new LinkedList();
//    JTextField bridgeField = new JTextField(30);
//    JTextField queueField = new JTextField(30);
//    JTextArea textArea = new JTextArea(25, 50);
//
//    public static void main(String[] args) {
//        NarrowBridgeAnimation bridge = new NarrowBridgeAnimation();
//
//        while(true) {
//            Bus bus = new Bus(bridge);
//            (new Thread(bus)).start();
//
//            try {
//                Thread.sleep((long)(5500 - TRAFFIC));
//            } catch (InterruptedException var4) {
//            }
//        }
//    }
//
//    void printBridgeInfo(Bus bus, String message) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Bus[" + bus.id + "->" + bus.dir + "]  ");
//        sb.append(message + "\n");
//        this.textArea.insert(sb.toString(), 0);
//        sb = new StringBuilder();
//        Iterator var5 = this.busesWaiting.iterator();
//
//        Bus b;
//        while(var5.hasNext()) {
//            b = (Bus)var5.next();
//            sb.append(b.id);
//            sb.append(" ");
//        }
//
//        this.queueField.setText(sb.toString());
//        sb = new StringBuilder();
//        var5 = this.busesOnTheBridge.iterator();
//
//        while(var5.hasNext()) {
//            b = (Bus)var5.next();
//            sb.append(b.id);
//            sb.append(" ");
//        }
//
//        this.bridgeField.setText(sb.toString());
//    }
//
//    synchronized void getOnTheBridge(Bus bus) {
//        for(; !this.busesOnTheBridge.isEmpty(); this.busesWaiting.remove(bus)) {
//            this.busesWaiting.add(bus);
//            this.printBridgeInfo(bus, "CZEKA NA WJAZD");
//
//            try {
//                this.wait();
//            } catch (InterruptedException var3) {
//            }
//        }
//
//        this.busesOnTheBridge.add(bus);
//        this.printBridgeInfo(bus, "WJEŻDŻA NA MOST");
//    }
//
//    synchronized void getOffTheBridge(Bus bus) {
//        this.busesOnTheBridge.remove(bus);
//        this.printBridgeInfo(bus, "OPUSZCZA MOST");
//        this.notify();
//    }
//
//    NarrowBridgeAnimation() {
//        super("Symulacja przejazdu przez wąski most");
//        this.setSize(550, 710);
//        this.setResizable(false);
//        this.setDefaultCloseOperation(3);
//        JPanel leftPanel = new JPanel();
//        Font font = new Font("MonoSpaced", 1, 16);
//        final JSlider slider = new JSlider(0, 500, 5000, 2000);
//        slider.setFont(font);
//        slider.setSize(300, 20);
//        slider.setMajorTickSpacing(1000);
//        slider.setMinorTickSpacing(500);
//        slider.setPaintLabels(true);
//        Hashtable labelTable = new Hashtable();
//        labelTable.put(new Integer(0), new JLabel("Małe"));
//        labelTable.put(new Integer(5000), new JLabel("Duże"));
//        slider.setLabelTable(labelTable);
//        slider.addChangeListener(new ChangeListener() {
//            public void stateChanged(ChangeEvent e) {
//                NarrowBridgeAnimation.TRAFFIC = slider.getValue();
//            }
//        });
//        JLabel sliderLabel = new JLabel("Natężenie ruchu:");
//        JLabel bridgeLabel = new JLabel("       Na moście:");
//        JLabel queueLabel = new JLabel("         Kolejka:");
//        sliderLabel.setFont(font);
//        bridgeLabel.setFont(font);
//        queueLabel.setFont(font);
//        this.textArea.setFont(font);
//        this.bridgeField.setFont(font);
//        this.queueField.setFont(font);
//        leftPanel.add(sliderLabel);
//        leftPanel.add(slider);
//        leftPanel.add(bridgeLabel);
//        leftPanel.add(this.bridgeField);
//        leftPanel.add(queueLabel);
//        leftPanel.add(this.queueField);
//        this.textArea.setLineWrap(true);
//        this.textArea.setWrapStyleWord(true);
//        JScrollPane scroll_bars = new JScrollPane(this.textArea, 22, 30);
//        leftPanel.add(scroll_bars);
//        this.setContentPane(leftPanel);
//        this.setVisible(true);
//    }
//}

