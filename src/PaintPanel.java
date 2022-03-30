import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PaintPanel extends JPanel {
    public Image img;

    public PaintPanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println(img == null);
        if (img != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            AffineTransform at = new AffineTransform();
            at.translate(100, 40);
            g2d.drawImage(img, at, this);
            g2d.dispose();
        }
    }
}