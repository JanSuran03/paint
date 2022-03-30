import javax.swing.*;

public class ColorPicker {

    public JColorChooser color_palette = new JColorChooser();
    JButton button_to_show_dialog;

    public ColorPicker(JButton button) {
        button_to_show_dialog = button;
    }

    public void show() {
        JDialog dialog = JColorChooser.createDialog(null, "Color Chooser",
                true, color_palette,
                actionEvent -> button_to_show_dialog.setBackground(color_palette.getColor()),
                null);
        dialog.setVisible(true);
    }
}
