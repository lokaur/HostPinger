package pinger;

import pinger.controller.PingerController;

import javax.swing.*;

public class HostPinger {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(PingerController::new);
    }
}
