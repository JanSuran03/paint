import javax.swing.*;
import java.awt.*;

public class ColorPicker {
    static final int HALF_MAX_RGB_SUM = 3 * 255 / 2;
    public JColorChooser color_palette = new JColorChooser();
    JButton button_to_show_dialog;

    public ColorPicker(JButton button) {
        button_to_show_dialog = button;
        button_to_show_dialog.setBackground(Color.WHITE);
    }

    public void show() {
        JDialog dialog = JColorChooser.createDialog(null, "Color Chooser",
                true, color_palette,
                (actionEvent) -> {
                    Color c = color_palette.getColor();
                    if (c.getRed() + c.getGreen() + c.getBlue() < HALF_MAX_RGB_SUM) {
                        button_to_show_dialog.setForeground(Color.WHITE);
                    } else {
                        button_to_show_dialog.setForeground(Color.BLACK);
                    }
                    button_to_show_dialog.setBackground(color_palette.getColor());
                },
                null);
        dialog.setVisible(true);
    }
}
