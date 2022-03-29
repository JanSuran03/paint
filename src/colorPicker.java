import javax.swing.*;

public class colorPicker {

    public JColorChooser color_palette = new JColorChooser();
    JButton button_to_show_dialog;

    public colorPicker(JButton button) {
        button_to_show_dialog = button;
        color_palette.getSelectionModel().addChangeListener(changeEvent -> {
            button_to_show_dialog.setBackground(color_palette.getColor());
        });
    }

    public void show(){
        JDialog dialog = JColorChooser.createDialog(null, "Color Chooser",
                true, color_palette, null, null);
        dialog.setVisible(true);
    }
}
