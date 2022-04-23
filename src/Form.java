import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class Form extends JFrame {
    public JPanel mainPanel;
    public JSpinner numberChooser;
    public JPanel paintPanel;
    public JPanel numberChooserWrapper;
    public JLabel widthLabel;
    public JButton mainColorButton;
    public JButton saveImageButton;
    public JPanel wrapperForButtons;
    public JButton loadImageButton;
    public JTextPane fileMetadata;
    public JButton rotateImageButton;
    private JButton switchToPencilButton;
    private JButton eraserColorButton;
    private JButton paintToolSwitcherButton;
    static final int MIN_WIDTH = 1, MAX_WIDTH = 150;
    public final ColorPicker main_color_picker = new ColorPicker(mainColorButton);

    public final ColorPicker eraser_color_picker = new ColorPicker(eraserColorButton);
    public final FileChooser fileChooser = new FileChooser(FileChooser.DialogType.LOAD);
    public final FileChooser fileSaver = new FileChooser(FileChooser.DialogType.SAVE);
    public Image selectedImage;
    public boolean isDrawingToCanvas = false;
    public HashMap<String, String> imageMeta;
    static final Color bg = new Color(170, 170, 170);

    void initListeners(Form form) {

        numberChooser.addChangeListener(changeEvent -> {
            Object v = numberChooser.getValue();
            try {
                numberChooser.setValue(Util.between((int) v,
                        MIN_WIDTH, MAX_WIDTH)
                );
            } catch (Throwable t) {
                System.out.println("invalid number: " + v.toString());
            }
        });
        paintToolSwitcherButton.addActionListener(actionEvent -> {
            if (((PaintPanel) paintPanel).current_paint_tool == PaintPanel.PaintTool.BRUSH) {
                ((PaintPanel) paintPanel).current_paint_tool = PaintPanel.PaintTool.SPRAY;
                paintToolSwitcherButton.setText("SPRAY");
            } else {
                ((PaintPanel) paintPanel).current_paint_tool = PaintPanel.PaintTool.BRUSH;
                paintToolSwitcherButton.setText("BRUSH");
            }
        });

        mainColorButton.addActionListener(actionEvent -> main_color_picker.show());

        eraserColorButton.addActionListener(actionEvent -> eraser_color_picker.show());

        loadImageButton.addActionListener(actionEvent -> {
            fileChooser.showOpenDialog(form);
        });

        saveImageButton.addActionListener(actionEvent -> {
            if (selectedImage != null)
                System.out.println("opening saving dialog");
            fileSaver.showOpenDialog(form);
        });

        fileChooser.initListeners(form);
        fileSaver.initListeners(form);

        rotateImageButton.addActionListener(actionEvent -> {
            if (selectedImage != null)
                if (isDrawingToCanvas)
                    System.out.println("Can't rotate custom bitmap: canvas dimensions aren't equal.");
                else {
                    selectedImage = ImageUtil.rotateImage(form, selectedImage, 90);
                    form.paintPanel.updateUI();
                }
        });

        switchToPencilButton.addActionListener(actionEvent ->

        {
            if (!isDrawingToCanvas) {
                isDrawingToCanvas = true;
                int w = paintPanel.getWidth(), h = paintPanel.getHeight();
                selectedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED);
                Graphics2D g2d = (Graphics2D) selectedImage.getGraphics().create();
                g2d.setColor(bg);
                g2d.fillRect(0, 0, w, h);
                g2d.dispose();
                imageMeta = null;
                fileMetadata.setText("");
                paintPanel.updateUI();
            }
        });
    }

    public void initButtonIcon() {
        ImageIcon imageIcon = new ImageIcon("images/pencil.png");
        Image img = imageIcon.getImage();
        Image newImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        switchToPencilButton.setIcon(new ImageIcon(newImg));
    }

    public void init() {
        numberChooser.setValue(8);
        mainPanel.setBackground(bg);
        initButtonIcon();
    }

    Form() {
        super("Paint");
        setContentPane(mainPanel);
        setBounds(300, 100, 1100, 700);
        init();
        initListeners(this);
    }

    public void main() {
        setVisible(true);
        Form f = this;
    }

    public static void main(String[] args) {
        Form form = new Form();
        form.main();
    }

    private void createUIComponents() {
        paintPanel = new PaintPanel(this);
        ((PaintPanel) paintPanel).initListeners();
    }
}
