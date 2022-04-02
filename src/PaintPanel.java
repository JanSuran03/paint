import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PaintPanel extends JPanel {
    public Image img;
    private Form form;

    public PaintPanel(Form form) {
        this.form = form;
    }

    void paintImage(Graphics g) {
        if (img != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            AffineTransform at = new AffineTransform();
            at.translate(100, 40);
            at.quadrantRotate(form.rotateQuad - 1,
                    (double) img.getWidth(this) / 2,
                    (double) img.getHeight(this) / 2);
            g2d.drawImage(img, at, this);
            g2d.dispose();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintImage(g);
    }
}