import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class UITheme {
    // Colors
    public static final Color BG_MAIN          = new Color(30, 31, 34);
    public static final Color BG_SURFACE       = new Color(43, 45, 48);
    public static final Color TEXT_PRIMARY     = new Color(242, 243, 245);
    public static final Color TEXT_MUTED       = new Color(175, 177, 182);
    public static final Color BORDER_COLOR     = new Color(78, 80, 88);

    // Action Colors
    public static final Color ACCENT_PRIMARY   = new Color(88, 101, 242);
    public static final Color ACCENT_SECONDARY = new Color(78, 80, 88);
    public static final Color TEXT_ON_ACCENT   = Color.WHITE;

    // Typography
    public static final Font FONT_TITLE    = new Font("Arial", Font.BOLD, 26);
    public static final Font FONT_LABEL    = new Font("Arial", Font.PLAIN, 14);
    public static final Font FONT_HEADER   = new Font("Arial", Font.BOLD, 18);
    public static final Font FONT_TABLE    = new Font("Arial", Font.PLAIN, 13);

    public static void applyTextFieldStyle(JTextField field) {
        field.setBackground(BG_SURFACE);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setFont(FONT_LABEL);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }

    public static void applyButtonStyle(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(FONT_LABEL);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }

    public static TitledBorder createCustomTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1), title
        );
        border.setTitleColor(TEXT_MUTED);
        border.setTitleFont(FONT_LABEL);
        return border;
    }
}
