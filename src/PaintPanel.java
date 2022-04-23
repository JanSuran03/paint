import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PaintPanel extends JPanel {
    private final Form form;

    public PaintPanel(Form form) {
        this.form = form;
    }

    public boolean mouse_down = false;

    private long counter = 0L;

    public Thread spray_thread;

    public int mouse_button = 0;

    public enum PaintTool {
        BRUSH, SPRAY
    }

    static Random random = new Random();

    public PaintTool current_paint_tool = PaintTool.BRUSH;

    public void mouseListener(MouseEvent e) {
        if (form.isDrawingToCanvas) {
            ColorPicker color_picker;
            switch (e.getButton()) {
                case 1: // left button
                    mouse_button = 1;
                    color_picker = form.main_color_picker;
                    break;
                case 3: // right button
                    mouse_button = 3;
                    color_picker = form.eraser_color_picker;
                    break;
                case 0: // mouse dragged
                    if (mouse_button == 1) {
                        color_picker = form.main_color_picker;
                    } else {
                        color_picker = form.eraser_color_picker;
                    }
                    break;
                default:
                    return;
            }
            mouse_down = true;
            AtomicInteger x = new AtomicInteger();
            AtomicInteger y = new AtomicInteger();
            int width;
            switch (current_paint_tool) {
                case BRUSH:
                    Graphics2D g2d = (Graphics2D) form.selectedImage.getGraphics().create();
                    g2d.setColor(color_picker.color_palette.getColor());
                    x.set(e.getX());
                    y.set(e.getY());
                    width = (int) form.numberChooser.getValue();
                    g2d.fillOval(x.get() - (width / 2), y.get() - (width / 2), width, width);
                    g2d.dispose();
                    break;
                case SPRAY:
                    if (spray_thread != null) {
                        counter++;
                        spray_thread.interrupt();
                    }
                    spray_thread = new Thread(() -> {
                        try {
                            int r = (int) form.numberChooser.getValue();
                            int r_square = r * r;
                            long temp = counter;
                            while (mouse_down) {
                                x.set(e.getX());
                                y.set(e.getY());
                                Graphics2D g2d_ = (Graphics2D) form.selectedImage.getGraphics().create();
                                for (int i = 0; i < r_square / 30 && counter == temp; i++) {
                                    int _x = random.nextInt(r) - r;
                                    if (random.nextBoolean())
                                        _x = -_x;
                                    int _y = random.nextInt((int) Math.sqrt(r_square - _x * _x + 1));
                                    if (random.nextBoolean())
                                        _y = -_y;
                                    g2d_.setColor(color_picker.color_palette.getColor());
                                    g2d_.fillOval(x.get() + _x / 2, y.get() + _y / 2, 2, 2);
                                }
                                g2d_.dispose();
                                repaint();
                                try {
                                    Thread.sleep(30);
                                } catch (InterruptedException ignored) {
                                }
                            }
                        } catch (Throwable t) {
                            t.printStackTrace();
                        } finally {
                            spray_thread = null;
                        }
                    });
                    spray_thread.start();
                    break;
            }
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
                mouse_down = false;
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