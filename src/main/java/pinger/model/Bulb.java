package pinger.model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bulb extends JPanel {

    private boolean isAvailable;

    public Bulb(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String name = "src//main//resources//" + (isAvailable ? "green.jpg" : "red.jpg");
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(name));
        } catch (IOException ignored) {}

        g.drawImage(image, 0, 0, null);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

