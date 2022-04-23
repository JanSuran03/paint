import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class PaintPanel extends JPanel {
    private final Form form;

    public PaintPanel(Form form) {
        this.form = form;
    }

    public int mouse_button = 0;

    public void mouseListener(MouseEvent e) {
        if (form.isDrawingToCanvas) {
            System.out.println(e.getButton());
            ColorPicker color_picker;
            switch (e.getButton()) {
                case 1:
                    mouse_button = 1;
                    color_picker = form.main_color_picker;
                    break;
                case 3:
                    mouse_button = 3;
                    color_picker = form.eraser_color_picker;
                    break;
                case 0:
                    if (mouse_button == 1) {
                        color_picker = form.main_color_picker;
                    } else {
                        color_picker = form.eraser_color_picker;
                    }
                    break;
                default:
                    //System.out.println("middle button?");
                    return;
            }
            Graphics2D g2d = (Graphics2D) form.selectedImage.getGraphics().create();
            g2d.setColor(color_picker.color_palette.getColor());
            int x = e.getX(), y = e.getY(), width = (int) form.numberChooser.getValue();
            g2d.fillOval(x - (width / 2), y - (width / 2), width, width);
            g2d.dispose();
            repaint();
        }
    }

    public void initListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseListener(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
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
                mouseListener(e);
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