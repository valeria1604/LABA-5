package com.company.rogalinski;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

class AnimationPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    AnimationPanel() {
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        g.setColor(Color.BLUE);
        g.fillRect(150, 0, 300, this.getHeight() - 1);
    }
}

