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
    private JTextField portField;
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
        portField.setText("80");
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

            int port = Integer.valueOf(portField.getText());
            if (port <= 0 || port > 9999) {
                errorLabel.setText("'Порт' должен иметь значение от 1 до 9999");
                return false;
            }

            JsonWriter.addHost(new Host(name, uri, port));
        } catch (NumberFormatException e) {
            errorLabel.setText("'Порт' должен быть числом");
            return false;
        } catch (MalformedURLException e) {
            errorLabel.setText("Введите валидный адрес");
            return false;
        } catch (HostExistsException e) {
            errorLabel.setText("Хост с таким адресом и портом уже существует");
            return false;
        } catch (IOException e) {
            errorLabel.setText("Произошла ошибка чтения файла");
            return false;
        } catch (ParseException e) {
            errorLabel.setText("Произошла ошибка распознавания файла");
            return false;
        }

        controller.reloadTable();
        return true;
    }
}
