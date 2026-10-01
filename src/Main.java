import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        // Start the interface on the Swing event dispatch thread.
        SwingUtilities.invokeLater(() -> {
            BMICalculatorUI calculator = new BMICalculatorUI();
            calculator.setVisible(true);
        });
    }
}