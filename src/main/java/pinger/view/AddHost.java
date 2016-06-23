package pinger.view;

import org.json.simple.parser.ParseException;
import pinger.controller.PingerController;
import pinger.exceptions.HostExistsException;
import pinger.model.Host;
import pinger.model.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;

public class AddHost extends JFrame {
    private JTextField nameField;
    private JTextField uriField;
    private JButton buttonOk;
    private JButton buttonApply;
    private JButton buttonEscape;
    private JLabel errorLabel;
    private JPanel rootPanel;

    private PingerController controller;

    public AddHost(PingerController controller) {
        super("Новый хост");
        this.controller = controller;
        setContentPane(rootPanel);
        setSize(new Dimension(500, 300));
        createUIComponents();
        setLocationRelativeTo(controller.getMainFrame());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void createUIComponents() {
        buttonEscape.addActionListener(e -> AddHost.this.setVisible(false));
        buttonOk.addActionListener(e -> {
            if (saveHost())
                AddHost.this.setVisible(false);
        });
        buttonApply.addActionListener(e -> {
            if (saveHost())
                clearFields();
        });
    }

    private void clearFields() {
        nameField.setText("");
        uriField.setText("");
    }

    private boolean saveHost() {
        try {
            errorLabel.setText("");

            String name = nameField.getText();
            if (name.length() <= 0) {
                errorLabel.setText("Введите имя хоста");
                return false;
            }

            String uri = uriField.getText();

            JsonWriter.addHost(new Host(name, uri));
        } catch (MalformedURLException e) {
            errorLabel.setText("Введите правильный адрес.");
            return false;
        } catch (HostExistsException e) {
            errorLabel.setText("Хост с таким адресом уже существует.");
            return false;
        } catch (IOException e) {
            errorLabel.setText("Произошла ошибка чтения файла c диска. Проверьте расположение файла.");
            return false;
        } catch (ParseException e) {
            errorLabel.setText("Произошла ошибка чтения файла.");
            return false;
        }

        controller.reloadTable();
        return true;
    }
}
