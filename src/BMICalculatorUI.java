import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Locale;

public class BMICalculatorUI extends JFrame {

    private final BMIModel model = new BMIModel();

    private final JRadioButton metricButton = new JRadioButton("Metric (kg / m)", true);
    private final JRadioButton englishButton = new JRadioButton("English (lb / in)");

    private final JLabel weightLabel = new JLabel("Weight (kg):");
    private final JLabel heightLabel = new JLabel("Height (m):");

    private final JTextField weightField = new JTextField(15);
    private final JTextField heightField = new JTextField(15);

    private final JLabel bmiLabel = new JLabel("Your BMI: --");
    private final JLabel categoryLabel = new JLabel("Category: --");

    public BMICalculatorUI() {
        setTitle("BMI Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UITheme.BG_MAIN);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("BMI Calculator");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(20));

        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(metricButton);
        unitGroup.add(englishButton);

        JPanel unitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        unitPanel.setBackground(UITheme.BG_MAIN);

        JLabel unitsLabel = new JLabel("Units:");
        unitsLabel.setForeground(UITheme.TEXT_MUTED);
        unitsLabel.setFont(UITheme.FONT_LABEL);
        unitPanel.add(unitsLabel);

        metricButton.setBackground(UITheme.BG_MAIN);
        metricButton.setForeground(UITheme.TEXT_PRIMARY);
        metricButton.setFont(UITheme.FONT_LABEL);
        metricButton.setFocusPainted(false);
        unitPanel.add(metricButton);

        englishButton.setBackground(UITheme.BG_MAIN);
        englishButton.setForeground(UITheme.TEXT_PRIMARY);
        englishButton.setFont(UITheme.FONT_LABEL);
        englishButton.setFocusPainted(false);
        unitPanel.add(englishButton);

        mainPanel.add(unitPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        inputPanel.setBackground(UITheme.BG_MAIN);

        weightLabel.setForeground(UITheme.TEXT_PRIMARY);
        weightLabel.setFont(UITheme.FONT_LABEL);
        inputPanel.add(weightLabel);

        UITheme.applyTextFieldStyle(weightField);
        inputPanel.add(weightField);

        heightLabel.setForeground(UITheme.TEXT_PRIMARY);
        heightLabel.setFont(UITheme.FONT_LABEL);
        inputPanel.add(heightLabel);

        UITheme.applyTextFieldStyle(heightField);
        inputPanel.add(heightField);

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JButton calculateButton = new JButton("Calculate BMI");
        UITheme.applyButtonStyle(calculateButton, UITheme.ACCENT_PRIMARY, UITheme.TEXT_ON_ACCENT);

        JButton clearButton = new JButton("Clear");
        UITheme.applyButtonStyle(clearButton, UITheme.ACCENT_SECONDARY, UITheme.TEXT_PRIMARY);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(UITheme.BG_MAIN);
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel resultPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        resultPanel.setBackground(UITheme.BG_SURFACE);
        resultPanel.setBorder(BorderFactory.createCompoundBorder(
                UITheme.createCustomTitledBorder("Result"),
                new EmptyBorder(10, 10, 10, 10)
        ));

        bmiLabel.setFont(UITheme.FONT_HEADER);
        bmiLabel.setForeground(UITheme.TEXT_PRIMARY);

        categoryLabel.setFont(UITheme.FONT_HEADER);
        categoryLabel.setForeground(UITheme.TEXT_MUTED);

        resultPanel.add(bmiLabel);
        resultPanel.add(categoryLabel);

        mainPanel.add(resultPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        String[] columns = {"Category", "BMI"};
        String[][] rows = {
                {"Underweight", "Less than 18.5"},
                {"Normal", "18.5 - 24.9"},
                {"Overweight", "25 - 29.9"},
                {"Obese", "30 or greater"}
        };

        JTable referenceTable = new JTable(rows, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        referenceTable.setRowHeight(28);
        referenceTable.setFont(UITheme.FONT_TABLE);
        referenceTable.getTableHeader().setReorderingAllowed(false);
        referenceTable.getTableHeader().setFont(UITheme.FONT_TABLE);

        referenceTable.setBackground(UITheme.BG_SURFACE);
        referenceTable.setForeground(UITheme.TEXT_PRIMARY);
        referenceTable.setGridColor(UITheme.BORDER_COLOR);
        referenceTable.getTableHeader().setBackground(UITheme.BG_MAIN);
        referenceTable.getTableHeader().setForeground(UITheme.TEXT_MUTED);
        referenceTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR));

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setBackground(UITheme.BG_SURFACE);
        cellRenderer.setForeground(UITheme.TEXT_PRIMARY);
        referenceTable.setDefaultRenderer(Object.class, cellRenderer);

        JPanel referencePanel = new JPanel(new BorderLayout());
        referencePanel.setBackground(UITheme.BG_SURFACE);
        referencePanel.setBorder(UITheme.createCustomTitledBorder("BMI Reference"));

        referencePanel.add(referenceTable.getTableHeader(), BorderLayout.NORTH);
        referencePanel.add(referenceTable, BorderLayout.CENTER);

        mainPanel.add(referencePanel);

        calculateButton.addActionListener(event -> calculateBMI());
        clearButton.addActionListener(event -> clearFields());
        metricButton.addActionListener(event -> changeUnits());
        englishButton.addActionListener(event -> changeUnits());

        getRootPane().setDefaultButton(calculateButton);
        setContentPane(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void changeUnits() {
        if (metricButton.isSelected()) {
            weightLabel.setText("Weight (kg):");
            heightLabel.setText("Height (m):");
        } else {
            weightLabel.setText("Weight (lb):");
            heightLabel.setText("Height (in):");
        }
        clearFields();
    }

    private void calculateBMI() {
        resetResult();

        String weightText = weightField.getText().trim();
        String heightText = heightField.getText().trim();

        if (weightText.isEmpty() || heightText.isEmpty()) {
            showError("Please enter both weight and height.");
            return;
        }

        try {
            double weight = Double.parseDouble(weightText);
            double height = Double.parseDouble(heightText);

            double bmi = model.calculateBMI(weight, height, metricButton.isSelected());

            bmiLabel.setText(String.format(Locale.US, "Your BMI: %.2f", bmi));
            categoryLabel.setText("Category: " + model.getCategory(bmi));

        } catch (NumberFormatException e) {
            showError("Please enter valid numbers only.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void clearFields() {
        weightField.setText("");
        heightField.setText("");
        resetResult();
        weightField.requestFocusInWindow();
    }

    private void resetResult() {
        bmiLabel.setText("Your BMI: --");
        categoryLabel.setText("Category: --");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
}
