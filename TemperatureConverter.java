import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureConverter extends JFrame {
    private JTextField inputField;
    private JComboBox<String> unitBox;
    private JLabel celsiusLabel;
    private JLabel fahrenheitLabel;
    private JLabel kelvinLabel;

    public TemperatureConverter() {
        setTitle("Temperature Converter");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        JLabel inputLabel = new JLabel("Enter temperature: ");
        inputField = new JTextField(10);
        String[] units = {"Celsius", "Fahrenheit", "Kelvin"};
        unitBox = new JComboBox<>(units);
        JButton convertButton = new JButton("Convert");
        celsiusLabel = new JLabel("Celsius: ");
        fahrenheitLabel = new JLabel("Fahrenheit: ");
        kelvinLabel = new JLabel("Kelvin: ");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertTemperature();
            }
        });
        add(inputLabel);
        add(inputField);
        add(unitBox);
        add(convertButton);
        add(celsiusLabel);
        add(fahrenheitLabel);
        add(kelvinLabel);
        setVisible(true);
    }
    private void convertTemperature() {
        try {
            double inputTemp = Double.parseDouble(inputField.getText());
            String selectedUnit = (String) unitBox.getSelectedItem();

            double celsius, fahrenheit, kelvin;

            switch (selectedUnit) {
                case "Celsius":
                    celsius = inputTemp;
                    fahrenheit = (celsius * 9/5) + 32;
                    kelvin = celsius + 273.15;
                    break;
                case "Fahrenheit":
                    fahrenheit = inputTemp;
                    celsius = (fahrenheit - 32) * 5/9;
                    kelvin = celsius + 273.15;
                    break;
                case "Kelvin":
                    kelvin = inputTemp;
                    celsius = kelvin - 273.15;
                    fahrenheit = (celsius * 9/5) + 32;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + selectedUnit);
            }

            celsiusLabel.setText(String.format("Celsius: %.2f", celsius));
            fahrenheitLabel.setText(String.format("Fahrenheit: %.2f", fahrenheit));
            kelvinLabel.setText(String.format("Kelvin: %.2f", kelvin));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        new TemperatureConverter();
    }
}
