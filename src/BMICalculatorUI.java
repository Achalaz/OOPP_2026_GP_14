import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
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

    // Modern Flat Color Palette
    private final Color COLOR_BG = new Color(30, 30, 36);          // Dark Charcoal Background
    private final Color COLOR_PANEL_BG = new Color(43, 43, 50);    // Slightly Lighter Card Background
    private final Color COLOR_TEXT_MAIN = new Color(245, 245, 247); // Clean Crisp White Text
    private final Color COLOR_TEXT_MUTED = new Color(160, 160, 168);// Soft Gray for Secondary Elements
    private final Color COLOR_ACCENT = new Color(10, 132, 255);    // Modern Royal Blue Primary
    private final Color COLOR_SECONDARY = new Color(64, 64, 75);   // Border & Secondary Action Gray

    public BMICalculatorUI() {
        // Configure the application window.
        setTitle("BMI Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Root Panel Configuration
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(COLOR_BG);

        // Header Title
        JLabel title = new JLabel("BMI Calculator");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(COLOR_TEXT_MAIN);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(20));

        // Unit Selection Area
        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(metricButton);
        unitGroup.add(englishButton);

        JPanel unitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        unitPanel.setBackground(COLOR_BG);

        JLabel unitsTitle = new JLabel("Units:");
        unitsTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        unitsTitle.setForeground(COLOR_TEXT_MAIN);
        unitPanel.add(unitsTitle);

        styleRadioButton(metricButton);
        styleRadioButton(englishButton);
        unitPanel.add(metricButton);
        unitPanel.add(englishButton);

        mainPanel.add(unitPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Form Fields Area
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        inputPanel.setBackground(COLOR_BG);

        styleFieldLabel(weightLabel);
        styleFieldLabel(heightLabel);
        styleTextField(weightField);
        styleTextField(heightField);

        inputPanel.add(weightLabel);
        inputPanel.add(weightField);
        inputPanel.add(heightLabel);
        inputPanel.add(heightField);

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        // Action Buttons Area
        JButton calculateButton = new JButton("Calculate BMI");
        JButton clearButton = new JButton("Clear");

        styleButton(calculateButton, COLOR_ACCENT, COLOR_TEXT_MAIN);
        styleButton(clearButton, COLOR_SECONDARY, COLOR_TEXT_MAIN);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(COLOR_BG);
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        // Visual Results Card
        JPanel resultPanel = new JPanel(new GridLayout(2, 1, 0, 8));
        resultPanel.setBackground(COLOR_PANEL_BG);
        resultPanel.setBorder(BorderFactory.createCompoundBorder(
                createModernTitledBorder("Result"),
                new EmptyBorder(15, 15, 15, 15)
        ));

        bmiLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        bmiLabel.setForeground(COLOR_ACCENT);
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        categoryLabel.setForeground(COLOR_TEXT_MAIN);

        resultPanel.add(bmiLabel);
        resultPanel.add(categoryLabel);

        mainPanel.add(resultPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        // Data Reference Grid
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

        styleTable(referenceTable);

        JPanel referencePanel = new JPanel(new BorderLayout());
        referencePanel.setBackground(COLOR_BG);
        referencePanel.setBorder(createModernTitledBorder("BMI Reference"));
        referencePanel.add(referenceTable.getTableHeader(), BorderLayout.NORTH);
        referencePanel.add(referenceTable, BorderLayout.CENTER);

        mainPanel.add(referencePanel);

        // Hook Interactive Handlers
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

    // Helper styling strategies to keep logic decoupled from appearance
    private void styleRadioButton(JRadioButton button) {
        button.setBackground(COLOR_BG);
        button.setForeground(COLOR_TEXT_MUTED);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusPainted(false);
    }

    private void styleFieldLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(COLOR_TEXT_MAIN);
    }

    private void styleTextField(JTextField field) {
        field.setBackground(COLOR_PANEL_BG);
        field.setForeground(COLOR_TEXT_MAIN);
        field.setCaretColor(COLOR_TEXT_MAIN);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_SECONDARY, 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10) // Internal field margins
        ));
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private TitledBorder createModernTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_SECONDARY, 1), title
        );
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 12));
        border.setTitleColor(COLOR_TEXT_MUTED);
        return border;
    }

    private void styleTable(JTable table) {
        table.setBackground(COLOR_PANEL_BG);
        table.setForeground(COLOR_TEXT_MAIN);
        table.setGridColor(COLOR_SECONDARY);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setShowGrid(true);

        // Header Formatting
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBackground(COLOR_SECONDARY);
        header.setForeground(COLOR_TEXT_MAIN);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Center Align Text Cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(COLOR_PANEL_BG);
        centerRenderer.setForeground(COLOR_TEXT_MAIN);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
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

