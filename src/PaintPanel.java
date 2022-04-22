import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class PaintPanel extends JPanel {
    private final Form form;
    private boolean isMouseDown = false;

    public PaintPanel(Form form) {
        this.form = form;
    }

    public void initListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isMouseDown = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isMouseDown = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (form.isDrawingToCanvas) {
                    Graphics2D g2d = (Graphics2D) form.selectedImage.getGraphics().create();
                    g2d.setColor(form.color_picker.color_palette.getColor());
                    int x = e.getX(), y = e.getY(), width = (int) form.numberChooser.getValue();
                    g2d.fillOval(x - (width / 2), y - (width / 2), width, width);
                    g2d.dispose();
                    repaint();
                }
            }
        });
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