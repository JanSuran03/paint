import javax.swing.*;
import java.awt.*;

public class PaintPanel extends JPanel {
    private final Form form;

    public PaintPanel(Form form) {
        this.form = form;
    }

    void paintImage(Graphics g) {
        Image img = form.selectedImage;
        if (img != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(img, null, this);
            g2d.dispose();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintImage(g);
    }
}